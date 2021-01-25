package frontend;

import frontend.helper.WindowLoader;

import java.io.IOException;

public class WindowTasksController {

    /**
     * Handler for a button click on categories. Opens new window for the
     * recipe categories.
     *
     * @throws IOException failed or interrupted I/O operations
     */
    public void handleCategoriesClick() throws IOException {
        WindowLoader.openNewWindow("RecipeCategoriesWindow",
                "Rezeptkategorien bearbeiten");
    }

    /**
     * Handler for a button click on grocery items. Opens new window for the
     * grocery items.
     *
     * @throws IOException failed or interrupted I/O operations
     */
    public void handleGroceryItemsClick() throws IOException {
        WindowLoader.openNewWindow("GroceryItemsWindow",
                "Zutaten bearbeiten");
    }
}
