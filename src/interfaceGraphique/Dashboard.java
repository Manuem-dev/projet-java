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
	private static final Color BG       = new Color(0xF5F5F5);
	private static final Color PRIMARY  = new Color(0x1565C0);
	private static final Color ACCENT   = new Color(0x0D47A1);
	private static final Color CARD_BG  = Color.WHITE;
	private static final Color TEXT_SUB = new Color(0x757575);
	private static final Color GREEN    = new Color(0x2E7D32);
	private static final Color RED_CLR  = new Color(0xC62828);
	private static final Font  FONT_TTL  = new Font("Segoe UI", Font.BOLD,  18);
	private static final Font  FONT_LBL  = new Font("Segoe UI", Font.PLAIN, 13);
	private static final Font  FONT_CARD = new Font("Segoe UI", Font.BOLD,  14);

	private GestionUtilisateur   gestionUtilisateur;
	private GestionProduit       gestionProduit;
	private GestionEmploye       gestionEmploye;
	private GestionClient        gestionClient;
	private GestionRayon         gestionRayon;
	private GestionCaisseVente   gestionCaisseVente;
	private GestionFacture       gestionFacture;

	private CardLayout cardLayout;
	private JPanel     mainContainer;
	private JButton    btnRetour;

	public Dashboard(GestionUtilisateur gestionUtilisateur, GestionProduit gestionProduit,
			GestionEmploye gestionEmploye, GestionClient gestionClient, GestionRayon gestionRayon,
			GestionCaisseVente gestionCaisseVente, GestionFacture gestionFacture) {
		this.gestionUtilisateur = gestionUtilisateur;
		this.gestionProduit     = gestionProduit;
		this.gestionEmploye     = gestionEmploye;
		this.gestionClient      = gestionClient;
		this.gestionRayon       = gestionRayon;
		this.gestionCaisseVente = gestionCaisseVente;
		this.gestionFacture     = gestionFacture;

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

	// ════════════════════════════════════════════════════════════════════════════
	// UI principale
	// ════════════════════════════════════════════════════════════════════════════

	private void buildUI(Utilisateur u) {
		// ── En-tête fixe ──────────────────────────────────────────────────────
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(PRIMARY);
		header.setBorder(new EmptyBorder(14, 24, 14, 24));

		JLabel titre = new JLabel("SUPER MARKET PLUS");
		titre.setFont(FONT_TTL);
		titre.setForeground(Color.WHITE);
		header.add(titre, BorderLayout.WEST);

		String nomUser  = (u != null) ? u.getEmploye().getPrenom() + " " + u.getEmploye().getNom() : "";
		String roleUser = (u != null) ? u.getRole() : "";
		JLabel userInfo = new JLabel("  " + nomUser + "  [" + roleUser + "]");
		userInfo.setFont(FONT_LBL.deriveFont(Font.ITALIC));
		userInfo.setForeground(new Color(0xBBDEFB));
		header.add(userInfo, BorderLayout.CENTER);

		btnRetour = headerBtn("← Retour", ACCENT, new Color(0x283593));
		btnRetour.setVisible(false);
		btnRetour.addActionListener(e -> showCard("MENU"));

		JButton btnDeconnect = headerBtn("Déconnexion", RED_CLR, new Color(0xB71C1C));
		btnDeconnect.addActionListener(e -> {
			gestionUtilisateur.deconnecter();
			new Accueil(gestionUtilisateur, gestionProduit, gestionEmploye, gestionClient,
					gestionRayon, gestionCaisseVente, gestionFacture).setVisible(true);
			dispose();
		});

		JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		rightHeader.setOpaque(false);
		rightHeader.add(btnRetour);
		rightHeader.add(btnDeconnect);
		header.add(rightHeader, BorderLayout.EAST);
		add(header, BorderLayout.NORTH);

		//  CardLayout principal 
		cardLayout    = new CardLayout();
		mainContainer = new JPanel(cardLayout);
		mainContainer.setBackground(BG);

		mainContainer.add(buildMenu(u), "MENU");
		mainContainer.add(new FenetreProduit(gestionProduit), "PRODUITS");
		mainContainer.add(new FenetreClient(gestionClient), "CLIENTS");
		mainContainer.add(new FenetreStock(gestionProduit, gestionRayon, gestionUtilisateur), "RAYONS");
		mainContainer.add(new FenetreCaisse(gestionCaisseVente, gestionProduit, gestionClient, gestionEmploye), "CAISSE");
		mainContainer.add(new FenetreEmploye(gestionEmploye), "EMPLOYES");
		mainContainer.add(buildDashboardPanel(), "TABLEAU_BORD");

		add(mainContainer, BorderLayout.CENTER);
	}

	//  Menu principal (grille de modules)

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

		boolean voirProduits = role(utilisateur, "Directeur", "Magasinier", "Caissier");
		grid.add(voirProduits
				? buildCard("📦 Produits",      "Gérer le catalogue des produits",    e -> showCard("PRODUITS"))
				: buildCard("📦 Produits",      "Accès réservé",                      null));

		boolean voirClients = role(utilisateur, "Directeur", "Caissier");
		grid.add(voirClients
				? buildCard("👤 Clients",       "Fidélité et historique d'achats",    e -> showCard("CLIENTS"))
				: buildCard("👤 Clients",       "Accès réservé",                      null));

		boolean voirRayons = role(utilisateur, "Directeur", "Magasinier", "ChefRayon");
		grid.add(voirRayons
				? buildCard("🏪 Stocks & Rayons", "Surveiller et ajuster les stocks", e -> showCard("RAYONS"))
				: buildCard("🏪 Stocks & Rayons", "Accès réservé",                    null));

		boolean voirCaisse = role(utilisateur, "Directeur", "Caissier");
		grid.add(voirCaisse
				? buildCard("💳 Caisse",        "Effectuer et enregistrer une vente", e -> showCard("CAISSE"))
				: buildCard("💳 Caisse",        "Accès réservé",                      null));

		boolean voirTDB = role(utilisateur, "Directeur", "Comptable");
		grid.add(voirTDB
				? buildCard("📊 Tableau de bord", "Statistiques et rapports",         e -> { refreshDashboard(); showCard("TABLEAU_BORD"); })
				: buildCard("📊 Tableau de bord", "Accès réservé",                    null));

		boolean voirEmployes = role(utilisateur, "Directeur");
		grid.add(voirEmployes
				? buildCard("👷 Employés",      "Gérer le personnel",                 e -> showCard("EMPLOYES"))
				: buildCard("👷 Employés",      "Accès réservé",                      null));

		menuPanel.add(grid, BorderLayout.CENTER);
		return menuPanel;
	}

	/** Vérifie si l'utilisateur possède l'un des rôles indiqués. */
	private boolean role(Utilisateur u, String... roles) {
		if (u == null) return false;
		for (String r : roles) if (u.verifierRole(r)) return true;
		return false;
	}

	//  Tableau de bord — panneau enrichi 

	private JPanel tdbPanel;
	// Labels KPI
	private JLabel tdbCA, tdbVentes, tdbClients, tdbEmployes, tdbProduits, tdbAlertes, tdbPerimes;
	// Modèles pour les onglets
	private DefaultTableModel vmVentes, vmAlertes, vmPerimes;

	private JPanel buildDashboardPanel() {
		tdbPanel = new JPanel(new BorderLayout(0, 0));
		tdbPanel.setBackground(BG);

		//  En-tête du tableau de bord 
		JPanel tdbHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tdbHeader.setBackground(PRIMARY);
		tdbHeader.setBorder(new EmptyBorder(10, 16, 10, 16));
		JLabel tdbTitre = new JLabel("📊  Tableau de Bord — Direction");
		tdbTitre.setFont(new Font("Segoe UI", Font.BOLD, 16));
		tdbTitre.setForeground(Color.WHITE);
		tdbHeader.add(tdbTitre);

		// Bouton rafraîchir
		JButton btnRefresh = new JButton("🔄 Actualiser") {
			@Override protected void paintComponent(Graphics g) {
				g.setColor(getBackground()); g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		btnRefresh.setFont(FONT_LBL); btnRefresh.setForeground(Color.WHITE);
		btnRefresh.setBackground(new Color(0x1976D2)); btnRefresh.setContentAreaFilled(false);
		btnRefresh.setFocusPainted(false); btnRefresh.setBorderPainted(false);
		btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRefresh.addActionListener(e -> refreshDashboard());
		tdbHeader.add(btnRefresh);
		tdbPanel.add(tdbHeader, BorderLayout.NORTH);

		//  Grille KPI (2 × 4) 
		tdbCA       = new JLabel("—");
		tdbVentes   = new JLabel("—");
		tdbClients  = new JLabel("—");
		tdbEmployes = new JLabel("—");
		tdbProduits = new JLabel("—");
		tdbAlertes  = new JLabel("—");
		tdbPerimes  = new JLabel("—");

		JPanel kpiGrid = new JPanel(new GridLayout(2, 4, 14, 14));
		kpiGrid.setBackground(BG);
		kpiGrid.setBorder(new EmptyBorder(16, 20, 10, 20));
		kpiGrid.add(kpiCard("💰 Chiffre d'Affaires",    tdbCA,       new Color(0x1B5E20)));
		kpiGrid.add(kpiCard("🧾 Ventes enregistrées",   tdbVentes,   PRIMARY));
		kpiGrid.add(kpiCard("👤 Clients fidèles",        tdbClients,  new Color(0x4A148C)));
		kpiGrid.add(kpiCard("👷 Employés",               tdbEmployes, new Color(0xE65100)));
		kpiGrid.add(kpiCard("📦 Produits en catalogue",  tdbProduits, new Color(0x006064)));
		kpiGrid.add(kpiCard("⚠️ Alertes stock faible",  tdbAlertes,  RED_CLR));
		kpiGrid.add(kpiCard("🗓️ Produits périmés",      tdbPerimes,  new Color(0x880E4F)));
		// Carte vide pour compléter la grille
		JPanel placeholderCard = new JPanel();
		placeholderCard.setBackground(BG);
		kpiGrid.add(placeholderCard);

		tdbPanel.add(kpiGrid, BorderLayout.CENTER);

		//  JTabbedPane — 3 onglets détaillés 
		JTabbedPane tabs = new JTabbedPane();
		tabs.setFont(FONT_LBL.deriveFont(Font.BOLD));
		tabs.setBackground(BG);

		//  Onglet 1 : Dernières ventes 
		String[] colsVentes = { "N° Vente", "Date", "Client", "Caissier", "Montant (€)" };
		vmVentes = new DefaultTableModel(colsVentes, 0) {
			public boolean isCellEditable(int r, int c) { return false; }
		};
		tabs.addTab("📋 Dernières ventes", buildTablePanel(vmVentes, null));

		//  Onglet 2 : Alertes stock 
		String[] colsAlertes = { "Réf.", "Désignation", "Type", "Stock actuel", "Seuil", "Statut" };
		vmAlertes = new DefaultTableModel(colsAlertes, 0) {
			public boolean isCellEditable(int r, int c) { return false; }
		};
		JScrollPane spAlertes = buildTablePanel(vmAlertes, (t, v, sel, foc, r, c) -> {
			String statut = (String) vmAlertes.getValueAt(r, 5);
			Color bg = "🔴 Rupture".equals(statut) ? new Color(0xFF7A86)
					 : "⚠️ Stock faible".equals(statut) ? new Color(0xACAD61)
					 : Color.WHITE;
			return new DefaultTableCellRenderer() {{
				setBackground(sel ? new Color(0xBBDEFB) : bg);
				setText(v == null ? "" : v.toString());
				setFont(FONT_LBL);
			}};
		});
		tabs.addTab("⚠️ Alertes stock", spAlertes);

		//  Onglet 3 : Produits périmés 
		String[] colsPerimes = { "Réf.", "Désignation", "Stock", "Date péremption", "Périmé depuis" };
		vmPerimes = new DefaultTableModel(colsPerimes, 0) {
			public boolean isCellEditable(int r, int c) { return false; }
		};
		tabs.addTab("🗓️ Produits périmés", buildTablePanel(vmPerimes, null));

		// Wrapper pour limiter la hauteur des onglets
		JPanel bottomWrapper = new JPanel(new BorderLayout());
		bottomWrapper.setBackground(new Color(0x46B3F2));
		bottomWrapper.setBorder(new EmptyBorder(0, 20, 16, 20));
		bottomWrapper.add(tabs, BorderLayout.CENTER);
		tdbPanel.add(bottomWrapper, BorderLayout.SOUTH);

		return tdbPanel;
	}

	/** Crée un JScrollPane contenant un JTable stylisé depuis un DefaultTableModel. */
	private JScrollPane buildTablePanel(DefaultTableModel model,
			TableCellRenderer renderer) {
		JTable t = new JTable(model);
		t.setFont(FONT_LBL);
		t.setRowHeight(24);
		t.getTableHeader().setBackground(PRIMARY);
		t.getTableHeader().setForeground(Color.WHITE);
		t.getTableHeader().setFont(FONT_LBL.deriveFont(Font.BOLD));
		t.setSelectionBackground(new Color(0xBBDEFB));
		t.setGridColor(new Color(0xE0E0E0));
		if (renderer != null) t.setDefaultRenderer(Object.class, renderer);
		JScrollPane sp = new JScrollPane(t);
		sp.setPreferredSize(new Dimension(0, 180));
		return sp;
	}

	/** Crée une KPI card avec titre et valeur. */
	private JPanel kpiCard(String titre, JLabel valLabel, Color accent) {
		JPanel card = new JPanel(new BorderLayout());
		card.setBackground(CARD_BG);
		card.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(0xE0E0E0), 1, true),
				new EmptyBorder(14, 18, 14, 18)));

		JLabel lblTitre = new JLabel(titre);
		lblTitre.setFont(FONT_LBL);
		lblTitre.setForeground(TEXT_SUB);
		card.add(lblTitre, BorderLayout.NORTH);

		valLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
		valLabel.setForeground(accent);
		card.add(valLabel, BorderLayout.CENTER);

		return card;
	}

	//  Rafraîchissement des données du tableau de bord 

	private void refreshDashboard() {
		ArrayList<Vente> ventes = gestionCaisseVente.getListeVentes();
		double ca = 0;
		for (Vente v : ventes) ca += v.calculerTotal();

		//  KPI 
		tdbCA.setText(String.format("%.2f €", ca));
		tdbVentes.setText(String.valueOf(ventes.size()));
		tdbClients.setText(String.valueOf(gestionClient.getListeClients().size()));
		tdbEmployes.setText(String.valueOf(gestionEmploye.getListeEmployes().size()));
		tdbProduits.setText(String.valueOf(gestionProduit.getListeDesProduits().size()));

		long alertes = gestionProduit.getListeDesProduits().stream()
				.filter(p -> p.getQuantiteStock() <= 10).count();
		tdbAlertes.setText(String.valueOf(alertes));
		tdbAlertes.setForeground(alertes > 0 ? RED_CLR : GREEN);

		ArrayList<ProduitFrais> perimes = gestionProduit.getProduitsPerimes();
		tdbPerimes.setText(String.valueOf(perimes.size()));
		tdbPerimes.setForeground(perimes.size() > 0 ? new Color(0x880E4F) : GREEN);

		//  Onglet 1 : Ventes 
		vmVentes.setRowCount(0);
		for (Vente v : ventes) {
			String clientNom  = v.getClient() != null ? v.getClient().getNom() : "—";
			String caissierNom = v.getCaissier() != null
					? v.getCaissier().getPrenom() + " " + v.getCaissier().getNom() : "—";
			vmVentes.addRow(new Object[]{
				v.getNumeroVente(),
				v.getDate(),
				clientNom,
				caissierNom,
				String.format("%.2f", v.calculerTotal())
			});
		}

		//  Onglet 2 : Alertes stock 
		vmAlertes.setRowCount(0);
		for (Produit p : gestionProduit.getListeDesProduits()) {
			if (p.getQuantiteStock() <= 10) {
				String type = (p instanceof ProduitFrais)        ? "Frais"
						     : (p instanceof ProduitElectronique) ? "Électronique" : "Artisanal";
				String statut = p.getQuantiteStock() == 0 ? "🔴 Rupture" : "⚠️ Stock faible";
				vmAlertes.addRow(new Object[]{
					p.getReference(), p.getDesignation(), type,
					p.getQuantiteStock(), 10, statut
				});
			}
		}

		//  Onglet 3 : Produits périmés 
		vmPerimes.setRowCount(0);
		for (ProduitFrais pf : perimes) {
			long jours = java.time.temporal.ChronoUnit.DAYS.between(
					pf.getDatePeremption(), java.time.LocalDate.now());
			vmPerimes.addRow(new Object[]{
				pf.getReference(), pf.getDesignation(), pf.getQuantiteStock(),
				pf.getDatePeremption(),
				jours + " jour(s)"
			});
		}
	}

	//  Navigation

	private void showCard(String name) {
		cardLayout.show(mainContainer, name);
		btnRetour.setVisible(!name.equals("MENU"));
	}

	//  Carte de menu (module)

	private JPanel buildCard(String titre, String description, ActionListener action) {
		JPanel card = new JPanel(new BorderLayout());
		card.setBackground(CARD_BG);
		card.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(0xE0E0E0), 1, true),
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
				@Override protected void paintComponent(Graphics g) {
					g.setColor(getBackground()); g.fillRect(0, 0, getWidth(), getHeight());
					super.paintComponent(g);
				}
			};
			btn.setFont(FONT_LBL.deriveFont(Font.BOLD));
			btn.setBackground(PRIMARY); btn.setForeground(Color.WHITE);
			btn.setContentAreaFilled(false); btn.setFocusPainted(false); btn.setBorderPainted(false);
			btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btn.addActionListener(action);
			btn.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) { btn.setBackground(ACCENT); }
				public void mouseExited(MouseEvent e)  { btn.setBackground(PRIMARY); }
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

	//  Bouton d'en-tête stylisé 

	private JButton headerBtn(String label, Color bg, Color hover) {
		JButton b = new JButton(label) {
			@Override protected void paintComponent(Graphics g) {
				g.setColor(getBackground()); g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		b.setFont(FONT_LBL); b.setBackground(bg); b.setForeground(Color.WHITE);
		b.setContentAreaFilled(false); b.setFocusPainted(false); b.setBorderPainted(false);
		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
			public void mouseExited(MouseEvent e)  { b.setBackground(bg); }
		});
		return b;
	}

	// Renderer fonctionnel pour les tableaux colorés
	@FunctionalInterface
	interface TableCellRenderer extends javax.swing.table.TableCellRenderer {
		Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c);
	}
}
