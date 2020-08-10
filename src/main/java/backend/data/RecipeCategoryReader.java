package backend.data;

import backend.dataclasses.recipecategories.RecipeCategory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Reader for recipeCategories.xml.
 */
public final class RecipeCategoryReader {

    /**
     * file path to recipeCategories.xml.
     */
    private static String xmlPath = "./src/main/resources/recipeCategories.xml";

    private RecipeCategoryReader() {
    }

    /**
     * Gets a list with the saved recipe categories in the xml file.
     *
     * @return list of RecipeCategory
     * @throws FileNotFoundException If file that has the categories saved is
     *                               non existent
     */
    public static ArrayList<RecipeCategory> readRecipeCategories()
            throws FileNotFoundException {

        Document doc = XMLHandler.getDocument(xmlPath);
        return readDocument(doc);
    }

    /**
     * Reads document for the recipecategories and returns a list of them.
     *
     * @param doc Document of file which will be parsed
     * @return List of RecipeCategory saved in the document
     */
    private static ArrayList<RecipeCategory> readDocument(Document doc) {

        ArrayList<RecipeCategory> categories =
                new ArrayList<>();

        NodeList nList = doc.getDocumentElement().getElementsByTagName("category");

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                categories.add(handleCategory((Element) node));
            }
        }

        return categories;
    }

    /**
     * Handles reading of a recipecategory and the creation of its object.
     *
     * @param node category node in the xml file
     * @return RecipCategory that was created from the saved data
     */
    private static RecipeCategory handleCategory(Element node) {


        Node idNode = node.getElementsByTagName("id").item(0);
        String id = idNode.getTextContent();

        Node nameNode = node.getElementsByTagName("name").item(0);
        String name = nameNode.getTextContent();

        return new RecipeCategory(id, name);
    }
}
