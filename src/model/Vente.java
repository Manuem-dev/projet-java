package model;

import java.time.LocalDate;
import java.util.ArrayList;


public class Vente {
	
	// _____==== EXERCICE 7 : MODULE CAISSE ET VENTE ====_____

	// Attributs
	private int numeroVente;
	private LocalDate date; // Date aussi pourrait passer
	private Client client;
	private Caissier caissier;
	private ArrayList <Produit> panier;
	
	// Méthodes

	// Constructeur d'initialisation
	public Vente(int pNumeroVente, LocalDate pDate, Client pClient, Caissier pCaissier, ArrayList<Produit> pPanier) {
		numeroVente = pNumeroVente;
		date = pDate;
		client = pClient;
		caissier = pCaissier;
		panier = pPanier;
	}

	// Ajout au panier
	public void ajouterProduit(Produit pProduit){
		panier.add(pProduit);
	}

	// retirer du panier
	public void supprimerProduit(Produit pProduit){
		panier.remove(pProduit);
	}

	// Calcul du total du panier
	public double calculTotal(){
		double total = 0.0;
		for (Produit produit : panier) {
			total = total + produit.getPrixVente();
		}
		return total;
		
	}
	
	// Validation de la vente
	public void validerVente(){
		// à faire après création de la classe Paiement
	}
	
	
	// Getters
	
	public int getNumeroVente() {
		return numeroVente;
	}

	public LocalDate getDate() {
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
	
	

	
}
