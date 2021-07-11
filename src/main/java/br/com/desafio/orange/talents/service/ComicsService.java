package br.com.desafio.orange.talents.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import br.com.desafio.orange.talents.entity.Comic;
import br.com.desafio.orange.talents.entity.Creator;
import br.com.desafio.orange.talents.entity.Prices;
import br.com.desafio.orange.talents.entity.DTO.ComicDTO;
import br.com.desafio.orange.talents.entity.enums.TypePrice;
import br.com.desafio.orange.talents.repository.ComicsRepository;
import br.com.desafio.orange.talents.service.exception.ValidationException;

@Service
public class ComicsService {

	ComicsRepository repository;
	CreatorsService creatorsService;
	
	@Value("${api.marvel.key}")
	private String key; 
	
	@Value("${api.marvel.hash}")
	private String hash; 
	
	@Value("${api.marvel.ts}")
	private String ts; 

	@Autowired
	public ComicsService(ComicsRepository repository, CreatorsService creatorsService) {
		this.repository = repository;
		this.creatorsService = creatorsService;
	}
	
	@Transactional(readOnly=true)
	public Page<ComicDTO> findAll(Pageable pageable){
		Page<Comic> comic = repository.findAll(pageable);		
		return comic.map(x-> new ComicDTO(x));
	}

	public List<Comic> saveComicsByPage(int page, int limit) {
		if(limit>100)throw new ValidationException("The maximum limit is 100 comics per entry");
		if(limit<1)throw new ValidationException("The minimum limit is 1 comics per entry");
		if(page<1)throw new ValidationException("The first page is number 1");
		page -=1;
		int maxPage = 49136/limit;
		if(page>maxPage)throw new ValidationException("With " + limit + " comics per page, the maximum number of pages is: "+ (maxPage +1));
		
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://gateway.marvel.com/v1/public/comics"
				 		+ "?limit=" +limit
				 		+ "&offset=" + (limit*page)
				 		+ "&apikey=" + key
				 		+ "&hash=" + hash
				 		+ "&ts=" + ts;

		String response = restTemplate.getForObject(url, String.class);
		return saveFromApi(response);

	}
	
	public Comic saveComicById(Long id) {
		//Check if already registered so that you don't need to query the external API
		if(findById(id).isPresent()) throw new ValidationException("Comic (id= "+ id +") already registered");
		
		
		RestTemplate restTemplate = new RestTemplate();		
		String url = "https://gateway.marvel.com/v1/public/comics/" + id
						+ "?apikey=" + key
				 		+ "&hash=" + hash
				 		+ "&ts=" + ts;
		
		String response = restTemplate.getForObject(url, String.class);
		
		return saveFromApi(response).get(0);
	}
	
	public Optional<Comic> findById(Long id) {
		Optional<Comic> comicOpt= repository.findById(id);
		return comicOpt;		
	}	
	

	private List<Comic> saveFromApi(String responseApi) {
		
		List<Comic> comicList = new ArrayList<>(); 
		
		JSONObject response = new JSONObject(responseApi);
		JSONObject data = new JSONObject(response.get("data").toString());
		JSONArray results = data.getJSONArray("results");
		
		for (int i = 0; i < results.length(); i++) {
			
			String idString = results.getJSONObject(i).get("id").toString();
			Long id = Long.parseLong(idString);
			
			//Checking if already registered so as not to overwrite the data already registered
			Optional<Comic> comicOpt= findById(id);
			if(comicOpt.isPresent()) {
				comicList.add(comicOpt.get());
				break;
			}
			Set<Prices> priceList = new HashSet<>();
			
			String title = results.getJSONObject(i).get("title").toString();
			String isbn = results.getJSONObject(i).get("isbn").toString();
			String description = results.getJSONObject(i).get("description").toString();
			
			//Limiting the description so as not to exceed the size that Postgres accepts
			if (description.length() > 1000) {
				description = description.substring(999);
			}
			
			String priceString;
			Double price = 0.0;
			Set<Creator> creatorsSet = new HashSet<>();
			Comic comic = new Comic(id, title, description, isbn, creatorsSet);
			JSONArray prices = results.getJSONObject(i).getJSONArray("prices");

			for (int j = 0; j < prices.length(); j++) {
				String type = prices.getJSONObject(j).get("type").toString();
				priceString = prices.getJSONObject(j).get("price").toString();
				price = Double.parseDouble(priceString);
				priceList.add(new Prices(TypePrice.toEnumByString(type).getCod()
									, price, comic));				
			}
			comic.setPrices(priceList);

			JSONObject creators = new JSONObject(results.getJSONObject(i).get("creators").toString());

			JSONArray items = creators.getJSONArray("items");
			
			for (int j = 0; j < items.length(); j++) {
				comic.addCreators(creatorsService.findCreators(items.getJSONObject(j)));
			}
			
			comicList.add(repository.save(comic));
		}
		return comicList;
	}
}
