package HW3.Utils;


import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
}
