package service;

import java.util.ArrayList;
import model.Client;


public class GestionClient {

	// _____==== EXERCICE 6 : GESTION DES CLIENTS ET FIDÉLITÉ ====_____

	private ArrayList<Client> listeClients = new ArrayList<>();
	private int numeroClient = 1;

	// Ajouter un client
	public Client ajouterClient(String nom, String telephone) {
		Client client = new Client(numeroClient++, nom, telephone);
		listeClients.add(client);
		System.out.println("Client ajouté : " + nom + " (n°" + client.getNumeroClient() + ")");
		return client;
	}

	// Rechercher un client par numéro
	public Client rechercherClient(int numeroClient) {
		for (Client client : listeClients) {
			if (client.getNumeroClient() == numeroClient)
				return client;
		}
		return null;
	}

	// Rechercher par nom
	public ArrayList<Client> rechercherParNom(String nom) {
		ArrayList<Client> resultats = new ArrayList<>();
		String motCle = nom.trim().toLowerCase();
		for (Client client : listeClients) {
			if (client.getNom().toLowerCase().contains(motCle))
				resultats.add(client);
		}
		return resultats;
	}

	// Supprimer un client
	public boolean supprimerClient(int numeroClient) {
		Client client = rechercherClient(numeroClient);
		if (client != null) {
			listeClients.remove(client);
			System.out.println("Client supprimé : " + client.getNom());
			return true;
		}
		System.out.println("Client introuvable (n°" + numeroClient + ")");
		return false;
	}

	// Afficher tous les clients
	public void afficherTousLesClients() {
		System.out.println("=== LISTE DES CLIENTS (" + listeClients.size() + ") ===");
		for (Client client : listeClients) {
			client.afficherClient();
		}
	}

	// Clients avec le plus de points (top fidélité)
	public ArrayList<Client> getClientsFidelite(int minPoints) {
		ArrayList<Client> fideles = new ArrayList<>();
		for (Client client : listeClients) {
			if (client.getPointsFidelite() >= minPoints)
				fideles.add(client);
		}
		return fideles;
	}

	// Getters
	public ArrayList<Client> getListeClients() {
		return listeClients;
	}
}
