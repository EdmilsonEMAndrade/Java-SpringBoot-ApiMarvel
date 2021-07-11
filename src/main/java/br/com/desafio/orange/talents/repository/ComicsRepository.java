package br.com.desafio.orange.talents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafio.orange.talents.entity.Comic;

public interface ComicsRepository extends JpaRepository<Comic, Long>{
	
}
