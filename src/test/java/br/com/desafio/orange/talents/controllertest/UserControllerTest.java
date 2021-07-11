package br.com.desafio.orange.talents.controllertest;


import br.com.desafio.orange.talents.controller.ComicController;
import br.com.desafio.orange.talents.controller.UserController;
import br.com.desafio.orange.talents.entity.User;
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
public class UserControllerTest {

    UserController controller;
    ComicController comicController;

    @Autowired
    public UserControllerTest(UserController controller, ComicController comicController) {
        this.controller = controller;
        this.comicController = comicController;
    }

    @Test
	public void errorRegisterValidationException(){
		User user = new User();
		assertTimeout(Duration.ofSeconds(1), ()->{
			assertThrows(ValidationException.class, ()->{
                controller.saveUser(user);
			});
		});
	}

	@Test
	public void errorRegisterConstraintViolation(){
		User user = new User();
		user.setDateOfBirth(LocalDate.now());
		assertTimeout(Duration.ofSeconds(1), ()->{
			assertThrows(ConstraintViolationException.class, ()->{
                controller.saveUser(user);
			});
		});
	}

	@Test
	public void saveUser(){
		User user = new User();
		user.setName("User Test");
		user.setCpf("54942941024");
		user.setEmail("testuser2@mail.com");
		user.setDateOfBirth(LocalDate.now());
        assertTimeout(Duration.ofSeconds(1), ()->{
			controller.saveUser(user);
		});
	}

	@Test
	public void errorRegisterDataIntegrityViolationCPF(){
		User user = new User();
		user.setName("User Test");
		user.setCpf("54942941024");
		user.setEmail("testuser@mail.com");
		user.setDateOfBirth(LocalDate.now());

		assertTimeout(Duration.ofSeconds(1), ()->{
            assertThrows(DataIntegrityViolationException.class, ()->{
                controller.saveUser(user);
            });
		});
	}

    @Test
    public void errorRegisterDataIntegrityViolationEmail(){
        User user = new User();
        user.setName("User Test");
        user.setCpf("75988159095");
        user.setEmail("testuser2@mail.com");
        user.setDateOfBirth(LocalDate.now());

        assertTimeout(Duration.ofSeconds(1), ()->{
            assertThrows(DataIntegrityViolationException.class, ()->{
                controller.saveUser(user);
            });
        });
    }

    @Test
    public void addingComic(){
        User user = new User();
        user.setName("User Comic");
        user.setCpf("75988159095");
        user.setEmail("addingComic@mail.com");
        user.setDateOfBirth(LocalDate.now());
        controller.saveUser(user);
        Long idComic = 1689L;
        comicController.saveById(idComic);
        user.setId(controller.findUserByEmail("addingComic@mail.com").getBody().getId());

        assertTimeout(Duration.ofSeconds(1), ()->{
            assertTrue(controller.findUserById(user.getId()).getBody().getComicsDto().size()==0);
            controller.addComicByIds(user.getId(), idComic );
            assertTrue(controller.findUserById(user.getId()).getBody().getComicsDto().size()==1);
        });
    }

    @Test
    public void addingComicsUser1(){
        comicController.saveComicsByPage(201,100);
        assertTimeout(Duration.ofSeconds(1), ()-> {
            controller.addComicByIds(1L, 33052L);
        });
        assertTimeout(Duration.ofSeconds(1), ()-> {
            controller.findUserById(1L);
        });
    }

    @Test
    public void errorAddingComicsInvalidDataAccess(){
        User user = new User();
        user.setName("User Comic2");
        user.setCpf("32896554076");
        user.setEmail("testcomicuser@mail.com");
        user.setDateOfBirth(LocalDate.now());
        controller.saveUser(user);
        Long idComic = 1308L;
        comicController.saveById(idComic);
        user.setId(controller.findUserByEmail("testcomicuser@mail.com").getBody().getId());
        controller.addComicByIds(user.getId(), idComic );
        assertTimeout(Duration.ofSeconds(1), ()->{
            assertThrows(InvalidDataAccessApiUsageException.class, ()->{
                controller.addComicByIds(user.getId(), idComic );
            });
        });
    }

}
