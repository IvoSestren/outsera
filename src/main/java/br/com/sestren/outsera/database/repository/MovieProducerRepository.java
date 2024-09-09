package br.com.sestren.outsera.database.repository;

import br.com.sestren.outsera.database.entity.MovieProducer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieProducerRepository extends JpaRepository<MovieProducer, Long> {
}
