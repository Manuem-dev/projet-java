package model;

import java.util.Date;
import java.util.ArrayList;

public class Vente {

	// _____==== EXERCICE 7 : MODULE CAISSE ET VENTE ====_____

	// Attributs 
	private int numeroVente;
	private Date date;
	private Client client;
	private Caissier caissier;
	private ArrayList<Produit> panier;
	private boolean validee; // pour confirmer la vente 

	// Constructeur d'initialisation
	public Vente(int pNumeroVente, Date pDate, Client pClient, Caissier pCaissier) {
		numeroVente = pNumeroVente;
		date = pDate;
		client = pClient;
		caissier = pCaissier;
		panier = new ArrayList<>();
		validee = false;
	}

	// Ajout au panier
	public void ajouterProduit(Produit pProduit) {
		if (validee) {
			System.out.println("Vente déjà validée, impossible de modifier le panier.");
			return;
		}
		if (pProduit.getQuantiteStock() > 0) {
			panier.add(pProduit);
		} else {
			System.out.println("Stock insuffisant pour : " + pProduit.getDesignation());
		}
	}

	// Retirer du panier
	public void supprimerProduit(Produit pProduit) {
		if (validee)
			return;
		panier.remove(pProduit);
	}

	// Calcul du total de la vente
	public double calculerTotal() {
		double total = 0.0;
		for (Produit produit : panier) {
			total += produit.getPrixVente();
		}
		return total;
	}

	// Validation de la vente : décrémente le stock de chaque produit
	public void validerVente() {
		if (validee) {
			System.out.println("Vente n°" + numeroVente + " déjà validée.");
			return;
		}
		for (Produit produit : panier) {
			produit.retirerStock(1);
		}
		validee = true;
		// Enregistrer dans l'historique du client
		if (client != null) {
			client.ajouterVenteHistorique(this);
		}
		System.out.println(
				"Vente n°" + numeroVente + " validée. Total : " + String.format("%.2f", calculerTotal()) + " €");
	}



	// Getters
	public int getNumeroVente() {
		return numeroVente;
	}

	public Date getDate() {
		return date;
	}

	public Client getClient() {
		return client;
	}

	public Caissier getCaissier() {
		return caissier;
	}

	public ArrayList<Produit> getPanier() {
		return panier;
	}

	public boolean isValidee() {
		return validee;
	}

	// Setter pour associer un client après démarrage de la vente
	public void setClient(Client pClient) {
		this.client = pClient;
	}
}
