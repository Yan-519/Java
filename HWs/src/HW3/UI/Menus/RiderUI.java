package HW3.UI.Menus;

import HW3.DeliveryDataBase;
import HW3.UI.UIHelper;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RiderUI extends UIBase  {

	public RiderUI(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
	}

	@Override
	public void Init() {
	        VBox root = UIHelper.createVRoot();

	        Label title = new Label("Rider Login");

	        TextField code = new TextField();
	        code.setPromptText("Rider Code");

	        PasswordField password = new PasswordField();
	        password.setPromptText("Password");

	        Button loginButton = UIHelper.createButton("Login");
	        Button backButton = UIHelper.createButton("Back");

	        loginButton.setOnAction(e -> {

	            // TODO:
	            // Authenticate rider.

	            Main();
	        });

	        backButton.setOnAction(e ->
	                backF.run()
	        );

	        root.getChildren().addAll(
	                title,
	                code,
	                password,
	                loginButton,
	                backButton
	        );

	        UIHelper.setScene(stage, root, 500, 400);
	    

		
	}

	@Override
	protected void Main() {
		// TODO Auto-generated method stub
		
	}

	
	
}
