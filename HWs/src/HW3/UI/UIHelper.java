package HW3.UI;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class UIHelper {

    public static Button createButton(String text) {
        Button button = new Button(text);

        button.setPrefWidth(250);
        button.setPrefHeight(50);

        return button;
    }

    public static void setScene(
    		Stage stage,
            Parent root,
            double width,
            double height) {

        stage.setScene(
                new Scene(root, width, height)
        );
    }


    public static Button createButton(String text, Consumer<ActionEvent> onPress) {
        Button button = createButton(text);
        button.setOnAction(e -> onPress.accept(e));

        return button;
    }
    
    public static Button createButton(String text, Runnable onPress) {
        Button button = createButton(text);
        button.setOnAction(e -> onPress.run());

        return button;
    }
    
    public static VBox createVRoot() {
    	VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        return root;
    }
    
    public static VBox createVRoot(String title) {
    	VBox root = new VBox(new Label(title));
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        return root;
    }
    
    // Helper method to setup a uniform form layout with Submit and Back buttons
    public static VBox createBaseFormLayout(String titleText, GridPane grid, Runnable onSubmit, Runnable onBack) {
        Label titleLabel = new Label(titleText);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button submitBtn = new Button("Submit");
        Button backBtn = new Button("Back");

        submitBtn.setOnAction(e -> onSubmit.run());
        backBtn.setOnAction(e -> onBack.run());

        HBox buttonBox = new HBox(15, submitBtn, backBtn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        VBox root = new VBox(20, titleLabel, grid, buttonBox);
        root.setPadding(new Insets(20));
        return root;
    }

    public static GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        return grid;
    }
    
    public static void showList(Stage stage, List<?> lst, Runnable backF) {
    	if(lst.isEmpty()) {
    		MessageBox.Info("No values found");
    		return;
    	}
    	
    	
    	List<String> strL = lst.stream().map(Object::toString).toList();
    	ObservableList<String> observableCustomers = FXCollections.observableArrayList(strL);

    	ListView<String> listView = new ListView<>(observableCustomers);
    	
    	
    	Button backButton = UIHelper.createButton("Back", e -> backF.run());
    	
    	VBox root = UIHelper.createVRoot();
    	
    	root.getChildren().addAll(listView, backButton);
    	
    	UIHelper.setScene(stage, root, 300, 250);

    }
}
