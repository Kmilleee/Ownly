package fr.eni.springboot.bo;

public class Category {
    private long category_id;
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(long category_id, String name) {
        this.category_id = category_id;
        this.name = name;
    }

    public long getIdCategory() {
        return category_id;
    }

    public void setIdCategory(long category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
