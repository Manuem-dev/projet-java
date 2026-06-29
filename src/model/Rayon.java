package model;

import java.util.ArrayList;

public class Rayon {

	// _____==== EXERCICE 4 : GESTION DES RAYONS DU SUPERMARCHÉ ====_____

	// Attributs
	private int codeRayon;
	private String nomRayon;
	private ChefRayon responsable;
	private ArrayList<Produit> listeProduits;

	// Constructeur d'initialisation
	public Rayon(int pCodeRayon, String pNomRayon, ChefRayon pResponsable) {
		codeRayon = pCodeRayon;
		nomRayon = pNomRayon;
		responsable = pResponsable;
		listeProduits = new ArrayList<>();
	}

	// Ajout de produit au rayon
	public void ajouterProduit(Produit produit) {
		if (!listeProduits.contains(produit)) {
			listeProduits.add(produit);
		} else {
			System.out.println("Ce produit est déjà dans le rayon.");
		}
	}

	// Retrait de produit du rayon
	public void retirerProduit(Produit produit) {
		if (listeProduits.contains(produit)) {
			listeProduits.remove(produit);
		} else {
			System.out.println("Produit non trouvé dans ce rayon.");
		}
	}

	// Rechercher un produit par référence dans le rayon
	public Produit rechercherProduit(int reference) {
		for (Produit produit : listeProduits) {
			if (produit.getReference() == reference) {
				return produit;
			}
		}
		return null; // Non trouvé
	}

	// Afficher les produits du rayon
	public void afficherProduits() {
		System.out.println("=== Rayon : " + nomRayon + " (Responsable : " + responsable.getPrenom() + " "
				+ responsable.getNom() + ") ===");
		if (listeProduits.isEmpty()) {
			System.out.println("  Aucun produit dans ce rayon.");
			return;
		}
		for (Produit produit : listeProduits) {
			System.out.println("  - " + produit.getDesignation() + " | Stock : " + produit.getQuantiteStock()
					+ " | Prix : " + produit.getPrixVente() + " €");
		}
	}

	// Calcul de la valeur totale du stock dans le rayon
	public double calculValeurStock() {
		double valeurStock = 0.0;
		for (Produit produit : listeProduits) {
			valeurStock += produit.getPrixVente() * produit.getQuantiteStock();
		}
		return valeurStock;
	}

	@Override
	public String toString() {
		return nomRayon + " (Code: " + codeRayon + ", " + listeProduits.size() + " produits)";
	}

	// Getters
	public int getCodeRayon() {
		return codeRayon;
	}

	public String getNomRayon() {
		return nomRayon;
	}

	public ChefRayon getResponsable() {
		return responsable;
	}

	public ArrayList<Produit> getListeProduits() {
		return listeProduits;
	}

	// Setters
	public void setResponsable(ChefRayon responsable) {
		this.responsable = responsable;
	}

	public void setNomRayon(String nomRayon) {
		this.nomRayon = nomRayon;
	}
}
