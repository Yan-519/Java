package HW3.Menus;

import HW3.DeliveryDataBase;
import HW3.DataObjects.Rider;
import HW3.Exceptions.RiderNotFoundException;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RiderUI extends UIBase  {
	
	private Rider rider;

	public RiderUI(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
	}

	@Override
	public void Auth() {
        VBox root = UIHelper.createVRoot();

        Label title = new Label("Rider Login");

        TextField code = new TextField();
        code.setPromptText("Rider ID");

        Button loginButton = UIHelper.createButton("Login");
        Button backButton = UIHelper.createButton("Back");

        loginButton.setOnAction(e -> {
        	try {
				rider = deliveryDataBase.getRider(code.getText());
				Main();
			} catch (RiderNotFoundException e1) {
				MessageBox.error(e1);
			}
        });

        backButton.setOnAction( e -> backF.run());

        root.getChildren().addAll(
                title,
                code,
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
