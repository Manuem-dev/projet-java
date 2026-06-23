package interfaceGraphique;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import service.*;
import model.Utilisateur;

/**
 * Fenêtre de connexion — reçoit tous les services déjà peuplés depuis Application.java.
 */
public class Accueil extends JFrame {

    private static final Color BG      = new Color(0xF5F5F5);
    private static final Color PRIMARY = new Color(0x1565C0);
    private static final Color ACCENT  = new Color(0x0D47A1);
    private static final Color TEXT_FG = new Color(0x212121);
    private static final Font  FONT_LBL = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font  FONT_TTL = new Font("Segoe UI", Font.BOLD, 24);

    private JTextField tfLogin;
    private JPasswordField pfMotDePasse;
    private JButton btnConnecter;
    private JLabel lblMessage;

    private GestionUtilisateur gestionUtilisateur;
    // Tous les services partagés
    private GestionProduit      gestionProduit;
    private GestionEmploye      gestionEmploye;
    private GestionClient       gestionClient;
    private GestionRayon        gestionRayon;
    private GestionCaisseVente  gestionCaisseVente;
    private GestionFacture      gestionFacture;

    /** Constructeur simplifié (legacy) */
    public Accueil(GestionUtilisateur gestionUtilisateur) {
        this(gestionUtilisateur,
             new GestionProduit(), new GestionEmploye(), new GestionClient(),
             new GestionRayon(), new GestionCaisseVente(), new GestionFacture());
    }

    /** Constructeur complet — reçoit les services déjà peuplés */
    public Accueil(GestionUtilisateur gestionUtilisateur,
                   GestionProduit gestionProduit,
                   GestionEmploye gestionEmploye,
                   GestionClient gestionClient,
                   GestionRayon gestionRayon,
                   GestionCaisseVente gestionCaisseVente,
                   GestionFacture gestionFacture) {
        this.gestionUtilisateur = gestionUtilisateur;
        this.gestionProduit     = gestionProduit;
        this.gestionEmploye     = gestionEmploye;
        this.gestionClient      = gestionClient;
        this.gestionRayon       = gestionRayon;
        this.gestionCaisseVente = gestionCaisseVente;
        this.gestionFacture     = gestionFacture;

        setTitle("SuperMarché — Connexion");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 560);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {
        // ── En-tête ──────────────────────────────────────────────────────
        JPanel header = new JPanel();
        header.setBackground(PRIMARY);
        header.setBorder(new EmptyBorder(30, 24, 30, 24));
        JLabel titre = new JLabel("🛒  Gestion Supermarché");
        titre.setFont(FONT_TTL);
        titre.setForeground(Color.WHITE);
        header.add(titre);
        add(header, BorderLayout.NORTH);

        // ── Formulaire ───────────────────────────────────────────────────
        JPanel formWrapper = new JPanel(new GridBagLayout());
        formWrapper.setBackground(BG);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(0xDDDDDD), 1, true),
                new EmptyBorder(30, 40, 30, 40)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        // Titre du formulaire
        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2;
        JLabel formTitle = new JLabel("Connexion", SwingConstants.CENTER);
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        formTitle.setForeground(PRIMARY);
        form.add(formTitle, gbc);

        // Login
        gbc.gridwidth=1; gbc.gridy=1; gbc.gridx=0; gbc.weightx=0;
        form.add(label("Login :"), gbc);
        gbc.gridx=1; gbc.weightx=1;
        tfLogin = styledTextField();
        form.add(tfLogin, gbc);

        // Mot de passe
        gbc.gridx=0; gbc.gridy=2; gbc.weightx=0;
        form.add(label("Mot de passe :"), gbc);
        gbc.gridx=1; gbc.weightx=1;
        pfMotDePasse = new JPasswordField(15);
        styleTextField(pfMotDePasse);
        form.add(pfMotDePasse, gbc);

        // Message feedback
        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2;
        lblMessage = new JLabel(" ");
        lblMessage.setForeground(Color.RED);
        lblMessage.setFont(FONT_LBL.deriveFont(Font.ITALIC, 12f));
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        form.add(lblMessage, gbc);

        // Bouton
        gbc.gridy=4;
        btnConnecter = new JButton("Se connecter") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnConnecter.setFont(FONT_LBL.deriveFont(Font.BOLD, 14f));
        btnConnecter.setBackground(PRIMARY);
        btnConnecter.setForeground(Color.WHITE);
        btnConnecter.setContentAreaFilled(false);
        btnConnecter.setFocusPainted(false);
        btnConnecter.setBorderPainted(false);
        btnConnecter.setPreferredSize(new Dimension(200, 42));
        btnConnecter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnConnecter.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnConnecter.setBackground(ACCENT); }
            public void mouseExited(MouseEvent e)  { btnConnecter.setBackground(PRIMARY); }
        });
        btnConnecter.addActionListener(e -> tentativeConnexion());
        pfMotDePasse.addActionListener(e -> tentativeConnexion());
        form.add(btnConnecter, gbc);

        // Hint comptes
        gbc.gridy=5;
        JLabel hint = new JLabel("<html><center><font color='#9E9E9E'>directeur/dir2024 · caissier1/caisse1 · comptable/compta1 · magasinier/stock123</font></center></html>", SwingConstants.CENTER);
        hint.setFont(FONT_LBL.deriveFont(11f));
        form.add(hint, gbc);

        formWrapper.add(form);
        add(formWrapper, BorderLayout.CENTER);
    }

    private void tentativeConnexion() {
        String login = tfLogin.getText().trim();
        String mdp   = new String(pfMotDePasse.getPassword()).trim();
        if (login.isEmpty() || mdp.isEmpty()) {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("Veuillez remplir tous les champs.");
            return;
        }
        Utilisateur u = gestionUtilisateur.connecter(login, mdp);
        if (u != null) {
            lblMessage.setForeground(new Color(0x2E7D32));
            lblMessage.setText("Bienvenue, " + u.getEmploye().getPrenom() + " !");
            SwingUtilities.invokeLater(() -> {
                new Dashboard(gestionUtilisateur, gestionProduit, gestionEmploye,
                              gestionClient, gestionRayon, gestionCaisseVente, gestionFacture)
                    .setVisible(true);
                dispose();
            });
        } else {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("Login ou mot de passe incorrect.");
        }
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_LBL);
        l.setForeground(TEXT_FG);
        return l;
    }

    private JTextField styledTextField() {
        JTextField tf = new JTextField(15);
        styleTextField(tf);
        return tf;
    }

    private void styleTextField(JTextField tf) {
        tf.setFont(FONT_LBL);
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(0xBDBDBD), 1, true),
                new EmptyBorder(6, 10, 6, 10)));
    }
}
