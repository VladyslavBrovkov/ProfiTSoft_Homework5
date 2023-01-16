package profitsoft.homework.fifth.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Builder
@Jacksonized
public class FilmInfoDto {
    private Integer id;
    private String title;
    private BigDecimal price;
    private Integer filmCategoryId;
}
