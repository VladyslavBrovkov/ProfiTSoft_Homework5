package profitsoft.homework.fifth.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import profitsoft.homework.fifth.model.Film;
import profitsoft.homework.fifth.model.FilmCategory;
import profitsoft.homework.fifth.model.dto.FilmInfoDto;
import profitsoft.homework.fifth.model.dto.FilmSaveDto;
import profitsoft.homework.fifth.model.dto.FilmSearchDto;
import profitsoft.homework.fifth.repository.FilmCategoryRepository;
import profitsoft.homework.fifth.repository.FilmRepository;
import profitsoft.homework.fifth.service.FilmService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    private final FilmCategoryRepository filmCategoryRepository;

    @Override
    @Transactional
    public Integer createFilm(FilmSaveDto filmSaveDto) {
        Film film = filmFromDto(filmSaveDto, checkCategoryOrThrow(filmSaveDto.getFilmCategoryId()));
        return filmRepository.save(film).getId();
    }

    @Override
    @Transactional
    public Integer updateFilm(Integer filmId, FilmSaveDto filmDto) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("Film with id %d now found".formatted(filmId)));
        Integer categoryId = filmDto.getFilmCategoryId();
        FilmCategory filmCategory = filmCategoryRepository.findById(filmDto.getFilmCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("FilmCategory with id %d not found".formatted(categoryId)));
        film.setTitle(filmDto.getTitle());
        film.setPrice(filmDto.getPrice());
        film.setFilmCategory(filmCategory);
        return filmRepository.save(film).getId();
    }


    @Override
    @Transactional
    public void deleteFilmById(Integer filmId) {
        var film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("No Film with id=%d".formatted(filmId)));
        filmRepository.delete(film);
    }

    @Override
    @Transactional(readOnly = true)
    public FilmInfoDto findFilmById(Integer filmId) {
        var film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("Film with id=%d not found".formatted(filmId)));
        return toFilmInfoDto(film);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FilmInfoDto> findFilmWithPagination(FilmSearchDto filmSearchDto, Integer page, Integer size) {
        int defaultSize = 3;
        PageRequest pageRequest = PageRequest.of(page, size < 1 ? defaultSize : size);
        Page<Film> filmPage = constructFilmPage(filmSearchDto, pageRequest);
        return filmPage.map(this::toFilmInfoDto);
    }

    private Page<Film> constructFilmPage(FilmSearchDto filmSearchDto, PageRequest pageRequest) {
        if (filmSearchDto.getTitle() != null || filmSearchDto.getPrice() != null) {
            return filmRepository.findAll(specifyPageByParams(filmSearchDto), pageRequest);
        }
        return filmRepository.findAll(pageRequest);
    }

    private Specification<Film> specifyPageByParams(FilmSearchDto filmSearchDto) {
        String title = filmSearchDto.getTitle();
        BigDecimal price = filmSearchDto.getPrice();
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> paramsList = new ArrayList<>();
            if (title != null) {
                paramsList.add(criteriaBuilder.equal(root.get("title"), title));
            }
            if (price != null) {
                paramsList.add(criteriaBuilder.equal(root.get("price"), price));
            }
            return criteriaBuilder.and(paramsList.toArray(Predicate[]::new));
        };
    }

    @Override
    @Transactional(readOnly = true)
    public Set<FilmInfoDto> getAllFilms() {
        return filmRepository.findAll().stream()
                .sorted(Comparator.comparing(Film::getId))
                .map(this::toFilmInfoDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Film filmFromDto(FilmSaveDto filmSaveDto, FilmCategory filmCategory) {
        Film film = new Film();
        film.setTitle(filmSaveDto.getTitle());
        film.setPrice(filmSaveDto.getPrice());
        film.setFilmCategory(filmCategory);
        return film;
    }

    private FilmInfoDto toFilmInfoDto(Film film) {
        return FilmInfoDto.builder()
                .id(film.getId())
                .title(film.getTitle())
                .price(film.getPrice())
                .filmCategoryId(film.getFilmCategory().getId())
                .build();
    }

    private FilmCategory checkCategoryOrThrow(Integer categoryId) {
        return filmCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("FilmCategory with id %d not found".formatted(categoryId)));
    }

}
