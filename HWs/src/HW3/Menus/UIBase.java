package HW3.Menus;

import HW3.DeliveryDataBase;
import javafx.stage.Stage;

public abstract class UIBase {
	// the stage of the app
	protected Stage stage;
	// the deliveryDataBase of the app
	protected DeliveryDataBase deliveryDataBase;
	// the go to back page action
	protected Runnable backF;
	
	
	public UIBase(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super();
		this.stage = stage;
		this.deliveryDataBase = deliveryDataBase;
		this.backF = backF;
	}
	
	// authentication page
	public void Auth() {
		Main();
	}
	
	// main page
	protected abstract void Main();
}
