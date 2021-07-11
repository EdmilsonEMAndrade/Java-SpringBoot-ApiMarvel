package br.com.desafio.orange.talents.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="Users", schema="talents")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Registration code in the database.")
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Need to enter the name.")
	private String name;
	
	@NotEmpty(message = "Need to enter the e-mail.")
	@Email(message = "Enter a valid email address.")
	@Column(unique = true)
	private String email;
	
	@NotEmpty(message = "Need to enter the CPF.")
	@CPF(message = "Enter a valid CPF")
	@Column(unique = true)
	private String cpf;
	
	@Column(columnDefinition = "DATE")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	@ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Comic> comics = new HashSet<>();
	
	public User() {}

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate  getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Set<Comic> getComics() {
		return comics;
	}

	public void setComics(Set<Comic> comics) {
		this.comics = comics;
	}
	
	public void AddComic(Comic comic) {
		this.comics.add(comic);
	}

}
