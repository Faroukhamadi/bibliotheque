package org.farouk_maram.Entities;

import java.util.ArrayList;

import org.farouk_maram.Statut;

import javafx.beans.property.SimpleStringProperty;

public class Usager extends Personne {
  private int id;
  private SimpleStringProperty email;
  private Statut statut;
  private ArrayList<Emprunt> emprunts = new ArrayList<Emprunt>();

  public Usager(int id, String nom, String prenom, String email, Statut statut) {
    super(nom, prenom);
    this.id = id;
    this.email.setValue(email);
    this.statut = statut;
  }

  public Usager(int id, String nom, String prenom, String email, Statut statut, ArrayList<Emprunt> emprunts) {
    super(nom, prenom);
    this.id = id;
    this.email.setValue(email);
    this.statut = statut;
    this.emprunts = emprunts;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public SimpleStringProperty getEmailProperty() {
    return email;
  }

  public String getEmail() {
    return email.get();
  }

  public void setEmail(String email) {
    this.email.setValue(email);
  }

  public Statut getStatut() {
    return statut;
  }

  public void setStatut(Statut statut) {
    this.statut = statut;
  }

  public ArrayList<Emprunt> getEmprunts() {
    return emprunts;
  }

  @Override
  public String toString() {
    return "Usager [email=" + email + ", id=" + id + ", nom=" + getNom() + ", statut=" + statut + ", prenom="
        + getPrenom()
        + "]";
  }
}