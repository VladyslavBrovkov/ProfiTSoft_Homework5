package profitsoft.homework.fifth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ProfiTSoftApplication.class)
@AutoConfigureMockMvc
public class FilmCategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getAllFilmCategoriesTest() throws Exception {
        mvc
                .perform(get("/api/filmCategory/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].genreName").value("HORROR"));
    }

}
