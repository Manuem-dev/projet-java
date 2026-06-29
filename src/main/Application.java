package main;

import java.time.LocalDate;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import model.*;
import service.*;
import interfaceGraphique.Accueil;


public class Application {

	public static void main(String[] args) {

		
		// INITIALISATION DES DONNÉES DE TEST
		
		// ─── Services ─────────────────────────────────────────────────────
		GestionProduit gestionProduit = new GestionProduit();
		GestionEmploye gestionEmploye = new GestionEmploye();
		GestionClient gestionClient = new GestionClient();
		GestionRayon gestionRayon = new GestionRayon();
		GestionCaisseVente gestionCaisseVente = new GestionCaisseVente();
		GestionFacture gestionFacture = new GestionFacture();
		GestionFournisseurApprovisionnement gestionFourn = new GestionFournisseurApprovisionnement();
		GestionUtilisateur gestionUtil = new GestionUtilisateur();
		Stock stock = new Stock();

		// ─── EXERCICE 1 : Produits ────────────────────────────────────────
		System.out.println("\n=== EXERCICE 1 : PRODUITS ===");
		gestionProduit.ajoutProduitArtisanal(101, 50, 1.2, 2.5, "Baguette", "Boulangerie", "Boulangerie Martin",
				"France");
		gestionProduit.ajoutProduitArtisanal(102, 30, 8.0, 15.0, "Filet de saumon", "Poissonnerie",
				"Poissonnerie Duval", "Norvège");
		gestionProduit.ajoutProduitArtisanal(103, 20, 5.0, 12.0, "Côte de bœuf", "Boucherie", "Boucherie Pascal",
				"France");
		gestionProduit.ajoutProduitElectronique(201, 15, 180.0, 299.99, "Télévision 40\"", "Samsung", 24);
		gestionProduit.ajoutProduitElectronique(202, 8, 50.0, 89.99, "Casque Bluetooth", "Sony", 12);
		gestionProduit.ajoutProduitFrais(301, 80, 0.8, 1.99, "Yaourt nature", 4.0, LocalDate.now().plusDays(15));
		gestionProduit.ajoutProduitFrais(302, 5, 1.5, 3.99, "Lait entier (1L)", 6.0, LocalDate.now().plusDays(7));
		

		System.out.println("Produits créés : " + gestionProduit.getListeDesProduits().size());

		// ─── EXERCICE 2 : Fournisseurs ────────────────────────────────────
		System.out.println("\n=== EXERCICE 2 : FOURNISSEURS ===");
		gestionFourn.ajouterFournisseur("F001", "Boulangerie Martin", "0612345678", "12 rue du Pain, Lyon");
		gestionFourn.ajouterFournisseur("F002", "Samsung France", "0699887766", "Tour Samsung, Paris");
		gestionFourn.ajouterFournisseur("F003", "Frais du Terroir", "0477889900", "Zone Agro, Marseille");

		// ─── EXERCICE 3 : Stock (test d'entrée/sortie) ───────────────────
		System.out.println("\n=== EXERCICE 3 : STOCK ===");
		Produit baguette = gestionProduit.rechercherProduit(101);
		Fournisseur fournMartin = gestionFourn.rechercherFournisseur("F001");
		stock.entreeStock(baguette, 20, fournMartin);
		stock.sortieStock(baguette, 5);
		System.out.println("Alertes stock (seuil 10) : "
				+ stock.verifierStockMinimum(gestionProduit.getListeDesProduits(), 10).size());

		// ─── EXERCICE 4 : Rayons ─────────────────────────────────────────
		System.out.println("\n=== EXERCICE 4 : RAYONS ===");
		ChefRayon chefBoulangerie = new ChefRayon(50, "Durand", "Alice", 3200, "Boulangerie");
		ChefRayon chefElectro = new ChefRayon(51, "Lambert", "Paul", 3500, "Électronique");
		ChefRayon chefFrais = new ChefRayon(52, "Morin", "Lucie", 3000, "Produits frais");

		Rayon rayonBoulan = gestionRayon.ajouterRayon("Boulangerie / Poissonnerie", chefBoulangerie);
		Rayon rayonElectro = gestionRayon.ajouterRayon("Électronique", chefElectro);
		Rayon rayonFrais = gestionRayon.ajouterRayon("Produits frais", chefFrais);

		// Associer les produits aux rayons
		rayonBoulan.ajouterProduit(gestionProduit.rechercherProduit(101));
		rayonBoulan.ajouterProduit(gestionProduit.rechercherProduit(102));
		rayonBoulan.ajouterProduit(gestionProduit.rechercherProduit(103));
		rayonElectro.ajouterProduit(gestionProduit.rechercherProduit(201));
		rayonElectro.ajouterProduit(gestionProduit.rechercherProduit(202));
		rayonFrais.ajouterProduit(gestionProduit.rechercherProduit(301));
		rayonFrais.ajouterProduit(gestionProduit.rechercherProduit(302));
		gestionRayon.afficherTousLesRayons();

		// ─── EXERCICE 5 : Personnel ──────────────────────────────────────
		System.out.println("\n=== EXERCICE 5 : PERSONNEL ===");
		Directeur directeur = new Directeur(1, "Beaumont", "Jean", 6000);
		Caissier caissier1 = new Caissier(10, "Petit", "Marie", 2100, 1);
		Caissier caissier2 = new Caissier(11, "Garnier", "Thomas", 2100, 2);
		Comptable comptable = new Comptable(20, "Simon", "Nathalie", 3800);
		Magasinier magasinier = new Magasinier(30, "Renard", "Kevin", 2400);

		gestionEmploye.ajouterEmploye(directeur);
		gestionEmploye.ajouterEmploye(caissier1);
		gestionEmploye.ajouterEmploye(caissier2);
		gestionEmploye.ajouterEmploye(chefBoulangerie);
		gestionEmploye.ajouterEmploye(chefElectro);
		gestionEmploye.ajouterEmploye(chefFrais);
		gestionEmploye.ajouterEmploye(comptable);
		gestionEmploye.ajouterEmploye(magasinier);
		gestionEmploye.afficherTousLesEmployes();

		// Prime de test
		gestionEmploye.ajouterPrime(10, 150.0);

		// ─── EXERCICE 6 : Clients ─────────────────────────────────────────
		System.out.println("\n=== EXERCICE 6 : CLIENTS ===");
		Client client1 = gestionClient.ajouterClient("Sophie Marchand", "0611223344");
		Client client2 = gestionClient.ajouterClient("Pierre Leroy", "0699001122");
		Client client3 = gestionClient.ajouterClient("Emma Blanc", "0677334455");

		// ─── EXERCICE 7 & 8 : Vente et paiement ──────────────────────────
		System.out.println("\n=== EXERCICE 7 & 8 : VENTE & PAIEMENT ===");
		caissier1.ouvrirCaisse();

		// Vente 1 : Sophie achète une baguette et un yaourt
		Vente vente1 = gestionCaisseVente.demarrerVente(caissier1, client1);
		gestionCaisseVente.ajouterProduitVente(vente1, gestionProduit.rechercherProduit(101), 3);
		gestionCaisseVente.ajouterProduitVente(vente1, gestionProduit.rechercherProduit(301), 2);
		Facture facture1 = gestionCaisseVente.finaliserVente(vente1, Paiement.especes, 20.0);
		if (facture1 != null)
			gestionFacture.enregistrerFacture(facture1);

		// Vente 2 : Pierre achète un casque Bluetooth
		Vente vente2 = gestionCaisseVente.demarrerVente(caissier2, client2);
		gestionCaisseVente.ajouterProduitVente(vente2, gestionProduit.rechercherProduit(202), 1);
		Facture facture2 = gestionCaisseVente.finaliserVente(vente2, Paiement.carte, 89.99);
		if (facture2 != null)
			gestionFacture.enregistrerFacture(facture2);

		// ─── EXERCICE 9 : Factures ────────────────────────────────────────
		System.out.println("\n=== EXERCICE 9 : FACTURES ===");
		gestionFacture.afficherToutesLesFactures();

		// ─── EXERCICE 10 : Tableau de bord ───────────────────────────────
		System.out.println("\n=== EXERCICE 10 : TABLEAU DE BORD ===");
		Supermarche supermarche = new Supermarche("SuperMarchéPlus");
		for (Produit p : gestionProduit.getListeDesProduits())
			supermarche.ajouterProduit(p);
		for (Employe e : gestionEmploye.getListeEmployes())
			supermarche.ajouterEmploye(e);
		supermarche.ajouterClient(client1);
		supermarche.ajouterClient(client2);
		supermarche.ajouterClient(client3);
		for (Rayon r : gestionRayon.getListeRayons())
			supermarche.ajouterRayon(r);
		for (Vente v : gestionCaisseVente.getListeVentes())
			supermarche.enregistrerVente(v);
		supermarche.ajouterFournisseur(fournMartin);

		directeur.voirStatistiques(supermarche);
		directeur.consulterEmployes(gestionEmploye.getListeEmployes());
		comptable.genererRapport(gestionCaisseVente.getListeVentes());

		TableauDeBord tdb = new TableauDeBord(supermarche);
		tdb.afficherTableauDeBord();

		// ─── EXERCICE 11 : Utilisateurs ──────────────────────────────────
		System.out.println("\n=== EXERCICE 11 : UTILISATEURS ===");
		gestionUtil.creerUtilisateur("directeur", "dir2024", directeur);
		gestionUtil.creerUtilisateur("caissier1", "caisse1", caissier1);
		gestionUtil.creerUtilisateur("caissier2", "caisse2", caissier2);
		gestionUtil.creerUtilisateur("comptable", "compta1", comptable);
		gestionUtil.creerUtilisateur("magasinier", "stock123", magasinier);

		System.out.println("\nTest connexion directeur :");
		gestionUtil.connecter("directeur", "dir2024");
		gestionUtil.deconnecter();

		System.out.println("Test connexion avec mauvais mdp :");
		gestionUtil.connecter("directeur", "mauvais");

		
		// LANCEMENT DE L'INTERFACE GRAPHIQUE
	
		System.out.println("\nLANCEMENT DE L'INTERFACE GRAPHIQUE");
		System.out.println("Comptes disponibles :");
		System.out.println("  login: directeur  | mdp: dir2024");
		System.out.println("  login: caissier1  | mdp: caisse1");
		System.out.println("  login: comptable  | mdp: compta1");
		System.out.println("  login: magasinier | mdp: stock123");

		// Passer les services partagés à l'UI
		final GestionUtilisateur guFinal = gestionUtil;

		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception ignored) {
			}

			// Passer TOUS les services peuplés à l'UI
			Accueil accueil = new Accueil(guFinal, gestionProduit, gestionEmploye, gestionClient, gestionRayon,
					gestionCaisseVente, gestionFacture);
			accueil.setVisible(true);
		});
	}
}
