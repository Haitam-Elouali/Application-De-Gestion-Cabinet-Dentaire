package ma.TeethCare.mvc.ui.pages.commonPages;

import ma.TeethCare.config.ApplicationContext;
import ma.TeethCare.mvc.dto.authentificationDtos.UserPrincipal;
import ma.TeethCare.mvc.dto.profileDtos.ProfileData;
import ma.TeethCare.mvc.ui.pages.pagesNames.ApplicationPages;
import ma.TeethCare.mvc.ui.palette.utils.ImageTools;
import ma.TeethCare.mvc.ui.palette.utils.UIConstants;
import ma.TeethCare.service.profileService.api.ProfileService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class HeaderBannerPanel extends JPanel {

    private final JLabel logoLabel = new JLabel();
    private final JLabel lblUserName = new JLabel("");
    private final JLabel lblRole = new JLabel("");
    private final JLabel avatarLabel = new JLabel();
    private final Consumer<ApplicationPages> onNavigate;

    private static final String LOGO_PATH = "/static/icons/logo.png";
    private static final String AVATAR_PATH = "/static/icons/user.png";

    public HeaderBannerPanel(UserPrincipal principal, Consumer<ApplicationPages> onNavigate) {
        this.onNavigate = onNavigate;

        setLayout(new BorderLayout(20, 0));
        setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 2, 0, UIConstants.BORDER_GRAY),
                new EmptyBorder(12, 24, 12, 24)));
        setBackground(Color.WHITE);
        setOpaque(true);

        // Logo
        logoLabel.setIcon(ImageTools.loadIcon(LOGO_PATH, 48, 48));
        add(logoLabel, BorderLayout.WEST);

        // Right Content: User Info + Notifications (Handled by parent maybe? No, let's
        // keep it here for now)
        add(buildRightSection(), BorderLayout.EAST);

        refresh(principal);
        refreshFromContext(principal);
    }

    private JComponent buildRightSection() {
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        right.setOpaque(false);

        // User Info Container
        JPanel userBox = new JPanel(new GridBagLayout());
        userBox.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        lblUserName.setFont(UIConstants.FONT_BOLD.deriveFont(15f));
        lblUserName.setForeground(UIConstants.TEXT_DARK);
        lblRole.setFont(UIConstants.FONT_REGULAR.deriveFont(12f));
        lblRole.setForeground(UIConstants.TEXT_GRAY);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(lblUserName);
        textPanel.add(lblRole);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        userBox.add(textPanel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 12, 0, 0);
        avatarLabel.setPreferredSize(new Dimension(40, 40));
        setDefaultAvatar();
        userBox.add(avatarLabel, gbc);

        right.add(userBox);
        return right;
    }

    public void refresh(UserPrincipal principal) {
        if (principal == null)
            return;
        lblUserName.setText(principal.nom() != null ? principal.nom().toUpperCase() : "UTILISATEUR");
        lblRole.setText(principal.rolePrincipal() != null ? principal.rolePrincipal().name() : "-");
    }

    public void refreshFromContext(UserPrincipal principal) {
        try {
            var profileService = ApplicationContext.getBean(ProfileService.class);
            var profile = profileService.loadByUserId(principal.id());
            refreshFromProfile(profile);
        } catch (Exception ignored) {
        }
    }

    public void refreshFromProfile(ProfileData profile) {
        if (profile == null)
            return;
        String fullName = (profile.prenom() + " " + profile.nom()).trim();
        lblUserName.setText(fullName.isBlank() ? "SANS NOM" : fullName.toUpperCase());

        BufferedImage img = ImageTools.loadBufferedImageFromPath(profile.avatar()); // Assuming this exists or similar
        if (img != null) {
            avatarLabel.setIcon(new ImageIcon(makeCircleAvatar(img, 40)));
        } else {
            setDefaultAvatar();
        }
    }

    private void setDefaultAvatar() {
        avatarLabel.setIcon(ImageTools.loadIcon(AVATAR_PATH, 40, 40));
    }

    private static BufferedImage makeCircleAvatar(BufferedImage src, int size) {
        BufferedImage out = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = out.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new Ellipse2D.Double(0, 0, size, size));
        g2.drawImage(src, 0, 0, size, size, null);
        g2.dispose();
        return out;
    }
}
