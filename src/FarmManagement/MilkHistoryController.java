package FarmManagement;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import java.time.LocalDate;

public class MilkHistoryController extends BaseController {
    @FXML private Label lblCowId, lblAverage, lblTotal;
    @FXML private TableView<MilkRecord> tableMilk;
    @FXML private TableColumn<MilkRecord, LocalDate> colDate;
    @FXML private TableColumn<MilkRecord, Double> colLiters;

    public void setCow(Cow cow) {
        // Update Labels
        lblCowId.setText("Milk History: Cow #" + cow.getId());
        lblAverage.setText(String.format("Daily Average: %.2f L", cow.getDailyAverageMilk()));
        lblTotal.setText(String.format("Total Produced: %.2f L", cow.getTotalMilk()));

        // Set up Column mapping
        // IMPORTANT: These strings MUST match the getter names in MilkRecord.java
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));   // calls getDate()
        colLiters.setCellValueFactory(new PropertyValueFactory<>("liters")); // calls getLiters()

        // Populate Table
        if (cow.getMilkRecords() != null) {
            tableMilk.setItems(FXCollections.observableArrayList(cow.getMilkRecords()));
        }
    }

    @FXML
    private void handleBack() {
        // This takes you back to the main inventory table
        DashboardController.getInstance().showViewActive();
    }
}