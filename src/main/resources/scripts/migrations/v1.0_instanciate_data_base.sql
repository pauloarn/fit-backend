create schema FitApp;

CREATE SEQUENCE idbodypart_id_seq;
CREATE SEQUENCE idequipment_type_id_seq;
CREATE SEQUENCE idexercise_type_id_seq;
CREATE SEQUENCE idexercises_id_seq;
CREATE SEQUENCE idexercise_routine_id_seq;
CREATE SEQUENCE idexercise_routine_exercise_id_seq;


create table fitapp.bodypart
(
    id        bigint    not null primary key default nextval('idbodypart_id_seq'),
    "name"    varchar(255),
    nome      varchar(255),
    createdAt timestamp not null,
    updatedAt timestamp not null
);
create table fitapp.equipment_type
(
    id        bigint    not null primary key default nextval('idequipment_type_id_seq'),
    name      varchar(255),
    nome      varchar(255),
    createdAt timestamp not null,
    updatedAt timestamp not null
);
create table fitapp.exercise_type
(
    id        bigint    not null primary key default nextval('idexercise_type_id_seq'),
    name      varchar(255),
    nome      varchar(255),
    createdAt timestamp not null,
    updatedAt timestamp not null
);

create table fitapp.exercises
(
    id               bigint    not null primary key default nextval('idexercises_id_seq'),
    name             varchar(255),
    nome             varchar(255),
    gifUrl           varchar(255),
    page             int,
    item             int,
    bodyPart_fk      bigint,
    equipmentType_fk bigint,
    exerciseType_fk  bigint,
    createdAt        timestamp not null,
    updatedAt        timestamp not null,
    CONSTRAINT ExeciseBodyPart_FK foreign key (bodyPart_fk) references fitapp.bodypart (id),
    CONSTRAINT ExeciseEquipmentType_FK foreign key (equipmentType_fk) references fitapp.equipment_type (id),
    CONSTRAINT ExerciseExerciseType_FK foreign key (exerciseType_fk) references fitapp.exercise_type (id)
);

create table fitapp.exercise_routine (
    id        bigint    not null primary key default nextval('idexercise_routine_id_seq'),
    name            varchar(255),
    description     varchar(255),
    series          int,
    repetitions     int,
    rest_time       float8,
    isVisible       boolean,
    createdAt       timestamp not null,
    updatedAt       timestamp not null
);

create table fitapp.exercise_routine_exercise (
    id        bigint    not null primary key default nextval('idexercise_routine_exercise_id_seq'),
    exercise_id     bigint,
    exercise_routine_id bigint,
    notes            varchar(255),
    series          int,
    repetitions     int,
    rest_time       float8,
    createdAt        timestamp not null,
    updatedAt       timestamp not null,
    CONSTRAINT ExerciseRoutineExerciseExercise_FK foreign key (exercise_id) references fitapp.exercises (id),
    CONSTRAINT ExerciseRoutineExerciseRoutineExercise_FK foreign key (exercise_routine_id) references fitapp.exercise_routine (id)
);