package FarmManagement;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.time.LocalDate;

public class AddCowController extends BaseController {

    @FXML private TextField txtId, txtSource, txtPrice, txtSemenName;
    @FXML private ComboBox<Breed> comboBreed;
    @FXML private ComboBox<ReproductiveStatus> comboRepro;
    @FXML private ComboBox<SemenType> comboSemenType;
    @FXML private DatePicker dateEntry, dateInsem;
    @FXML private VBox breedingSection;

    @Override
    protected void onManagerSet() {
        comboBreed.getItems().setAll(Breed.values());
        comboRepro.getItems().setAll(ReproductiveStatus.values());
        comboSemenType.getItems().setAll(SemenType.values());
        dateEntry.setValue(LocalDate.now());

        breedingSection.managedProperty().bind(breedingSection.visibleProperty());
        breedingSection.setVisible(false);

        comboRepro.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            boolean isFresh = (newVal == ReproductiveStatus.FRESH);
            breedingSection.setVisible(!isFresh);
        });
    }

    @FXML
    private void handleSave() {
        try {
            // --- VALIDATION SECTION ---
            String id = txtId.getText().trim();
            LocalDate entryDate = dateEntry.getValue();

            if (id.isEmpty() || comboBreed.getValue() == null || comboRepro.getValue() == null || entryDate == null) {
                showError("Validation Error", "ID, Breed, Status, and Entry Date are mandatory!");
                return;
            }

            // Check for Future Entry Date
            if (entryDate.isAfter(LocalDate.now())) {
                showError("Date Error", "The Entry Date cannot be in the future!");
                return;
            }

            // Check for Negative Price
            double price = Double.parseDouble(txtPrice.getText());
            if (price < 0) {
                showError("Price Error", "Purchase price cannot be negative!");
                return;
            }

            if (manager.idExists(id)) {
                showError("Duplicate ID", "A cow with ID " + id + " already exists!");
                return;
            }

            // Capture Breeding Attributes
            LocalDate insemDate = null;
            SemenType sType = null;
            String sName = "NA";

            if (comboRepro.getValue() != ReproductiveStatus.FRESH) {
                insemDate = dateInsem.getValue();
                sType = comboSemenType.getValue();
                sName = txtSemenName.getText().trim();

                if (insemDate == null || sType == null || sName.isEmpty()) {
                    showError("Breeding Info Missing", "Please provide Insemination Date, Semen Type, and Name.");
                    return;
                }

                // Note: We are NOT checking if insemDate is before entryDate
                // because a cow can be bought already pregnant.
            }

            // --- SAVE SECTION ---
            Cow newCow = new Cow(id, comboBreed.getValue(), txtSource.getText(), price,
                    entryDate, comboRepro.getValue(), insemDate, sType, sName);

            manager.addCow(newCow);
            manager.saveToFile(); // Ensure it saves to disk
            showSuccess("Success", "Cow #" + id + " has been added to the system.");
            clearFields();

        } catch (NumberFormatException e) {
            showError("Input Error", "Please enter a valid numeric price.");
        }
    }

    private void clearFields() {
        txtId.clear();
        txtSource.clear();
        txtPrice.clear();
        txtSemenName.clear();
        comboBreed.setValue(null);
        comboRepro.setValue(null);
        comboSemenType.setValue(null);
        dateInsem.setValue(null);
        dateEntry.setValue(LocalDate.now());
    }

    private void showError(String title, String content) {
        new Alert(Alert.AlertType.ERROR, content).showAndWait();
    }

    private void showSuccess(String title, String content) {
        new Alert(Alert.AlertType.INFORMATION, content).showAndWait();
    }
}