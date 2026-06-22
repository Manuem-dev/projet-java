package model;

public class Utilisateur {

    // _____==== EXERCICE 11 : GESTION DES UTILISATEURS ET ACCÈS AUX MODULES ====_____

    // Attributs
    private String login;
    private String motDePasse;
    private Employe employe;
    private String role;
    
    // Méthodes
    
    // Constructeur d'initialisation
	public Utilisateur(String pLogin, String pMotDePasse, Employe pEmploye, String pRole) {
		login = pLogin;
		motDePasse = pMotDePasse;
		employe = pEmploye;
		role = pRole;
	}

    // Connexion
    public boolean seConnecter(String pLogin,String pMotDePasse){
    	if (login.equals(pLogin) && motDePasse.equals(pMotDePasse)) {
			return true;
		} else {
			return false;
		}
    }

    // Vérification du rôle
    public boolean verifierRole(String pRole){
        if (role.equals(pRole)) {
        	
        	return true;
			
		} else {
			return false;
		}
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
