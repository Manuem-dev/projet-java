package service;

import java.util.ArrayList;
import model.Produit;
import model.ProduitFrais;
import model.Fournisseur;

public class Stock {

	// _____==== EXERCICE 3 : GESTION DU STOCK ====_____

	private static final int seuil = 10;

	// Entrée en stock (via livraison fournisseur)
	public void entreeStock(Produit produit, int pQuantiteEntree, Fournisseur fournisseur) {
		if (pQuantiteEntree <= 0) {
			System.out.println("Quantité invalide pour l'entrée en stock.");
			return;
		}
		produit.ajouterStock(pQuantiteEntree);
		System.out.println("Entrée stock : +" + pQuantiteEntree + " unités de [" + produit.getDesignation()
				+ "] | Fournisseur : " + fournisseur.getNom() + " | Nouveau stock : " + produit.getQuantiteStock());
	}

	// Sortie de stock (vente ou perte)
	public void sortieStock(Produit produit, int pQuantiteSortie) {
		if (pQuantiteSortie <= 0) {
			System.out.println("Quantité invalide pour la sortie en stock.");
			return;
		}
		produit.retirerStock(pQuantiteSortie);
		System.out.println("Sortie stock : -" + pQuantiteSortie + " unités de [" + produit.getDesignation()
				+ "] | Nouveau stock : " + produit.getQuantiteStock());
	}

	// Vérifier si un produit est en rupture de stock
	public boolean estEnRuptureDeStock(Produit produit) {
		return produit.getQuantiteStock() <= seuil;
	}

	// Obtenir les produits sous le seuil d'alerte 
	public ArrayList<Produit> verifierStockMinimum(ArrayList<Produit> produits, int seuil) {
		ArrayList<Produit> alerte = new ArrayList<>();
		for (Produit produit : produits) {
			if (produit.getQuantiteStock() <= seuil) {
				alerte.add(produit);
				System.out.println(
						"[ALERTE STOCK] " + produit.getDesignation() + " | Stock : " + produit.getQuantiteStock() + " unités");
			}
		}
		return alerte;
	}

	// Obtenir les produits frais périmés ( car c'est eux seulement qui ont date de péremption )
	public ArrayList<ProduitFrais> getProduitsPerimes(ArrayList<Produit> produits) {
		ArrayList<ProduitFrais> perimes = new ArrayList<>();
		for (Produit produit : produits) {
			if (produit instanceof ProduitFrais) {
				ProduitFrais produitFrais = (ProduitFrais) produit;
				if (produitFrais.estPerime()) {
					perimes.add(produitFrais);
					System.out.println("[PÉRIMÉ] " + produitFrais.getDesignation() + " | Exp. : " + produitFrais.getDatePeremption());
				}
			}
		}
		return perimes;
	}

	

	// Calculer la valeur totale du stock
	public double calculerValeurTotaleStock(ArrayList<Produit> produits) {
		double valeur = 0.0;
		for (Produit produit : produits) {
			valeur += produit.getPrixAchat() * produit.getQuantiteStock();
		}
		return valeur;
	}
	
	// Getters
	public static int getSeuil() {
		return seuil;
	}

	
}
