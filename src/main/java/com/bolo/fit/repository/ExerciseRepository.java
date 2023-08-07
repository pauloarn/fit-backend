package com.bolo.fit.repository;

import com.bolo.fit.model.Exercise;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends GenericRepository<Exercise, Long> {

    @Query("select e from Exercise e where " +
            "(:exerciseTypeId is NULL OR e.exerciseType.execiseId = :exerciseTypeId) and " +
            "(:bodyPartId is NULL OR e.bodyPart.bodyPartId = :bodyPartId) and " +
            "(:equipmentTypeId is NULL OR e.equipmentType.equipmentTypeId = :equipmentTypeId)")
    List<Exercise> findExercisesFromProperties(
            @Param("exerciseTypeId") Long exerciseTypeId,
            @Param("bodyPartId") Long bodyPartId,
            @Param("equipmentTypeId") Long equipmentTypeId
    );

}
