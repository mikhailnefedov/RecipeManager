package data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileNotFoundException;

/**
 * Reader for recipes.xml.
 */
public class RecipeReader {

    /** file path to recipes.xml. */
    private static String recXMLPath = "./src/main/resources/recipes.xml";

    /**
     * Reads the categories.xml file and loads the data into.
     * ListOfPurchasableItems
     * @throws FileNotFoundException If categories.xml is missing
     */
    public static void readRecipes() throws FileNotFoundException {

        Document doc = XMLHandler.getDocument(recXMLPath);
        readDocument(doc);
    }

    /**
     * Reads the document for the recipes nodes.
     * @param doc Document of file which will be parsed
     */
    public static void readDocument(Document doc) {

        NodeList nList = doc.getDocumentElement().getElementsByTagName("recipe");

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                handleRecipe((Element) node);
            }
        }
    }

    /**
     * Handles reading of one category and its items. Adds data into
     * ListOfPurchasableItems.
     * @param node category node
     */
    static void handleRecipe(Element node) {

        Node itemNode = node.getElementsByTagName("id").item(0);
        System.out.println(itemNode.getTextContent());

        Node titleNode = node.getElementsByTagName("title").item(0);
        System.out.println(titleNode.getTextContent());

        Node categoryNode = node.getElementsByTagName("category").item(0);
        System.out.println(categoryNode.getTextContent());

        Node linkNode = node.getElementsByTagName("link").item(0);
        System.out.println(linkNode.getTextContent());

        //TODO: portionsize

        Node timeNode = node.getElementsByTagName("time").item(0);
        System.out.println(timeNode.getTextContent());

        Node vegetarianNode = node.getElementsByTagName("vegetarian").item(0);
        System.out.println(vegetarianNode.getTextContent());

        //TODO: ingridients

        //TODO: preparation

        Node commentNode = node.getElementsByTagName("comment").item(0);
        System.out.println(commentNode.getTextContent());

    }


}
