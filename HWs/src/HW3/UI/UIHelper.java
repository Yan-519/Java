package HW3.UI;

import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

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
}
