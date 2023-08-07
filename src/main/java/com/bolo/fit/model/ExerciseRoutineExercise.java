package com.bolo.fit.model;

import com.bolo.fit.config.Catalog;
import com.bolo.fit.config.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @ManyToOne()
    @JoinColumn(name = "exercise_routine_id")
    private ExerciseRoutine exerciseRoutine;

    @ManyToOne()
    @JoinColumn(name="exercise_id")
    private Exercise exercise;
}
