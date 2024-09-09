package br.com.sestren.outsera.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "year_movie")
    private Integer year;
    private String title;
    private Boolean winner;

    @OneToMany(mappedBy = "movie")
    private List<MovieProducer> producers;

    @OneToMany(mappedBy = "movie")
    private List<MovieStudio> studios;
}
