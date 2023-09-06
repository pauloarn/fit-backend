package com.bolo.fit.model;

import com.bolo.fit.config.Catalog;
import com.bolo.fit.config.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "exercise_routine_exercise", catalog = Catalog.FIT_DATA_BASE, schema = Schema.FitApp)
public class ExerciseRoutineExercise extends BaseEntity{
    @Id
    @SequenceGenerator(name = "fitapp.idexercise_routine_exercise_id_seq",
            sequenceName = "fitapp.idexercise_routine_exercise_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "fitapp.idexercise_routine_exercise_id_seq")
    @Column(name = "id")
    private Long exerciseRoutineId;

    @Column(name="series")
    private Integer series;

    @Column(name="repetitions")
    private Integer repetitions;

    @Column(name="rest_time")
    private Double rest_time;

    @Column(name="notes")
    private String notes;

    @Column(name="exerciseweight")
    private Double exerciseWeight;

    @ManyToOne()
    @JoinColumn(name = "exercise_routine_id")
    private ExerciseRoutine exerciseRoutine;

    @ManyToOne()
    @JoinColumn(name="exercise_id")
    private Exercise exercise;

    @OneToMany
    @JoinTable(
            name = "bi_set_routine_relation",
            joinColumns= @JoinColumn(name="bi_set_main_exercise_routine_exercise_fk"),
            inverseJoinColumns = @JoinColumn(name="bi_set_exercise_fk"),
            catalog = Catalog.FIT_DATA_BASE,
            schema = Schema.FitApp
    )
    private List<Exercise> secondaryExercisesList = new ArrayList<>();
}
