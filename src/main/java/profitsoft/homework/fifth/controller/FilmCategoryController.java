package profitsoft.homework.fifth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import profitsoft.homework.fifth.model.dto.FilmCategoryInfoDto;
import profitsoft.homework.fifth.service.FilmCategoryService;

import java.util.Set;

@RestController
@RequestMapping("/api/filmCategory")
@RequiredArgsConstructor
public class FilmCategoryController {

    private final FilmCategoryService filmCategoryService;

    @GetMapping("/getAll")
    public ResponseEntity<Set<FilmCategoryInfoDto>> getAll() {
        return ResponseEntity.ok(filmCategoryService.getAllFilmCategories());
    }

}
