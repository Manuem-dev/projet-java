package model;

import java.util.ArrayList;

public class Comptable extends Employe {

    //_____==== EXERCICE 5 : GESTION DU PERSONNEL DU SUPERMARCHÉ ====_____

    // Constructeur d'initialisation
    public Comptable(int pMatricule, String pNom, String pPrenom, double pSalaire) {
        super(pMatricule, pNom, pPrenom, pSalaire);
        setRole("Comptable");
    }

    // Calcul des recettes totales à partir de la liste des ventes
    public double calculerRecette(ArrayList<Vente> ventes) {
        double totalRecettes = 0.0;
        for (Vente vente : ventes) {
            totalRecettes += vente.calculerTotal();
        }
        System.out.println("Recettes totales calculées : " + String.format("%.2f", totalRecettes) + " €");
        return totalRecettes;
    }

    // Génération d'un rapport textuel sur les ventes
    public void genererRapport(ArrayList<Vente> ventes) {
        System.out.println("========== RAPPORT COMPTABLE ==========");
        System.out.println("Nombre de ventes enregistrées : " + ventes.size());
        double total = 0.0;
        for (Vente vente : ventes) {
            double montant = vente.calculerTotal();
            total += montant;
            System.out.println("  Vente n°" + vente.getNumeroVente()
                    + " | Date : " + vente.getDate()
                    + " | Montant : " + String.format("%.2f", montant) + " €"
                    + " | Client : " + vente.getClient().getNom());
        }
        System.out.println("---------------------------------------");
        System.out.println("TOTAL RECETTES : " + String.format("%.2f", total) + " €");
        System.out.println("=======================================");
    }
}
