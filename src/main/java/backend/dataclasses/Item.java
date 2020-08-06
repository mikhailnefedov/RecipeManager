package backend.dataclasses;

public class Item implements Comparable<Item>{

    private String name;

    public Item(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int compareTo(Item other) {

        String otherName = other.toString();
        return name.compareTo(otherName);
    }
}
