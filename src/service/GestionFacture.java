package service;

import java.util.ArrayList;
import model.Facture;

public class GestionFacture {

	// _____==== EXERCICE 9 : GESTION DES FACTURES ====_____

	private ArrayList<Facture> listeFactures = new ArrayList<>();

	// Enregistrer une facture
	public void enregistrerFacture(Facture facture) {
		listeFactures.add(facture);
	}

	// Rechercher une facture par numéro
	public Facture rechercherFacture(int numeroFacture) {
		for (Facture facture : listeFactures) {
			if (facture.getNumeroFacture() == numeroFacture)
				return facture;
		}
		return null;
	}

	// Afficher toutes les factures
	public void afficherToutesLesFactures() {
		System.out.println("=== LISTE DES FACTURES (" + listeFactures.size() + ") ===");
		for (Facture facture : listeFactures) {
			System.out.println("  " + facture.toString());
		}
	}

	// Imprimer une facture par numéro
	public void imprimerFacture(int numeroFacture) {
		Facture facture = rechercherFacture(numeroFacture);
		if (facture != null) {
			facture.imprimerFacture();
		} else {
			System.out.println("Facture n°" + numeroFacture + " introuvable.");
		}
	}

	// Getter
	public ArrayList<Facture> getListeFactures() {
		return listeFactures;
	}
}
