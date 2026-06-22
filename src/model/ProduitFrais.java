package model;

import java.time.LocalDate;


public class ProduitFrais extends Produit {

    // _____==== EXERCICE 1 : GESTION DES PRODUITS ====_____

    // Attributs
	private LocalDate datePeremption; // Libre d'utiliser aussi Date à la place
    private double temperatureConservation;

    // Méthodes

    // Constructeur d'initialisation
	public ProduitFrais(int pReference, int pQuantiteStock, double pPrixAchat, double pPrixVente, String pDesignation,double pTemperatureConservation,LocalDate pDatePeremption) {
		super(pReference, pQuantiteStock, pPrixAchat, pPrixVente, pDesignation);
        datePeremption = pDatePeremption;
        temperatureConservation = pTemperatureConservation;
		
	}

	// Getters
	
	public LocalDate getDatePeremption() {
		return datePeremption;
	}

	public double getTemperatureConservation() {
		return temperatureConservation;
	}
	
	// Setters
	
	public void setDatePeremption(LocalDate datePeremption) {
		this.datePeremption = datePeremption;
	}

	public void setTemperatureConservation(double temperatureConservation) {
		this.temperatureConservation = temperatureConservation;
	}
	
	



}
