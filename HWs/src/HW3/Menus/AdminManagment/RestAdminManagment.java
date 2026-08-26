package HW3.Menus.AdminManagment;


import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import HW3.DeliveryDataBase;
import HW3.DataObjects.RestAdmin;
import HW3.DataObjects.Restaurant;
import HW3.Exceptions.CodedNotFoundException;
import HW3.Exceptions.TargetObjectDoesntExistException;
import HW3.Menus.Tables;
import HW3.Menus.UIBase;
import HW3.Utils.DataSelector;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RestAdminManagment extends UIBase {

	public RestAdminManagment(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
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
	    		() -> Tables.restAdmin(stage, deliveryDataBase.getRestAdmins(), this::Main));
	    Button addAdminButton = UIHelper.createButton("Add Restaurant Admin to a restaurant", this::addRestaurantAdmin);

	    Button searchAdminButton = UIHelper.createButton("Search Restaurant Admin", 
	    		() -> MessageBox.Info(DataSelector.selectRestAdmin())
		);
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
		
		else
			stage.setScene(new Scene(createCodedSelectionView(restAdmin.getRestaurants(), 
				s -> {
					restAdmin.removeRestaurants(s);
					Main();
				}
		)));
	}

	
	private static VBox createCodedSelectionView(List<Restaurant> items, Consumer<HashSet<Integer>> onFinish) {
        // Track selected items using their toString representation
		HashSet<Integer> selected = new HashSet<>();

        // Setup ListView with custom cells
        ListView<Restaurant> listView = new ListView<>(FXCollections.observableArrayList(items));
        listView.setCellFactory(param -> new ListCell<>() {
            private final CheckBox checkBox = new CheckBox();
            private final HBox container = new HBox(10, checkBox);

            {
                checkBox.setOnAction(e -> {
                    Restaurant currentItem = getItem();
                    if(currentItem == null) e.consume();
                    
                    if (checkBox.isSelected()) 
                        selected.add(currentItem.getCode());
                    else 
                        selected.remove(currentItem.getCode());
                });
            }

            @Override
            protected void updateItem(Restaurant item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    checkBox.setText(item.toString());
                    checkBox.setSelected(selected.contains(item.toString()));
                    setGraphic(container);
                }
            }
        });

        Button finishButton = new Button("Finish");
        finishButton.setOnAction(e -> onFinish.accept(selected));

        VBox layout = new VBox(10, listView, finishButton);
        layout.setPadding(new Insets(15));
        return layout;
    }
}
