package br.com.desafio.orange.talents.entity.DTO;

import java.io.Serializable;

import br.com.desafio.orange.talents.entity.Comic;


public class ComicDTO implements Serializable{
	private static final long serialVersionUID = 1L;	

	private Long id;
	private String title;
	private String description;

	public ComicDTO(Comic comic) {
		this.id = comic.getId();
		this.title = comic.getTitle();
		this.description = comic.getDescription();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
		
}
