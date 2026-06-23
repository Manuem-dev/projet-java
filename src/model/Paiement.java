package model;

import java.util.Date;

public class Paiement {

    // _____==== EXERCICE 8 : GESTION DU PAIEMENT ====_____

    // Modes de paiement supportés
    public static final String MODE_ESPECES  = "Espèces";
    public static final String MODE_CARTE    = "Carte bancaire";
    public static final String MODE_CHEQUE   = "Chèque";

    // Attributs respectant strictement le PDF
    private int numeroPaiement;
    private double montant;
    private String modePaiement;
    private Date datePaiement;
    private Vente vente;
    
    // Attribut supplémentaire
    private boolean valide;

    // Constructeur d'initialisation
    public Paiement(int pNumeroPaiement, double pMontant, String pModePaiement,
                    Date pDatePaiement, Vente pVente) {
        numeroPaiement = pNumeroPaiement;
        montant        = pMontant;
        modePaiement   = pModePaiement;
        datePaiement   = pDatePaiement;
        vente          = pVente;
        valide         = false;
    }

    // Validation du paiement : le montant doit couvrir le total de la vente
    public boolean validerPaiement() {
        if (montant <= 0) {
            System.out.println("Paiement invalide : montant nul ou négatif.");
            valide = false;
            return false;
        }
        double totalVente = vente.calculerTotal();
        if (montant < totalVente) {
            System.out.println("Paiement insuffisant : " + montant + " € pour "
                    + String.format("%.2f", totalVente) + " €");
            valide = false;
            return false;
        }
        double rendu = montant - totalVente;
        valide = true;
        System.out.println("Paiement validé (" + modePaiement + "). Monnaie rendue : "
                + String.format("%.2f", rendu) + " €");
        return true;
    }

    // Calcul de la monnaie à rendre
    public double calculerMonnaieRendue() {
        return Math.max(0, montant - vente.calculerTotal());
    }

    // Afficher les détails du paiement (nom spécifié dans le PDF)
    public void afficherPaiement() {
        System.out.println("Paiement n°" + numeroPaiement
                + " | Mode: " + modePaiement
                + " | Montant: " + String.format("%.2f", montant) + " €"
                + " | Date: " + datePaiement
                + " | État: " + (valide ? "Validé" : "Non validé"));
    }

    @Override
    public String toString() {
        return "Paiement n°" + numeroPaiement + " [" + modePaiement + " - "
                + String.format("%.2f", montant) + " €]";
    }

    // Getters
    public int getNumeroPaiement()     { return numeroPaiement; }
    public double getMontant()         { return montant; }
    public String getModePaiement()    { return modePaiement; }
    public Date getDatePaiement()      { return datePaiement; }
    public Vente getVente()            { return vente; }
    public boolean isValide()          { return valide; }
}
