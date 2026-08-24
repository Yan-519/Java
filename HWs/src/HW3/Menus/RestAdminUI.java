package HW3.Menus;

import HW3.DeliveryDataBase;
import HW3.Exceptions.RestAdminNotFoundException;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RestAdminUI extends UIBase  {

	public RestAdminUI(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
	}

	@Override
	public void Auth() {
        VBox root = UIHelper.createVRoot();

        Label title = new Label("System Administrator Login");

        TextField username = new TextField();
        username.setPromptText("Username");
        username.setMaxWidth(300);

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(300);

        Button loginButton = UIHelper.createButton("Login", () -> {
        	try {
				deliveryDataBase.getRestAdmin(username.getText(), password.getText());
	            Main();
			} catch (RestAdminNotFoundException e) {
				MessageBox.error(e);
			}
        });
        
        Button backButton = UIHelper.createButton("Back", backF);
        
        root.getChildren().addAll(
                title,
                username,
                password,
                loginButton,
                backButton
        );

        stage.setScene(new Scene(root, 500, 400));
	}

	@Override
	protected void Main() {
		// TODO Auto-generated method stub
		
	}

}
