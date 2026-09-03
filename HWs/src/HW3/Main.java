package HW3;

import HW3.Exceptions.OrderNotFoundException;
import HW3.Menus.MainUI;
import HW3.Utils.MenuManager;
import HW3.Utils.MessageBox;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
    public void start(@SuppressWarnings("exports") Stage stage) {
		MenuManager.setStage(stage);
		try {
			MainUI menuManager = new MainUI(stage, new DeliveryDataBase());
	        menuManager.Auth();
	        stage.show();
		} catch (OrderNotFoundException e) {
			MessageBox.error(e);
		}
    }

	public static void main(String[] args) {
		launch(args);
	}

}
