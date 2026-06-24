package FarmManagement;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.transformation.FilteredList;

public class ViewActiveController extends BaseController {

    @FXML private TableView<Cow> tableCows;
    @FXML private TableColumn<Cow, String> colId, colBreed, colSource;
    @FXML private TableColumn<Cow, Double> colPrice; // Added for financial visibility
    @FXML private TableColumn<Cow, Object> colEntry, colRepro, colInsem, colCalving;
    @FXML private TableColumn<Cow, Double> colMilk;

    @Override
    protected void onManagerSet() {
        // 1. Mapping Table Columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBreed.setCellValueFactory(new PropertyValueFactory<>("breed"));
        colSource.setCellValueFactory(new PropertyValueFactory<>("boughtFrom"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        colEntry.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
        colRepro.setCellValueFactory(new PropertyValueFactory<>("reproStatus"));
        colInsem.setCellValueFactory(new PropertyValueFactory<>("inseminationDate"));
        colCalving.setCellValueFactory(new PropertyValueFactory<>("expectedCalvingDate"));
        colMilk.setCellValueFactory(new PropertyValueFactory<>("totalMilk"));

        // 2. Set Row Factory for Double-Click
        tableCows.setRowFactory(tv -> {
            TableRow<Cow> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Cow clickedCow = row.getItem();
                    DashboardController.getInstance().loadMilkHistory(clickedCow);
                }
            });
            return row;
        });

        // 3. Set the Filtered Items (Only Active Cows)
        FilteredList<Cow> activeCows = new FilteredList<>(manager.getCows(),
                cow -> cow.getStatus() == AnimalStatus.ACTIVE);
        tableCows.setItems(activeCows);
    }


}