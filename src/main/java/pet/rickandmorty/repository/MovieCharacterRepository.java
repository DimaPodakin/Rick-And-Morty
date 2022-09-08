package pet.rickandmorty.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import pet.rickandmorty.model.MovieCharacter;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {
    List<MovieCharacter> findAllByExternalIdIn(Set<Long> externalIds);

    List<MovieCharacter> findAllByNameContains(String namePart, PageRequest pageRequest);

}
