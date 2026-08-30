package HW3.Menus;

import HW3.DeliveryDataBase;
import HW3.DataObjects.Order.OrderStatus;
import HW3.DataObjects.*;
import HW3.Exceptions.*;
import HW3.Utils.InputManager;
import HW3.Utils.MenuManager;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RiderUI extends UIBase  {
	
	private Rider rider;

	public RiderUI(DeliveryDataBase deliveryDataBase) {
		super(deliveryDataBase);
	}

	@Override
	public void Auth() {
        VBox root = UIHelper.createVRoot();

        Label title = new Label("Rider Login, Enter ID:");

        TextField code = new TextField();
        code.setPromptText("Rider ID");

        Button loginButton = UIHelper.createButton("Login", () -> {
        	try {
				rider = deliveryDataBase.getRider(code.getText());
				Main();
			} catch (RiderNotFoundException e1) {
				MessageBox.error(e1);
			}
        });
        
        root.getChildren().addAll(
                title,
                code,
                loginButton,
                UIHelper.createBackButton()
        );

        MenuManager.goTo(new Scene(root, 400, 300));
	}

	@Override
	protected void Main() {
	    BorderPane root = new BorderPane();

	    VBox header = UIHelper.createVRoot("Rider Portal");
	    root.setTop(header);

	    GridPane grid = new GridPane();
	    grid.setHgap(20);
	    grid.setVgap(20);
	    grid.setPadding(new Insets(30));
	    grid.setAlignment(Pos.CENTER);

	    Button viewAssignedOrdersButton = UIHelper.createButton("View Assigned Orders", 
	    		() -> Tables.order( rider.getDeliverdOrders(), rider.getCurrentOrder()));
	    Button viewActiveOrderButton = UIHelper.createButton("View Active Order", 
	    		() ->{
	    			if (rider.getCurrentOrder() != null) {
	    		        MessageBox.Info("Active Order", rider.getCurrentOrder());
	    		    } else {
	    		        MessageBox.Info("Notice", "No active order found.");
	    		    }
	    		});

	    Button updateToOnWayButton = UIHelper.createButton("Update Status to 'On the Way'", this::updateStatusToOnWay);
	    Button updateToDeliveredButton = UIHelper.createButton("Update Status to 'Delivered'", this::updateStatusToDelivered);

	    Button viewHistoryButton = UIHelper.createButton("View Delivery History", 
	    		() -> Tables.order( rider.getDeliverdOrders()));
	    Button viewTotalCountButton = UIHelper.createButton("Show Total Deliveries Count", 
	    		() -> MessageBox.Info("Total Deliveries", "You have completed " + rider.getDeliverdOrders().size() + " deliveries."));

	    grid.add(viewAssignedOrdersButton, 0, 0);
	    grid.add(viewActiveOrderButton, 1, 0);

	    grid.add(updateToOnWayButton, 0, 1);
	    grid.add(updateToDeliveredButton, 1, 1);

	    grid.add(viewHistoryButton, 0, 2);
	    grid.add(viewTotalCountButton, 1, 2);

	    grid.add(UIHelper.createBackButton(), 0, 3, 2, 1);

	    root.setCenter(grid);

        MenuManager.goTo(new Scene(root, 500, 400));
	}

	private void updateStatusToOnWay() {
	    if (rider.getCurrentOrder() == null) {
	        MessageBox.Info("Notice", "No active order to update.");
	        return;
	    }
	    
	    if (rider.getCurrentOrder().getStatus() != OrderStatus.Created) {
	        MessageBox.Info("Notice", "Order is already on the way or delivered.");
	        return;
	    }
	    
	    try {
	        deliveryDataBase.updateDeliveryStatus(rider.getId(), null);
	        MessageBox.Info("Success", "Order status updated to 'On the Way'.");
	    } catch (RiderNotFoundException | TargetObjectDoesntExistException e) {
	        MessageBox.error(e);
	    }
	}

	private void updateStatusToDelivered() {
	    if (rider.getCurrentOrder() == null) {
	        MessageBox.Info("Notice", "No active order to update.");
	        return;
	    }
	    
	    if (rider.getCurrentOrder().getStatus() != OrderStatus.OnTheWay) {
	        MessageBox.Info("Notice", "Order must be 'On the Way' before it can be delivered.");
	        return;
	    }
	    
	    InputManager.createDateAfterDate( rider.getCurrentOrder().getOrderingDate(), date -> {
	        if (date != null) {
	            try {
	                deliveryDataBase.updateDeliveryStatus(rider.getId(), date);
	                MessageBox.Info("Success", "Order status updated to 'Delivered'.");
	            } catch (RiderNotFoundException | TargetObjectDoesntExistException e) {
	                MessageBox.error(e);
	            }
	        }
	        MenuManager.goBack();
	    });
	}
	
}
