package service;

import java.util.Date;
import java.util.ArrayList;

import model.CommandeFournisseur;
import model.Fournisseur;
import model.Magasinier;
import model.Produit;

public class GestionFournisseurApprovisionnement {

	// _____==== EXERCICE 2 : GESTION FOURNISSEURS ET APPROVISIONNEMENT ====_____

	private ArrayList<Fournisseur> listeFournisseurs = new ArrayList<>();
	private ArrayList<CommandeFournisseur> listeCommandes = new ArrayList<>();
	private int numeroCommandeAuto = 1;

	// ==================== FOURNISSEURS ====================

	public void ajouterFournisseur(String code, String nom, String telephone, String adresse) {
		if (rechercherFournisseur(code) != null) {
			System.out.println("Fournisseur avec le code " + code + " déjà existant.");
			return;
		}
		listeFournisseurs.add(new Fournisseur(code, nom, telephone, adresse));
		System.out.println("Fournisseur ajouté : " + nom);
	}

	public Fournisseur rechercherFournisseur(String code) {
		for (Fournisseur fournisseur : listeFournisseurs) {
			if (fournisseur.getCode().equalsIgnoreCase(code))
				return fournisseur;
		}
		return null;
	}

	public boolean supprimerFournisseur(String code) {
		Fournisseur fournisseur = rechercherFournisseur(code);
		if (fournisseur != null) {
			listeFournisseurs.remove(fournisseur);
			System.out.println("Fournisseur supprimé : " + fournisseur.getNom());
			return true;
		}
		System.out.println("Fournisseur introuvable : " + code);
		return false;
	}

	// ==================== COMMANDES ====================

	public CommandeFournisseur creerCommande(String codeFournisseur, ArrayList<Produit> produits) {
		Fournisseur fournisseur = rechercherFournisseur(codeFournisseur);
		if (fournisseur == null) {
			System.out.println("Fournisseur introuvable : " + codeFournisseur);
			return null;
		}
		CommandeFournisseur commande = new CommandeFournisseur(numeroCommandeAuto++, new Date(), fournisseur, produits);
		listeCommandes.add(commande);
		System.out.println("Commande n°" + commande.getNumeroCommande() + " créée pour " + fournisseur.getNom());
		return commande;
	}

	public void validerCommande(int numeroCommande) {
		CommandeFournisseur commande = rechercherCommande(numeroCommande);
		if (commande != null)
			commande.validerCommande();
		else
			System.out.println("Commande introuvable : " + numeroCommande);
	}

	public void recevoirCommande(int numeroCommande, Magasinier magasinier) {
		CommandeFournisseur commande = rechercherCommande(numeroCommande);
		if (commande == null) {
			System.out.println("Commande introuvable : " + numeroCommande);
			return;
		}
		// ça c'est pour incrémenter le stock de chaque produit de la commande
		for (Produit produit : commande.getListeProduits()) {
			magasinier.mettreAJourStock(produit, produit.getQuantiteStock(), true);
		}
		commande.marquerLivree();
		magasinier.receptionnerLivraison(commande);
	}

	public void annulerCommande(int numeroCommande) {
		CommandeFournisseur commande = rechercherCommande(numeroCommande);
		if (commande != null)
			commande.annulerCommande();
		else
			System.out.println("Commande introuvable : " + numeroCommande);
	}

	public CommandeFournisseur rechercherCommande(int numeroCommande) {
		for (CommandeFournisseur commande : listeCommandes) {
			if (commande.getNumeroCommande() == numeroCommande)
				return commande;
		}
		return null;
	}

	// Getters
	public ArrayList<Fournisseur> getListeFournisseurs() {
		return listeFournisseurs;
	}

	public ArrayList<CommandeFournisseur> getListeCommandes() {
		return listeCommandes;
	}
}
