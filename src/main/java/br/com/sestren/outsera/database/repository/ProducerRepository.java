package br.com.sestren.outsera.database.repository;

import br.com.sestren.outsera.database.entity.Producer;
import br.com.sestren.outsera.database.entity.ProducerMax;
import br.com.sestren.outsera.database.entity.ProducerMin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Optional<Producer> findByName(String name);

    @Query(value = """
            select p.id, p.name, count(*) qtd_movie, min(m.year_movie) year_first_movie, max(m.year_movie) year_last_movie, (max(m.year_movie) - min(m.year_movie)) qtd_years_movie
            from producers p
                     inner join movie_producers mp on mp.id_producer = p.id
                     inner join movies m on m.id = mp.id_movie
            where m.winner = true
            group by p.id, p.name
            having qtd_movie > 1
            order by qtd_years_movie desc
            """, nativeQuery = true)
    List<ProducerMax> findMaxInterval();

    @Query(value = """
            select a.id, a.name, m.year_movie
            from (
            select ap.id, ap.name, count(*) qtd_movies
            from producers ap
                     inner join movie_producers amp on amp.id_producer = ap.id
                     inner join movies am on am.id = amp.id_movie
            where am.winner = true
            group by ap.id, ap.name
            having qtd_movies > 1
            ) a
            inner join movie_producers mp on mp.id_producer = a.id
            inner join movies m on m.id = mp.id_movie
            order by a.id, m.year_movie
            """, nativeQuery = true)
    List<ProducerMin> findMinInterval();
}
