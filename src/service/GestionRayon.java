package service;

import java.util.ArrayList;
import model.ChefRayon;
import model.Produit;
import model.Rayon;

public class GestionRayon {

    // _____==== EXERCICE 4 : GESTION DES RAYONS ====_____

    private ArrayList<Rayon> listeRayons = new ArrayList<>();
    private int codeRayonAuto = 1;

    // Ajouter un rayon
    public Rayon ajouterRayon(String nomRayon, ChefRayon responsable) {
        Rayon rayon = new Rayon(codeRayonAuto++, nomRayon, responsable);
        listeRayons.add(rayon);
        System.out.println("Rayon ajouté : " + nomRayon + " (Responsable : "
                + responsable.getPrenom() + " " + responsable.getNom() + ")");
        return rayon;
    }

    // Supprimer un rayon
    public boolean supprimerRayon(int codeRayon) {
        Rayon r = rechercherRayon(codeRayon);
        if (r != null) {
            listeRayons.remove(r);
            System.out.println("Rayon supprimé : " + r.getNomRayon());
            return true;
        }
        System.out.println("Rayon introuvable (code: " + codeRayon + ")");
        return false;
    }

    // Rechercher un rayon par code
    public Rayon rechercherRayon(int codeRayon) {
        for (Rayon r : listeRayons) {
            if (r.getCodeRayon() == codeRayon) return r;
        }
        return null;
    }

    // Ajouter un produit dans un rayon
    public void ajouterProduitDansRayon(int codeRayon, Produit produit) {
        Rayon r = rechercherRayon(codeRayon);
        if (r != null) {
            r.ajouterProduit(produit);
            System.out.println("Produit [" + produit.getDesignation() + "] ajouté au rayon : " + r.getNomRayon());
        } else {
            System.out.println("Rayon introuvable (code: " + codeRayon + ")");
        }
    }

    // Afficher tous les rayons
    public void afficherTousLesRayons() {
        System.out.println("=== RAYONS DU SUPERMARCHÉ (" + listeRayons.size() + ") ===");
        for (Rayon r : listeRayons) {
            System.out.println("  " + r.toString()
                    + " | Valeur stock : " + String.format("%.2f", r.calculValeurStock()) + " €");
        }
    }

    // Getter
    public ArrayList<Rayon> getListeRayons() { return listeRayons; }
}
