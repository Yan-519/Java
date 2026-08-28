package HW3.Utils;


import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class UIHelper {

	// make standard action
    private static Button createButton(String text) {
        Button button = new Button(text);

        button.setPrefWidth(250);
        button.setPrefHeight(50);

        return button;
    }

    // make button with action
    public static Button createButton(String text, Consumer<ActionEvent> onPress) {
        Button button = createButton(text);
        button.setOnAction(e -> onPress.accept(e));

        return button;
    }

    // make button with action
    public static Button createButton(String text, Runnable onPress) {
        Button button = createButton(text);
        button.setOnAction(e -> onPress.run());

        return button;
    }
    
    // makes a simple VBox
    public static VBox createVRoot() {
    	VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        return root;
    }
    
    // makes a simple VBox with title
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

    // create default Grid
    public static GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        return grid;
    }

}
