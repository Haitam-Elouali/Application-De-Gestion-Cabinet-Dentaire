package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AgendaView extends JPanel {

    private LocalDate currentDate;
    private JPanel calendarGrid;
    private JLabel monthLabel;
    private Map<LocalDate, String> dayStatuses = new HashMap<>();
    private Map<LocalDate, java.util.List<String>> daySlots = new HashMap<>();

    public AgendaView() {
        this.currentDate = LocalDate.now();
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(24, 24, 24, 24));

        initUI();
    }

    private void initUI() {
        // Top Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(new EmptyBorder(0, 0, 24, 0));

        // Month Navigation
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        navPanel.setOpaque(false);
        
        // Rounded Buttons
        JButton prevBtn = createNavButton("<");
        prevBtn.addActionListener(e -> { currentDate = currentDate.minusMonths(1); updateCalendar(); });
        
        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        monthLabel.setForeground(TailwindPalette.GRAY_900);
        
        JButton nextBtn = createNavButton(">");
        nextBtn.addActionListener(e -> { currentDate = currentDate.plusMonths(1); updateCalendar(); });

        navPanel.add(prevBtn);
        navPanel.add(monthLabel);
        navPanel.add(nextBtn);
        
        topBar.add(navPanel, BorderLayout.WEST);
        
        // Actions
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionsPanel.setOpaque(false);
        ModernButton generateBtn = new ModernButton("Générer Type", ModernButton.Variant.SUCCESS);
        generateBtn.addActionListener(e -> generateMonthSchedule());
        actionsPanel.add(generateBtn);
        ModernButton clearBtn = new ModernButton("Effacer Mois", ModernButton.Variant.DESTRUCTIVE);
        clearBtn.addActionListener(e -> clearMonthSchedule());
        actionsPanel.add(clearBtn);
        
        topBar.add(actionsPanel, BorderLayout.EAST);
        
        add(topBar, BorderLayout.NORTH);

        // Calendar Grid
        calendarGrid = new JPanel(new GridLayout(0, 7, 0, 0)); // No gap, handled by border
        calendarGrid.setBackground(Color.WHITE); 
        
        add(calendarGrid, BorderLayout.CENTER);
        
        updateCalendar();
    }
    
    private JButton createNavButton(String text) {
        JButton btn = new JButton(text) {
             @Override
             protected void paintComponent(Graphics g) {
                 Graphics2D g2 = (Graphics2D)g;
                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                 g2.setColor(getModel().isRollover() ? TailwindPalette.GRAY_100 : Color.WHITE);
                 g2.fillOval(0, 0, getWidth(), getHeight());
                 g2.setColor(TailwindPalette.GRAY_300);
                 g2.drawOval(0, 0, getWidth()-1, getHeight()-1);
                 super.paintComponent(g);
             }
        };
        btn.setPreferredSize(new Dimension(32, 32));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(TailwindPalette.GRAY_600);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void updateCalendar() {
        calendarGrid.removeAll();
        
        YearMonth yearMonth = YearMonth.from(currentDate);
        monthLabel.setText(currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH)).substring(0, 1).toUpperCase() + currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH)).substring(1));

        // Header
        String[] days = {"LUN", "MAR", "MER", "JEU", "VEN", "SAM", "DIM"};
        for (String d : days) {
            JPanel headerCell = new JPanel(new BorderLayout());
            headerCell.setBackground(Color.WHITE);
            headerCell.setPreferredSize(new Dimension(0, 40));
            headerCell.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TailwindPalette.GRAY_200));
            
            JLabel l = new JLabel(d, SwingConstants.CENTER);
            l.setFont(new Font("Segoe UI", Font.BOLD, 12));
            l.setForeground(TailwindPalette.GRAY_400);
            headerCell.add(l, BorderLayout.CENTER);
            calendarGrid.add(headerCell);
        }

        // Days calculation
        LocalDate firstOfMonth = yearMonth.atDay(1);
        // Map Monday(1) -> 0 ... Sunday(7) -> 6
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue(); // 1=Mon, 7=Sun
        int startOffset = dayOfWeek - 1; 
        
        // Empty slots before
        for (int i = 0; i < startOffset; i++) {
             JPanel empty = new JPanel();
             empty.setBackground(Color.WHITE);
             empty.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, TailwindPalette.GRAY_100));
             calendarGrid.add(empty);
        }
        
        for (int i = 1; i <= yearMonth.lengthOfMonth(); i++) {
            LocalDate date = yearMonth.atDay(i);
            calendarGrid.add(createDayCell(date));
        }
        
        // Fill remaining
        int totalCells = startOffset + yearMonth.lengthOfMonth();
        while (totalCells % 7 != 0) {
             JPanel empty = new JPanel();
             empty.setBackground(Color.WHITE);
             empty.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, TailwindPalette.GRAY_100));
             calendarGrid.add(empty);
             totalCells++;
        }

        calendarGrid.revalidate();
        calendarGrid.repaint();
    }
    
    private void clearMonthSchedule() {
        // Confirmation Dialog
        int response = JOptionPane.showConfirmDialog(this, 
            "Voulez-vous vraiment effacer tous les statuts et créneaux pour ce mois ?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
            
        if (response == JOptionPane.YES_OPTION) {
            YearMonth yearMonth = YearMonth.from(currentDate);
            for (int i = 1; i <= yearMonth.lengthOfMonth(); i++) {
                LocalDate date = yearMonth.atDay(i);
                dayStatuses.remove(date);
                daySlots.remove(date);
            }
            updateCalendar();
        }
    }
    
    private void generateMonthSchedule() {
        YearMonth yearMonth = YearMonth.from(currentDate);
        for (int i = 1; i <= yearMonth.lengthOfMonth(); i++) {
            LocalDate date = yearMonth.atDay(i);
            int dayOfWeek = date.getDayOfWeek().getValue();
            if (dayOfWeek == 6 || dayOfWeek == 7) { // Saturday, Sunday
                dayStatuses.put(date, "INDISPONIBLE");
            } else {
                dayStatuses.put(date, "DISPONIBLE");
            }
        }
        updateCalendar();
    }
    
    private JPanel createDayCell(LocalDate date) {
        JPanel cell = new JPanel(new BorderLayout());
        cell.setBackground(Color.WHITE);
        cell.setPreferredSize(new Dimension(0, 100)); // Min height
        cell.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, TailwindPalette.GRAY_200)); // Light grid
        cell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        cell.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                java.util.List<String> initialSlots = daySlots.get(date);
                // If day is not generated/set yet (no status), start with empty slots
                if (initialSlots == null && !dayStatuses.containsKey(date)) {
                    initialSlots = new java.util.ArrayList<>();
                }

                new DayManagementDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(AgendaView.this),
                    date,
                    dayStatuses.getOrDefault(date, "DISPONIBLE"),
                    initialSlots,
                    newStatus -> dayStatuses.put(date, newStatus),
                    newSlots -> daySlots.put(date, newSlots),
                    () -> updateCalendar()
                ).setVisible(true);
            }
        });
        
        // Top Right Day Number
        boolean isWeekend = (date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7);
        JLabel num = new JLabel(String.valueOf(date.getDayOfMonth()));
        num.setFont(new Font("Segoe UI", Font.BOLD, 12));
        num.setForeground(isWeekend ? TailwindPalette.RED_500 : TailwindPalette.GRAY_400); // Red for weekend
        
        JPanel topContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        topContainer.setOpaque(false);
        topContainer.add(num);
        cell.add(topContainer, BorderLayout.NORTH);
        
        // Content (Appointments)
        // Only show if mock data exists
        // Content (Appointments or Status)
        String status = dayStatuses.get(date);
        
        if (status != null) {
            JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            statusPanel.setOpaque(false);
            
            Color textColor = TailwindPalette.RED_800;
            Color bgColor = TailwindPalette.RED_100;
            
            if (status.equals("DISPONIBLE")) {
                textColor = TailwindPalette.GREEN_800;
                bgColor = TailwindPalette.GREEN_100;
            } else if (status.equals("CONGE")) {
                textColor = TailwindPalette.ORANGE_800;
                bgColor = TailwindPalette.ORANGE_100;
            }

            JLabel statusLabel = new JLabel(status);
            statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
            statusLabel.setForeground(textColor);
            
            JPanel pill = new JPanel(new BorderLayout());
            pill.setOpaque(false);
            pill.setBorder(new EmptyBorder(2, 8, 2, 8));
            pill.add(statusLabel);
            
            Color finalBgColor = bgColor;
            JPanel container = new JPanel(new BorderLayout()) {
                 @Override
                 protected void paintComponent(Graphics g) {
                     Graphics2D g2 = (Graphics2D)g;
                     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                     g2.setColor(finalBgColor);
                     g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                     super.paintComponent(g);
                 }
            };
            container.setOpaque(false);
            container.add(pill);
            statusPanel.add(container);
            
            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setOpaque(false);
            wrapper.setBorder(new EmptyBorder(10, 0, 0, 0)); // Top margin
            wrapper.add(statusPanel, BorderLayout.NORTH);
            
            if (status.equals("DISPONIBLE")) {
                 int count = daySlots.containsKey(date) && daySlots.get(date) != null ? daySlots.get(date).size() : 13; // Default 13
                 JLabel slots = new JLabel(count + " créneaux", SwingConstants.CENTER);
                 slots.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                 slots.setForeground(TailwindPalette.GRAY_500);
                 slots.setBorder(new EmptyBorder(4, 0, 0, 0));
                 wrapper.add(slots, BorderLayout.CENTER);
            }
            
            cell.add(wrapper, BorderLayout.CENTER);
        }
        
        return cell;
    }
}
