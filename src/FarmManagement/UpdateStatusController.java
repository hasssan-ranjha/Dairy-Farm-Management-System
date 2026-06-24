package FarmManagement;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class UpdateStatusController extends BaseController {

    @FXML private ComboBox<Cow> comboCows;
    @FXML private RadioButton rbSold, rbDeceased;
    @FXML private DatePicker dateEvent;
    @FXML private TextField txtBuyer, txtSalePrice;
    @FXML private Label lblBuyer, lblPrice;

    @Override
    protected void onManagerSet() {
        comboCows.getItems().setAll(manager.getCows().filtered(c -> c.getStatus() == AnimalStatus.ACTIVE));
        dateEvent.setValue(LocalDate.now());

        rbDeceased.selectedProperty().addListener((obs, oldVal, newVal) -> {
            boolean isDeceased = newVal;
            txtBuyer.setDisable(isDeceased);
            txtSalePrice.setDisable(isDeceased);
            if (isDeceased) {
                txtBuyer.clear();
                txtSalePrice.setText("0");
            }
        });
    }

    @FXML
    private void handleUpdate() {
        Cow selected = comboCows.getValue();
        LocalDate eventDate = dateEvent.getValue();

        if (selected == null || eventDate == null) {
            showAlert("Selection Error", "Please select a cow and a date.");
            return;
        }

        // --- VALIDATION SECTION ---

        // 1. Check for Future Date
        if (eventDate.isAfter(LocalDate.now())) {
            showAlert("Date Error", "The event date cannot be in the future!");
            return;
        }

        // 2. Check Logical Sequence (Sale cannot happen before Purchase)
        if (eventDate.isBefore(selected.getEntryDate())) {
            showAlert("Logic Error", "This cow was purchased on " + selected.getEntryDate() +
                    ". You cannot record a status change before that date.");
            return;
        }

        try {
            if (rbSold.isSelected()) {
                String buyer = txtBuyer.getText().trim();
                double price = Double.parseDouble(txtSalePrice.getText());

                // 3. Check for Negative Sale Price
                if (price < 0) {
                    showAlert("Price Error", "Sale price cannot be negative.");
                    return;
                }

                if (buyer.isEmpty()) {
                    showAlert("Error", "Buyer name is required for sales.");
                    return;
                }

                selected.sell(buyer, price, eventDate);
                showAlert("Success", "Cow #" + selected.getId() + " marked as SOLD.");
            } else {
                selected.markDeceased(eventDate);
                showAlert("Success", "Cow #" + selected.getId() + " marked as DECEASED.");
            }

            manager.saveToFile();
            clearForm();

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid sale price.");
        }
    }

    private void clearForm() {
        comboCows.getItems().setAll(manager.getCows().filtered(c -> c.getStatus() == AnimalStatus.ACTIVE));
        txtBuyer.clear();
        txtSalePrice.clear();
        comboCows.setValue(null);
        dateEvent.setValue(LocalDate.now());
    }

    private void showAlert(String title, String msg) {
        // Using ERROR type for validation failures makes them stand out
        Alert.AlertType type = title.equals("Success") ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR;
        new Alert(type, msg).showAndWait();
    }
}