package HW3;

import HW3.Exceptions.OrderNotFoundException;
import HW3.Menus.MainUI;
import HW3.Utils.MessageBox;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
    public void start(Stage stage) {
        MainUI menuManager;
		try {
			menuManager = new MainUI(stage);
		} catch (OrderNotFoundException e) {
			MessageBox.error(e);
			return;
		}
        menuManager.Auth();
        stage.show();
    }

	public static void main(String[] args) {
		launch(args);
	}

}
