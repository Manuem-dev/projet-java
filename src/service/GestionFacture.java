package service;

import java.util.ArrayList;
import model.Facture;

public class GestionFacture {

    // _____==== EXERCICE 9 : GESTION DES FACTURES ====_____

    private ArrayList<Facture> listeFactures = new ArrayList<>();

    // Enregistrer une facture
    public void enregistrerFacture(Facture facture) {
        listeFactures.add(facture);
    }

    // Rechercher une facture par numéro
    public Facture rechercherFacture(int numeroFacture) {
        for (Facture f : listeFactures) {
            if (f.getNumeroFacture() == numeroFacture) return f;
        }
        return null;
    }

    // Afficher toutes les factures
    public void afficherToutesLesFactures() {
        System.out.println("=== LISTE DES FACTURES (" + listeFactures.size() + ") ===");
        for (Facture f : listeFactures) {
            System.out.println("  " + f.toString());
        }
    }

    // Imprimer une facture par numéro
    public void imprimerFacture(int numeroFacture) {
        Facture f = rechercherFacture(numeroFacture);
        if (f != null) {
            f.imprimerFacture();
        } else {
            System.out.println("Facture n°" + numeroFacture + " introuvable.");
        }
    }

    // Getter
    public ArrayList<Facture> getListeFactures() { return listeFactures; }
}
