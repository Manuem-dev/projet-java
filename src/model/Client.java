package model;

import java.util.ArrayList;

public class Client {

	// _____==== EXERCICE 6 : GESTION DES CLIENTS ET FIDÉLITÉ ====_____

	// Attributs
	private int numeroClient;
	private String nom;
	private String telephone;
	private int pointsFidelite;
	private ArrayList<Vente> historiqueAchats;

	// Constructeur d'initialisation
	public Client(int pNumeroClient, String pNom, String pTelephone) {
		numeroClient = pNumeroClient;
		nom = pNom;
		telephone = pTelephone;
		pointsFidelite = 0;
		historiqueAchats = new ArrayList<>();
	}

	// Ajout de points de fidélité (on suppose ici que 1 point par euro dépensé)
	public void ajouterPoints(double montantAchat) {
		int pointsGagnes = (int) montantAchat; 
		if (pointsGagnes > 0) {
			pointsFidelite += pointsGagnes;
			System.out.println("+" + pointsGagnes + " points de fidélité ajoutés. Total : " + pointsFidelite + " pts");
		}
	}

	// Consulter l'historique des achats
	public void consulterHistorique() {
		System.out.println("=== HISTORIQUE D'ACHATS de " + nom + " ===");
		if (historiqueAchats.isEmpty()) {
			System.out.println("Aucun achat enregistré.");
			return;
		}
		double totalDepense = 0.0;
		for (Vente vente : historiqueAchats) {
			double montant = vente.calculerTotal();
			totalDepense += montant;
			System.out.println("  Vente n°" + vente.getNumeroVente() + " | Date : " + vente.getDate() + " | Montant : "
					+ String.format("%.2f", montant) + " €");
		}
		System.out.println("Total dépensé : " + String.format("%.2f", totalDepense) + " €");
		System.out.println("Points de fidélité : " + pointsFidelite + " pts");
	}

	// Enregistrer une vente dans l'historique
	public void ajouterVenteHistorique(Vente vente) {
		historiqueAchats.add(vente);
		ajouterPoints(vente.calculerTotal());
	}

	// Affichage du client
	public void afficherClient() {
		System.out.println("Client [n°" + numeroClient + " | Nom: " + nom + " | Tél: " + telephone + " | Points: "
				+ pointsFidelite + " pts" + " | Achats: " + historiqueAchats.size() + "]");
	}

	
	public String toString() {
		return nom + " (n°" + numeroClient + ", Tél: " + telephone + ", " + pointsFidelite + " pts)";
	}

	// Getters
	public int getNumeroClient() {
		return numeroClient;
	}

	public String getNom() {
		return nom;
	}

	public String getTelephone() {
		return telephone;
	}

	public int getPointsFidelite() {
		return pointsFidelite;
	}

	public ArrayList<Vente> getHistoriqueAchats() {
		return historiqueAchats;
	}

	// Setters
	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
