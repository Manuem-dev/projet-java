package model;

public class Utilisateur {

    // _____==== EXERCICE 11 : GESTION DES UTILISATEURS ET ACCÈS AUX MODULES ====_____

    // Rôles possibles (correspondant aux sous-classes d'Employe)
    public static final String ROLE_DIRECTEUR   = "Directeur";
    public static final String ROLE_CAISSIER    = "Caissier";
    public static final String ROLE_CHEF_RAYON  = "ChefRayon";
    public static final String ROLE_COMPTABLE   = "Comptable";
    public static final String ROLE_MAGASINIER  = "Magasinier";

    // Attributs
    private String login;
    private String motDePasse;
    private Employe employe;
    private String role;

    // Constructeur d'initialisation
    public Utilisateur(String pLogin, String pMotDePasse, Employe pEmploye) {
        login      = pLogin;
        motDePasse = pMotDePasse;
        employe    = pEmploye;
        role       = pEmploye.getRole(); // Rôle hérité de l'employé associé
    }

    // Connexion : vérifie les identifiants
    public boolean seConnecter(String pLogin, String pMotDePasse) {
        if (login.equals(pLogin) && motDePasse.equals(pMotDePasse)) {
            System.out.println("Connexion réussie : " + employe.getPrenom()
                    + " " + employe.getNom() + " [" + role + "]");
            return true;
        } else {
            System.out.println("Identifiants incorrects.");
            return false;
        }
    }

    // Vérification du rôle (pour contrôle d'accès aux modules)
    public boolean verifierRole(String pRole) {
        return role.equals(pRole);
    }

    // Modifier le mot de passe
    public boolean changerMotDePasse(String ancienMdp, String nouveauMdp) {
        if (motDePasse.equals(ancienMdp)) {
            if (nouveauMdp != null && nouveauMdp.length() >= 4) {
                motDePasse = nouveauMdp;
                System.out.println("Mot de passe modifié avec succès.");
                return true;
            }
            System.out.println("Le nouveau mot de passe doit avoir au moins 4 caractères.");
        } else {
            System.out.println("Ancien mot de passe incorrect.");
        }
        return false;
    }

    @Override
    public String toString() {
        return login + " [" + role + " - " + employe.getPrenom() + " " + employe.getNom() + "]";
    }

    // Getters
    public String getLogin()          { return login; }
    public String getMotDePasse()     { return motDePasse; }
    public Employe getEmploye()       { return employe; }
    public String getRole()           { return role; }
}
