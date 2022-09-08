package pet.rickandmorty.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pet.rickandmorty.model.Gender;
import pet.rickandmorty.model.MovieCharacter;
import pet.rickandmorty.model.Status;
import pet.rickandmorty.service.MovieCharacterService;

import static org.hibernate.criterion.Restrictions.eq;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MovieCharacterControllerTest {
    @MockBean
    private MovieCharacterService service;
    @Autowired
    private MockMvc mockMvc;
    private MovieCharacter rickSanchez;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        rickSanchez = new MovieCharacter();
        rickSanchez.setId(1L);
        rickSanchez.setExternalId(1L);
        rickSanchez.setName("Rick Sanchez");
        rickSanchez.setStatus(Status.ALIVE);
        rickSanchez.setGender(Gender.MALE);
    }

    @Test
    void getRandomCharacter_ok() {
        Mockito.when(service.getRandomCharacter()).thenReturn(rickSanchez);
        RestAssuredMockMvc.when()
                .get("/movie-character/random")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("externalId", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("Rick Sanchez"))
                .body("status", Matchers.equalTo("ALIVE"))
                .body("gender", Matchers.equalTo("MALE"));
    }

    @Test
    public void findAllByNameContains_ok() {
        String namePart = "Rick";
        MovieCharacter adjudicatorRick = new MovieCharacter();
        adjudicatorRick.setId(8L);
        adjudicatorRick.setExternalId(8L);
        adjudicatorRick.setName("Adjudicator Rick");
        adjudicatorRick.setStatus(Status.DEAD);
        adjudicatorRick.setGender(Gender.MALE);

        List<MovieCharacter> characters = List.of(rickSanchez, adjudicatorRick);

        Mockito.when(service.findAllByNameContains(ArgumentMatchers.eq(namePart), any())).thenReturn(characters);
        RestAssuredMockMvc.given()
                .queryParam("name", namePart)
                .when()
                .get("/movie-character/by-name")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(2))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].externalId", Matchers.equalTo(1))
                .body("[0].name", Matchers.equalTo("Rick Sanchez"))
                .body("[0].status", Matchers.equalTo("ALIVE"))
                .body("[0].gender", Matchers.equalTo("MALE"))
                .body("[1].id", Matchers.equalTo(8))
                .body("[1].externalId", Matchers.equalTo(8))
                .body("[1].name", Matchers.equalTo("Adjudicator Rick"))
                .body("[1].status", Matchers.equalTo("DEAD"))
                .body("[1].gender", Matchers.equalTo("MALE"));
    }

    @Test
    void findById_ok() {
        Long id = rickSanchez.getId();
        Mockito.when(service.findById(id)).thenReturn(rickSanchez);
        RestAssuredMockMvc.when()
                .get("/movie-character/" + id)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("externalId", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("Rick Sanchez"))
                .body("status", Matchers.equalTo("ALIVE"))
                .body("gender", Matchers.equalTo("MALE"));
    }

    @Test
    void findAll_ok() {
        MovieCharacter adjudicatorRick = new MovieCharacter();
        adjudicatorRick.setId(8L);
        adjudicatorRick.setExternalId(8L);
        adjudicatorRick.setName("Adjudicator Rick");
        adjudicatorRick.setStatus(Status.DEAD);
        adjudicatorRick.setGender(Gender.MALE);

        List<MovieCharacter> characters = List.of(rickSanchez, adjudicatorRick);

        Mockito.when(service.findAll(any())).thenReturn(characters);
        RestAssuredMockMvc.when()
                .get("/movie-character")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(2))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].externalId", Matchers.equalTo(1))
                .body("[0].name", Matchers.equalTo("Rick Sanchez"))
                .body("[0].status", Matchers.equalTo("ALIVE"))
                .body("[0].gender", Matchers.equalTo("MALE"))
                .body("[1].id", Matchers.equalTo(8))
                .body("[1].externalId", Matchers.equalTo(8))
                .body("[1].name", Matchers.equalTo("Adjudicator Rick"))
                .body("[1].status", Matchers.equalTo("DEAD"))
                .body("[1].gender", Matchers.equalTo("MALE"));
    }
}
