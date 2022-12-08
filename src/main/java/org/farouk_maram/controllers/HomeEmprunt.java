package org.farouk_maram.controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.farouk_maram.App;
import org.farouk_maram.Entities.EmpruntForHome;
import org.farouk_maram.db.Database;
import org.farouk_maram.interfaces.ManyFetcher;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

// change select query to sort by date
public class HomeEmprunt extends App implements ManyFetcher {
  private TableView<EmpruntForHome> table = new TableView<>();
  private final ObservableList<EmpruntForHome> emprunts = FXCollections.observableArrayList();

  @Override
  public void fetchAll() {
    Database db = new Database();
    try {
      db.connect();
      Connection conn = db.getConn();
      Statement statement = conn.createStatement();
      ResultSet resultSet = statement
          .executeQuery(
              "select * from emprunt e, livre l, usager u where e.livre_id = l.id_livre and u.id_usager = e.usager_id order by date_emprunt desc");
      while (resultSet.next()) {
        int id = resultSet.getInt("id_emprunt");
        Date dateEmprunt = resultSet.getDate("date_emprunt");
        String dateRetour = resultSet.getString("date_retour");
        String titre = resultSet.getString("titre");
        String auteur = resultSet.getString("auteur");
        String isbn = resultSet.getString("isbn");
        String email = resultSet.getString("email");

        if (dateRetour == null) {
          EmpruntForHome emprunt = new EmpruntForHome(id, dateEmprunt, "Not returned yet", titre, auteur, isbn, email);
          emprunts.add(emprunt);
        } else {
          EmpruntForHome emprunt = new EmpruntForHome(id, dateEmprunt, dateRetour, titre, auteur, isbn, email);
          emprunts.add(emprunt);
        }

      }

    } catch (SQLException e) {
      System.err.println("Error while fetching all books");
      e.printStackTrace();
    }
  }

  public HomeEmprunt(Stage stage) {
    this.stage = stage;
  }

  public Scene getScene() {
    fetchAll();

    StackPane stackPane = new StackPane();

    Scene scene = new Scene(stackPane, 640, 480);

    stage.setTitle("All books");
    stage.setMinHeight(800);
    stage.setMinWidth(800);

    final Label label = new Label("All Books");
    label.setFont(new Font("Arial", 20));

    table.setEditable(true);

    TableColumn<EmpruntForHome, String> idCol = new TableColumn<>("Id");
    idCol.setMinWidth(100);
    idCol.setCellValueFactory(
        new PropertyValueFactory<>("id"));

    TableColumn<EmpruntForHome, String> titreCol = new TableColumn<>("Titre");
    titreCol.setMinWidth(100);
    titreCol.setCellValueFactory(
        new PropertyValueFactory<>("titre"));

    TableColumn<EmpruntForHome, String> auteurCol = new TableColumn<>("Auteur");
    auteurCol.setMinWidth(200);
    auteurCol.setCellValueFactory(
        new PropertyValueFactory<>("auteur"));

    TableColumn<EmpruntForHome, String> isbnCol = new TableColumn<>("Isbn");
    isbnCol.setMinWidth(200);
    isbnCol.setCellValueFactory(
        new PropertyValueFactory<>("isbn"));

    TableColumn<EmpruntForHome, String> dateEmpruntCol = new TableColumn<>("Date Emprunt");
    dateEmpruntCol.setMinWidth(200);
    dateEmpruntCol.setCellValueFactory(
        new PropertyValueFactory<>("dateEmprunt"));

    TableColumn<EmpruntForHome, String> dateRetourCol = new TableColumn<>("Date Retour");
    dateRetourCol.setMinWidth(200);
    dateRetourCol.setCellValueFactory(
        new PropertyValueFactory<>("dateRetour"));

    TableColumn<EmpruntForHome, String> emailCol = new TableColumn<>("Email");
    emailCol.setMinWidth(200);
    emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

    FilteredList<EmpruntForHome> flLivre = new FilteredList(emprunts, p -> true);// Pass the data to a filtered list
    table.setItems(flLivre);// Set the table's items using the filtered list
    table.getColumns().addAll(idCol, titreCol, auteurCol, isbnCol, dateEmpruntCol, dateRetourCol, emailCol);

    // Adding ChoiceBox and TextField here!
    ChoiceBox<String> choiceBox = new ChoiceBox();
    choiceBox.getItems().addAll("Id", "Titre", "Auteur", "Isbn", "Date Emprunt", "Date Retour", "Email");
    choiceBox.setValue("Titre");

    TextField textField = new TextField();
    textField.setPromptText("Search here!");
    textField.textProperty().addListener((obs, oldValue, newValue) -> {
      switch (choiceBox.getValue()) {
        case "Id":
          flLivre.setPredicate(
              e -> Integer.toString(e.getId()).toLowerCase()
                  .contains(newValue.toLowerCase().trim()));
          break;
        case "Titre":
          flLivre.setPredicate(e -> e.getTitre().toLowerCase().contains(newValue.toLowerCase().trim()));
          break;
        case "Auteur":
          flLivre.setPredicate(e -> e.getAuteur().toLowerCase().contains(newValue.toLowerCase().trim()));
          break;
        case "Isbn":
          flLivre.setPredicate(e -> e.getIsbn().toLowerCase().contains(newValue.toLowerCase().trim()));
          break;
        case "Date Emprunt":
          flLivre
              .setPredicate(e -> e.getDateEmprunt().toString().toLowerCase().contains(newValue.toLowerCase().trim()));
          break;
        case "Date Retour":
          flLivre.setPredicate(
              e -> e.getDateRetour().toString().toLowerCase().contains(newValue.toLowerCase().trim()));
          break;
        case "Email":
          flLivre.setPredicate(e -> e.getEmail().toLowerCase().contains(newValue.toLowerCase().trim()));
          break;
      }
    });

    choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {// reset table and
      if (newVal != null) {
        textField.setText("");
      }
    });

    HBox hBox = new HBox(choiceBox, textField);
    hBox.setAlignment(Pos.CENTER);
    final VBox vbox = new VBox();
    vbox.setSpacing(5);
    vbox.setPadding(new Insets(10, 0, 0, 10));
    vbox.getChildren().addAll(label, table, hBox);

    StackPane root = (StackPane) scene.getRoot();
    root.getChildren().addAll(vbox);

    return scene;
  }

}