package com.bolo.fit.model;

import com.bolo.fit.config.Catalog;
import com.bolo.fit.config.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Exercise_Type", catalog = Catalog.FIT_DATA_BASE, schema = Schema.FitApp)
public class ExerciseType extends BaseEntity {
    @Id
    @SequenceGenerator(name = "fitapp.idexercise_type_id_seq",
            sequenceName = "fitapp.idexercise_type_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "fitapp.idexercise_type_id_seq")
    @Column(name = "id")
    private Long execiseId;

    @Column(name = "name")
    private String name;

    @Column(name = "nome")
    private String nome;

    @Column(name = "exercises_count")
    private Integer exerciseCount;

    @OneToMany(mappedBy = "exerciseType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Exercise> exercise;
}
