package model;

public class Employe {

    //_____==== EXERCICE 5 : GESTION DU PERSONNEL DU SUPERMARCHÉ ====_____

    // Attributs
    private int matricule;
    private String nom;
    private String prenom;
    private double salaire;
    private String role; // Caissier, ChefRayon, Directeur, Comptable, Magasinier

    // Constructeur d'initialisation
    public Employe(int pMatricule, String pNom, String pPrenom, double pSalaire) {
        matricule = pMatricule;
        nom = pNom;
        prenom = pPrenom;
        salaire = pSalaire;
        role = "Employe";
    }

    // Affichage des infos sur l'employé
    public void afficherInformations() {
        System.out.println("Employé : " + prenom + " " + nom
                + " | Matricule : " + matricule
                + " | Rôle : " + role
                + " | Salaire : " + salaire + " €");
    }

    // Calcul de salaire avec prime
    public double calculerPrime(double pPrime) {
        if (pPrime > 0) {
            salaire = salaire + pPrime;
        }
        return salaire;
    }

    // Affichage du rôle de l'employé
    public void afficherRole() {
        System.out.println("Rôle : " + role + " | Employé : " + prenom + " " + nom + " | Matricule : " + matricule);
    }

    // toString
    @Override
    public String toString() {
        return prenom + " " + nom + " (Matricule: " + matricule + ", Rôle: " + role + ", Salaire: " + salaire + " €)";
    }

    // Getters
    public int getMatricule() { return matricule; }
    public String getNom()    { return nom; }
    public String getPrenom() { return prenom; }
    public double getSalaire(){ return salaire; }
    public String getRole()   { return role; }

    // Setters
    public void setSalaire(double salaire) { this.salaire = salaire; }
    public void setRole(String role)       { this.role = role; }
}
