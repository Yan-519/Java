package HW3.Menus.AdminManagment;

import HW3.DeliveryDataBase;
import HW3.Menus.Tables;
import HW3.Menus.UIBase;
import HW3.DataObjects.PremiumRestaurant;
import HW3.DataObjects.Restaurant;
import HW3.DataObjects.Rider;
import HW3.Utils.MenuManager;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ReportsUI extends UIBase {


	public ReportsUI(DeliveryDataBase deliveryDataBase) {
		super(deliveryDataBase);
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
                Tables.customer( deliveryDataBase.getCustomers());
            });

        Button sortCustomersByNameButton = UIHelper.createButton("Sort Customers by First Name", 
            () -> {
                deliveryDataBase.sortCustomersByName();
                Tables.customer( deliveryDataBase.getCustomers());
            });
        
        Button sortRestaurantsByRatingButton = UIHelper.createButton("Sort Restaurants by Rating", 
            () -> {
                deliveryDataBase.sortRestaurantsByRaiting();
                Tables.restaurant( deliveryDataBase.getRestaurants());
            });

        Button sortOrdersByPriceButton = UIHelper.createButton("Sort Orders by Final Price", 
            () -> {
                deliveryDataBase.sortOrdersByFinalPrice();
                Tables.order( deliveryDataBase.getOrders());
            });
        
        Button sortOrdersByDateButton = UIHelper.createButton("Sort Orders by Date", 
            () -> {
                deliveryDataBase.sortOrdersByDate();
                Tables.order( deliveryDataBase.getOrders());
            });

        Button sortRidersByDeliveriesButton = UIHelper.createButton("Sort Riders by Delivery Count", 
            () -> {
                deliveryDataBase.sortRidersByDeliverdCount();
                Tables.rider( deliveryDataBase.getRiders());
            });
        
        Button showOpenRestaurantsButton = UIHelper.createButton("Show Open Restaurants", 
        		() -> Tables.restaurant( deliveryDataBase.getRestaurants().stream()
                        .filter(Restaurant::getIsOpen).toList()));
        Button showPremiumRestaurantsButton = UIHelper.createButton("Show Premium Restaurants", 
        		() -> Tables.restaurant( deliveryDataBase.getRestaurants().stream()
                        .filter(r -> r instanceof PremiumRestaurant).toList()));
        
        Button showAvailableRidersButton = UIHelper.createButton("Show Available Riders", 
        		() -> Tables.rider(  deliveryDataBase.getRiders().stream()
                        .filter(Rider::getIsAvailable).toList()));
        Button showTotalPaymentsButton = UIHelper.createButton("Show Total Payments", 
        		() -> MessageBox.Info("Total System Payments", "Total payments collected in the system: " + deliveryDataBase.getTotalSpentByCustomer().values().stream()
                        .mapToDouble(Double::doubleValue)
                        .sum()));

        Button backButton = UIHelper.createButton("Back", MenuManager::goBack);

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

        MenuManager.goTo(new Scene(root, 500, 500));
    }
}
