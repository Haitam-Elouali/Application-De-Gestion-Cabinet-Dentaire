package ma.TeethCare.mvc.ui.palette.sidebarBuilder;

import ma.TeethCare.mvc.dto.authentificationDtos.UserPrincipal;
import ma.TeethCare.mvc.ui.palette.alert.Alert;
import ma.TeethCare.mvc.ui.palette.buttons.PillNavButtonLight;
import ma.TeethCare.mvc.ui.palette.utils.ImageTools;
import ma.TeethCare.mvc.ui.palette.utils.UIConstants;
import ma.TeethCare.service.authentificationService.api.AuthorizationService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public final class SidebarBuilder {

    private SidebarBuilder() {
    }

    public interface Navigator {
        void go(AbstractButton source, String pageId);
    }

    public static JComponent build(
            Component parentForAlerts,
            UserPrincipal principal,
            AuthorizationService auth,
            List<NavSpec> items,
            Navigator navigator,
            boolean hideForbidden) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(20, 0, 20, 0));
        sidebar.setBackground(UIConstants.SURFACE_GREEN_100);
        sidebar.setPreferredSize(new Dimension(260, 0));

        // Group by section
        Map<String, List<NavSpec>> bySection = new LinkedHashMap<>();
        java.util.List<PillNavButtonLight> allButtons = new java.util.ArrayList<>();

        for (NavSpec it : items) {
            bySection.computeIfAbsent(it.section(), k -> new ArrayList<>()).add(it);
        }

        for (var entry : bySection.entrySet()) {
            sidebar.add(sectionTitle(entry.getKey()));

            for (NavSpec spec : entry.getValue()) {
                boolean allowed = (spec.privilege() == null || spec.privilege().isBlank())
                        || auth.hasPrivilege(principal, spec.privilege());

                if (!allowed && hideForbidden)
                    continue;

                ImageIcon icon = safeIcon(spec.iconPath(), 22, 22);
                PillNavButtonLight btn = new PillNavButtonLight(spec.label(), icon);

                btn.setEnabled(allowed);
                btn.addActionListener(e -> {
                    if (!btn.isEnabled()) {
                        Alert.warning(parentForAlerts, "Accès refusé : privilège requis = " + spec.privilege());
                        return;
                    }
                    for (PillNavButtonLight b : allButtons)
                        b.setActive(false);
                    btn.setActive(true);
                    navigator.go(btn, spec.pageId());
                });

                sidebar.add(btn);
                allButtons.add(btn);
            }
            sidebar.add(Box.createVerticalStrut(15));
        }

        sidebar.add(Box.createVerticalGlue());

        JScrollPane sp = new JScrollPane(sidebar);
        sp.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, UIConstants.BORDER_GREEN));
        sp.getVerticalScrollBar().setUnitIncrement(14);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return sp;
    }

    private static JLabel sectionTitle(String text) {
        JLabel l = new JLabel(text.toUpperCase());
        l.setFont(UIConstants.FONT_BOLD.deriveFont(11f));
        l.setForeground(UIConstants.TEXT_GRAY);
        l.setBorder(new EmptyBorder(10, 24, 8, 24));
        return l;
    }

    private static ImageIcon safeIcon(String path, int w, int h) {
        try {
            return ImageTools.loadIcon(path, w, h);
        } catch (Exception ex) {
            return new ImageIcon(new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB));
        }
    }
}
