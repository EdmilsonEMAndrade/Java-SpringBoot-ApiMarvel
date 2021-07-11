package br.com.desafio.orange.talents.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.desafio.orange.talents.entity.User;
import br.com.desafio.orange.talents.entity.DTO.UserDTO;
import br.com.desafio.orange.talents.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/users")
@Api(value="Orange Talents")
public class UserController {
	
	UserService service;	
	
	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}
	
	@ApiOperation(value="Register user")
	@PostMapping
	public ResponseEntity<Void> saveUser(@Valid @RequestBody User user){
		User response = service.saveUser(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("")
				.buildAndExpand(response.getId()).toUri();
		return ResponseEntity.created(uri).build();		
	}
	
	@ApiOperation(value="Search user by ID")
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findUserById(@PathVariable Long id){		
		UserDTO user = service.findById(id);
		return ResponseEntity.ok(user);		
	}
	
	@ApiOperation(value="Search user by e-mail")
	@GetMapping
	public ResponseEntity<UserDTO> findUserByEmail(@RequestParam(name="email") String email){		
		UserDTO user = service.findByEmail(email);
		return ResponseEntity.ok(user);		
	}
	
	@ApiOperation(value="?idComic=   |Adds a comic for the user informing the comic id")
	@PutMapping("/{id}")
	public ResponseEntity<User> addComicByIds(@PathVariable Long id,
												@RequestParam(name="idComic") Long idComic){		
		User user = service.addComicByIds(id, idComic);
		return ResponseEntity.ok(user);		
	}
	

}
