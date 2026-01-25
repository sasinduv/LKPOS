package lk.pos.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import lk.pos.dao.ReportDAO;
import lk.pos.util.ReportPDFGenerator;

import java.util.Map;

public class ReportController {

    @FXML private Label lblToday;
    @FXML private Label lblMonth;
    @FXML private Label lblQty;
    @FXML private BarChart<String, Number> barChart;
    @FXML private PieChart pieChart;

    @FXML
    public void initialize() {
        try{
            lblToday.setText("Rs. " + ReportDAO.getTodayeSales());
            lblMonth.setText("Rs. " + ReportDAO.getMonthlySales());
            lblQty.setText(String.valueOf(ReportDAO.getTotalSoldQty()));
            loadBarchart();
            loadPieChart();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadBarchart() {
        try {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Monthly Sales");

            Map<String, Double> data = ReportDAO.getMonthlySalesChart();
            data.forEach((month, total)->
                    series.getData().add(new XYChart.Data<>(month, total))
                    );
            barChart.getData().add(series);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPieChart() {
        try {
            Map<String, Integer> data = ReportDAO.getPaymentChart();
            data.forEach((method, count) ->
                    pieChart.getData().add(new PieChart.Data(method, count))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exportPDF() throws Exception {
        ReportPDFGenerator.generate();

    }

}
