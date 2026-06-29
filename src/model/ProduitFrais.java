package model;

import java.time.LocalDate;

public class ProduitFrais extends Produit {

	// _____==== EXERCICE 1 : GESTION DES PRODUITS ====_____

	// Attributs
	private LocalDate datePeremption;
	private double temperatureConservation; // en degrés Celsius

	// Constructeur d'initialisation
	public ProduitFrais(int pReference, int pQuantiteStock, double pPrixAchat, double pPrixVente, String pDesignation,
			double pTemperatureConservation, LocalDate pDatePeremption) {
		super(pReference, pQuantiteStock, pPrixAchat, pPrixVente, pDesignation);
		temperatureConservation = pTemperatureConservation;
		datePeremption = pDatePeremption;
	}

	// Vérifier si le produit est périmé
	public boolean estPerime() {
		return LocalDate.now().isAfter(datePeremption);
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
