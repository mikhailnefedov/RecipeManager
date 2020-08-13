package backend.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileNotFoundException;

/**
 * Writer for recipeCategories.xml.
 */
public final class RecipeCategoryWriter {

    /**
     * file path to recipeCategories.xml.
     */
    private static String xmlPath = "./src/main/resources/recipeCategories.xml";

    private RecipeCategoryWriter() {
    }

    /**
     * Change saved information about a category in xml.
     *
     * @param oldID saved id of category in xml
     * @param oldCategoryName saved name of category in xml
     * @param newID new id of category
     * @param newCategoryName new name of category
     * @throws FileNotFoundException If xml that saves the data is not found
     */
    public static void changeCategory(String oldID, String oldCategoryName,
                                      String newID, String newCategoryName)
            throws FileNotFoundException {
        Document doc = XMLHandler.getDocument(xmlPath);
        Element docElement = doc.getDocumentElement();
        XMLHandler.removeWhiteSpaceNodes(doc);

        Node category = getCorrectNode(oldID, oldCategoryName, docElement);
        Node idNode = getIdNode((Element) category);
        Node nameNode = getNameNode((Element) category);

        idNode.setTextContent(newID);
        nameNode.setTextContent(newCategoryName);

        XMLHandler.writeToXML(doc, docElement, xmlPath);
    }

    /**
     * Writes new category into xml save file.
     *
     * @param id           id of new category
     * @param categoryName name of new category
     * @throws FileNotFoundException If xml that saves the data is not found
     */
    public static void writeNewCategory(String id, String categoryName)
            throws FileNotFoundException {

        Document doc = XMLHandler.getDocument(xmlPath);
        Element docElement = doc.getDocumentElement();
        XMLHandler.removeWhiteSpaceNodes(doc);

        Element nodeElement = doc.createElement("category");
        Element idElement = doc.createElement("id");
        idElement.setTextContent(id);
        Element nameElement = doc.createElement("name");
        nameElement.setTextContent(categoryName);

        nodeElement.appendChild(idElement);
        nodeElement.appendChild(nameElement);
        docElement.appendChild(nodeElement);
        XMLHandler.writeToXML(doc, docElement, xmlPath);
    }

    /**
     * Removes category from xml file.
     *
     * @param id           id of category
     * @param categoryName name of category
     * @throws FileNotFoundException If xml that saves the data is not found
     */
    public static void removeCategory(String id, String categoryName)
            throws FileNotFoundException {
        Document doc = XMLHandler.getDocument(xmlPath);
        Element docElement = doc.getDocumentElement();
        XMLHandler.removeWhiteSpaceNodes(doc);

        docElement.removeChild(getCorrectNode(id, categoryName, docElement));
        XMLHandler.writeToXML(doc, docElement, xmlPath);
    }

    /**
     * Gets the node corresponding to the category id and category name.
     *
     * @param id id of category
     * @param categoryName name of category
     * @param docElement DocumentElement of document from xml
     * @return corresponding Node of document of the category
     */
    private static Node getCorrectNode(String id, String categoryName, Element docElement) {
        NodeList nList = docElement.getElementsByTagName("category");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (checkIfCorrectNode(id, categoryName, element)) {
                    return node;
                }
            }
        }
        return null;
    }

    /**
     * Checks if the node is the correct node that saves the data of the category.
     *
     * @param id           id of the category
     * @param categoryName name of the category
     * @param element      Node that shall be checked
     * @return false, if it is another node
     */
    private static boolean checkIfCorrectNode(String id, String categoryName,
                                              Element element) {
        Node idNode = getIdNode(element);
        Node nameNode = getNameNode(element);

        return idNode.getTextContent().equals(id) &&
                nameNode.getTextContent().equals(categoryName);
    }

    /**
     * Get the id node of a category node.
     *
     * @param element category node
     * @return id node
     */
    private static Node getIdNode(Element element) {
        NodeList idList = element.getElementsByTagName("id");
        return idList.item(0);
    }

    /**
     * Get the name node of a category node.
     *
     * @param element category node
     * @return name node
     */
    private static Node getNameNode(Element element) {
        NodeList nameList = element.getElementsByTagName("name");
        return nameList.item(0);
    }
}
