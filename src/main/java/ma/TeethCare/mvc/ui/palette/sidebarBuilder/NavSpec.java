package ma.TeethCare.mvc.ui.palette.sidebarBuilder;

public record NavSpec(
        String section,      // "Général", "Gestion", ...
        String label,        // "Patients"
        String iconPath,     // "/static/icons/menu/patient.png"
        String pageId,       // "PATIENT"
        String privilege     // Privileges.PATIENT_ACCESS (ou null)
)
{}

