// SELECT para pegar o MAX
select p.name, count(*) qtd_movie, min(m.year_movie) year_first_movie, max(m.year_movie) year_last_movie, (max(m.year_movie) - min(m.year_movie)) qtd_year_movie
from producers p
         inner join movie_producers mp on mp.id_producer = p.id
         inner join movies m on m.id = mp.id_movie
where m.winner = true
group by p.name
having qtd_movie > 1
order by qtd_year_movie desc
    limit 1


// SELECT para pegar o MIN
select p.id, p.name, count(*) qtd_movies
from producers p
         inner join movie_producers mp on mp.id_producer = p.id
         inner join movies m on m.id = mp.id_movie
where m.winner = true
group by p.id, p.name
having qtd_movies > 1