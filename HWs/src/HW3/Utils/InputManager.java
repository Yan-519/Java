package HW3.Utils;

import HW3.DeliveryDataBase;
import HW3.DataObjects.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class InputManager {

    private static DeliveryDataBase deliveryDataBase;

    public static void setDeliveryDataBase(DeliveryDataBase db) {
        deliveryDataBase = db;
    }


    // Creates a new Rider
    public static void createRider(Consumer<Rider> callback) {
        GridPane grid = UIHelper.createGrid();

        TextField idField = new TextField();
        TextField phoneField = new TextField();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField vehicleField = new TextField();

        grid.add(new Label("ID (9 digits):"), 0, 0); grid.add(idField, 1, 0);
        grid.add(new Label("Phone (IL):"), 0, 1); grid.add(phoneField, 1, 1);
        grid.add(new Label("First Name:"), 0, 2); grid.add(firstNameField, 1, 2);
        grid.add(new Label("Last Name:"), 0, 3); grid.add(lastNameField, 1, 3);
        grid.add(new Label("Vehicle:"), 0, 4); grid.add(vehicleField, 1, 4);

        VBox root = UIHelper.createBaseFormLayout("Create Rider", grid, () -> {
            String id = idField.getText().trim();
            String phone = phoneField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String vehicle = vehicleField.getText().trim();

            if (!DataChecker.isValidId(id)) {
                MessageBox.error("Validation Error", "Invalid ID format. Must be 9 positive digits.");
                return;
            }
            if (deliveryDataBase != null && deliveryDataBase.isContainsRider(id)) {
                MessageBox.error("Validation Error", "Rider with ID " + id + " already exists.");
                return;
            }
            if (!DataChecker.isValidPhoneNumber(phone)) {
                MessageBox.error("Validation Error", "Invalid Israeli phone number.");
                return;
            }
            if (firstName.isEmpty() || lastName.isEmpty() || vehicle.isEmpty()) {
                MessageBox.error("Validation Error", "All fields are required.");
                return;
            }

            if (firstName.indexOf(' ') != -1) {
                firstName = firstName.substring(0, firstName.indexOf(' '));
            }

            callback.accept(new Rider(id, firstName, lastName, phone, vehicle));
        }, () -> callback.accept(null));
        
        
        MenuManager.goTo(new Scene(root, 400, 320));
    }

    // Creates a new Customer
    public static void createCustomer(int code, Consumer<Customer> callback) {
        GridPane grid = UIHelper.createGrid();

        TextField phoneField = new TextField();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField streetField = new TextField();
        TextField townField = new TextField();
        TextField zipField = new TextField();
        TextField emailField = new TextField();
        TextField balanceField = new TextField();

        grid.add(new Label("Phone (IL):"), 0, 0); grid.add(phoneField, 1, 0);
        grid.add(new Label("First Name:"), 0, 1); grid.add(firstNameField, 1, 1);
        grid.add(new Label("Last Name:"), 0, 2); grid.add(lastNameField, 1, 2);
        grid.add(new Label("Street:"), 0, 3); grid.add(streetField, 1, 3);
        grid.add(new Label("Town:"), 0, 4); grid.add(townField, 1, 4);
        grid.add(new Label("ZIP Code:"), 0, 5); grid.add(zipField, 1, 5);
        grid.add(new Label("Email:"), 0, 6); grid.add(emailField, 1, 6);
        grid.add(new Label("Balance:"), 0, 7); grid.add(balanceField, 1, 7);

        VBox root = UIHelper.createBaseFormLayout("Create Customer", grid, () -> {
            String phone = phoneField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String street = streetField.getText().trim();
            String town = townField.getText().trim();
            String zip = zipField.getText().trim();
            String email = emailField.getText().trim();
            String balanceStr = balanceField.getText().trim();

            if (!DataChecker.isValidPhoneNumber(phone)) {
                MessageBox.error("Validation Error", "Invalid Israeli phone number.");
                return;
            }
            if (!DataChecker.isValidZipCode(zip)) {
                MessageBox.error("Validation Error", "ZIP code must be 5-7 non-negative digits.");
                return;
            }
            if (!DataChecker.isValidEmail(email)) {
                MessageBox.error("Validation Error", "Invalid email address.");
                return;
            }
            if (firstName.isEmpty() || lastName.isEmpty() || street.isEmpty() || town.isEmpty()) {
                MessageBox.error("Validation Error", "All fields are required.");
                return;
            }

            try {
                double balance = Double.parseDouble(balanceStr);
                if (balance < 0) {
                    MessageBox.error("Validation Error", "Balance cannot be negative.");
                    return;
                }

                if (firstName.indexOf(' ') != -1) {
                    firstName = firstName.substring(0, firstName.indexOf(' '));
                }

                callback.accept(new Customer(code, firstName, lastName, street, town, zip, phone, email, balance));
            } catch (NumberFormatException e) {
                MessageBox.error("Validation Error", "Balance must be a valid number.");
            }
        }, () -> callback.accept(null));

        MenuManager.goTo(new Scene(root, 400, 450));
    }

    // Creates a new Restaurant
    public static void createRestaurant(int code, Consumer<Restaurant> callback) {
        GridPane grid = UIHelper.createGrid();

        TextField nameField = new TextField();
        TextField kitchenField = new TextField();
        TextField ratingField = new TextField();
        TextField feeField = new TextField();
        CheckBox isOpenBox = new CheckBox("Open");

        grid.add(new Label("Name:"), 0, 0); grid.add(nameField, 1, 0);
        grid.add(new Label("Kitchen Type:"), 0, 1); grid.add(kitchenField, 1, 1);
        grid.add(new Label("Rating (0-5):"), 0, 2); grid.add(ratingField, 1, 2);
        grid.add(new Label("Delivery Fee:"), 0, 3); grid.add(feeField, 1, 3);
        grid.add(new Label("Is Open:"), 0, 4); grid.add(isOpenBox, 1, 4);

        VBox root = UIHelper.createBaseFormLayout("Create Restaurant", grid, () -> {
            String name = nameField.getText().trim();
            String kitchen = kitchenField.getText().trim();

            if (name.isEmpty() || kitchen.isEmpty()) {
                MessageBox.error("Validation Error", "Name and Kitchen Type are required.");
                return;
            }

            try {
                double rating = Double.parseDouble(ratingField.getText().trim());
                if (rating < 0 || rating > 5) {
                    MessageBox.error("Validation Error", "Rating must be between 0 and 5.");
                    return;
                }
                double fee = Double.parseDouble(feeField.getText().trim());
                if (fee < 0) {
                    MessageBox.error("Validation Error", "Delivery Fee must be non-negative.");
                    return;
                }

                callback.accept(new Restaurant(code, name, kitchen, rating, isOpenBox.isSelected(), fee));
            } catch (NumberFormatException e) {
                MessageBox.error("Validation Error", "Rating and Delivery Fee must be valid numbers.");
            }
        }, () -> callback.accept(null));

        MenuManager.goTo(new Scene(root, 400, 320));
    }

    // Creates a new FastFoodRestaurant
    public static void createFastFoodRestaurant(int code, Consumer<FastFoodRestaurant> callback) {
        GridPane grid = UIHelper.createGrid();

        TextField nameField = new TextField();
        TextField kitchenField = new TextField();
        TextField ratingField = new TextField();
        TextField feeField = new TextField();
        TextField prepTimeField = new TextField();
        TextField expressCostField = new TextField();
        CheckBox isOpenBox = new CheckBox("Open");

        grid.add(new Label("Name:"), 0, 0); grid.add(nameField, 1, 0);
        grid.add(new Label("Kitchen Type:"), 0, 1); grid.add(kitchenField, 1, 1);
        grid.add(new Label("Rating (0-5):"), 0, 2); grid.add(ratingField, 1, 2);
        grid.add(new Label("Delivery Fee:"), 0, 3); grid.add(feeField, 1, 3);
        grid.add(new Label("Prep Time (min):"), 0, 4); grid.add(prepTimeField, 1, 4);
        grid.add(new Label("Express Cost:"), 0, 5); grid.add(expressCostField, 1, 5);
        grid.add(new Label("Is Open:"), 0, 6); grid.add(isOpenBox, 1, 6);

        VBox root = UIHelper.createBaseFormLayout("Create Fast Food Restaurant", grid, () -> {
            String name = nameField.getText().trim();
            String kitchen = kitchenField.getText().trim();

            if (name.isEmpty() || kitchen.isEmpty()) {
                MessageBox.error("Validation Error", "Name and Kitchen Type are required.");
                return;
            }

            try {
                double rating = Double.parseDouble(ratingField.getText().trim());
                if (rating < 0 || rating > 5) {
                    MessageBox.error("Validation Error", "Rating must be between 0 and 5.");
                    return;
                }
                double fee = Double.parseDouble(feeField.getText().trim());
                int prepTime = Integer.parseInt(prepTimeField.getText().trim());
                double expressCost = Double.parseDouble(expressCostField.getText().trim());

                if (fee < 0 || prepTime < 0 || expressCost < 0) {
                    MessageBox.error("Validation Error", "Numeric values must be non-negative.");
                    return;
                }

                callback.accept(new FastFoodRestaurant(code, name, kitchen, rating, isOpenBox.isSelected(), fee, prepTime, expressCost));
            } catch (NumberFormatException e) {
                MessageBox.error("Validation Error", "Please enter valid numeric values.");
            }
        }, () -> callback.accept(null));

        MenuManager.goTo(new Scene(root, 400, 400));
    }

    // Creates a new PremiumRestaurant
    public static void createPremiumRestaurant(int code, Consumer<PremiumRestaurant> callback) {
        GridPane grid = UIHelper.createGrid();

        TextField nameField = new TextField();
        TextField kitchenField = new TextField();
        TextField ratingField = new TextField();
        TextField feeField = new TextField();
        TextField minOrderField = new TextField();
        TextField commissionField = new TextField();
        CheckBox isOpenBox = new CheckBox("Open");

        grid.add(new Label("Name:"), 0, 0); grid.add(nameField, 1, 0);
        grid.add(new Label("Kitchen Type:"), 0, 1); grid.add(kitchenField, 1, 1);
        grid.add(new Label("Rating (0-5):"), 0, 2); grid.add(ratingField, 1, 2);
        grid.add(new Label("Delivery Fee:"), 0, 3); grid.add(feeField, 1, 3);
        grid.add(new Label("Minimum Order:"), 0, 4); grid.add(minOrderField, 1, 4);
        grid.add(new Label("Commission %:"), 0, 5); grid.add(commissionField, 1, 5);
        grid.add(new Label("Is Open:"), 0, 6); grid.add(isOpenBox, 1, 6);

        VBox root = UIHelper.createBaseFormLayout("Create Premium Restaurant", grid, () -> {
            String name = nameField.getText().trim();
            String kitchen = kitchenField.getText().trim();

            if (name.isEmpty() || kitchen.isEmpty()) {
                MessageBox.error("Validation Error", "Name and Kitchen Type are required.");
                return;
            }

            try {
                double rating = Double.parseDouble(ratingField.getText().trim());
                if (rating < 0 || rating > 5) {
                    MessageBox.error("Validation Error", "Rating must be between 0 and 5.");
                    return;
                }
                double fee = Double.parseDouble(feeField.getText().trim());
                double minOrder = Double.parseDouble(minOrderField.getText().trim());
                double commission = Double.parseDouble(commissionField.getText().trim());

                if (fee < 0 || minOrder < 0 || commission < 0) {
                    MessageBox.error("Validation Error", "Numeric values must be non-negative.");
                    return;
                }

                callback.accept(new PremiumRestaurant(code, name, kitchen, rating, isOpenBox.isSelected(), fee, minOrder, commission));
            } catch (NumberFormatException e) {
                MessageBox.error("Validation Error", "Please enter valid numeric values.");
            }
        }, () -> callback.accept(null));

        MenuManager.goTo(new Scene(root, 400, 400));
    }

    // Creates a new Date
    public static void createDate(Consumer<Date> callback) {
        GridPane grid = UIHelper.createGrid();

        TextField yearField = new TextField();
        TextField monthField = new TextField();
        TextField dayField = new TextField();

        grid.add(new Label("Year (2000-2026):"), 0, 0); grid.add(yearField, 1, 0);
        grid.add(new Label("Month (1-12):"), 0, 1); grid.add(monthField, 1, 1);
        grid.add(new Label("Day:"), 0, 2); grid.add(dayField, 1, 2);

        VBox root = UIHelper.createBaseFormLayout("Create Date", grid, () -> {
            try {
                int year = Integer.parseInt(yearField.getText().trim());
                int month = Integer.parseInt(monthField.getText().trim());
                int day = Integer.parseInt(dayField.getText().trim());

                if (year < 2000 || year > 2026) {
                    MessageBox.error("Validation Error", "Year must be between 2000 and 2026.");
                    return;
                }
                if (month < 1 || month > 12) {
                    MessageBox.error("Validation Error", "Month must be between 1 and 12.");
                    return;
                }
                int maxDay = DataChecker.getDaysInMonth(year, month);
                if (day < 1 || day > maxDay) {
                    MessageBox.error("Validation Error", "Day must be between 1 and " + maxDay + " for the selected month.");
                    return;
                }

                callback.accept(new Date(day, month, year));
            } catch (NumberFormatException e) {
                MessageBox.error("Validation Error", "Please enter valid integers for date fields.");
            }
        }, () -> callback.accept(null));

        MenuManager.goTo(new Scene(root, 350, 250));
    }

    // Creates a Date after a given Date
    public static void createDateAfterDate(Date before, Consumer<Date> callback) {
        createDate( d ->{
        	if(d == null) callback.accept(null);
        	else if(!d.isAfter(before) && !d.equals(before))
        		MessageBox.error("Validation Error", "The delivery date must be after the order creation date.");
        	else callback.accept(d);
        } );
    }
}