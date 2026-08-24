package HW3.Menus.AdminManagment;

import HW3.DeliveryDataBase;
import HW3.DeliveryDataBase.CodedType;
import HW3.DataObjects.Order;
import HW3.DataObjects.Rider;
import HW3.Exceptions.TargetObjectAlreadyExistException;
import HW3.Menus.UIBase;
import javafx.stage.Stage;
import HW3.Utils.DataSelector;
import HW3.Utils.InputManager;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class RiderManagment extends UIBase {

	public RiderManagment(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Init() {
		Main();

	}

	@Override
	protected void Main() {
	    BorderPane root = new BorderPane();

	    VBox header = UIHelper.createVRoot("Rider Management");
	    root.setTop(header);

	    GridPane grid = new GridPane();
	    grid.setHgap(20);
	    grid.setVgap(20);
	    grid.setPadding(new Insets(30));
	    grid.setAlignment(Pos.CENTER);

	    Button showAllButton = UIHelper.createButton("Show All Riders", 
	    		() -> UIHelper.showList(stage, deliveryDataBase.getRiders(), this::Main));
	    Button addRiderButton = UIHelper.createButton("Add New Rider",  e -> {
                	InputManager.createRider(stage, customer -> {
                	    if (customer != null) {
                	    	try {
	                	        deliveryDataBase.add(customer);
	                	        MessageBox.Info("Rider has beed added");
	                	    } catch (TargetObjectAlreadyExistException ex) {
	                	        MessageBox.error(ex);
	                	    }
                	    }
                	    Main();
                	});
                }
);

	    Button searchRiderButton = UIHelper.createButton("Search Rider", () ->{
	    	Rider rider = DataSelector.selectRider();
	    	if(rider == null) return;
	    	MessageBox.Info(rider);
	    });
	    Button riderOrdersButton = UIHelper.createButton("Show Rider Orders", () ->{
	    	Rider rider = DataSelector.selectRider();
	    	if(rider == null) return;
	    	UIHelper.showList(stage, rider.getDeliverdOrders(), this::Main);
	    });

	    Button updateStatusButton = UIHelper.createButton("Update Order Status", () ->{
	    	Rider rider = DataSelector.selectRider();
	    	if(rider == null) return;
	    	if(!rider.isAvailable())
	    	{
	    		MessageBox.Info("The selected rider has no active order");
	    		return;
	    	}

			Order order = rider.getCurrentOrder();
			
			System.out.println("The status of the current order is " + order.getOrderStatus());
			boolean isUpdate = MessageBox.inputBOOL( null, 
					"Do you want to update the status of the order (The status of the current order is " + order.getStatus(),
					"(created->on the way->delivered)", false);
			if(!isUpdate) return;
			
			Date date = order.getOrderStatus() == OrderStatus.Created ? null : InputManager.createDateAfterDate(order.getOrderingDate());
			try {
				deliveryDataBase.updateDeliveryStatus(rider.getId(), date);
			} catch (RiderNotFoundException e) {
				MessageBox.error(e);
			}

	    });
	    Button topRiderButton = UIHelper.createButton("Show Top Rider", this::showTopRider);

	    Button backButton = UIHelper.createButton("Back", backF);

	    grid.add(showAllButton, 0, 0);
	    grid.add(addRiderButton, 1, 0);

	    grid.add(searchRiderButton, 0, 1);
	    grid.add(riderOrdersButton, 1, 1);

	    grid.add(updateStatusButton, 0, 2);
	    grid.add(topRiderButton, 1, 2);

	    grid.add(backButton, 0, 3, 2, 1);

	    root.setCenter(grid);

	    stage.setScene(new Scene(root, 700, 600));
	}

	// Empty handler methods:

	private void showAllRiders() {
	    // TODO: Implement logic to display all riders
	}

	private void addNewRider() {
	    // TODO: Implement logic to add a new rider
	}

	private void searchRider() {
	    // TODO: Implement logic to search for a rider
	}

	private void showRiderOrders() {
	    // TODO: Implement logic to display all orders assigned to a specific rider
	}

	private void updateOrderStatus() {
	    // TODO: Implement logic to update the status of an order
	}

	private void showTopRider() {
	    // TODO: Implement logic to display the rider with the highest number of deliveries
	}

}
