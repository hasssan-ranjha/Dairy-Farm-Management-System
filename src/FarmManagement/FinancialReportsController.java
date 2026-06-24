package FarmManagement;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.transformation.FilteredList;
import java.time.LocalDate;

public class FinancialReportsController extends BaseController {

    @FXML private Label lblTotalInvestment, lblTotalRevenue, lblNetProfit;
    @FXML private TableView<Cow> tableFinancials;
    @FXML private TableColumn<Cow, String> colId;
    @FXML private TableColumn<Cow, Object> colStatus, colBuyDate, colSaleDate;
    @FXML private TableColumn<Cow, Double> colBuyPrice, colSalePrice, colProfit;

    @Override
    protected void onManagerSet() {
        calculateSummary();

        // 1. Existing column mappings
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colBuyPrice.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        colSalePrice.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        colProfit.setCellValueFactory(new PropertyValueFactory<>("profitLoss"));

        // 2. NEW mappings for dates
        // Note: These look for getEntryDate() and getCloseDate() in Cow.java
        colBuyDate.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
        colSaleDate.setCellValueFactory(new PropertyValueFactory<>("closeDate"));

        // 3. Show only non-active cows (Sold or Deceased)
        FilteredList<Cow> closedCows = new FilteredList<>(manager.getCows(),
                cow -> cow.getStatus() != AnimalStatus.ACTIVE);

        tableFinancials.setItems(closedCows);
    }

    private void calculateSummary() {
        double totalInvested = 0;
        double totalEarned = 0;
        double netPL = 0;

        for (Cow cow : manager.getCows()) {
            totalInvested += cow.getBuyingPrice();
            if (cow.getStatus() == AnimalStatus.SOLD) {
                totalEarned += cow.getSalePrice();
            }
            netPL += cow.getProfitLoss();
        }

        lblTotalInvestment.setText(String.format("PKR %.0f", totalInvested));
        lblTotalRevenue.setText(String.format("PKR %.0f", totalEarned));
        lblNetProfit.setText(String.format("PKR %.0f", netPL));

        if (netPL >= 0) lblNetProfit.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        else lblNetProfit.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
    }
}