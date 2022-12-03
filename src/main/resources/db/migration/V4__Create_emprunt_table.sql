create table Emprunt (
  id_emprunt int NOT NULL AUTO_INCREMENT,
  date_emprunt Date,
  date_retour Date,
  id_livre int NOT NULL,
  id_usager int NOT NULL,
  primary key (id_emprunt),
  foreign key (id_livre) REFERENCES livre(id_livre),
  foreign key (id_usager) REFERENCES usager(id_usager)
);