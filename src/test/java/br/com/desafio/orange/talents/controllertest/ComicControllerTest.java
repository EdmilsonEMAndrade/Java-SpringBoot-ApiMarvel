package br.com.desafio.orange.talents.controllertest;

import br.com.desafio.orange.talents.controller.ComicController;
import br.com.desafio.orange.talents.entity.Comic;
import br.com.desafio.orange.talents.service.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ComicControllerTest {

    ComicController controller;

    @Autowired
    public ComicControllerTest(ComicController controller) {
        this.controller = controller;
    }

    @Test
    public void errorHttpClientErrorException(){
        assertTimeout(Duration.ofSeconds(1), ()->{
            assertThrows(HttpClientErrorException.class, ()->{
                controller.saveById(12345878L);
            });
        });
    }

    @Test
    public void saveComicByID(){
        assertTimeout(Duration.ofSeconds(1), ()->{
            controller.saveById(77264L);
        });
    }

    @Test
    public void findComicByID(){
        controller.saveById(59897L);
        assertTimeout(Duration.ofSeconds(1), ()->{
            Comic comic = controller.findById(59897L).getBody();
            assertFalse(comic.getTitle().isBlank());
        });
    }

    @Test
    public void saveComicByPage(){
            controller.saveComicsByPage(1,20);
    }

    @Test
    public void errorValidationException(){
        assertTimeout(Duration.ofSeconds(1), ()->{
            assertThrows(ValidationException.class, ()-> {
                controller.saveComicsByPage(1, 150);
            });
        });
        assertTimeout(Duration.ofSeconds(1), ()->{
            assertThrows(ValidationException.class, ()-> {
                controller.saveComicsByPage(0, 5);
            });
        });
        assertTimeout(Duration.ofSeconds(1), ()->{
            assertThrows(ValidationException.class, ()-> {
                controller.saveComicsByPage(5000, 100);
            });
        });
        assertTimeout(Duration.ofSeconds(1), ()->{
            assertThrows(ValidationException.class, ()-> {
                controller.saveComicsByPage(20, 0 );
            });
        });
    }

}
