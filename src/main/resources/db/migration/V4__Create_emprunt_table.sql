create table emprunt (
  id_emprunt int NOT NULL AUTO_INCREMENT,
  date_emprunt Date NOT NULL,
  date_retour Date,
  livre_id int NOT NULL,
  usager_id int NOT NULL,
  primary key (id_emprunt),
  foreign key (livre_id) REFERENCES livre(id_livre) ON DELETE CASCADE,
  foreign key (usager_id) REFERENCES usager(id_usager) ON DELETE CASCADE
);
