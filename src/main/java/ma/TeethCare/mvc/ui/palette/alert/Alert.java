package ma.TeethCare.mvc.ui.palette.alert;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public final class Alert {

    private Alert() {}

    // 🎨 Couleurs
    private static final Color INFO_BG     = new Color(52, 152, 219);
    private static final Color SUCCESS_BG  = new Color(46, 204, 113);
    private static final Color WARNING_BG  = new Color(241, 196, 15);
    private static final Color ERROR_BG    = new Color(231, 76, 60);

    private static final Font TITLE_FONT = new Font("Optima", Font.BOLD, 16);
    private static final Font TEXT_FONT  = new Font("Optima", Font.PLAIN, 14);

    // Limites UI (pro)
    private static final int MSG_WIDTH  = 520;
    private static final int MSG_HEIGHT = 220;

    /* ======================
       API PUBLIQUE
       ====================== */

    public static void info(Component parent, String message) {
        show(parent, "Information", message, INFO_BG, "/static/icons/alert/info.png", false);
    }

    public static void success(Component parent, String message) {
        show(parent, "Succès", message, SUCCESS_BG, "/static/icons/alert/success.png", false);
    }

    public static void warning(Component parent, String message) {
        show(parent, "Attention", message, WARNING_BG, "/static/icons/alert/warning.png", false);
    }

    public static void error(Component parent, String message) {
        show(parent, "Erreur", message, ERROR_BG, "/static/icons/alert/error.png", false);
    }

    public static boolean confirm(Component parent, String message) {
        return show(parent, "Confirmation", message, INFO_BG, "/static/icons/alert/confirm.png", true);
    }

    /* ======================
       IMPLÉMENTATION
       ====================== */

    private static boolean show(Component parent,
                                String title,
                                String message,
                                Color headerColor,
                                String iconPath,
                                boolean confirm) {

        final boolean[] result = {false};

        Window owner = (parent != null)
                ? SwingUtilities.getWindowAncestor(parent)
                : null;

        JDialog dialog = new JDialog(owner, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.setLayout(new BorderLayout());
        dialog.setBackground(Color.WHITE);

        // ---------- HEADER ----------
        JPanel header = new JPanel(new BorderLayout(10, 0));
        header.setBackground(headerColor);
        header.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel lblIcon  = new JLabel(loadIcon(iconPath, 24, 24));
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(TITLE_FONT);
        lblTitle.setForeground(Color.WHITE);

        JButton btClose = createHeaderCloseButton();
        btClose.addActionListener(e -> dialog.dispose());

        header.add(lblIcon, BorderLayout.WEST);
        header.add(lblTitle, BorderLayout.CENTER);
        header.add(btClose, BorderLayout.EAST);

        makeDraggable(dialog, header);

        // ---------- MESSAGE (PRO avec scroll) ----------
        JTextArea area = new JTextArea(message);
        area.setFont(TEXT_FONT);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setOpaque(false);
        area.setBorder(new EmptyBorder(12, 12, 12, 12));

        JScrollPane sp = new JScrollPane(area);
        sp.setBorder(new EmptyBorder(0, 0, 0, 0));
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setPreferredSize(new Dimension(MSG_WIDTH, MSG_HEIGHT));
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Color.WHITE);
        body.setBorder(new EmptyBorder(12, 12, 12, 12));
        body.add(sp, BorderLayout.CENTER);

        // ---------- FOOTER ----------
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        footer.setBackground(Color.WHITE);

        // Bouton principal (OK / Oui)
        JButton btnOk = createButton(confirm ? "Oui" : "OK");
        btnOk.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });
        footer.add(btnOk);

        // Bouton secondaire (Non) pour confirm
        JButton btnNo = null;
        if (confirm) {
            btnNo = createButton("Non");
            btnNo.addActionListener(e -> dialog.dispose());
            footer.add(btnNo);
        }

        // ✅ KeyBindings (sans setDefaultButton => pas de style bleu)
        installDialogKeyBindings(dialog, btnOk, btnNo);

        // ---------- ASSEMBLY ----------
        dialog.add(header, BorderLayout.NORTH);
        dialog.add(body, BorderLayout.CENTER);
        dialog.add(footer, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return result[0];
    }

    /* ======================
       OUTILS UI
       ====================== */

    private static JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(TEXT_FONT);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(110, 34));
        // ⚠️ On ne touche pas au background/foreground => Look&Feel original conservé
        return btn;
    }

    private static JButton createHeaderCloseButton() {
        JButton bt = new JButton("✕");
        bt.setFont(new Font("Optima", Font.BOLD, 16));
        bt.setForeground(Color.WHITE);
        bt.setFocusPainted(false);
        bt.setBorderPainted(false);
        bt.setContentAreaFilled(false);
        bt.setOpaque(false);
        bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bt.setBorder(new EmptyBorder(0, 8, 0, 0));
        return bt;
    }

    private static ImageIcon loadIcon(String path, int w, int h) {
        URL url = Alert.class.getResource(path);
        if (url == null) throw new IllegalArgumentException("Icône introuvable : " + path);

        Image img = new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    /**
     * KeyBindings pro au niveau du rootPane :
     * - ENTER => clique sur btnOk (OK / Oui)
     * - ESC   => si confirm => clique "Non", sinon => ferme
     *
     * Important : on N'utilise PAS setDefaultButton(btnOk)
     * pour ne pas changer le style/couleur du bouton (bleu sur macOS/Nimbus).
     */
    private static void installDialogKeyBindings(JDialog dialog, JButton btnOk, JButton btnNo) {
        JRootPane root = dialog.getRootPane();

        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        // ENTER : action principale
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ALERT_OK");
        am.put("ALERT_OK", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                btnOk.doClick();
            }
        });

        // ESC : annuler (Non si confirm, sinon close)
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ALERT_CANCEL");
        am.put("ALERT_CANCEL", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (btnNo != null) btnNo.doClick();
                else dialog.dispose();
            }
        });
    }

    // 🖱️ Permet de déplacer une fenêtre undecorated
    private static void makeDraggable(JDialog dialog, JComponent dragArea) {
        final Point[] click = {null};

        dragArea.addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                click[0] = e.getPoint();
            }
        });

        dragArea.addMouseMotionListener(new MouseAdapter() {
            @Override public void mouseDragged(MouseEvent e) {
                if (click[0] == null) return;
                Point p = e.getLocationOnScreen();
                dialog.setLocation(p.x - click[0].x, p.y - click[0].y);
            }
        });
    }
}
