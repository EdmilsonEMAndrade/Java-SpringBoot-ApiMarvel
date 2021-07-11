package br.com.desafio.orange.talents.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="Creators", schema="talents")
public class Creator implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Código do autor vindo da API")
	@Id	
	private Long id;
	
	private String fullName;
	
	@JsonIgnore
	@ManyToMany( mappedBy = "creators")
	private Set<Comic> comics = new HashSet<>();
	
	public Creator() {}	

	public Creator(Long id, String fullName) {		
		this.id = id;
		this.fullName = fullName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
