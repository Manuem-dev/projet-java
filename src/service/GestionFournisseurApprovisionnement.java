package service;

import java.util.Date;
import java.util.ArrayList;

import model.CommandeFournisseur;
import model.Fournisseur;
import model.Magasinier;
import model.Produit;

public class GestionFournisseurApprovisionnement {

    // _____==== EXERCICE 2 : GESTION FOURNISSEURS ET APPROVISIONNEMENT ====_____

    private ArrayList<Fournisseur> listeFournisseurs = new ArrayList<>();
    private ArrayList<CommandeFournisseur> listeCommandes = new ArrayList<>();
    private int numeroCommandeAuto = 1;

    // ==================== FOURNISSEURS ====================

    public void ajouterFournisseur(String code, String nom, String telephone, String adresse) {
        if (rechercherFournisseur(code) != null) {
            System.out.println("Fournisseur avec le code " + code + " déjà existant.");
            return;
        }
        listeFournisseurs.add(new Fournisseur(code, nom, telephone, adresse));
        System.out.println("Fournisseur ajouté : " + nom);
    }

    public Fournisseur rechercherFournisseur(String code) {
        for (Fournisseur f : listeFournisseurs) {
            if (f.getCode().equalsIgnoreCase(code)) return f;
        }
        return null;
    }

    public boolean supprimerFournisseur(String code) {
        Fournisseur f = rechercherFournisseur(code);
        if (f != null) {
            listeFournisseurs.remove(f);
            System.out.println("Fournisseur supprimé : " + f.getNom());
            return true;
        }
        System.out.println("Fournisseur introuvable : " + code);
        return false;
    }

    // ==================== COMMANDES ====================

    public CommandeFournisseur creerCommande(String codeFournisseur, ArrayList<Produit> produits) {
        Fournisseur f = rechercherFournisseur(codeFournisseur);
        if (f == null) {
            System.out.println("Fournisseur introuvable : " + codeFournisseur);
            return null;
        }
        CommandeFournisseur cmd = new CommandeFournisseur(numeroCommandeAuto++, new Date(), f, produits);
        listeCommandes.add(cmd);
        System.out.println("Commande n°" + cmd.getNumeroCommande() + " créée pour " + f.getNom());
        return cmd;
    }

    public void validerCommande(int numeroCommande) {
        CommandeFournisseur cmd = rechercherCommande(numeroCommande);
        if (cmd != null) cmd.validerCommande();
        else System.out.println("Commande introuvable : " + numeroCommande);
    }

    public void recevoirCommande(int numeroCommande, Magasinier magasinier) {
        CommandeFournisseur cmd = rechercherCommande(numeroCommande);
        if (cmd == null) {
            System.out.println("Commande introuvable : " + numeroCommande);
            return;
        }
        // Incrémenter le stock de chaque produit de la commande
        for (Produit p : cmd.getListeProduits()) {
            magasinier.mettreAJourStock(p, p.getQuantiteStock(), true);
        }
        cmd.marquerLivree();
        magasinier.receptionnerLivraison(cmd);
    }

    public void annulerCommande(int numeroCommande) {
        CommandeFournisseur cmd = rechercherCommande(numeroCommande);
        if (cmd != null) cmd.annulerCommande();
        else System.out.println("Commande introuvable : " + numeroCommande);
    }

    public CommandeFournisseur rechercherCommande(int numeroCommande) {
        for (CommandeFournisseur c : listeCommandes) {
            if (c.getNumeroCommande() == numeroCommande) return c;
        }
        return null;
    }

    // Getters
    public ArrayList<Fournisseur> getListeFournisseurs()           { return listeFournisseurs; }
    public ArrayList<CommandeFournisseur> getListeCommandes()      { return listeCommandes; }
}
