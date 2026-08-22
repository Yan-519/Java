package HW3.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MessageBox {
	
	private static void show(AlertType type, String title, String content, String header) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setContentText(content);
		
		if(header != null)
			alert.setHeaderText(header);
		
		alert.showAndWait();
	}

	public static void error(String title, String content) {
		show(AlertType.ERROR, title, content, null);
	}
	
	public static void error(String title, Exception content) {
		show(AlertType.ERROR, title, content.getMessage(), null);
	}
	

	public static void error(Exception content) {
		show(AlertType.ERROR, "Operation error", content.getMessage(), content.getClass().getSimpleName());
	}
	
	public static void Info(String title, Object content) {
		show(AlertType.INFORMATION, title, content.toString(), null);
	}
	
	public static void Info(Object content) {
		show(AlertType.INFORMATION, "Noitice", content.toString(), null);
	}
	
}
