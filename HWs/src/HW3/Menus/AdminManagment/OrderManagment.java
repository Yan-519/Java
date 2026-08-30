package HW3.Menus.AdminManagment;

import HW3.DeliveryDataBase;
import HW3.DataObjects.Customer;
import HW3.DataObjects.Restaurant;
import HW3.Menus.Tables;
import HW3.Menus.UIBase;
import HW3.Utils.DataSelector;
import HW3.Utils.MenuManager;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class OrderManagment extends UIBase {

	public OrderManagment(DeliveryDataBase deliveryDataBase) {
		super(deliveryDataBase);
	}

	@Override
	protected void Main() {
	    BorderPane root = new BorderPane();

	    VBox header = UIHelper.createVRoot("Order Management");
	    root.setTop(header);

	    GridPane grid = new GridPane();
	    grid.setHgap(20);
	    grid.setVgap(20);
	    grid.setPadding(new Insets(30));
	    grid.setAlignment(Pos.CENTER);

	    Button showAllButton = UIHelper.createButton("Show All Orders", () ->{
	    	Tables.order( deliveryDataBase.getOrders());
	    });
	    Button searchByCodeButton = UIHelper.createButton("Search Order by Code", () ->
	    	MessageBox.Info(DataSelector.selectOrder())
	    );

	    Button filterByCustomerButton = UIHelper.createButton("Show Orders by Customer", () ->{
	    	Customer customer = DataSelector.selectCustomer();
	    	if(customer != null)
	    		Tables.order( deliveryDataBase.getOrdersOfCustomer(customer));
	    });
	    Button filterByRestaurantButton = UIHelper.createButton("Show Orders by Restaurant", () ->{
	    	Restaurant restaurant = DataSelector.selectRestaurant();
	    	if(restaurant != null)
	    		Tables.order( deliveryDataBase.getOrdersByuRestaurant(restaurant.getCode()));
	    });

	    Button highestPriceButton = UIHelper.createButton("Show Highest Price Order", () ->
	    	MessageBox.Info(deliveryDataBase.getOrders().stream().max((o1, o2) -> Double.compare(o1.getFinalPrice(), o2.getFinalPrice())).get())
	    );

	    grid.add(showAllButton, 0, 0);
	    grid.add(searchByCodeButton, 1, 0);

	    grid.add(filterByCustomerButton, 0, 1);
	    grid.add(filterByRestaurantButton, 1, 1);

	    grid.add(highestPriceButton, 0, 2, 2, 1);

	    grid.add(UIHelper.createBackButton(), 0, 3, 2, 1);

	    root.setCenter(grid);

	    MenuManager.goTo(new Scene(root, 500, 400));
	}
	
	
}
