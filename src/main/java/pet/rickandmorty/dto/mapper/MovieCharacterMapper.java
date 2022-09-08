package pet.rickandmorty.dto.mapper;

import org.springframework.stereotype.Component;
import pet.rickandmorty.dto.CharacterResponseDto;
import pet.rickandmorty.dto.external.ApiCharacterDto;
import pet.rickandmorty.model.Gender;
import pet.rickandmorty.model.MovieCharacter;
import pet.rickandmorty.model.Status;

@Component
public class MovieCharacterMapper {
    public MovieCharacter parseApiCharacterResponseDto(ApiCharacterDto dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setName(dto.getName());
        movieCharacter.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        movieCharacter.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        movieCharacter.setExternalId(dto.getId());
        return movieCharacter;
    }

    public CharacterResponseDto toDto(MovieCharacter movieCharacter) {
        CharacterResponseDto responseDto = new CharacterResponseDto();
        responseDto.setId(movieCharacter.getId());
        responseDto.setExternalId(movieCharacter.getExternalId());
        responseDto.setName(movieCharacter.getName());
        responseDto.setStatus(movieCharacter.getStatus());
        responseDto.setGender(movieCharacter.getGender());
        return responseDto;
    }
}
