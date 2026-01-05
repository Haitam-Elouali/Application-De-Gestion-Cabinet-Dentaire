package ma.TeethCare.mvc.ui.palette.menu;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import ma.TeethCare.mvc.ui.palette.utils.ImageTools;

public class MyMenuBar extends JMenuBar {

    // 🎨 Palette (à ajuster selon ton thème)
    private static final Color MENU_BG        = new Color(32, 34, 37);
    private static final Color MENU_FG        = Color.WHITE;
    private static final Color MENU_HOVER_BG  = new Color(60, 63, 65);

    private static final Font MENU_FONT =
            new Font("Optima", Font.PLAIN, 16);

    public MyMenuBar(ActionListener onLogout, ActionListener onExit) {

        setBackground(MENU_BG);
        setBorder(new EmptyBorder(4, 8, 4, 8));

        JMenu sessionMenu = buildMenu(
                "Session",
                "/static/icons/menu/session.png"
        );

        JMenuItem logout = buildMenuItem(
                "Déconnexion",
                "/static/icons/menu/logout.png",
                onLogout
        );

        JMenuItem exit = buildMenuItem(
                "Quitter",
                "/static/icons/menu/exit.png",
                onExit
        );

        sessionMenu.add(logout);
        sessionMenu.addSeparator();
        sessionMenu.add(exit);

        add(sessionMenu);
    }

    // ———————————— Menu UI Builders
    private JMenu buildMenu(String text, String iconPath) {
        JMenu menu = new JMenu(text);
        menu.setFont(MENU_FONT);
        menu.setForeground(MENU_FG);
        menu.setIcon(ImageTools.loadIcon(iconPath, 35, 35));
        menu.setOpaque(true);
        menu.setBackground(MENU_BG);

        // Hover menu (pro)
        menu.getPopupMenu().setBorder(BorderFactory.createLineBorder(MENU_BG));
        menu.getPopupMenu().setBackground(MENU_BG);

        return menu;
    }
    private JMenuItem buildMenuItem(String text, String iconPath, ActionListener action) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(MENU_FONT);
        item.setForeground(MENU_FG);
        item.setIcon(ImageTools.loadIcon(iconPath, 35, 35));
        item.setOpaque(true);
        item.setBackground(MENU_BG);
        item.setBorder(new EmptyBorder(6, 12, 6, 12));

        item.addActionListener(action);

        // Hover effect
        item.addChangeListener(e -> {
            ButtonModel model = item.getModel();
            if (model.isArmed() || model.isSelected()) {
                item.setBackground(MENU_HOVER_BG);
            } else {
                item.setBackground(MENU_BG);
            }
        });

        return item;
    }


}
