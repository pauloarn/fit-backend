CREATE TABLE fitapp.bi_set_routine_relation
(
    bi_set_main_exercise_routine_exercise_fk     bigint not null,
    bi_set_exercise_fk bigint not null,
    CONSTRAINT BI_SET_MAIN_EXERCISE_ROUTINE_EXERCISE_fk FOREIGN KEY (bi_set_main_exercise_routine_exercise_fk) references fitapp.exercise_routine_exercise (id),
    CONSTRAINT BI_SET_SECONDARY_EXERCISE_fk FOREIGN KEY (bi_set_exercise_fk) references fitapp.exercises (id)
);