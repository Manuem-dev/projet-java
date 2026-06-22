package model;

import java.time.LocalDate;

public class Facture {

    // _____==== EXERCICE 9 : GESTION DES FACTURES ====_____

    // Attributs 
    private int numeroFacture;
    private LocalDate dateFacture; // Possible aussi d'utiliser Date à la place
    private Vente vente;
    private Paiement paiement;

    // Méthodes

    // Constructeur d'initialisation
    public Facture(int pNumeroFacture, LocalDate pDateFacture, Vente pVente, Paiement pPaiement) {
    	numeroFacture = pNumeroFacture;
    	dateFacture = pDateFacture;
    	vente = pVente;
    	paiement = pPaiement;
    }

    // Générer une facture
    public Facture genereFacture(){
		  return null;
        
    }
    
    // Impression de la facture
    public void imprimerFacture() {
    	
    }
    
    // Affichage de la facture
    public void afficherFacture(){
    	
    }
    
    // Getters

    public int getNumeroFacture() {
      return numeroFacture;
    }

    public LocalDate getDateFacture() {
      return dateFacture;
    }

    public Vente getVente() {
      return vente;
    }

    public Paiement getPaiement() {
      return paiement;
    }
      

}
