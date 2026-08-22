package HW3.UI.Menus;

import HW3.DeliveryDataBase;
import javafx.stage.Stage;

public abstract class UIBase {
	
	protected Stage stage;
	protected DeliveryDataBase deliveryDataBase;
	protected Runnable backF;
	
	
	public UIBase(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super();
		this.stage = stage;
		this.deliveryDataBase = deliveryDataBase;
		this.backF = backF;
	}
	
	public abstract void Auth();
	
	protected abstract void Main();
}
