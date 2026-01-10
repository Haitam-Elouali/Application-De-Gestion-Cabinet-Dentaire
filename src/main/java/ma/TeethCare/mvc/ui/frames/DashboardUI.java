package ma.TeethCare.mvc.ui.frames;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import lombok.Getter;
import lombok.Setter;
import ma.TeethCare.mvc.ui.palette.utils.ImageTools;
import ma.TeethCare.service.modules.auth.api.AuthorizationService;
import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
// import ma.TeethCare.service.profileService.api.ProfileService; // COMMENTED: ProfileService does not exist

@Getter
@Setter
public class DashboardUI extends JFrame {

    // private final DashboardController controller; // COMMENTED:
    // DashboardController does not exist
    private final Object controller; // Placeholder
    private final AuthorizationService authorizationService;

    private UserPrincipal principal;

    // UI parts
    // private HeaderBannerPanel headerBanner; // COMMENTED: HeaderBannerPanel does
    // not exist
    // private FooterPanel footer; // COMMENTED: FooterPanel does not exist
    // private CenterPanel center; // COMMENTED: CenterPanel does not exist

    // Drag window
    private Point dragOffset;

    public DashboardUI(Object controller, // Changed DashboardController to Object
            AuthorizationService authorizationService,
            UserPrincipal principal) {

        this.controller = controller;
        this.authorizationService = authorizationService;
        this.principal = principal;

        setSize(1620, 1020);
        setLocationRelativeTo(null);
        setResizable(true);

        // ✅ comme LoginUI
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // COMMENTED: Missing classes - buildMenuBar(), buildRoot(), ApplicationPages,
        // NotificationLevel
        // setJMenuBar(buildMenuBar());
        // setContentPane(buildRoot());
        // navigateTo(ApplicationPages.DASHBOARD);
        // showNotificationsCount(5, NotificationLevel.INFO);

        setVisible(true);
    }

    // API Controller -> View ———————————————————————————————————————————————
    public void refreshSession(UserPrincipal principal) {
        this.principal = principal;
        // COMMENTED: headerBanner does not exist
        // if (headerBanner != null)
        // headerBanner.refresh(principal);
    }

    public void refreshHeaderFromProfile(Object p) { // Changed ProfileData to Object
        // COMMENTED: ProfileData and headerBanner do not exist
        // if (p == null)
        // return;
        // ... entire method body commented
    }

    // petit helper local
    private String safe(String s) {
        return s == null ? "" : s;
    }

    public void navigateTo(Object page) { // Changed ApplicationPages to Object
        // COMMENTED: ApplicationPages does not exist
        // openPage(page);
    }

    public void showNotificationsCount(int count) {
        // COMMENTED: headerBanner does not exist
        // if (headerBanner != null)
        // headerBanner.setNotificationCount(count);
    }

    public void showNotificationsCount(int count, Object level) { // Changed NotificationLevel to Object
        // COMMENTED: headerBanner does not exist
        // if (headerBanner != null)
        // headerBanner.setNotificationCount(count, level);
    }

    /**
     * Navigation : le SideBar appelle cette méthode.
     * - Le controller construit/retourne le panel de la page demandée
     * - Le panel est injecté dans le CenterPanel (CardLayout)
     *
     * Côté controller, adapte la signature pour retourner un JComponent :
     * JComponent onNavigateRequested(DashboardPage page);
     */
    private void openPage(Object page) { // Changed ApplicationPages to Object
        // COMMENTED: center does not exist
        // if (page == null || center == null)
        // return;
        // JComponent view = controller.onNavigateRequested(page);
        // if (view != null)
        // center.upsertPage(page, view);
        // center.showPage(page);
    }

    // UI Builders ———————————————————————————————————————————————————————
    private JMenuBar buildMenuBar() {
        // COMMENTED: MyMenuBar does not exist
        // return new MyMenuBar(
        // e -> controller.onLogoutRequested(),
        // e -> controller.onExitRequested());
        return new JMenuBar(); // Return empty menu bar
    }

    private JComponent buildTopBar() {
        // COMMENTED: HeaderBannerPanel, ApplicationContext, ProfileService do not exist
        // JPanel top = new JPanel(new BorderLayout(10, 0));
        // top.setOpaque(false);

        // headerBanner = new HeaderBannerPanel(principal, this::openPage);

        // // charger et afficher l’avatar déjà en BD dès le démarrage
        // try {
        // var profileService = ApplicationContext.getBean(ProfileService.class);
        // var profile = profileService.loadByUserId(principal.id());
        // headerBanner.refreshFromProfile(profile);
        // } catch (Exception ignored) {
        // /* on garde l’avatar default si erreur */}

        // // ✅ on passe headerBanner
        // JComponent controls = new HeaderWindowControls(this, headerBanner);

        // installWindowDrag(headerBanner);
        // installWindowDrag(top);

        // top.add(headerBanner, BorderLayout.CENTER);
        // top.add(controls, BorderLayout.EAST);
        // return top;
        return new JPanel(); // Return empty panel
    }

    // la fenêtre sans décoration, a perdu l'option de la glisser dans l'écran là où
    // on veut
    // avec installWindowDrag avec un component en paramètre, on peut ajouter un
    // mouvement (Motion) de glissement à partir de ce componenet
    private void installWindowDrag(Component c) {
        c.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragOffset = e.getPoint();
            }
        });
        c.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragOffset == null)
                    return;
                Point p = e.getLocationOnScreen();
                setLocation(p.x - dragOffset.x, p.y - dragOffset.y);
            }
        });
    }

    private JComponent buildSideBar() {
        // COMMENTED: NavigationSpecs, SidebarBuilder, ApplicationPages do not exist
        // var items = NavigationSpecs.forPrincipal(principal);
        // return SidebarBuilder.build(...)
        return new JPanel(); // Return empty panel
    }

    private JComponent buildCenter() {
        // COMMENTED: CenterPanel does not exist
        // center = new CenterPanel();
        // return center;
        return new JPanel(); // Return empty panel
    }

    private JComponent buildFooter() {
        // COMMENTED: FooterPanel does not exist
        // footer = new FooterPanel();
        // return footer;
        return new JPanel(); // Return empty panel
    }

    private JPanel buildRoot() {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        // COMMENTED: UIConstants does not exist
        // root.setBackground(UIConstants.SURFACE_MAIN);
        root.setBackground(Color.WHITE);

        // North: Top Bar (Header)
        root.add(buildTopBar(), BorderLayout.NORTH);

        // West: Sidebar
        root.add(buildSideBar(), BorderLayout.WEST);

        // Center: Content
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(new EmptyBorder(0, 0, 0, 0)); // Content itself has padding
        centerWrapper.add(buildCenter(), BorderLayout.CENTER);

        root.add(centerWrapper, BorderLayout.CENTER);

        // South: Optional Footer
        // root.add(buildFooter(), BorderLayout.SOUTH);

        return root;
    }

}
