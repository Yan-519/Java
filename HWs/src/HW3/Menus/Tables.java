package HW3.Menus;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import HW3.DataObjects.*;
import HW3.DataObjects.Helpers.Coded;
import HW3.Utils.MenuManager;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;

public class Tables {
	
	// create a table with the given columns and src
	@SuppressWarnings("unchecked")
	private static <T> void makeTable(List<T> lst, TableColumn<T, ?>...columns) {
		if (lst == null || lst.isEmpty()) {
	        MessageBox.Info("No values found");
	        return;
	    }

	    ArrayList<TableColumn<T, ?>> cols;

	    if (lst.getFirst() instanceof Coded<?>) {
	        TableColumn<T, Integer> colCode = new TableColumn<>("Code");

	        colCode.setCellValueFactory(cellData ->
	            new ReadOnlyObjectWrapper<>(
	                ((Coded<?>) cellData.getValue()).getCode()
	            )
	        );

	        cols = new ArrayList<>(Arrays.asList(columns));
	        cols.addFirst(colCode);
	    } else 
	        cols = new ArrayList<>(Arrays.asList(columns));
	    

	    cols.forEach(c -> {
	        c.setSortable(false);
	        c.setReorderable(false);
	    });

	    TableView<T> tableView =
	        new TableView<>(FXCollections.observableArrayList(lst));

	    tableView.getColumns().addAll(cols);

	    tableView.setColumnResizePolicy(
	        TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
	    );

	    tableView.setPlaceholder(
	        new Label("No data available")
	    );

	    tableView.setFixedCellSize(42);

	    tableView.setRowFactory(tv -> {
	        TableRow<T> row = new TableRow<>();

	        row.setOnMouseEntered(e -> {
	            if (!row.isEmpty())
	                row.setStyle("-fx-background-color: #f3f4f6;");
	        });

	        row.setOnMouseExited(e -> {
	            if (!row.isEmpty())
	                row.setStyle("");
	        });

	        return row;
	    });

	    for (TableColumn<T, ?> column : cols) {
	        column.setStyle(
	            "-fx-font-size: 14px;" +
	            "-fx-font-weight: bold;"
	        );
	    }

	    VBox root = UIHelper.createVRoot();

	    root.setSpacing(15);
	    root.setPadding(new Insets(20));

	    VBox.setVgrow(tableView, Priority.ALWAYS);

	    root.getChildren().addAll(
	        tableView,
	        UIHelper.createBackButton()
	    );

	    MenuManager.goTo(new Scene(root, 850, 500));
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
	
	public static void order(List<Order> deliveredOrders, Order currentOrder) {
        List<Order> allOrders = new ArrayList<>();
        
        if (currentOrder != null) 
            allOrders.add(currentOrder);
        
        
        if (deliveredOrders != null && !deliveredOrders.isEmpty()) 
            allOrders.addAll(deliveredOrders);
        
        order(allOrders);
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
}
