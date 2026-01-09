package ma.TeethCare.mvc.ui.palette.combos;

import lombok.Data;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

@Data
public class CustomEnumComboBox<E extends Enum<E>> extends JComboBox<CustomEnumComboBox.ComboItem<E>> {

    // Placeholder (1ère valeur)
    private String placeholder = "— Sélectionner —";

    // Style (proche de CustomTextField)
    private int arcWidth = 20;
    private int arcHeight = 20;

    private Color borderColor = new Color(100, 150, 255);
    private Color backgroundColor = new Color(240, 240, 240);

    private Color gainColor = new Color(7, 28, 73);
    private Color lostColor = Color.GRAY;

    private Font gainFont = new Font("Optima", Font.BOLD, 18);
    private Font lostFont = new Font("Optima", Font.ITALIC, 15);

    private Dimension preferred = new Dimension(320, 38);

    // Format d’affichage (par défaut: NAME -> "Name" ou "SECRETAIRE" -> "Secretaire")
    private Function<E, String> labeler = CustomEnumComboBox::prettyEnum;

    public CustomEnumComboBox(Class<E> enumType) {
        this(enumType, null);
    }

    public CustomEnumComboBox(Class<E> enumType, String placeholder) {
        if (placeholder != null && !placeholder.isBlank()) this.placeholder = placeholder;

        setOpaque(false);
        setBorder(new EmptyBorder(5, 10, 5, 10));
        setPreferredSize(preferred);

        // UI minimal + custom renderer
        setUI(new BasicComboBoxUI() {
            @Override protected JButton createArrowButton() {
                JButton b = new JButton("▾");
                b.setBorderPainted(false);
                b.setFocusPainted(false);
                b.setContentAreaFilled(false);
                b.setOpaque(false);
                b.setForeground(new Color(90, 90, 90));
                b.setFont(new Font("Optima", Font.BOLD, 14));
                return b;
            }
        });

        // Remplir model : placeholder + valeurs enum
        DefaultComboBoxModel<ComboItem<E>> model = new DefaultComboBoxModel<>();
        model.addElement(ComboItem.placeholder(this.placeholder));

        Arrays.stream(enumType.getEnumConstants())
                .filter(Objects::nonNull)
                .forEach(v -> model.addElement(ComboItem.value(v, labeler.apply(v))));

        setModel(model);
        setSelectedIndex(0);

        // Renderer moderne + séparateur
        setRenderer(new ModernRenderer());

        // Style initial (lost)
        applyLostStyle();

        // Focus -> gain/lost
        addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { applyGainStyle(); }
            @Override public void focusLost(FocusEvent e)  { refreshStyleBasedOnSelection(); }
        });

        // Quand sélection change, ajuster style
        addActionListener(e -> refreshStyleBasedOnSelection());
    }

    // ===== Public API =====

    /** null si placeholder sélectionné */
    public E getSelectedEnum() {
        ComboItem<E> it = (ComboItem<E>) getSelectedItem();
        if (it == null || it.isPlaceholder()) return null;
        return it.getValue();
    }

    /** si value == null => placeholder */
    public void setSelectedEnum(E value) {
        ComboBoxModel<ComboItem<E>> m = getModel();
        if (value == null) {
            setSelectedIndex(0);
            refreshStyleBasedOnSelection();
            return;
        }
        for (int i = 0; i < m.getSize(); i++) {
            ComboItem<E> it = m.getElementAt(i);
            if (!it.isPlaceholder() && it.getValue() == value) {
                setSelectedIndex(i);
                refreshStyleBasedOnSelection();
                return;
            }
        }
        // si pas trouvé => placeholder
        setSelectedIndex(0);
        refreshStyleBasedOnSelection();
    }

    public void setPreferredFieldSize(int w, int h) {
        this.preferred = new Dimension(w, h);
        setPreferredSize(preferred);
        revalidate();
        repaint();
    }

    public void setArc(int arcW, int arcH) {
        this.arcWidth = arcW;
        this.arcHeight = arcH;
        repaint();
    }

    public void setLabeler(Function<E, String> labeler) {
        this.labeler = (labeler == null) ? CustomEnumComboBox::prettyEnum : labeler;
        // rebuild labels
        ComboBoxModel<ComboItem<E>> m = getModel();
        DefaultComboBoxModel<ComboItem<E>> nm = new DefaultComboBoxModel<>();
        nm.addElement(ComboItem.placeholder(this.placeholder));
        for (int i = 1; i < m.getSize(); i++) {
            ComboItem<E> it = m.getElementAt(i);
            nm.addElement(ComboItem.value(it.value, this.labeler.apply(it.value)));
        }
        setModel(nm);
        setSelectedIndex(0);
        refreshStyleBasedOnSelection();
    }

    // ===== Painting (fond arrondi + border) =====

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

        // Border
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

        g2.dispose();
        super.paintComponent(g);
    }

    // ===== Style helpers =====

    private void refreshStyleBasedOnSelection() {
        if (getSelectedEnum() == null) applyLostStyle();
        else applyGainStyle();
    }

    private void applyLostStyle() {
        setFont(lostFont);
        setForeground(lostColor);
        repaint();
    }

    private void applyGainStyle() {
        setFont(gainFont);
        setForeground(gainColor);
        repaint();
    }

    // ===== Renderer moderne + séparateur =====

    private class ModernRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus
        ) {
            JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            lbl.setBorder(new EmptyBorder(8, 12, 8, 12));
            lbl.setFont(new Font("Optima", Font.PLAIN, 15));

            ComboItem<E> it = (ComboItem<E>) value;

            // Placeholder style
            if (it != null && it.isPlaceholder()) {
                lbl.setForeground(new Color(120, 120, 120));
                lbl.setFont(new Font("Optima", Font.ITALIC, 15));
            }

            // Petite séparation visuelle après placeholder (dans dropdown uniquement)
            if (index == 1) { // premier vrai élément => on ajoute un "separator line" via matte border
                lbl.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(225, 225, 225)),
                        new EmptyBorder(8, 12, 8, 12)
                ));
            }

            return lbl;
        }
    }

    // ===== Item wrapper =====

    @Data
    public static class ComboItem<T extends Enum<T>> {
        private final T value;
        private final String label;
        private final boolean placeholder;

        public static <T extends Enum<T>> ComboItem<T> placeholder(String label) {
            return new ComboItem<>(null, label, true);
        }

        public static <T extends Enum<T>> ComboItem<T> value(T value, String label) {
            return new ComboItem<>(value, label, false);
        }

        public boolean isPlaceholder() { return placeholder; }

        @Override public String toString() { return label; }
    }

    // ===== Pretty enum =====

    private static <E extends Enum<E>> String prettyEnum(E e) {
        // "SECRETAIRE" -> "Secretaire", "Homme" reste "Homme" si déjà bien
        String s = e.name().toLowerCase().replace('_', ' ');
        if (s.isBlank()) return e.name();
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
