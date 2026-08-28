package HW3.Utils;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuManager {

	// the stage of the app
	private static Stage stage;
	// all the previous windows in the app
	private static Stack<Scene> windows;
	
	public static void setStage(Stage s) {
		stage = s;
		windows = new Stack<>();
	}
	
	// navigate to a new Scene (saving it)
	public static void goTo(Scene p) {
		stage.setScene(p);
		windows.push(p);
	}
	
	// navigate to the previous Scene
	public static void goBack() {
		if(windows.isEmpty()) {
			stage.close();
			return;
		}
		windows.pop();
		
		if(windows.isEmpty())
			stage.close();
		
		else 
			stage.setScene(windows.peek());
		
	}
	
}
