package org.farouk_maram.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.farouk_maram.App;
import org.farouk_maram.Entities.Livre;
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

public class HomeLivre extends App implements ManyFetcher {
    private TableView<Livre> table = new TableView<>();
    private final ObservableList<Livre> livres = FXCollections.observableArrayList();

    @Override
    public void fetchAll() {
        Database db = new Database();
        try {
            db.connect();
            Connection conn = db.getConn();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM livre");
            while (resultSet.next()) {
                int id = resultSet.getInt("id_livre");
                String titre = resultSet.getString("titre");
                String auteur = resultSet.getString("auteur");
                String isbn = resultSet.getString("isbn");
                Livre livre = new Livre(id, titre, auteur, isbn);
                livres.add(livre);
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching all books");
            e.printStackTrace();
        }
    }

    public HomeLivre(Stage stage) {
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

        TableColumn<Livre, String> idCol = new TableColumn<>("Id");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
                new PropertyValueFactory<Livre, String>("id"));

        TableColumn<Livre, String> titreCol = new TableColumn<>("Titre");
        titreCol.setMinWidth(100);
        titreCol.setCellValueFactory(
                new PropertyValueFactory<Livre, String>("titre"));

        TableColumn<Livre, String> auteurCol = new TableColumn<>("Auteur");
        auteurCol.setMinWidth(200);
        auteurCol.setCellValueFactory(
                new PropertyValueFactory<Livre, String>("auteur"));

        TableColumn<Livre, String> isbnCol = new TableColumn<>("Isbn");
        auteurCol.setMinWidth(200);
        auteurCol.setCellValueFactory(
                new PropertyValueFactory<Livre, String>("isbn"));

        FilteredList<Livre> flLivre = new FilteredList(livres, p -> true);// Pass the data to a filtered list
        table.setItems(flLivre);// Set the table's items using the filtered list
        table.getColumns().addAll(idCol, titreCol, auteurCol, isbnCol);

        // Adding ChoiceBox and TextField here!
        ChoiceBox<String> choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("Id", "Titre", "Auteur", "Isbn");
        choiceBox.setValue("Titre");

        TextField textField = new TextField();
        textField.setPromptText("Search here!");
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (choiceBox.getValue()) {
                case "Id":
                    flLivre.setPredicate(
                            p -> Integer.toString(p.getId()).toLowerCase()
                                    .contains(newValue.toLowerCase().trim()));
                    break;
                case "Titre":
                    flLivre.setPredicate(p -> p.getTitre().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
                case "Auteur":
                    flLivre.setPredicate(p -> p.getAuteur().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
                case "Isbn":
                    flLivre.setPredicate(p -> p.getIsbn().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
            }
        });

        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {// reset table and
            if (newVal != null) {
                textField.setText("");
            }
        });

        HBox hBox = new HBox(choiceBox, textField);// Add choiceBox and textField to hBox
        hBox.setAlignment(Pos.CENTER);// Center HBox
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hBox);

        StackPane root = (StackPane) scene.getRoot();
        root.getChildren().addAll(vbox);

        return scene;
    }

}
