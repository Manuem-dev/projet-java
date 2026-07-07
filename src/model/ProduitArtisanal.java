package model;

public class ProduitArtisanal extends Produit {

	// _____==== EXERCICE 1 : GESTION DES PRODUITS ====_____
	

	// Attributs spécifiques
	private String categorie; 

	// Constructeur d'initialisation
	public ProduitArtisanal(int pReference, int pQuantiteStock, double pPrixAchat, double pPrixVente,
			String pDesignation, String pCategorie) {
		super(pReference, pQuantiteStock, pPrixAchat, pPrixVente, pDesignation);
		categorie = pCategorie;
		
	}


	// Getters
	public String getCategorie() {
		return categorie;
	}



	// Setters
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}


}
