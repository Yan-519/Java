package HW3.Menus.AdminManagment;

import HW3.DeliveryDataBase;
import HW3.DataObjects.RestAdmin;
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

public class RestAdminManagment extends UIBase {


	public RestAdminManagment(DeliveryDataBase deliveryDataBase) {
		super(deliveryDataBase);
	}

	@Override
	protected void Main() {
	    BorderPane root = new BorderPane();

	    VBox header = UIHelper.createVRoot("Restaurant Administrator Management");
	    root.setTop(header);

	    GridPane grid = new GridPane();
	    grid.setHgap(20);
	    grid.setVgap(20);
	    grid.setPadding(new Insets(30));
	    grid.setAlignment(Pos.CENTER);

	    Button showAllButton = UIHelper.createButton("Show All Restaurant Admins", 
	    		() -> Tables.restAdmin( deliveryDataBase.getRestAdmins()));
	    Button addAdminButton = UIHelper.createButton("Add Restaurant Admin & connect to a restaurant", this::addRestaurantAdmin);

	    Button searchAdminButton = UIHelper.createButton("Search Restaurant Admin", 
	    		() -> MessageBox.Info(DataSelector.selectRestAdmin())
		);
	    Button updateStatusButton = UIHelper.createButton("Update Admin Status", this::updateAdminStatus);

	    grid.add(showAllButton, 0, 0);
	    grid.add(addAdminButton, 1, 0);

	    grid.add(searchAdminButton, 0, 1);
	    grid.add(updateStatusButton, 1, 1);

	    grid.add(UIHelper.createBackButton(), 0, 2, 2, 1);

	    root.setCenter(grid);
	    
	    MenuManager.goTo(new Scene(root, 500, 300));
	}

	// add a new restaurant admin and connect it to a restaurant
	private void addRestaurantAdmin() {
		int code = deliveryDataBase.getNextRestAdminCode();
		
		InputManager.createRestAdmin(code, restAdmin -> {
			if(restAdmin != null) {
				try {
					deliveryDataBase.add(restAdmin);
					
					boolean connectToRestaurant = MessageBox.inputBOOL("Do you want to connect the new admin to a restaurant?");
					
					if(connectToRestaurant) {
						Restaurant restaurant = DataSelector.selectRestaurant();
						if(restaurant != null) {
							try {
								deliveryDataBase.addRestToAdmin(restAdmin.getCode(), restaurant.getCode());
								MessageBox.Info("Restaurant Admin has been added and connected to the restaurant");
							} catch (CodedNotFoundException | TargetObjectDoesntExistException e) {
								MessageBox.error(e);
							}
						}
					}
					
				} catch (TargetObjectAlreadyExistException e) {
					MessageBox.error(e);
				}
			}
			MenuManager.goBack();
		});
	}

	// update the status of the admin by adding a restaurant to his control
	private void updateAdminStatus() {
		RestAdmin restAdmin = DataSelector.selectRestAdmin();
		if(restAdmin == null) return;
		
		String remove = "Remove a restaurant from the admin control";
		String add = "Add a restaurant to the admin control";
		
		String choice = MessageBox.inputSelect(add, remove);
		if(choice == null) return;

		
		if(choice.equals(remove)) {
			Restaurant restaurant = DataSelector.dataFilter(DataSelector::selectRestaurant, 
					r -> restAdmin.containsRestaurant(r.getCode()),
					"The selected restaurant is not under the selected manager control");
			
			if(restaurant == null) return;
			
			try {
				deliveryDataBase.removeRestFromAdmin(restAdmin.getCode(), restaurant.getCode());
			} catch (CodedNotFoundException | TargetObjectDoesntExistException e) {
				MessageBox.error(e);
			}
		}
		else if(choice.equals(add)) {
			
			Restaurant restaurant = DataSelector.dataFilter(DataSelector::selectRestaurant, 
					r -> !restAdmin.containsRestaurant(r.getCode()),
					"The selected restaurant is already under the selected manager control");
			if(restaurant == null) return;
			
			try {
				deliveryDataBase.addRestToAdmin(restAdmin.getCode(), restaurant.getCode());
			} catch (CodedNotFoundException | TargetObjectDoesntExistException e) {
				MessageBox.error(e);
			}
		}
	}

	
}
