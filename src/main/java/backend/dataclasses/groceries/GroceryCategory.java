package backend.dataclasses.groceries;

public class GroceryCategory implements Comparable<GroceryCategory>{

    private String name;

    public GroceryCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int compareTo(GroceryCategory other) {

        String otherName = other.toString();
        return name.compareTo(otherName);
    }
}
