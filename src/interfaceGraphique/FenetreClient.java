package interfaceGraphique;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import service.GestionClient;
import model.Client;

/**
 * Fenêtre de gestion des clients et fidélité.
 */
public class FenetreClient extends JPanel {

	private static final Color BG = new Color(0xF5F5F5);
	private static final Color PRIMARY = new Color(0x1565C0);
	private static final Color ACCENT = new Color(0x0D47A1);
	private static final Font FONT_LBL = new Font("Segoe UI", Font.PLAIN, 13);
	private static final Font FONT_TTL = new Font("Segoe UI", Font.BOLD, 16);

	private GestionClient gestionClient;
	private DefaultTableModel tableModel;
	private JTable table;
	private JTextField tfNom, tfTelephone, tfRecherche;

	public FenetreClient(GestionClient gestionClient) {
		this.gestionClient = gestionClient;
		this.setBackground(BG);
		setLayout(new BorderLayout(10, 10));
		buildUI();
		refreshTable(gestionClient.getListeClients());
	}

	private void buildUI() {
		// En-tête
		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
		header.setBackground(PRIMARY);
		header.setBorder(new EmptyBorder(10, 16, 10, 16));
		JLabel titre = new JLabel("👤  Gestion des Clients");
		titre.setFont(FONT_TTL);
		titre.setForeground(Color.WHITE);
		header.add(titre);
		add(header, BorderLayout.NORTH);

		// Barre de recherche + table
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(BG);
		topPanel.setBorder(new EmptyBorder(8, 10, 0, 10));

		JPanel barreRecherche = new JPanel(new FlowLayout(FlowLayout.LEFT));
		barreRecherche.setBackground(BG);
		tfRecherche = new JTextField(18);
		tfRecherche.setFont(FONT_LBL);
		JButton btnRech = blueButton("🔍 Rechercher");
		btnRech.addActionListener(e -> {
			ArrayList<Client> res = gestionClient.rechercherParNom(tfRecherche.getText().trim());
			refreshTable(res);
		});
		JButton btnTout = blueButton("Tous");
		btnTout.addActionListener(e -> {
			tfRecherche.setText("");
			refreshTable(gestionClient.getListeClients());
		});
		barreRecherche.add(new JLabel("Nom : "));
		barreRecherche.add(tfRecherche);
		barreRecherche.add(btnRech);
		barreRecherche.add(btnTout);
		topPanel.add(barreRecherche, BorderLayout.NORTH);

		String[] cols = { "N°", "Nom", "Téléphone", "Points fidélité", "Nb achats" };
		tableModel = new DefaultTableModel(cols, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		table = new JTable(tableModel);
		table.setFont(FONT_LBL);
		table.setRowHeight(24);
		table.getTableHeader().setBackground(PRIMARY);
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(FONT_LBL.deriveFont(Font.BOLD));
		table.setSelectionBackground(new Color(0xBBDEFB));
		topPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(topPanel, BorderLayout.CENTER);

		// Formulaire + boutons
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBackground(Color.WHITE);
		formPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(0xBDBDBD), 1, true),
				" Ajouter / Supprimer un client ", TitledBorder.LEFT, TitledBorder.TOP, FONT_LBL.deriveFont(Font.BOLD),
				PRIMARY));
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(6, 8, 6, 8);
		g.fill = GridBagConstraints.HORIZONTAL;

		g.gridx = 0;
		g.gridy = 0;
		formPanel.add(new JLabel("Nom :"), g);
		g.gridx = 1;
		tfNom = new JTextField(16);
		formPanel.add(tfNom, g);
		g.gridx = 2;
		formPanel.add(new JLabel("Téléphone :"), g);
		g.gridx = 3;
		tfTelephone = new JTextField(12);
		formPanel.add(tfTelephone, g);

		g.gridx = 0;
		g.gridy = 1;
		g.gridwidth = 4;
		JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		btnRow.setBackground(Color.WHITE);
		JButton btnAjouter = blueButton("➕ Ajouter");
		JButton btnHistorique = blueButton("📋 Historique");
		JButton btnSupprimer = redButton("🗑️ Supprimer");
		btnAjouter.addActionListener(e -> ajouterClient());
		btnHistorique.addActionListener(e -> voirHistorique());
		btnSupprimer.addActionListener(e -> supprimerClient());
		btnRow.add(btnAjouter);
		btnRow.add(btnHistorique);
		btnRow.add(btnSupprimer);
		formPanel.add(btnRow, g);
		add(formPanel, BorderLayout.SOUTH);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chargerLigne();
			}
		});
	}

	private void refreshTable(ArrayList<Client> liste) {
		tableModel.setRowCount(0);
		for (Client c : liste) {
			tableModel.addRow(new Object[] { c.getNumeroClient(), c.getNom(), c.getTelephone(), c.getPointsFidelite(),
					c.getHistoriqueAchats().size() });
		}
	}

	private void ajouterClient() {
		String nom = tfNom.getText().trim();
		String tel = tfTelephone.getText().trim();
		if (nom.isEmpty() || tel.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Remplissez nom et téléphone.", "Info", JOptionPane.WARNING_MESSAGE);
			return;
		}
		gestionClient.ajouterClient(nom, tel);
		refreshTable(gestionClient.getListeClients());
		tfNom.setText("");
		tfTelephone.setText("");
		JOptionPane.showMessageDialog(this, "Client ajouté !", "Succès", JOptionPane.INFORMATION_MESSAGE);
	}

	private void supprimerClient() {
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(this, "Sélectionnez un client.", "Info", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int num = (int) tableModel.getValueAt(row, 0);
		int rep = JOptionPane.showConfirmDialog(this, "Supprimer ce client ?", "Confirmation",
				JOptionPane.YES_NO_OPTION);
		if (rep == JOptionPane.YES_OPTION) {
			gestionClient.supprimerClient(num);
			refreshTable(gestionClient.getListeClients());
		}
	}

	private void voirHistorique() {
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(this, "Sélectionnez un client.", "Info", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int num = (int) tableModel.getValueAt(row, 0);
		Client c = gestionClient.rechercherClient(num);
		if (c == null)
			return;
		StringBuilder sb = new StringBuilder();
		sb.append("Client : ").append(c.getNom()).append(" | Points : ").append(c.getPointsFidelite()).append("\n\n");
		if (c.getHistoriqueAchats().isEmpty()) {
			sb.append("Aucun achat enregistré.");
		} else {
			for (var v : c.getHistoriqueAchats()) {
				sb.append("• Vente n°").append(v.getNumeroVente()).append(" | ").append(v.getDate()).append(" | ")
						.append(String.format("%.2f €", v.calculerTotal())).append("\n");
			}
		}
		JOptionPane.showMessageDialog(this, sb.toString(), "Historique de " + c.getNom(),
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void chargerLigne() {
		int row = table.getSelectedRow();
		if (row < 0)
			return;
		tfNom.setText((String) tableModel.getValueAt(row, 1));
		tfTelephone.setText((String) tableModel.getValueAt(row, 2));
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

	private JButton redButton(String text) {
		JButton b = new JButton(text) {
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
		return b;
	}
}
