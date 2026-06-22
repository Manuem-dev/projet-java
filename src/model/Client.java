package model;

import java.util.ArrayList;

public class Client {

    //_____==== EXERCICE 6 : GESTION DES CLIENTS ET FIDÉLITÉ ====_____

    // Attributs 
    private int numeroClient;
    private String nom;
    private String telephone;
    private int pointsFidelite = 0;
    private ArrayList <Vente> historiqueAchats;

    // Méthodes

    // Constructeur d'initialisation
    public Client(int pNumeroClient, String pNom, String pTelephone, int pPointsFidelite,
    		ArrayList<Vente> pHistoriqueAchats) {

    	numeroClient = pNumeroClient;
    	nom = pNom;
    	telephone = pTelephone;
    	pointsFidelite = pPointsFidelite;
    	historiqueAchats = pHistoriqueAchats;
    }
    

    

    // Ajout de points de fidélité
    public void ajouterPoint(int pPointsFidelite){
        if (pPointsFidelite>0) {
            pointsFidelite = pointsFidelite + pPointsFidelite;
        } else {
            System.out.println("Valeur entrée invalide");
        }
        
    }

    // Historique d'achat
    public void consulterHistorique(){
        
    }

	// Affichage du client
	public void afficherClient() {
		System.out.println("Client [numeroClient=" + numeroClient + ", nom=" + nom + ", telephone=" + telephone
				+ ", pointsFidelite=" + pointsFidelite + ", historiqueAchats=" + historiqueAchats + "]");
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
    
    

}
