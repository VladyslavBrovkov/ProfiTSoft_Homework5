package profitsoft.homework.fifth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import profitsoft.homework.fifth.model.Film;
import profitsoft.homework.fifth.model.dto.FilmSearchDto;
import profitsoft.homework.fifth.model.dto.RestResponse;
import profitsoft.homework.fifth.repository.FilmRepository;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ProfiTSoftApplication.class)
@AutoConfigureMockMvc
public class FilmControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FilmRepository filmRepository;


    @Test
    public void createFilmTest() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/api/film/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonTestFilm())
                )
                .andExpect(status().isCreated())
                .andReturn();

        RestResponse response = parseResponse(mvcResult, RestResponse.class);
        Integer filmId = Integer.parseInt(response.getResult());
        assertThat(filmId).isGreaterThanOrEqualTo(1);

        Film film = filmRepository.findById(filmId).orElse(null);
        assertThat(film).isNotNull();
        assertThat(film.getTitle()).isEqualTo("Test Film");
        assertThat(film.getPrice()).isEqualTo("10.00");
        assertThat(film.getFilmCategory().getId()).isEqualTo(1);
    }

    @Test
    public void createFilm_validationTest() throws Exception {
        mvc.perform(post("/api/film/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateExistingFilmTest() throws Exception {
        mvc
                .perform(
                        put("/api/film/update/7")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getJsonTestFilm())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.result").value("Film with id=7 updated"));

        Film updatedFilm = filmRepository.findById(7).orElse(null);
        assertThat(updatedFilm).isNotNull();
        assertThat(updatedFilm.getTitle()).isEqualTo("Test Film");
    }

    @Test
    void updateNotExistingFilmTest() throws Exception {
        mvc
                .perform(
                        put("/api/film/update/10")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getJsonTestFilm()))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> {
                    var exceptionClass = Objects.requireNonNull(mvcResult.getResolvedException()).getClass();
                    var booleanResult = exceptionClass.equals(EntityNotFoundException.class);
                    assertThat(booleanResult).isEqualTo(true);
                });
    }

    @Test
    void getFilmWithCorrectIdTest() throws Exception {
        mvc
                .perform(get("/api/film/getById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.title").value("The Texas Chainsaw Massacre"))
                .andExpect(jsonPath("$.price").value("105.56"));
    }

    @Test
    void getFilmWithWrongIdTest() throws Exception {
        mvc
                .perform(get("/api/film/getById/999"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> {
                    var exceptionClass = Objects.requireNonNull(mvcResult.getResolvedException()).getClass();
                    var booleanResult = exceptionClass.equals(EntityNotFoundException.class);
                    assertThat(booleanResult).isEqualTo(true);
                });
    }

    @Test
    void getAllFilms() throws Exception {
        mvc
                .perform(get("/api/film/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("The Texas Chainsaw Massacre"));
    }

    @Test
    void getFilmsWithPaginationNoParamsTest() throws Exception {
        mvc
                .perform(
                        post("/api/film/_getByPage")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(3));
    }

    @Test
    void getFilmsWithPaginationTitleParamTest() throws Exception {
        FilmSearchDto filmSearchDto = FilmSearchDto.builder()
                .title("Commando")
                .build();
        mvc
                .perform(
                        post("/api/film/_getByPage")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(filmSearchDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].title").value("Commando"))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(3));
    }


    @Test
    void deleteFilmWithCorrectIdTest() throws Exception {
        mvc
                .perform(delete("/api/film/deleteById/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("Film with id=7 deleted"));
    }

    @Test
    void deleteFilmWithWrongIdTest() throws Exception {
        mvc
                .perform(delete("/api/film/deleteById/100"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> {
                    var exceptionClass = Objects.requireNonNull(mvcResult.getResolvedException()).getClass();
                    var booleanResult = exceptionClass.equals(EntityNotFoundException.class);
                    assertThat(booleanResult).isEqualTo(true);
                });
    }


    private <T> T parseResponse(MvcResult mvcResult, Class<T> c) {
        try {
            return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), c);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error parsing json", e);
        }
    }

    private String getJsonTestFilm() {
        String title = "Test Film";
        BigDecimal price = new BigDecimal("10.00");
        Integer filmCategoryId = 1;
        return """
                {
                    "title": "%s",
                    "price": "%s",
                    "filmCategoryId": %d
                }
                """.formatted(title, price, filmCategoryId);
    }


}
