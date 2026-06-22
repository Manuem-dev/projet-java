package service;

import java.time.LocalDate;
import java.util.ArrayList;

import model.Produit;
import model.ProduitArtisanal;
import model.ProduitElectronique;
import model.ProduitFrais;

public class GestionProduit {
	
	// Liste de tous les produits du supermarché
	private ArrayList<Produit> listeDesProduits = new ArrayList<Produit>();
	
	// Méthodes d'ajout, de suppression,de modification et de recherche de produit
	
	// Ajout de produit Artisanal
	public void ajoutProduitArtisanal(int pReference,int pQuantiteStock,double pPrixAchat,double pPrixVente,String pDesignation) {
		boolean produitExisteDeja = false;
		for (Produit produit : listeDesProduits) {
			if (produit.getReference() == pReference) {
				produitExisteDeja = true;
				break;
			}
		}
		
		if (produitExisteDeja) {
			System.out.println("La référence de ce produit existe déjà,veuillez changer de référence");
		} else if(pQuantiteStock <= 0 || pPrixAchat <= 0 || pPrixVente <= 0 || pDesignation.trim().isEmpty()) {
			System.out.println("Erreur: veuillez vérifiez correctement les données entrées");
		} else {
			listeDesProduits.add(new ProduitArtisanal(pReference, pQuantiteStock, pPrixAchat, pPrixVente, pDesignation));
		}
		
	}
	
	// Ajout de produit electronique
	public void ajoutProduitElectronique(int pReference,int pQuantiteStock,double pPrixAchat,double pPrixVente,String pDesignation,String pMarque,int pGarantie) {
		boolean produitExisteDeja = false;
		for (Produit produit : listeDesProduits) {
			if (produit.getReference() == pReference) {
				produitExisteDeja = true;
				break;
			}
		}
		
		if (produitExisteDeja) {
			System.out.println("La référence de ce produit existe déjà,veuillez devoir changer de référence");
		} else if(pQuantiteStock <= 0 || pPrixAchat <= 0 || pPrixVente <= 0 || pGarantie < 0 ||  pDesignation.trim().isEmpty() || pMarque.trim().isEmpty()) {
			System.out.println("Erreur: veuillez vérifiez correctement les données entrées");
		} else {
			listeDesProduits.add(new ProduitElectronique(pReference, pQuantiteStock, pPrixAchat, pPrixVente, pDesignation, pMarque, pGarantie));
		}
		
	}
	
	// Ajout de produit frais
	public void ajoutProduitFrais(int pReference, int pQuantiteStock, double pPrixAchat, double pPrixVente, String pDesignation,double pTemperatureConservation,LocalDate pDatePeremption) {
		boolean produitExisteDeja = false;
		for (Produit produit : listeDesProduits) {
			if (produit.getReference() == pReference) {
				produitExisteDeja = true;
				break;
			}
		}
		
		if (produitExisteDeja) {
			System.out.println("La référence de ce produit existe déjà,veuillez changer de référence");
		} else if(pQuantiteStock <= 0 || pPrixAchat <= 0 || pPrixVente <= 0 || pDesignation.trim().isEmpty() || pDatePeremption.isBefore(LocalDate.now())) {
			System.out.println("Erreur: veuillez vérifiez correctement les données entrées");
		} else {
			listeDesProduits.add(new ProduitFrais(pReference, pQuantiteStock, pPrixAchat, pPrixVente, pDesignation, pTemperatureConservation, pDatePeremption));
		}
		
	}
	
	// Modification de produit Artisanal
	public void modifierProduitArtisanal(Produit pProduit,int pReference,int pQuantiteStock,double pPrixAchat,double pPrixVente,String pDesignation) {
		if (listeDesProduits.contains(pProduit)) {  
	        if (pQuantiteStock <= 0 || pPrixAchat <= 0 || pPrixVente <= 0 || pDesignation.trim().isEmpty()) {
	            System.out.println("Erreur: données invalides");
	        }else if (pReference != pProduit.getReference() && rechercherProduit(pReference) != null) {
	        	System.out.println("Cette nouvelle référence existe déjà !");
			} else {
				((ProduitArtisanal) pProduit).setReference(pReference);
	            ((ProduitArtisanal) pProduit).setQuantiteStock(pQuantiteStock);
	            ((ProduitArtisanal) pProduit).setPrixAchat(pPrixAchat);
	            ((ProduitArtisanal) pProduit).setPrixVente(pPrixVente);
	            ((ProduitArtisanal) pProduit).setDesignation(pDesignation);
	        }
	    } else {
	        System.out.println("Produit non retrouvé");
	    }
		
	}
	
	// Modification de produit electronique 
	
	public void modifierProduitElectronique(Produit pProduit,int pReference,int pQuantiteStock,double pPrixAchat,double pPrixVente,String pDesignation,String pMarque,int pGarantie) {
		if (listeDesProduits.contains(pProduit)) {  
	        if (pQuantiteStock <= 0 || pPrixAchat <= 0 || pPrixVente <= 0 || pDesignation.trim().isEmpty() || pGarantie < 0 || pMarque.trim().isEmpty()) {
	            System.out.println("Erreur: données invalides");
	        }else if (pReference != pProduit.getReference() && rechercherProduit(pReference) != null) {
	        	System.out.println("Cette nouvelle référence existe déjà !");
			} else {
				((ProduitElectronique) pProduit).setReference(pReference);
	            ((ProduitElectronique) pProduit).setQuantiteStock(pQuantiteStock);
	            ((ProduitElectronique) pProduit).setPrixAchat(pPrixAchat);
	            ((ProduitElectronique) pProduit).setPrixVente(pPrixVente);
	            ((ProduitElectronique) pProduit).setDesignation(pDesignation);
	            ((ProduitElectronique) pProduit).setMarque(pMarque);
	            ((ProduitElectronique) pProduit).setGarantie(pGarantie);
	        }
	    } else {
	        System.out.println("Produit non retrouvé");
	    }
		
	}
	
	// Modification de produit frais
	
	public void modifierProduitFrais(Produit pProduit,int pReference, int pQuantiteStock, double pPrixAchat, double pPrixVente, String pDesignation,double pTemperatureConservation,LocalDate pDatePeremption) {
		if (listeDesProduits.contains(pProduit)) {  
	        if (pQuantiteStock <= 0 || pPrixAchat <= 0 || pPrixVente <= 0 || pDesignation.trim().isEmpty() || pDatePeremption.isBefore(LocalDate.now())) {
	            System.out.println("Erreur: données invalides");
	        }else if (pReference != pProduit.getReference() && rechercherProduit(pReference) != null) {
	        	System.out.println("Cette nouvelle référence existe déjà !");
			} else {
				((ProduitFrais) pProduit).setReference(pReference);
				((ProduitFrais) pProduit).setQuantiteStock(pQuantiteStock);
	            ((ProduitFrais) pProduit).setPrixAchat(pPrixAchat);
	            ((ProduitFrais) pProduit).setPrixVente(pPrixVente);
	            ((ProduitFrais) pProduit).setDesignation(pDesignation);
	            ((ProduitFrais) pProduit).setDatePeremption(pDatePeremption);
	            ((ProduitFrais) pProduit).setTemperatureConservation(pTemperatureConservation);
	        }
	    } else {
	        System.out.println("Produit non retrouvé");
	    }
		
	}
	
	// Supression
	public void supprimerProduit(Produit pProduit) {
		if (listeDesProduits.contains(pProduit)) {
			listeDesProduits.remove(pProduit);
		} else {
			System.out.println("Produit non retrouvé");
		}
		
	}
	
	// Recherche 
	public Produit rechercherProduit(int pReference) {
		for (Produit produit : listeDesProduits) {
			if (pReference == produit.getReference()){
				return produit;
			}
		}
		return null;
	}

	
	// Getter
	public ArrayList<Produit> getListeDesProduits() {
		return listeDesProduits;
	}
	
	
	
	
}
