package service;

import java.time.LocalDate;
import java.util.ArrayList;

import model.Produit;
import model.ProduitArtisanal;
import model.ProduitElectronique;
import model.ProduitFrais;

public class GestionProduit {

    // _____==== EXERCICE 1 : GESTION DES PRODUITS ====_____

    // Liste de tous les produits du supermarché
    private ArrayList<Produit> listeDesProduits = new ArrayList<>();

    // ==================== AJOUT ====================

    public void ajoutProduitArtisanal(int pReference, int pQuantiteStock, double pPrixAchat,
                                       double pPrixVente, String pDesignation,
                                       String pCategorie, String pArtisan, String pOrigine) {
        if (rechercherProduit(pReference) != null) {
            System.out.println("Référence déjà existante : " + pReference);
            return;
        }
        if (pQuantiteStock <= 0 || pPrixAchat <= 0 || pPrixVente <= 0 || pDesignation.trim().isEmpty()) {
            System.out.println("Erreur : données invalides pour le produit artisanal.");
            return;
        }
        listeDesProduits.add(new ProduitArtisanal(pReference, pQuantiteStock, pPrixAchat,
                pPrixVente, pDesignation, pCategorie, pArtisan, pOrigine));
        System.out.println("Produit artisanal ajouté : " + pDesignation);
    }

    public void ajoutProduitElectronique(int pReference, int pQuantiteStock, double pPrixAchat,
                                          double pPrixVente, String pDesignation,
                                          String pMarque, int pGarantie) {
        if (rechercherProduit(pReference) != null) {
            System.out.println("Référence déjà existante : " + pReference);
            return;
        }
        if (pQuantiteStock <= 0 || pPrixAchat <= 0 || pPrixVente <= 0 || pGarantie < 0
                || pDesignation.trim().isEmpty() || pMarque.trim().isEmpty()) {
            System.out.println("Erreur : données invalides pour le produit électronique.");
            return;
        }
        listeDesProduits.add(new ProduitElectronique(pReference, pQuantiteStock, pPrixAchat,
                pPrixVente, pDesignation, pMarque, pGarantie));
        System.out.println("Produit électronique ajouté : " + pDesignation);
    }

    public void ajoutProduitFrais(int pReference, int pQuantiteStock, double pPrixAchat,
                                   double pPrixVente, String pDesignation,
                                   double pTemperatureConservation, LocalDate pDatePeremption) {
        if (rechercherProduit(pReference) != null) {
            System.out.println("Référence déjà existante : " + pReference);
            return;
        }
        if (pQuantiteStock <= 0 || pPrixAchat <= 0 || pPrixVente <= 0
                || pDesignation.trim().isEmpty() || pDatePeremption.isBefore(LocalDate.now())) {
            System.out.println("Erreur : données invalides ou date de péremption dépassée.");
            return;
        }
        listeDesProduits.add(new ProduitFrais(pReference, pQuantiteStock, pPrixAchat,
                pPrixVente, pDesignation, pTemperatureConservation, pDatePeremption));
        System.out.println("Produit frais ajouté : " + pDesignation);
    }

    // ==================== MODIFICATION ====================

    public void modifierProduit(int pReference, int pQuantiteStock, double pPrixAchat,
                                 double pPrixVente, String pDesignation) {
        Produit produit = rechercherProduit(pReference);
        if (produit == null) {
            System.out.println("Produit introuvable (réf: " + pReference + ")");
            return;
        }
        if (pQuantiteStock < 0 || pPrixAchat <= 0 || pPrixVente <= 0 || pDesignation.trim().isEmpty()) {
            System.out.println("Erreur : données invalides.");
            return;
        }
        produit.setQuantiteStock(pQuantiteStock);
        produit.setPrixAchat(pPrixAchat);
        produit.setPrixVente(pPrixVente);
        produit.setDesignation(pDesignation);
        System.out.println("Produit modifié : " + pDesignation);
    }

    // ==================== SUPPRESSION ====================

    public boolean supprimerProduit(int pReference) {
        Produit produit = rechercherProduit(pReference);
        if (produit != null) {
            listeDesProduits.remove(produit);
            System.out.println("Produit supprimé : " + produit.getDesignation());
            return true;
        }
        System.out.println("Produit introuvable (réf: " + pReference + ")");
        return false;
    }

    // ==================== RECHERCHE ====================

    public Produit rechercherProduit(int pReference) {
        for (Produit produit : listeDesProduits) {
            if (produit.getReference() == pReference) return produit;
        }
        return null;
    }

    public ArrayList<Produit> rechercherParDesignation(String designation) {
        ArrayList<Produit> resultats = new ArrayList<>();
        String motCle = designation.trim().toLowerCase();
        for (Produit p : listeDesProduits) {
            if (p.getDesignation().toLowerCase().contains(motCle)) {
                resultats.add(p);
            }
        }
        return resultats;
    }

    // ==================== ALERTES ====================

    public ArrayList<Produit> getProduitsStockFaible(int seuil) {
        ArrayList<Produit> alerte = new ArrayList<>();
        for (Produit p : listeDesProduits) {
            if (p.getQuantiteStock() <= seuil) alerte.add(p);
        }
        return alerte;
    }

    public ArrayList<ProduitFrais> getProduitsPerimes() {
        ArrayList<ProduitFrais> perimes = new ArrayList<>();
        for (Produit p : listeDesProduits) {
            if (p instanceof ProduitFrais && ((ProduitFrais) p).estPerime()) {
                perimes.add((ProduitFrais) p);
            }
        }
        return perimes;
    }

    // Getter
    public ArrayList<Produit> getListeDesProduits() { return listeDesProduits; }
}
