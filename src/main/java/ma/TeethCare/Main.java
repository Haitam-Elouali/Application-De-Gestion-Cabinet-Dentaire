package ma.TeethCare;

import com.formdev.flatlaf.FlatLightLaf;
import ma.TeethCare.mvc.ui.login.LoginView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Setup FlatLaf for modern look
        try {
            FlatLightLaf.setup();
            UIManager.put("Panel.background", Color.decode("#F3F8F5"));
            UIManager.put("Button.arc", 12);
            UIManager.put("Component.arc", 12);
            UIManager.put("ProgressBar.arc", 12);
            UIManager.put("TextComponent.arc", 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch Login View
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}
