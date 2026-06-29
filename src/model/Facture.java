package model;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

public class Facture {

	// _____==== EXERCICE 9 : GESTION DES FACTURES ====_____

	// Attributs respectant strictement le PDF
	private int numeroFacture;
	private Date dateFacture;
	private Vente vente;
	private Paiement paiement;

	// Constructeur d'initialisation
	public Facture(int pNumeroFacture, Date pDateFacture, Vente pVente, Paiement pPaiement) {
		numeroFacture = pNumeroFacture;
		dateFacture = pDateFacture;
		vente = pVente;
		paiement = pPaiement;
	}

	// Générer la facture          
	public static Facture genererFacture(int numeroFacture, Vente vente, Paiement paiement) {
		return new Facture(numeroFacture, new Date(), vente, paiement);
	}

	// Impression de la facture (console)
	public void imprimerFacture() {
		System.out.println(getContenuFacture());
	}

	// Affichage de la facture à l'écran (retourne le contenu formaté)
	public void afficherFacture() {
		System.out.println(getContenuFacture());
	}

	// Contenu textuel formaté de la facture
	public String getContenuFacture() {
		StringBuilder sb = new StringBuilder();
		sb.append("╔══════════════════════════════════════════╗\n");
		sb.append("║           FACTURE N°").append(String.format("%-22d", numeroFacture)).append("║\n");
		sb.append("╠══════════════════════════════════════════╣\n");
		sb.append("║ Date        : ").append(String.format("%-28s", dateFacture)).append("║\n");
		if (vente.getClient() != null) {
			sb.append("║ Client      : ").append(String.format("%-28s", vente.getClient().getNom())).append("║\n");
		}
		sb.append("║ Caissier    : ")
				.append(String.format("%-28s", vente.getCaissier().getPrenom() + " " + vente.getCaissier().getNom()))
				.append("║\n");
		sb.append("╠══════════════════════════════════════════╣\n");
		sb.append("║ DÉTAIL DES ARTICLES                      ║\n");
		sb.append("╠══════════════════════════════════════════╣\n");

		// Regrouper les produits pour un affichage plus clair
		Map<Produit, Integer> occurrences = new HashMap<>();
		for (Produit p : vente.getPanier()) {
			occurrences.put(p, occurrences.getOrDefault(p, 0) + 1);
		}

		for (Map.Entry<Produit, Integer> entry : occurrences.entrySet()) {
			String ligne = "║ " + entry.getKey().getDesignation() + " x" + entry.getValue() + " = "
					+ String.format("%.2f", entry.getKey().getPrixVente() * entry.getValue()) + " €";
			sb.append(String.format("%-44s", ligne)).append("║\n");
		}
		sb.append("╠══════════════════════════════════════════╣\n");
		sb.append("║ TOTAL         : ")
				.append(String.format("%-26s", String.format("%.2f", vente.calculerTotal()) + " €")).append("║\n");
		sb.append("║ Mode paiement : ").append(String.format("%-26s", paiement.getModePaiement())).append("║\n");
		sb.append("║ Monnaie rendue: ")
				.append(String.format("%-26s", String.format("%.2f", paiement.calculerMonnaieRendue()) + " €"))
				.append("║\n");
		sb.append("╚══════════════════════════════════════════╝\n");
		return sb.toString();
	}

	
	
	// Getters
	public int getNumeroFacture() {
		return numeroFacture;
	}

	public Date getDateFacture() {
		return dateFacture;
	}

	public Vente getVente() {
		return vente;
	}

	public Paiement getPaiement() {
		return paiement;
	}
}
