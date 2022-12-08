package org.farouk_maram.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.farouk_maram.App;
import org.farouk_maram.Statut;
import org.farouk_maram.Entities.Usager;
import org.farouk_maram.db.Database;
import org.farouk_maram.interfaces.HomeCRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeUsager extends App implements HomeCRUD<Usager> {
  @Override
  public int addOne(Usager usager) {
    Database db = new Database();
    try {
      db.connect();
      Connection conn = db.getConn();
      PreparedStatement statement = conn
          .prepareStatement("INSERT INTO usager (nom, prenom, status, email) VALUES (?, ?, ?, ?)");
      statement.setString(1, usager.getNom());
      statement.setString(2, usager.getPrenom());
      statement.setString(3, usager.getStatut().toString());
      statement.setString(4, usager.getEmail());

      statement.executeUpdate();

      Statement statement2 = conn.createStatement();
      ResultSet resultSet = statement2.executeQuery("SELECT MAX(id_usager) FROM usager");
      int id = 0;
      while (resultSet.next()) {
        id = resultSet.getInt("MAX(id_usager)");
      }
      return id;
    } catch (SQLException e) {
      System.err.println("Error while inserting a new usager");
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public void deleteOne(int id) {
    Database db = new Database();
    try {
      db.connect();
      Connection conn = db.getConn();
      PreparedStatement statement = conn.prepareStatement("DELETE FROM usager WHERE id_usager = ?");
      PreparedStatement statement2 = conn.prepareStatement("DELETE FROM emprunt WHERE usager_id = ?");
      statement2.setInt(1, id);
      statement.setInt(1, id);
      statement2.executeUpdate();
      int rowsDeleted = statement.executeUpdate();

      if (rowsDeleted > 0) {
        System.out.println("A usager was deleted successfully!");
      } else {
        System.out.println("A usager was not deleted successfully!");
      }

    } catch (SQLException e) {
      System.err.println("Error while fetching all usagers");
      e.printStackTrace();
    }

  }

  @Override
  public void updateOne(Usager usager) {
    Database db = new Database();
    try {
      db.connect();
      Connection conn = db.getConn();
      PreparedStatement statement = conn.prepareStatement(
          "UPDATE usager SET nom = ?, prenom = ?, email = ?, statut = ? WHERE id_usager = ?");
      statement.setString(1, usager.getNom());
      statement.setString(2, usager.getPrenom());
      statement.setString(3, usager.getEmail());
      statement.setString(4, usager.getStatut().toString());
      statement.setInt(5, usager.getId());

      int rowsUpdated = statement.executeUpdate();
      if (rowsUpdated > 0) {
        System.out.println("An existing usager was updated successfully!");
      } else {
        System.out.println("An existing usager was not updated successfully!");
      }

    } catch (SQLException e) {
      System.err.println("Error while updating a usager");
      e.printStackTrace();
    }
  }

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
        String statut = resultSet.getString("statut");
        Usager usager = new Usager(id, nom, prenom, email, Statut.valueOf(statut));

        usagers.add(usager);
      }

    } catch (SQLException e) {
      System.err.println("Error while fetching all usagers");
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

    FilteredList<Usager> lvUsager = new FilteredList<>(usagers, p -> true);// Pass the data to a filtered list
    table.setItems(lvUsager);// Set the table's items using the filtered list
    table.getColumns().addAll(idCol, nomCol, prenomCol, statutCol, emailCol);

    ChoiceBox<String> choiceBox = new ChoiceBox<>();
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

    Button deleteButton = new Button("Delete");
    Button editButton = new Button("Edit");
    Button addButton = new Button("Add");

    editButton.setDisable(true);
    deleteButton.setDisable(true);

    addButton.setOnAction(e -> {
      Stage dialog = new Stage();

      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.setTitle("Add a book");
      dialog.setMinWidth(400);
      dialog.setMinHeight(400);
      Label label1 = new Label("Nom");
      TextField textField1 = new TextField();
      Label label2 = new Label("Prenom");
      TextField textField2 = new TextField();
      Label label3 = new Label("Email");
      TextField textField3 = new TextField();
      Label label4 = new Label("Statut");
      TextField textField4 = new TextField();

      Button button = new Button("Add");
      VBox vBox = new VBox(label1, textField1, label2, textField2, label3, textField3, label4, textField4, button);

      Scene myDialogScene = new Scene(vBox);

      button.setOnAction(e1 -> {

        Usager usager = new Usager(textField1.getText(), textField2.getText(), textField3.getText(),
            Statut.valueOf(textField4.getText()));

        int id = addOne(usager);
        usager.setId(id);

        usagers.add(usager);
        dialog.close();
      });

      dialog.setScene(myDialogScene);
      dialog.show();

    });

    editButton.setOnAction(e -> {
      Stage dialog = new Stage();

      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.setTitle("Edit a book");
      dialog.setMinWidth(400);
      dialog.setMinHeight(400);
      Label label1 = new Label("Nom");
      TextField textField1 = new TextField(table.getSelectionModel().getSelectedItem().getNom());
      Label label2 = new Label("Prenom");
      TextField textField2 = new TextField(table.getSelectionModel().getSelectedItem().getPrenom());
      Label label3 = new Label("Email");
      TextField textField3 = new TextField(table.getSelectionModel().getSelectedItem().getEmail());
      Label label4 = new Label("Statut");
      ChoiceBox<String> statuts = new ChoiceBox<>();
      Button button = new Button("Edit");
      statuts.getItems().addAll("etudiant", "enseignant");
      statuts.setValue(table.getSelectionModel().getSelectedItem().getStatut().toString());

      VBox vBox = new VBox(label1, textField1, label2, textField2, label3, textField3, label4, statuts, button);

      Scene myDialogScene = new Scene(vBox);

      button.setOnAction(e1 -> {
        Usager usager = new Usager(table.getSelectionModel().getSelectedItem().getId(), textField1.getText(),
            textField2.getText(),
            textField3.getText(), Statut.valueOf(statuts.getValue()));

        updateOne(usager);

        usagers.set(usagers.indexOf(table.getSelectionModel().getSelectedItem()), usager);
        dialog.close();
      });

      dialog.setScene(myDialogScene);
      dialog.show();

    });

    deleteButton.setOnAction(e -> {
      Stage dialog = new Stage();

      dialog.initModality(Modality.APPLICATION_MODAL);

      Text text = new Text("Are you sure you want to delete this usager?");
      text.setFont(new Font("Arial", 20));
      Button yesButton = new Button("Yes");
      Button noButton = new Button("No");
      HBox hBox = new HBox(yesButton, noButton);
      hBox.setSpacing(30);
      hBox.setAlignment(Pos.CENTER);
      VBox vBox = new VBox(text, hBox);
      vBox.setSpacing(30);
      vBox.setAlignment(Pos.CENTER);
      vBox.setPadding(new Insets(10));
      Scene myDialogScene = new Scene(vBox);

      yesButton.setOnAction(e1 -> {
        Usager usager = table.getSelectionModel().getSelectedItem();
        deleteOne(usager.getId());
        usagers.remove(usager);
        dialog.close();
      });

      noButton.setOnAction(e1 -> {
        dialog.close();
      });

      dialog.setScene(myDialogScene);
      dialog.show();

    });

    // disable button if no row is selected and enable if a row is selected
    table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        editButton.setDisable(false);
        deleteButton.setDisable(false);
      } else {
        editButton.setDisable(true);
        deleteButton.setDisable(true);
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
    vbox.getChildren().addAll(label, table, hBox, addButton, editButton, deleteButton);

    StackPane root = (StackPane) scene.getRoot();
    root.getChildren().addAll(vbox);

    return scene;
  }

}
