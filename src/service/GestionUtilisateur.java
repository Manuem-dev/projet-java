package service;

import java.util.ArrayList;
import model.Employe;
import model.Utilisateur;

public class GestionUtilisateur {

    // _____==== EXERCICE 11 : GESTION DES UTILISATEURS ET ACCÈS ====_____

    private ArrayList<Utilisateur> listeUtilisateurs = new ArrayList<>();
    private Utilisateur utilisateurConnecte = null;

    // Créer un compte utilisateur pour un employé
    public Utilisateur creerUtilisateur(String login, String motDePasse, Employe employe) {
        if (rechercherParLogin(login) != null) {
            System.out.println("Ce login est déjà utilisé : " + login);
            return null;
        }
        Utilisateur u = new Utilisateur(login, motDePasse, employe);
        listeUtilisateurs.add(u);
        System.out.println("Compte créé pour : " + employe.getPrenom() + " " + employe.getNom()
                + " [login: " + login + "]");
        return u;
    }

    // Connexion
    public Utilisateur connecter(String login, String motDePasse) {
        Utilisateur u = rechercherParLogin(login);
        if (u != null && u.seConnecter(login, motDePasse)) {
            utilisateurConnecte = u;
            return u;
        }
        System.out.println("Connexion échouée.");
        return null;
    }

    // Déconnexion
    public void deconnecter() {
        if (utilisateurConnecte != null) {
            System.out.println("Déconnexion de : " + utilisateurConnecte.getLogin());
            utilisateurConnecte = null;
        }
    }

    // Vérifier l'accès à un module
    public boolean verifierAcces(String roleRequis) {
        if (utilisateurConnecte == null) {
            System.out.println("Aucun utilisateur connecté.");
            return false;
        }
        boolean acces = utilisateurConnecte.verifierRole(roleRequis);
        if (!acces) {
            System.out.println("Accès refusé : " + roleRequis + " requis (vous êtes : "
                    + utilisateurConnecte.getRole() + ")");
        }
        return acces;
    }

    // Rechercher par login
    public Utilisateur rechercherParLogin(String login) {
        for (Utilisateur u : listeUtilisateurs) {
            if (u.getLogin().equalsIgnoreCase(login)) return u;
        }
        return null;
    }

    // Supprimer un utilisateur
    public boolean supprimerUtilisateur(String login) {
        Utilisateur u = rechercherParLogin(login);
        if (u != null) {
            listeUtilisateurs.remove(u);
            System.out.println("Utilisateur supprimé : " + login);
            return true;
        }
        return false;
    }

    // Getters
    public ArrayList<Utilisateur> getListeUtilisateurs()   { return listeUtilisateurs; }
    public Utilisateur getUtilisateurConnecte()             { return utilisateurConnecte; }
}
