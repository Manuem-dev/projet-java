package service;

import java.util.ArrayList;
import model.Employe;

public class GestionEmploye {

    // _____==== EXERCICE 5 : GESTION DU PERSONNEL ====_____

    private ArrayList<Employe> listeEmployes = new ArrayList<>();

    // Ajouter un employé
    public boolean ajouterEmploye(Employe employe) {
        for (Employe e : listeEmployes) {
            if (e.getMatricule() == employe.getMatricule()) {
                System.out.println("Matricule déjà existant : " + employe.getMatricule());
                return false;
            }
        }
        listeEmployes.add(employe);
        System.out.println("Employé ajouté : " + employe.getPrenom() + " " + employe.getNom());
        return true;
    }

    // Supprimer un employé par matricule
    public boolean supprimerEmploye(int matricule) {
        Employe e = rechercherEmploye(matricule);
        if (e != null) {
            listeEmployes.remove(e);
            System.out.println("Employé supprimé : " + e.getPrenom() + " " + e.getNom());
            return true;
        }
        System.out.println("Employé introuvable (matricule: " + matricule + ")");
        return false;
    }

    // Rechercher un employé par matricule
    public Employe rechercherEmploye(int matricule) {
        for (Employe e : listeEmployes) {
            if (e.getMatricule() == matricule) return e;
        }
        return null;
    }

    // Modifier le salaire d'un employé
    public void modifierSalaire(int matricule, double nouveauSalaire) {
        Employe e = rechercherEmploye(matricule);
        if (e != null) {
            e.setSalaire(nouveauSalaire);
            System.out.println("Salaire modifié pour " + e.getPrenom() + " " + e.getNom()
                    + " : " + nouveauSalaire + " €");
        } else {
            System.out.println("Employé introuvable (matricule: " + matricule + ")");
        }
    }

    // Ajouter une prime à un employé
    public void ajouterPrime(int matricule, double prime) {
        Employe e = rechercherEmploye(matricule);
        if (e != null) {
            double nouveauSalaire = e.calculerPrime(prime);
            System.out.println("Prime de " + prime + " € ajoutée. Nouveau salaire : " + nouveauSalaire + " €");
        } else {
            System.out.println("Employé introuvable (matricule: " + matricule + ")");
        }
    }

    // Afficher tous les employés
    public void afficherTousLesEmployes() {
        System.out.println("=== LISTE DES EMPLOYÉS (" + listeEmployes.size() + ") ===");
        for (Employe e : listeEmployes) {
            e.afficherInformations();
        }
    }

    // Getters
    public ArrayList<Employe> getListeEmployes() { return listeEmployes; }
}
