package backend.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Reader for categories.xml.
 */
public final class CategoryReader {

    private CategoryReader() { }

    /**
     * file path to categories.xml.
     */
    private static String catXMLPath = "./src/main/resources/categories.xml";

    /**
     * Reads the categories.xml file and gets the categories with their items.
     *
     * @return HashMap with categories and their items (Strings)
     * @throws FileNotFoundException If categories.xml is missing
     */
    public static HashMap<String, ArrayList<String>> readCategories()
            throws FileNotFoundException {

        Document doc = XMLHandler.getDocument(catXMLPath);
        return readDocument(doc);
    }

    /**
     * Reads the document for the category nodes and its items.
     *
     * @param doc Document of file which will be parsed
     * @return HashMap of the categories with their items
     */
    private static HashMap<String, ArrayList<String>> readDocument(Document doc) {

        NodeList nList = doc.getDocumentElement().getElementsByTagName("category");
        HashMap<String, ArrayList<String>> categoriesAndItems = new
                HashMap<String, ArrayList<String>>();

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String categoryName = element.getAttribute("name");
                categoriesAndItems.put(categoryName, handleCategory(element));
            }
        }

        return categoriesAndItems;
    }

    /**
     * Gets the items of one node (category).
     *
     * @param node category node
     * @return list of the items
     */
    private static ArrayList<String> handleCategory(Element node) {

        NodeList nList = node.getElementsByTagName("item");
        return handleItems(nList);
    }

    /**
     * Gets the list of items as an ArrayList<String>.
     *
     * @param list list of nodes (document) of items
     * @return list of items
     */
    private static ArrayList<String> handleItems(NodeList list) {

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
