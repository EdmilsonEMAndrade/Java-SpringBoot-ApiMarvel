package br.com.desafio.orange.talents.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.desafio.orange.talents.entity.enums.TypePrice;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="Prices", schema="talents")
public class Prices implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;	
	
	private Integer type;
	
	private Double price;
	
	@ManyToOne
	@JsonIgnore
	private Comic comic;

	public Prices() {}

	public Prices(Integer type, Double price, Comic comic) {
		this.type = type;
		this.price = price;
		this.comic = comic;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypePrice getType() {
		return TypePrice.toEnum(type);
	}

	public void setType(TypePrice type) {
		this.type = type.getCod();
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Comic getComic() {
		return comic;
	}

	public void setComic(Comic comic) {
		this.comic = comic;
	}

}
