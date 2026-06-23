package service;

import java.util.ArrayList;
import model.Client;
import model.Vente;

public class GestionClient {

    // _____==== EXERCICE 6 : GESTION DES CLIENTS ET FIDÉLITÉ ====_____

    private ArrayList<Client> listeClients = new ArrayList<>();
    private int numeroClientAuto = 1;

    // Ajouter un client
    public Client ajouterClient(String nom, String telephone) {
        Client client = new Client(numeroClientAuto++, nom, telephone);
        listeClients.add(client);
        System.out.println("Client ajouté : " + nom + " (n°" + client.getNumeroClient() + ")");
        return client;
    }

    // Rechercher un client par numéro
    public Client rechercherClient(int numeroClient) {
        for (Client c : listeClients) {
            if (c.getNumeroClient() == numeroClient) return c;
        }
        return null;
    }

    // Rechercher par nom
    public ArrayList<Client> rechercherParNom(String nom) {
        ArrayList<Client> resultats = new ArrayList<>();
        String motCle = nom.trim().toLowerCase();
        for (Client c : listeClients) {
            if (c.getNom().toLowerCase().contains(motCle)) resultats.add(c);
        }
        return resultats;
    }

    // Supprimer un client
    public boolean supprimerClient(int numeroClient) {
        Client c = rechercherClient(numeroClient);
        if (c != null) {
            listeClients.remove(c);
            System.out.println("Client supprimé : " + c.getNom());
            return true;
        }
        System.out.println("Client introuvable (n°" + numeroClient + ")");
        return false;
    }

    // Afficher tous les clients
    public void afficherTousLesClients() {
        System.out.println("=== LISTE DES CLIENTS (" + listeClients.size() + ") ===");
        for (Client c : listeClients) {
            c.afficherClient();
        }
    }

    // Clients avec le plus de points (top fidélité)
    public ArrayList<Client> getClientsFidelite(int minPoints) {
        ArrayList<Client> fideles = new ArrayList<>();
        for (Client c : listeClients) {
            if (c.getPointsFidelite() >= minPoints) fideles.add(c);
        }
        return fideles;
    }

    // Getters
    public ArrayList<Client> getListeClients() { return listeClients; }
}
