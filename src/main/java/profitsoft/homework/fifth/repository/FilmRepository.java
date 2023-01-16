package profitsoft.homework.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import profitsoft.homework.fifth.model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film> {
}
