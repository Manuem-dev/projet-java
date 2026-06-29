package model;

import java.util.Date;
import java.util.ArrayList;

public class CommandeFournisseur {

	// _____==== EXERCICE 2 : GESTION FOURNISSEURS ET APPROVISIONNEMENT ====_____

	// États possibles d'une commande
	public static final String attente = "En attente";
	public static final String validee = "Validée";
	public static final String livree = "Livrée";
	public static final String annulee = "Annulée";

	// Attributs respectant strictement le PDF
	private int numeroCommande;
	private Date date;
	private Fournisseur fournisseur;
	private ArrayList<Produit> listeProduits;
	private String etatCommande;

	// Constructeur d'initialisation
	public CommandeFournisseur(int pNumeroCommande, Date pDate, Fournisseur pFournisseur,
			ArrayList<Produit> pListeProduits) {
		numeroCommande = pNumeroCommande;
		date = pDate;
		fournisseur = pFournisseur;
		listeProduits = pListeProduits;
		etatCommande = attente;
	}

	// Valider la commande
	public void validerCommande() {
		if (etatCommande.equals(attente)) {
			etatCommande = validee;
			System.out.println("Commande n°" + numeroCommande + " validée.");
		} else {
			System.out.println("Impossible de valider : état actuel = " + etatCommande);
		}
	}

	// Marquer la commande comme livrée et mettre à jour les stocks
	public void marquerLivree() {
		if (etatCommande.equals(validee)) {
			etatCommande = livree;
			System.out.println("Commande n°" + numeroCommande + " marquée comme livrée.");
		} else {
			System.out.println("Impossible de livrer : état actuel = " + etatCommande);
		}
	}

	// Annuler la commande
	public void annulerCommande() {
		if (!etatCommande.equals(livree)) {
			etatCommande = annulee;
			System.out.println("Commande n°" + numeroCommande + " annulée.");
		} else {
			System.out.println("Impossible d'annuler une commande déjà livrée.");
		}
	}

	// Calculer le coût total de la commande
	public double calculerCoutTotal() {
		double cout = 0.0;
		for (Produit p : listeProduits) {
			cout += p.getPrixAchat() * p.getQuantiteStock();
		}
		return cout;
	}

	// Afficher la commande
	public void afficher() {
		System.out.println("Commande n°" + numeroCommande + " | Date: " + date + " | Fournisseur: "
				+ fournisseur.getNom() + " | État: " + etatCommande + " | Produits: " + listeProduits.size());
	}

	@Override
	public String toString() {
		return "Cmd n°" + numeroCommande + " - " + fournisseur.getNom() + " [" + etatCommande + "]";
	}

	// Getters
	public int getNumeroCommande() {
		return numeroCommande;
	}

	public Date getDate() {
		return date;
	}

	public Fournisseur getFournisseur() {
		return fournisseur;
	}

	public ArrayList<Produit> getListeProduits() {
		return listeProduits;
	}

	public String getEtatCommande() {
		return etatCommande;
	}

	// Setters
	public void setEtatCommande(String etat) {
		this.etatCommande = etat;
	}
}
