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

public class CategoryReader {

    public static void readCategories() throws FileNotFoundException {

        Document doc = getDocument();
        readDocument(doc);
    }

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

    public static void readDocument(Document doc) {

        NodeList nList = doc.getDocumentElement().getElementsByTagName("category");

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                handleCategory((Element) node);
            }
        }
    }

    static void handleCategory(Element node) {

        Element element = node;
        String categoryName = element.getAttribute("name");
        NodeList nList = element.getElementsByTagName("item");
        ArrayList<String> items = handleItems(nList);
        ListOfPurchasableItems.getInstance().addCategoryWithItems(categoryName, items);
    }

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
