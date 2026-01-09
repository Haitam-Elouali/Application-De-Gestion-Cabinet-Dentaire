package ma.TeethCare.mvc.ui.palette.utils;

import javax.swing.*;
import java.awt.*;
import ma.TeethCare.mvc.ui.palette.alert.Alert;

public class HeaderWindowControls extends JPanel {

    private final JButton btFull;

    public HeaderWindowControls(JFrame frame, JComponent headerBanner) {
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.RIGHT, 6, 0));

        JButton btMin = miniButton("—");
        btMin.addActionListener(e -> frame.setState(Frame.ICONIFIED));

        btFull = miniButton("⬜"); // sera géré par WindowChromeSupport

        JButton btClose = miniButton("✕");
        //btClose.addActionListener(e -> frame.dispose());
        btClose.addActionListener(e -> {
            boolean ok = Alert.confirm(frame, "Voulez-vous vraiment quitter l’application ?");
            if (ok) {
                System.exit(0);
            }
        });

        add(btMin);
        add(btFull);
        add(btClose);

        // Installer les bonus
        WindowChromeSupport.Options opt = new WindowChromeSupport.Options();
        opt.enableF11Shortcut = true;
        opt.enableDoubleClickOnHeader = true;
        opt.animationDurationMs = 180;          // animation douce
        opt.useMaximumWindowBounds = true;      // évite taskbar/dock
        opt.iconEnterFullscreen = "⬜";
        opt.iconExitFullscreen  = "🗗";

        WindowChromeSupport.install(frame, headerBanner, btFull, opt);
    }

    private JButton miniButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFont(new Font("Optima", Font.BOLD, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}
