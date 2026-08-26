package HW3.Menus;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import HW3.DataObjects.*;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;

public class Tables {

	public static void customer(Stage stage, List<Customer> lst, Runnable backF) {
        if (lst.isEmpty()) {
            MessageBox.Info("No values found");
            return;
        }

        ObservableList<Customer> observables = FXCollections.observableArrayList(lst);
        TableView<Customer> tableView = new TableView<>(observables);

        // Define Columns (Field name strings must match getter methods, e.g., getName(), getLastName())
        TableColumn<Customer, String> colFirstName = new TableColumn<>("First Name");
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> colLastName = new TableColumn<>("Last Name");
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Customer, String> colPhone = new TableColumn<>("Phone");
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Customer, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("emain"));

        TableColumn<Customer, String> colTown = new TableColumn<>("Town");
        colTown.setCellValueFactory(new PropertyValueFactory<>("town"));

        TableColumn<Customer, Double> colBalance = new TableColumn<>("Balance");
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

        // Add columns to TableView
        tableView.getColumns().addAll(colFirstName, colLastName, colPhone, colEmail, colTown, colBalance);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button backButton = UIHelper.createButton("Back", e -> backF.run());

        VBox root = UIHelper.createVRoot();
        root.getChildren().addAll(tableView, backButton);

        stage.setScene(new Scene(root, 700, 400));
    }
	
	public static void order(Stage stage, List<Order> lst, Runnable backF) {
        if (lst == null || lst.isEmpty()) {
            MessageBox.Info("No values found");
            return;
        }

        ObservableList<Order> observables = FXCollections.observableArrayList(lst);
        TableView<Order> tableView = new TableView<>(observables);

        // Columns setup matching Order properties
        TableColumn<Order, Integer> colClientCode = new TableColumn<>("Client Code");
        colClientCode.setCellValueFactory(new PropertyValueFactory<>("clientCode"));

        TableColumn<Order, Integer> colRestCode = new TableColumn<>("Restaurant Code");
        colRestCode.setCellValueFactory(new PropertyValueFactory<>("restaurantCode"));

        TableColumn<Order, String> colRiderId = new TableColumn<>("Rider ID");
        colRiderId.setCellValueFactory(new PropertyValueFactory<>("riderId"));

        TableColumn<Order, Date> colOrderDate = new TableColumn<>("Order Date");
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderingDate"));

        TableColumn<Order, Date> colDeliveryDate = new TableColumn<>("Delivery Date");
        colDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveringDate"));

        TableColumn<Order, Double> colBasePrice = new TableColumn<>("Base Price");
        colBasePrice.setCellValueFactory(new PropertyValueFactory<>("basePrice"));

        TableColumn<Order, Double> colFinalPrice = new TableColumn<>("Final Price");
        colFinalPrice.setCellValueFactory(new PropertyValueFactory<>("finalPrice"));

        TableColumn<Order, Order.OrderStatus> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.getColumns().addAll(
            colClientCode, colRestCode, colRiderId, 
            colOrderDate, colDeliveryDate, 
            colBasePrice, colFinalPrice, colStatus
        );

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button backButton = UIHelper.createButton("Back", e -> backF.run());

        VBox root = UIHelper.createVRoot();
        root.getChildren().addAll(tableView, backButton);

        stage.setScene(new Scene(root, 800, 450));
    }
	
	public static void order(Stage stage, List<Order> deliveredOrders, Runnable backF, Order currentOrder) {
        // Combine current order and delivered orders if present
        List<Order> allOrders = new ArrayList<>();
        
        if (currentOrder != null) {
            allOrders.add(currentOrder);
        }
        
        if (deliveredOrders != null && !deliveredOrders.isEmpty()) {
            allOrders.addAll(deliveredOrders);
        }

        if (allOrders.isEmpty()) {
            MessageBox.Info("No orders found for this rider");
            return;
        }

        ObservableList<Order> observables = FXCollections.observableArrayList(allOrders);
        TableView<Order> tableView = new TableView<>(observables);

        // Columns setup matching Order properties
        TableColumn<Order, Integer> colClientCode = new TableColumn<>("Client Code");
        colClientCode.setCellValueFactory(new PropertyValueFactory<>("clientCode"));

        TableColumn<Order, Integer> colRestCode = new TableColumn<>("Restaurant Code");
        colRestCode.setCellValueFactory(new PropertyValueFactory<>("restaurantCode"));

        TableColumn<Order, String> colRiderId = new TableColumn<>("Rider ID");
        colRiderId.setCellValueFactory(new PropertyValueFactory<>("riderId"));

        TableColumn<Order, Date> colOrderDate = new TableColumn<>("Order Date");
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderingDate"));

        TableColumn<Order, Date> colDeliveryDate = new TableColumn<>("Delivery Date");
        colDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveringDate"));

        TableColumn<Order, Double> colBasePrice = new TableColumn<>("Base Price");
        colBasePrice.setCellValueFactory(new PropertyValueFactory<>("basePrice"));

        TableColumn<Order, Double> colFinalPrice = new TableColumn<>("Final Price");
        colFinalPrice.setCellValueFactory(new PropertyValueFactory<>("finalPrice"));

        TableColumn<Order, Order.OrderStatus> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.getColumns().addAll(
            colClientCode, colRestCode, colRiderId, 
            colOrderDate, colDeliveryDate, 
            colBasePrice, colFinalPrice, colStatus
        );

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button backButton = UIHelper.createButton("Back", e -> backF.run());

        VBox root = UIHelper.createVRoot();
        
        // Optional status header label if there is an active order
        if (currentOrder != null) {
            Label activeLabel = new Label("Active Order highlighted at top");
            root.getChildren().addAll(activeLabel, tableView, backButton);
        } else {
            root.getChildren().addAll(tableView, backButton);
        }

        stage.setScene(new Scene(root, 850, 450));
    }
	
	public static void restaurant(Stage stage, List<? extends Restaurant> lst, Runnable backF) {
        if (lst == null || lst.isEmpty()) {
            MessageBox.Info("No values found");
            return;
        }

        ObservableList<Restaurant> observables = FXCollections.observableArrayList(lst);
        TableView<Restaurant> tableView = new TableView<>(observables);

        // Base Restaurant Columns
        TableColumn<Restaurant, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Restaurant, String> colKitchen = new TableColumn<>("Kitchen Type");
        colKitchen.setCellValueFactory(new PropertyValueFactory<>("kitchenType"));

        TableColumn<Restaurant, Double> colRating = new TableColumn<>("Rating");
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        TableColumn<Restaurant, Boolean> colIsOpen = new TableColumn<>("Open");
        colIsOpen.setCellValueFactory(new PropertyValueFactory<>("isOpen"));

        TableColumn<Restaurant, Double> colBaseFee = new TableColumn<>("Base Fee");
        colBaseFee.setCellValueFactory(new PropertyValueFactory<>("baseDeliveryFee"));

        // Subclass Specific Columns (Custom Cell Value Factories to handle inheritance safely)
        TableColumn<Restaurant, String> colPrepTime = new TableColumn<>("Prep Time (min)");
        colPrepTime.setCellValueFactory(cell -> {
            if (cell.getValue() instanceof FastFoodRestaurant fastFood) {
                return new SimpleStringProperty(String.valueOf(fastFood.getAveragePreparingTimeInMinutes()));
            }
            return new SimpleStringProperty("-");
        });

        TableColumn<Restaurant, String> colExpressCost = new TableColumn<>("Express Delivery Fee");
        colExpressCost.setCellValueFactory(cell -> {
            if (cell.getValue() instanceof FastFoodRestaurant fastFood) {
                return new SimpleStringProperty(String.valueOf(fastFood.getAdditionalCostForExpressDelivery()));
            }
            return new SimpleStringProperty("-");
        });

        TableColumn<Restaurant, String> colMinOrder = new TableColumn<>("Min Order");
        colMinOrder.setCellValueFactory(cell -> {
            if (cell.getValue() instanceof PremiumRestaurant premium) {
                return new SimpleStringProperty(String.valueOf( premium.getMinimumOrderCost()));
            }
            return new SimpleStringProperty("-");
        });

        TableColumn<Restaurant, String> colCommission = new TableColumn<>("Commission (%)");
        colCommission.setCellValueFactory(cell -> {
            if (cell.getValue() instanceof PremiumRestaurant premium) {
                return new SimpleStringProperty(String.valueOf( premium.getAdditionalCommissionPercentagePerOrder()));
            }
            return new SimpleStringProperty("-");
        });

        tableView.getColumns().addAll(
            colName, colKitchen, colRating, colIsOpen, colBaseFee,
            colPrepTime, colExpressCost, colMinOrder, colCommission
        );

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button backButton = UIHelper.createButton("Back", e -> backF.run());

        VBox root = UIHelper.createVRoot();
        root.getChildren().addAll(tableView, backButton);

        stage.setScene(new Scene(root, 950, 450));
    }
	
	public static void rider(Stage stage, List<Rider> lst, Runnable backF) {
	    if (lst == null || lst.isEmpty()) {
	        MessageBox.Info("No values found");
	        return;
	    }

	    ObservableList<Rider> observables = FXCollections.observableArrayList(lst);
	    TableView<Rider> tableView = new TableView<>(observables);

	    // Direct Property Columns
	    TableColumn<Rider, String> colId = new TableColumn<>("ID");
	    colId.setCellValueFactory(new PropertyValueFactory<>("id"));

	    TableColumn<Rider, String> colFirstName = new TableColumn<>("First Name");
	    colFirstName.setCellValueFactory(new PropertyValueFactory<>("name"));

	    TableColumn<Rider, String> colLastName = new TableColumn<>("Last Name");
	    colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

	    TableColumn<Rider, String> colPhone = new TableColumn<>("Phone");
	    colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

	    TableColumn<Rider, String> colVehicle = new TableColumn<>("Vehicle");
	    colVehicle.setCellValueFactory(new PropertyValueFactory<>("vehicle"));

	    TableColumn<Rider, Boolean> colAvailable = new TableColumn<>("Available");
	    colAvailable.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));

	    
	    TableColumn<Rider, String> colDeliveredCount = new TableColumn<>("Orders Delivered");
	    colDeliveredCount.setCellValueFactory(cell -> 
	         new SimpleStringProperty(cell.getValue().getDeliverdOrders().size() + " orders")
	    );

	    TableColumn<Rider, String> colCurrentOrder = new TableColumn<>("Current Order");
	    colCurrentOrder.setCellValueFactory(cell -> {
	        Order current = cell.getValue().getCurrentOrder();
	        return new SimpleStringProperty(current != null ? current.toString() : "None");
	    });

	    tableView.getColumns().addAll(
	        colId, colFirstName, colLastName, colPhone, 
	        colVehicle, colAvailable, colDeliveredCount, colCurrentOrder
	    );

	    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	    Button backButton = UIHelper.createButton("Back", e -> backF.run());

	    VBox root = UIHelper.createVRoot();
	    root.getChildren().addAll(tableView, backButton);

	    stage.setScene(new Scene(root, 900, 450));
	}
	
	public static void restAdmin(Stage stage, List<RestAdmin> lst, Runnable backF) {
	    if (lst == null || lst.isEmpty()) {
	        MessageBox.Info("No values found");
	        return;
	    }

	    ObservableList<RestAdmin> observables = FXCollections.observableArrayList(lst);
	    TableView<RestAdmin> tableView = new TableView<>(observables);

	    // Direct String Properties
	    TableColumn<RestAdmin, String> colName = new TableColumn<>("Name");
	    colName.setCellValueFactory(new PropertyValueFactory<>("name"));

	    TableColumn<RestAdmin, String> colUsername = new TableColumn<>("Username");
	    colUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));

	    // Password Property (Masked for privacy/security)
	    TableColumn<RestAdmin, String> colPassword = new TableColumn<>("Password");
	    colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

	    // Complex List Property: Formats the ArrayList<Restaurant> into a count & names summary
	    TableColumn<RestAdmin, String> colRestaurants = new TableColumn<>("Managed Restaurants");
	    colRestaurants.setCellValueFactory(cell -> {
	        List<Restaurant> restList = cell.getValue().getRestaurants();
	        if (restList == null || restList.isEmpty()) 
	            return new SimpleStringProperty("None");
	        return new SimpleStringProperty(restList.stream().map(Restaurant::getName).collect(Collectors.joining(", ")));
	    });

	    tableView.getColumns().addAll(colName, colUsername, colPassword, colRestaurants);
	    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	    Button backButton = UIHelper.createButton("Back", e -> backF.run());

	    VBox root = UIHelper.createVRoot();
	    root.getChildren().addAll(tableView, backButton);

	    stage.setScene(new Scene(root, 750, 400));
	}
	
}
