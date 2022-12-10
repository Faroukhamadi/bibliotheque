
package org.farouk_maram.controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.farouk_maram.App;
import org.farouk_maram.Entities.Emprunt;
import org.farouk_maram.Entities.EmpruntForHome;
import org.farouk_maram.db.Database;
import org.farouk_maram.interfaces.HomeCRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

// change select query to sort by date
public class HomeEmpruntHistory extends App implements HomeCRUD<Emprunt> {
  @Override
  public int addOne(Emprunt emprunt) {
    Database db = new Database();
    try {
      db.connect();
      Connection conn = db.getConn();
      PreparedStatement statement = conn
          .prepareStatement("INSERT INTO emprunt (date_emprunt, livre_id, usager_id) VALUES (?, ?, ?)");
      statement.setDate(1, emprunt.getDateEmprunt());
      statement.setInt(2, emprunt.getLivreId());
      statement.setInt(3, emprunt.getUsagerId());

      statement.executeUpdate();

      Statement statement2 = conn.createStatement();
      ResultSet resultSet = statement2.executeQuery("SELECT MAX(id_emprunt) FROM emprunt");
      int id = 0;
      while (resultSet.next()) {
        id = resultSet.getInt("MAX(id_emprunt)");
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
  public void updateOne(Emprunt t) {
  }

  // rendre livre => update date_retour
  public void updateOne(int id) {
    Database db = new Database();
    try {
      db.connect();
      Connection conn = db.getConn();
      System.out.println("livre id: " + id);
      PreparedStatement statement = conn.prepareStatement(
          "UPDATE emprunt SET date_retour = NOW() WHERE id_emprunt = ?");
      statement.setInt(1, id);

      int rowsUpdated = statement.executeUpdate();
      if (rowsUpdated > 0) {
        System.out.println("An existing emprunt was updated successfully!");
      } else {
        System.out.println("An existing emprunt was not updated successfully!");
      }

    } catch (SQLException e) {
      System.err.println("Error while updating an emprunt");
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
              "select * from emprunt e, livre l, usager u where e.livre_id = l.id_livre and u.id_usager = e.usager_id and date_retour is not null order by date_emprunt desc");
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
      System.err.println("Error while fetching all emprunts");
      e.printStackTrace();
    }
  }

  public HomeEmpruntHistory(Stage stage) {
    this.stage = stage;
  }

  public Scene getScene() {
    fetchAll();

    StackPane stackPane = new StackPane();

    Scene scene = new Scene(stackPane, 640, 480);

    stage.setTitle("Historique Emprunts");
    stage.setMinHeight(800);
    stage.setMinWidth(800);

    final Label label = new Label("Historique Emprunts");
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

    Button addButton = new Button("Add");

    addButton.setOnAction(e -> {
      Stage dialog = new Stage();

      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.setTitle("Add a book");
      dialog.setMinWidth(400);
      dialog.setMinHeight(400);
      Label label1 = new Label("Date Emprunt");
      TextField textField1 = new TextField();
      Label label2 = new Label("Email");
      TextField textField2 = new TextField();
      Label label3 = new Label("Isbn");
      TextField textField3 = new TextField();

      Button button = new Button("Add");

      VBox vBox = new VBox(label1, textField1, label2, textField2, label3, textField3, button);

      Scene myDialogScene = new Scene(vBox);

      button.setOnAction(e1 -> {

        String dateEmprunt = textField1.getText();
        String email = textField2.getText();
        String isbn = textField3.getText();

        int idLivre = -1;
        int idUsager = -1;
        // fetch id of livre by isbn from database
        Database db = new Database();
        try {
          db.connect();
          Connection conn = db.getConn();
          PreparedStatement ps = conn.prepareStatement("SELECT id_livre FROM livre WHERE isbn = ?");
          PreparedStatement ps1 = conn.prepareStatement("SELECT id_usager FROM usager WHERE email = ?");

          ps.setString(1, isbn);
          ps1.setString(1, email);

          ResultSet rs = ps.executeQuery();
          ResultSet rs1 = ps1.executeQuery();
          if (rs.next()) {
            idLivre = rs.getInt("id_livre");
            if (rs1.next()) {
              idUsager = rs1.getInt("id_usager");
            }
          }
        } catch (SQLException e2) {
          e2.printStackTrace();
        }

        if (idLivre == -1) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error");
          alert.setHeaderText("Livre not found");
          alert.setContentText("Livre with isbn " + isbn + " not found");
          alert.showAndWait();
          return;
        } else if (idUsager == -1) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error");
          alert.setHeaderText("Usager not found");
          alert.setContentText("Usager with email " + email + " not found");
          alert.showAndWait();
          return;
        }

        Emprunt emprunt = new Emprunt(0, Date.valueOf(dateEmprunt), null, idLivre, idUsager);

        int id = addOne(emprunt);
        emprunt.setId(id);

        // select livre.titre, livre.auteur from livre where livre.id_livre =
        // emprunt.id_livre;
        String titre = "";
        String auteur = "";
        try {
          db.connect();
          Connection conn = db.getConn();
          PreparedStatement ps = conn.prepareStatement(
              "SELECT titre, auteur FROM livre WHERE id_livre = ?");
          ps.setInt(1, idLivre);
          ResultSet rs = ps.executeQuery();
          if (rs.next()) {
            titre = rs.getString("titre");
            auteur = rs.getString("auteur");
          }
        } catch (SQLException e2) {
          e2.printStackTrace();
        }

        EmpruntForHome empruntForHome = new EmpruntForHome(emprunt, titre, auteur, isbn, email);

        emprunts.add(empruntForHome);
        dialog.close();
      });

      dialog.setScene(myDialogScene);
      dialog.show();

    });

    // disable button if no row is selected and enable if a row is selected

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
    vbox.getChildren().addAll(label, table, hBox, addButton);

    StackPane root = (StackPane) scene.getRoot();
    root.getChildren().addAll(vbox);

    return scene;
  }

}
