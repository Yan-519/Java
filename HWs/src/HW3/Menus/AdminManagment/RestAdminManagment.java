package HW3.Menus.AdminManagment;


import HW3.DeliveryDataBase;
import HW3.Menus.UIBase;
import HW3.Utils.UIHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RestAdminManagment extends UIBase {

	public RestAdminManagment(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
		// TODO Auto-generated constructor stub
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
	    		() -> UIHelper.showList(stage, deliveryDataBase.getRestAdmins(), this::Main));
	    Button addAdminButton = UIHelper.createButton("Add Restaurant Admin to a restaurant", this::addRestaurantAdmin);

	    Button searchAdminButton = UIHelper.createButton("Search Restaurant Admin", this::searchRestaurantAdmin);
	    Button updateStatusButton = UIHelper.createButton("Update Admin Status", this::updateAdminStatus);

	    Button backButton = UIHelper.createButton("Back", backF);

	    grid.add(showAllButton, 0, 0);
	    grid.add(addAdminButton, 1, 0);

	    grid.add(searchAdminButton, 0, 1);
	    grid.add(updateStatusButton, 1, 1);

	    grid.add(backButton, 0, 2, 2, 1);

	    root.setCenter(grid);

	    stage.setScene(new Scene(root, 700, 600));
	}

	private void addRestaurantAdmin() {
	    // TODO: Implement logic to add a new administrator and assign them to a restaurant
	}

	private void searchRestaurantAdmin() {
	    // TODO: Implement logic to search for a restaurant administrator
	}

	private void updateAdminStatus() {
	    // TODO: Implement logic to update a restaurant administrator's status
	}

}
