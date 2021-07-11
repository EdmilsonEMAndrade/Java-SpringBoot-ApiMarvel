package br.com.desafio.orange.talents.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="Comics", schema="talents")
public class Comic implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Comic code coming from the Marvel API.")
	@Id
	private Long id;
	
	private String title;
	@Column(length=1000)
	private String description;
	private String isbn;
	
	@OneToMany(mappedBy = "comic", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Prices> prices = new HashSet<>();	
	
	@ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Creator> creators = new HashSet<>();
	
	@JsonIgnore
	@ManyToMany( mappedBy = "comics")
	private Set<User> users = new HashSet<>();	

	public Comic() {}	
	
	public Comic(Long id, String title, String description, String isbn, Set<Creator> creators) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.isbn = isbn;
		this.creators = creators;
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

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}		

	public Set<Prices> getPrices() {
		return prices;
	}

	public void setPrices(Set<Prices> prices) {
		this.prices = prices;
	}

	public Set<Creator> getCreators() {
		return creators;
	}

	public void setCreators(Set<Creator> creators) {
		this.creators = creators;
	}
	
	public void addCreators(Creator creator) {
		this.creators.add(creator);
	}	

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
}
