package model;

public class Utilisateur {

	// _____==== EXERCICE 11 : GESTION DES UTILISATEURS ET ACCÈS AUX MODULES ====_____

	// Rôles possibles 
	public static final String directeur = "Directeur";
	public static final String caissier = "Caissier";
	public static final String chefRayon = "ChefRayon";
	public static final String comptable = "Comptable";
	public static final String magasinier = "Magasinier";

	// Attributs
	private String login;
	private String motDePasse;
	private Employe employe;
	private String role;

	// Constructeur d'initialisation
	public Utilisateur(String pLogin, String pMotDePasse, Employe pEmploye) {
		login = pLogin;
		motDePasse = pMotDePasse;
		employe = pEmploye;
		role = pEmploye.getRole(); 
	}

	// Connexion : vérifie les identifiants
	public boolean seConnecter(String pLogin, String pMotDePasse) {
		if (login.equals(pLogin) && motDePasse.equals(pMotDePasse)) {
			System.out
					.println("Connexion réussie : " + employe.getPrenom() + " " + employe.getNom() + " [" + role + "]");
			return true;
		} else {
			System.out.println("Identifiants incorrects.");
			return false;
		}
	}

	// Vérification du rôle (pour contrôle d'accès aux modules)
	public boolean verifierRole(String pRole) {
		return role.equalsIgnoreCase(pRole);
	}

	// Modifier le mot de passe
	public boolean changerMotDePasse(String ancienMdp, String nouveauMotDePasse) {
		if (motDePasse.equals(ancienMdp)) {
			if (nouveauMotDePasse != null && nouveauMotDePasse.length() >= 4) {
				motDePasse = nouveauMotDePasse;
				System.out.println("Mot de passe modifié avec succès.");
				return true;
			}
			System.out.println("Le nouveau mot de passe doit avoir au moins 4 caractères.");
		} else {
			System.out.println("Ancien mot de passe incorrect.");
		}
		return false;
	}

	
	
	// Getters
	public String getLogin() {
		return login;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public Employe getEmploye() {
		return employe;
	}

	public String getRole() {
		return role;
	}
}
