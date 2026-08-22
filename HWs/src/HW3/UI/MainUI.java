package HW3.UI;

import HW3.DeliveryDataBase;
import HW3.Utils.DataInitializer;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainUI extends UIBase {
	
	private AdminUI adminUI;
	private CustomerUI customerUI;
	private RestAdminUI restAdminUI;
	private OrderUI orderUI;
	private RiderUI riderUI;
	

    private MainUI(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
	}

	public MainUI(Stage stage) {
		super(stage, DataInitializer.initialDataBase(), () -> {});
		
        stage.setTitle("Delivery System");
        
        adminUI = new AdminUI(stage, deliveryDataBase, this::Init);
        
    }
	

	@Override
	public void Init() {
        VBox root = UIHelper.createVRoot();

        Label title = new Label("Delivery System");

        Button adminButton = UIHelper.createButton(
                "System Administrator Login",
                e -> adminUI.Init()
        );

        Button userButton = UIHelper.createButton(
                "User Login",
                e -> showUserTypeScreen()
        );

        Button exitButton = UIHelper.createButton(
                "Exit",
                e -> stage.close()
        );

        root.getChildren().addAll(
                title,
                adminButton,
                userButton,
                exitButton
        );

        UIHelper.setScene(stage, root, 500, 400);
	}

    
    private void showUserTypeScreen() {

        VBox root = UIHelper.createVRoot();

        Label title = new Label("Select User Type");

        Button customerButton = UIHelper.createButton(
                "Customer",
                e -> showCustomerLogin()
        );

        Button riderButton = UIHelper.createButton(
                "Rider",
                e -> showRiderLogin()
        );

        Button restaurantAdminButton = UIHelper.createButton(
                "Restaurant Administrator",
                e -> showRestaurantAdminLogin()
        );

        Button backButton = UIHelper.createButton(
                "Back",
                e -> Init()
        );

        root.getChildren().addAll(
                title,
                customerButton,
                riderButton,
                restaurantAdminButton,
                backButton
        );

        UIHelper.setScene(stage, root, 500, 500);
    }
    


    private void showCustomerLogin() {

        VBox root = UIHelper.createVRoot();

        Label title = new Label("Customer Login");

        TextField code = new TextField();
        code.setPromptText("Customer Code");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        Button loginButton = UIHelper.createButton("Login");
        Button backButton = UIHelper.createButton("Back");

//        loginButton.setOnAction(e -> {
//
//            // TODO:
//            // Authenticate customer using your DeliverySystem.
//
//            showCustomerScreen();
//        });

        backButton.setOnAction(e ->
                showUserTypeScreen()
        );

        root.getChildren().addAll(
                title,
                code,
                password,
                loginButton,
                backButton
        );

        UIHelper.setScene(stage, root, 500, 400);
    }


    private void showRiderLogin() {

        VBox root = UIHelper.createVRoot();

        Label title = new Label("Rider Login");

        TextField code = new TextField();
        code.setPromptText("Rider Code");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        Button loginButton = UIHelper.createButton("Login");
        Button backButton = UIHelper.createButton("Back");

        loginButton.setOnAction(e -> {

            // TODO:
            // Authenticate rider.

            showRiderScreen();
        });

        backButton.setOnAction(e ->
                showUserTypeScreen()
        );

        root.getChildren().addAll(
                title,
                code,
                password,
                loginButton,
                backButton
        );

        UIHelper.setScene(stage, root, 500, 400);
    }

    private void showRestaurantAdminLogin() {

        VBox root = UIHelper.createVRoot();

        Label title = new Label("Restaurant Administrator Login");

        TextField code = new TextField();
        code.setPromptText("Restaurant Administrator Code");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        Button loginButton = UIHelper.createButton("Login");
        Button backButton = UIHelper.createButton("Back");

        loginButton.setOnAction(e -> {

            // TODO:
            // Authenticate restaurant administrator.

            showRestaurantAdminScreen();
        });

        backButton.setOnAction(e ->
                showUserTypeScreen()
        );

        root.getChildren().addAll(
                title,
                code,
                password,
                loginButton,
                backButton
        );

        UIHelper.setScene(stage, root, 500, 450);
    }


    private void showAllRestaurants() {

        MessageBox.Info(
                "Restaurants",
                "All restaurants will be displayed here."
        );
    }

	private void searchRestaurant() {

        TextInputDialog dialog =
                new TextInputDialog();

        dialog.setTitle("Search Restaurant");
        dialog.setHeaderText("Search Restaurant by Code");
        dialog.setContentText("Restaurant Code:");

        dialog.showAndWait().ifPresent(code -> {

            // TODO:
            // Search your restaurants list.

            MessageBox.Info(
                    "Search",
                    "Searching for restaurant: " + code
            );
        });
    }

    private void addRestaurant() {

        MessageBox.Info(
                "Add Restaurant",
                "Restaurant creation form will be displayed here."
        );
    }

    private void updateRestaurantRating() {

        MessageBox.Info(
                "Update Rating",
                "Restaurant rating update form will be displayed here."
        );
    }

    private void openRestaurant() {

        MessageBox.Info(
                "Open Restaurant",
                "Restaurant will be opened here."
        );
    }

    private void closeRestaurant() {

        MessageBox.Info(
                "Close Restaurant",
                "Restaurant will be closed here."
        );
    }

    private void showRestaurantsByType() {

        ChoiceDialog<String> dialog =
                new ChoiceDialog<>(
                        "Fast Food",
                        "Fast Food",
                        "Premium",
                        "Regular"
                );

        dialog.setTitle("Restaurant Type");
        dialog.setHeaderText("Select Restaurant Type");
        dialog.setContentText("Type:");

        dialog.showAndWait().ifPresent(type -> {

            // TODO:
            // Filter restaurants according to type.

            MessageBox.Info(
                    "Restaurants",
                    "Showing " + type + " restaurants."
            );
        });
    }

    private void showOpenRestaurants() {

        MessageBox.Info(
                "Open Restaurants",
                "All currently open restaurants will be displayed here."
        );
    }

    // =========================================================
    // Other Screens
    // =========================================================

    private void showRiderScreen() {

        MessageBox.Info(
                "Rider",
                "Rider screen will be implemented here."
        );
    }

    private void showRestaurantAdminScreen() {

        MessageBox.Info(
                "Restaurant Administrator",
                "Restaurant administrator screen will be implemented here."
        );
    }

    
    
}
