package br.com.sestren.outsera.database.repository;

import br.com.sestren.outsera.database.entity.MovieStudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieStudioRepository extends JpaRepository<MovieStudio, Long> {
}
