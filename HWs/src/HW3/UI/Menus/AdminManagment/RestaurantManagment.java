package HW3.UI.Menus.AdminManagment;

import HW3.DeliveryDataBase;
import HW3.DeliveryDataBase.CodedType;
import HW3.DataObjects.Restaurant;
import HW3.Exceptions.CodedNotFoundException;
import HW3.Exceptions.TargetObjectAlreadyExistException;
import HW3.Exceptions.TargetObjectDoesntExistException;
import HW3.UI.MessageBox;
import HW3.UI.UIHelper;
import HW3.UI.Menus.UIBase;
import HW3.UI.MessageBox.NumberSign;
import HW3.Utils.DataSelector;
import HW3.Utils.InputManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RestaurantManagment extends UIBase {

	public RestaurantManagment(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
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

        VBox header = UIHelper.createVRoot("Restaurant Management");
        root.setTop(header);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(30));
        grid.setAlignment(Pos.CENTER);

        Button showAllButton = UIHelper.createButton("Show All Restaurants", e -> {
        	UIHelper.showList(stage, null, this::Main);
        });
        Button searchByCodeButton = UIHelper.createButton("Search Restaurant by Code", 
        		() -> MessageBox.Info(DataSelector.selectRestaurant()));
        
        Button addRestaurantButton = UIHelper.createButton("Add New Restaurant", this::addNewRestaurant);
        Button updateRatingButton = UIHelper.createButton("Update Restaurant Rating", 
        		() -> {
        			Restaurant restaurant = DataSelector.selectRestaurant();
        			if(restaurant == null) return;
        			
        			double raiting = MessageBox.inputDOUB(null, "Enter new raiting", NumberSign.NOT_NEGATIVE, 5);
        			try {
						deliveryDataBase.updateRestaurantRaiting(restaurant.getCode(), raiting);
					} catch (CodedNotFoundException | TargetObjectDoesntExistException e1) {
						MessageBox.error(e1);
					}
        		});
        
        Button openRestaurantButton = UIHelper.createButton("Open Restaurant", () ->{
        	Restaurant restaurant = DataSelector.dataFilter(DataSelector::selectRestaurant, r -> !r.isOpen(), "Select a close restaurant");
        	if(restaurant == null) return;
        	restaurant.setOpen(true);
        });
        Button closeRestaurantButton = UIHelper.createButton("Close Restaurant", () ->{
        	Restaurant restaurant = DataSelector.dataFilter(DataSelector::selectRestaurant, r -> r.isOpen(), "Select an open restaurant");
        	if(restaurant == null) return;
        	restaurant.setOpen(false);
        });
        
        Button filterByTypeButton = UIHelper.createButton("Show Restaurants by Type", this::showRestaurantsByType);
        Button filterOpenOnlyButton = UIHelper.createButton("Show Open Restaurants Only", () ->
        	UIHelper.showList(stage, deliveryDataBase.getRestaurants().stream().filter(r -> r.isOpen()).toList(), this::Main)
        );

        Button backButton = UIHelper.createButton("Back", backF);

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

        stage.setScene(new Scene(root, 700, 600));
    }

    private void addNewRestaurant() { // add select type ////////////////////////////////////////////////////////////////////////////////////
    	
    	int code = deliveryDataBase.generateCode(CodedType.Restaurant);

    	InputManager.createRestaurant(stage, code, rest -> {
    	    if (rest != null) {
    	    	try {
        	        if (deliveryDataBase.add(rest)) 
        	            MessageBox.Info("The customer code is " + code);
        	    } catch (TargetObjectAlreadyExistException ex) {
        	        MessageBox.error(ex);
        	    }
    	    }
    	    Main();
    	});
    }

    private void showRestaurantsByType() {
        // TODO: Implement logic to filter and display restaurants by cuisine/type
    }


}
