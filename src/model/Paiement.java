package model;

import java.time.LocalDate;


public class Paiement {
	
	// _____==== EXERCICE 8 : GESTION DU PAIEMENT ====_____

	// Attributs
	private int numeroPaiement;
	private double montant;
	private String modePaiement;
	private LocalDate datePaiement;
	private Vente vente;
	
	// Méthodes 

	// Constructeur d'initialisation
	public Paiement(int pNumeroPaiement, double pMontant, String pModePaiement, LocalDate pDatePaiement, Vente pVente) {
		numeroPaiement = pNumeroPaiement;
		montant = pMontant;
		modePaiement = pModePaiement;
		datePaiement = pDatePaiement;
		vente = pVente;
	}

	// Validation de paiement
	public boolean validerPaiement(){
		if (montant > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Getters 
	
	public int getNumeroPaiement() {
		return numeroPaiement;
	}

	public double getMontant() {
		return montant;
	}

	public String getModePaiement() {
		return modePaiement;
	}

	public LocalDate getDatePaiement() {
		return datePaiement;
	}

	public Vente getVente() {
		return vente;
	}
	
	
}
