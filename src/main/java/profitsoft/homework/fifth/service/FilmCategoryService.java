package profitsoft.homework.fifth.service;

import profitsoft.homework.fifth.model.dto.FilmCategoryInfoDto;

import java.util.Set;

public interface FilmCategoryService {
    Set<FilmCategoryInfoDto> getAllFilmCategories();
}
