package lk.pos.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import lk.pos.dao.ReportDAO;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

public class ReportPDFGenerator {

    public static void generate() throws Exception {

        Document document = new Document();
       String userHome = System.getProperty("user.home");

            File file = new File(userHome + "/Desktop/SalesReport.pdf");

            PdfWriter.getInstance(
                    document,
                new FileOutputStream(file)
            );
        document.open();

        document.add(new Paragraph("Retail POS - Sales Report\n\n"));

        document.add(new Paragraph("Monthly Sales:\n"));

        Map<String, Double> monthly = ReportDAO.getMonthlySalesChart();
        for (String month : monthly.keySet()) {
            document.add(new Paragraph(
                    month + " : Rs. " + monthly.get(month)
            ));
        }

        document.add(new Paragraph("\nPayment Method Summary:\n"));

        Map<String, Integer> payment = ReportDAO.getPaymentChart();
        for (String method : payment.keySet()) {
            document.add(new Paragraph(
                    method + " : " + payment.get(method) + " sales"
            ));
        }

        document.close();
    }
}
