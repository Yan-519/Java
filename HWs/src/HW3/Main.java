package HW3;

import HW3.UI.Menus.MainUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
    public void start(Stage stage) {
        MainUI menuManager = new MainUI(stage);
        menuManager.Auth();
        stage.show();
    }

	public static void main(String[] args) {
		launch(args);
	}

}
