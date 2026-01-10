package ma.TeethCare.mvc.ui.palette.utils;

import java.awt.Color;

public class TailwindPalette {

    // Colors extracted from Maquette usage (Tailwind standard palette)
    // The Maquette uses blue-600 as primary action color.

    public static final Color BACKGROUND = Color.decode("#ffffff");
    
    // Blue Theme
    public static final Color BLUE_50 = new Color(239, 246, 255);     // #eff6ff
    public static final Color BLUE_100 = new Color(219, 234, 254);    // #dbeafe
    public static final Color BLUE_200 = new Color(191, 219, 254);    // #bfdbfe
    public static final Color BLUE_300 = new Color(147, 197, 253);    // #93c5fd
    public static final Color BLUE_500 = new Color(59, 130, 246);     // #3b82f6
    public static final Color BLUE_600 = new Color(37, 99, 235);      // #2563eb
    public static final Color BLUE_700 = new Color(29, 78, 216);      // #1d4ed8
    public static final Color BLUE_800 = new Color(30, 64, 175);      // #1e40af
    public static final Color BLUE_900 = new Color(30, 58, 138);      // #1e3a8a

    // Green Theme
    public static final Color GREEN_50 = new Color(240, 253, 244);    // #f0fdf4
    public static final Color GREEN_100 = new Color(220, 252, 231);   // #dcfce7
    public static final Color GREEN_200 = new Color(187, 247, 208);   // #bbf7d0
    public static final Color GREEN_300 = new Color(134, 239, 172);   // #86efac
    public static final Color GREEN_400 = new Color(74, 222, 128);    // #4ade80
    public static final Color GREEN_500 = new Color(34, 197, 94);     // #22c55e
    public static final Color GREEN_600 = new Color(22, 163, 74);     // #16a34a
    public static final Color GREEN_700 = new Color(21, 128, 61);     // #15803d
    public static final Color GREEN_800 = new Color(22, 101, 52);     // #166534
    public static final Color GREEN_900 = new Color(20, 83, 45);      // #14532d

    // Red Theme
    public static final Color RED_50 = new Color(254, 242, 242);      // #fef2f2
    public static final Color RED_100 = new Color(254, 226, 226);     // #fee2e2
    public static final Color RED_200 = new Color(254, 202, 202);     // #feca
    public static final Color RED_300 = new Color(252, 165, 165);     // #fca5a5
    public static final Color RED_400 = new Color(248, 113, 113);     // #f87171
    public static final Color RED_500 = new Color(239, 68, 68);       // #ef4444
    public static final Color RED_600 = new Color(220, 38, 38);       // #dc2626
    public static final Color RED_700 = new Color(185, 28, 28);       // #b91c1c
    public static final Color RED_800 = new Color(153, 27, 27);       // #991b1b
    public static final Color RED_900 = new Color(127, 29, 29);       // #7f1d1d

    // Rose Theme (Admin)
    public static final Color ROSE_50 = new Color(255, 241, 242);     // #fff1f2
    public static final Color ROSE_100 = new Color(255, 228, 230);    // #ffe4e6
    public static final Color ROSE_600 = new Color(225, 29, 72);      // #e11d48
    public static final Color ROSE_700 = new Color(190, 18, 60);      // #be123c

    // Orange Theme (for warnings/pending)
    public static final Color ORANGE_100 = new Color(255, 237, 213);  // #ffedd5
    public static final Color ORANGE_400 = new Color(251, 146, 60);   // #fb923c
    public static final Color ORANGE_500 = new Color(249, 115, 22);   // #f97316
    public static final Color ORANGE_600 = new Color(234, 88, 12);    // #ea580c
    public static final Color ORANGE_800 = new Color(154, 52, 18);    // #9a3412
    
    // Purple Theme
    public static final Color PURPLE_100 = new Color(243, 232, 255);  // #f3e8ff
    public static final Color PURPLE_500 = new Color(168, 85, 247);   // #a855f7
    public static final Color PURPLE_600 = new Color(147, 51, 234);   // #9333ea

    // Teal Theme
    public static final Color TEAL_100 = new Color(204, 251, 241);    // #ccfbf1
    public static final Color TEAL_500 = new Color(20, 184, 166);     // #14b8a6
    public static final Color TEAL_600 = new Color(13, 148, 136);     // #0d9488

    // Gray Scale
    public static final Color GRAY_50  = new Color(249, 250, 251);    // #f9fafb
    public static final Color GRAY_100 = new Color(243, 244, 246);    // #f3f4f6
    public static final Color GRAY_200 = new Color(229, 231, 235);    // #e5e7eb
    public static final Color GRAY_300 = new Color(209, 213, 219);    // #d1d5db
    public static final Color GRAY_400 = new Color(156, 163, 175);
    public static final Color GRAY_500 = new Color(107, 114, 128);    // #6b7280
    public static final Color GRAY_600 = new Color(75, 85, 99);       // #4b5563
    public static final Color GRAY_700 = new Color(55, 65, 81);       // #374151
    public static final Color GRAY_800 = new Color(31, 41, 55);       // #1f2937
    public static final Color GRAY_900 = new Color(17, 24, 39);       // #111827
    public static final Color FOREGROUND = Color.decode("#1f2937"); // gray-800

    public static final Color CARD = Color.decode("#ffffff");
    public static final Color CARD_FOREGROUND = Color.decode("#1f2937");

    public static final Color POPOVER = Color.WHITE;
    public static final Color POPOVER_FOREGROUND = Color.decode("#1f2937");

    // Primary = blue-600
    public static final Color PRIMARY = Color.decode("#2563eb");
    public static final Color PRIMARY_FOREGROUND = Color.WHITE; 

    // Secondary = blue-50 or gray-100
    public static final Color SECONDARY = Color.decode("#eff6ff"); // blue-50
    public static final Color SECONDARY_FOREGROUND = Color.decode("#1e3a8a"); // blue-900

    public static final Color MUTED = Color.decode("#f3f4f6"); // gray-100
    public static final Color MUTED_FOREGROUND = Color.decode("#6b7280"); // gray-500

    public static final Color ACCENT = Color.decode("#dbeafe"); // blue-100
    public static final Color ACCENT_FOREGROUND = Color.decode("#1e40af"); // blue-800

    public static final Color DESTRUCTIVE = Color.decode("#dc2626"); // red-600
    public static final Color DESTRUCTIVE_FOREGROUND = Color.WHITE;

    // Border = gray-300 or gray-200. Login uses gray-400 (#9ca3af)
    public static final Color BORDER = Color.decode("#e5e7eb"); // gray-200 for cards usually
    public static final Color BORDER_INPUT = Color.decode("#9ca3af"); // gray-400 for inputs
    
    public static final Color INPUT_BACKGROUND = Color.WHITE; // Login inputs are white

    // Ring = blue-500 (#3b82f6) for focus
    public static final Color RING = Color.decode("#3b82f6"); 

    // Chart colors if needed later
    public static final Color CHART_1 = Color.decode("#e76e50"); 
    public static final Color CHART_2 = Color.decode("#2a9d8f"); 
    
    // Sidebar specific colors
    public static final Color SIDEBAR_BG = Color.decode("#dbeafe"); // blue-100
    public static final Color SIDEBAR_FG = Color.decode("#1e3a8a"); // blue-900
}
