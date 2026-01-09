package ma.TeethCare.mvc.ui.frames;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import lombok.Getter;
import lombok.Setter;
import ma.TeethCare.mvc.ui.palette.utils.ImageTools;
import ma.TeethCare.service.authentificationService.api.AuthorizationService;
import ma.TeethCare.service.profileService.api.ProfileService;

@Getter
@Setter
public class DashboardUI extends JFrame {

    private final DashboardController controller;
    private final AuthorizationService authorizationService;

    private UserPrincipal principal;

    // UI parts
    private HeaderBannerPanel headerBanner;
    private FooterPanel footer;
    private CenterPanel center;

    // Drag window
    private Point dragOffset;

    public DashboardUI(DashboardController controller,
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

        setJMenuBar(buildMenuBar());
        setContentPane(buildRoot());

        // default
        navigateTo(ApplicationPages.DASHBOARD);

        showNotificationsCount(5, NotificationLevel.INFO);

        setVisible(true);
    }

    // API Controller -> View ———————————————————————————————————————————————
    public void refreshSession(UserPrincipal principal) {
        this.principal = principal;
        if (headerBanner != null)
            headerBanner.refresh(principal);
        // if (sideBar != null) sideBar.refresh(principal); // si tu as cette méthode
        // (sinon supprime la ligne)
    }

    public void refreshHeaderFromProfile(ProfileData p) {
        if (p == null)
            return;

        // 1) Mettre à jour principal (session) si tu veux refléter partout
        // (selon ton design, tu peux aussi juste rafraîchir le header)
        if (principal != null) {
            // On garde l'id/login/roles/privileges existants, on remplace juste
            // nom/email/avatar si souhaité
            // ⚠️ Ici, UserPrincipal est un record ? sinon adapte.
            principal = new UserPrincipal(
                    principal.id(),
                    (safe(p.prenom()) + " " + safe(p.nom())).trim(),
                    p.email(),
                    principal.login(),
                    principal.rolePrincipal(),
                    principal.roles(),
                    principal.privileges());
        }

        // 2) Rafraîchir le header
        if (headerBanner != null) {
            headerBanner.refresh(principal);

            // avatar : si ton HeaderBannerPanel supporte un setter d’avatar, appelle-le ici
            // headerBanner.setAvatarPath(p.avatar());
            try {
                // p.avatar() = "avatars/u1_xxx.png" (relatif)
                ImageIcon icon = ImageTools.loadAvatarFromProfilePath(p.avatar(), 40, 40);
                headerBanner.setAvatarIcon(icon);
            } catch (Exception ignored) {
            }

        }
    }

    // petit helper local
    private String safe(String s) {
        return s == null ? "" : s;
    }

    public void navigateTo(ApplicationPages page) {
        openPage(page);
    }

    public void showNotificationsCount(int count) {
        if (headerBanner != null)
            headerBanner.setNotificationCount(count);
    }

    public void showNotificationsCount(int count, NotificationLevel level) {
        if (headerBanner != null)
            headerBanner.setNotificationCount(count, level);
    }

    /**
     * Navigation : le SideBar appelle cette méthode.
     * - Le controller construit/retourne le panel de la page demandée
     * - Le panel est injecté dans le CenterPanel (CardLayout)
     *
     * Côté controller, adapte la signature pour retourner un JComponent :
     * JComponent onNavigateRequested(DashboardPage page);
     */
    private void openPage(ApplicationPages page) {
        if (page == null || center == null)
            return;

        JComponent view = controller.onNavigateRequested(page);
        if (view != null)
            center.upsertPage(page, view);
        center.showPage(page);
    }

    // UI Builders ———————————————————————————————————————————————————————
    private JMenuBar buildMenuBar() {
        return new MyMenuBar(
                e -> controller.onLogoutRequested(),
                e -> controller.onExitRequested());
    }

    private JComponent buildTopBar() {
        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.setOpaque(false);

        headerBanner = new HeaderBannerPanel(principal, this::openPage);

        // charger et afficher l’avatar déjà en BD dès le démarrage
        try {
            var profileService = ApplicationContext.getBean(ProfileService.class);
            var profile = profileService.loadByUserId(principal.id());
            headerBanner.refreshFromProfile(profile);
        } catch (Exception ignored) {
            /* on garde l’avatar default si erreur */}

        // ✅ on passe headerBanner
        JComponent controls = new HeaderWindowControls(this, headerBanner);

        installWindowDrag(headerBanner);
        installWindowDrag(top);

        top.add(headerBanner, BorderLayout.CENTER);
        top.add(controls, BorderLayout.EAST);
        return top;
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

        var items = NavigationSpecs.forPrincipal(principal);

        return SidebarBuilder.build(
                this, // parentForAlerts
                principal,
                authorizationService,
                items,
                (source, pageId) -> {
                    ApplicationPages page = ApplicationPages.valueOf(pageId);
                    openPage(page);
                },
                true // hideForbidden : true = cacher, false = désactiver
        );
    }

    private JComponent buildCenter() {
        center = new CenterPanel();
        return center;
    }

    private JComponent buildFooter() {
        footer = new FooterPanel();
        return footer;
    }

    private JPanel buildRoot() {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(UIConstants.SURFACE_MAIN);

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
