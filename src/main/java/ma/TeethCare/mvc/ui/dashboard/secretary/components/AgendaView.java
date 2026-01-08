package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
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
        topBar.setBorder(new EmptyBorder(0, 0, 16, 0));

        // Month Navigation
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        navPanel.setOpaque(false);
        
        ModernButton prevBtn = new ModernButton("<", ModernButton.Variant.OUTLINE);
        prevBtn.setPreferredSize(new Dimension(40, 36));
        prevBtn.addActionListener(e -> { currentDate = currentDate.minusMonths(1); updateCalendar(); });
        
        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        monthLabel.setPreferredSize(new Dimension(200, 36));
        
        ModernButton nextBtn = new ModernButton(">", ModernButton.Variant.OUTLINE);
        nextBtn.setPreferredSize(new Dimension(40, 36));
        nextBtn.addActionListener(e -> { currentDate = currentDate.plusMonths(1); updateCalendar(); });

        navPanel.add(prevBtn);
        navPanel.add(monthLabel);
        navPanel.add(nextBtn);
        
        topBar.add(navPanel, BorderLayout.WEST);
        
        // Actions
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionsPanel.setOpaque(false);
        actionsPanel.add(new ModernButton("Générer Type", ModernButton.Variant.SECONDARY));
        actionsPanel.add(new ModernButton("Effacer Mois", ModernButton.Variant.DESTRUCTIVE));
        
        topBar.add(actionsPanel, BorderLayout.EAST);
        
        add(topBar, BorderLayout.NORTH);

        // Calendar Grid
        calendarGrid = new JPanel(new GridLayout(0, 7, 1, 1));
        calendarGrid.setBackground(TailwindPalette.BORDER); // Grid lines color
        calendarGrid.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        
        add(calendarGrid, BorderLayout.CENTER);
        
        updateCalendar();
    }

    private void updateCalendar() {
        calendarGrid.removeAll();
        
        YearMonth yearMonth = YearMonth.from(currentDate);
        monthLabel.setText(currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH)).substring(0, 1).toUpperCase() + currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH)).substring(1));

        // Header
        String[] days = {"DIM", "LUN", "MAR", "MER", "JEU", "VEN", "SAM"};
        for (String d : days) {
            JPanel headerCell = new JPanel(new BorderLayout());
            headerCell.setBackground(Color.WHITE);
            headerCell.setPreferredSize(new Dimension(0, 40));
            JLabel l = new JLabel(d, SwingConstants.CENTER);
            l.setFont(new Font("Segoe UI", Font.BOLD, 12));
            l.setForeground(Color.GRAY);
            headerCell.add(l, BorderLayout.CENTER);
            calendarGrid.add(headerCell);
        }

        // Days
        LocalDate firstOfMonth = yearMonth.atDay(1);
        int emptySlots = firstOfMonth.getDayOfWeek().getValue() % 7; // Sunday is 7 in java.time, but 0 in standard calendar view often. Adjust logic:
        // java.time: Mon=1 ... Sun=7.
        // Array above: DIM(0), LUN(1)...
        // So if day 1 is Mon(1), we need 1 empty slot (Sun).
        // If day 1 is Sun(7), we need 0 empty slots.
        // Wait, standard view starts Sun.
        // If Mon(1), skip Sun(0). 1 empty slot.
        // If Sun(7), skip nothing? No, Sun is first col.
        // Let's assume standard Sun-Sat grid.
        // java.time.DayOfWeek: Mon(1) -> Sun(7).
        // Target: Sun(0) -> Sat(6).
        // Mapping: map(7) -> 0.
        int startDay = (firstOfMonth.getDayOfWeek().getValue() % 7); 
        
        for (int i = 0; i < startDay; i++) {
             JPanel empty = new JPanel();
             empty.setBackground(TailwindPalette.GRAY_50);
             calendarGrid.add(empty);
        }
        
        for (int i = 1; i <= yearMonth.lengthOfMonth(); i++) {
            LocalDate date = yearMonth.atDay(i);
            calendarGrid.add(createDayCell(date));
        }
        
        // Fill remaining
        int totalCells = startDay + yearMonth.lengthOfMonth();
        while (totalCells % 7 != 0) {
             JPanel empty = new JPanel();
             empty.setBackground(TailwindPalette.GRAY_50);
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
        cell.setBorder(new EmptyBorder(4, 8, 4, 8));
        cell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Top Right Day Number
        boolean isWeekend = (date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7);
        JLabel num = new JLabel(String.valueOf(date.getDayOfMonth()), SwingConstants.RIGHT);
        num.setFont(new Font("Segoe UI", Font.BOLD, 14));
        num.setForeground(isWeekend ? Color.RED : Color.GRAY);
        cell.add(num, BorderLayout.NORTH);
        
        // Content (Status) - Mock data logic
        // Center panel
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        
        if (!isWeekend) {
             JLabel status = new JLabel("Disponible");
             status.setOpaque(true);
             status.setBackground(TailwindPalette.GREEN_100);
             status.setForeground(TailwindPalette.GREEN_800);
             status.setFont(new Font("Segoe UI", Font.BOLD, 10));
             status.setBorder(new EmptyBorder(2, 6, 2, 6));
             center.add(status);
             
             JLabel slots = new JLabel("12 créneaux");
             slots.setFont(new Font("Segoe UI", Font.PLAIN, 10));
             slots.setForeground(Color.GRAY);
             GridBagConstraints gbc = new GridBagConstraints();
             gbc.gridy = 1;
             center.add(slots, gbc);
        } else {
             cell.setBackground(TailwindPalette.GRAY_50);
        }
        
        cell.add(center, BorderLayout.CENTER);
        
        return cell;
    }
}
