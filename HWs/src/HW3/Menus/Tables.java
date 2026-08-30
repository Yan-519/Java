package HW3.Menus;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import HW3.DataObjects.*;
import HW3.Utils.MenuManager;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;

public class Tables {
	
	@SuppressWarnings("unchecked")
	private static <T> void makeTable(List<T> lst, TableColumn<T, ?>...columns) {
		if (lst == null || lst.isEmpty()) {
            MessageBox.Info("No values found");
            return;
        }
		
		Arrays.asList(columns).forEach(c -> c.setSortable(false));
		
        TableView<T> tableView = new TableView<>(FXCollections.observableArrayList(lst));
        
        tableView.getColumns().addAll(columns);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        
        VBox root = UIHelper.createVRoot();
        root.getChildren().addAll(tableView, UIHelper.createBackButton());

        MenuManager.goTo(new Scene(root, 850, 400));
	}
	
	
	// all functions are creating tables to show the given values
	
	@SuppressWarnings("unchecked")
	public static void customer(List<Customer> lst) {

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

	    makeTable(lst, colFirstName, colLastName, colPhone, colEmail, colTown, colBalance);
	}
	
	@SuppressWarnings("unchecked")
	public static void order(List<Order> lst) {

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

	    makeTable(lst, colClientCode, colRestCode, colRiderId, colOrderDate, colDeliveryDate, colBasePrice, colFinalPrice, colStatus);
	}
	
	@SuppressWarnings("unchecked")
	public static void order(List<Order> deliveredOrders, Order currentOrder) {
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

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        Button backButton = UIHelper.createBackButton();

        VBox root = UIHelper.createVRoot();
        
        // Optional status header label if there is an active order
        if (currentOrder != null) {
            Label activeLabel = new Label("Active Order highlighted at top");
            root.getChildren().addAll(activeLabel, tableView, backButton);
        } else {
            root.getChildren().addAll(tableView, backButton);
        }

        MenuManager.goTo(new Scene(root, 850, 450));
    }
	
	@SuppressWarnings("unchecked")
	public static <T extends Restaurant> void restaurant(List<T> lst) {

	    TableColumn<T, String> colName = new TableColumn<>("Name");
	    colName.setCellValueFactory(new PropertyValueFactory<>("name"));

	    TableColumn<T, String> colKitchen = new TableColumn<>("Kitchen Type");
	    colKitchen.setCellValueFactory(new PropertyValueFactory<>("kitchenType"));

	    TableColumn<T, Double> colRating = new TableColumn<>("Rating");
	    colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

	    TableColumn<T, Boolean> colIsOpen = new TableColumn<>("Open");
	    colIsOpen.setCellValueFactory(new PropertyValueFactory<>("isOpen"));

	    TableColumn<T, Double> colBaseFee = new TableColumn<>("Base Fee");
	    colBaseFee.setCellValueFactory(new PropertyValueFactory<>("baseDeliveryFee"));

	    TableColumn<T, String> colPrepTime = new TableColumn<>("Prep Time (min)");
	    colPrepTime.setCellValueFactory(cell -> {
	        if (cell.getValue() instanceof FastFoodRestaurant fastFood)
	            return new SimpleStringProperty(String.valueOf(fastFood.getAveragePreparingTimeInMinutes()));
	        return new SimpleStringProperty("-");
	    });

	    TableColumn<T, String> colExpressCost = new TableColumn<>("Express Delivery Fee");
	    colExpressCost.setCellValueFactory(cell -> {
	        if (cell.getValue() instanceof FastFoodRestaurant fastFood)
	            return new SimpleStringProperty(String.valueOf(fastFood.getAdditionalCostForExpressDelivery()));
	        return new SimpleStringProperty("-");
	    });

	    TableColumn<T, String> colMinOrder = new TableColumn<>("Min Order");
	    colMinOrder.setCellValueFactory(cell -> {
	        if (cell.getValue() instanceof PremiumRestaurant premium)
	            return new SimpleStringProperty(String.valueOf(premium.getMinimumOrderCost()));
	        return new SimpleStringProperty("-");
	    });

	    TableColumn<T, String> colCommission = new TableColumn<>("Commission (%)");
	    colCommission.setCellValueFactory(cell -> {
	        if (cell.getValue() instanceof PremiumRestaurant premium)
	            return new SimpleStringProperty(String.valueOf(premium.getAdditionalCommissionPercentagePerOrder()));
	        return new SimpleStringProperty("-");
	    });

	    // cast to raw List to satisfy makeTable generic signature
	    makeTable(lst, colName, colKitchen, colRating, colIsOpen, colBaseFee, colPrepTime, colExpressCost, colMinOrder, colCommission);
	}
	
	@SuppressWarnings("unchecked")
	public static void rider(List<Rider> lst) {

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

	    makeTable(lst, colId, colFirstName, colLastName, colPhone, colVehicle, colAvailable, colDeliveredCount, colCurrentOrder);
	}
	
	@SuppressWarnings("unchecked")
	public static void restAdmin(List<RestAdmin> lst) {
		
	    TableColumn<RestAdmin, String> colName = new TableColumn<>("Name");
	    colName.setCellValueFactory(new PropertyValueFactory<>("name"));

	    TableColumn<RestAdmin, String> colUsername = new TableColumn<>("Username");
	    colUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));

	    TableColumn<RestAdmin, String> colPassword = new TableColumn<>("Password");
	    colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

	    TableColumn<RestAdmin, String> colRestaurants = new TableColumn<>("Managed Restaurants");
	    colRestaurants.setCellValueFactory(cell -> {
	        List<Restaurant> restList = cell.getValue().getRestaurants();
	        if (restList == null || restList.isEmpty())
	            return new SimpleStringProperty("None");
	        return new SimpleStringProperty(restList.stream().map(r -> r.getName() + "(" + r.getCode() + ")").collect(Collectors.joining(", ")));
	    });

	    makeTable(lst, colName, colUsername, colPassword, colRestaurants);
	}
	
	// select restaurants
	@SuppressWarnings("unchecked")
	public static void createCodedSelectionView(List<? extends Restaurant> items, Consumer<HashSet<Integer>> onFinish) {
	    if (items == null || items.isEmpty()) {
	        MessageBox.Info("No values found");
	        return;
	    }

	    HashSet<Integer> selected = new HashSet<>();
	    ObservableList<Restaurant> observables = FXCollections.observableArrayList(items);
	    TableView<Restaurant> tableView = new TableView<>(observables);

	    TableColumn<Restaurant, Boolean> colSelect = new TableColumn<>("Select");
	    colSelect.setCellValueFactory(cell -> new SimpleBooleanProperty(selected.contains(cell.getValue().getCode())));
	    colSelect.setCellFactory(col -> new TableCell<>() {
	        private final CheckBox checkBox = new CheckBox();

	        {
	            checkBox.setOnAction(e -> {
	                Restaurant currentItem = getTableView().getItems().get(getIndex());
	                if (currentItem != null) {
	                    if (checkBox.isSelected()) {
	                        selected.add(currentItem.getCode());
	                    } else {
	                        selected.remove(currentItem.getCode());
	                    }
	                }
	            });
	        }

	        @Override
	        protected void updateItem(Boolean item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
	                setGraphic(null);
	            } else {
	                Restaurant currentItem = getTableView().getItems().get(getIndex());
	                checkBox.setSelected(selected.contains(currentItem.getCode()));
	                setGraphic(checkBox);
	            }
	        }
	    });

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

	    TableColumn<Restaurant, String> colPrepTime = new TableColumn<>("Prep Time (min)");
	    colPrepTime.setCellValueFactory(cell -> {
	        if (cell.getValue() instanceof FastFoodRestaurant fastFood) 
	            return new SimpleStringProperty(String.valueOf(fastFood.getAveragePreparingTimeInMinutes()));
	        return new SimpleStringProperty("-");
	    });

	    TableColumn<Restaurant, String> colExpressCost = new TableColumn<>("Express Delivery Fee");
	    colExpressCost.setCellValueFactory(cell -> {
	        if (cell.getValue() instanceof FastFoodRestaurant fastFood) 
	            return new SimpleStringProperty(String.valueOf(fastFood.getAdditionalCostForExpressDelivery()));
	        return new SimpleStringProperty("-");
	    });

	    TableColumn<Restaurant, String> colMinOrder = new TableColumn<>("Min Order");
	    colMinOrder.setCellValueFactory(cell -> {
	        if (cell.getValue() instanceof PremiumRestaurant premium) 
	            return new SimpleStringProperty(String.valueOf(premium.getMinimumOrderCost()));
	        return new SimpleStringProperty("-");
	    });

	    TableColumn<Restaurant, String> colCommission = new TableColumn<>("Commission (%)");
	    colCommission.setCellValueFactory(cell -> {
	        if (cell.getValue() instanceof PremiumRestaurant premium) 
	            return new SimpleStringProperty(String.valueOf(premium.getAdditionalCommissionPercentagePerOrder()));
	        return new SimpleStringProperty("-");
	    });

	    tableView.getColumns().addAll(
	        colSelect, colName, colKitchen, colRating, colIsOpen, colBaseFee,
	        colPrepTime, colExpressCost, colMinOrder, colCommission
	    );

	    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

	    Button finishButton = UIHelper.createButton("Finish", () -> onFinish.accept(selected));

	    VBox root = UIHelper.createVRoot();
	    root.getChildren().addAll(tableView, finishButton);
	    
	    MenuManager.goTo(new Scene(root, 900, 450));
	}

	
}
