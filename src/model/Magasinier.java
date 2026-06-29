package model;

public class Magasinier extends Employe {

	// _____==== EXERCICE 5 : GESTION DU PERSONNEL DU SUPERMARCHÉ ====_____

	// Attributs
	 

	// Constructeur d'initialisation
	public Magasinier(int pMatricule, String pNom, String pPrenom, double pSalaire) {
		super(pMatricule, pNom, pPrenom, pSalaire);
		setRole("Magasinier");
		
	}

	// Réceptionner une livraison et ajouter la quantité reçue au stock du produit
	public void receptionnerLivraison(CommandeFournisseur commande) {
		System.out.println("=== Réception de la commande n°" + commande.getNumeroCommande() + " du fournisseur : "
				+ commande.getFournisseur().getNom() + " ===");
		for (Produit produit : commande.getListeProduits()) {
			System.out.println("  Produit réceptionné : " + produit.getDesignation()
					+ " | Quantité en stock après réception : " + produit.getQuantiteStock());
		}
	}

	// Mise à jour du stock d'un produit (entrée ou sortie)
	public void mettreAJourStock(Produit produit, int quantite, boolean estEntree) {
		if (estEntree) {
			produit.ajouterStock(quantite);
			System.out.println("Stock mis à jour (+) : " + produit.getDesignation() + " | Nouveau stock : "
					+ produit.getQuantiteStock());
		} else {
			produit.retirerStock(quantite);
			System.out.println("Stock mis à jour (-) : " + produit.getDesignation() + " | Nouveau stock : "
					+ produit.getQuantiteStock());
		}
	}

	
}