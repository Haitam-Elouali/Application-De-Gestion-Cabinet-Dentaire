package ma.TeethCare.mvc.ui.frames;

import ma.TeethCare.mvc.controllers.authentificationModule.api.LoginController;
import ma.TeethCare.mvc.ui.palette.buttons.MyButton;
import ma.TeethCare.mvc.ui.palette.fields.CustomPasswordField;
import ma.TeethCare.mvc.ui.palette.fields.CustomTextField;
import ma.TeethCare.mvc.ui.palette.utils.ImageTools;
import ma.TeethCare.mvc.ui.palette.utils.UIConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Map;

public class LoginUI extends JFrame {

        private final LoginController controller;

        private JTextField txt_lg;
        private JPasswordField txt_pass;

        private JLabel lbl_err_global;

        // Bouton Login (référence nécessaire pour KeyBinding)
        private JButton btLogin, btCancel;

        public LoginUI(LoginController controller) {
                this.controller = controller;

                setSize(450, 600);
                setLocationRelativeTo(null);
                setResizable(false);
                setUndecorated(true);

                setContentPane(buildRoot());
                installKeyBindings();
                setVisible(false);
        }

        // Actions ———————————————————————————————————————————————————————————————————
        private void loginAction(ActionEvent e) {
                String login = txt_lg.getText();
                String pass = new String(txt_pass.getPassword());
                controller.onLoginRequested(login, pass);
        }

        private void cancelAction(ActionEvent e) {
                controller.onCancelRequested();
        }
        // ——————————————————————————————————————————————————————————————————————————

        // API Controller -> View
        public void showFieldErrors(Map<String, String> errors) {
                if (errors == null || errors.isEmpty())
                        return;
                lbl_err_global.setText(errors.getOrDefault("_global", "Informations incorrectes"));
        }

        public void clearErrors() {
                lbl_err_global.setText(" ");
        }

        private JPanel buildRoot() {
                JPanel root = new JPanel(new GridBagLayout());
                root.setBackground(UIConstants.SURFACE_MAIN);
                root.setBorder(new LineBorder(UIConstants.BORDER_GRAY, 1));

                JPanel card = new JPanel();
                card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                                new LineBorder(UIConstants.BORDER_GRAY, 1),
                                new EmptyBorder(40, 40, 40, 40)));
                card.setPreferredSize(new Dimension(380, 520));

                // Logo & Title
                JLabel lblLogo = new JLabel(ImageTools.loadIcon("/static/icons/logo.png", 80, 80));
                lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel lblWelcome = new JLabel("Bienvenue");
                lblWelcome.setFont(UIConstants.FONT_TITLE.deriveFont(24f));
                lblWelcome.setForeground(UIConstants.TEXT_DARK);
                lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel lblSub = new JLabel("Connectez-vous à votre espace");
                lblSub.setFont(UIConstants.FONT_REGULAR.deriveFont(14f));
                lblSub.setForeground(UIConstants.TEXT_GRAY);
                lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Fields
                txt_lg = new CustomTextField("Nom d'utilisateur");
                txt_lg.setPreferredSize(new Dimension(300, 45));
                txt_lg.setMaximumSize(new Dimension(300, 45));
                txt_lg.setText("admin.omar");

                txt_pass = new CustomPasswordField("Mot de passe");
                txt_pass.setPreferredSize(new Dimension(300, 45));
                txt_pass.setMaximumSize(new Dimension(300, 45));
                txt_pass.setText("Admin@2025");

                // Error
                lbl_err_global = new JLabel(" ");
                lbl_err_global.setFont(UIConstants.FONT_BOLD.deriveFont(13f));
                lbl_err_global.setForeground(new Color(220, 38, 38));
                lbl_err_global.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Buttons
                btLogin = new MyButton("Se connecter", null, UIConstants.FONT_BOLD.deriveFont(16f));
                btLogin.setPreferredSize(new Dimension(300, 45));
                btLogin.setMaximumSize(new Dimension(300, 45));
                btLogin.addActionListener(this::loginAction);

                btCancel = new JButton("Annuler");
                btCancel.setFont(UIConstants.FONT_REGULAR.deriveFont(14f));
                btCancel.setForeground(UIConstants.TEXT_GRAY);
                btCancel.setBorderPainted(false);
                btCancel.setContentAreaFilled(false);
                btCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
                btCancel.addActionListener(this::cancelAction);

                // Adding components
                card.add(lblLogo);
                card.add(Box.createVerticalStrut(20));
                card.add(lblWelcome);
                card.add(lblSub);
                card.add(Box.createVerticalStrut(40));
                card.add(txt_lg);
                card.add(Box.createVerticalStrut(15));
                card.add(txt_pass);
                card.add(Box.createVerticalStrut(10));
                card.add(lbl_err_global);
                card.add(Box.createVerticalStrut(30));
                card.add(btLogin);
                card.add(Box.createVerticalStrut(15));
                card.add(btCancel);

                root.add(card);
                return root;
        }

        // —————————————— KEY BINDINGS (BONNE PRATIQUE SWING)

        private void installKeyBindings() {

                // InputMap du RootPane → actif partout dans la fenêtre
                InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                ActionMap actionMap = getRootPane().getActionMap();

                // Association de la touche ENTER à l'action "LOGIN"
                inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "LOGIN");
                // Association de la touche Escape à l'action "CANCEL"
                inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "CANCEL");

                // Action exécutée quand ENTER est pressée
                actionMap.put("LOGIN", new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                loginAction(e);
                        }
                });
                // Action exécutée quand ENTER est pressée
                actionMap.put("CANCEL", new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                cancelAction(e);
                        }
                });
        }
}
