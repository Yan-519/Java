package HW3.Menus.AdminManagment;

import HW3.DeliveryDataBase;
import HW3.Menus.Tables;
import HW3.Menus.UIBase;
import javafx.stage.Stage;
import HW3.DataObjects.PremiumRestaurant;
import HW3.DataObjects.Restaurant;
import HW3.DataObjects.Rider;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.stream.Collectors;

public class ReportsUI extends UIBase {

	public ReportsUI(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
	}

	@Override
    protected void Main() {
        BorderPane root = new BorderPane();

        VBox header = UIHelper.createVRoot("Reports and Sorting");
        root.setTop(header);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(30));
        grid.setAlignment(Pos.CENTER);

        Button sortCustomersByBalanceButton = UIHelper.createButton("Sort Customers by Balance", 
            () -> {
                deliveryDataBase.sortCustomersByBalance();
                Tables.customer(stage, deliveryDataBase.getCustomers(), this::Main);
            });

        Button sortCustomersByNameButton = UIHelper.createButton("Sort Customers by First Name", 
            () -> {
                deliveryDataBase.sortCustomersByName();
                Tables.customer(stage, deliveryDataBase.getCustomers(), this::Main);
            });
        
        Button sortRestaurantsByRatingButton = UIHelper.createButton("Sort Restaurants by Rating", 
            () -> {
                deliveryDataBase.sortRestaurantsByRaiting();
                Tables.restaurant(stage, deliveryDataBase.getRestaurants(), this::Main);
            });

        Button sortOrdersByPriceButton = UIHelper.createButton("Sort Orders by Final Price", 
            () -> {
                deliveryDataBase.sortOrdersByFinalPrice();
                Tables.order(stage, deliveryDataBase.getOrders(), this::Main);
            });
        
        Button sortOrdersByDateButton = UIHelper.createButton("Sort Orders by Date", 
            () -> {
                deliveryDataBase.sortOrdersByDate();
                Tables.order(stage, deliveryDataBase.getOrders(), this::Main);
            });

        Button sortRidersByDeliveriesButton = UIHelper.createButton("Sort Riders by Delivery Count", 
            () -> {
                deliveryDataBase.sortRidersByDeliverdCount();
                Tables.rider(stage, deliveryDataBase.getRiders(), this::Main);
            });
        
        Button showOpenRestaurantsButton = UIHelper.createButton("Show Open Restaurants", 
        		() -> Tables.restaurant(stage, deliveryDataBase.getRestaurants().stream()
                        .filter(Restaurant::getIsOpen).toList(), this::Main));
        Button showPremiumRestaurantsButton = UIHelper.createButton("Show Premium Restaurants", 
        		() -> Tables.restaurant(stage, deliveryDataBase.getRestaurants().stream()
                        .filter(r -> r instanceof PremiumRestaurant).toList(), this::Main));
        
        Button showAvailableRidersButton = UIHelper.createButton("Show Available Riders", 
        		() -> Tables.rider(stage,  deliveryDataBase.getRiders().stream()
                        .filter(Rider::getIsAvailable).toList(), this::Main));
        Button showTotalPaymentsButton = UIHelper.createButton("Show Total Payments", 
        		() -> MessageBox.Info("Total System Payments", "Total payments collected in the system: " + deliveryDataBase.getTotalSpentByCustomer().values().stream()
                        .mapToDouble(Double::doubleValue)
                        .sum()));

        Button backButton = UIHelper.createButton("Back", backF);

        // Adding to grid
        grid.add(sortCustomersByBalanceButton, 0, 0);
        grid.add(sortCustomersByNameButton, 1, 0);

        grid.add(sortRestaurantsByRatingButton, 0, 1);
        grid.add(sortOrdersByPriceButton, 1, 1);

        grid.add(sortOrdersByDateButton, 0, 2);
        grid.add(sortRidersByDeliveriesButton, 1, 2);

        grid.add(showOpenRestaurantsButton, 0, 3);
        grid.add(showPremiumRestaurantsButton, 1, 3);

        grid.add(showAvailableRidersButton, 0, 4);
        grid.add(showTotalPaymentsButton, 1, 4);

        grid.add(backButton, 0, 5, 2, 1);

        root.setCenter(grid);

        stage.setScene(new Scene(root, 750, 650));
    }
}
