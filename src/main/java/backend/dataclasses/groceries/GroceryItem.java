package backend.dataclasses.groceries;

public class GroceryItem implements Comparable<GroceryItem>{

    private String name;

    public GroceryItem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int compareTo(GroceryItem other) {

        String otherName = other.toString();
        return name.compareTo(otherName);
    }
}
