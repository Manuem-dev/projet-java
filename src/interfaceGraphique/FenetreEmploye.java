package interfaceGraphique;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import service.GestionEmploye;
import model.Employe;
import model.Caissier;
import model.ChefRayon;
import model.Directeur;
import model.Comptable;
import model.Magasinier;

/**
 * Fenêtre de gestion du personnel.
 */
public class FenetreEmploye extends JPanel {

	private static final Color BG = new Color(0xF5F5F5);
	private static final Color PRIMARY = new Color(0x1565C0);
	private static final Color ACCENT = new Color(0x0D47A1);
	private static final Font FONT_LBL = new Font("Segoe UI", Font.PLAIN, 13);
	private static final Font FONT_TTL = new Font("Segoe UI", Font.BOLD, 16);

	private GestionEmploye gestionEmploye;
	private DefaultTableModel tableModel;
	private JTable table;

	// Champs formulaire
	private JTextField tfMatricule, tfNom, tfPrenom, tfSalaire, tfExtra;
	private JComboBox<String> cbRole;

	public FenetreEmploye(GestionEmploye gestionEmploye) {
		this.gestionEmploye = gestionEmploye;
		this.setBackground(BG);
		setLayout(new BorderLayout(10, 10));
		buildUI();
		refreshTable();
	}

	private void buildUI() {
		// En-tête
		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
		header.setBackground(PRIMARY);
		header.setBorder(new EmptyBorder(10, 16, 10, 16));
		JLabel titre = new JLabel("👷  Gestion des Employés");
		titre.setFont(FONT_TTL);
		titre.setForeground(Color.WHITE);
		header.add(titre);
		add(header, BorderLayout.NORTH);

		// Table
		String[] cols = { "Matricule", "Prénom", "Nom", "Rôle", "Salaire (€)" };
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
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBorder(new EmptyBorder(8, 10, 0, 10));
		add(scroll, BorderLayout.CENTER);

		// Formulaire
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBackground(Color.WHITE);
		formPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(0xBDBDBD), 1, true),
				" Ajouter / Supprimer un employé ", TitledBorder.LEFT, TitledBorder.TOP, FONT_LBL.deriveFont(Font.BOLD),
				PRIMARY));
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(5, 8, 5, 8);
		g.fill = GridBagConstraints.HORIZONTAL;

		g.gridx = 0;
		g.gridy = 0;
		JLabel lblMatricule = new JLabel("Matricule :");
		lblMatricule.setForeground(new Color(0x212121));
		formPanel.add(lblMatricule, g);
		g.gridx = 1;
		tfMatricule = new JTextField(6);
		formPanel.add(tfMatricule, g);
		g.gridx = 2;
		JLabel lblRole = new JLabel("Rôle :");
		lblRole.setForeground(new Color(0x212121));
		formPanel.add(lblRole, g);
		g.gridx = 3;
		cbRole = new JComboBox<>(new String[] { "Caissier", "ChefRayon", "Directeur", "Comptable", "Magasinier" });
		cbRole.setFont(FONT_LBL);
		formPanel.add(cbRole, g);

		g.gridx = 0;
		g.gridy = 1;
		JLabel lblPrenom = new JLabel("Prénom :");
		lblPrenom.setForeground(new Color(0x212121));
		formPanel.add(lblPrenom, g);
		g.gridx = 1;
		tfPrenom = new JTextField(12);
		formPanel.add(tfPrenom, g);
		g.gridx = 2;
		JLabel lblNom = new JLabel("Nom :");
		lblNom.setForeground(new Color(0x212121));
		formPanel.add(lblNom, g);
		g.gridx = 3;
		tfNom = new JTextField(12);
		formPanel.add(tfNom, g);

		g.gridx = 0;
		g.gridy = 2;
		JLabel lblSalaire = new JLabel("Salaire (€) :");
		lblSalaire.setForeground(new Color(0x212121));
		formPanel.add(lblSalaire, g);
		g.gridx = 1;
		tfSalaire = new JTextField(8);
		formPanel.add(tfSalaire, g);
		g.gridx = 2;
		JLabel lblExtra = new JLabel("Info extra :");
		lblExtra.setForeground(new Color(0x212121));
		formPanel.add(lblExtra, g);
		g.gridx = 3;
		tfExtra = new JTextField(14);
		formPanel.add(tfExtra, g);

		g.gridx = 0;
		g.gridy = 3;
		g.gridwidth = 4;
		JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 2));
		btnRow.setBackground(Color.WHITE);
		JButton btnAjouter = blueButton("➕ Ajouter");
		JButton btnSupprimer = redButton("🗑️ Supprimer");
		JButton btnPrime = blueButton("💰 Ajouter prime");
		btnAjouter.addActionListener(e -> ajouterEmploye());
		btnSupprimer.addActionListener(e -> supprimerEmploye());
		btnPrime.addActionListener(e -> ajouterPrime());
		btnRow.add(btnAjouter);
		btnRow.add(btnPrime);
		btnRow.add(btnSupprimer);
		formPanel.add(btnRow, g);
		add(formPanel, BorderLayout.SOUTH);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chargerLigne();
			}
		});
	}

	private void refreshTable() {
		tableModel.setRowCount(0);
		for (Employe emp : gestionEmploye.getListeEmployes()) {
			tableModel.addRow(new Object[] { emp.getMatricule(), emp.getPrenom(), emp.getNom(), emp.getRole(),
					String.format("%.2f", emp.getSalaire()) });
		}
	}

	private void ajouterEmploye() {
		try {
			int mat = Integer.parseInt(tfMatricule.getText().trim());
			String pr = tfPrenom.getText().trim();
			String nom = tfNom.getText().trim();
			double sal = Double.parseDouble(tfSalaire.getText().trim());
			String role = (String) cbRole.getSelectedItem();
			String extra = tfExtra.getText().trim();

			Employe emp;
			switch (role) {
			case "Caissier":
				int numCaisse = extra.isEmpty() ? 1 : Integer.parseInt(extra);
				emp = new Caissier(mat, nom, pr, sal, numCaisse);
				break;
			case "ChefRayon":
				emp = new ChefRayon(mat, nom, pr, sal, extra.isEmpty() ? "Général" : extra);
				break;
			case "Directeur":
				emp = new Directeur(mat, nom, pr, sal);
				break;
			case "Comptable":
				emp = new Comptable(mat, nom, pr, sal);
				break;
			default:
				emp = new Magasinier(mat, nom, pr, sal);
			}
			if (gestionEmploye.ajouterEmploye(emp)) {
				refreshTable();
				viderFormulaire();
				JOptionPane.showMessageDialog(this, "Employé ajouté !", "Succès", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Matricule déjà utilisé.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void supprimerEmploye() {
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(this, "Sélectionnez un employé.", "Info", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int mat = (int) tableModel.getValueAt(row, 0);
		if (JOptionPane.showConfirmDialog(this, "Supprimer cet employé ?", "Confirmation",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			gestionEmploye.supprimerEmploye(mat);
			refreshTable();
		}
	}

	private void ajouterPrime() {
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(this, "Sélectionnez un employé.", "Info", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int mat = (int) tableModel.getValueAt(row, 0);
		String input = JOptionPane.showInputDialog(this, "Montant de la prime (€) :", "Ajouter prime",
				JOptionPane.QUESTION_MESSAGE);
		if (input == null || input.trim().isEmpty())
			return;
		try {
			double prime = Double.parseDouble(input.trim());
			gestionEmploye.ajouterPrime(mat, prime);
			refreshTable();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Montant invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void chargerLigne() {
		int row = table.getSelectedRow();
		if (row < 0)
			return;
		tfMatricule.setText(String.valueOf(tableModel.getValueAt(row, 0)));
		tfPrenom.setText((String) tableModel.getValueAt(row, 1));
		tfNom.setText((String) tableModel.getValueAt(row, 2));
		tfSalaire.setText(String.valueOf(tableModel.getValueAt(row, 4)));
		cbRole.setSelectedItem(tableModel.getValueAt(row, 3));
	}

	private void viderFormulaire() {
		tfMatricule.setText("");
		tfNom.setText("");
		tfPrenom.setText("");
		tfSalaire.setText("");
		tfExtra.setText("");
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
