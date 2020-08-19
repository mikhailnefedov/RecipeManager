package backend.data;

import backend.dataclasses.recipe.Quantity;
import backend.dataclasses.recipe.Recipe;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Reader for recipes.xml.
 */
public final class RecipeReader {

    /**
     * file path to recipes.xml.
     */
    private static String recXMLPath = "./src/main/resources/recipes.xml";

    private RecipeReader() {
    }

    /**
     * Gets the saved Recipes from recipes.xml.
     *
     * @return ArrayList with Recipes that are saved in recipes.xml
     * @throws FileNotFoundException If recipes.xml is missing
     */
    public static ArrayList<Recipe.RecipeBuilder> readRecipes()
            throws FileNotFoundException {

        Document doc = XMLHandler.getDocument(recXMLPath);
        return readDocument(doc);
    }

    /**
     * Reads the document for the recipes nodes and returns the recipes saved in
     * it.
     *
     * @param doc Document of file which will be parsed
     * @return Recipes saved in doc
     */
    private static ArrayList<Recipe.RecipeBuilder> readDocument(Document doc) {

        ArrayList<Recipe.RecipeBuilder> recipes =
                new ArrayList<>();

        NodeList nList = doc.getDocumentElement().getElementsByTagName("recipe");

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                recipes.add(handleRecipe((Element) node));
            }
        }

        return recipes;
    }

    /**
     * Handles reading of one Recipes and its items.
     * @param node category node
     * @return RecipeBuilder created from the saved data of the xml element
     */
    private static Recipe.RecipeBuilder handleRecipe(Element node) {

        Recipe.RecipeBuilder builder = new Recipe.RecipeBuilder();

        Node itemNode = node.getElementsByTagName("id").item(0);
        builder.id(itemNode.getTextContent());

        Node titleNode = node.getElementsByTagName("title").item(0);
        builder.title(titleNode.getTextContent());

        Node categoryNode = node.getElementsByTagName("category").item(0);
        builder.category(categoryNode.getTextContent());

        Node linkNode = node.getElementsByTagName("link").item(0);
        builder.recipeLink(linkNode.getTextContent());


        Node timeNode = node.getElementsByTagName("time").item(0);
        builder.time(timeNode.getTextContent());

        Node vegetarianNode = node.getElementsByTagName("vegetarian").item(0);
        builder.vegetarian(vegetarianNode.getTextContent());

        handleIngredientList(node, builder);

        Node commentNode = node.getElementsByTagName("comment").item(0);
        builder.comment(commentNode.getTextContent());

        return builder;
    }

    /**
     * Inserts every ingredient into the Recipebuilder.
     *
     * @param node xml node of the recipe
     * @param builder Recipebuilder to which the ingredients will be added
     */
    private static void handleIngredientList(Element node,
                                             Recipe.RecipeBuilder builder) {
        builder.ingredientsInitializer();

        Node ingredients = node.getElementsByTagName("ingredients").item(0);
        Element ingredientsElement = (Element) ingredients;
        NodeList categories = ingredientsElement.getElementsByTagName("categoryOfIngridient");

        for (int i = 0; i < categories.getLength(); i++) {
            Node category = categories.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                handleItems((Element) category, builder);
            }
        }
    }

    /**
     * Handles insertion of grocery items of a category.
     *
     * @param categoryNode the category node
     * @param builder Recipebuilder to which the ingredients will be added
     */
    private static void handleItems(Element categoryNode,
                                    Recipe.RecipeBuilder builder) {
        String categoryName = categoryNode.getAttribute("categoryName");

        NodeList ingredients = categoryNode.getElementsByTagName("ingredient");

        for (int i = 0; i < ingredients.getLength(); i++) {
            Node ingredient = ingredients.item(i);
            if (ingredient.getNodeType() == Node.ELEMENT_NODE) {
                Element ingredientElement = (Element) ingredient;

                String ingredientName = ingredientElement
                        .getAttribute("name");
                String amount = ingredientElement.getAttribute("amount");
                String unit = ingredientElement.getAttribute("unit");


                builder.addGroceryItem(categoryName, ingredientName,
                        new Quantity(amount, unit));
            }
        }
    }


}
