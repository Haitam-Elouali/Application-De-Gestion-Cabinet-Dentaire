package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.dashboard.doctor.components.StatsCard;
import ma.TeethCare.mvc.ui.palette.containers.ModernCard;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SecretaryHome extends JPanel {

    public SecretaryHome() {
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent to show parent Green-50 background parent might handle
        setBorder(new EmptyBorder(24, 24, 24, 24));

        initUI();
    }

    private void initUI() {
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setOpaque(false);

        // 1. Header (Date + Title)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel title = new JLabel("Tableau de Bord");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.decode("#1f2937")); // gray-800
        
        JLabel date = new JLabel("Jeudi 8 janvier 2026"); // Mock date
        date.setFont(new Font("Segoe UI", Font.BOLD, 14));
        date.setForeground(Color.GRAY);
        
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(date, BorderLayout.EAST);
        
        mainContainer.add(headerPanel);
        mainContainer.add(Box.createVerticalStrut(24));

        // 2. Stats Cards
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 24, 0));
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // RDV Aujourd'hui (Green)
        statsPanel.add(new StatsCard("RDV Aujourd'hui", "12", StatsCard.Type.GREEN));
        
        // File d'attente (Orange) - Reuse StatsCard or create custom if needed
        statsPanel.add(new StatsCard("File d'attente", "3", StatsCard.Type.ORANGE));
        
        // Recettes du jour (Blue)
        statsPanel.add(new StatsCard("Recettes du jour", "850,00 €", StatsCard.Type.BLUE));
        
        mainContainer.add(statsPanel);
        mainContainer.add(Box.createVerticalStrut(24));

        // 3. Grid (Upcoming RDV | Waiting List)
        JPanel widgetsPanel = new JPanel(new GridLayout(1, 2, 24, 0));
        widgetsPanel.setOpaque(false);
        
        // Left Column: Upcoming RDV
        ModernCard upcomingCard = new ModernCard();
        upcomingCard.setLayout(new BorderLayout());
        
        JPanel upcomingHeader = new JPanel(new BorderLayout());
        upcomingHeader.setOpaque(false);
        upcomingHeader.setBorder(new EmptyBorder(0, 0, 16, 0));
        JLabel upcomingTitle = new JLabel("Prochains Rendez-vous");
        upcomingTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        upcomingHeader.add(upcomingTitle, BorderLayout.WEST);
        upcomingCard.add(upcomingHeader, BorderLayout.NORTH);
        
        // List placeholder
        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);
        list.add(createRdvItem("14:00", "Sophie Bernard", "Soin Carie"));
        list.add(Box.createVerticalStrut(8));
        list.add(createRdvItem("14:45", "Lucas Martin", "Consultation"));
        list.add(Box.createVerticalStrut(8));
        list.add(createRdvItem("15:30", "Emma Petit", "Contrôle"));
        
        upcomingCard.add(list, BorderLayout.CENTER);
        widgetsPanel.add(upcomingCard);
        
        // Right Column: Notifications / Waiting List
        ModernCard notifCard = new ModernCard();
        notifCard.setLayout(new BorderLayout());
        
        JLabel notifTitle = new JLabel("Notifications");
        notifTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        notifTitle.setBorder(new EmptyBorder(0, 0, 16, 0));
        notifCard.add(notifTitle, BorderLayout.NORTH);
        
        JPanel notifList = new JPanel();
        notifList.setLayout(new BoxLayout(notifList, BoxLayout.Y_AXIS));
        notifList.setOpaque(false);
        
        notifList.add(createNotifItem("Résultats d'analyse reçus", "09:15", TailwindPalette.GREEN_100, TailwindPalette.GREEN_800));
        notifList.add(Box.createVerticalStrut(8));
        notifList.add(createNotifItem("Rappel: Réunion d'équipe", "08:30", TailwindPalette.BLUE_100, TailwindPalette.BLUE_800));
        
        notifCard.add(notifList, BorderLayout.CENTER);
        widgetsPanel.add(notifCard);
        
        mainContainer.add(widgetsPanel);

        add(new JScrollPane(mainContainer), BorderLayout.CENTER);
    }
    
    private JPanel createRdvItem(String time, String name, String reason) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TailwindPalette.BORDER));
        p.setPreferredSize(new Dimension(0, 50));
        
        JLabel timeLabel = new JLabel(time);
        timeLabel.setOpaque(true);
        timeLabel.setBackground(TailwindPalette.GREEN_100);
        timeLabel.setForeground(TailwindPalette.GREEN_800);
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        timeLabel.setBorder(new EmptyBorder(4, 8, 4, 8));
        
        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setOpaque(false);
        info.setBorder(new EmptyBorder(0, 12, 0, 0));
        JLabel n = new JLabel(name); n.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel r = new JLabel(reason); r.setFont(new Font("Segoe UI", Font.PLAIN, 12)); r.setForeground(Color.GRAY);
        info.add(n); info.add(r);
        
        p.add(timeLabel, BorderLayout.WEST);
        p.add(info, BorderLayout.CENTER);
        
        return p;
    }
    
    private JPanel createNotifItem(String msg, String time, Color bg, Color fg) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(true);
        p.setBackground(bg);
        p.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); // p-2
        // Rounded border would require custom paint
        
        JLabel m = new JLabel(msg);
        m.setForeground(fg);
        m.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        JLabel t = new JLabel(time);
        t.setForeground(fg);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        
        p.add(m, BorderLayout.CENTER);
        p.add(t, BorderLayout.SOUTH);
        
        return p;
    }
}
