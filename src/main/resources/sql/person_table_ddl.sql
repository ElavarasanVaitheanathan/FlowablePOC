CREATE TABLE person (
  id integer NOT NULL auto_increment,
  username varchar(45) NOT NULL,
  first_name varchar(45) DEFAULT NULL,
  last_name varchar(45) DEFAULT NULL,
  birth_date varchar(45) DEFAULT NULL,
  PRIMARY KEY (id)
);