package profitsoft.homework.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import profitsoft.homework.fifth.model.FilmCategory;

@Repository
public interface FilmCategoryRepository extends JpaRepository<FilmCategory, Integer> {
}
