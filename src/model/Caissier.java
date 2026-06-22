package model;

public class Caissier extends Employe {

	//_____==== EXERCICE 5 : GESTION DU PERSONNEL DU SUPERMARCHÉ ====_____

	// Attributs
	private int numeroCaisse;


	// Méthodes

	// Constructeur d'initialisation
	public Caissier(int pMatricule, String pNom, String pPrenom, double pSalaire,int pNumeroCaisse ){
		super(pMatricule, pNom, pPrenom, pSalaire);
		numeroCaisse = pNumeroCaisse;
	}
	

	// Ouverture de caisse
	public void ouvrirCaisse(){

	}

	

	// Vente
	public void effectuerVente(){

	}

	// Paiement
	public void encaisserPaiement(){

	}
	

	// Getters
	
	public int getNumeroCaisse() {
		return numeroCaisse;
	}

}
