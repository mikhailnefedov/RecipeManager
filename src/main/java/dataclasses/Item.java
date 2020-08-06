package dataclasses;

public class Item implements Comparable{

    private String name;

    public Item(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int compareTo(Object other) {

        String otherName = other.toString();
        return name.compareTo(otherName);
    }
}
