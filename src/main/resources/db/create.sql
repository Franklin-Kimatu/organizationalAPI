SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS departments (
id int PRIMARY KEY auto_increment,
departmentName VARCHAR,
description VARCHAR,
employeesNumber INTEGER
);

CREATE TABLE IF NOT EXISTS articles (
id int PRIMARY KEY auto_increment,
content VARCHAR
);

CREATE TABLE IF NOT EXISTS users(
id int PRIMARY KEY auto_increment,
userName VARCHAR,
userCompanyPosition VARCHAR,
userRole VARCHAR,
departmentId VARCHAR
);

