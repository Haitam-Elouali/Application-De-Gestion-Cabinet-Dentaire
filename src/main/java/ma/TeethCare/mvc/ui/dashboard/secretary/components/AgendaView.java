package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AgendaView extends JPanel {

    private LocalDate currentDate;
    private JPanel calendarGrid;
    private JLabel monthLabel;

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
        actionsPanel.add(new ModernButton("Générer Type", ModernButton.Variant.SUCCESS));
        // actionsPanel.add(new ModernButton("Effacer Mois", ModernButton.Variant.DESTRUCTIVE));
        
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
    
    private JPanel createDayCell(LocalDate date) {
        JPanel cell = new JPanel(new BorderLayout());
        cell.setBackground(Color.WHITE);
        cell.setPreferredSize(new Dimension(0, 100)); // Min height
        cell.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, TailwindPalette.GRAY_200)); // Light grid
        cell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
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
        if (date.getDayOfMonth() == 15 || date.getDayOfMonth() == 22) { // Mock logic
             JPanel content = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
             content.setOpaque(false);
             
             JLabel dot = new JLabel("● 8 RDV"); // Bullet
             dot.setForeground(TailwindPalette.BLUE_600);
             dot.setFont(new Font("Segoe UI", Font.BOLD, 11));
             content.add(dot);
             
             cell.add(content, BorderLayout.CENTER);
        }
        
        return cell;
    }
}
