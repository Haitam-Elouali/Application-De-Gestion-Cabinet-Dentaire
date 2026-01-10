package ma.TeethCare.mvc.ui.pages.dashboardPages.pageFactory;

import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.common.enums.RoleType;

import javax.swing.*;
import ma.TeethCare.mvc.ui.pages.dashboardPages.AdminDashboardPanel;
import ma.TeethCare.mvc.ui.pages.dashboardPages.DefaultDashboardPanel;
import ma.TeethCare.mvc.ui.pages.dashboardPages.DoctorDashboardPanel;
import ma.TeethCare.mvc.ui.pages.dashboardPages.SecretaryDashboardPanel;

public final class DashboardPanelFactory {

    private DashboardPanelFactory() {
    }

    public static JComponent create(UserPrincipal principal) {
        RoleType role = null;
        if (principal != null && principal.roles() != null && !principal.roles().isEmpty()) {
            try {
                role = RoleType.valueOf(principal.roles().get(0));
            } catch (IllegalArgumentException e) {
                // Invalid role, will default to null
            }
        }

        if (role == null) {
            return new DefaultDashboardPanel(principal);
        }

        return switch (role) {
            case ADMIN -> new AdminDashboardPanel(principal);
            case MEDECIN -> new DoctorDashboardPanel(principal);
            case SECRETAIRE -> new SecretaryDashboardPanel(principal);
            default -> new DefaultDashboardPanel(principal);
        };
    }
}
