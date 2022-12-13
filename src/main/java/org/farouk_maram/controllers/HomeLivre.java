package org.farouk_maram.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeLivre extends App implements HomeCRUD<Livre> {
    private TableView<Livre> table = new TableView<>();
    private final ObservableList<Livre> livres = FXCollections.observableArrayList();

    @Override
    public int addOne(Livre livre) {
        Database db = new Database();
        try {
            db.connect();
            Connection conn = db.getConn();
            PreparedStatement statement = conn
                    .prepareStatement("INSERT INTO livre (titre, auteur, isbn) VALUES (?, ?, ?)");
            statement.setString(1, livre.getTitre());
            statement.setString(2, livre.getAuteur());
            statement.setString(3, livre.getIsbn());

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

    public boolean hasActiveBorrow(int id) {
        Database db = new Database();
        try {
            db.connect();
            Connection conn = db.getConn();
            PreparedStatement statement = conn
                    .prepareStatement("SELECT * FROM emprunt WHERE livre_id = ? and date_retour IS NULL");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error while fetching all books");
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasActiveBorrowExpired(int id) {
        Database db = new Database();
        try {
            db.connect();
            Connection conn = db.getConn();
            PreparedStatement statement = conn
                    .prepareStatement(
                            "SELECT * FROM emprunt WHERE livre_id = ? and date_retour IS NULL and date_emprunt < DATE_SUB(NOW(), INTERVAL 1 YEAR)");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error while fetching all books");
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Integer> getIdsByIsbn(String isbn) {
        Database db = new Database();
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            db.connect();
            Connection conn = db.getConn();
            PreparedStatement statement = conn
                    .prepareStatement(
                            "SELECT id_livre FROM livre where isbn = ?");
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getInt("id_livre"));
            }
            System.out.println("ids: " + ids);
            return ids;
        } catch (SQLException e) {
            System.err.println("Error while fetching all books");
            e.printStackTrace();
            return ids;
        }
    }

    public void deleteOneLivre(String isbn, int id) {
        Database db = new Database();
        try {
            db.connect();
            Connection conn = db.getConn();
            // PreparedStatement statement = conn.prepareStatement("DELETE FROM livre WHERE
            // id_livre = ?");
            PreparedStatement statement = conn.prepareStatement("DELETE FROM livre WHERE isbn = ?");
            PreparedStatement statement2 = conn.prepareStatement("DELETE FROM emprunt WHERE livre_id = ?");
            statement2.setInt(1, id);
            statement.setString(1, isbn);
            statement2.executeUpdate();
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A book was deleted successfully!");
            } else {
                System.out.println("A book was not deleted successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching all books");
            e.printStackTrace();
        }

    }

    @Override
    public void deleteOne(int id) {
        Database db = new Database();
        try {
            db.connect();
            Connection conn = db.getConn();
            // PreparedStatement statement = conn.prepareStatement("DELETE FROM livre WHERE
            // id_livre = ?");
            PreparedStatement statement = conn.prepareStatement("DELETE FROM livre WHERE isbn = ?");
            PreparedStatement statement2 = conn.prepareStatement("DELETE FROM emprunt WHERE livre_id = ?");
            statement2.setInt(1, id);
            statement.setInt(1, id);
            statement2.executeUpdate();
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A book was deleted successfully!");
            } else {
                System.out.println("A book was not deleted successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching all books");
            e.printStackTrace();
        }

    }

    public void deleteOneExemplaire(String id) {
        Database db = new Database();
        try {
            db.connect();
            Connection conn = db.getConn();
            // PreparedStatement statement = conn.prepareStatement("DELETE FROM livre WHERE
            // id_livre = ?");
            PreparedStatement statement = conn.prepareStatement("DELETE FROM livre WHERE id_livre = ?");
            PreparedStatement statement2 = conn.prepareStatement("DELETE FROM emprunt WHERE livre_id = ?");
            statement2.setString(1, id);
            statement.setString(1, id);
            statement2.executeUpdate();
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A book was deleted successfully!");
            } else {
                System.out.println("A book was not deleted successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching all books");
            e.printStackTrace();
        }

    }

    @Override
    public void updateOne(Livre livre) {
        Database db = new Database();
        try {
            db.connect();
            Connection conn = db.getConn();
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id_livre = ?");
            statement.setString(1, livre.getTitre());
            statement.setString(2, livre.getAuteur());
            statement.setString(3, livre.getIsbn());
            statement.setInt(4, livre.getId());

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
        isbnCol.setMinWidth(200);
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<Livre, String>("isbn"));

        FilteredList<Livre> flLivre = new FilteredList<>(livres, p -> true);

        table.setItems(flLivre);// Set the table's items using the filtered list
        table.getColumns().addAll(idCol, titreCol, auteurCol, isbnCol);

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Id", "Titre", "Auteur", "Isbn");
        choiceBox.setValue("Titre");

        TextField textField = new TextField();
        textField.setPromptText("Search here!");
        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            switch (choiceBox.getValue()) {
                case "Id":
                    flLivre.setPredicate(
                            p -> Integer.toString(p.getId()).toLowerCase().contains(newVal.toLowerCase().trim()));
                    break;
                case "Titre":
                    flLivre.setPredicate(p -> p.getTitre().toLowerCase().contains(newVal.toLowerCase().trim()));
                    break;
                case "Auteur":
                    flLivre.setPredicate(p -> p.getAuteur().toLowerCase().contains(newVal.toLowerCase().trim()));
                    break;
                case "Isbn":
                    flLivre.setPredicate(p -> p.getIsbn().toLowerCase().contains(newVal.toLowerCase().trim()));
                    break;
            }
        });

        Button editButton = new Button("Edit");
        Button addButton = new Button("Ajouter");
        Button deleteButton = new Button("Supprimer Livre");
        Button deleteExemplaireButton = new Button("Supprimer Exemplaire");

        editButton.setDisable(true);
        deleteButton.setDisable(true);
        deleteExemplaireButton.setDisable(true);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {// reset table and
            if (newVal != null) {
                textField.setText("");
            }
        });

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
            Button button = new Button("Ajouter");
            button.setMinSize(100, 50);

            VBox vBox = new VBox(label1, textField1, label2, textField2, label3, textField3, button);
            // center the VBox
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(10);
            vBox.setPadding(new Insets(10, 10, 10, 10));

            Scene myDialogScene = new Scene(vBox);

            button.setOnAction(e1 -> {

                Livre livre = new Livre(textField1.getText(), textField2.getText(),
                        textField3.getText());

                int id = addOne(livre);
                livre.setId(id);

                livres.add(livre);
                dialog.close();
            });

            myDialogScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            dialog.setScene(myDialogScene);
            dialog.show();

        });

        editButton.setOnAction(e -> {
            Stage dialog = new Stage();

            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Edit a book");
            dialog.setMinWidth(400);
            dialog.setMinHeight(400);
            Label label1 = new Label("Titre");
            TextField textField1 = new TextField(table.getSelectionModel().getSelectedItem().getTitre());
            Label label2 = new Label("Auteur");
            TextField textField2 = new TextField(table.getSelectionModel().getSelectedItem().getAuteur());
            Label label3 = new Label("Isbn");
            TextField textField3 = new TextField(table.getSelectionModel().getSelectedItem().getIsbn());
            Button button = new Button("Edit");
            button.setMinSize(100, 50);

            VBox vBox = new VBox(label1, textField1, label2, textField2, label3, textField3, button);
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(10);
            vBox.setPadding(new Insets(10, 10, 10, 10));

            Scene myDialogScene = new Scene(vBox);

            button.setOnAction(e1 -> {
                Livre livre = new Livre(table.getSelectionModel().getSelectedItem().getId(), textField1.getText(),
                        textField2.getText(),
                        textField3.getText());

                updateOne(livre);

                livres.set(livres.indexOf(table.getSelectionModel().getSelectedItem()), livre);
                dialog.close();
            });

            myDialogScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            dialog.setScene(myDialogScene);
            dialog.show();

        });

        deleteButton.setOnAction(e -> {
            Stage dialog = new Stage();

            dialog.initModality(Modality.APPLICATION_MODAL);

            Text text = new Text("Are you sure you want to delete all copies of this book?");
            text.setFont(new Font("Arial", 20));
            text.setFill(Color.RED);
            Text text2 = new Text();
            text.setFont(new Font("Arial", 20));
            text2.setFont(new Font("Arial", 15));

            Button yesButton = new Button("Yes");
            Button noButton = new Button("No");

            yesButton.setMinSize(100, 50);
            noButton.setMinSize(100, 50);

            HBox hBox = new HBox(yesButton, noButton);
            hBox.setSpacing(30);
            hBox.setAlignment(Pos.CENTER);
            VBox vBox = new VBox(text, text2, hBox);
            vBox.setSpacing(30);
            vBox.setAlignment(Pos.CENTER);
            vBox.setPadding(new Insets(10));
            Scene myDialogScene = new Scene(vBox);

            String selectedItemIsbn = table.getSelectionModel().getSelectedItem().getIsbn();

            ArrayList<Integer> ids = getIdsByIsbn(selectedItemIsbn);

            for (int id : ids) {
                if (hasActiveBorrow(id) && !hasActiveBorrowExpired(id)) {
                    text.setText("Warning: There are active borrows of this book that are less than 1 year old!");
                    text2.setText("Are you sure you want to delete all copies of this book?");
                    break;
                } else if (hasActiveBorrow(id)) {
                    text.setText(
                            "There are active borrows of this book that might have been lost. Are you sure you want to delete all copies of this book?");
                    break;
                }
            }
            yesButton.setOnAction(e1 -> {
                Livre livre = table.getSelectionModel().getSelectedItem();
                // deleteOne(livre.getId());
                ids.forEach(id -> {
                    deleteOneLivre(selectedItemIsbn, id);
                });
                livres.remove(livre);
                // remove all exemplaires from livres
                livres.removeIf(l -> l.getIsbn().equals(selectedItemIsbn));
                // livres.removeIf(l -> l.getIsbn().equals(selectedItemIsbn));

                dialog.close();
            });

            noButton.setOnAction(e1 -> {
                dialog.close();
            });

            myDialogScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            dialog.setScene(myDialogScene);
            dialog.show();

        });

        deleteExemplaireButton.setOnAction(e -> {
            Stage dialog = new Stage();

            dialog.initModality(Modality.APPLICATION_MODAL);

            Text text = new Text("Are you sure you want to delete this copy of the book?");
            text.setFont(new Font("Arial", 20));
            text.setFill(Color.RED);
            Text text2 = new Text();
            text.setFont(new Font("Arial", 20));

            Button yesButton = new Button("Yes");
            Button noButton = new Button("No");

            text2.setFont(new Font("Arial", 15));

            yesButton.setMinSize(100, 50);
            noButton.setMinSize(100, 50);

            HBox hBox = new HBox(yesButton, noButton);
            hBox.setSpacing(30);
            hBox.setAlignment(Pos.CENTER);
            VBox vBox = new VBox(text, text2, hBox);
            vBox.setSpacing(30);
            vBox.setAlignment(Pos.CENTER);
            vBox.setPadding(new Insets(10));
            Scene myDialogScene = new Scene(vBox);

            int selectedItemId = table.getSelectionModel().getSelectedItem().getId();

            if (hasActiveBorrow(selectedItemId) && !hasActiveBorrowExpired(selectedItemId)) {
                text.setText("Warning: There is an active borrow of this copy that is less than 1 year old!");
                text2.setText("Are you sure you want to delete it?");
            } else if (hasActiveBorrow(selectedItemId)) {
                text.setText(
                        "There is an active borrow of this copy that might have been lost. Are you sure you want to delete it?");
            }

            yesButton.setOnAction(e1 -> {
                Livre livre = table.getSelectionModel().getSelectedItem();
                deleteOne(livre.getId());
                livres.remove(livre);
                dialog.close();
            });

            noButton.setOnAction(e1 -> {
                dialog.close();
            });

            myDialogScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            dialog.setScene(myDialogScene);
            dialog.show();

        });

        // disable button if no row is selected and enable if a row is selected
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
                deleteExemplaireButton.setDisable(false);
            } else {
                editButton.setDisable(true);
                deleteButton.setDisable(true);
                deleteExemplaireButton.setDisable(true);
            }
        });

        Hyperlink link = new Hyperlink("<- Back to welcome page");
        link.setStyle("-fx-text-fill: #040f16; -fx-margin: 10; -fx-padding: 5;");
        link.setFont(new Font("Arial", 20));
        link.setOnAction(e -> {
            changeScencesToWelcome();
        });

        HBox hBox = new HBox(choiceBox, textField);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        final VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hBox, addButton, editButton, deleteButton, deleteExemplaireButton,
                link);

        StackPane root = (StackPane) scene.getRoot();
        root.getChildren().addAll(vbox);

        return scene;
    }

}
