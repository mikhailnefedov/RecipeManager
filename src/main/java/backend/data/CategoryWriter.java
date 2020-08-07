package backend.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileNotFoundException;

/**
 * Writer for categories.xml.
 */
public final class CategoryWriter {

    private CategoryWriter() { }

    /**
     * Path of the categories xml.
     */
    private static String catXMLPath = "./src/main/resources/categories.xml";

    /**
     * Writes a new category into xml.
     *
     * @param categoryName name of the new category
     * @throws FileNotFoundException If categories.xml not found
     */
    public static void writeCategory(String categoryName)
            throws FileNotFoundException {

        Document doc = XMLHandler.getDocument(catXMLPath);
        Element docElement = doc.getDocumentElement();
        XMLHandler.removeWhiteSpaceNodes(doc);

        Element nodeElement = doc.createElement("category");
        nodeElement.setAttribute("name", categoryName);
        docElement.appendChild(nodeElement);
        XMLHandler.writeToXML(doc, docElement, catXMLPath);
    }

    /**
     * Writes new item into the categories.xml.
     *
     * @param categoryName name of category to which the item will be inserted
     * @param itemName     name of the new item
     * @throws FileNotFoundException If categories.xml not found
     */
    public static void writeItem(String categoryName, String itemName)
            throws FileNotFoundException {

        Document doc = XMLHandler.getDocument(catXMLPath);
        Element docElement = doc.getDocumentElement();
        XMLHandler.removeWhiteSpaceNodes(doc);

        NodeList nList = docElement.getElementsByTagName("category");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getAttribute("name").equals(categoryName)) {
                    handleInsertion(doc, element, itemName);
                }
            }
        }
        XMLHandler.writeToXML(doc, docElement, catXMLPath);
    }

    /**
     * Appends new item node to category node.
     *
     * @param doc      document in which the appending happens
     * @param node     category node to which the item will be appended
     * @param itemName name of the new item
     */
    private static void handleInsertion(Document doc, Element node,
                                        String itemName) {

        Element element = doc.createElement("item");
        element.setTextContent(itemName);
        node.appendChild(element);
    }

}
