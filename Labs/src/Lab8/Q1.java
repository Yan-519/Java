package Lab8;

import Lab8.RoomBooking.RoomTypes;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Q1 extends Application {
	
	private ObservableList<RoomBooking> bookingList = FXCollections.observableArrayList();

    private Stage primaryStage;
    private Scene formScene;
    private Scene tableScene;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("מערכת לניהול הזמנות חדרים במלון");

        createFormScene();
        createTableScene();

        primaryStage.setScene(formScene);
        primaryStage.show();
    }

    private void createFormScene() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        Label lblTitle = new Label("Room Booking");
        lblTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label lblName = new Label("Full name:");
        TextField txtName = new TextField();

        Label lblNights = new Label("Nights count:");
        TextField txtNights = new TextField();

        Label lblType = new Label("Room type:");
        ComboBox<String> cmbRoomType = new ComboBox<>();
        cmbRoomType.getItems().addAll(RoomTypes.Standard.toString(), RoomTypes.Delux.toString(), RoomTypes.Suite.toString());
        cmbRoomType.getSelectionModel().selectFirst();

        Button btnBook = new Button("Book Room");
        btnBook.setMaxWidth(Double.MAX_VALUE);

        Label lblError = new Label();
        lblError.setStyle("-fx-text-fill: red;");

        grid.add(lblTitle, 0, 0, 2, 1);
        grid.add(lblName, 0, 1);
        grid.add(txtName, 1, 1);
        grid.add(lblNights, 0, 2);
        grid.add(txtNights, 1, 2);
        grid.add(lblType, 0, 3);
        grid.add(cmbRoomType, 1, 3);
        grid.add(btnBook, 0, 4, 2, 1);
        grid.add(lblError, 0, 5, 2, 1);

        btnBook.setOnAction(e -> {
            lblError.setText("");
            String name = txtName.getText().trim();
            String nightsText = txtNights.getText().trim();
            String roomType = cmbRoomType.getValue();

            if (name.isEmpty() || nightsText.isEmpty()) {
                lblError.setText("Fill all filds");
                return;
            }

            try {
                int nights = Integer.parseInt(nightsText);
                if (nights <= 0) {
                    lblError.setText("Nights count must be positive");
                    return;
                }

                RoomBooking booking = new RoomBooking(name, nights, RoomTypes.valueOf(roomType));
                bookingList.add(booking);

                FXCollections.sort(bookingList, RoomBooking.getNightsComparatorDescending());

                txtName.clear();
                txtNights.clear();
                cmbRoomType.getSelectionModel().selectFirst();

                primaryStage.setScene(tableScene);

            } catch (Exception ex) {
                lblError.setText(ex.getMessage());
            }
        });

        formScene = new Scene(grid, 400, 350);
    }

    private void createTableScene() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        Label lblTitle = new Label("Booking List");
        lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TableView<RoomBooking> table = new TableView<>();
        table.setItems(bookingList); 

        TableColumn<RoomBooking, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setMinWidth(60);

        TableColumn<RoomBooking, String> colName = new TableColumn<>("Full name");
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colName.setMinWidth(150);

        TableColumn<RoomBooking, Integer> colNights = new TableColumn<>("Nights count");
        colNights.setCellValueFactory(new PropertyValueFactory<>("nights"));
        colNights.setMinWidth(100);

        TableColumn<RoomBooking, String> colType = new TableColumn<>("Room type");
        colType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colType.setMinWidth(100);

        table.getColumns().addAll(colId, colName, colNights, colType);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> primaryStage.setScene(formScene));

        vbox.getChildren().addAll(lblTitle, table, btnBack);

        tableScene = new Scene(vbox, 500, 400);
    }

    public static void main(String[] args) {
        launch(args);
    }


}
