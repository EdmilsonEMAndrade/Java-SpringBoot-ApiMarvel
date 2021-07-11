package br.com.desafio.orange.talents.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafio.orange.talents.entity.Creator;
import br.com.desafio.orange.talents.repository.CreatorsRepository;

@Service
public class CreatorsService {
	
	CreatorsRepository repository;

	@Autowired
	public CreatorsService(CreatorsRepository repository) {
		this.repository = repository;
	}	
	
	@Transactional(readOnly=true)
	public Creator findCreators(JSONObject jsonObj) {
		String resourceURI = jsonObj.get("resourceURI").toString();
		Long id = idCreator(resourceURI);
		Optional<Creator> creOptional = findById(id);
		
		if(creOptional.isEmpty()) {
			String name = jsonObj.get("name").toString();
			return new Creator(id, name);
		}else {
			return creOptional.get(); 		
		}			
	}
		
	private Optional<Creator> findById(Long id){
		return repository.findById(id);
	}
	
	private Long idCreator(String resourceURI) {
		int last = resourceURI.lastIndexOf("/");
		String idString = resourceURI.substring(last+1);
		return Long.parseLong(idString);
	}

}
