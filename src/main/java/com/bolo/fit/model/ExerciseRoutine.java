package com.bolo.fit.model;

import com.bolo.fit.config.Catalog;
import com.bolo.fit.config.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "exercise_routine", catalog = Catalog.FIT_DATA_BASE, schema = Schema.FitApp)
public class ExerciseRoutine extends BaseEntity {

    @Id
    @SequenceGenerator(name = "fitapp.idexecise_routine_id_seq",
            sequenceName = "fitapp.idexecise_routine_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "fitapp.idexecise_routine_id_seq")
    @Column(name = "id")
    private Long exerciseRoutineId;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="series")
    private Integer series;

    @Column(name="repetitions")
    private Integer repetitions;

    @Column(name="rest_time")
    private Double restTime;

    @Column(name="isVisible")
    private Boolean isVisible;

    @OneToMany(mappedBy = "exerciseRoutine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseRoutineExercise> exerciseRoutineExercise;
}
