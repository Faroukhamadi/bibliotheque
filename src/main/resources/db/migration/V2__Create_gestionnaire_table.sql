create table gestionnaire (
  id_gest int NOT NULL AUTO_INCREMENT,
  username varchar(40) UNIQUE NOT NULL,
  password varchar(100) NOT NULL,
  primary key (id_gest)
);

alter table
  gestionnaire
add
  index (username);