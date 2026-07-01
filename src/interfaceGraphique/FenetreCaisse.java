package interfaceGraphique;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import service.GestionCaisseVente;
import service.GestionProduit;
import service.GestionClient;
import service.GestionEmploye;
import model.*;

/**
 * Module Caisse — sélection du caissier via pop-up, client via pop-up.
 */
public class FenetreCaisse extends JPanel {

	private static final Color BG = new Color(0xF5F5F5);
	private static final Color PRIMARY = new Color(0x1565C0);
	private static final Color ACCENT = new Color(0x0D47A1);
	private static final Color GREEN = new Color(0x2E7D32);
	private static final Color TEXT_FG = new Color(0x212121);
	private static final Font FONT_LBL = new Font("Segoe UI", Font.PLAIN, 13);
	private static final Font FONT_TTL = new Font("Segoe UI", Font.BOLD, 16);
	private static final Font FONT_TOTAL = new Font("Segoe UI", Font.BOLD, 22);

	private GestionCaisseVente gestionCaisseVente;
	private GestionProduit gestionProduit;
	private GestionClient gestionClient;
	private GestionEmploye gestionEmploye;

	private Vente venteEnCours;
	private Client clientEnCours;
	private Caissier caissierActif;

	private DefaultTableModel tableModel;
	private JTable table;
	private JLabel lblTotal, lblCaissier, lblClientInfo;
	private JTextField tfRef, tfQte;

	public FenetreCaisse(GestionCaisseVente gestionCaisseVente, GestionProduit gestionProduit,
			GestionClient gestionClient, GestionEmploye gestionEmploye) {
		this.gestionCaisseVente = gestionCaisseVente;
		this.gestionProduit = gestionProduit;
		this.gestionClient = gestionClient;
		this.gestionEmploye = gestionEmploye;
		this.setBackground(BG);
		setLayout(new BorderLayout(0, 0));
		buildUI();
	}

	// Compatibilité avec l'ancien constructeur sans GestionEmploye
	public FenetreCaisse(GestionCaisseVente gv, GestionProduit gp, GestionClient gc) {
		this(gv, gp, gc, new GestionEmploye());
	}

	private void buildUI() {
		// En-tête
		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
		header.setBackground(PRIMARY);
		header.setBorder(new EmptyBorder(10, 16, 10, 16));
		JLabel titre = new JLabel("💳  Module Caisse & Vente");
		titre.setFont(FONT_TTL);
		titre.setForeground(Color.WHITE);
		header.add(titre);
		add(header, BorderLayout.NORTH);

		// Gauche : panier
		JPanel left = new JPanel(new BorderLayout(6, 6));
		left.setBackground(BG);
		left.setBorder(new EmptyBorder(10, 10, 10, 5));

		String[] cols = { "Réf.", "Désignation", "Qté", "PU (€)", "Sous-total (€)" };
		tableModel = new DefaultTableModel(cols, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		table = new JTable(tableModel);
		table.setFont(FONT_LBL);
		table.setRowHeight(26);
		table.getTableHeader().setBackground(PRIMARY);
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(FONT_LBL.deriveFont(Font.BOLD));
		table.setSelectionBackground(new Color(0xBBDEFB));
		left.add(new JScrollPane(table), BorderLayout.CENTER);

		JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		totalPanel.setBackground(BG);
		lblTotal = new JLabel("Total : 0,00 €");
		lblTotal.setFont(FONT_TOTAL);
		lblTotal.setForeground(GREEN);
		totalPanel.add(lblTotal);
		left.add(totalPanel, BorderLayout.SOUTH);
		add(left, BorderLayout.CENTER);

		// Droite : contrôles
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.setBackground(BG);
		right.setBorder(new EmptyBorder(10, 5, 10, 10));
		right.setPreferredSize(new Dimension(260, 0));

		// Section Caissier
		JPanel secCaissier = section("👤 Caissier actif");
		lblCaissier = new JLabel("— aucun —");
		lblCaissier.setFont(FONT_LBL.deriveFont(Font.ITALIC));
		lblCaissier.setForeground(new Color(0x757575));
		JButton btnCaissier = blueBtn("Choisir caissier…");
		btnCaissier.addActionListener(e -> choisirCaissier());
		secCaissier.add(lblCaissier);
		secCaissier.add(Box.createVerticalStrut(6));
		secCaissier.add(btnCaissier);
		right.add(secCaissier);
		right.add(Box.createVerticalStrut(10));

		// Section Client
		JPanel secClient = section("👥 Client");
		lblClientInfo = new JLabel("— aucun —");
		lblClientInfo.setFont(FONT_LBL.deriveFont(Font.ITALIC));
		lblClientInfo.setForeground(new Color(0x757575));
		JButton btnClient = blueBtn("Choisir client…");
		btnClient.addActionListener(e -> choisirClient());
		secClient.add(lblClientInfo);
		secClient.add(Box.createVerticalStrut(6));
		secClient.add(btnClient);
		right.add(secClient);
		right.add(Box.createVerticalStrut(10));

		// Section Produit
		JPanel secProduit = section("📦 Ajouter produit");
		tfRef = new JTextField(8);
		tfRef.setFont(FONT_LBL);
		tfQte = new JTextField("1", 4);
		tfQte.setFont(FONT_LBL);
		JButton btnPicker = blueBtn("Choisir produit…");
		btnPicker.addActionListener(e -> choisirProduit());
		JButton btnAjouterProd = blueBtn("Ajouter au panier");
		JButton btnRetirer = redBtn("Retirer sélectionné");
		btnAjouterProd.addActionListener(e -> ajouterProduitPanier());
		btnRetirer.addActionListener(e -> retirerProduitPanier());
		secProduit.add(rowOf("Réf. :", tfRef));
		secProduit.add(Box.createVerticalStrut(4));
		secProduit.add(btnPicker);
		secProduit.add(Box.createVerticalStrut(4));
		secProduit.add(rowOf("Quantité :", tfQte));
		secProduit.add(Box.createVerticalStrut(6));
		secProduit.add(btnAjouterProd);
		secProduit.add(Box.createVerticalStrut(4));
		secProduit.add(btnRetirer);
		right.add(secProduit);
		right.add(Box.createVerticalStrut(10));

		// Section Paiement
		JPanel secPaiement = section("💰 Finaliser la vente");
		JTextField tfMontant = new JTextField(10);
		tfMontant.setFont(FONT_LBL);
		JButton btnChoisirMode = blueBtn("Choisir mode paiement…");
		final String[] modeChoisi = { Paiement.especes };
		JLabel lblMode = new JLabel("Mode : " + modeChoisi[0]);
		lblMode.setFont(FONT_LBL);
		lblMode.setForeground(PRIMARY);
		btnChoisirMode.addActionListener(e -> {
			String[] modes = { Paiement.especes, Paiement.carte, Paiement.cheque };
			JList<String> lst = new JList<>(modes);
			lst.setFont(FONT_LBL.deriveFont(14f));
			lst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			lst.setSelectedIndex(0);
			JPanel p = new JPanel(new BorderLayout(0, 8));
			p.add(new JLabel("Choisissez le mode de paiement :"), BorderLayout.NORTH);
			p.add(new JScrollPane(lst), BorderLayout.CENTER);
			if (JOptionPane.showConfirmDialog(this, p, "Mode de paiement", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION && lst.getSelectedIndex() >= 0) {
				modeChoisi[0] = modes[lst.getSelectedIndex()];
				lblMode.setText("Mode : " + modeChoisi[0]);
			}
		});
		JButton btnPayer = new JButton("✅ Valider la vente") {
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		btnPayer.setFont(FONT_LBL.deriveFont(Font.BOLD));
		btnPayer.setBackground(GREEN);
		btnPayer.setForeground(Color.WHITE);
		btnPayer.setContentAreaFilled(false);
		btnPayer.setFocusPainted(false);
		btnPayer.setBorderPainted(false);
		btnPayer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPayer.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnPayer.setBackground(new Color(0x1B5E20));
			}

			public void mouseExited(MouseEvent e) {
				btnPayer.setBackground(GREEN);
			}
		});
		btnPayer.addActionListener(e -> finaliserVente(modeChoisi[0], tfMontant));
		secPaiement.add(btnChoisirMode);
		secPaiement.add(Box.createVerticalStrut(4));
		secPaiement.add(lblMode);
		secPaiement.add(Box.createVerticalStrut(4));
		secPaiement.add(rowOf("Montant reçu (€) :", tfMontant));
		secPaiement.add(Box.createVerticalStrut(8));
		secPaiement.add(btnPayer);
		right.add(secPaiement);

		add(right, BorderLayout.EAST);
	}

	// ── Logique métier ────────────────────────────────────────────────────────

	private void choisirCaissier() {
		// Récupérer les caissiers disponibles depuis gestionEmploye
		ArrayList<Employe> employes = gestionEmploye.getListeEmployes();
		ArrayList<Caissier> caissiers = new ArrayList<>();
		for (Employe e : employes) {
			if (e instanceof Caissier)
				caissiers.add((Caissier) e);
		}

		if (caissiers.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Aucun caissier disponible.", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String[] noms = new String[caissiers.size()];
		for (int i = 0; i < caissiers.size(); i++)
			noms[i] = caissiers.get(i).getPrenom() + " " + caissiers.get(i).getNom() + " (mat. "
					+ caissiers.get(i).getMatricule() + ")";

		JList<String> lst = new JList<>(noms);
		lst.setFont(FONT_LBL.deriveFont(14f));
		lst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lst.setSelectedIndex(0);
		JPanel p = new JPanel(new BorderLayout(0, 8));
		p.add(new JLabel("Sélectionnez le caissier :"), BorderLayout.NORTH);
		p.add(new JScrollPane(lst), BorderLayout.CENTER);

		if (JOptionPane.showConfirmDialog(this, p, "Choisir un caissier", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION && lst.getSelectedIndex() >= 0) {
			caissierActif = caissiers.get(lst.getSelectedIndex());
			caissierActif.ouvrirCaisse();
			venteEnCours = gestionCaisseVente.demarrerVente(caissierActif, null);
			lblCaissier.setText(caissierActif.getPrenom() + " " + caissierActif.getNom());
			lblCaissier.setForeground(PRIMARY);
			refreshPanier();
		}
	}

	private void choisirClient() {
		if (venteEnCours == null) {
			JOptionPane.showMessageDialog(this, "Choisissez d'abord un caissier.", "Info", JOptionPane.WARNING_MESSAGE);
			return;
		}
		ArrayList<Client> clients = gestionClient.getListeClients();
		if (clients.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Aucun client enregistré.", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String[] noms = new String[clients.size()];
		for (int i = 0; i < clients.size(); i++)
			noms[i] = clients.get(i).getNom() + " — " + clients.get(i).getPointsFidelite() + " pts";

		JList<String> lst = new JList<>(noms);
		lst.setFont(FONT_LBL.deriveFont(14f));
		lst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lst.setSelectedIndex(0);
		JPanel p = new JPanel(new BorderLayout(0, 8));
		p.add(new JLabel("Sélectionnez le client :"), BorderLayout.NORTH);
		p.add(new JScrollPane(lst), BorderLayout.CENTER);

		if (JOptionPane.showConfirmDialog(this, p, "Choisir un client", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION && lst.getSelectedIndex() >= 0) {
			clientEnCours = clients.get(lst.getSelectedIndex());
			venteEnCours.setClient(clientEnCours);
			lblClientInfo.setText(clientEnCours.getNom() + " (" + clientEnCours.getPointsFidelite() + " pts)");
			lblClientInfo.setForeground(PRIMARY);
		}
	}

	private void choisirProduit() {
		ArrayList<Produit> produits = gestionProduit.getListeDesProduits();
		if (produits.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Aucun produit disponible.", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String[] noms = new String[produits.size()];
		for (int i = 0; i < produits.size(); i++)
			noms[i] = "[" + produits.get(i).getReference() + "] " + produits.get(i).getDesignation() + " — "
					+ String.format("%.2f €", produits.get(i).getPrixVente()) + " (stock : "
					+ produits.get(i).getQuantiteStock() + ")";

		JList<String> lst = new JList<>(noms);
		lst.setFont(FONT_LBL.deriveFont(14f));
		lst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lst.setSelectedIndex(0);
		JPanel p = new JPanel(new BorderLayout(0, 8));
		p.add(new JLabel("Sélectionnez un produit :"), BorderLayout.NORTH);
		p.add(new JScrollPane(lst), BorderLayout.CENTER);

		if (JOptionPane.showConfirmDialog(this, p, "Choisir un produit", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION && lst.getSelectedIndex() >= 0) {
			tfRef.setText(String.valueOf(produits.get(lst.getSelectedIndex()).getReference()));
		}
	}

	private void ajouterProduitPanier() {
		if (venteEnCours == null) {
			JOptionPane.showMessageDialog(this, "Choisissez d'abord un caissier.", "Info", JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
			int ref = Integer.parseInt(tfRef.getText().trim());
			int qte = Integer.parseInt(tfQte.getText().trim());
			Produit produit = gestionProduit.rechercherProduit(ref);
			if (produit == null) {
				JOptionPane.showMessageDialog(this, "Produit réf. " + ref + " introuvable.", "Erreur",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (qte > produit.getQuantiteStock()) {
				JOptionPane.showMessageDialog(this, "Stock insuffisant,vous ne pouvez pas ajouter la quantité demandée", "Erreur",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			gestionCaisseVente.ajouterProduitVente(venteEnCours, produit, qte);
			refreshPanier();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Référence ou quantité invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void retirerProduitPanier() {
		if (venteEnCours == null)
			return;
		int row = table.getSelectedRow();
		if (row < 0)
			return;
		int ref = (int) tableModel.getValueAt(row, 0);
		Produit produit = gestionProduit.rechercherProduit(ref);
		if (produit != null) {
			gestionCaisseVente.retirerProduitVente(venteEnCours, produit);
			refreshPanier();
		}
	}

	private void finaliserVente(String mode, JTextField tfMontant) {
		if (venteEnCours == null) {
			JOptionPane.showMessageDialog(this, "Aucune vente en cours.", "Info", JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
			double montant = Double.parseDouble(tfMontant.getText().trim().replace(",", "."));
			Facture f = gestionCaisseVente.finaliserVente(venteEnCours, mode, montant);
			if (f != null) {
				JOptionPane.showMessageDialog(this, f.getContenuFacture(), "Facture n°" + f.getNumeroFacture(),
						JOptionPane.INFORMATION_MESSAGE);
				venteEnCours = null;
				clientEnCours = null;
				caissierActif = null;
				tableModel.setRowCount(0);
				lblTotal.setText("Total : 0,00 €");
				lblCaissier.setText("— aucun —");
				lblCaissier.setForeground(new Color(0x757575));
				lblClientInfo.setText("— aucun —");
				lblClientInfo.setForeground(new Color(0x757575));
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Montant invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void refreshPanier() {
		tableModel.setRowCount(0);
		if (venteEnCours == null)
			return;
		java.util.Map<Produit, Integer> occ = new java.util.LinkedHashMap<>();
		for (Produit p : venteEnCours.getPanier())
			occ.put(p, occ.getOrDefault(p, 0) + 1);
		for (java.util.Map.Entry<Produit, Integer> e : occ.entrySet()) {
			Produit p = e.getKey();
			int qte = e.getValue();
			tableModel.addRow(new Object[] { p.getReference(), p.getDesignation(), qte,
					String.format("%.2f", p.getPrixVente()), String.format("%.2f", p.getPrixVente() * qte) });
		}
		lblTotal.setText(String.format("Total : %.2f €", venteEnCours.calculerTotal()));
	}

	// ── Helpers UI ────────────────────────────────────────────────────────────
	private JPanel section(String titre) {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBackground(Color.WHITE);
		p.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(0xBDBDBD), 1, true), " " + titre + " ",
				TitledBorder.LEFT, TitledBorder.TOP, FONT_LBL.deriveFont(Font.BOLD), PRIMARY));
		p.setAlignmentX(Component.LEFT_ALIGNMENT);
		return p;
	}

	private JPanel rowOf(String lbl, JComponent comp) {
		JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
		row.setBackground(Color.WHITE);
		JLabel l = new JLabel(lbl);
		l.setFont(FONT_LBL);
		l.setForeground(TEXT_FG);
		row.add(l);
		row.add(comp);
		return row;
	}

	private JButton blueBtn(String t) {
		JButton b = new JButton(t) {
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
		b.setAlignmentX(Component.LEFT_ALIGNMENT);
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

	private JButton redBtn(String t) {
		JButton b = new JButton(t) {
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		b.setFont(FONT_LBL);
		b.setBackground(new Color(0xC62828));
		b.setForeground(Color.WHITE);
		b.setContentAreaFilled(false);
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setAlignmentX(Component.LEFT_ALIGNMENT);
		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				b.setBackground(new Color(0xB71C1C));
			}

			public void mouseExited(MouseEvent e) {
				b.setBackground(new Color(0xC62828));
			}
		});
		return b;
	}
}
