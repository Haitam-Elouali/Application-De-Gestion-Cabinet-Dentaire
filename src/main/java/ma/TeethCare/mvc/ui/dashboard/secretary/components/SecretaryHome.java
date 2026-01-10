package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.containers.RoundedPanel;
import ma.TeethCare.mvc.ui.palette.utils.IconUtils;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class SecretaryHome extends JPanel {

    public SecretaryHome() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(10, 0, 10, 0)); // Global Padding
        initUI();
    }

    private void initUI() {
        // Main Container with MigLayout
        // "wrap 2" = 2 columns grid
        // "[70%] [30%]" = Column sizing (Left ~70%, Right ~30%)
        // "fill" = Components grow to fill cells
        JPanel mainContainer = new JPanel(new MigLayout("insets 0, gap 20, fill", "[grow, fill]20[320!]", "[140!]20[grow, fill]"));
        mainContainer.setOpaque(false);

        // --- ROW 1: STATS (Span 2 to take full width initially, but reference shows 3 cards. 
        // Actually reference has 3 cards: RDV, File, Recettes. They sit above the columns? 
        // Let's put them in a dedicated top container spanning all columns.
        
        JPanel statsContainer = new JPanel(new MigLayout("insets 0, gap 20, fill", "[grow][grow][grow]")); 
        statsContainer.setOpaque(false);
        
        // Card 1: RDV
        statsContainer.add(createStatCard("RDV AUJOURD'HUI", "12", "", 
            new Color(220, 252, 231), new Color(22, 163, 74), IconUtils.IconType.CALENDAR), "grow"); // Green

        // Card 2: FILE D'ATTENTE
        statsContainer.add(createStatCard("FILE D'ATTENTE", "2", "en salle", 
            new Color(255, 237, 213), new Color(234, 88, 12), IconUtils.IconType.USERS), "grow"); // Orange

        // Card 3: RECETTES
        statsContainer.add(createStatCard("RECETTES DU JOUR", "850,00 €", "", 
            new Color(219, 234, 254), new Color(37, 99, 235), IconUtils.IconType.BUILDING), "grow"); // Blue

        mainContainer.add(statsContainer, "span 2, wrap");

        // --- ROW 2: CONTENT
        
        // LEFT COLUMN (Prochains RDV + Agenda)
        JPanel leftCol = new JPanel(new MigLayout("insets 0, gap 20, fill, flowy", "[grow]", "[grow][grow]"));
        leftCol.setOpaque(false);

        leftCol.add(createUpcomingRDVWidget(), "grow");
        leftCol.add(createAgendaCalendarWidget(), "grow");

        mainContainer.add(leftCol, "grow");

        // RIGHT COLUMN (File d'attente + Widgets)
        JPanel rightCol = new JPanel(new MigLayout("insets 0, gap 20, fill, flowy", "[grow]", "[grow][grow]"));
        rightCol.setOpaque(false);
        
        rightCol.add(createWaitingListWidget(), "grow");
        rightCol.add(createNotificationsWidget(), "grow, h 250!"); // Fixed smaller height for notifs

        mainContainer.add(rightCol, "grow");

        // ScrollPane for the whole dashboard
        JScrollPane scroll = new JScrollPane(mainContainer);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        // Faster scrolling
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);
    }
    
    // --- Components ---

    private JPanel createStatCard(String title, String value, String sub, Color iconBg, Color iconFg, IconUtils.IconType icon) {
        RoundedPanel p = new RoundedPanel(20); // 20px radius
        p.setBackground(Color.WHITE);
        p.setLayout(new MigLayout("insets 24, fill", "[]push[]", "[]12[]"));
        
        // Header (Title)
        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLbl.setForeground(new Color(107, 114, 128)); // Gray 500
        p.add(titleLbl);
        
        // Icon
        JPanel iconPanel = new JPanel(new BorderLayout());
        iconPanel.setOpaque(false); // custom paint
        JLabel l = new JLabel(IconUtils.getIcon(icon, 20, iconFg)) {
             @Override
             protected void paintComponent(Graphics g) {
                 Graphics2D g2 = (Graphics2D)g;
                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                 g2.setColor(iconBg);
                 g2.fillOval(0,0,getWidth(), getHeight());
                 super.paintComponent(g);
             }
        };
        l.setBorder(new EmptyBorder(8,8,8,8));
        l.setPreferredSize(new Dimension(36, 36)); 
        p.add(l, "wrap");
        
        // Value
        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLbl.setForeground(new Color(31, 41, 55)); // Gray 800
        p.add(valueLbl);
        
        // Subtext
        if (!sub.isEmpty()) {
            JLabel subLbl = new JLabel(sub);
            subLbl.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            subLbl.setForeground(new Color(156, 163, 175)); // Gray 400
            p.add(subLbl, "align right"); 
        }

        return p;
    }

    private JPanel createUpcomingRDVWidget() {
        RoundedPanel p = new RoundedPanel(20);
        p.setBackground(Color.WHITE);
        p.setLayout(new MigLayout("insets 20, fillx, flowy", "[grow]", "[]16[]"));
        
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel t = new JLabel("Prochains Rendez-vous");
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JLabel seeAll = new JLabel("Voir tout");
        seeAll.setFont(new Font("Segoe UI", Font.BOLD, 12));
        seeAll.setForeground(new Color(22, 163, 74));
        seeAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(t, BorderLayout.WEST);
        header.add(seeAll, BorderLayout.EAST);
        p.add(header, "growx");

        // List Container
        JPanel list = new JPanel(new MigLayout("insets 0, fillx, gapy 0", "[grow]")); 
        list.setOpaque(false);
        
        list.add(createRdvRow("14:00", "Sophie Bernard", "Soin Carie - Salle 2", new Color(220, 252, 231), new Color(22, 101, 52)), "growx, wrap");
        list.add(createRdvRow("14:45", "Lucas Martin", "Consultation - Salle 1", new Color(219, 234, 254), new Color(30, 64, 175)), "growx, wrap");
        list.add(createRdvRow("15:30", "Emma Petit", "Contrôle - Salle 2", new Color(255, 237, 213), new Color(154, 52, 18)), "growx, wrap");

        p.add(list, "growx");
        return p;
    }

    private JPanel createRdvRow(String time, String name, String detail, Color pillBg, Color pillFg) {
        JPanel r = new JPanel(new MigLayout("insets 12 0 12 0, fillx", "[][grow]", "[]"));
        r.setOpaque(false);
        r.setBorder(BorderFactory.createMatteBorder(0,0,1,0, new Color(243, 244, 246))); // Separator
        
        // Time Pill
        JLabel timeLbl = new JLabel(time);
        timeLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        timeLbl.setForeground(pillFg);
        timeLbl.setOpaque(false);
        
        JPanel pill = new JPanel(new BorderLayout());
        pill.setOpaque(false); 
        pill.add(timeLbl);
        pill.setBorder(new EmptyBorder(4, 8, 4, 8));
        
        // Custom Pill BG
        JPanel pillContainer = new JPanel(new BorderLayout()) {
             @Override
             protected void paintComponent(Graphics g) {
                 Graphics2D g2 = (Graphics2D)g;
                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                 g2.setColor(pillBg);
                 g2.fillRoundRect(0,0,getWidth(), getHeight(), 10, 10);
                 super.paintComponent(g);
             }
        };
        pillContainer.setOpaque(false);
        pillContainer.add(pill);
        
        r.add(pillContainer);
        
        // Text
        JPanel txt = new JPanel(new MigLayout("insets 0, gap 0", "[]", "[]0[]"));
        txt.setOpaque(false);
        JLabel n = new JLabel(name);
        n.setFont(new Font("Segoe UI", Font.BOLD, 14));
        n.setForeground(new Color(31, 41, 55));
        JLabel d = new JLabel(detail);
        d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        d.setForeground(new Color(107, 114, 128));
        txt.add(n, "wrap");
        txt.add(d);
        
        r.add(txt);
        
        return r;
    }

    private JPanel createAgendaCalendarWidget() {
        RoundedPanel p = new RoundedPanel(20);
        p.setBackground(Color.WHITE);
        p.setLayout(new MigLayout("insets 20, fill", "[grow][grow][grow]", "[]12[grow]"));
        
        JLabel t = new JLabel("Agenda Médecin");
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        p.add(t, "span 3, wrap");
        
        // Columns
        p.add(createDayColumn("Lundi", "12", new String[]{"09:00 - Dispo", "11:00 - M. X"}), "grow");
        p.add(createDayColumn("Mardi", "13", new String[]{"09:30 - Mme Y", "14:00 - Dispo"}), "grow");
        p.add(createDayColumn("Mercredi", "14", new String[]{"Congé"}), "grow");

        return p;
    }
    
    private JPanel createDayColumn(String name, String num, String[] slots) {
        JPanel c = new JPanel(new MigLayout("insets 0, flowy, fillx", "[grow]", "[]4[]8[]"));
        c.setOpaque(false);
        
        JLabel n = new JLabel(name);
        n.setFont(new Font("Segoe UI", Font.BOLD, 12));
        n.setForeground(new Color(156, 163, 175));
        
        JLabel nb = new JLabel(num);
        nb.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        c.add(n, "center");
        c.add(nb, "center");
        
        for (String s : slots) {
            boolean isDispo = s.contains("Dispo");
            Color bg = isDispo ? new Color(240, 253, 244) : new Color(239, 246, 255);
            Color border = isDispo ? new Color(22, 163, 74) : new Color(37, 99, 235);
            
            JLabel sl = new JLabel(s);
            sl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            sl.setBorder(new EmptyBorder(4, 8, 4, 8));
            
            JPanel sp = new JPanel(new BorderLayout());
            sp.setOpaque(false);
            sp.add(sl);
            
             JPanel slotContainer = new JPanel(new BorderLayout()) {
                 @Override
                 protected void paintComponent(Graphics g) {
                     Graphics2D g2 = (Graphics2D)g;
                     g2.setColor(bg);
                     g2.fillRect(0,0,getWidth(), getHeight());
                     g2.setColor(border);
                     g2.fillRect(0,0, 3, getHeight()); // Left border thick
                     super.paintComponent(g);
                 }
            };
            slotContainer.add(sp);
            
            c.add(slotContainer, "growx");
        }
        
        return c;
    }

    private JPanel createWaitingListWidget() {
        RoundedPanel p = new RoundedPanel(20);
        p.setBackground(Color.WHITE);
        p.setLayout(new MigLayout("insets 20, fillx, flowy", "[grow]", "[]16[]push[]"));
        
        JLabel t = new JLabel("File d'attente");
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        p.add(t, "growx");
        
        JPanel list = new JPanel(new MigLayout("insets 0, fillx, gapy 0", "[grow]"));
        list.setOpaque(false);
        
        list.add(createWaitingRow("09:30", "Jean Dupont", "Salle d'attente", "Arrivé"), "growx, wrap");
        list.add(createWaitingRow("10:20", "Marie Curie", "Salle 1", "Arrivé 10:20"), "growx, wrap");
        list.add(createWaitingRow("10:45", "Paul Antoine", "Acceuil", "Arrivé 10:45"), "growx, wrap");
        
        p.add(list, "growx");
        
        JLabel more = new JLabel("Voir la file complète");
        more.setFont(new Font("Segoe UI", Font.BOLD, 12));
        more.setForeground(new Color(37, 99, 235)); // Blue 600
        more.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p.add(more, "center");

        return p;
    }

    private JPanel createWaitingRow(String time, String name, String loc, String status) {
        JPanel row = new JPanel(new MigLayout("insets 12 0 12 0, fillx", "[][grow]", "[]"));
        row.setOpaque(false);
        row.setBorder(BorderFactory.createMatteBorder(0,0,1,0, new Color(243, 244, 246)));

        JLabel t = new JLabel(time);
        t.setFont(new Font("Segoe UI", Font.BOLD, 13));
        t.setForeground(new Color(31, 41, 55));
        row.add(t);
        
        JPanel info = new JPanel(new MigLayout("insets 0", "[]", "[]0[]"));
        info.setOpaque(false);
        JLabel n = new JLabel(name + " (" + loc + ")");
        n.setFont(new Font("Segoe UI", Font.BOLD, 13));
        JLabel s = new JLabel(status);
        s.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        s.setForeground(new Color(107, 114, 128));
        info.add(n, "wrap");
        info.add(s);
        
        row.add(info);
        return row;
    }

    private JPanel createNotificationsWidget() {
        RoundedPanel p = new RoundedPanel(20);
        p.setBackground(Color.WHITE);
        p.setLayout(new MigLayout("insets 20, fillx, flowy", "[grow]", "[]12[]"));
        
        JLabel t = new JLabel("Notifications");
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        p.add(t);
        
        JPanel list = new JPanel(new MigLayout("insets 0, fillx, gapy 8", "[grow]"));
        list.setOpaque(false);
        
        list.add(createNotifRow("Résultats reçus", TailwindPalette.GREEN_100), "growx, wrap");
        list.add(createNotifRow("Rappel réunion 15h", TailwindPalette.BLUE_100), "growx, wrap");
        list.add(createNotifRow("Facture impayée", TailwindPalette.ORANGE_100), "growx, wrap");
        
        p.add(list, "growx");
        return p;
    }
    
    private JPanel createNotifRow(String txt, Color bg) {
        JPanel r = new JPanel(new BorderLayout());
        r.setBackground(bg); // Use solid color or rounded?
        // Let's make it rounded too
         JPanel container = new JPanel(new BorderLayout()) {
             @Override
             protected void paintComponent(Graphics g) {
                 Graphics2D g2 = (Graphics2D)g;
                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                 g2.setColor(bg);
                 g2.fillRoundRect(0,0,getWidth(), getHeight(), 8, 8);
                 super.paintComponent(g);
             }
        };
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(8, 12, 8, 12));
        
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        container.add(l, BorderLayout.CENTER);
        
        return container;
    }
}
