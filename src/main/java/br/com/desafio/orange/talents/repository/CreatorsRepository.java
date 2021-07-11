package br.com.desafio.orange.talents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafio.orange.talents.entity.Creator;

public interface CreatorsRepository extends JpaRepository<Creator, Long>{
	
}
