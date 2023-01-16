package profitsoft.homework.fifth.model.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class FilmSearchDto {
    private String title;

    @DecimalMin(value = "0")
    private BigDecimal price;
}

