package FarmManagement;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML private TextField txtName;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<Role> comboRole;

    private AppLauncher mainApp;

    // This links the controller back to the main application launcher
    public void setMainApp(AppLauncher mainApp) {
        this.mainApp = mainApp;
        comboRole.getItems().setAll(Role.values());
    }

    @FXML
    private void handleLogin() {
        String name = txtName.getText();
        Role role = comboRole.getValue();
        String pass = txtPassword.getText();

        if (name.isEmpty() || role == null || pass.isEmpty()) {
            showAlert("Input Error", "Please fill in all login fields.");
            return;
        }

        User user = null;
        // Preservation of your original Credentials logic
        if (role == Role.ADMIN && pass.equals(Credentials.ADMIN_PASSWORD.getPassword())) {
            user = new Admin(name);
        } else if (role == Role.WORKER && pass.equals(Credentials.WORKER_PASSWORD.getPassword())) {
            user = new Worker(name);
        }

        if (user != null) {
            mainApp.navigateToDashboard(user);
        } else {
            showAlert("Access Denied", "Incorrect password for " + role + " role.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}