package Selector;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    // 1. Define your Data Model
    public static class Person {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty name;

        public Person(int id, String name) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
        }

        public int getId() { return id.get(); }
        public String getName() { return name.get(); }
    }

    @Override
    public void start(Stage primaryStage) {
        TableView<Person> table = new TableView<>();

        // 2. Configure Selection Model
        // Change SelectionMode.MULTIPLE to SelectionMode.SINGLE if you only want one row selected at a time
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); 

        // 3. Listen for row selection changes
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Selected Person: " + newSelection.getName());
            }
        });

        // 4. Define Columns
        TableColumn<Person, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> data.getValue().id.asObject());

        TableColumn<Person, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data -> data.getValue().name);

        table.getColumns().addAll(idColumn, nameColumn);

        // 5. Populate Data
        ObservableList<Person> data = FXCollections.observableArrayList(
                new Person(1, "Alice"),
                new Person(2, "Bob"),
                new Person(3, "Charlie")
        );
        table.setItems(data);

        // UI Setup
        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Table Selection");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
