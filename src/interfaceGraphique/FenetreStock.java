package interfaceGraphique;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import service.GestionProduit;
import service.GestionRayon;
import model.*;

/**
 * Fenêtre gestion des stocks et des rayons.
 */
public class FenetreStock extends JPanel {

	private static final Color BG = new Color(0xF5F5F5);
	private static final Color PRIMARY = new Color(0x1565C0);
	private static final Color ACCENT = new Color(0x0D47A1);
	private static final Color ORANGE = new Color(0xE65100);
	private static final Font FONT_LBL = new Font("Segoe UI", Font.PLAIN, 13);
	private static final Font FONT_TTL = new Font("Segoe UI", Font.BOLD, 16);

	private GestionProduit gestionProduit;
	private GestionRayon gestionRayon;

	private DefaultTableModel tableModelProduits;
	private DefaultTableModel tableModelRayons;
	private JTable tableProduits;
	private JTable tableRayons;

	public FenetreStock(GestionProduit pGestionProduit, GestionRayon pGestionRayon) {
		gestionProduit = pGestionProduit;
		gestionRayon = pGestionRayon;
		this.setBackground(BG);
		setLayout(new BorderLayout(10, 10));
		buildUI();
		refreshTables();
	}

	private void buildUI() {
		// En-tête
		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
		header.setBackground(PRIMARY);
		header.setBorder(new EmptyBorder(10, 16, 10, 16));
		JLabel titre = new JLabel("🏪  Gestion des Rayons & Stocks");
		titre.setFont(FONT_TTL);
		titre.setForeground(Color.WHITE);
		header.add(titre);
		add(header, BorderLayout.NORTH);

		// Panneau splitté : rayons (gauche) | produits stock (droite)
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		split.setDividerLocation(380);
		split.setBackground(BG);

		// ── Rayons ────────────────────────────────────────────────────────
		JPanel panelRayons = new JPanel(new BorderLayout(6, 6));
		panelRayons.setBackground(BG);
		panelRayons.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(0xBDBDBD), 1), " Rayons ",
				TitledBorder.LEFT, TitledBorder.TOP, FONT_LBL.deriveFont(Font.BOLD), PRIMARY));
		String[] colsR = { "Code", "Nom rayon", "Responsable", "Produits" };
		tableModelRayons = new DefaultTableModel(colsR, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		tableRayons = new JTable(tableModelRayons);
		tableRayons.setFont(FONT_LBL);
		tableRayons.setRowHeight(24);
		tableRayons.getTableHeader().setBackground(PRIMARY);
		tableRayons.getTableHeader().setForeground(Color.WHITE);
		tableRayons.getTableHeader().setFont(FONT_LBL.deriveFont(Font.BOLD));
		tableRayons.setSelectionBackground(new Color(0xBBDEFB));
		panelRayons.add(new JScrollPane(tableRayons), BorderLayout.CENTER);

		// Formulaire rayon
		JPanel formRayon = new JPanel(new GridBagLayout());
		formRayon.setBackground(Color.WHITE);
		formRayon.setBorder(new EmptyBorder(6, 6, 6, 6));
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(3, 5, 3, 5);
		g.fill = GridBagConstraints.HORIZONTAL;
		JTextField tfNomRayon = new JTextField(12);
		JTextField tfMatResp = new JTextField(6);
		g.gridx = 0;
		g.gridy = 0;
		JLabel lblNomRayon = new JLabel("Nom rayon :");
		lblNomRayon.setForeground(new Color(0x212121));
		formRayon.add(lblNomRayon, g);
		g.gridx = 1;
		formRayon.add(tfNomRayon, g);
		g.gridx = 0;
		g.gridy = 1;
		JLabel lblMatricule = new JLabel("Matricule chef :");
		lblMatricule.setForeground(new Color(0x212121));
		formRayon.add(lblMatricule, g);
		g.gridx = 1;
		formRayon.add(tfMatResp, g);
		g.gridx = 0;
		g.gridy = 2;
		g.gridwidth = 2;
		JButton btnAddRayon = blueButton("➕ Créer rayon");
		btnAddRayon.addActionListener(e -> {
			String nom = tfNomRayon.getText().trim();
			if (nom.isEmpty())
				return;
			// Chef rayon simplifié pour la démo
			ChefRayon chef = new ChefRayon(1, "Chef", nom, 3000, nom);
			gestionRayon.ajouterRayon(nom, chef);
			refreshTables();
			tfNomRayon.setText("");
			tfMatResp.setText("");
		});
		formRayon.add(btnAddRayon, g);
		panelRayons.add(formRayon, BorderLayout.SOUTH);
		split.setLeftComponent(panelRayons);

		// ── Produits / Stock ──────────────────────────────────────────────
		JPanel panelProduits = new JPanel(new BorderLayout(6, 6));
		panelProduits.setBackground(BG);
		panelProduits.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(0xBDBDBD), 1),
				" Tous les produits & état du stock ", TitledBorder.LEFT, TitledBorder.TOP,
				FONT_LBL.deriveFont(Font.BOLD), PRIMARY));
		String[] colsP = { "Réf.", "Désignation", "Type", "Stock", "Alerte" };
		tableModelProduits = new DefaultTableModel(colsP, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		tableProduits = new JTable(tableModelProduits);
		tableProduits.setFont(FONT_LBL);
		tableProduits.setRowHeight(24);
		tableProduits.getTableHeader().setBackground(PRIMARY);
		tableProduits.getTableHeader().setForeground(Color.WHITE);
		tableProduits.getTableHeader().setFont(FONT_LBL.deriveFont(Font.BOLD));
		tableProduits.setSelectionBackground(new Color(0xBBDEFB));

		// Coloriage des lignes en alerte
		tableProduits.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c) {
				super.getTableCellRendererComponent(t, v, sel, foc, r, c);
				String alerte = (String) tableModelProduits.getValueAt(r, 4);
				if ("⚠️ Stock faible".equals(alerte))
					setBackground(new Color(0xFFD3C4));
				else if ("🔴 Rupture".equals(alerte))
					setBackground(new Color(0xFF7A86));
				else
					setBackground(new Color(0x9BD989));
				if (sel)
					setBackground(new Color(0xBBDEFB));
				return this;
			}
		});
		panelProduits.add(new JScrollPane(tableProduits), BorderLayout.CENTER);

		// Boutons stock
		JPanel btnStock = new JPanel(new FlowLayout(FlowLayout.LEFT));
		btnStock.setBackground(BG);
		JButton btnAjoutStock = blueButton("➕ Entrée stock");
		JButton btnSortieStock = new JButton("➖ Sortie stock") {
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		btnSortieStock.setFont(FONT_LBL);
		btnSortieStock.setBackground(ORANGE);
		btnSortieStock.setForeground(Color.WHITE);
		btnSortieStock.setContentAreaFilled(false);
		btnSortieStock.setFocusPainted(false);
		btnSortieStock.setBorderPainted(false);
		JButton btnRefresh = blueButton("🔄 Actualiser");

		btnAjoutStock.addActionListener(e -> mouvementStock(true));
		btnSortieStock.addActionListener(e -> mouvementStock(false));
		btnRefresh.addActionListener(e -> refreshTables());

		btnStock.add(btnAjoutStock);
		btnStock.add(btnSortieStock);
		btnStock.add(btnRefresh);
		panelProduits.add(btnStock, BorderLayout.SOUTH);
		split.setRightComponent(panelProduits);

		add(split, BorderLayout.CENTER);
	}

	private void refreshTables() {
		// Rayons
		tableModelRayons.setRowCount(0);
		for (Rayon r : gestionRayon.getListeRayons()) {
			tableModelRayons.addRow(new Object[] { r.getCodeRayon(), r.getNomRayon(),
					r.getResponsable().getPrenom() + " " + r.getResponsable().getNom(), r.getListeProduits().size() });
		}
		// Produits
		tableModelProduits.setRowCount(0);
		for (Produit p : gestionProduit.getListeDesProduits()) {
			String type = (p instanceof ProduitFrais) ? "Frais"
					: (p instanceof ProduitElectronique) ? "Électronique" : "Artisanal";
			String alerte = p.getQuantiteStock() == 0 ? "🔴 Rupture"
					: p.getQuantiteStock() <= 10 ? "⚠️ Stock faible" : "✅ OK";
			tableModelProduits
					.addRow(new Object[] { p.getReference(), p.getDesignation(), type, p.getQuantiteStock(), alerte });
		}
	}

	private void mouvementStock(boolean entree) {
		int row = tableProduits.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(this, "Sélectionnez un produit.", "Info", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int ref = (int) tableModelProduits.getValueAt(row, 0);
		Produit p = gestionProduit.rechercherProduit(ref);
		if (p == null)
			return;
		String msg = entree ? "Quantité à ajouter :" : "Quantité à retirer :";
		String input = JOptionPane.showInputDialog(this, msg, entree ? "Entrée stock" : "Sortie stock",
				JOptionPane.QUESTION_MESSAGE);
		if (input == null || input.trim().isEmpty())
			return;
		try {
			int qte = Integer.parseInt(input.trim());
			if (entree)
				p.ajouterStock(qte);
			else
				p.retirerStock(qte);
			refreshTables();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Valeur invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	private JButton blueButton(String text) {
		JButton b = new JButton(text) {
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		b.setFont(FONT_LBL);
		b.setBackground(PRIMARY);
		b.setForeground(Color.WHITE);
		b.setContentAreaFilled(false);
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				b.setBackground(ACCENT);
			}

			public void mouseExited(MouseEvent e) {
				b.setBackground(PRIMARY);
			}
		});
		return b;
	}
}
