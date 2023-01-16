package profitsoft.homework.fifth.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class FilmCategoryInfoDto {
    private Integer id;
    private String genreName;
}
