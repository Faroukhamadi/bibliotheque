package org.farouk_maram.controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.farouk_maram.App;
import org.farouk_maram.Entities.Emprunt;
import org.farouk_maram.Entities.EmpruntForHome;
import org.farouk_maram.Entities.Livre;
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

// change select query to sort by date
public class HomeEmprunt extends App implements HomeCRUD<Emprunt> {
  @Override
  public int addOne(Emprunt emprunt) {
    Database db = new Database();
    try {
      db.connect();
      Connection conn = db.getConn();
      PreparedStatement statement = conn
          .prepareStatement("INSERT INTO emprunt (date_emprunt, date_retour, livre_id, usager_id) VALUES (?, ?, ?, ?)");
      statement.setDate(1, emprunt.getDateEmprunt());
      if (emprunt.getDateRetour() == null) {
        statement.setDate(2, null);
      } else {
        statement.setDate(2, emprunt.getDateRetour());
      }
      statement.setInt(3, emprunt.getLivre().getId());
      statement.setInt(4, emprunt.getUsager().getId());

      statement.executeUpdate();

      Statement statement2 = conn.createStatement();
      ResultSet resultSet = statement2.executeQuery("SELECT MAX(id_livre) FROM livre");
      int id = 0;
      while (resultSet.next()) {
        id = resultSet.getInt("MAX(id_livre)");
      }
      return id;
    } catch (SQLException e) {
      System.err.println("Error while inserting a new book");
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public void deleteOne(int id) {
  }

  @Override
  // rendre livre => update date_retour
  public void updateOne(Emprunt emprunt) {
    Database db = new Database();
    try {
      db.connect();
      Connection conn = db.getConn();
      PreparedStatement statement = conn.prepareStatement(
          "UPDATE emprunt SET date_retour = NOW() WHERE id_livre = ?");
      statement.setInt(1, emprunt.getLivre().getId());

      int rowsUpdated = statement.executeUpdate();
      if (rowsUpdated > 0) {
        System.out.println("An existing book was updated successfully!");
      } else {
        System.out.println("An existing book was not updated successfully!");
      }

    } catch (SQLException e) {
      System.err.println("Error while updating a book");
      e.printStackTrace();
    }
  }

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

    FilteredList<EmpruntForHome> flLivre = new FilteredList<>(emprunts, p -> true);// Pass the data to a filtered list
    table.setItems(flLivre);// Set the table's items using the filtered list
    table.getColumns().addAll(idCol, titreCol, auteurCol, isbnCol, dateEmpruntCol, dateRetourCol, emailCol);

    ChoiceBox<String> choiceBox = new ChoiceBox<>();
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

    Button deleteButton = new Button("Delete");
    Button rendreButton = new Button("Rendre");
    Button addButton = new Button("Add");

    rendreButton.setDisable(true);
    deleteButton.setDisable(true);

    addButton.setOnAction(e -> {
      Stage dialog = new Stage();

      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.setTitle("Add a book");
      dialog.setMinWidth(400);
      dialog.setMinHeight(400);
      Label label1 = new Label("Titre");
      TextField textField1 = new TextField();
      Label label2 = new Label("Auteur");
      TextField textField2 = new TextField();
      Label label3 = new Label("Isbn");
      TextField textField3 = new TextField();
      Button button = new Button("Add");
      VBox vBox = new VBox(label1, textField1, label2, textField2, label3,
          textField3, button);

      Scene myDialogScene = new Scene(vBox);

      button.setOnAction(e1 -> {

        Emprunt emprunt = new Emprunt(textField1.getText(), textField2.getText(),
            textField3.getText());

        int id = addOne(emprunt);
        emprunt.setId(id);

        emprunts.add(emprunt);
        dialog.close();
      });

      dialog.setScene(myDialogScene);
      dialog.show();

    });

    rendreButton.setOnAction(e -> {
      Stage dialog = new Stage();

      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.setTitle("Rendre un livre");
      dialog.setMinWidth(400);
      dialog.setMinHeight(400);
      Label label1 = new Label("Titre");
      TextField textField1 = new TextField(table.getSelectionModel().getSelectedItem().getTitre());
      Label label2 = new Label("Auteur");
      TextField textField2 = new TextField(table.getSelectionModel().getSelectedItem().getAuteur());
      Label label3 = new Label("Isbn");
      TextField textField3 = new TextField(table.getSelectionModel().getSelectedItem().getIsbn());
      Button button = new Button("Edit");
      VBox vBox = new VBox(label1, textField1, label2, textField2, label3, textField3, button);

      Scene myDialogScene = new Scene(vBox);

      button.setOnAction(e1 -> {
        Emprunt emprunt = new Emprunt(table.getSelectionModel().getSelectedItem().getId(), textField1.getText(),
            textField2.getText(),
            textField3.getText());

        updateOne(emprunt);

        emprunts.set(emprunts.indexOf(table.getSelectionModel().getSelectedItem()), emprunt);
        dialog.close();
      });

      dialog.setScene(myDialogScene);
      dialog.show();

    });

    deleteButton.setOnAction(e -> {
      Stage dialog = new Stage();

      dialog.initModality(Modality.APPLICATION_MODAL);

      Text text = new Text("Are you sure you want to delete this emprunt?");
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
        // Livre livre = table.getSelectionModel().getSelectedItem();
        // deleteOne(livre.getId());
        // livres.remove(livre);
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
        rendreButton.setDisable(false);
        deleteButton.setDisable(false);
      } else {
        rendreButton.setDisable(true);
        deleteButton.setDisable(true);
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
    vbox.getChildren().addAll(label, table, hBox, addButton, rendreButton, deleteButton);

    StackPane root = (StackPane) scene.getRoot();
    root.getChildren().addAll(vbox);

    return scene;
  }

}
