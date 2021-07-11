package br.com.desafio.orange.talents.entity.DTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import br.com.desafio.orange.talents.entity.Comic;
import br.com.desafio.orange.talents.entity.User;

public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;	
	private String email;
	private Set<ComicListDTO> comicsDto = new HashSet<>();

	public UserDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		setComicsDto(user.getComics());
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

	public Set<ComicListDTO> getComicsDto() {
		return comicsDto;
	}

	public void setComicsDto(Set<Comic> comics) {
		for(Comic comic : comics) {
			comicsDto.add(new ComicListDTO(comic));
		}
	}
	
}
