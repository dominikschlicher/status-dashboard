# REGISTERSYSTEM schema

# --- !Ups

CREATE TABLE RegisteredSystem (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    url varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE RegisteredSystem;