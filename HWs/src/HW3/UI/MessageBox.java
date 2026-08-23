package HW3.UI;

import java.util.Optional;

import HW3.Utils.DataChecker;
import HW3.Utils.DataSelector;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

public class MessageBox {
	
	private static void show(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		
		if(content != null)
			alert.setContentText(content);
		
		
		alert.showAndWait();
	}

	public static void error(String title, String header) {
		show(AlertType.ERROR, title, header, null);
	}
	
	public static void error(String title, Exception header) {
		show(AlertType.ERROR, title, header.getMessage(), null);
	}
	

	public static void error(Exception header) {
		show(AlertType.ERROR, "Operation error", header.getMessage(), header.getClass().getSimpleName());
	}
	
	public static void Info(String title, Object header) {
		show(AlertType.INFORMATION, title, header.toString(), null);
	}
	
	public static void Info(Object header) {
		if(header == null) return;
		show(AlertType.INFORMATION, "Noitice", header.toString(), null);
	}
	
	
	// number input sign
	public enum NumberSign{ALL, POSITIVE, NOT_NEGATIVE }
	
	private static String baseString(String title, String header, String text) {
		
        TextInputDialog dialog = new TextInputDialog();

        if(title != null)
        	dialog.setTitle(title);
        else dialog.setTitle("Input");
        dialog.setHeaderText(header);
        if(text != null)
        	dialog.setContentText(text);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) 
        	return result.get().trim();
        
		return null;
	}
	
	public static String inputSTR(String title, String header, String text) {
		return DataSelector.dataFilter( () -> baseString(title, header, text), s -> !s.isEmpty(), "Invalide text input (empty or blanck)");
	}
	
	public static String inputSTR(String title, String header, boolean isName) {
		return DataSelector.dataFilter(() -> inputSTR(header, header, "Only letters and spaces"), s -> DataChecker.isValidName(s), "Not valide name"); 
	}
	
	public static Integer inputINT(String title, String header, String text) {

		String n = DataSelector.dataFilter(() -> inputSTR(title, header, text), 
				str -> {
            try {
                Integer.valueOf(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
				, "Input not an Integer");
		
		if(n == null) return null;
		return Integer.valueOf(n);
	}
	

	public static Integer inputINT(String title, String header, NumberSign inputSign) {
		
		return DataSelector.dataFilter(() -> inputINT(title, header, inputSign) ,
				n -> {
					if(inputSign == NumberSign.ALL) return true;
					else if(inputSign == NumberSign.NOT_NEGATIVE) return 0 <= n;
					else return 0 < n;
				}
		, "Wronge sign");
	}
	
	public static Double inputDOUB(String title, String header, String text) {

		String n = DataSelector.dataFilter(() -> inputSTR(title, header, text), 
				str -> {
            try {
                Double.valueOf(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
				, "Input not an Double");
		
		if(n == null) return null;
		return Double.valueOf(n);
	}
	

	public static Double inputDOUB(String title, String header, NumberSign inputSign) {
		return DataSelector.dataFilter(() -> inputDOUB(title, header, inputSign) ,
				n -> {
					if(inputSign == NumberSign.ALL) return true;
					else if(inputSign == NumberSign.NOT_NEGATIVE) return 0 <= n;
					else return 0 < n;
				}
		, "Wronge sign");
	}

	public static Double inputDOUB(String title, String header, NumberSign inputSign, double max) {
		return DataSelector.dataFilter(() -> 
			inputDOUB(title, header, inputSign + " not bigger than " + max),
			d -> d <= max,
			"Input beyond limit");
	}
	

	public static Boolean inputBOOL(String title, String header, boolean isNull) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		if(title != null)
			alert.setTitle(title);
		alert.setHeaderText(header);

		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent())
		    return result.get() == ButtonType.YES;
		
		if(isNull)
			return null;
		return false;
	}
	
	public static Boolean inputBOOL(String header, boolean isNull) {
		return inputBOOL(null, header, isNull);
	}
	
	public static Boolean inputBOOL(String header) {
		return inputBOOL(null, header, false);
	}
}
