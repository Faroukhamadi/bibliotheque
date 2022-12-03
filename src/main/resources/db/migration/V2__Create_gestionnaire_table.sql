create table gestionnaire (
  id_gest int NOT NULL AUTO_INCREMENT,
  username varchar(40) NOT NULL,
  password varchar(100) NOT NULL,
  prenom varchar(40) NOT NULL,
  primary key (id_gest)
);