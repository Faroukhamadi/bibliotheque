package org.farouk_maram.Entities;

import javafx.beans.property.SimpleStringProperty;

public class Livre {
  private int id;
  private SimpleStringProperty titre = new SimpleStringProperty();
  private SimpleStringProperty auteur = new SimpleStringProperty();
  private SimpleStringProperty isbn = new SimpleStringProperty();

  public Livre(int id, String titre, String auteur, String isbn) {
    this.id = id;
    this.titre.setValue(titre);
    this.auteur.setValue(auteur);
    this.isbn.setValue(isbn);
  }

  public Livre(String titre, String auteur, String isbn) {
    this.titre.setValue(titre);
    this.auteur.setValue(auteur);
    this.isbn.setValue(isbn);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public SimpleStringProperty getTitreProperty() {
    return titre;
  }

  public String getTitre() {
    return titre.get();
  }

  public void setTitre(SimpleStringProperty titre) {
    this.titre = titre;
  }

  public SimpleStringProperty getAuteurProperty() {
    return auteur;
  }

  public String getAuteur() {
    return auteur.get();
  }

  public void setAuteur(SimpleStringProperty auteur) {
    this.auteur = auteur;
  }

  public SimpleStringProperty getIsbnProperty() {
    return isbn;
  }

  public String getIsbn() {
    return isbn.get();
  }

  public void setIsbn(SimpleStringProperty isbn) {
    this.isbn = isbn;
  }

  @Override
  public String toString() {
    return "Livre [auteur=" + auteur.get() + ", id=" + id + ", isbn=" + isbn.get() + ", titre=" + titre.get() + "]";
  }

}
