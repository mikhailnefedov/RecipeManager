module RecipeManager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    opens frontend to javafx.fxml;
    exports frontend;

    opens backend.dataclasses.recipe to javafx.base;
    exports backend.dataclasses.recipe;
}