package model;
import java.time.LocalDate;
import java.util.ArrayList;

public class CommandeFournisseur {

    // _____==== EXERCICE 2 : GESTION FOURNISSEURS ET APPROVISIONNEMENT ====_____

    // Attributs
    private int numeroCommande;
    private LocalDate date; // Je pouvais aussi utiliser Date
    private Fournisseur fournisseur;
    private ArrayList <Produit> listeProduits ;
    private String etatCommande;
	
    // Constructeur d'initialisation
    public CommandeFournisseur(int pNumeroCommande, LocalDate pDate, Fournisseur pFournisseur,
			ArrayList<Produit> pListeProduits, String pEtatCommande) {
		
		numeroCommande = pNumeroCommande;
		date = pDate;
		fournisseur = pFournisseur;
		listeProduits = pListeProduits;
		etatCommande = pEtatCommande;
	}
    
    // Getters

	public int getNumeroCommande() {
		return numeroCommande;
	}

	public LocalDate getDate() {
		return date;
	}

	public Fournisseur getFournisseur() {
		return fournisseur;
	}

	public ArrayList<Produit> getListeProduits() {
		return listeProduits;
	}

	public String getEtatCommande() {
		return etatCommande;
	}
    
    



}
