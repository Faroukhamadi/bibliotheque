package org.farouk_maram.Entities;

import java.sql.Date;

import javafx.beans.property.SimpleIntegerProperty;

public class Emprunt {
  private int id;
  private Date dateEmprunt;
  private Date dateRetour;
  private Livre livre;
  private Usager usager;
  private SimpleIntegerProperty livreId = new SimpleIntegerProperty();
  private SimpleIntegerProperty usagerId = new SimpleIntegerProperty();

  public Emprunt(int id, Date dateEmprunt, Date dateRetour, Livre livre, Usager usager) {
    this.id = id;
    this.dateEmprunt = dateEmprunt;
    this.dateRetour = dateRetour;
    this.livre = livre;
    this.usager = usager;
  }

  public Emprunt(int id, Date dateEmprunt, Date dateRetour, int livreId, int usagerId) {
    this.id = id;
    this.dateEmprunt = dateEmprunt;
    this.dateRetour = dateRetour;
    this.livreId.set(livreId);
    this.usagerId.set(usagerId);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getDateEmprunt() {
    return dateEmprunt;
  }

  public void setDateEmprunt(Date dateEmprunt) {
    this.dateEmprunt = dateEmprunt;
  }

  public Date getDateRetour() {
    return dateRetour;
  }

  public void setDateRetour(Date dateRetour) {
    this.dateRetour = dateRetour;
  }

  public Livre getLivre() {
    return livre;
  }

  public void setLivre(Livre livre) {
    this.livre = livre;
  }

  public Usager getUsager() {
    return usager;
  }

  public void setUsager(Usager usager) {
    this.usager = usager;
  }

  @Override
  public String toString() {
    return "Emprunt [dateEmprunt=" + dateEmprunt + ", dateRetour=" + dateRetour + ", id=" + id + ", livre=" + livre
        + ", usager=" + usager + "]";
  }

  public int getLivreId() {
    return livreId.get();
  }

  public void setLivreId(SimpleIntegerProperty livreId) {
    this.livreId = livreId;
  }

  public int getUsagerId() {
    return usagerId.get();
  }

  public void setUsagerId(SimpleIntegerProperty usagerId) {
    this.usagerId = usagerId;
  }

}