create table livre (
  id_livre int NOT NULL AUTO_INCREMENT,
  titre varchar(100) NOT NULL,
  auteur varchar(40) NOT NULL,
  isbn varchar(13) NOT NULL,
  primary key (id_livre)
);