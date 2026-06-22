package model;

public class Fournisseur {

    // _____==== EXERCICE 2 : GESTION FOURNISSEURS ET APPROVISIONNEMENT ====_____

    // Attributs
    String code,nom,telephone,adresse;

    // Méthodes

    // Constructeur d'initialisation
    public Fournisseur(String pCode,String pNom,String pTelephone, String pAdresse){
        code = pCode;
        nom = pNom;
        telephone = pTelephone;
        adresse = pAdresse;
    }

    // Fournissement de produit
    public void ajouterProduit(Produit produit,int pQuantiteAFournir){
        if (pQuantiteAFournir>0) {
            produit.setQuantiteStock(produit.getQuantiteStock() + pQuantiteAFournir);
        } else {
            System.out.println("Valeur entrée invalide");
        }
        
    }

    // Afficher
    public void afficher() {
		System.out.println("Fournisseur [code=" + code + ", nom=" + nom + ", telephone=" + telephone + ", adresse=" + adresse + "]");
	}

}
