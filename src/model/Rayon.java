package model;

import java.util.ArrayList;

public class Rayon {

    // _____==== EXERCICE 4 : GESTION DES RAYONS DU SUPERMARCHÉ ====_____

    // Attributs 
    private int codeRayon;
	private String nomRayon;
    private ChefRayon responsable;
    private ArrayList <Produit> listeProduits;

    // Méthodes 

    // Constructeur d'initialisation
    public Rayon(int pCodeRayon, String pNomRayon, ChefRayon pResponsable, ArrayList<Produit> pListeProduits) {
    	super();
    	codeRayon = pCodeRayon;
    	nomRayon = pNomRayon;
    	responsable = pResponsable;
    	listeProduits = pListeProduits;
    }
    
    // Ajout de produit au rayon
    public void ajouterProduit(Produit produit){
        listeProduits.add(produit);
    }
        
    // Retrait de produit du rayon
    public void retirerProduit(Produit produit){
        listeProduits.remove(produit);
    }

    // Rechercher un produit dans le rayon
    public Produit rechercheProduit(Produit produitCherche){
        for (Produit produit : listeProduits) {
            if (produit == produitCherche) {
                break;
            }
        }
        return produitCherche;
        
    }

    // Afficher les produits présents dans un rayon
    public void afficherProduit(){
        for (Produit produit : listeProduits) {
            
            System.out.println("Produit :" + produit.getDesignation() + " Stock :" + produit.getQuantiteStock() + " prix:" + produit.getPrixVente());
        }
    }

    // Calcul de la valeur totale du stock dans le rayon
    public double calculValeurStock(){
        double valeurStock = 0.0;
        for (Produit produit : listeProduits) {
            valeurStock = valeurStock + produit.getPrixVente()*produit.getQuantiteStock();
        }
        return valeurStock;
    }

    //Getters
    
	public int getCodeRayon() {
		return codeRayon;
	}

	public String getNomRayon() {
		return nomRayon;
	}

	public ChefRayon getResponsable() {
		return responsable;
	}

	public ArrayList<Produit> getListeProduits() {
		return listeProduits;
	}
    
    
}
