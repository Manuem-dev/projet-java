package service;

import java.util.ArrayList;
import model.Employe;
import model.Supermarche;
import model.Vente;

public class TableauDeBord {

    // _____==== EXERCICE 10 : TABLEAU DE BORD DIRECTION ====_____

    private Supermarche supermarche;

    public TableauDeBord(Supermarche supermarche) {
        this.supermarche = supermarche;
    }

    // Chiffre d'affaires total
    public double getChiffreAffaires() {
        return supermarche.calculerChiffreAffaires();
    }

    // Nombre de ventes
    public int getNombreVentes() {
        return supermarche.getVentes().size();
    }

    // Nombre de clients
    public int getNombreClients() {
        return supermarche.getClients().size();
    }

    // Nombre d'employés
    public int getNombreEmployes() {
        return supermarche.getEmployes().size();
    }

    // Nombre de produits
    public int getNombreProduits() {
        return supermarche.getProduits().size();
    }

    // Afficher le tableau de bord complet
    public void afficherTableauDeBord() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║       TABLEAU DE BORD - " + supermarche.getNom());
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║  Chiffre d'affaires : " + String.format("%.2f", getChiffreAffaires()) + " €");
        System.out.println("║  Nombre de ventes   : " + getNombreVentes());
        System.out.println("║  Nombre de clients  : " + getNombreClients());
        System.out.println("║  Nombre d'employés  : " + getNombreEmployes());
        System.out.println("║  Nombre de produits : " + getNombreProduits());
        System.out.println("║  Stocks faibles     : " + supermarche.afficherStockFaible(10).size() + " produit(s)");
        System.out.println("║  Produits périmés   : " + supermarche.afficherProduitsExpires().size() + " produit(s)");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
