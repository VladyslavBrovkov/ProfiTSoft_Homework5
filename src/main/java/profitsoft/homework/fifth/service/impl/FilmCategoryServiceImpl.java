package profitsoft.homework.fifth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import profitsoft.homework.fifth.model.FilmCategory;
import profitsoft.homework.fifth.model.dto.FilmCategoryInfoDto;
import profitsoft.homework.fifth.repository.FilmCategoryRepository;
import profitsoft.homework.fifth.service.FilmCategoryService;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmCategoryServiceImpl implements FilmCategoryService {

    private final FilmCategoryRepository filmCategoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Set<FilmCategoryInfoDto> getAllFilmCategories() {
        System.out.println(filmCategoryRepository.findAll());
        return filmCategoryRepository.findAll().stream()
                .sorted(Comparator.comparing(FilmCategory::getId))
                .map(this::toDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private FilmCategoryInfoDto toDto(FilmCategory filmCategory) {
        return FilmCategoryInfoDto.builder()
                .id(filmCategory.getId())
                .genreName(filmCategory.getGenreName())
                .build();
    }
}
