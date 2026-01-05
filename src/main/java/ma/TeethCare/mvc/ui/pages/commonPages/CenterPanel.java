package ma.TeethCare.mvc.ui.pages.commonPages;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;
import javax.swing.*;
import ma.TeethCare.mvc.ui.pages.pagesNames.ApplicationPages;

public class CenterPanel extends JPanel {

    private final CardLayout cardLayout = new CardLayout();
    private final Map<ApplicationPages, JComponent> pages = new EnumMap<>(ApplicationPages.class);

    public CenterPanel() {
        setLayout(cardLayout);
        setOpaque(false);
    }

    public void registerPage(ApplicationPages page, JComponent view) {
        upsertPage(page, view);
    }

    /**
     * Ajoute ou remplace la vue d'une page (utile quand le controller retourne un nouveau panel).
     */
    public void upsertPage(ApplicationPages page, JComponent view) {
        if (page == null || view == null) return;

        JComponent old = pages.put(page, view);
        if (old != null) remove(old);

        add(view, page.name());
        revalidate();
        repaint();
    }

    public void showPage(ApplicationPages page) {
        if (!pages.containsKey(page)) return;
        cardLayout.show(this, page.name());
    }
}
