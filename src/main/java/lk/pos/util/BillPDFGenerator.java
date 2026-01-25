package lk.pos.util;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lk.pos.model.SalesItem;

import java.io.FileOutputStream;
import java.util.List;

public class BillPDFGenerator {

    public static void generate(
            int saleId,
            List<SalesItem> items,
            double total,
            double cash,
            double balance
    ) throws Exception {

        Document document = new Document();

        String userHome = System.getProperty("user.home");

        PdfWriter.getInstance(
                document,
                new FileOutputStream(
                        userHome + "/Desktop/Bill_" + saleId + ".pdf"
                )
        );


        document.open();

        document.add(new Paragraph("==== RETAIL POS BILL ====\n\n"));

        for (SalesItem item : items) {
            document.add(new Paragraph(
                    item.getName() + " x " + item.getQty() + " = " + item.getTotal()
            ));
        }

        document.add(new Paragraph("\n------------------------"));
        document.add(new Paragraph("Total   : " + total));
        document.add(new Paragraph("Cash    : " + cash));
        document.add(new Paragraph("Balance : " + balance));

        document.close();
    }
}
