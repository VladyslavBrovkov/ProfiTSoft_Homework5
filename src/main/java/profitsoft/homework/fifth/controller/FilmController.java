package profitsoft.homework.fifth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import profitsoft.homework.fifth.model.dto.FilmInfoDto;
import profitsoft.homework.fifth.model.dto.FilmSaveDto;
import profitsoft.homework.fifth.model.dto.FilmSearchDto;
import profitsoft.homework.fifth.model.dto.RestResponse;
import profitsoft.homework.fifth.service.FilmService;

import java.util.Set;

@RestController
@RequestMapping("/api/film")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse createFilm(@Valid @RequestBody FilmSaveDto filmSaveDto) {
        Integer id = filmService.createFilm(filmSaveDto);
        return new RestResponse(String.valueOf(id));
    }

    @PutMapping("/update/{id}")
    public RestResponse updateFilm(@PathVariable Integer id,
                                   @Valid @RequestBody FilmSaveDto filmSaveDto) {
        Integer updatedFilmId = filmService.updateFilm(id, filmSaveDto);
        return new RestResponse("Film with id=%d updated".formatted(updatedFilmId));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<FilmInfoDto> getFilmById(@PathVariable Integer id) {
        return ResponseEntity.ok(filmService.findFilmById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Set<FilmInfoDto>> getAllFilms() {
        return ResponseEntity.ok(filmService.getAllFilms());
    }


    @GetMapping("/getByPage/{page}/{size}")
    public ResponseEntity<Page<FilmInfoDto>> getFilmsByPage(@PathVariable Integer page,
                                                            @PathVariable Integer size,
                                                            @Valid @RequestBody FilmSearchDto filmSearchDto) {
        return ResponseEntity.ok(filmService.findFilmWithPagination(filmSearchDto, page, size));
    }

    @DeleteMapping("/deleteById/{id}")
    public RestResponse deleteFilm(@PathVariable Integer id) {
        filmService.deleteFilmById(id);
        return new RestResponse("Film with id=%d deleted".formatted(id));
    }
}
