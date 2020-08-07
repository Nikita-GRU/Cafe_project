package by.gruca.cafe.model;


public enum Category {
    ALL(1, "all"),
    PIZZA(2, "pizza"),
    DRINK(3, "drink"),
    GARNISH(4, "garnish"),
    SALAD(5, "salad"),
    SOUP(6, "soup");

    int id;
    String category;

    Category(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }
}
