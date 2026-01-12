package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.utils.IconUtils;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class DayManagementDialog extends JDialog {

    private final LocalDate date;
    private String currentStatus;
    private List<String> slots;
    private final Consumer<String> onStatusChange;
    private final Consumer<List<String>> onSlotsChange;
    private final Runnable onUpdate; // Callback to refresh UI

    private JPanel statusContainer;
    private JPanel dynamicContentPanel;

    public DayManagementDialog(Frame parent, LocalDate date, String initialStatus, List<String> initialSlots,
                               Consumer<String> onStatusChange, Consumer<List<String>> onSlotsChange, Runnable onUpdate) {
        super(parent, true);
        this.date = date;
        this.currentStatus = initialStatus != null ? initialStatus : "DISPONIBLE";
        this.slots = new ArrayList<>(initialSlots != null ? initialSlots : getDefaultSlots());
        this.onStatusChange = onStatusChange;
        this.onSlotsChange = onSlotsChange;
        this.onUpdate = onUpdate;

        initUI();
    }

    private List<String> getDefaultSlots() {
        return Arrays.asList("09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30");
    }

    private void initUI() {
        setTitle("Gérer le planning");
        setUndecorated(true);
        setSize(500, 500);
        setLocationRelativeTo(getParent());
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, TailwindPalette.GRAY_300),
                new EmptyBorder(24, 24, 24, 24)
        ));
        
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        
        JLabel title = new JLabel("Gérer le " + date.format(DateTimeFormatter.ofPattern("d/M/yyyy")));
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(TailwindPalette.GRAY_900);
        
        JLabel subtitle = new JLabel("Modifiez le statut et les créneaux pour cette date.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TailwindPalette.GRAY_500);
        subtitle.setBorder(new EmptyBorder(4, 0, 16, 0));
        
        JButton closeIcon = new JButton("×");
        closeIcon.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        closeIcon.setForeground(TailwindPalette.GRAY_400);
        closeIcon.setContentAreaFilled(false);
        closeIcon.setBorderPainted(false);
        closeIcon.setFocusPainted(false);
        closeIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeIcon.addActionListener(e -> dispose());
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(title, BorderLayout.NORTH);
        titlePanel.add(subtitle, BorderLayout.CENTER);
        
        header.add(titlePanel, BorderLayout.CENTER);
        header.add(closeIcon, BorderLayout.EAST);
        header.setBorder(new MatteBorder(0, 0, 1, 0, TailwindPalette.GRAY_200));
        
        mainPanel.add(header, BorderLayout.NORTH);

        // Content
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(16, 0, 0, 0));
        
        // Status Section
        JLabel statusLabel = new JLabel("Statut du jour");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusLabel.setForeground(TailwindPalette.GRAY_700);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(statusLabel);
        content.add(Box.createVerticalStrut(8));
        
        statusContainer = new JPanel(new GridLayout(1, 3, 0, 0)); // No gap, single merged look
        statusContainer.setOpaque(false);
        statusContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        statusContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        updateStatusButtons();
        
        content.add(statusContainer);
        content.add(Box.createVerticalStrut(24));
        
        // Dynamic Content Area (Slots)
        dynamicContentPanel = new JPanel(new BorderLayout());
        dynamicContentPanel.setOpaque(false);
        dynamicContentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        content.add(dynamicContentPanel);
        
        updateContent();
        
        mainPanel.add(content, BorderLayout.CENTER);
        
        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setOpaque(false);
        footer.setBorder(new MatteBorder(1, 0, 0, 0, TailwindPalette.GRAY_200));
        
        ModernButton closeBtn = new ModernButton("Fermer", ModernButton.Variant.OUTLINE);
        closeBtn.addActionListener(e -> dispose());
        footer.add(closeBtn);
        
        mainPanel.add(footer, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private void updateStatusButtons() {
        statusContainer.removeAll();
        statusContainer.add(createStatusButton("Disponible", "DISPONIBLE"));
        statusContainer.add(createStatusButton("Indisponible", "INDISPONIBLE"));
        statusContainer.add(createStatusButton("Congé", "CONGE"));
        statusContainer.revalidate();
        statusContainer.repaint();
    }
    
    private JButton createStatusButton(String label, String value) {
        boolean isSelected = this.currentStatus.equals(value);
        
        JButton btn = new JButton(label) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isSelected) {
                    g2.setColor(Color.WHITE);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    
                    // Shadow/Border
                    g2.setColor(TailwindPalette.GRAY_300);
                    g2.setStroke(new BasicStroke(1));
                    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                } else {
                    g2.setColor(TailwindPalette.GRAY_100);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
                
                super.paintComponent(g);
            }
        };
        
        btn.setFont(new Font("Segoe UI", isSelected ? Font.BOLD : Font.PLAIN, 14));
        btn.setForeground(isSelected ? TailwindPalette.GRAY_900 : TailwindPalette.GRAY_500);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btn.addActionListener(e -> {
            this.currentStatus = value;
            onStatusChange.accept(value);
            onUpdate.run(); // Refresh parent immediately
            updateStatusButtons();
            updateContent();
        });
        
        // Container for background styling if needed (segmented look)
        // For now simple buttons work
        return btn;
    }
    
    private void updateContent() {
        dynamicContentPanel.removeAll();
        
        if ("DISPONIBLE".equals(currentStatus)) {
            // Container for inner content
            JPanel innerContent = new JPanel(new BorderLayout());
            innerContent.setOpaque(false);

            // Header for Slots
            JPanel header = new JPanel(new BorderLayout());
            header.setOpaque(false);
            
            JLabel lbl = new JLabel("Créneaux horaires");
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lbl.setForeground(TailwindPalette.GRAY_700);
            
            ModernButton addBtn = new ModernButton("+ Ajouter", ModernButton.Variant.DEFAULT);
            addBtn.setPreferredSize(new Dimension(80, 32));
            addBtn.addActionListener(e -> {
               String newSlot = JOptionPane.showInputDialog(this, "Nouveau créneau (HH:mm):", "Ajouter", JOptionPane.PLAIN_MESSAGE);
               if (newSlot != null && !newSlot.isEmpty()) {
                   slots.add(newSlot);
                   onSlotsChange.accept(slots);
                   onUpdate.run();
                   updateContent();
               }
            });
            
            header.add(lbl, BorderLayout.WEST);
            header.add(addBtn, BorderLayout.EAST);
            
            innerContent.add(header, BorderLayout.NORTH);
            
            // Slots Grid
            JPanel slotsGrid = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
            slotsGrid.setOpaque(false);
            slotsGrid.setBorder(new EmptyBorder(12, 0, 0, 0));
            
            for (String slot : slots) {
                JPanel chip = new JPanel(new BorderLayout(8, 0));
                chip.setBackground(Color.WHITE);
                chip.setBorder(new CompoundBorder(
                    new MatteBorder(1, 1, 1, 1, TailwindPalette.GRAY_300),
                    new EmptyBorder(4, 12, 4, 8)
                ));
                
                JLabel time = new JLabel(slot);
                time.setFont(new Font("Consolas", Font.PLAIN, 13));
                time.setForeground(TailwindPalette.GRAY_800);
                
                JLabel remove = new JLabel("×");
                remove.setFont(new Font("Arial", Font.BOLD, 16));
                remove.setForeground(TailwindPalette.RED_400);
                remove.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                remove.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        slots.remove(slot);
                        onSlotsChange.accept(slots);
                        onUpdate.run();
                        updateContent();
                    }
                });
                
                chip.add(time, BorderLayout.CENTER);
                chip.add(remove, BorderLayout.EAST);
                
                slotsGrid.add(chip);
            }
            
            innerContent.add(slotsGrid, BorderLayout.CENTER);
            
            // Outer box style
            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setOpaque(true);
            wrapper.setBackground(TailwindPalette.GRAY_50);
            wrapper.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, TailwindPalette.GRAY_200),
                new EmptyBorder(16, 16, 16, 16)
            ));
            
            // Add innerContent to wrapper instead of dynamicContentPanel
            wrapper.add(innerContent, BorderLayout.CENTER);
            
            // Add wrapper to dynamicContentPanel
            dynamicContentPanel.add(wrapper, BorderLayout.CENTER);
            
        } else {
            // Empty or basic message
             JPanel empty = new JPanel(new FlowLayout(FlowLayout.CENTER));
             empty.setOpaque(false);
             dynamicContentPanel.add(empty, BorderLayout.CENTER);
        }
        
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }
}
