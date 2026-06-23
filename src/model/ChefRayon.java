package model;

import java.util.ArrayList;

public class ChefRayon extends Employe {

    //_____==== EXERCICE 5 : GESTION DU PERSONNEL DU SUPERMARCHÉ ====_____

    // Attributs
    private String secteurRayon; // ex: "Boulangerie", "Électronique", "Frais"

    // Constructeur d'initialisation
    public ChefRayon(int pMatricule, String pNom, String pPrenom, double pSalaire, String pSecteurRayon) {
        super(pMatricule, pNom, pPrenom, pSalaire);
        setRole("ChefRayon");
        secteurRayon = pSecteurRayon;
    }

    // Organiser le rayon : vérifie les produits en rupture de stock
    public void organiserRayon(Rayon rayon) {
        System.out.println("=== Organisation du rayon : " + rayon.getNomRayon() + " par " + getPrenom() + " " + getNom() + " ===");
        for (Produit p : rayon.getListeProduits()) {
            if (p.getQuantiteStock() <= 5) {
                System.out.println("  [ALERTE] Stock faible : " + p.getDesignation() + " (" + p.getQuantiteStock() + " unités)");
            }
        }
    }

    // Vérifier les stocks sous un seuil donné
    public ArrayList<Produit> verifierStocksFaibles(Rayon rayon, int seuil) {
        ArrayList<Produit> produitsAReapprovisionner = new ArrayList<>();
        for (Produit p : rayon.getListeProduits()) {
            if (p.getQuantiteStock() <= seuil) {
                produitsAReapprovisionner.add(p);
            }
        }
        return produitsAReapprovisionner;
    }

    // Getters
    public String getSecteurRayon() { return secteurRayon; }

    // Setters
    public void setSecteurRayon(String secteurRayon) { this.secteurRayon = secteurRayon; }
}
