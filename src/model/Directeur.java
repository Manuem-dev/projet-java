package model;

import java.util.ArrayList;

public class Directeur extends Employe {

    //_____==== EXERCICE 5 : GESTION DU PERSONNEL DU SUPERMARCHÉ ====_____

    // Constructeur d'initialisation
    public Directeur(int pMatricule, String pNom, String pPrenom, double pSalaire) {
        super(pMatricule, pNom, pPrenom, pSalaire);
        setRole("Directeur");
    }

    // Voir les statistiques du supermarché
    public void voirStatistiques(Supermarche supermarche) {
        System.out.println("=== STATISTIQUES DU SUPERMARCHÉ : " + supermarche.getNom() + " ===");
        System.out.println("Nombre de produits      : " + supermarche.getProduits().size());
        System.out.println("Nombre de clients       : " + supermarche.getClients().size());
        System.out.println("Nombre d'employés       : " + supermarche.getEmployes().size());
        System.out.println("Nombre de rayons        : " + supermarche.getRayons().size());
        System.out.println("Nombre de ventes        : " + supermarche.getVentes().size());

        double chiffreAffaires = 0.0;
        for (Vente v : supermarche.getVentes()) {
            chiffreAffaires += v.calculerTotal();
        }
        System.out.println("Chiffre d'affaires total: " + String.format("%.2f", chiffreAffaires) + " €");
        System.out.println("================================================");
    }

    // Consulter la liste de tous les employés
    public void consulterEmployes(ArrayList<Employe> employes) {
        System.out.println("=== LISTE DES EMPLOYÉS ===");
        if (employes.isEmpty()) {
            System.out.println("Aucun employé enregistré.");
            return;
        }
        for (Employe employe : employes) {
            employe.afficherInformations();
        }
        System.out.println("Total employés : " + employes.size());
    }
}
