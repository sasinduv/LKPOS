package lk.pos.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lk.pos.dao.ReportDAO;

public class ReportController {

    @FXML private Label lblToday;
    @FXML private Label lblMonth;
    @FXML private Label lblQty;

    @FXML
    public void initialize() {
        try{
            lblToday.setText("Rs. " + ReportDAO.getTodayeSales());
            lblMonth.setText("Rs. " + ReportDAO.getMonthlySales());
            lblQty.setText(String.valueOf(ReportDAO.getTotalSoldQty()));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
