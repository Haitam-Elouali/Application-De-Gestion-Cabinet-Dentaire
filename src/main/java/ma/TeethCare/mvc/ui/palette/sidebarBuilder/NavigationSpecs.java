package ma.TeethCare.mvc.ui.palette.sidebarBuilder;

import ma.TeethCare.common.enums.RoleType;
import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.mvc.ui.pages.pagesNames.ApplicationPages;

import java.util.ArrayList;
import java.util.List;

// import static ma.TeethCare.security.Privileges.*; // COMMENTED: Privileges class does not exist

public final class NavigationSpecs {

        private NavigationSpecs() {
        }

        public static List<NavSpec> forPrincipal(UserPrincipal principal) {

                boolean isAdmin = principal != null && principal.roles() != null
                                && principal.roles().contains(RoleType.ADMIN.name());
                boolean isMedecin = principal != null && principal.roles() != null
                                && principal.roles().contains(RoleType.MEDECIN.name());

                List<NavSpec> items = new ArrayList<>();

                // -------- Général (commun)
                items.add(item("Général", "Dashboard", "/static/icons/home.png", ApplicationPages.DASHBOARD, null));
                items.add(item("Général", "Profil", "/static/icons/profile.png", ApplicationPages.PROFILE, null));
                items.add(item("Général", "Notifications", "/static/icons/bell.png", ApplicationPages.NOTIFICATIONS,
                                null));

                // -------- Admin
                if (isAdmin) {
                        items.add(item("Administration", "Cabinets", "/static/icons/cabinet.png",
                                        ApplicationPages.CABINETS,
                                        null)); // CABINET_ACCESS commented - Privileges class missing
                        items.add(item("Administration", "Utilisateurs", "/static/icons/users.png",
                                        ApplicationPages.USERS,
                                        null)); // USERS_ACCESS commented - Privileges class missing
                        items.add(item("Système", "Paramètres", "/static/icons/param.png", ApplicationPages.PARAMETRAGE,
                                        null)); // CABINET_ACCESS commented - Privileges class missing
                }

                // -------- Médecin
                if (isMedecin) {
                        items.add(item("Cabinet", "Patients", "/static/icons/patient.png", ApplicationPages.PATIENTS,
                                        null)); // PATIENT_ACCESS commented - Privileges class missing
                        items.add(item("Cabinet", "Caisse", "/static/icons/caisse.png", ApplicationPages.CAISSE, null)); // CAISSE_ACCESS
                                                                                                                         // commented
                                                                                                                         // -
                                                                                                                         // Privileges
                                                                                                                         // class
                                                                                                                         // missing
                        items.add(item("Cabinet", "Cabinet", "/static/icons/cabinet.png", ApplicationPages.CABINET,
                                        null));
                        items.add(item("Système", "Paramètres", "/static/icons/param.png", ApplicationPages.PARAMETRAGE,
                                        null)); // CABINET_ACCESS commented - Privileges class missing
                }

                // Option: secrétaire = sinon, tu peux ajouter un bloc else/if role SECRETAIRE

                return items;
        }

        private static NavSpec item(String section, String label, String iconPath,
                        ApplicationPages page, String privilegeOrNull) {
                return new NavSpec(section, label, iconPath, page.name(), privilegeOrNull);
        }
}
