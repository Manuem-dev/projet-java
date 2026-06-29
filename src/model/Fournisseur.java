package model;

import java.util.ArrayList;

public class Fournisseur {

	// _____==== EXERCICE 2 : GESTION FOURNISSEURS ET APPROVISIONNEMENT ====_____

	// Attributs
	private String code;
	private String nom;
	private String telephone;
	private String adresse;
	private ArrayList<Produit> produitsFournis;

	// Constructeur d'initialisation
	public Fournisseur(String pCode, String pNom, String pTelephone, String pAdresse) {
		code = pCode;
		nom = pNom;
		telephone = pTelephone;
		adresse = pAdresse;
		produitsFournis = new ArrayList<>();
	}

	// Ajouter un produit à la liste de ce fournisseur et incrémenter son stock
	public void ajouterProduit(Produit produit, int pQuantiteAFournir) {
		if (pQuantiteAFournir > 0) {
			produit.ajouterStock(pQuantiteAFournir);
			if (!produitsFournis.contains(produit)) {
				produitsFournis.add(produit);
			}
			System.out.println("Fournisseur [" + nom + "] a livré " + pQuantiteAFournir + " unités de : "
					+ produit.getDesignation());
		} else {
			System.out.println("Quantité invalide.");
		}
	}

	// Afficher les infos du fournisseur
	public void afficher() {
		System.out.println("Fournisseur [Code: " + code + " | Nom: " + nom + " | Tél: " + telephone + " | Adresse: "
				+ adresse + "]");
	}

	// Getters
	public String getCode() {
		return code;
	}

	public String getNom() {
		return nom;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getAdresse() {
		return adresse;
	}

	public ArrayList<Produit> getProduitsFournis() {
		return produitsFournis;
	}

	// Setters
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
}
