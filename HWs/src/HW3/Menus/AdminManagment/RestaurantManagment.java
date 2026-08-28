package HW3.Menus.AdminManagment;

import HW3.DeliveryDataBase;
import HW3.DeliveryDataBase.CodedType;
import HW3.DataObjects.Restaurant;
import HW3.Exceptions.CodedNotFoundException;
import HW3.Exceptions.TargetObjectAlreadyExistException;
import HW3.Exceptions.TargetObjectDoesntExistException;
import HW3.Menus.Tables;
import HW3.Menus.UIBase;
import HW3.Utils.DataSelector;
import HW3.Utils.InputManager;
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

public class RestaurantManagment extends UIBase {


	public RestaurantManagment(DeliveryDataBase deliveryDataBase) {
		super(deliveryDataBase);
	}

	@Override
	protected void Main() {
    	BorderPane root = new BorderPane();

        VBox header = UIHelper.createVRoot("Restaurant Management");
        root.setTop(header);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(30));
        grid.setAlignment(Pos.CENTER);

        Button showAllButton = UIHelper.createButton("Show All Restaurants", e -> {
        	Tables.restaurant( deliveryDataBase.getRestaurants());
        });
        Button searchByCodeButton = UIHelper.createButton("Search Restaurant by Code", 
        		() -> MessageBox.Info(DataSelector.selectRestaurant()));
        
        Button addRestaurantButton = UIHelper.createButton("Add New Restaurant", this::addNewRestaurant);
        Button updateRatingButton = UIHelper.createButton("Update Restaurant Rating", 
        		() -> {
        			Restaurant restaurant = DataSelector.selectRestaurant();
        			if(restaurant == null) return;
        			
        			Double raiting = MessageBox.inputDOUB(null, "Enter new raiting  (0-5)", 5);
        			if(raiting == null) return;
        			try {
						deliveryDataBase.updateRestaurantRaiting(restaurant.getCode(), raiting);
					} catch (CodedNotFoundException | TargetObjectDoesntExistException e1) {
						MessageBox.error(e1);
					}
        		});
        
        Button openRestaurantButton = UIHelper.createButton("Open Restaurant", () ->{
        	Restaurant restaurant = DataSelector.dataFilter(DataSelector::selectRestaurant, r -> !r.getIsOpen(), "Select a close restaurant");
        	if(restaurant == null) return;
        	restaurant.setOpen(true);
        });
        Button closeRestaurantButton = UIHelper.createButton("Close Restaurant", () ->{
        	Restaurant restaurant = DataSelector.dataFilter(DataSelector::selectRestaurant, r -> r.getIsOpen(), "Select an open restaurant");
        	if(restaurant == null) return;
        	restaurant.setOpen(false);
        });
        
        Button filterByTypeButton = UIHelper.createButton("Show Restaurants by Type", () -> {
			String type = MessageBox.inputSTR(null, "Enter restauran kitchen type", null);
			if(type == null) return;
			Tables.restaurant(
					deliveryDataBase.getRestaurants().stream().filter(r -> r.getKitchenType().equalsIgnoreCase(type)).toList());
		});
        Button filterOpenOnlyButton = UIHelper.createButton("Show Open Restaurants Only", () ->
        Tables.restaurant( deliveryDataBase.getRestaurants().stream().filter(r -> r.getIsOpen()).toList())
        );

        Button backButton = UIHelper.createButton("Back", MenuManager::goBack);

        grid.add(showAllButton, 0, 0);
        grid.add(searchByCodeButton, 1, 0);

        grid.add(addRestaurantButton, 0, 1);
        grid.add(updateRatingButton, 1, 1);

        grid.add(openRestaurantButton, 0, 2);
        grid.add(closeRestaurantButton, 1, 2);

        grid.add(filterByTypeButton, 0, 3);
        grid.add(filterOpenOnlyButton, 1, 3);

        grid.add(backButton, 0, 4, 2, 1);

        root.setCenter(grid);

        MenuManager.goTo(new Scene(root, 500, 400));
    }

    private void addNewRestaurant() {
    	
    	int code;
		try {
			code = deliveryDataBase.generateCode(CodedType.Restaurant);
		} catch (TargetObjectDoesntExistException e) {
			MessageBox.error(e);
			return;
		}
		
		String type = MessageBox.inputSelect("Regular", "Fast food", "Primium");
		if(type == null) return;
		
		
		else if(type.equals("Regular")) {
			InputManager.createRestaurant( code, rest -> {
	    	    if (rest != null) {
	    	    	try {
	        	        deliveryDataBase.add(rest);
	        	        MessageBox.Info("The customer code is " + code);
	        	    } catch (TargetObjectAlreadyExistException ex) {
	        	        MessageBox.error(ex);
	        	    }
	    	    }
	    	    MenuManager.goBack();
	    	});
		}
		else if(type.equals("Fast food")) {
			InputManager.createFastFoodRestaurant( code, rest -> {
	    	    if (rest != null) {
	    	    	try {
	        	        deliveryDataBase.add(rest);
	        	        MessageBox.Info("The customer code is " + code);
	        	    } catch (TargetObjectAlreadyExistException ex) {
	        	        MessageBox.error(ex);
	        	    }
	    	    }
	    	    MenuManager.goBack();
	    	});
		}
		else if(type.equals("Primium")) {
			InputManager.createPremiumRestaurant( code, rest -> {
	    	    if (rest != null) {
	    	    	try {
	        	        deliveryDataBase.add(rest);
	        	        MessageBox.Info("The customer code is " + code);
	        	    } catch (TargetObjectAlreadyExistException ex) {
	        	        MessageBox.error(ex);
	        	    }
	    	    }
	    	    MenuManager.goBack();
	    	});
		}
    }

}
