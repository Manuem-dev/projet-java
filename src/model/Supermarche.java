package model;

import java.util.ArrayList;

public class Supermarche {

	// _____==== EXERCICE 10 : TABLEAU DE BORD DIRECTION ====_____

	// Attributs
	private String nom;
	private ArrayList<Produit> produits;
	private ArrayList<Client> clients;
	private ArrayList<Employe> employes;
	private ArrayList<Rayon> rayons;
	private ArrayList<Vente> ventes;
	private ArrayList<Fournisseur> fournisseurs;
	private ArrayList<CommandeFournisseur> commandes;

	// Constructeur d'initialisation
	public Supermarche(String pNom) {
		nom = pNom;
		produits = new ArrayList<>();
		clients = new ArrayList<>();
		employes = new ArrayList<>();
		rayons = new ArrayList<>();
		ventes = new ArrayList<>();
		fournisseurs = new ArrayList<>();
		commandes = new ArrayList<>();
	}

	// Ajout de produit
	public void ajouterProduit(Produit produit) {
		produits.add(produit);
	}

	// Suppression de produit
	public void supprimerProduit(Produit produit) {
		produits.remove(produit);
	}

	// Ajout de client à la liste des clients du supermarché
	public void ajouterClient(Client client) {
		clients.add(client);
	}
	
	// Suppression d'un client
	public void supprimerClient(Client client) {
		clients.remove(client);
	}
	
	// Recherche d'un client
	public Client rechercherClient(int numeroClient) {
		for (Client c : clients) {
			if (c.getNumeroClient() == numeroClient)
				return c;
		}
		return null;
	}

	// Ajout d'un employé
	public void ajouterEmploye(Employe employe) {
		employes.add(employe);
	}

	// Suppression d'un employé
	public void supprimerEmploye(Employe employe) {
		employes.remove(employe);
	}

	// Ajout de rayon dans le supermarché
	public void ajouterRayon(Rayon rayon) {
		rayons.add(rayon);
	}
	
	// Suppression de rayon dans le supermarché
	public void supprimerRayon(Rayon rayon) {
		rayons.remove(rayon);
	}

	// Enregistrement de vente
	public void enregistrerVente(Vente vente) {
		ventes.add(vente);
	}

	// Ajout de fournisseur
	public void ajouterFournisseur(Fournisseur fournisseur) {
		fournisseurs.add(fournisseur);
	}
	
	// Réalisation d'une commande
	public void ajouterCommande(CommandeFournisseur commande) {
		commandes.add(commande);
	}

	// Certaines statistiques

	// Chiffre d'affaires total
	public double calculerChiffreAffaires() {
		double chiffreAffaire = 0.0;
		for (Vente vente : ventes)
			chiffreAffaire += vente.calculerTotal();
		return chiffreAffaire;
	}

	// Afficher les produits en stock faible (sous le seuil)
	public ArrayList<Produit> afficherStockFaible(int seuil) {
		ArrayList<Produit> produitsAlerte = new ArrayList<>();
		for (Produit produit : produits) {
			if (produit.getQuantiteStock() <= seuil) {
				produitsAlerte.add(produit);
				System.out.println("[ALERTE STOCK] " + produit.getDesignation() + " : " + produit.getQuantiteStock() + " unités");
			}
		}
		return produitsAlerte;
	}

	// Afficher les produits frais périmés
	public ArrayList<Produit> afficherProduitsExpires() {
		ArrayList<Produit> expires = new ArrayList<>();
		for (Produit produit : produits) {
			if (produit instanceof ProduitFrais) {
				ProduitFrais produitFrais = (ProduitFrais) produit;
				if (produitFrais.estPerime()) {
					expires.add(produitFrais);
					System.out.println("[PÉRIMÉ] " + produitFrais.getDesignation() + " | Exp. : " + produitFrais.getDatePeremption());
				}
			}
		}
		System.out.println("Total produits périmés : " + expires.size());
		return expires;
	}

	// Getters
	public String getNom() {
		return nom;
	}

	public ArrayList<Produit> getProduits() {
		return produits;
	}

	public ArrayList<Client> getClients() {
		return clients;
	}

	public ArrayList<Employe> getEmployes() {
		return employes;
	}

	public ArrayList<Rayon> getRayons() {
		return rayons;
	}

	public ArrayList<Vente> getVentes() {
		return ventes;
	}

	public ArrayList<Fournisseur> getFournisseurs() {
		return fournisseurs;
	}

	public ArrayList<CommandeFournisseur> getCommandes() {
		return commandes;
	}
}
