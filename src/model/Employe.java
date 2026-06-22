package model;

public class Employe {

    //_____==== EXERCICE 5 : GESTION DU PERSONNEL DU SUPERMARCHÉ ====_____

    // Attributs
    private int matricule;
    private String nom;
    private String prenom;
    private double salaire;

    // Méthodes

    // Constructeur d'initialisation
    public Employe(int pMatricule, String pNom, String pPrenom, double pSalaire) {
    	matricule = pMatricule;
    	nom = pNom;
    	prenom = pPrenom;
    	salaire = pSalaire;
    }

    // Affichage des infos sur l'employé
    public void afficherInformations(){
        System.out.println("Employé : " + prenom + " " + nom + " Matricule : " + matricule + " Salaire : " + salaire);
    }

    // Calcul de salaire avec prime 
    public double calculerPrime(double pPrime){
        salaire = salaire + pPrime;
        return salaire;
    }

    // Affichage du rôle de l'employé ( à revoir )
    public void afficherRole(String pRole){
        System.out.println("Role : " + pRole + " pour l'employé : " + prenom + " " + nom + " matricule : " + matricule );
    }

    // Getters
    
	public int getMatricule() {
		return matricule;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public double getSalaire() {
		return salaire;
	}
    
    

}
