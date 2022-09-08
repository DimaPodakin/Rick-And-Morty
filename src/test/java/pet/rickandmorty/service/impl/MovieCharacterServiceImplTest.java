package pet.rickandmorty.service.impl;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pet.rickandmorty.dto.external.ApiCharacterDto;
import pet.rickandmorty.dto.external.ApiInfoDto;
import pet.rickandmorty.dto.external.ApiResponseDto;
import pet.rickandmorty.dto.mapper.MovieCharacterMapper;
import pet.rickandmorty.model.Gender;
import pet.rickandmorty.model.MovieCharacter;
import pet.rickandmorty.model.Status;
import pet.rickandmorty.repository.MovieCharacterRepository;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MovieCharacterServiceImplTest {
    @InjectMocks
    private MovieCharacterServiceImpl service;
    @Mock
    private MovieCharacterRepository repository;
    @Mock
    private MovieCharacterMapper mapper;
    private MovieCharacter rickSanchez;
    private ApiCharacterDto apiCharacterDto;
    private ApiResponseDto apiResponseDto;

    @BeforeEach
    void setUp() {
        rickSanchez = new MovieCharacter();
        rickSanchez.setId(1L);
        rickSanchez.setExternalId(1L);
        rickSanchez.setName("Rick Sanchez");
        rickSanchez.setStatus(Status.ALIVE);
        rickSanchez.setGender(Gender.MALE);

        apiCharacterDto = new ApiCharacterDto();
        apiCharacterDto.setId(1L);
        apiCharacterDto.setName("Rick Sanchez");
        apiCharacterDto.setStatus("Alive");
        apiCharacterDto.setGender("Male");


        ApiInfoDto infoDto = new ApiInfoDto();
        infoDto.setCount(1);
        infoDto.setPage(1);
        infoDto.setPrev(null);
        infoDto.setNext(null);

        apiResponseDto = new ApiResponseDto();
        apiResponseDto.setInfo(infoDto);
        apiResponseDto.setResults(new ApiCharacterDto[] {apiCharacterDto});
    }

    @Test
    void saveDtosToDB_ok() {
        Mockito.when(repository.findAllByExternalIdIn(any())).thenReturn(Collections.emptyList());
        Mockito.when(mapper.parseApiCharacterResponseDto(apiCharacterDto)).thenReturn(rickSanchez);
        Assertions.assertEquals(1L, service.saveDtosToDB(apiResponseDto).size());
        Assertions.assertEquals("Rick Sanchez", service.saveDtosToDB(apiResponseDto).get(0).getName());
    }
}
