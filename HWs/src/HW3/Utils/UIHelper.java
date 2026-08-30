package HW3.Utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class UIHelper {

	// make a button with an action
    public static Button createButton(String text, Runnable onPress) {
    	Button button = new Button(text);
        
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMinHeight(40);
        
        button.getStyleClass().add("custom-button");
        button.setOnAction(e -> onPress.run());
        return button;
    }
    
    public static Button createBackButton() {
    	return createButton("Back", MenuManager::goBack);
    }
    
    // Makes a simple VBox with increased breathing room
    public static VBox createVRoot() {
        VBox root = new VBox(15); 
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("main-root");
        return root;
    }
    
    // Makes a simple VBox with a styled title
    public static VBox createVRoot(String title) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("header-title");
        
        VBox root = new VBox(15, titleLabel);
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("main-root");
        return root;
    }
    
    // Setup a uniform form layout with Submit and Back buttons
    public static VBox createBaseFormLayout(String titleText, GridPane grid, Runnable onSubmit, Runnable onBack) {
        Label titleLabel = new Label(titleText);
        titleLabel.getStyleClass().add("form-title");

        Button submitBtn = createButton("Submit", onSubmit);
        Button backBtn = createButton("Back", onBack);
        submitBtn.setMaxWidth(120);
        backBtn.setMaxWidth(120);
        submitBtn.getStyleClass().add("submit-button");

        HBox buttonBox = new HBox(15, submitBtn, backBtn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        VBox root = new VBox(25, titleLabel, grid, buttonBox);
        root.setPadding(new Insets(25));
        root.getStyleClass().add("form-root");
        return root;
    }

    // Create default Grid with wider gaps for readability
    public static GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.getStyleClass().add("form-grid");
        return grid;
    }
}