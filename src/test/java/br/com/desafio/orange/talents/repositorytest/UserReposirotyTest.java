package br.com.desafio.orange.talents.repositorytest;

import br.com.desafio.orange.talents.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.desafio.orange.talents.repository.UserRepository;

import javax.validation.ConstraintViolationException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserReposirotyTest {
	
	@Autowired
	UserRepository repository;

	@Test
	public void findByEmailTest(){
		User user = new User();
		user.setName("User Test");
		user.setCpf("02194693009");
		user.setEmail("test@mail.com");
		user.setDateOfBirth(LocalDate.now());
		repository.save(user);
		assertTimeout(Duration.ofSeconds(1), ()->{
			Optional<User> entityOpt = repository.findByEmail("test@mail.com");
			Assertions.assertThat(entityOpt).isNotEmpty();
			User entity = entityOpt.get();
			assertNotNull(entity.getId());
			assertTrue(entity.getName().equals("User Test"));
		});
	}

	@Test
	public void errorRegister(){
		User user = new User();
		user.setDateOfBirth(LocalDate.now());
		assertTimeout(Duration.ofSeconds(1), ()->{
			assertThrows(ConstraintViolationException.class, ()->{
				repository.save(user);
			});
		});
	}

}
