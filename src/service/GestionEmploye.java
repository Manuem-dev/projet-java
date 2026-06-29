package service;

import java.util.ArrayList;
import model.Employe;

public class GestionEmploye {

	// _____==== EXERCICE 5 : GESTION DU PERSONNEL ====_____

	private ArrayList<Employe> listeEmployes = new ArrayList<>();

	// Ajouter un employé
	public boolean ajouterEmploye(Employe pEmploye) {
		for (Employe employe : listeEmployes) {
			if (employe.getMatricule() == pEmploye.getMatricule()) {
				System.out.println("Matricule déjà existant : " + pEmploye.getMatricule());
				return false;
			}
		}
		listeEmployes.add(pEmploye);
		System.out.println("Employé ajouté : " + pEmploye.getPrenom() + " " + pEmploye.getNom());
		return true;
	}

	// Supprimer un employé par matricule
	public boolean supprimerEmploye(int matricule) {
		Employe employe = rechercherEmploye(matricule);
		if (employe != null) {
			listeEmployes.remove(employe);
			System.out.println("Employé supprimé : " + employe.getPrenom() + " " + employe.getNom());
			return true;
		}
		System.out.println("Employé introuvable (matricule: " + matricule + ")");
		return false;
	}

	// Rechercher un employé par matricule
	public Employe rechercherEmploye(int matricule) {
		for (Employe employe : listeEmployes) {
			if (employe.getMatricule() == matricule)
				return employe;
		}
		return null;
	}

	// Modifier le salaire d'un employé
	public void modifierSalaire(int matricule, double nouveauSalaire) {
		Employe employe = rechercherEmploye(matricule);
		if (employe != null) {
			employe.setSalaire(nouveauSalaire);
			System.out.println(
					"Salaire modifié pour " + employe.getPrenom() + " " + employe.getNom() + " : " + nouveauSalaire + " €");
		} else {
			System.out.println("Employé introuvable (matricule: " + matricule + ")");
		}
	}

	// Ajouter une prime à un employé
	public void ajouterPrime(int matricule, double prime) {
		Employe employe = rechercherEmploye(matricule);
		if (employe != null) {
			double nouveauSalaire = employe.calculerPrime(prime);
			System.out.println("Prime de " + prime + " € ajoutée. Nouveau salaire : " + nouveauSalaire + " €");
		} else {
			System.out.println("Employé introuvable (matricule: " + matricule + ")");
		}
	}

	// Afficher tous les employés
	public void afficherTousLesEmployes() {
		System.out.println("=== LISTE DES EMPLOYÉS (" + listeEmployes.size() + ") ===");
		for (Employe employe : listeEmployes) {
			employe.afficherInformations();
		}
	}

	// Getters
	public ArrayList<Employe> getListeEmployes() {
		return listeEmployes;
	}
}
