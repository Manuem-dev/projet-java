package interfaceGraphique;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

import service.GestionProduit;
import model.*;

/**
 * Panneau de gestion des produits.
 * Sélection du type via pop-up de choix, champs dynamiques selon le type.
 */
public class FenetreProduit extends JPanel {

    private static final Color BG      = new Color(0xF5F5F5);
    private static final Color PRIMARY = new Color(0x1565C0);
    private static final Color ACCENT  = new Color(0x0D47A1);
    private static final Color TEXT_FG = new Color(0x212121);
    private static final Font  FONT_LBL= new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font  FONT_TTL= new Font("Segoe UI", Font.BOLD, 16);

    private GestionProduit gestionProduit;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField tfRecherche;

    // Champs formulaire
    private JTextField tfRef, tfDesignation, tfQte, tfPrixAchat, tfPrixVente;
    private JLabel lblType;

    public FenetreProduit(GestionProduit gestionProduit) {
        this.gestionProduit = gestionProduit;
        this.setBackground(BG);
        setLayout(new BorderLayout(0, 0));
        buildUI();
        refreshTable(gestionProduit.getListeDesProduits());
    }

    private void buildUI() {
        // En-tête
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(PRIMARY);
        header.setBorder(new EmptyBorder(10, 16, 10, 16));
        JLabel titre = new JLabel("📦  Gestion des Produits");
        titre.setFont(FONT_TTL); titre.setForeground(Color.WHITE);
        header.add(titre);
        add(header, BorderLayout.NORTH);

        // Table + barre de recherche
        String[] cols = {"Réf.", "Désignation", "Type", "Qté stock", "Prix achat", "Prix vente", "Marge"};
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
        tfRecherche = new JTextField(22); tfRecherche.setFont(FONT_LBL);
        JButton btnRech = blueBtn("🔍 Rechercher");
        JButton btnTout = blueBtn("Tout afficher");
        btnRech.addActionListener(e -> {
            String mot = tfRecherche.getText().trim();
            refreshTable(mot.isEmpty() ? gestionProduit.getListeDesProduits()
                                       : gestionProduit.rechercherParDesignation(mot));
        });
        btnTout.addActionListener(e -> { tfRecherche.setText(""); refreshTable(gestionProduit.getListeDesProduits()); });
        barreRecherche.add(new JLabel("Recherche :"));
        barreRecherche.add(tfRecherche);
        barreRecherche.add(btnRech);
        barreRecherche.add(btnTout);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BG);
        tablePanel.add(barreRecherche, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        // Formulaire bas
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(new Color(0xBDBDBD), 1, true), " Ajouter / Modifier un produit ",
                TitledBorder.LEFT, TitledBorder.TOP, FONT_LBL.deriveFont(Font.BOLD), PRIMARY));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 7, 5, 7);
        g.fill = GridBagConstraints.HORIZONTAL;

        // Ligne 0
        g.gridx=0; g.gridy=0; formPanel.add(lbl("Type de produit :"), g);
        g.gridx=1;
        lblType = new JLabel("— cliquez sur Choisir le type →");
        lblType.setFont(FONT_LBL.deriveFont(Font.ITALIC));
        lblType.setForeground(new Color(0x9E9E9E));
        formPanel.add(lblType, g);
        g.gridx=2;
        JButton btnChoisirType = blueBtn("Choisir le type…");
        btnChoisirType.addActionListener(e -> choisirTypeProduit());
        formPanel.add(btnChoisirType, g);
        g.gridx=3; formPanel.add(lbl("Réf. :"), g);
        g.gridx=4; tfRef = new JTextField(6); formPanel.add(tfRef, g);

        // Ligne 1
        g.gridx=0; g.gridy=1; formPanel.add(lbl("Désignation :"), g);
        g.gridx=1; g.gridwidth=4; tfDesignation = new JTextField(26); formPanel.add(tfDesignation, g);
        g.gridwidth=1;

        // Ligne 2
        g.gridx=0; g.gridy=2; formPanel.add(lbl("Quantité :"), g);
        g.gridx=1; tfQte = new JTextField(7); formPanel.add(tfQte, g);
        g.gridx=2; formPanel.add(lbl("Prix achat (€) :"), g);
        g.gridx=3; tfPrixAchat = new JTextField(7); formPanel.add(tfPrixAchat, g);
        g.gridx=4; tfPrixVente = new JTextField(7);
        formPanel.add(lbl("Prix vente (€) :"), g);
        // Ligne 3
        g.gridx=0; g.gridy=3; formPanel.add(lbl("Prix vente (€) :"), g);
        g.gridx=1; tfPrixVente = new JTextField(7); formPanel.add(tfPrixVente, g);

        // Boutons
        g.gridx=0; g.gridy=4; g.gridwidth=5;
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        btnRow.setBackground(Color.WHITE);
        JButton btnAjouter   = blueBtn("➕ Ajouter");
        JButton btnModifier  = blueBtn("✏️ Modifier");
        JButton btnSupprimer = redBtn("🗑️ Supprimer");
        JButton btnVider     = grayBtn("Vider");
        btnAjouter.addActionListener(e   -> ajouterProduit());
        btnModifier.addActionListener(e  -> modifierProduit());
        btnSupprimer.addActionListener(e -> supprimerProduit());
        btnVider.addActionListener(e     -> viderFormulaire());
        btnRow.add(btnAjouter); btnRow.add(btnModifier); btnRow.add(btnSupprimer); btnRow.add(btnVider);
        formPanel.add(btnRow, g);
        add(formPanel, BorderLayout.SOUTH);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { chargerLigne(); }
        });
    }

    /** Pop-up de sélection du type de produit */
    private String selectedType = null;

    private void choisirTypeProduit() {
        String[] types = {"📦 Artisanal", "⚡ Électronique", "❄️ Frais"};
        JList<String> list = new JList<>(types);
        list.setFont(FONT_LBL.deriveFont(15f));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(selectedType == null ? 0 :
                selectedType.equals("Artisanal") ? 0 : selectedType.equals("Électronique") ? 1 : 2);

        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.add(new JLabel("Sélectionnez le type de produit :"), BorderLayout.NORTH);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        int res = JOptionPane.showConfirmDialog(this, panel, "Type de produit",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION && list.getSelectedIndex() >= 0) {
            String[] keys = {"Artisanal", "Électronique", "Frais"};
            selectedType = keys[list.getSelectedIndex()];
            lblType.setText(types[list.getSelectedIndex()]);
            lblType.setForeground(PRIMARY);
            lblType.setFont(FONT_LBL.deriveFont(Font.BOLD));
        }
    }

    private void refreshTable(ArrayList<Produit> liste) {
        tableModel.setRowCount(0);
        for (Produit p : liste) {
            String type = (p instanceof ProduitFrais) ? "Frais"
                    : (p instanceof ProduitElectronique) ? "Électronique" : "Artisanal";
            tableModel.addRow(new Object[]{
                    p.getReference(), p.getDesignation(), type,
                    p.getQuantiteStock(),
                    String.format("%.2f €", p.getPrixAchat()),
                    String.format("%.2f €", p.getPrixVente()),
                    String.format("%.2f €", p.calculerMarge())
            });
        }
    }

    private void ajouterProduit() {
        if (selectedType == null) {
            JOptionPane.showMessageDialog(this, "Veuillez d'abord choisir le type de produit.", "Type manquant", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int ref    = Integer.parseInt(tfRef.getText().trim());
            int qte    = Integer.parseInt(tfQte.getText().trim());
            double pa  = Double.parseDouble(tfPrixAchat.getText().trim().replace(",", "."));
            double pv  = Double.parseDouble(tfPrixVente.getText().trim().replace(",", "."));
            String des = tfDesignation.getText().trim();
            if (des.isEmpty()) throw new IllegalArgumentException("Désignation vide");

            switch (selectedType) {
                case "Artisanal":
                    // Pop-up pour les champs artisanal
                    JTextField tfCateg = new JTextField(12);
                    JTextField tfArtisan = new JTextField(12);
                    JTextField tfOrigine = new JTextField(12);
                    JPanel pa_form = new JPanel(new GridLayout(3, 2, 6, 6));
                    pa_form.add(new JLabel("Catégorie :")); pa_form.add(tfCateg);
                    pa_form.add(new JLabel("Artisan :")); pa_form.add(tfArtisan);
                    pa_form.add(new JLabel("Origine :")); pa_form.add(tfOrigine);
                    if (JOptionPane.showConfirmDialog(this, pa_form, "Détails Artisanal", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
                        gestionProduit.ajoutProduitArtisanal(ref, qte, pa, pv, des, tfCateg.getText(), tfArtisan.getText(), tfOrigine.getText());
                    break;
                case "Électronique":
                    JTextField tfMarque = new JTextField(12);
                    JTextField tfGarantie = new JTextField(6);
                    JPanel pe_form = new JPanel(new GridLayout(2, 2, 6, 6));
                    pe_form.add(new JLabel("Marque :")); pe_form.add(tfMarque);
                    pe_form.add(new JLabel("Garantie (mois) :")); pe_form.add(tfGarantie);
                    if (JOptionPane.showConfirmDialog(this, pe_form, "Détails Électronique", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        int gar = tfGarantie.getText().trim().isEmpty() ? 12 : Integer.parseInt(tfGarantie.getText().trim());
                        gestionProduit.ajoutProduitElectronique(ref, qte, pa, pv, des, tfMarque.getText(), gar);
                    }
                    break;
                case "Frais":
                    JTextField tfTemp = new JTextField("4.0", 6);
                    JTextField tfDate = new JTextField(LocalDate.now().plusMonths(1).toString(), 12);
                    JPanel pf_form = new JPanel(new GridLayout(2, 2, 6, 6));
                    pf_form.add(new JLabel("Température (°C) :")); pf_form.add(tfTemp);
                    pf_form.add(new JLabel("Date péremption (YYYY-MM-DD) :")); pf_form.add(tfDate);
                    if (JOptionPane.showConfirmDialog(this, pf_form, "Détails Frais", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        double temp = Double.parseDouble(tfTemp.getText().trim().replace(",", "."));
                        LocalDate date = LocalDate.parse(tfDate.getText().trim());
                        gestionProduit.ajoutProduitFrais(ref, qte, pa, pv, des, temp, date);
                    }
                    break;
            }
            refreshTable(gestionProduit.getListeDesProduits());
            viderFormulaire();
            JOptionPane.showMessageDialog(this, "Produit ajouté !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierProduit() {
        try {
            int ref = Integer.parseInt(tfRef.getText().trim());
            int qte = Integer.parseInt(tfQte.getText().trim());
            double pa = Double.parseDouble(tfPrixAchat.getText().trim().replace(",", "."));
            double pv = Double.parseDouble(tfPrixVente.getText().trim().replace(",", "."));
            String des = tfDesignation.getText().trim();
            gestionProduit.modifierProduit(ref, qte, pa, pv, des);
            refreshTable(gestionProduit.getListeDesProduits());
            viderFormulaire();
            JOptionPane.showMessageDialog(this, "Produit modifié !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerProduit() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Sélectionnez un produit.", "Info", JOptionPane.WARNING_MESSAGE); return; }
        int ref = (int) tableModel.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Supprimer ce produit ?", "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            gestionProduit.supprimerProduit(ref);
            refreshTable(gestionProduit.getListeDesProduits());
            viderFormulaire();
        }
    }

    private void chargerLigne() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        tfRef.setText(String.valueOf(tableModel.getValueAt(row, 0)));
        tfDesignation.setText((String) tableModel.getValueAt(row, 1));
        String type = (String) tableModel.getValueAt(row, 2);
        selectedType = type;
        lblType.setText(type.equals("Frais") ? "❄️ Frais" : type.equals("Électronique") ? "⚡ Électronique" : "📦 Artisanal");
        lblType.setForeground(PRIMARY); lblType.setFont(FONT_LBL.deriveFont(Font.BOLD));
        tfQte.setText(String.valueOf(tableModel.getValueAt(row, 3)));
        tfPrixAchat.setText(((String) tableModel.getValueAt(row, 4)).replace(" €", ""));
        tfPrixVente.setText(((String) tableModel.getValueAt(row, 5)).replace(" €", ""));
    }

    private void viderFormulaire() {
        tfRef.setText(""); tfDesignation.setText("");
        tfQte.setText(""); tfPrixAchat.setText(""); tfPrixVente.setText("");
        selectedType = null;
        lblType.setText("— cliquez sur Choisir le type →");
        lblType.setForeground(new Color(0x9E9E9E));
        lblType.setFont(FONT_LBL.deriveFont(Font.ITALIC));
    }

    // Helpers UI
    private JLabel lbl(String t) { JLabel l = new JLabel(t); l.setFont(FONT_LBL); l.setForeground(TEXT_FG); return l; }
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
        b.setBackground(PRIMARY); b.setForeground(Color.WHITE);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(ACCENT); }
            public void mouseExited(MouseEvent e)  { b.setBackground(PRIMARY); }
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
        b.setBackground(new Color(0xC62828)); b.setForeground(Color.WHITE);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(new Color(0xB71C1C)); }
            public void mouseExited(MouseEvent e)  { b.setBackground(new Color(0xC62828)); }
        });
        return b;
    }
    private JButton grayBtn(String t) {
        JButton b = new JButton(t) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        b.setFont(FONT_LBL);
        b.setBackground(new Color(0x757575)); b.setForeground(Color.WHITE);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(new Color(0x616161)); }
            public void mouseExited(MouseEvent e)  { b.setBackground(new Color(0x757575)); }
        });
        return b;
    }
}
