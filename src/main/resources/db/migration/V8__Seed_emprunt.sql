insert into
  emprunt (date_emprunt, livre_id, usager_id)
values
  (NOW(), 2, 3);

insert into
  emprunt (date_emprunt, livre_id, usager_id)
values
  (NOW(), 1, 2);

insert into
  emprunt (date_emprunt, date_retour, livre_id, usager_id)
values
  (NOW(), NOW(), 3, 1);