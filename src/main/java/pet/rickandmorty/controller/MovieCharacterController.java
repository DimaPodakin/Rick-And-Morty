package pet.rickandmorty.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pet.rickandmorty.dto.CharacterResponseDto;
import pet.rickandmorty.dto.mapper.MovieCharacterMapper;
import pet.rickandmorty.model.MovieCharacter;
import pet.rickandmorty.service.MovieCharacterService;
import pet.rickandmorty.service.sort.SortParse;

@RequestMapping("/movie-character")
@RestController
@RequiredArgsConstructor
public class MovieCharacterController {
    private final MovieCharacterMapper mapper;
    private final MovieCharacterService characterService;

    @GetMapping("/random")
    @ApiOperation(value = "Find random character from DB")
    public CharacterResponseDto getRandomCharacter() {
        MovieCharacter randomCharacter = characterService.getRandomCharacter();
        return mapper.toDto(randomCharacter);
    }

    @GetMapping("/by-name")
    @ApiOperation(value = "Find characters whose names contain some letters")
    public List<CharacterResponseDto> findAllByName(
            @RequestParam("name") @ApiParam(value = "Input part of name") String namePart,
            @RequestParam (defaultValue = "10") @ApiParam(value = "10 by default") Integer count,
            @RequestParam (defaultValue = "1") @ApiParam(value = "1 by default") Integer page,
            @RequestParam (defaultValue = "id") @ApiParam(value = "id by default") String sort) {
        PageRequest pageRequest = PageRequest.of((page - 1), count, SortParse.parse(sort));
        return characterService.findAllByNameContains(namePart, pageRequest)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find the character by id")
    public CharacterResponseDto findById(@PathVariable Long id) {
        MovieCharacter character = characterService.findById(id);
        return mapper.toDto(character);
    }

    @GetMapping
    @ApiOperation(value = "Find all characters from DB")
    public List<CharacterResponseDto> findAll(
            @RequestParam (defaultValue = "10") @ApiParam(value = "10 by default") Integer count,
            @RequestParam (defaultValue = "1") @ApiParam(value = "1 by default") Integer page,
            @RequestParam (defaultValue = "id") @ApiParam(value = "id by default") String sort) {
        PageRequest pageRequest = PageRequest.of((page - 1), count, SortParse.parse(sort));
        return characterService.findAll(pageRequest)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
