package br.com.desafio.orange.talents.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafio.orange.talents.entity.Comic;
import br.com.desafio.orange.talents.entity.User;
import br.com.desafio.orange.talents.entity.DTO.UserDTO;
import br.com.desafio.orange.talents.repository.UserRepository;
import br.com.desafio.orange.talents.service.exception.ValidationException;

@Service
public class UserService {
	
	UserRepository repository;
	ComicsService comicsService;

	@Autowired
	public UserService(UserRepository repository, ComicsService comicsService) {
		this.repository = repository;
		this.comicsService = comicsService;
	}	
	
	public User saveUser(User user) {
		if(user.getDateOfBirth()==null) throw new ValidationException("Precisa informar a data de aniversário no padrão yyyy-MM-dd");
		return repository.save(user);
	}

	public UserDTO findById(Long id) {
		Optional<User> userOpt = repository.findById(id);
		return new UserDTO(userOpt.get());
	}
	
	public UserDTO findByEmail(String email) {		
		Optional<User> userOpt = repository.findByEmail(email);
		return new UserDTO(userOpt.get());
	}
	
	public User addComicByIds(Long idUser, Long idComic) {
		User user = repository.findById(idUser).get();
		Comic comic = comicsService.findById(idComic).get();
		user.AddComic(comic);
		return repository.save(user);		
	}
		
}
