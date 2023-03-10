package profitsoft.homework.fifth.model.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Setter
@Jacksonized
@Builder
public class FilmSearchDto {
    private int page;

    private int size;

    private String title;

    @DecimalMin(value = "0")
    private BigDecimal price;
}

