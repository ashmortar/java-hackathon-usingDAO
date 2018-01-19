SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS members (
id int PRIMARY KEY auto_increment,
name VARCHAR,
email VARCHAR,
twitter VARCHAR
);

CREATE TABLE IF NOT EXISTS teams (
id int PRIMARY KEY auto_increment,
name VARCHAR,
description VARCHAR
);