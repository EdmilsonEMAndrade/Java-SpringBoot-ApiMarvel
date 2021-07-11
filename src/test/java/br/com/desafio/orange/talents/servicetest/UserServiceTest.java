package br.com.desafio.orange.talents.servicetest;

import br.com.desafio.orange.talents.controller.ComicController;
import br.com.desafio.orange.talents.entity.User;
import br.com.desafio.orange.talents.service.UserService;
import br.com.desafio.orange.talents.service.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

	UserService service;
	ComicController comicsController;

	@Autowired
    public UserServiceTest(UserService service, ComicController comicsController) {
        this.service = service;
        this.comicsController = comicsController;
    }

    @Test
	public void errorRegisterValidationException(){
		User user = new User();
		assertTimeout(Duration.ofSeconds(1), ()->{
			assertThrows(ValidationException.class, ()->{
				service.saveUser(user);
			});
		});
	}

	@Test
	public void errorRegisterConstraintViolation(){
		User user = new User();
		user.setDateOfBirth(LocalDate.now());
		assertTimeout(Duration.ofSeconds(1), ()->{
			assertThrows(ConstraintViolationException.class, ()->{
				service.saveUser(user);
			});
		});
	}

	@Test
	public void saveUser(){
		User user = new User();
		user.setName("User Test");
		user.setCpf("50013658050");
		user.setEmail("saveuser@mail.com");
		user.setDateOfBirth(LocalDate.now());
        assertTimeout(Duration.ofSeconds(1), ()->{
			User entity = service.saveUser(user);
			assertNotNull(entity.getId());
		});
	}

	@Test
	public void errorRegisterDataIntegrityViolationCPF(){
		User user = new User();
		user.setName("User Test");
		user.setCpf("50013658050");
		user.setEmail("testuser@mail.com");
		user.setDateOfBirth(LocalDate.now());

		assertTimeout(Duration.ofSeconds(1), ()->{
            assertThrows(DataIntegrityViolationException.class, ()->{
                service.saveUser(user);
            });
		});
	}

    @Test
    public void errorRegisterDataIntegrityViolationEmail(){
        User user = new User();
        user.setName("User Test");
        user.setCpf("76728145087");
        user.setEmail("saveuser@mail.com");
        user.setDateOfBirth(LocalDate.now());

        assertTimeout(Duration.ofSeconds(1), ()->{
            assertThrows(DataIntegrityViolationException.class, ()->{
                service.saveUser(user);
            });
        });
    }

    @Test
    public void addingComics(){
        User user = new User();
        user.setName("User Comic");
        user.setCpf("76728145087");
        user.setEmail("addingComicsServiceTest@mail.com");
        user.setDateOfBirth(LocalDate.now());
        User userEntity = service.saveUser(user);
        Long idComic = 73425L;
        comicsController.saveById(idComic);

        assertTimeout(Duration.ofSeconds(1), ()->{
            assertTrue(service.findById(userEntity.getId()).getComicsDto().size()==0);
            service.addComicByIds(userEntity.getId(), idComic );
            assertTrue(service.findByEmail(userEntity.getEmail()).getComicsDto().size()==1);
        });
    }

    @Test
    public void errorAddingComicsInvalidDataAccess(){
        User user = new User();
        user.setName("User Comic2");
        user.setCpf("80219911002");
        user.setEmail("errorAddingComicsInvalidDataAccess@mail.com");
        user.setDateOfBirth(LocalDate.now());
        User userEntity = service.saveUser(user);
        Long idComic = 82967L;
        comicsController.saveById(idComic);
        service.addComicByIds(userEntity.getId(), idComic );
        assertTimeout(Duration.ofSeconds(1), ()->{
                    assertThrows(InvalidDataAccessApiUsageException.class, ()-> {
                        service.addComicByIds(userEntity.getId(), idComic);
                    });
        });
    }

}
