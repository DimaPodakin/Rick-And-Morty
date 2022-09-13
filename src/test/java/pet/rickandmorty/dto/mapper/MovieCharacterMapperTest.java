package pet.rickandmorty.dto.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pet.rickandmorty.dto.CharacterResponseDto;
import pet.rickandmorty.dto.external.ApiCharacterDto;
import pet.rickandmorty.model.Gender;
import pet.rickandmorty.model.MovieCharacter;
import pet.rickandmorty.model.Status;

@ExtendWith(MockitoExtension.class)
class MovieCharacterMapperTest {
    @InjectMocks
    private MovieCharacterMapper mapper;
    private MovieCharacter character;
    private ApiCharacterDto characterDto;

    @BeforeEach
    void setUp() {
        character = new MovieCharacter();
        character.setId(1L);
        character.setExternalId(1L);
        character.setName("Rick");
        character.setStatus(Status.ALIVE);
        character.setGender(Gender.MALE);

        characterDto = new ApiCharacterDto();
        characterDto.setId(1L);
        characterDto.setName("Rick");
        characterDto.setStatus("Alive");
        characterDto.setGender("Male");
    }

    @Test
    void parseApiCharacterResponseDto_ok() {
        MovieCharacter actual = mapper.toModel(characterDto);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1L, actual.getExternalId());
        Assertions.assertEquals("Rick", actual.getName());
        Assertions.assertEquals(Status.ALIVE, actual.getStatus());
        Assertions.assertEquals(Gender.MALE, actual.getGender());
    }

    @Test
    void toDto() {
        CharacterResponseDto actual = mapper.toDto(character);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1L, actual.getId());
        Assertions.assertEquals(1L, actual.getExternalId());
        Assertions.assertEquals("Rick", actual.getName());
        Assertions.assertEquals(Status.ALIVE, actual.getStatus());
        Assertions.assertEquals(Gender.MALE, actual.getGender());
    }
}
