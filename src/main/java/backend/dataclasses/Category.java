package backend.dataclasses;

public class Category implements Comparable<Category>{

    private String name;

    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int compareTo(Category other) {

        String otherName = other.toString();
        return name.compareTo(otherName);
    }
}
