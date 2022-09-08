package pet.rickandmorty.dto;

import lombok.Data;
import pet.rickandmorty.model.Gender;
import pet.rickandmorty.model.Status;

@Data
public class CharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
}
