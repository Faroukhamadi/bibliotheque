package org.farouk_maram.controllers;

import org.farouk_maram.App;

import javafx.beans.property.SimpleStringProperty;
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

public class Home extends App {
    private TableView<Person> table = new TableView<Person>();
    private final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("Jacob", "Smith", "jacob.smith@example.com"),
            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
            new Person("Ethan", "Williams", "ethan.williams@example.com"),
            new Person("Emma", "Jones", "emma.jones@example.com"),
            new Person("Michael", "Brown", "michael.brown@example.com"));

    public Home(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        StackPane stackPane = new StackPane();

        Scene scene = new Scene(stackPane, 640, 480);

        stage.setTitle("Search all books");
        stage.setMinHeight(800);
        stage.setMinWidth(800);

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName"));

        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));

        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("email"));

        FilteredList<Person> flPerson = new FilteredList(data, p -> true);// Pass the data to a filtered list
        table.setItems(flPerson);// Set the table's items using the filtered list
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

        // Adding ChoiceBox and TextField here!
        ChoiceBox<String> choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("First Name", "Last Name", "Email");
        choiceBox.setValue("First Name");

        TextField textField = new TextField();
        textField.setPromptText("Search here!");
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (choiceBox.getValue())// Switch on choiceBox value
            {
                case "First Name":
                    flPerson.setPredicate(p -> p.getFirstName().toLowerCase().contains(newValue.toLowerCase().trim()));// filter
                                                                                                                       // table
                                                                                                                       // by
                                                                                                                       // first
                                                                                                                       // name
                    break;
                case "Last Name":
                    flPerson.setPredicate(p -> p.getLastName().toLowerCase().contains(newValue.toLowerCase().trim()));// filter
                                                                                                                      // table
                                                                                                                      // by
                                                                                                                      // last
                                                                                                                      // name
                    break;
                case "Email":
                    flPerson.setPredicate(p -> p.getEmail().toLowerCase().contains(newValue.toLowerCase().trim()));// filter
                                                                                                                   // table
                                                                                                                   // by
                                                                                                                   // email
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

    public static class Person {
        private final SimpleStringProperty firstName = new SimpleStringProperty();
        private final SimpleStringProperty lastName = new SimpleStringProperty();
        private final SimpleStringProperty email = new SimpleStringProperty();

        private Person(String fName, String lName, String email) {
            this.firstName.setValue(fName);
            this.lastName.setValue(lName);
            this.email.setValue(email);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public SimpleStringProperty getFirstNameProperty() {
            return firstName;
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String fName) {
            lastName.set(fName);
        }

        public SimpleStringProperty getLastNameProperty() {
            return lastName;
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String fName) {
            email.set(fName);
        }

        public SimpleStringProperty getEmailProperty() {
            return email;
        }
    }
}
