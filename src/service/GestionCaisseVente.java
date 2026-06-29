package service;

import java.util.Date;
import java.util.ArrayList;
import model.Caissier;
import model.Client;
import model.Facture;
import model.Paiement;
import model.Produit;
import model.Vente;

public class GestionCaisseVente {

	// _____==== EXERCICE 7 : MODULE CAISSE ET VENTE ====_____

	private ArrayList<Vente> listeVentes = new ArrayList<>();
	private int numeroVente = 1;
	private int numeroPaiement = 1;
	private int numeroFacture = 1;

	// Démarrer une nouvelle vente
	public Vente demarrerVente(Caissier caissier, Client client) {
		if (!caissier.isCaisseOuverte()) {
			caissier.ouvrirCaisse();
		}
		Vente vente = new Vente(numeroVente++, new Date(), client, caissier);
		System.out.println("Vente n°" + vente.getNumeroVente() + " démarrée.");
		return vente;
	}

	// Ajouter un produit au panier
	public void ajouterProduitVente(Vente vente, Produit produit, int quantite) {
		for (int i = 0; i < quantite; i++) {
			vente.ajouterProduit(produit);
		}
	}

	// Retirer un produit du panier
	public void retirerProduitVente(Vente vente, Produit produit) {
		while (vente.getPanier().contains(produit)) {
			vente.supprimerProduit(produit);
		}
	}

	// Finaliser la vente 
	public Facture finaliserVente(Vente vente, String modePaiement, double montantRecu) {
		// 1. Valider la vente 
		vente.validerVente();

		// 2. Créer le paiement
		Paiement paiement = new Paiement(numeroPaiement++, montantRecu, modePaiement, new Date(), vente);

		// 3. Valider le paiement
		if (!paiement.validerPaiement()) {
			System.out.println("Paiement insuffisant. Vente non complétée.");
			return null;
		}

		// 4. Générer la facture
		Facture facture = Facture.genererFacture(numeroFacture++, vente, paiement);

		// 5. Enregistrer la vente
		listeVentes.add(vente);

		// 6. Afficher la facture
		facture.imprimerFacture();

		return facture;
	}

	// Calculer le chiffre d'affaires total
	public double calculerChiffreAffaires() {
		double chiffreAffaire = 0.0;
		for (Vente vente : listeVentes)
			chiffreAffaire += vente.calculerTotal();
		return chiffreAffaire;
	}

	// Getters
	public ArrayList<Vente> getListeVentes() {
		return listeVentes;
	}
}
