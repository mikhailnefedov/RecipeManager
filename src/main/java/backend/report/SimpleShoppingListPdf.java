package backend.report;

import backend.dataclasses.groceries.GroceryCategory;
import backend.dataclasses.groceries.GroceryItem;
import backend.dataclasses.groceries.ShoppingList;
import backend.dataclasses.recipe.uses.Quantity;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Creates a simple shopping list pdf from a Shopping list object.
 */
public class SimpleShoppingListPdf {

    public final String destination = "src/main/resources/reports/SimpleShoppingListPDF.pdf";
    private PdfDocument pdfDocument;
    private Document doc;

    public void generate(ShoppingList list) throws IOException {
        File file = new File(destination);
        file.getParentFile().mkdir();
        createPdf(list);
    }

    /**
     * Creates the shopping list pdf and saves it.
     *
     * @param list shopping list object containing the grocery items and their
     *             quantities.
     * @throws IOException
     */
    private void createPdf(ShoppingList list) throws IOException {

        PdfWriter writer = new PdfWriter(destination);
        pdfDocument = new PdfDocument(writer);

        doc = new Document(pdfDocument);

        addTitle();

        for (GroceryCategory category : list.getShoppingList().keySet()) {
            addCategory(category);
            for (GroceryItem item : list.getShoppingList().get(category).keySet()) {
                createCheckbox(String.valueOf(item.getID()));
                addItem(item, list.getShoppingList().get(category).get(item));
            }
        }
        doc.close();
        pdfDocument.close();
        writer.close();
    }

    /**
     * Adds the title to the document.
     */
    private void addTitle() {
        Paragraph title = new Paragraph("Einkaufsliste")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(16);
        doc.add(title);
    }

    /**
     * Adds a new paragraph with the category name.
     *
     * @param category the category to be added
     */
    private void addCategory(GroceryCategory category) {
        Paragraph p = new Paragraph(category.getName())
                .setUnderline();
        doc.add(p);
    }

    /**
     * Creates a new Checkbox in the next paragraph.
     *
     * @param checkBoxName individual name for the checkbox
     */
    private void createCheckbox(String checkBoxName) {
        Rectangle freeBBox = doc.getRenderer().getCurrentArea().getBBox();
        float top = freeBBox.getTop();
        float fieldHeight = 15;
        float fieldWidth = 15;

        PdfFormField checkBox = PdfFormField.createCheckBox(pdfDocument,
                new Rectangle(freeBBox.getLeft(), top - fieldHeight - 5, fieldWidth, fieldHeight), checkBoxName, "Off");
        checkBox.getWidgets().get(0).setBorderStyle(PdfAnnotation.STYLE_BEVELED);
        checkBox.setBorderWidth(1).setBorderColor(ColorConstants.BLACK);

        PdfAcroForm form = PdfAcroForm.getAcroForm(doc.getPdfDocument(), true);
        form.addField(checkBox);
    }

    /**
     * Adds a new paragraph with the quantity and the item that needs to be
     * bought.
     *
     * @param item       item itself
     * @param quantities list of quantities that are needed of this item
     */
    private void addItem(GroceryItem item, ArrayList<Quantity> quantities) {
        String text = quantities.toString() + " " + item.getName();
        Paragraph p = new Paragraph(text);
        p.setFontSize(12);
        p.setFirstLineIndent(30);
        doc.add(p);
    }
}
