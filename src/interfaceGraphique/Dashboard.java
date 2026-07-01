package interfaceGraphique;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import service.*;
import model.*;

/**
 * Dashboard principal — reçoit les services peuplés depuis Application.java.
 * Utilise un CardLayout pour naviguer sans ouvrir de nouvelles fenêtres.
 */
public class Dashboard extends JFrame {

	// ── Design tokens ─────────────────────────────────────────────────────────
	private static final Color BG = new Color(0xF5F5F5);
	private static final Color PRIMARY = new Color(0x1565C0);
	private static final Color ACCENT = new Color(0x0D47A1);
	private static final Color CARD_BG = Color.WHITE;
	private static final Color TEXT_SUB = new Color(0x757575);
	private static final Color GREEN = new Color(0x2E7D32);
	private static final Color RED_CLR = new Color(0xC62828);
	private static final Font FONT_TTL = new Font("Segoe UI", Font.BOLD, 18);
	private static final Font FONT_LBL = new Font("Segoe UI", Font.PLAIN, 13);
	private static final Font FONT_CARD = new Font("Segoe UI", Font.BOLD, 14);

	private GestionUtilisateur gestionUtilisateur;
	private GestionProduit gestionProduit;
	private GestionEmploye gestionEmploye;
	private GestionClient gestionClient;
	private GestionRayon gestionRayon;
	private GestionCaisseVente gestionCaisseVente;
	private GestionFacture gestionFacture;

	private CardLayout cardLayout;
	private JPanel mainContainer;
	private JButton btnRetour;

	public Dashboard(GestionUtilisateur gestionUtilisateur, GestionProduit gestionProduit,
			GestionEmploye gestionEmploye, GestionClient gestionClient, GestionRayon gestionRayon,
			GestionCaisseVente gestionCaisseVente, GestionFacture gestionFacture) {
		this.gestionUtilisateur = gestionUtilisateur;
		this.gestionProduit = gestionProduit;
		this.gestionEmploye = gestionEmploye;
		this.gestionClient = gestionClient;
		this.gestionRayon = gestionRayon;
		this.gestionCaisseVente = gestionCaisseVente;
		this.gestionFacture = gestionFacture;

		Utilisateur u = gestionUtilisateur.getUtilisateurConnecte();
		setTitle("SuperMarché — Dashboard");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1100, 720);
		setLocationRelativeTo(null);
		setResizable(true);
		getContentPane().setBackground(BG);
		setLayout(new BorderLayout());
		buildUI(u);
	}

	private void buildUI(Utilisateur u) {
		// ── En-tête fixe ──────────────────────────────────────────────────
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(PRIMARY);
		header.setBorder(new EmptyBorder(14, 24, 14, 24));

		JLabel titre = new JLabel("🛒  Gestion Supermarché");
		titre.setFont(FONT_TTL);
		titre.setForeground(Color.WHITE);
		header.add(titre, BorderLayout.WEST);

		String nomUser = (u != null) ? u.getEmploye().getPrenom() + " " + u.getEmploye().getNom() : "";
		String roleUser = (u != null) ? u.getRole() : "";
		JLabel userInfo = new JLabel("  " + nomUser + "  [" + roleUser + "]");
		userInfo.setFont(FONT_LBL.deriveFont(Font.ITALIC));
		userInfo.setForeground(new Color(0xBBDEFB));
		header.add(userInfo, BorderLayout.CENTER);

		btnRetour = new JButton("← Retour") {
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		btnRetour.setFont(FONT_LBL);
		btnRetour.setBackground(ACCENT);
		btnRetour.setForeground(Color.WHITE);
		btnRetour.setContentAreaFilled(false);
		btnRetour.setFocusPainted(false);
		btnRetour.setBorderPainted(false);
		btnRetour.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRetour.setVisible(false);
		btnRetour.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnRetour.setBackground(new Color(0x283593));
			}

			public void mouseExited(MouseEvent e) {
				btnRetour.setBackground(ACCENT);
			}
		});
		btnRetour.addActionListener(e -> showCard("MENU"));

		JButton btnDeconnect = new JButton("Déconnexion") {
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		btnDeconnect.setFont(FONT_LBL);
		btnDeconnect.setBackground(RED_CLR);
		btnDeconnect.setForeground(Color.WHITE);
		btnDeconnect.setContentAreaFilled(false);
		btnDeconnect.setFocusPainted(false);
		btnDeconnect.setBorderPainted(false);
		btnDeconnect.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDeconnect.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnDeconnect.setBackground(new Color(0xB71C1C));
			}

			public void mouseExited(MouseEvent e) {
				btnDeconnect.setBackground(RED_CLR);
			}
		});
		btnDeconnect.addActionListener(e -> {
			gestionUtilisateur.deconnecter();
			new Accueil(gestionUtilisateur, gestionProduit, gestionEmploye, gestionClient, gestionRayon,
					gestionCaisseVente, gestionFacture).setVisible(true);
			dispose();
		});

		JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		rightHeader.setOpaque(false);
		rightHeader.add(btnRetour);
		rightHeader.add(btnDeconnect);
		header.add(rightHeader, BorderLayout.EAST);
		add(header, BorderLayout.NORTH);

		// ── CardLayout principal ──────────────────────────────────────────
		cardLayout = new CardLayout();
		mainContainer = new JPanel(cardLayout);
		mainContainer.setBackground(BG);

		// ── Carte MENU ───────────────────────────────────────────────────
		mainContainer.add(buildMenu(u), "MENU");

		// ── Cartes Modules (avec les vrais services peuplés) ─────────────
		mainContainer.add(new FenetreProduit(gestionProduit), "PRODUITS");
		mainContainer.add(new FenetreClient(gestionClient), "CLIENTS");
		mainContainer.add(new FenetreStock(gestionProduit, gestionRayon), "RAYONS");
		mainContainer.add(new FenetreCaisse(gestionCaisseVente, gestionProduit, gestionClient, gestionEmploye),
				"CAISSE");
		mainContainer.add(new FenetreEmploye(gestionEmploye), "EMPLOYES");
		mainContainer.add(buildDashboardPanel(), "TABLEAU_BORD");

		add(mainContainer, BorderLayout.CENTER);
	}

	private JPanel buildMenu(Utilisateur utilisateur) {
		JPanel menuPanel = new JPanel(new BorderLayout());
		menuPanel.setBackground(BG);

		JLabel sousTitre = new JLabel("Choisissez un module à gérer", SwingConstants.CENTER);
		sousTitre.setFont(FONT_LBL.deriveFont(Font.ITALIC, 14f));
		sousTitre.setForeground(TEXT_SUB);
		sousTitre.setBorder(new EmptyBorder(20, 0, 8, 0));
		menuPanel.add(sousTitre, BorderLayout.NORTH);

		JPanel grid = new JPanel(new GridLayout(2, 3, 20, 20));
		grid.setBackground(BG);
		grid.setBorder(new EmptyBorder(14, 32, 32, 32));
		
		boolean VoirProduits = utilisateur != null && (utilisateur.verifierRole("Directeur") ||utilisateur.verifierRole("Magasinier") || utilisateur.verifierRole("Caissier"));
		if (VoirProduits)
			grid.add(buildCard("📦 Produits", "Gérer le catalogue des produits", e -> showCard("PRODUITS")));
		else
			grid.add(buildCard("📦 Produits", "Accès réservé", null));
		
		boolean VoirClients = utilisateur != null && (utilisateur.verifierRole("Directeur") || utilisateur.verifierRole("Caissier"));
		if (VoirClients)
			grid.add(buildCard("👤 Clients", "Fidélité et historique d'achats", e -> showCard("CLIENTS")));
		else
			grid.add(buildCard("👤 Clients", "Accès réservé", null));
		
		boolean VoirStocksRayons = utilisateur != null && (utilisateur.verifierRole("Directeur") ||utilisateur.verifierRole("Magasinier") || utilisateur.verifierRole("ChefRayon"));
		if (VoirStocksRayons)
			grid.add(buildCard("🏪 Stocks & Rayons", "Surveiller et ajuster les stocks", e -> showCard("RAYONS")));
		else
			grid.add(buildCard("🏪 Stocks & Rayons", "Accès réservé", null));
		
		boolean VoirCaisse = utilisateur != null && (utilisateur.verifierRole("Directeur") ||utilisateur.verifierRole("Caissier"));
		if (VoirCaisse)
			grid.add(buildCard("💳 Caisse", "Effectuer et enregistrer une vente", e -> showCard("CAISSE")));
		else
			grid.add(buildCard("💳 Caisse", "Accès réservé", null));
		

		//grid.add(buildCard("📦 Produits", "Gérer le catalogue des produits", e -> showCard("PRODUITS")));
		//grid.add(buildCard("👤 Clients", "Fidélité et historique d'achats", e -> showCard("CLIENTS")));
		//grid.add(buildCard("🏪 Stocks & Rayons", "Surveiller et ajuster les stocks", e -> showCard("RAYONS")));
		//grid.add(buildCard("💳 Caisse", "Effectuer et enregistrer une vente", e -> showCard("CAISSE")));

		boolean voirTableauDeBord = utilisateur != null && (utilisateur.verifierRole("Directeur") || utilisateur.verifierRole("Comptable"));
		if (voirTableauDeBord)
			grid.add(buildCard("📊 Tableau de bord", "Statistiques et rapports", e -> {
				refreshDashboard();
				showCard("TABLEAU_BORD");
			}));
		else
			grid.add(buildCard("📊 Tableau de bord", "Accès réservé", null));

		boolean VoirEmployes = utilisateur != null && (utilisateur.verifierRole("Directeur"));
		if (VoirEmployes)
			grid.add(buildCard("👷 Employés", "Gérer le personnel", e -> showCard("EMPLOYES")));
		else
			grid.add(buildCard("👷 Employés", "Accès réservé", null));

		menuPanel.add(grid, BorderLayout.CENTER);
		return menuPanel;
	}

	// ── Vrai panneau Tableau de Bord ──────────────────────────────────────────
	private JPanel tdbPanel; // référence pour rafraîchissement
	private JLabel tdbCA, tdbVentes, tdbClients, tdbEmployes, tdbProduits, tdbAlertes;

	private JPanel buildDashboardPanel() {
		tdbPanel = new JPanel(new BorderLayout(0, 0));
		tdbPanel.setBackground(BG);

		JPanel tdbHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tdbHeader.setBackground(PRIMARY);
		tdbHeader.setBorder(new EmptyBorder(10, 16, 10, 16));
		JLabel tdbTitre = new JLabel("📊  Tableau de Bord — Direction");
		tdbTitre.setFont(new Font("Segoe UI", Font.BOLD, 16));
		tdbTitre.setForeground(Color.WHITE);
		tdbHeader.add(tdbTitre);
		tdbPanel.add(tdbHeader, BorderLayout.NORTH);

		// Grille de KPI
		JPanel kpiGrid = new JPanel(new GridLayout(2, 3, 16, 16));
		kpiGrid.setBackground(BG);
		kpiGrid.setBorder(new EmptyBorder(20, 24, 20, 24));

		tdbCA = new JLabel("—");
		tdbVentes = new JLabel("—");
		tdbClients = new JLabel("—");
		tdbEmployes = new JLabel("—");
		tdbProduits = new JLabel("—");
		tdbAlertes = new JLabel("—");

		kpiGrid.add(kpiCard("💰 Chiffre d'Affaires", tdbCA, new Color(0x1B5E20)));
		kpiGrid.add(kpiCard("🧾 Ventes enregistrées", tdbVentes, PRIMARY));
		kpiGrid.add(kpiCard("👤 Clients fidèles", tdbClients, new Color(0x4A148C)));
		kpiGrid.add(kpiCard("👷 Employés", tdbEmployes, new Color(0xE65100)));
		kpiGrid.add(kpiCard("📦 Produits en catalogue", tdbProduits, new Color(0x006064)));
		kpiGrid.add(kpiCard("⚠️ Alertes stock faible", tdbAlertes, RED_CLR));

		tdbPanel.add(kpiGrid, BorderLayout.CENTER);

		// Tableau des dernières ventes
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBackground(BG);
		bottomPanel.setBorder(new EmptyBorder(0, 24, 20, 24));

		JLabel lblVentesTitle = new JLabel("Dernières ventes enregistrées :");
		lblVentesTitle.setFont(FONT_CARD);
		lblVentesTitle.setForeground(PRIMARY);
		lblVentesTitle.setBorder(new EmptyBorder(0, 0, 8, 0));
		bottomPanel.add(lblVentesTitle, BorderLayout.NORTH);

		String[] cols = { "N° Vente", "Date", "Client", "Caissier", "Montant (€)" };
		DefaultTableModel ventesModel = new DefaultTableModel(cols, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		JTable ventesTable = new JTable(ventesModel);
		ventesTable.setFont(FONT_LBL);
		ventesTable.setRowHeight(24);
		ventesTable.getTableHeader().setBackground(PRIMARY);
		ventesTable.getTableHeader().setForeground(Color.WHITE);
		ventesTable.getTableHeader().setFont(FONT_LBL.deriveFont(Font.BOLD));
		ventesTable.setSelectionBackground(new Color(0xBBDEFB));
		bottomPanel.add(new JScrollPane(ventesTable), BorderLayout.CENTER);
		tdbPanel.add(bottomPanel, BorderLayout.SOUTH);

		// Stocker le modèle pour rafraîchissement
		tdbPanel.putClientProperty("ventesModel", ventesModel);

		return tdbPanel;
	}

	private JPanel kpiCard(String titre, JLabel valLabel, Color accent) {
		JPanel card = new JPanel(new BorderLayout());
		card.setBackground(CARD_BG);
		card.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(0xE0E0E0), 1, true),
				new EmptyBorder(16, 20, 16, 20)));

		JLabel lblTitre = new JLabel(titre);
		lblTitre.setFont(FONT_LBL);
		lblTitre.setForeground(TEXT_SUB);
		card.add(lblTitre, BorderLayout.NORTH);

		valLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
		valLabel.setForeground(accent);
		card.add(valLabel, BorderLayout.CENTER);

		return card;
	}

	private void refreshDashboard() {
		ArrayList<Vente> ventes = gestionCaisseVente.getListeVentes();
		double ca = 0;
		for (Vente v : ventes)
			ca += v.calculerTotal();

		tdbCA.setText(String.format("%.2f €", ca));
		tdbVentes.setText(String.valueOf(ventes.size()));
		tdbClients.setText(String.valueOf(gestionClient.getListeClients().size()));
		tdbEmployes.setText(String.valueOf(gestionEmploye.getListeEmployes().size()));
		tdbProduits.setText(String.valueOf(gestionProduit.getListeDesProduits().size()));

		long alertes = gestionProduit.getListeDesProduits().stream().filter(p -> p.getQuantiteStock() <= 10).count();
		tdbAlertes.setText(String.valueOf(alertes));
		tdbAlertes.setForeground(alertes > 0 ? RED_CLR : GREEN);

		// Rafraîchir tableau ventes
		DefaultTableModel vm = (DefaultTableModel) tdbPanel.getClientProperty("ventesModel");
		if (vm != null) {
			vm.setRowCount(0);
			for (Vente v : ventes) {
				String clientNom = v.getClient() != null ? v.getClient().getNom() : "—";
				String caissierNom = v.getCaissier() != null
						? v.getCaissier().getPrenom() + " " + v.getCaissier().getNom()
						: "—";
				vm.addRow(new Object[] { v.getNumeroVente(), v.getDate(), clientNom, caissierNom,
						String.format("%.2f", v.calculerTotal()) });
			}
		}
	}

	private void showCard(String name) {
		cardLayout.show(mainContainer, name);
		btnRetour.setVisible(!name.equals("MENU"));
	}

	private JPanel buildCard(String titre, String description, ActionListener action) {
		JPanel card = new JPanel(new BorderLayout());
		card.setBackground(CARD_BG);
		card.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(0xE0E0E0), 1, true),
				new EmptyBorder(20, 20, 20, 20)));

		JLabel lTitre = new JLabel(titre);
		lTitre.setFont(FONT_CARD);
		lTitre.setForeground(PRIMARY);
		card.add(lTitre, BorderLayout.NORTH);

		JLabel lDesc = new JLabel("<html>" + description + "</html>");
		lDesc.setFont(FONT_LBL);
		lDesc.setForeground(TEXT_SUB);
		card.add(lDesc, BorderLayout.CENTER);

		if (action != null) {
			JButton btn = new JButton("Ouvrir →") {
				@Override
				protected void paintComponent(Graphics g) {
					g.setColor(getBackground());
					g.fillRect(0, 0, getWidth(), getHeight());
					super.paintComponent(g);
				}
			};
			btn.setFont(FONT_LBL.deriveFont(Font.BOLD));
			btn.setBackground(PRIMARY);
			btn.setForeground(Color.WHITE);
			btn.setContentAreaFilled(false);
			btn.setFocusPainted(false);
			btn.setBorderPainted(false);
			btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btn.addActionListener(action);
			btn.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					btn.setBackground(ACCENT);
				}

				public void mouseExited(MouseEvent e) {
					btn.setBackground(PRIMARY);
				}
			});
			card.add(btn, BorderLayout.SOUTH);
		} else {
			JLabel verrou = new JLabel("🔒 Accès restreint");
			verrou.setFont(FONT_LBL.deriveFont(Font.ITALIC));
			verrou.setForeground(new Color(0xBDBDBD));
			card.add(verrou, BorderLayout.SOUTH);
		}
		return card;
	}
}
