update bodypart set exercises_count = (select count(exercises.id) from exercises where exercises.bodypart_fk = bodypart.id);
update equipment_type set exercises_count = (select count(exercises.id) from exercises where exercises.equipmenttype_fk = equipment_type.id);
update exercise_type set exercises_count = (select count(exercises.id) from exercises where exercises.exercisetype_fk  = exercise_type.id);
