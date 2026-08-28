package HW3.Menus;

import HW3.DeliveryDataBase;

public abstract class UIBase {
	// the deliveryDataBase of the app
	protected DeliveryDataBase deliveryDataBase;
	
	public UIBase(DeliveryDataBase deliveryDataBase) {
		super();
		this.deliveryDataBase = deliveryDataBase;
	}

	// authentication page
	public void Auth() {
		Main();
	}
	
	// main page
	protected abstract void Main();
}
