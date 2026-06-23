package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Caissier extends Employe {

    //_____==== EXERCICE 5 : GESTION DU PERSONNEL DU SUPERMARCHÉ ====_____

    // Attributs
    private int numeroCaisse;
    private boolean caisseOuverte;
    private Vente venteEnCours;

    // Constructeur d'initialisation
    public Caissier(int pMatricule, String pNom, String pPrenom, double pSalaire, int pNumeroCaisse) {
        super(pMatricule, pNom, pPrenom, pSalaire);
        setRole("Caissier");
        numeroCaisse = pNumeroCaisse;
        caisseOuverte = false;
        venteEnCours = null;
    }

    // Ouverture de caisse
    public void ouvrirCaisse() {
        if (!caisseOuverte) {
            caisseOuverte = true;
            System.out.println("Caisse n°" + numeroCaisse + " ouverte par " + getPrenom() + " " + getNom());
        } else {
            System.out.println("La caisse n°" + numeroCaisse + " est déjà ouverte.");
        }
    }

    // Démarrer une vente (crée une vente en cours)
    public void effectuerVente(int pNumeroVente, Client pClient, ArrayList<Produit> pPanier) {
        if (!caisseOuverte) {
            System.out.println("La caisse n°" + numeroCaisse + " n'est pas ouverte. Veuillez d'abord ouvrir la caisse.");
            return;
        }
        venteEnCours = new Vente(pNumeroVente, new java.util.Date(), pClient, this);
        if (pPanier != null) {
            for (Produit p : pPanier) {
                venteEnCours.ajouterProduit(p);
            }
        }
        System.out.println("Vente n°" + pNumeroVente + " démarrée pour le client : " + pClient.getNom());
    }

    // Encaisser le paiement de la vente en cours
    public Paiement encaisserPaiement(int pNumeroPaiement, String pModePaiement) {
        if (venteEnCours == null) {
            System.out.println("Aucune vente en cours sur la caisse n°" + numeroCaisse);
            return null;
        }
        double montant = venteEnCours.calculerTotal();
        Paiement paiement = new Paiement(pNumeroPaiement, montant, pModePaiement, new java.util.Date(), venteEnCours);
        if (paiement.validerPaiement()) {
            System.out.println("Paiement de " + montant + " € encaissé avec succès (mode: " + pModePaiement + ")");
            venteEnCours = null;
        } else {
            System.out.println("Le paiement n'est pas valide.");
        }
        return paiement;
    }

    // Getters
    public int getNumeroCaisse()   { return numeroCaisse; }
    public boolean isCaisseOuverte(){ return caisseOuverte; }
    public Vente getVenteEnCours() { return venteEnCours; }
}
