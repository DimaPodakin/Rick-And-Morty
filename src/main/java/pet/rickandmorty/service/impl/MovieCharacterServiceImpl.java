package pet.rickandmorty.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pet.rickandmorty.dto.external.ApiCharacterDto;
import pet.rickandmorty.dto.external.ApiResponseDto;
import pet.rickandmorty.dto.mapper.MovieCharacterMapper;
import pet.rickandmorty.model.MovieCharacter;
import pet.rickandmorty.repository.MovieCharacterRepository;
import pet.rickandmorty.service.HttpClient;
import pet.rickandmorty.service.MovieCharacterService;

@Log4j2
@Service
@RequiredArgsConstructor
public class MovieCharacterServiceImpl implements MovieCharacterService {
    @Value("${http.link}")
    private String httpLink;
    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieCharacterMapper movieCharacterMapper;

    @PostConstruct
    @Scheduled(cron = "0 0 8 * * ?")
    @Override
    public void syncExternalCharacters() {
        log.info("syncExternalCharacters method was invoked at " + LocalDateTime.now());
        ApiResponseDto apiResponseDto = httpClient.get(httpLink, ApiResponseDto.class);
        saveDtosToDB(apiResponseDto);
        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(),
                    ApiResponseDto.class);
            saveDtosToDB(apiResponseDto);
        }
    }

    @Override
    public MovieCharacter getRandomCharacter() {
        long count = movieCharacterRepository.count();
        long randomId = (long) (Math.random() * count);
        return movieCharacterRepository.findById(randomId).orElseThrow(
                () -> new NoSuchElementException("Cannot find character by id: " + randomId));
    }

    @Override
    public MovieCharacter findById(Long id) {
        return movieCharacterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot "
                        + "find character by id: " + id));
    }

    @Override
    public List<MovieCharacter> findAll(PageRequest pageRequest) {
        return movieCharacterRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart, PageRequest pageRequest) {
        return movieCharacterRepository.findAllByNameContains(namePart, pageRequest);
    }

    List<MovieCharacter> saveDtosToDB(ApiResponseDto apiResponseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays.stream(apiResponseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));
        Set<Long> externalIds = externalDtos.keySet();

        List<MovieCharacter> existingCharacters = movieCharacterRepository
                .findAllByExternalIdIn(externalIds);
        Map<Long, MovieCharacter> existingCharactersWithIds = existingCharacters.stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));
        Set<Long> existingIds = existingCharactersWithIds.keySet();
        externalIds.removeAll(existingIds);
        List<MovieCharacter> charactersToSave = externalIds.stream()
                .map(i -> movieCharacterMapper.toModel(externalDtos.get(i)))
                .collect(Collectors.toList());
        movieCharacterRepository.saveAll(charactersToSave);
        return charactersToSave;
    }
}
