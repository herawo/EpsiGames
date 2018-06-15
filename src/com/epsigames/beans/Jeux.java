package com.epsigames.beans;

public class Jeux {
    private String titre;
    private String producteur;
    private String genre;

    public String getGenre() {
        return genre;
    }

    public void setGenre( String genre ) {
        this.genre = genre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre( String titre ) {
        this.titre = titre;
    }

    public String getSousTitre() {
        return producteur;
    }

    public void getProducteur( String producteur ) {
        this.producteur = producteur;
    }
}
