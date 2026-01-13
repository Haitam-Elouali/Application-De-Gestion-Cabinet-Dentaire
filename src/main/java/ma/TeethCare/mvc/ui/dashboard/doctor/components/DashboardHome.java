package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.containers.ModernCard;
import ma.TeethCare.mvc.ui.palette.data.ModernBadge;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;
import ma.TeethCare.repository.mySQLImpl.ChargesRepositoryImpl;
import ma.TeethCare.repository.mySQLImpl.RevenuesRepositoryImpl;
import ma.TeethCare.service.modules.caisse.api.FinancialStatisticsService;
import ma.TeethCare.service.modules.caisse.impl.FinancialStatisticsServiceImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;

public class DashboardHome extends JPanel {

    private final FinancialStatisticsService financialService;
    private final ma.TeethCare.service.modules.agenda.api.rdvService rdvService;
    private final ma.TeethCare.service.modules.patient.api.PatientService patientService;

    public DashboardHome() {
        // Initialize Services
        this.financialService = new FinancialStatisticsServiceImpl(
            new RevenuesRepositoryImpl(), 
            new ChargesRepositoryImpl(),
            new ma.TeethCare.repository.mySQLImpl.FactureRepositoryImpl()
        );
        this.rdvService = new ma.TeethCare.service.modules.agenda.impl.rdvServiceImpl(new ma.TeethCare.repository.mySQLImpl.RdvRepositoryImpl());
        this.patientService = new ma.TeethCare.service.modules.patient.impl.PatientServiceImpl(new ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl());

        setLayout(new BorderLayout());
        setOpaque(false); // Transparent to show Blue background
        setBorder(new EmptyBorder(0, 0, 0, 0)); // Padding handled by parent modulePanel

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        
        // --- Fetch Data ---
        java.util.List<ma.TeethCare.mvc.dto.rdv.RdvDTO> todayRdvs = new java.util.ArrayList<>();
        long patientCount = 0;
        long consultationCount = 0;
        String dailyRevenue = "0 MAD";

        try {
            LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
            LocalDateTime now = LocalDateTime.now();
            FinancialStatisticsService.FinancialSummary dailySummary = financialService.getFinancialSummary(startOfDay, now);
            dailyRevenue = String.format("%,.0f MAD", dailySummary.totalRecettes());

            todayRdvs = rdvService.findTodayAppointments();
            consultationCount = todayRdvs.size();
            patientCount = todayRdvs.stream().map(ma.TeethCare.mvc.dto.rdv.RdvDTO::getPatientId).distinct().count();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 1. Stats Row
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 16, 0)); // 4 cols, gap 16
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        statsPanel.add(new StatsCard("Patients du jour", String.valueOf(patientCount), StatsCard.Type.BLUE, ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_PATIENTS));
        statsPanel.add(new StatsCard("Consultations", String.valueOf(consultationCount), StatsCard.Type.GREEN, ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_CONSULTATION));
        statsPanel.add(new StatsCard("Actes réalisés", "0", StatsCard.Type.PURPLE, ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_ACTS));
        // updated with real data
        statsPanel.add(new StatsCard("Revenus du jour", dailyRevenue, StatsCard.Type.ORANGE, ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_CASH));
        
        content.add(statsPanel);
        content.add(Box.createVerticalStrut(24)); // Gap

        // 2. Planning + Notifications Grid
        // Swing doesn't have CSS Grid, so we use a Panel with BorderLayout or GridBagLayout
        JPanel mainGrid = new JPanel(new GridBagLayout());
        mainGrid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Left: Planning (Take more width)
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.7; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 16); // right gap
        
        ModernCard planningCard = new ModernCard();
        planningCard.setLayout(new BorderLayout());
        
        // Header of Card
        JPanel pHeader = new JPanel(new BorderLayout());
        pHeader.setOpaque(false);
        pHeader.setBorder(new EmptyBorder(0, 0, 16, 0));
        JLabel pTitle = new JLabel("Planning du jour");
        pTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pHeader.add(pTitle, BorderLayout.WEST);
        planningCard.add(pHeader, BorderLayout.NORTH);
        
        // Table Prep
        String[] columns = {"Heure", "Patient", "Motif", "Statut"};
        Object[][] data = new Object[todayRdvs.size()][4];
        
        for (int i=0; i < todayRdvs.size(); i++) {
             ma.TeethCare.mvc.dto.rdv.RdvDTO r = todayRdvs.get(i);
             // Fetch Patient Name
             String patientName = "Inconnu";
             try {
                  java.util.Optional<ma.TeethCare.mvc.dto.patient.PatientDTO> pat = patientService.findById(r.getPatientId());
                  if (pat.isPresent()) {
                      patientName = pat.get().getNom() + " " + pat.get().getPrenom();
                  }
             } catch (Exception e) {}
             
             data[i][0] = r.getHeure() != null ? r.getHeure().toString() : "--:--";
             data[i][1] = patientName;
             data[i][2] = r.getMotif() != null ? r.getMotif() : "Consultation";
             data[i][3] = r.getStatut() != null ? r.getStatut() : "En attente";
        }
        
        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        // Add status renderer
        table.getColumnModel().getColumn(3).setCellRenderer(new ma.TeethCare.mvc.ui.palette.renderers.StatusPillRenderer());
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder());
        planningCard.add(sp, BorderLayout.CENTER);
        
        // Right: Notifications REMOVED
        
        // Adjust Planning to take full width
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        
        // Remove previous add to re-add with new constraints
        mainGrid.remove(planningCard);
        mainGrid.add(planningCard, gbc);
        
        content.add(mainGrid);

        add(content, BorderLayout.CENTER);
    }
}
