CREATE SEQUENCE fitapp.iduser_id_seq;

create table fitapp.users(
     id        bigint    not null primary key default nextval('fitapp.iduser_id_seq'),
     name varchar(255),
     email varchar(255) NOT NULL UNIQUE,
     role varchar(255) NOT NULL,
     password varchar(255) not null,
     createdAt timestamp not null,
     updatedAt timestamp not null
);