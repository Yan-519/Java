package HW3.Menus;

import HW3.DeliveryDataBase;
import HW3.Utils.UIHelper;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerUI extends UIBase  {

	public CustomerUI(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
	}

	@Override
	public void Init() {
        VBox root = UIHelper.createVRoot();

        Label title = new Label("Customer Login");

        TextField code = new TextField();
        code.setPromptText("Customer Code");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        Button loginButton = UIHelper.createButton("Login");
        Button backButton = UIHelper.createButton("Back");

//        loginButton.setOnAction(e -> {
//
//            // TODO:
//            // Authenticate customer using your DeliverySystem.
//
//            showCustomerScreen();
//        });

        backButton.setOnAction( e -> backF.run());

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
