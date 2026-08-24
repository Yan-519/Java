package Calculator;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.function.BiFunction;

public class Main extends Application {
	
	@FXML
    private TextField mainEntry;
	
	private boolean isApliedOperator = false;
	private boolean isForClear = false;
	
	private String input1,input2;
	
	private BiFunction<Double, Double, Double> function = (a,b) -> 0.0;
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
		
		Scene scene = new Scene(root);
		stage.setTitle("Calc");
		stage.setScene(scene);
		
		clearPress(null);
		
		stage.show();
	}
	
	
	@FXML
	public void numberPress(ActionEvent event) {
		if(isForClear) clearPress(null);
		String digit = ((Button) event.getSource()).getText();
		mainEntry.appendText(digit);
		
		if(isApliedOperator)
			input2 += digit;
			
		else 
			input1 += digit;
	}
	
	@FXML
	public void operationPress(ActionEvent event) {
		if(isForClear) clearPress(null);
		if(input1.isEmpty() || isApliedOperator) return;
		String op = ((Button) event.getSource()).getText();
		switch (op) {
		case "+": 
			function = (a,b) -> a+b;
			break;
		case "-": 
			function = (a,b) -> a-b;
			break;
		case "*": 
			function = (a,b) -> a*b;
			break;
		case "/": 
			function = (a,b) -> a/b;
			break;
			
		default: return;
		};

		mainEntry.appendText(" " +op +" ");
		
    	isApliedOperator = true;
	}
	
	@FXML
	public void equalsPress(ActionEvent event) {
    	if(!isApliedOperator || input1.isEmpty() || input2.isEmpty())return;
    	
    	try {
			double res = function.apply(Double.valueOf(input1), Double.valueOf(input2));
			
			mainEntry.appendText(" = " + res);
			
		} catch (Exception e) {
			mainEntry.setText(e.getMessage());
		}
    	isForClear = true;
    	
	}
	@FXML
	public void clearPress(ActionEvent event) {
		if (mainEntry != null) 
	        mainEntry.setText("");

    	input1 = "";
    	input2 = "";
    	
    	isApliedOperator = false;
    	isForClear = false;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
		
	}

}
