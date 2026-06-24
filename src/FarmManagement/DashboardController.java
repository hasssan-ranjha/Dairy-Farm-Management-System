package FarmManagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import java.io.IOException;

public class DashboardController {

    @FXML private StackPane contentArea;
    @FXML private Button btnAddCow;
    @FXML private Button btnUpdateStatus;

    private User currentUser;
    private FarmManager manager;


    // 1. Add this at the very top of the class
    private static DashboardController instance;
    public static DashboardController getInstance() { return instance; }

    // 2. Add this inside the initialize() or constructor
    public void initialize() {
        instance = this;
    }

    // 3. Add this method to handle loading the history with data
    public void loadMilkHistory(Cow cow) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Resources/MilkHistory.fxml"));
            Parent view = loader.load();

            MilkHistoryController controller = loader.getController();
            controller.setManager(manager);
            controller.setCow(cow); // Key step: Passing the cow!

            contentArea.getChildren().setAll(view);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    // This method is called by AppLauncher after login
    public void initData(User user, FarmManager manager) {
        this.currentUser = user;
        this.manager = manager;

        // LOGIC: Enforce Role-Based Access Control (RBAC)
        // If the user is a Worker, disable Admin-only buttons
        if (!user.canAddCow()) {
            btnAddCow.setDisable(true);
        }
        if (!user.canUpdateStatus()) {
            btnUpdateStatus.setDisable(true);
        }
    }


    private void loadView(String fxmlFileName) {
        try {
            // We use "Resources/" because that is the folder name in your project
            // We do NOT use a leading slash here because we want it relative to the Controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Resources/" + fxmlFileName));
            Parent view = loader.load();

            Object controller = loader.getController();
            if (controller instanceof BaseController) {
                ((BaseController) controller).setManager(manager);
            }

            contentArea.getChildren().setAll(view);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: Could not find file at: FarmManagement/Resources/" + fxmlFileName);
        }
    }


    // FXML Action Methods linked to your Dashboard.fxml buttons
    @FXML
    void showViewActive() { loadView("ViewActive.fxml"); }
    @FXML private void showAddCow() { loadView("AddCow.fxml"); }
    @FXML private void showAddMilk() { loadView("AddMilk.fxml"); }
    @FXML private void showUpdateStatus() { loadView("UpdateStatus.fxml"); }
    @FXML private void showViewClosed() { loadView("FinancialReports.fxml"); }

    @FXML
    private void handleLogout() {
        // Save data and close the app (or you could return to login screen)
        manager.saveToFile();
        System.exit(0);
    }
}