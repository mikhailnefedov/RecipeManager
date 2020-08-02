package datahandler;

import dataclasses.ListOfPurchasableItems;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Reader for categories.xml.
 */
public class CategoryReader {

    /**
     * Reads the categories.xml file and loads the data into.
     * ListOfPurchasableItems
     * @throws FileNotFoundException If categories.xml is missing
     */
    public static void readCategories() throws FileNotFoundException {

        Document doc = getDocument();
        readDocument(doc);
    }

    /**
     * Gets xml document.
     * @return Document of categories.xml
     * @throws FileNotFoundException If categories.xml is missing
     */
    public static Document getDocument() throws FileNotFoundException {

        try {
            String path = "./src/main/resources/categories.xml";
            File xmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileNotFoundException("categories.xml is missing");
        }
    }

    /**
     * Reads the document for the category nodes.
     * @param doc Document of file which will be parsed
     */
    public static void readDocument(Document doc) {

        NodeList nList = doc.getDocumentElement().getElementsByTagName("category");

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                handleCategory((Element) node);
            }
        }
    }

    /**
     * Handles reading of one category and its items. Adds data into
     * ListOfPurchasableItems.
     * @param node category node
     */
    static void handleCategory(Element node) {

        String categoryName = node.getAttribute("name");
        NodeList nList = node.getElementsByTagName("item");
        ArrayList<String> items = handleItems(nList);
        ListOfPurchasableItems.getInstance().addCategoryWithItems(categoryName,
                items);
    }

    /**
     * Gets the list of items as an ArrayList<String>.
     * @param list list of nodes (document) of items
     * @return list of items
     */
    static ArrayList<String> handleItems(NodeList list) {

        ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String item = node.getTextContent();
                items.add(item);
            }
        }
        return items;
    }
}
