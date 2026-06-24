package FarmManagement;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class AddMilkController extends BaseController {

    @FXML private ComboBox<Cow> comboCows;
    @FXML private TextField txtAmount;
    @FXML private DatePicker datePicker;

    @Override
    protected void onManagerSet() {
        // Show only ACTIVE cows in the dropdown
        comboCows.getItems().setAll(
                manager.getCows().filtered(c -> c.getStatus() == AnimalStatus.ACTIVE)
        );
        datePicker.setValue(LocalDate.now());

        // Custom display for ComboBox: Show ID and Breed instead of memory address
        comboCows.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Cow item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : "ID: " + item.getId() + " (" + item.getBreed() + ")");
            }
        });
        comboCows.setButtonCell(comboCows.getCellFactory().call(null));
    }

    @FXML
    private void handleSaveMilk() {
        Cow selectedCow = comboCows.getValue();
        String amountStr = txtAmount.getText();

        if (selectedCow == null || amountStr.isEmpty()) {
            showAlert("Input Error", "Please select a cow and enter the milk amount.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            LocalDate date = datePicker.getValue();

            // Logic: Add the milk record to the cow
            selectedCow.addMilkRecord(date, amount);

            // Critical: Refresh data file
            manager.saveToFile();

            showAlert("Success", "Recorded " + amount + "L for Cow #" + selectedCow.getId());
            txtAmount.clear();

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid number for milk amount.");
        }
    }

    private void showAlert(String title, String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }
}