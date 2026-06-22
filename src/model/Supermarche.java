package model;

import java.time.LocalDate;
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
    
    // Méthodes 
    
    // Constructeur d'initiasation
	public Supermarche(String pNom, ArrayList<Produit> pProduits, ArrayList<Client> pClients, ArrayList<Employe> pEmployes,
			ArrayList<Rayon> pRayons, ArrayList<Vente> pVentes) {
		nom = pNom;
		produits = pProduits;
		clients = pClients;
		employes = pEmployes;
		rayons = pRayons;
		ventes = pVentes;
	}

    // Ajout de produit
    public void ajouterProduit(Produit produit){
        produits.add(produit);
    }

    // Ajout de client
    public void ajouterClient(Client client){
        clients.add(client);
    }

    // Ajout d'un employé
    public void ajouterEmploye(Employe employe){
        employes.add(employe);
    }
    
    // Ajout d'un rayon
    public void ajouterRayon(Rayon rayon){
        rayons.add(rayon);
    }

    // Enregistrement de vente
    public void enregistrerVente(Vente vente){
        ventes.add(vente);
    }

    // Calcul du chiffre d'affaires
    public double calculerChiffreAffaires(ArrayList<Produit> produitVendus){
        double chiffreAffaires = 0.;
        for (Produit produit : produitVendus) {
			chiffreAffaires += produit.getPrixVente();
		}
        
        return chiffreAffaires;
    }

    // Affichage en cas de stock faible
    public void afficherStockFaible(){
        int seuil = 25;
        for (Produit produit : produits) {
            if (produit.getQuantiteStock() <= seuil) {
                System.out.println("Le produit === " + produit.getDesignation() + " === a un stock faible qui vaut " + produit.getQuantiteStock());
            }
        }
    }

    // Affichage en cas d'expiration du produit
    public void afficherProduitsExpires(){
        int count=0;
        for (Produit produit : produits) {
            if (((ProduitFrais) produit).getDatePeremption().isAfter(LocalDate.now())) {
                System.out.println("Le produit === " + produit.getDesignation() + " === est expiré,il faut donc le sortir du stock" );
                count++;
                
            }
        }
        System.out.println("Total de produits périmés : " + count);
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
    
    
}
