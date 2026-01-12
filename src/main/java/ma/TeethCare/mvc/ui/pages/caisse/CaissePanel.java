package ma.TeethCare.mvc.ui.pages.caisse;

import ma.TeethCare.mvc.controllers.modules.caisse.api.CaisseController;
import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.service.modules.caisse.api.chargesService;
import ma.TeethCare.service.modules.caisse.api.revenuesService;
import ma.TeethCare.service.modules.caisse.api.situationFinanciereService;
import ma.TeethCare.mvc.ui.palette.utils.UIConstants;

import javax.swing.*;
import java.awt.*;

public class CaissePanel extends JPanel {

    private final CaisseController controller;
    private final chargesService chargesService;
    private final revenuesService revenuesService;
    private final situationFinanciereService sfService;
    private final UserPrincipal principal;

    public CaissePanel(CaisseController controller, chargesService chargesService, revenuesService revenuesService, situationFinanciereService sfService, UserPrincipal principal) {
        this.controller = controller;
        this.chargesService = chargesService;
        this.revenuesService = revenuesService;
        this.sfService = sfService;
        this.principal = principal;

        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createHeader(), BorderLayout.NORTH);
        add(createTabs(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        JLabel title = new JLabel("Caisse & Finances");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(UIConstants.TEXT_DARK);

        header.add(title, BorderLayout.WEST);
        return header;
    }

    private JComponent createTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Placeholder panels for tabs - implementing full logic for all would exceed scope in one shot, 
        // but structure is here.
        tabs.addTab("Revenus", createRevenusPanel());
        tabs.addTab("Charges", createChargesPanel());
        tabs.addTab("Situation Financière", createSituationPanel());

        return tabs;
    }

    private JPanel createRevenusPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.add(new JLabel("Liste des revenus (Consultations, Actes...)", SwingConstants.CENTER), BorderLayout.CENTER);
        // Table logic similar to PatientPanel would go here
        return p;
    }

    private JPanel createChargesPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.add(new JLabel("Liste des charges (Achats, Salaires...)", SwingConstants.CENTER), BorderLayout.CENTER);
        return p;
    }
    
    private JPanel createSituationPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.add(new JLabel("Bilan et statistiques financières", SwingConstants.CENTER), BorderLayout.CENTER);
        return p;
    }
}
