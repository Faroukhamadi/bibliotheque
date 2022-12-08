package org.farouk_maram.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.farouk_maram.App;
import org.farouk_maram.Statut;
import org.farouk_maram.Entities.Usager;
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

public class HomeUsager extends App implements ManyFetcher {
  private TableView<Usager> table = new TableView<>();
  private final ObservableList<Usager> usagers = FXCollections.observableArrayList();

  @Override
  public void fetchAll() {
    Database db = new Database();
    try {
      db.connect();
      Connection conn = db.getConn();
      Statement statement = conn.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM usager");
      while (resultSet.next()) {
        int id = resultSet.getInt("id_usager");
        String nom = resultSet.getString("nom");
        String prenom = resultSet.getString("prenom");
        String email = resultSet.getString("email");
        String statut = resultSet.getString("statut").toUpperCase();
        Usager usager = new Usager(id, nom, prenom, email, Statut.valueOf(statut));

        usagers.add(usager);
      }

    } catch (SQLException e) {
      System.err.println("Error while fetching all books");
      e.printStackTrace();
    }
  }

  public HomeUsager(Stage stage) {
    this.stage = stage;
  }

  public Scene getScene() {
    fetchAll();

    StackPane stackPane = new StackPane();

    Scene scene = new Scene(stackPane, 640, 480);

    stage.setTitle("All usager");
    stage.setMinHeight(800);
    stage.setMinWidth(800);

    final Label label = new Label("All usagers");
    label.setFont(new Font("Arial", 20));

    table.setEditable(true);

    TableColumn<Usager, String> idCol = new TableColumn<>("Id");
    idCol.setMinWidth(100);
    idCol.setCellValueFactory(
        new PropertyValueFactory<Usager, String>("id"));

    TableColumn<Usager, String> nomCol = new TableColumn<>("Nom");
    nomCol.setMinWidth(100);
    nomCol.setCellValueFactory(
        new PropertyValueFactory<Usager, String>("nom"));

    TableColumn<Usager, String> prenomCol = new TableColumn<>("Prenom");
    prenomCol.setMinWidth(200);
    prenomCol.setCellValueFactory(
        new PropertyValueFactory<Usager, String>("prenom"));

    TableColumn<Usager, String> statutCol = new TableColumn<>("Statut");
    statutCol.setMinWidth(200);
    statutCol.setCellValueFactory(
        new PropertyValueFactory<Usager, String>("statut"));

    TableColumn<Usager, String> emailCol = new TableColumn<>("Email");
    emailCol.setMinWidth(200);
    emailCol.setCellValueFactory(
        new PropertyValueFactory<Usager, String>("email"));

    FilteredList<Usager> lvUsager = new FilteredList(usagers, p -> true);// Pass the data to a filtered list
    table.setItems(lvUsager);// Set the table's items using the filtered list
    table.getColumns().addAll(idCol, nomCol, prenomCol, statutCol, emailCol);

    // Adding ChoiceBox and TextField here!
    ChoiceBox<String> choiceBox = new ChoiceBox();
    choiceBox.getItems().addAll("Id", "Prenom", "Statut", "Email");
    choiceBox.setValue("Prenom");

    TextField textField = new TextField();
    textField.setPromptText("Search here!");
    textField.textProperty().addListener((obs, oldValue, newValue) -> {
      switch (choiceBox.getValue()) {
        case "Id":
          lvUsager.setPredicate(
              p -> Integer.toString(p.getId()).toLowerCase()
                  .contains(newValue.toLowerCase().trim()));
          break;
        case "Nom":
          lvUsager.setPredicate(p -> p.getNom().toLowerCase().contains(newValue.toLowerCase().trim()));
          break;
        case "Prenom":
          lvUsager.setPredicate(p -> p.getPrenom().toLowerCase().contains(newValue.toLowerCase().trim()));
          break;
        case "Statut":
          lvUsager.setPredicate(p -> p.getStatut().toString().toLowerCase().contains(newValue.toLowerCase().trim()));
          break;
        case "Email":
          lvUsager.setPredicate(p -> p.getEmail().toLowerCase().contains(newValue.toLowerCase().trim()));
          break;
      }
    });

    // reset textfield when choicebox is changed
    choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
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
