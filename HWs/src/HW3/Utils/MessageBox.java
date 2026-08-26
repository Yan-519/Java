package HW3.Utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
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
		show(AlertType.ERROR, title, header.getMessage(), header.getClass().getSimpleName());
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
	
	// input a string the is a valid string
	public static String inputSTR(String title, String header, boolean isName) {
		return DataSelector.dataFilter(() -> inputSTR(header, header, "Only letters and spaces"), s -> DataChecker.isValidName(s), "Not valide name"); 
	}
	
	// input int
	public static Integer inputINT(String title, String header, String text) {

		String n = DataSelector.dataFilter(() -> inputSTR(title, header, text + "[Integer]"), 
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
	
	// input in with sign check
	public static Integer inputINT(String title, String header, NumberSign inputSign) {
		
		return DataSelector.dataFilter(() -> inputINT(title, header, inputSign.toString()) ,
				n -> {
					if(inputSign == NumberSign.ALL) return true;
					else if(inputSign == NumberSign.NOT_NEGATIVE) return 0 <= n;
					else return 0 < n;
				}
		, "Wronge sign");
	}
	
	// double input
	public static Double inputDOUB(String title, String header, String text) {
		String n = DataSelector.dataFilter(() -> inputSTR(title, header, text + "[Double]"), 
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
	
	// double input with sign check
	public static Double inputDOUB(String title, String header, NumberSign inputSign) {
		return DataSelector.dataFilter(() -> inputDOUB(title, header, inputSign.toString()) ,
				n -> {
					if(inputSign == NumberSign.ALL) return true;
					else if(inputSign == NumberSign.NOT_NEGATIVE) return 0 <= n;
					else return 0 < n;
				}
		, "Wronge sign");
	}

	// not negative double input with max check
	public static Double inputDOUB(String title, String header, double max) {
		return DataSelector.dataFilter(() -> 
			inputDOUB(title, header, NumberSign.NOT_NEGATIVE),
			d -> d <= max,
			"Input beyond limit");
	}
	
	// ask user yes/no Q (boolean isNull if user cancld action checks between output null and false)
	public static Boolean inputBOOL(String title, String header, String taxt, boolean isNull) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		if(title != null)
			alert.setTitle(title);
		alert.setHeaderText(header);
		if(taxt != null)
			alert.setContentText(taxt);

		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent())
		    return result.get() == ButtonType.YES;
		
		if(isNull)
			return null;
		return false;
	}
	
	// yes/no Q that gives boolean not Boolean
	public static boolean inputBOOL(String header) {
		return inputBOOL(null, header, null, false);
	}
	
	// user select one option of the given
	public static String inputSelect(String... options) {
		if(options.length == 0) return null;
		
		ChoiceDialog<String> dialog = new ChoiceDialog<>(options[0], options);

		dialog.setTitle("Select Option");
		dialog.setHeaderText("Choose one item from the list:");
		dialog.setContentText("Select:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent())
		    return result.get();
		return null;
	}
}
