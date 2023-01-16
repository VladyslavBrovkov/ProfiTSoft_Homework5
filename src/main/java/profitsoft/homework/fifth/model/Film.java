package profitsoft.homework.fifth.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private FilmCategory filmCategory;
}
