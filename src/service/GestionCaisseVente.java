package service;

import java.util.Date;
import java.util.ArrayList;
import model.Caissier;
import model.Client;
import model.Facture;
import model.Paiement;
import model.Produit;
import model.Vente;

public class GestionCaisseVente {

    // _____==== EXERCICE 7 : MODULE CAISSE ET VENTE ====_____

    private ArrayList<Vente> listeVentes = new ArrayList<>();
    private int numeroVenteAuto = 1;
    private int numeroPaiementAuto = 1;
    private int numeroFactureAuto = 1;

    // Démarrer une nouvelle vente
    public Vente demarrerVente(Caissier caissier, Client client) {
        if (!caissier.isCaisseOuverte()) {
            caissier.ouvrirCaisse();
        }
        Vente vente = new Vente(numeroVenteAuto++, new Date(), client, caissier);
        System.out.println("Vente n°" + vente.getNumeroVente() + " démarrée.");
        return vente;
    }

    // Ajouter un produit au panier
    public void ajouterProduitVente(Vente vente, Produit produit, int quantite) {
        for (int i = 0; i < quantite; i++) {
            vente.ajouterProduit(produit);
        }
    }

    // Retirer un produit du panier
    public void retirerProduitVente(Vente vente, Produit produit) {
        while (vente.getPanier().contains(produit)) {
            vente.supprimerProduit(produit);
        }
    }

    // Finaliser la vente (valider + encaisser + générer facture)
    public Facture finaliserVente(Vente vente, String modePaiement, double montantRecu) {
        // 1. Valider la vente (décrémente le stock)
        vente.validerVente();

        // 2. Créer le paiement
        Paiement paiement = new Paiement(
                numeroPaiementAuto++, montantRecu, modePaiement, new Date(), vente);

        // 3. Valider le paiement
        if (!paiement.validerPaiement()) {
            System.out.println("Paiement insuffisant. Vente non complétée.");
            return null;
        }

        // 4. Générer la facture
        Facture facture = Facture.genererFacture(numeroFactureAuto++, vente, paiement);

        // 5. Enregistrer la vente
        listeVentes.add(vente);

        // 6. Afficher la facture
        facture.imprimerFacture();

        return facture;
    }

    // Calculer le chiffre d'affaires total
    public double calculerChiffreAffaires() {
        double ca = 0.0;
        for (Vente v : listeVentes) ca += v.calculerTotal();
        return ca;
    }

    // Getters
    public ArrayList<Vente> getListeVentes() { return listeVentes; }
}
