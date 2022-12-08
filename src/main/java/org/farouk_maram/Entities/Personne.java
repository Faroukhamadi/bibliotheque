package org.farouk_maram.Entities;

import javafx.beans.property.SimpleStringProperty;

public abstract class Personne {
  private SimpleStringProperty nom = new SimpleStringProperty();
  private SimpleStringProperty prenom = new SimpleStringProperty();

  public Personne(String nom, String prenom) {
    this.nom.setValue(prenom);
    this.prenom.setValue(prenom);
  }

  public String getNom() {
    return nom.get();
  }

  public SimpleStringProperty getNomProperty() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom.setValue(nom);
  }

  public String getPrenom() {
    return prenom.get();
  }

  public SimpleStringProperty getPrenomProperty() {
    return nom;
  }

  public void setPrenom(String prenom) {
    this.prenom.setValue(prenom);
  }

}
