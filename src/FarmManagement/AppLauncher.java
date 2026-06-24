package FarmManagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class AppLauncher extends Application {
    private Stage primaryStage;
    private FarmManager manager = new FarmManager();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        manager.loadFromFile(); // Logic preserved: Load data on boot
        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Resources/Login.fxml"));
            Parent root = loader.load();

            LoginController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setTitle("Farm Management - Login");
            primaryStage.setScene(new Scene(root, 500, 450));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToDashboard(User user) {
        try {
            // Path adjusted for your "Option 2" structure
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Resources/Dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.initData(user, manager);

            primaryStage.setTitle("Farm System - Dashboard: " + user.name);
            primaryStage.setScene(new Scene(root, 1100, 750));
            primaryStage.centerOnScreen();

            // Auto-save when the window is closed
            primaryStage.setOnCloseRequest(e -> manager.saveToFile());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Dashboard.fxml: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}