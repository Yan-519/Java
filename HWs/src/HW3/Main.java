package HW3;

import HW3.UI.MainUI;
import HW3.Utils.InputManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	
	@Override
    public void start(Stage stage) {
        MainUI menuManager = new MainUI(stage);
        menuManager.Init();
        stage.show();

		InputManager.input("title", "head", "text");
    }

	public static void main(String[] args) {
		
		new InputManager();
		
		
		launch(args);
	}

}
