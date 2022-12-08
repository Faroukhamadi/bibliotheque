create table emprunt_history (
  id_emprunt int NOT NULL AUTO_INCREMENT,
  date_emprunt Date NOT NULL,
  date_retour Date,
  livre_id int NOT NULL,
  usager_id int NOT NULL,
  primary key (id_emprunt)
);

CREATE TRIGGER emprunt_change_trigger BEFORE
INSERT
  ON emprunt FOR EACH ROW
INSERT INTO
  emprunt_history (date_emprunt, date_retour, livre_id, usager_id)
VALUES
  (
    NEW.date_emprunt,
    NEW.date_retour,
    NEW.livre_id,
    NEW.usager_id
  );