create table usager (
  id_usager int NOT NULL AUTO_INCREMENT,
  nom varchar(45) NOT NULL,
  prenom varchar(45) NOT NULL,
  statut varchar(45) NOT NULL,
  email varchar(40) NOT NULL,
  primary key (id_usager),
  constraint CHK_statut check (
    statut = 'enseignant'
    and statut = 'etudiant'
  )
);