package profitsoft.homework.fifth.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Builder
@Jacksonized
public class FilmSaveDto {
    @NotBlank(message = "Film's title is required")
    private String title;
    @NotNull(message = "Film's price is required")
    private BigDecimal price;
    @NotNull(message = "FilmCategory's Id is required")
    private Integer filmCategoryId;
}
