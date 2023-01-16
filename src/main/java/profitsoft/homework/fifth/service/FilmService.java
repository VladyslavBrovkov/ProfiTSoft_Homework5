package profitsoft.homework.fifth.service;

import org.springframework.data.domain.Page;
import profitsoft.homework.fifth.model.dto.FilmInfoDto;
import profitsoft.homework.fifth.model.dto.FilmSaveDto;
import profitsoft.homework.fifth.model.dto.FilmSearchDto;

import java.util.Set;

public interface FilmService {
    Integer createFilm(FilmSaveDto filmSaveDto);

    Integer updateFilm(Integer filmId, FilmSaveDto filmDto);

    void deleteFilmById(Integer filmId);

    FilmInfoDto findFilmById(Integer filmId);

    Page<FilmInfoDto> findFilmWithPagination(FilmSearchDto filmSearchDto);

    Set<FilmInfoDto> getAllFilms();

}
