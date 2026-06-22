package model;

public class ProduitElectronique extends Produit {

    // _____==== EXERCICE 1 : GESTION DES PRODUITS ====_____

    // Attributs
    private String marque;
    private int garantie; // Ici on suppose la garantie comme le nombre de mois de garantie

    // Méthodes

    // Constructeur d'initialisation
	public ProduitElectronique(int pReference, int pQuantiteStock, double pPrixAchat, double pPrixVente,
			String pDesignation,String pMarque,int pGarantie) {
		super(pReference, pQuantiteStock, pPrixAchat, pPrixVente, pDesignation);
        marque = pMarque;
        garantie = pGarantie;
		
	}
	
	// Getters

	public String getMarque() {
		return marque;
	}

	public int getGarantie() {
		return garantie;
	}
	
	// Stters
	
	public void setMarque(String marque) {
		this.marque = marque;
	}

	public void setGarantie(int garantie) {
		this.garantie = garantie;
	}
	
	

}
