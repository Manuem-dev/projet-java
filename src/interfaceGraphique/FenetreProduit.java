package interfaceGraphique;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import service.GestionProduit;
import model.*;

/**
 * Panneau de gestion des produits.
 * Le formulaire affiche des champs dynamiques selon le type de produit sélectionné
 * (Artisanal, Électronique, Frais) via un CardLayout intégré — sans pop-ups secondaires.
 */
public class FenetreProduit extends JPanel {

	private static final Color BG       = new Color(0xF5F5F5);
	private static final Color PRIMARY  = new Color(0x1565C0);
	private static final Color ACCENT   = new Color(0x0D47A1);
	private static final Color TEXT_FG  = new Color(0x212121);
	private static final Color RED_CLR  = new Color(0xC62828);
	private static final Color GRAY_CLR = new Color(0x757575);
	private static final Font  FONT_LBL  = new Font("Segoe UI", Font.PLAIN,  13);
	private static final Font  FONT_TTL  = new Font("Segoe UI", Font.BOLD,   16);
	private static final Font  FONT_CARD = new Font("Segoe UI", Font.BOLD,   13);

	private GestionProduit gestionProduit;
	private DefaultTableModel tableModel;
	private JTable table;
	private JTextField tfRecherche;

	//  Champs communs 
	private JTextField tfRef, tfDesignation, tfQte, tfPrixAchat, tfPrixVente;
	private JLabel     lblType;
	private String     selectedType = null;

	//  Panneau de champs spécifiques (CardLayout) 
	private JPanel        specificPanel;
	private CardLayout    specificLayout;

	// Artisanal
	private JTextField tfCateg, tfArtisan, tfOrigine;
	// Électronique
	private JTextField tfMarque;
	private JSpinner   spGarantie;
	// Frais
	private JTextField tfTemp, tfDate;


	public FenetreProduit(GestionProduit gestionProduit) {
		this.gestionProduit = gestionProduit;
		this.setBackground(BG);
		setLayout(new BorderLayout(0, 0));
		buildUI();
		refreshTable(gestionProduit.getListeDesProduits());
	}

	//  Construction de l'interface

	private void buildUI() {

		//  En-tête 
		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
		header.setBackground(PRIMARY);
		header.setBorder(new EmptyBorder(10, 16, 10, 16));
		JLabel titre = new JLabel("📦  Gestion des Produits");
		titre.setFont(FONT_TTL);
		titre.setForeground(Color.WHITE);
		header.add(titre);
		add(header, BorderLayout.NORTH);

		//  Tableau + barre de recherche 
		String[] cols = { "Réf.", "Désignation", "Type", "Qté stock", "Prix achat", "Prix vente", "Marge" };
		tableModel = new DefaultTableModel(cols, 0) {
			public boolean isCellEditable(int r, int c) { return false; }
		};
		table = new JTable(tableModel);
		table.setFont(FONT_LBL);
		table.setRowHeight(24);
		table.getTableHeader().setBackground(PRIMARY);
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(FONT_LBL.deriveFont(Font.BOLD));
		table.setSelectionBackground(new Color(0xBBDEFB));

		JPanel barreRecherche = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
		barreRecherche.setBackground(BG);
		tfRecherche = new JTextField(22);
		tfRecherche.setFont(FONT_LBL);
		JButton btnRech = blueBtn("🔍 Rechercher");
		JButton btnTout = blueBtn("Tout afficher");
		btnRech.addActionListener(e -> {
			String mot = tfRecherche.getText().trim();
			refreshTable(mot.isEmpty()
					? gestionProduit.getListeDesProduits()
					: gestionProduit.rechercherParDesignation(mot));
		});
		btnTout.addActionListener(e -> {
			tfRecherche.setText("");
			refreshTable(gestionProduit.getListeDesProduits());
		});
		barreRecherche.add(new JLabel("Recherche :"));
		barreRecherche.add(tfRecherche);
		barreRecherche.add(btnRech);
		barreRecherche.add(btnTout);

		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setBackground(BG);
		tablePanel.add(barreRecherche, BorderLayout.NORTH);
		tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(tablePanel, BorderLayout.CENTER);

		//  Formulaire bas 
		JPanel formOuter = new JPanel(new BorderLayout());
		formOuter.setBackground(Color.WHITE);
		formOuter.setBorder(BorderFactory.createTitledBorder(
				new LineBorder(new Color(0xBDBDBD), 1, true),
				" Ajouter / Modifier un produit ",
				TitledBorder.LEFT, TitledBorder.TOP,
				FONT_LBL.deriveFont(Font.BOLD), PRIMARY));

		//  Ligne communes : Type | Réf 
		JPanel rowCommon = new JPanel(new GridBagLayout());
		rowCommon.setBackground(Color.WHITE);
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(5, 7, 3, 7);
		g.fill   = GridBagConstraints.HORIZONTAL;

		// Ligne 0 : type + réf
		g.gridx = 0; g.gridy = 0;
		rowCommon.add(lbl("Type de produit :"), g);

		g.gridx = 1;
		lblType = new JLabel("— cliquez sur Choisir le type →");
		lblType.setFont(FONT_LBL.deriveFont(Font.ITALIC));
		lblType.setForeground(new Color(0x9E9E9E));
		rowCommon.add(lblType, g);

		g.gridx = 2;
		JButton btnChoisirType = blueBtn("Choisir le type…");
		btnChoisirType.addActionListener(e -> choisirTypeProduit());
		rowCommon.add(btnChoisirType, g);

		g.gridx = 3;
		rowCommon.add(lbl("Réf. :"), g);

		g.gridx = 4;
		tfRef = new JTextField(6);
		rowCommon.add(tfRef, g);

		// Ligne 1 : désignation
		g.gridx = 0; g.gridy = 1;
		rowCommon.add(lbl("Désignation :"), g);
		g.gridx = 1; g.gridwidth = 4;
		tfDesignation = new JTextField(26);
		rowCommon.add(tfDesignation, g);
		g.gridwidth = 1;

		// Ligne 2 : qté | prix achat | prix vente
		g.gridx = 0; g.gridy = 2;
		rowCommon.add(lbl("Quantité :"), g);
		g.gridx = 1;
		tfQte = new JTextField(7);
		rowCommon.add(tfQte, g);
		g.gridx = 2;
		rowCommon.add(lbl("Prix achat (€) :"), g);
		g.gridx = 3;
		tfPrixAchat = new JTextField(7);
		rowCommon.add(tfPrixAchat, g);
		g.gridx = 4;
		rowCommon.add(lbl("Prix vente (€) :"), g);

		// Ligne 3 : champ prix vente (séparé pour éviter bug)
		g.gridx = 0; g.gridy = 3;
		rowCommon.add(lbl("Prix vente (€) :"), g);
		g.gridx = 1;
		tfPrixVente = new JTextField(7);
		rowCommon.add(tfPrixVente, g);

		formOuter.add(rowCommon, BorderLayout.NORTH);

		//  Panneau spécifique (CardLayout) 
		specificLayout = new CardLayout();
		specificPanel  = new JPanel(specificLayout);
		specificPanel.setBackground(Color.WHITE);
		specificPanel.setBorder(new EmptyBorder(2, 0, 2, 0));

		// Carte VIDE (aucun type choisi)
		JPanel carteVide = new JPanel();
		carteVide.setBackground(Color.WHITE);
		JLabel lblVide = new JLabel("  ↑  Choisissez d'abord un type de produit pour voir les champs spécifiques");
		lblVide.setFont(FONT_LBL.deriveFont(Font.ITALIC));
		lblVide.setForeground(new Color(0xBDBDBD));
		carteVide.add(lblVide);
		specificPanel.add(carteVide, "VIDE");

		// Carte ARTISANAL
		JPanel carteArtisanal = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 6));
		carteArtisanal.setBackground(new Color(0xFFFDE7));
		carteArtisanal.setBorder(BorderFactory.createTitledBorder(
				new LineBorder(new Color(0xF9A825), 1, true),
				" 📦 Artisanal ", TitledBorder.LEFT, TitledBorder.TOP,
				FONT_LBL.deriveFont(Font.BOLD), new Color(0xF57F17)));
		tfCateg   = new JTextField(10);
		tfArtisan = new JTextField(12);
		tfOrigine = new JTextField(10);
		carteArtisanal.add(lbl("Catégorie :"));
		carteArtisanal.add(tfCateg);
		carteArtisanal.add(lbl("Artisan :"));
		carteArtisanal.add(tfArtisan);
		carteArtisanal.add(lbl("Origine :"));
		carteArtisanal.add(tfOrigine);
		specificPanel.add(carteArtisanal, "ARTISANAL");

		// Carte ELECTRONIQUE
		JPanel carteElec = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 6));
		carteElec.setBackground(new Color(0xE8EAF6));
		carteElec.setBorder(BorderFactory.createTitledBorder(
				new LineBorder(new Color(0x3949AB), 1, true),
				" ⚡ Électronique ", TitledBorder.LEFT, TitledBorder.TOP,
				FONT_LBL.deriveFont(Font.BOLD), new Color(0x1A237E)));
		tfMarque   = new JTextField(14);
		spGarantie = new JSpinner(new SpinnerNumberModel(12, 0, 120, 1));
		spGarantie.setFont(FONT_LBL);
		carteElec.add(lbl("Marque :"));
		carteElec.add(tfMarque);
		carteElec.add(lbl("Garantie (mois) :"));
		carteElec.add(spGarantie);
		specificPanel.add(carteElec, "ELECTRONIQUE");

		// Carte FRAIS
		JPanel carteFrais = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 6));
		carteFrais.setBackground(new Color(0xE0F7FA));
		carteFrais.setBorder(BorderFactory.createTitledBorder(
				new LineBorder(new Color(0x00838F), 1, true),
				" ❄️ Produit Frais ", TitledBorder.LEFT, TitledBorder.TOP,
				FONT_LBL.deriveFont(Font.BOLD), new Color(0x006064)));
		tfTemp = new JTextField("4.0", 7);
		tfDate = new JTextField(LocalDate.now().plusMonths(1).toString(), 12);
		carteFrais.add(lbl("Température (°C) :"));
		carteFrais.add(tfTemp);
		carteFrais.add(lbl("Date péremption (AAAA-MM-JJ) :"));
		carteFrais.add(tfDate);
		specificPanel.add(carteFrais, "FRAIS");

		// Afficher VIDE par défaut
		specificLayout.show(specificPanel, "VIDE");
		formOuter.add(specificPanel, BorderLayout.CENTER);

		//  Boutons action 
		JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
		btnRow.setBackground(Color.WHITE);
		JButton btnAjouter   = blueBtn("➕ Ajouter");
		JButton btnModifier  = blueBtn("✏️ Modifier");
		JButton btnSupprimer = redBtn("🗑️ Supprimer");
		JButton btnVider     = grayBtn("Vider");
		btnAjouter.addActionListener(e  -> ajouterProduit());
		btnModifier.addActionListener(e -> modifierProduit());
		btnSupprimer.addActionListener(e -> supprimerProduit());
		btnVider.addActionListener(e    -> viderFormulaire());
		btnRow.add(btnAjouter);
		btnRow.add(btnModifier);
		btnRow.add(btnSupprimer);
		btnRow.add(btnVider);
		formOuter.add(btnRow, BorderLayout.SOUTH);

		add(formOuter, BorderLayout.SOUTH);

		// Sélection dans la table → chargement dans le formulaire
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) { chargerLigne(); }
		});
	}

	//  Sélection du type et basculement de carte

	private void choisirTypeProduit() {
		String[] labels = { "📦 Artisanal", "⚡ Électronique", "❄️ Frais" };
		String[] keys   = { "Artisanal",    "Électronique",    "Frais"   };

		JList<String> list = new JList<>(labels);
		list.setFont(FONT_LBL.deriveFont(15f));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		int defIdx = selectedType == null ? 0
				: selectedType.equals("Artisanal")    ? 0
				: selectedType.equals("Électronique") ? 1 : 2;
		list.setSelectedIndex(defIdx);

		JPanel panel = new JPanel(new BorderLayout(0, 8));
		panel.add(new JLabel("Sélectionnez le type de produit :"), BorderLayout.NORTH);
		panel.add(new JScrollPane(list), BorderLayout.CENTER);

		int res = JOptionPane.showConfirmDialog(this, panel, "Type de produit",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (res == JOptionPane.OK_OPTION && list.getSelectedIndex() >= 0) {
			selectedType = keys[list.getSelectedIndex()];
			lblType.setText(labels[list.getSelectedIndex()]);
			lblType.setForeground(PRIMARY);
			lblType.setFont(FONT_CARD);
			// Basculer la carte spécifique
			switch (selectedType) {
				case "Artisanal":    specificLayout.show(specificPanel, "ARTISANAL");    break;
				case "Électronique": specificLayout.show(specificPanel, "ELECTRONIQUE"); break;
				case "Frais":        specificLayout.show(specificPanel, "FRAIS");        break;
			}
		}
	}

	//  Rafraîchissement du tableau 

	private void refreshTable(ArrayList<Produit> liste) {
		tableModel.setRowCount(0);
		for (Produit p : liste) {
			String type = (p instanceof ProduitFrais)        ? "Frais"
					     : (p instanceof ProduitElectronique) ? "Électronique" : "Artisanal";
			tableModel.addRow(new Object[]{
				p.getReference(), p.getDesignation(), type, p.getQuantiteStock(),
				String.format("%.2f €", p.getPrixAchat()),
				String.format("%.2f €", p.getPrixVente()),
				String.format("%.2f €", p.calculerMarge())
			});
		}
	}

	//  Logique CRUD 

	private void ajouterProduit() {
		if (selectedType == null) {
			JOptionPane.showMessageDialog(this,
					"Veuillez d'abord choisir le type de produit.",
					"Type manquant", JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
			int    ref = Integer.parseInt(tfRef.getText().trim());
			int    qte = Integer.parseInt(tfQte.getText().trim());
			double pa  = Double.parseDouble(tfPrixAchat.getText().trim().replace(",", "."));
			double pv  = Double.parseDouble(tfPrixVente.getText().trim().replace(",", "."));
			String des = tfDesignation.getText().trim();
			if (des.isEmpty()) throw new IllegalArgumentException("La désignation est vide.");

			switch (selectedType) {
				case "Artisanal":
					gestionProduit.ajoutProduitArtisanal(ref, qte, pa, pv, des,
							tfCateg.getText().trim()
							);
					break;

				case "Électronique":
					int gar = (int) spGarantie.getValue();
					gestionProduit.ajoutProduitElectronique(ref, qte, pa, pv, des,
							tfMarque.getText().trim(), gar);
					break;

				case "Frais":
					double    temp = Double.parseDouble(tfTemp.getText().trim().replace(",", "."));
					LocalDate date = LocalDate.parse(tfDate.getText().trim());
					gestionProduit.ajoutProduitFrais(ref, qte, pa, pv, des, temp, date);
					break;
			}
			refreshTable(gestionProduit.getListeDesProduits());
			viderFormulaire();
			JOptionPane.showMessageDialog(this, "Produit ajouté avec succès !", "Succès",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (DateTimeParseException ex) {
			JOptionPane.showMessageDialog(this,
					"Format de date invalide. Utilisez AAAA-MM-JJ (ex : 2026-12-31).",
					"Erreur de date", JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this,
					"Valeur numérique invalide : " + ex.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this,
					"Erreur : " + ex.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void modifierProduit() {
		try {
			int    ref = Integer.parseInt(tfRef.getText().trim());
			int    qte = Integer.parseInt(tfQte.getText().trim());
			double pa  = Double.parseDouble(tfPrixAchat.getText().trim().replace(",", "."));
			double pv  = Double.parseDouble(tfPrixVente.getText().trim().replace(",", "."));
			String des = tfDesignation.getText().trim();
			if (des.isEmpty()) throw new IllegalArgumentException("La désignation est vide.");
			gestionProduit.modifierProduit(ref, qte, pa, pv, des);
			refreshTable(gestionProduit.getListeDesProduits());
			viderFormulaire();
			JOptionPane.showMessageDialog(this, "Produit modifié avec succès !", "Succès",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this,
					"Valeur numérique invalide : " + ex.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this,
					"Erreur : " + ex.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void supprimerProduit() {
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(this, "Sélectionnez un produit dans la liste.",
					"Info", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int ref = (int) tableModel.getValueAt(row, 0);
		if (JOptionPane.showConfirmDialog(this,
				"Supprimer ce produit définitivement ?",
				"Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			gestionProduit.supprimerProduit(ref);
			refreshTable(gestionProduit.getListeDesProduits());
			viderFormulaire();
		}
	}

	/**
	 * Charge la ligne sélectionnée dans le formulaire, y compris les champs
	 * spécifiques au type (via cast de l'objet réel).
	 */
	private void chargerLigne() {
		int row = table.getSelectedRow();
		if (row < 0) return;

		int    ref = (int) tableModel.getValueAt(row, 0);
		String des = (String) tableModel.getValueAt(row, 1);
		String type = (String) tableModel.getValueAt(row, 2);

		tfRef.setText(String.valueOf(ref));
		tfDesignation.setText(des);
		tfQte.setText(String.valueOf(tableModel.getValueAt(row, 3)));
		tfPrixAchat.setText(((String) tableModel.getValueAt(row, 4)).replace(" €", ""));
		tfPrixVente.setText(((String) tableModel.getValueAt(row, 5)).replace(" €", ""));

		// Mettre à jour le type et la carte
		selectedType = type;
		switch (type) {
			case "Frais":
				lblType.setText("❄️ Frais");
				specificLayout.show(specificPanel, "FRAIS");
				// Pré-remplir les champs Frais
				ProduitFrais pf = (ProduitFrais) gestionProduit.rechercherProduit(ref);
				if (pf != null) {
					tfTemp.setText(String.valueOf(pf.getTemperatureConservation()));
					tfDate.setText(pf.getDatePeremption().toString());
				}
				break;
			case "Électronique":
				lblType.setText("⚡ Électronique");
				specificLayout.show(specificPanel, "ELECTRONIQUE");
				// Pré-remplir les champs Électronique
				ProduitElectronique pe = (ProduitElectronique) gestionProduit.rechercherProduit(ref);
				if (pe != null) {
					tfMarque.setText(pe.getMarque());
					spGarantie.setValue(pe.getGarantie());
				}
				break;
			default:
				lblType.setText("📦 Artisanal");
				specificLayout.show(specificPanel, "ARTISANAL");
				// Pré-remplir les champs Artisanal
				ProduitArtisanal pa = (ProduitArtisanal) gestionProduit.rechercherProduit(ref);
				if (pa != null) {
					tfCateg.setText(pa.getCategorie() != null ? pa.getCategorie() : "");
					
				}
				break;
		}
		lblType.setForeground(PRIMARY);
		lblType.setFont(FONT_CARD);
	}

	private void viderFormulaire() {
		tfRef.setText("");
		tfDesignation.setText("");
		tfQte.setText("");
		tfPrixAchat.setText("");
		tfPrixVente.setText("");
		selectedType = null;
		lblType.setText("— cliquez sur Choisir le type →");
		lblType.setForeground(new Color(0x9E9E9E));
		lblType.setFont(FONT_LBL.deriveFont(Font.ITALIC));
		// Champs spécifiques
		tfCateg.setText("");   tfArtisan.setText(""); tfOrigine.setText("");
		tfMarque.setText("");  spGarantie.setValue(12);
		tfTemp.setText("4.0"); tfDate.setText(LocalDate.now().plusMonths(1).toString());
		specificLayout.show(specificPanel, "VIDE");
		table.clearSelection();
	}

	//  Helpers UI 

	private JLabel lbl(String t) {
		JLabel l = new JLabel(t);
		l.setFont(FONT_LBL);
		l.setForeground(TEXT_FG);
		return l;
	}

	private JButton blueBtn(String t) { return styledBtn(t, PRIMARY, ACCENT); }
	private JButton redBtn(String t)  { return styledBtn(t, RED_CLR, new Color(0xB71C1C)); }
	private JButton grayBtn(String t) { return styledBtn(t, GRAY_CLR, new Color(0x616161)); }

	private JButton styledBtn(String t, Color bg, Color hover) {
		JButton b = new JButton(t) {
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		b.setFont(FONT_LBL);
		b.setBackground(bg);
		b.setForeground(Color.WHITE);
		b.setContentAreaFilled(false);
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
			public void mouseExited(MouseEvent e)  { b.setBackground(bg); }
		});
		return b;
	}
}
