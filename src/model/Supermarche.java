package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Supermarche {

    // _____==== EXERCICE 10 : TABLEAU DE BORD DIRECTION ====_____

    // Attributs
    private String nom;
    private ArrayList<Produit> produits;
    private ArrayList<Client> clients;
    private ArrayList<Employe> employes;
    private ArrayList<Rayon> rayons;
    private ArrayList<Vente> ventes;
    private ArrayList<Fournisseur> fournisseurs;
    private ArrayList<CommandeFournisseur> commandes;

    // Constructeur d'initialisation
    public Supermarche(String pNom) {
        nom         = pNom;
        produits    = new ArrayList<>();
        clients     = new ArrayList<>();
        employes    = new ArrayList<>();
        rayons      = new ArrayList<>();
        ventes      = new ArrayList<>();
        fournisseurs= new ArrayList<>();
        commandes   = new ArrayList<>();
    }

    // === GESTION DES PRODUITS ===
    public void ajouterProduit(Produit produit)         { produits.add(produit); }
    public void supprimerProduit(Produit produit)       { produits.remove(produit); }

    // === GESTION DES CLIENTS ===
    public void ajouterClient(Client client)            { clients.add(client); }
    public void supprimerClient(Client client)          { clients.remove(client); }

    public Client rechercherClient(int numeroClient) {
        for (Client c : clients) {
            if (c.getNumeroClient() == numeroClient) return c;
        }
        return null;
    }

    // === GESTION DES EMPLOYÉS ===
    public void ajouterEmploye(Employe employe)         { employes.add(employe); }
    public void supprimerEmploye(Employe employe)       { employes.remove(employe); }

    // === GESTION DES RAYONS ===
    public void ajouterRayon(Rayon rayon)               { rayons.add(rayon); }
    public void supprimerRayon(Rayon rayon)             { rayons.remove(rayon); }

    // === GESTION DES VENTES ===
    public void enregistrerVente(Vente vente)           { ventes.add(vente); }

    // === GESTION FOURNISSEURS ===
    public void ajouterFournisseur(Fournisseur f)       { fournisseurs.add(f); }
    public void ajouterCommande(CommandeFournisseur c)  { commandes.add(c); }

    // === CALCULS / STATISTIQUES ===

    // Chiffre d'affaires total
    public double calculerChiffreAffaires() {
        double ca = 0.0;
        for (Vente v : ventes) ca += v.calculerTotal();
        return ca;
    }

    // Afficher les produits en stock faible (sous le seuil)
    public ArrayList<Produit> afficherStockFaible(int seuil) {
        ArrayList<Produit> produitsAlerte = new ArrayList<>();
        for (Produit p : produits) {
            if (p.getQuantiteStock() <= seuil) {
                produitsAlerte.add(p);
                System.out.println("[ALERTE STOCK] " + p.getDesignation() + " : " + p.getQuantiteStock() + " unités");
            }
        }
        return produitsAlerte;
    }

    // Afficher les produits frais périmés
    public ArrayList<Produit> afficherProduitsExpires() {
        ArrayList<Produit> expires = new ArrayList<>();
        for (Produit produit : produits) {
            if (produit instanceof ProduitFrais) {
                ProduitFrais pf = (ProduitFrais) produit;
                if (pf.estPerime()) {
                    expires.add(pf);
                    System.out.println("[PÉRIMÉ] " + pf.getDesignation()
                            + " | Exp. : " + pf.getDatePeremption());
                }
            }
        }
        System.out.println("Total produits périmés : " + expires.size());
        return expires;
    }

    // Getters
    public String getNom()                              { return nom; }
    public ArrayList<Produit> getProduits()             { return produits; }
    public ArrayList<Client> getClients()               { return clients; }
    public ArrayList<Employe> getEmployes()             { return employes; }
    public ArrayList<Rayon> getRayons()                 { return rayons; }
    public ArrayList<Vente> getVentes()                 { return ventes; }
    public ArrayList<Fournisseur> getFournisseurs()     { return fournisseurs; }
    public ArrayList<CommandeFournisseur> getCommandes(){ return commandes; }
}
