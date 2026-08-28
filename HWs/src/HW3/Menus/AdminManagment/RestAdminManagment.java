package HW3.Menus.AdminManagment;

import HW3.DeliveryDataBase;
import HW3.DataObjects.RestAdmin;
import HW3.DataObjects.Restaurant;
import HW3.Exceptions.CodedNotFoundException;
import HW3.Exceptions.TargetObjectDoesntExistException;
import HW3.Menus.Tables;
import HW3.Menus.UIBase;
import HW3.Utils.DataSelector;
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
	    Button addAdminButton = UIHelper.createButton("Add Restaurant Admin to a restaurant", this::addRestaurantAdmin);

	    Button searchAdminButton = UIHelper.createButton("Search Restaurant Admin", 
	    		() -> MessageBox.Info(DataSelector.selectRestAdmin())
		);
	    Button updateStatusButton = UIHelper.createButton("Update Admin Status", this::updateAdminStatus);

	    Button backButton = UIHelper.createButton("Back", MenuManager::goBack);

	    grid.add(showAllButton, 0, 0);
	    grid.add(addAdminButton, 1, 0);

	    grid.add(searchAdminButton, 0, 1);
	    grid.add(updateStatusButton, 1, 1);

	    grid.add(backButton, 0, 2, 2, 1);

	    root.setCenter(grid);
	    
	    MenuManager.goTo(new Scene(root, 700, 600));
	}

	private void addRestaurantAdmin() {
		RestAdmin restAdmin = DataSelector.selectRestAdmin();
		if(restAdmin == null) return;
		
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

	private void updateAdminStatus() {
		RestAdmin restAdmin = DataSelector.selectRestAdmin();
		if(restAdmin == null) return;
		
		if(restAdmin.getRestaurants().isEmpty())
			MessageBox.Info("The selected manager has no restaurants");
		
		else Tables.createCodedSelectionView(restAdmin.getRestaurants(),
				s -> {
					restAdmin.removeRestaurants(s);
					MenuManager.goBack();
				});
	}

	
}
