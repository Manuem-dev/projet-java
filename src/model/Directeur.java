package model;

public class Directeur extends Employe {

	//_____==== EXERCICE 5 : GESTION DU PERSONNEL DU SUPERMARCHÉ ====_____

	// Constructeur d'initialisation
	public Directeur(int pMatricule, String pNom, String pPrenom, double pSalaire) {
		super(pMatricule, pNom, pPrenom, pSalaire);
		
	}

	// Statistiques
	public void voirStatistiques(){

	}

	// Consulter les employés
	public void consulterEmployes(ArrayList <Employe> employes){
		for (Employe employe : employes) {
			employe.afficherInformations();
		}
	}

}
