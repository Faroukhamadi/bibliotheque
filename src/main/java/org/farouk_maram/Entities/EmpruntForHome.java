
package org.farouk_maram.Entities;

import java.sql.Date;

import javafx.beans.property.SimpleStringProperty;

public class EmpruntForHome {
  private int id;
  private Date dateEmprunt;
  private String dateRetour;
  private SimpleStringProperty titre;
  private SimpleStringProperty auteur;
  private SimpleStringProperty isbn;
  private SimpleStringProperty email;

  public EmpruntForHome(int id, Date dateEmprunt, String dateRetour, String titre, String auteur, String isbn,
      String email) {
    this.id = id;
    this.dateEmprunt = dateEmprunt;
    this.dateRetour = dateRetour;
    this.titre = new SimpleStringProperty(titre);
    this.auteur = new SimpleStringProperty(auteur);
    this.isbn = new SimpleStringProperty(isbn);
    this.email = new SimpleStringProperty(email);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getDateEmpruntForHome() {
    return dateEmprunt;
  }

  public void setDateEmpruntForHome(Date dateEmprunt) {
    this.dateEmprunt = dateEmprunt;
  }

  public String getDateRetour() {
    return dateRetour;
  }

  public void setDateRetour(String dateRetour) {
    this.dateRetour = dateRetour;
  }

  public Date getDateEmprunt() {
    return dateEmprunt;
  }

  public void setDateEmprunt(Date dateEmprunt) {
    this.dateEmprunt = dateEmprunt;
  }

  public String getTitre() {
    return titre.get();
  }

  public void setTitre(String titre) {
    this.titre.setValue(titre);
  }

  public String getAuteur() {
    return auteur.get();
  }

  public void setAuteur(String auteur) {
    this.auteur.setValue(auteur);
    ;
  }

  public String getIsbn() {
    return isbn.get();
  }

  public void setIsbn(String isbn) {
    this.isbn.setValue(isbn);
  }

  public String getEmail() {
    return email.get();
  }

  public void setEmail(String email) {
    this.email.setValue(email);
  }

  @Override
  public String toString() {
    return "EmpruntForHome [auteur=" + auteur + ", dateEmprunt=" + dateEmprunt + ", dateRetour=" + dateRetour
        + ", email=" + email + ", id=" + id + ", isbn=" + isbn + ", titre=" + titre + "]";
  }

}
