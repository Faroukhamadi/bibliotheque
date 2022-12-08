package org.farouk_maram.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.farouk_maram.App;
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

public class HomeLivre extends App implements HomeCRUD {
    @Override
    public void addOne(int id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteOne(int id) {
        Database db = new Database();
        try {
            db.connect();
            Connection conn = db.getConn();
            PreparedStatement statement = conn.prepareStatement("DELETE FROM livre WHERE id_livre = ?");
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A user was deleted successfully!");
            } else {
                System.out.println("No user was deleted.");
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching all books");
            e.printStackTrace();
        }

    }

    @Override
    public void updateOne(int id) {
        // TODO Auto-generated method stub

    }

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

        Button deleteButton = new Button("Delete");
        Button editButton = new Button("Edit");
        Button addButton = new Button("Add");

        deleteButton.setDisable(true);
        editButton.setDisable(true);
        addButton.setDisable(true);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {// reset table and
            if (newVal != null) {
                textField.setText("");
            }
        });
        addButton.setOnAction(e -> {
            System.out.println(table.getSelectionModel().getSelectedItem());
            // AddLivre addLivre = new AddLivre(stage);
            // stage.setScene(addLivre.getScene());
        });

        editButton.setOnAction(e -> {
            System.out.println(table.getSelectionModel().getSelectedItem());
            // EditLivre editLivre = new EditLivre(stage,
            // table.getSelectionModel().getSelectedItem());
            // stage.setScene(editLivre.getScene());
        });

        deleteButton.setOnAction(e -> {
            System.out.println(table.getSelectionModel().getSelectedItem());

            Stage dialog = new Stage();

            dialog.initModality(Modality.APPLICATION_MODAL);

            Text text = new Text("Are you sure you want to delete this book?");
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
                Livre livre = table.getSelectionModel().getSelectedItem();
                deleteOne(livre.getId());
                dialog.close();
            });

            noButton.setOnAction(e1 -> {
                dialog.close();
            });

            dialog.setScene(myDialogScene);
            dialog.show();

        });
        // disable button if no row is selected and enable if a row is selected
        table.getSelectionModel().selectedItemProperty().addListener((obs,
                oldSelection, newSelection) -> {
            addButton.setDisable(false);
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        });

        HBox hBox = new HBox(choiceBox, textField);// Add choiceBox and textField to hBox
        hBox.setAlignment(Pos.CENTER);// Center HBox
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hBox, addButton, editButton, deleteButton);

        StackPane root = (StackPane) scene.getRoot();
        root.getChildren().addAll(vbox);

        return scene;
    }

}
