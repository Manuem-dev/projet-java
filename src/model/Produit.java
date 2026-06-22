package model;

public class Produit {

    // _____==== EXERCICE 1 : GESTION DES PRODUITS ====_____

    // Attributs
    private int quantiteStock=0,reference;
    private double prixAchat=0.,prixVente=0.;
    private String designation="";


    // Méthodes
    // Constructeur d'initialisation
    public Produit(int pReference,int pQuantiteStock,double pPrixAchat,double pPrixVente,String pDesignation){
    	reference = pReference;
        quantiteStock = pQuantiteStock;
        prixAchat = pPrixAchat;
        prixVente = pPrixVente;
        designation = pDesignation;
        
    }

    // Les autres méthodes

    // Ajout
    public void ajouterStock(int pQuantiteStock){
        if (pQuantiteStock>0) {
            quantiteStock = quantiteStock + pQuantiteStock;
        } else {
            System.out.println("Valeur entrée invalide");
        }
        
    }

    // Retrait
    public void retirerStock(int pQuantiteStock){
        if (pQuantiteStock<=quantiteStock && pQuantiteStock >0) {
            quantiteStock = quantiteStock - pQuantiteStock;
        } else if(pQuantiteStock>quantiteStock) {
            System.out.println("Stock insuffisant,vous ne pouvez pas retirer la quantité demandée");
        } else {
            System.out.println("Valeur entrée invalide");
        }
        
    }

    // Modification
    public void modifierPrix(double pPrixVente){  
        prixVente = pPrixVente;  
    }

    // Calcul de la marge
    public double calculerMarge(){
        final double marge;
        marge = prixVente - prixAchat;
        return marge;
    }

	// toString
	public String toString() {
		return "Produit [reference=" + reference + ", quantiteStock=" + quantiteStock + ", prixAchat=" + prixAchat
				+ ", prixVente=" + prixVente + ", designation=" + designation + ", Marge=" + calculerMarge()
				+ "]";
	}
	
	// Getters

	public int getQuantiteStock() {
		return quantiteStock;
	}

	public double getPrixAchat() {
		return prixAchat;
	}

	public double getPrixVente() {
		return prixVente;
	}

	public String getDesignation() {
		return designation;
	}

	public int getReference() {
		return reference;
	}

	// Setters
	
	public void setQuantiteStock(int quantiteStock) {
		this.quantiteStock = quantiteStock;
	}

	public void setPrixAchat(double prixAchat) {
		this.prixAchat = prixAchat;
	}

	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}
	
	

	
    
    


}