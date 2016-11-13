package me.tigerhe.shoppingpal;

public class Item {
    String name;
    boolean isChecked;

    Item(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    Item(String name) {
        this.name = name;
        this.isChecked = false;
    }
}
