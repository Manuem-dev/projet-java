package model;

public class ProduitArtisanal extends Produit {

    // _____==== EXERCICE 1 : GESTION DES PRODUITS ====_____
    // Produits artisanaux : Boulangerie, Poissonnerie, Boucherie

    // Attributs spécifiques
    private String categorie;    // "Boulangerie", "Poissonnerie", "Boucherie"
    private String artisan;      // Nom du fabricant/artisan
    private String origine;      // Région ou pays d'origine

    // Constructeur d'initialisation
    public ProduitArtisanal(int pReference, int pQuantiteStock, double pPrixAchat,
                             double pPrixVente, String pDesignation,
                             String pCategorie, String pArtisan, String pOrigine) {
        super(pReference, pQuantiteStock, pPrixAchat, pPrixVente, pDesignation);
        categorie = pCategorie;
        artisan   = pArtisan;
        origine   = pOrigine;
    }

    // toString enrichi
    @Override
    public String toString() {
        return super.toString() + " | Catégorie: " + categorie
                + " | Artisan: " + artisan + " | Origine: " + origine;
    }

    // Getters
    public String getCategorie() { return categorie; }
    public String getArtisan()   { return artisan; }
    public String getOrigine()   { return origine; }

    // Setters
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public void setArtisan(String artisan)     { this.artisan = artisan; }
    public void setOrigine(String origine)     { this.origine = origine; }
}
