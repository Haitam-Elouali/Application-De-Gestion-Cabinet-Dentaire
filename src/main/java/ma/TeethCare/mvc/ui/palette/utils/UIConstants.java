package ma.TeethCare.mvc.ui.palette.utils;

import java.awt.*;

public final class UIConstants {
    private UIConstants() {
    }

    // Colors - Tailwind-inspired
    public static final Color SURFACE_MAIN = new Color(248, 250, 252); // slate-50
    public static final Color SURFACE_GREEN = new Color(240, 253, 244); // green-50
    public static final Color SURFACE_GREEN_100 = new Color(220, 252, 231); // green-100
    public static final Color ACCENT_GREEN = new Color(21, 128, 61); // green-700
    public static final Color ACCENT_GREEN_600 = new Color(22, 163, 74); // green-600
    public static final Color ACCENT_GREEN_800 = new Color(22, 101, 52); // green-800
    public static final Color BORDER_GRAY = new Color(209, 213, 219); // gray-300
    public static final Color BORDER_GREEN = new Color(187, 247, 208); // green-300
    public static final Color TEXT_DARK = new Color(17, 24, 39); // gray-900
    public static final Color TEXT_GRAY = new Color(107, 114, 128); // gray-500

    // Fonts
    public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_LOGO = new Font("Optima", Font.BOLD, 22);

    // Common Design Elements
    public static final int ROUNDING = 8;
}
