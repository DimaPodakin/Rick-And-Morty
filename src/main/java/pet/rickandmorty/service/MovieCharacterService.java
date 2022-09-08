package pet.rickandmorty.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import pet.rickandmorty.model.MovieCharacter;

public interface MovieCharacterService {
    void syncExternalCharacters();

    MovieCharacter getRandomCharacter();

    MovieCharacter findById(Long id);

    List<MovieCharacter> findAll(PageRequest pageRequest);

    List<MovieCharacter> findAllByNameContains(String namePart, PageRequest pageRequest);
}
