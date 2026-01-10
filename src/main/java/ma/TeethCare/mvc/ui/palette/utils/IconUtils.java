package ma.TeethCare.mvc.ui.palette.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class IconUtils {

    public enum IconType {
        // Original Vectors (kept for fallback/utility)
        MENU, CLOSE, SEARCH, PLUS, CHECK_CIRCLE, CHECK,
        
        // New Image-based Types
        ICON_DASHBOARD, ICON_AGENDA, ICON_RDV, ICON_PATIENTS, 
        ICON_MEDICAL_RECORDS, ICON_CASH, ICON_FINANCIAL, ICON_ANTECEDENTS,
        ICON_VIEW, ICON_EDIT, ICON_DELETE, ICON_EURO, ICON_LOGO, ICON_HIDE, ICON_AVERAGE,
        ICON_CERTIFICATE, ICON_CONSULTATION, ICON_PRESCRIPTION, ICON_ACTS,
        ICON_PRINT, ICON_BILL, ICON_PERCENT, ICON_RDV_MANAGE, ICON_LINK,
        
        // Specific User Requested Icons
        ICON_DISABLE, ICON_SECURITY, ICON_ROLES, ICON_USERS_ADMIN, ICON_INSURANCE,
        
        // Mapped or Vector fallbacks
        USER, USERS, SHIELD, ACTIVITY, PILL, FILE_WARNING, BUILDING, LOCK,
        HOME, PATIENT, STETHOSCOPE, CLIPBOARD, CALENDAR, EURO, FILE_TEXT, CHEVRON_RIGHT,
        PRINTER, ALERT_TRIANGLE, EXPENSES, PROFIT
    }

    private static final Map<String, ImageIcon> cache = new HashMap<>();
    private static final Map<IconType, String> IMAGE_MAP = new HashMap<>();

    static {
        IMAGE_MAP.put(IconType.ICON_DASHBOARD, "/icons/dashboard.png");
        IMAGE_MAP.put(IconType.ICON_AGENDA, "/icons/agenda.png");
        IMAGE_MAP.put(IconType.ICON_RDV, "/icons/appointment.png");
        IMAGE_MAP.put(IconType.ICON_PATIENTS, "/icons/patient.png");
        IMAGE_MAP.put(IconType.ICON_MEDICAL_RECORDS, "/icons/medical_record.png");
        IMAGE_MAP.put(IconType.ICON_CASH, "/icons/cash.png");
        IMAGE_MAP.put(IconType.ICON_FINANCIAL, "/icons/financial.png");
        IMAGE_MAP.put(IconType.ICON_ANTECEDENTS, "/icons/antecedent.png");
        // COMPREHENSIVE MAPPING - User Specified
        IMAGE_MAP.put(IconType.ICON_VIEW, "/icons/consulter_img.png");
        IMAGE_MAP.put(IconType.ICON_EDIT, "/icons/modifier_img.png");
        IMAGE_MAP.put(IconType.ICON_DELETE, "/icons/supprimer_img.png");
        IMAGE_MAP.put(IconType.ICON_DISABLE, "/icons/disable.png");
        
        IMAGE_MAP.put(IconType.ICON_USERS_ADMIN, "/icons/utilisateur_png.png");
        IMAGE_MAP.put(IconType.ICON_ROLES, "/icons/role_img.png");
        IMAGE_MAP.put(IconType.ICON_ACTS, "/icons/actes.png");
        IMAGE_MAP.put(IconType.ICON_PRESCRIPTION, "/icons/medicament_img.png");
        IMAGE_MAP.put(IconType.ICON_ANTECEDENTS, "/icons/antecedent_img.png");
        IMAGE_MAP.put(IconType.ICON_INSURANCE, "/icons/assurance.png");
        IMAGE_MAP.put(IconType.ICON_FINANCIAL, "/icons/assurance.png"); // Map financial to assurance if appropriate, or keep separate? User said "Assurances : assurance.png". Financial is usually situation. I'll keep Financial separate or map to something else, but AdminSidebar uses INSURANCE.
        IMAGE_MAP.put(IconType.ICON_SECURITY, "/icons/security_img.png");
        
        // Legacy/Generic Mappings
        IMAGE_MAP.put(IconType.ICON_LOGO, "/icons/logo_transparent.png"); 
        IMAGE_MAP.put(IconType.ICON_HIDE, "/icons/cacher.png");
        IMAGE_MAP.put(IconType.HOME, "/icons/dashboard.png");
        
        // Admin Sidebar Specifics (previously mapped generic)
        // Now explicit in AdminSidebar.java, so we just need valid keys above.
        
        // Doctor Sidebar Mappings
        IMAGE_MAP.put(IconType.ICON_PATIENTS, "/icons/patient.png"); // Doctor uses this
        IMAGE_MAP.put(IconType.ICON_DASHBOARD, "/icons/dashboard.png");
        IMAGE_MAP.put(IconType.ICON_AGENDA, "/icons/agenda.png");
        IMAGE_MAP.put(IconType.ICON_MEDICAL_RECORDS, "/icons/medical_record.png");
        IMAGE_MAP.put(IconType.ICON_RDV, "/icons/appointment.png");
        IMAGE_MAP.put(IconType.ICON_CASH, "/icons/cash.png");
        
        // Others
        IMAGE_MAP.put(IconType.EURO, "/icons/euro.png");
        IMAGE_MAP.put(IconType.CLIPBOARD, "/icons/medical_record.png");
        IMAGE_MAP.put(IconType.SEARCH, "/icons/search.png");
        IMAGE_MAP.put(IconType.PRINTER, "/icons/print.png");
        IMAGE_MAP.put(IconType.FILE_WARNING, "/icons/antecedent_img.png"); 
        IMAGE_MAP.put(IconType.SHIELD, "/icons/role_img.png"); // Fallback for roles
        IMAGE_MAP.put(IconType.ALERT_TRIANGLE, "/icons/antecedent_img.png");
        IMAGE_MAP.put(IconType.CHECK_CIRCLE, "/icons/appointment.png"); 
        IMAGE_MAP.put(IconType.CHECK, "/icons/appointment.png");
        IMAGE_MAP.put(IconType.PLUS, "/icons/edit.png"); 
        
        // Mapped above already, keeping for safety or removing duplicates manually by overwrite
        // IMAGE_MAP.put(IconType.ICON_DELETE, "/icons/delete.png"); // OVERRIDDEN BY USER REQUEST
        // IMAGE_MAP.put(IconType.ICON_EDIT, "/icons/edit.png"); // OVERRIDDEN
        // IMAGE_MAP.put(IconType.ICON_VIEW, "/icons/view.png"); // OVERRIDDEN
        
        IMAGE_MAP.put(IconType.MENU, "/icons/dashboard.png"); 
        IMAGE_MAP.put(IconType.ACTIVITY, "/icons/dashboard.png");
        IMAGE_MAP.put(IconType.CALENDAR, "/icons/agenda.png");
        IMAGE_MAP.put(IconType.PATIENT, "/icons/patient.png");
        IMAGE_MAP.put(IconType.STETHOSCOPE, "/icons/medical_record.png"); 
        IMAGE_MAP.put(IconType.PILL, "/icons/medicament_img.png"); // Updated to medicament_img
        IMAGE_MAP.put(IconType.BUILDING, "/icons/assurance.png"); // Updated to assurance
        IMAGE_MAP.put(IconType.LOCK, "/icons/security_img.png"); // Updated to security
        IMAGE_MAP.put(IconType.FILE_TEXT, "/icons/medical_record.png");
        IMAGE_MAP.put(IconType.CHEVRON_RIGHT, "/icons/view.png"); 

        // New specific mappings
        IMAGE_MAP.put(IconType.ICON_AVERAGE, "/icons/average.png");
        IMAGE_MAP.put(IconType.EXPENSES, "/icons/expense.png");
        IMAGE_MAP.put(IconType.PROFIT, "/icons/profit.png");
        IMAGE_MAP.put(IconType.ICON_CERTIFICATE, "/icons/certificate_img.png");
        IMAGE_MAP.put(IconType.ICON_CONSULTATION, "/icons/consultation.png");
        IMAGE_MAP.put(IconType.ICON_PRESCRIPTION, "/icons/ordonnance_img.png");
        IMAGE_MAP.put(IconType.ICON_ACTS, "/icons/actes.png");
        IMAGE_MAP.put(IconType.ICON_PRINT, "/icons/print.png");
        IMAGE_MAP.put(IconType.ICON_BILL, "/icons/bill.png");
        IMAGE_MAP.put(IconType.ICON_PERCENT, "/icons/percent.png");
        IMAGE_MAP.put(IconType.ICON_RDV_MANAGE, "/icons/rdv_manage.png");
        IMAGE_MAP.put(IconType.ICON_LINK, "/icons/link.png");
        
        // Ensure Logo is JPEG
        // IMAGE_MAP.put(IconType.ICON_LOGO, "/icons/logo.jpeg"); // Already added above
        
        // I should add types for the new images if I want to use `getIcon(IconType.EXPENSES...)`
        // But IconType enum doesn't have them yet. I need to add them to enum first?
        // Wait, user provided images but didn't ask me to *use* them in specific places except 'depenses', 'benefices', 'montant moyen'.
        // I need to update CASH_MANAGEMENT icons to use these.
        // Since I can't easily add enum values without re-reading and full replace, I will map closest EXISTING enums 
        // OR I will assume `IconType` is flexible enough or update it.
        // Actually, I just updated `IconType` in previous steps to include `ICON_CASH`, `ICON_FINANCIAL` etc.
        // I can repurpose or add new ones to IMAGE_MAP if the key exists.
        // I'll add mappings for `ICON_CASH` -> cash.png? No user gave `caisse_img`.
        // User gave `depense_img`, `benefice_img`, `montant_moyen`.
        
        // I will map generic types to these new images for Cash View usage
        // But I need keys. I'll just map them to `ICON_FINANCIAL` etc or use strings in the view if I have to.
        // Better: I'll map `ICON_FINANCIAL` to `expense.png` (maybe?) no financial is usually situation.
        // I'll stick to updating existing mappings where I can.
    }

    public static Icon getIcon(IconType type, int size, Color color) {
        return getIcon(type, size, size, color);
    }

    public static Icon getIcon(IconType type, int width, int height, Color color) {
        String key = type + "_" + width + "x" + height + "_" + (color != null ? color.getRGB() : "null");
        if (cache.containsKey(key)) return cache.get(key);

        // 1. Try Loading Image
        if (IMAGE_MAP.containsKey(type)) {
            ImageIcon imgIcon = loadImage(IMAGE_MAP.get(type), width, height);
            if (imgIcon != null) {
                cache.put(key, imgIcon);
                return imgIcon;
            }
        }

        // 2. Fallback to Vector Drawing (Square assumption usually)
        int size = Math.min(width, height); // Fallback size
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color != null ? color : Color.BLACK);
        g2.setStroke(new BasicStroke(Math.max(1.5f, size / 12f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        float s = size;
        float pad = s * 0.15f; 
        float w = s - 2 * pad;
        float h = s - 2 * pad;
        float x = (width - s)/2 + pad; // center if vector
        float y = (height - s)/2 + pad;
        
        // ... (Drawing logic handled by cases, mostly replaced now but kept structure)
        if (!IMAGE_MAP.containsKey(type)) {
             g2.setColor(Color.RED);
             g2.draw(new Rectangle2D.Float(x, y, w, h));
        }

        g2.dispose();
        ImageIcon icon = new ImageIcon(img);
        cache.put(key, icon);
        return icon;
    }

    private static ImageIcon loadImage(String path, int w, int h) {
        try {
            URL url = IconUtils.class.getResource(path);
            if (url == null) {
                java.io.File f = new java.io.File("src/main/resources" + path);
                if (f.exists()) {
                    url = f.toURI().toURL();
                } else {
                    System.err.println("Icon not found: " + path);
                    return null;
                }
            }
            ImageIcon original = new ImageIcon(url);
            // Use -1 for one dimension to preserve ratio if needed, or exact if both provided
            Image scaled = original.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

