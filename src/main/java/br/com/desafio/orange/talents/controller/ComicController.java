package br.com.desafio.orange.talents.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.desafio.orange.talents.entity.Comic;
import br.com.desafio.orange.talents.entity.DTO.ComicDTO;
import br.com.desafio.orange.talents.service.ComicsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/comics")
@Api(value="Orange Talents")
public class ComicController {
	
	ComicsService service;	
	
	public ComicController(ComicsService service) {
		this.service = service;
	}

	@ApiOperation(value="?page= by default will be equal to 1 &limit= by default will be equal to 20"
						+ " |Save per page the amount of comics informed in the limit, with a maximum of 100. "
							+ "Previously saved data will not be overwritten.")
	@PostMapping
	public ResponseEntity<Void> saveComicsByPage(@RequestParam(required = false, name="page", defaultValue = "1") int page,
													@RequestParam(required = false, name="limit", defaultValue = "20") int limit) {		
		service.saveComicsByPage(page, limit);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("")
				.buildAndExpand().toUri();
		return ResponseEntity.created(uri).build();
	}	
	
	@ApiOperation(value="Save comic by your registration ID in the Marvel API")
	@PostMapping("/{id}")
	public ResponseEntity<Void> saveById(@PathVariable Long id) {
		Comic comic = service.saveComicById(id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("")
				.buildAndExpand(comic.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value="Search for comic already registered in the database by Id")
	@GetMapping("/{id}")
	public ResponseEntity<Comic> findById(@PathVariable Long id) {
		Comic comic = service.findById(id).get();		
		return ResponseEntity.ok(comic);
	}	
	
	@ApiOperation(value="?page= min 1 Search all comics, divided by page.")
	@GetMapping()
	public ResponseEntity<Page<ComicDTO>> findAll(@PageableDefault(page = 0, size = 10) Pageable page) {
		Page<ComicDTO> comicDTO = service.findAll(page);		
		return ResponseEntity.ok(comicDTO);
	}	

}
