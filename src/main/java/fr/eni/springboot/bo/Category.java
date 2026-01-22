package fr.eni.springboot.bo;

public class Category {
    private int idCategory;
    private String libelle;

    public Category() {
    }

    public Category(String libelle) {
        this.libelle = libelle;
    }

    public Category(int idCategory, String libelle) {
        this.idCategory = idCategory;
        this.libelle = libelle;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
