package ma.TeethCare.mvc.ui.pages.profilePages;

import java.util.function.Consumer;
import ma.TeethCare.conf.ApplicationContext;
import ma.TeethCare.common.enums.Sexe;
// import ma.TeethCare.mvc.dto.profileDtos.*; // COMMENTED: ProfileData and related DTOs do not exist
// import ma.TeethCare.service.profileService.api.ProfileService; // COMMENTED: ProfileService does not exist
import ma.TeethCare.mvc.ui.palette.alert.Alert;
import ma.TeethCare.mvc.ui.palette.combos.CustomEnumComboBox;
import ma.TeethCare.mvc.ui.palette.fields.CustomPasswordField;
import ma.TeethCare.mvc.ui.palette.fields.CustomTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;

public class ProfilePanel extends JPanel {

    // private final ProfileService service; // COMMENTED: ProfileService does not
    // exist
    // private ProfileData data; // COMMENTED: ProfileData does not exist
    private Object service; // Placeholder
    private Object data; // Placeholder

    // Avatar UI
    private AvatarView avatarView;
    private JButton btChooseAvatar;

    // Champs à afficher dans le profile UI
    // Champs générique ——————————————————————————————————————————————————————
    private CustomTextField tfPrenom, tfNom, tfEmail, tfTel, tfAdresse, tfCin;
    // vous pouvez le guarder ou l'enlever
    private JTextField tfAvatar;
    private CustomEnumComboBox<Sexe> cbSexe;
    // Champs spécifiques Médecin/Secretaire —————————————————————————————————
    private CustomTextField tfSpecialite, tfNumCNSS, tfCommission;

    // labels erreurs ————————————————————————————————————————————————————————
    private JLabel errPrenom, errNom, errEmail, errGlobal;

    // Nom & Email à gauche ————————————————————————————————————————————————
    // Avatar encerclé avec un bouton à afficher en survolant l'avatar à droite
    private JLabel lblName;
    private JLabel lblEmail;
    private JPanel avatarWrapper;
    private JButton btEditAvatar;

    // un callback pour mettre à jour le headerPanel quand on modifie le profile
    // private final Consumer<ProfileData> onProfileSaved; // COMMENTED: ProfileData
    // does not exist
    private final Consumer<Object> onProfileSaved; // Changed to Object

    public ProfilePanel(Object controllerIgnored, Object service, Object data, Consumer<Object> onProfileSaved) { // Changed
                                                                                                                  // ProfileService
                                                                                                                  // and
                                                                                                                  // ProfileData
                                                                                                                  // to
                                                                                                                  // Object
        this.service = service;
        this.data = data;
        this.onProfileSaved = onProfileSaved;

        setLayout(new BorderLayout(16, 16));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(22, 22, 22, 22));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildBody(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);

        fillFromData();
    }

    /*
     * =========================================================
     * HEADER (Titre + Avatar preview)
     * =========================================================
     */

    /*
     * private JComponent buildHeader() {
     * JPanel p = new JPanel(new BorderLayout(16, 0));
     * p.setOpaque(false);
     * 
     * lblTitle = new JLabel("—");
     * lblTitle.setFont(new Font("Optima", Font.BOLD, 28));
     * 
     * lblSubtitle = new JLabel("—");
     * lblSubtitle.setFont(new Font("Optima", Font.PLAIN, 14));
     * lblSubtitle.setForeground(new Color(90, 90, 90));
     * 
     * JPanel left = new JPanel(new GridLayout(0, 1, 0, 6));
     * left.setOpaque(false);
     * left.add(lblTitle);
     * left.add(lblSubtitle);
     * 
     * // Avatar à droite
     * JPanel right = new JPanel();
     * right.setOpaque(false);
     * right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
     * 
     * avatarView = new AvatarView(92);
     * avatarView.setAlignmentX(Component.CENTER_ALIGNMENT);
     * 
     * btChooseAvatar = new JButton("Choisir avatar…");
     * btChooseAvatar.setFont(new Font("Optima", Font.BOLD, 14));
     * btChooseAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
     * btChooseAvatar.addActionListener(e -> onChooseAvatar());
     * 
     * right.add(avatarView);
     * right.add(Box.createVerticalStrut(8));
     * right.add(btChooseAvatar);
     * 
     * p.add(left, BorderLayout.WEST);
     * p.add(right, BorderLayout.EAST);
     * 
     * return p;
     * }
     * 
     * 
     */
    private JComponent buildHeader() {
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setOpaque(false);

        // LEFT: Name + Email
        lblName = new JLabel("—");
        lblName.setFont(new Font("Optima", Font.BOLD, 28));

        lblEmail = new JLabel("—");
        lblEmail.setFont(new Font("Optima", Font.PLAIN, 14));
        lblEmail.setForeground(new Color(90, 90, 90));

        JPanel left = new JPanel(new GridLayout(0, 1, 0, 6));
        left.setOpaque(false);

        // RIGHT: Avatar + edit icon on hover
        avatarWrapper = new JPanel();
        avatarWrapper.setOpaque(false);
        avatarWrapper.setLayout(new OverlayLayout(avatarWrapper));
        avatarWrapper.setPreferredSize(new Dimension(92, 92));

        avatarView = new AvatarView(92);
        avatarView.setAlignmentX(0.5f);
        avatarView.setAlignmentY(0.5f);

        btEditAvatar = createAvatarEditButton(); // 👇 méthode plus bas
        btEditAvatar.setAlignmentX(1f);
        btEditAvatar.setAlignmentY(1f);
        btEditAvatar.setVisible(false); // visible seulement au survol

        avatarWrapper.add(btEditAvatar);
        avatarWrapper.add(avatarView);

        // left.add(btEditAvatar);
        // left.add(avatarView);
        left.add(lblName);
        left.add(lblEmail);

        // hover logic (sur l’avatar + wrapper)
        installAvatarHover(avatarWrapper, btEditAvatar);

        header.add(left, BorderLayout.WEST);
        header.add(avatarWrapper, BorderLayout.EAST);
        return header;
    }

    /*
     * =========================================================
     * BODY (Form + champ avatar path readonly)
     * =========================================================
     */

    private JComponent buildBody() {
        JPanel wrapper = new JPanel(new BorderLayout(16, 0));
        wrapper.setOpaque(false);

        wrapper.add(buildForm(), BorderLayout.CENTER);
        return wrapper;
    }

    private JComponent buildForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        tfPrenom = ctf("Prénom");
        tfNom = ctf("Nom");
        tfEmail = ctf("Email");
        tfTel = ctf("Téléphone");
        tfAdresse = ctf("Adresse");
        tfCin = ctf("CIN");

        // Avatar path readonly (tu peux aussi l’enlever du form comme tu as fait)
        tfAvatar = new JTextField();
        tfAvatar.setFont(new Font("Optima", Font.PLAIN, 16));
        tfAvatar.setPreferredSize(new Dimension(320, 38));
        tfAvatar.setEditable(false);
        tfAvatar.setBackground(new Color(245, 245, 245));

        // cbSexe = new JComboBox<>(new String[]{"Homme", "Femme"});

        cbSexe = new CustomEnumComboBox<>(Sexe.class, "— Sexe —");
        cbSexe.setPreferredFieldSize(320, 38);
        cbSexe.setFont(new Font("Optima", Font.PLAIN, 16));

        errPrenom = err();
        errNom = err();
        errEmail = err();
        errGlobal = err();
        errGlobal.setHorizontalAlignment(SwingConstants.CENTER);

        int row = 0;
        row = addRow(form, gc, row, "Prénom", tfPrenom, errPrenom);
        row = addRow(form, gc, row, "Nom", tfNom, errNom);
        row = addRow(form, gc, row, "Email", tfEmail, errEmail);
        row = addRow(form, gc, row, "Téléphone", tfTel, null);
        row = addRow(form, gc, row, "Adresse", tfAdresse, null);
        row = addRow(form, gc, row, "CIN", tfCin, null);
        row = addRow(form, gc, row, "Sexe", cbSexe, null);

        // Si tu veux garder affichage du chemin
        // row = addRow(form, gc, row, "Avatar", tfAvatar, null);

        // COMMENTED: data.rolePrincipal() does not exist
        // if (data != null && data.rolePrincipal() != null) {
        // switch (data.rolePrincipal()) {
        // case MEDECIN -> {
        // tfSpecialite = ctf("Spécialité");
        // row = addRow(form, gc, row, "Spécialité", tfSpecialite, null);
        // }
        // case SECRETAIRE -> {
        // tfNumCNSS = ctf("Num CNSS");
        // tfCommission = ctf("Commission");
        // row = addRow(form, gc, row, "Num CNSS", tfNumCNSS, null);
        // row = addRow(form, gc, row, "Commission", tfCommission, null);
        // }
        // default -> {}
        // }
        // }

        gc.gridy = row;
        gc.gridx = 0;
        gc.gridwidth = 2;
        form.add(errGlobal, gc);

        return form;
    }

    /*
     * =========================================================
     * FOOTER (Save + Change password)
     * =========================================================
     */

    private JComponent buildFooter() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        p.setOpaque(false);

        JButton btSave = new JButton("Enregistrer");
        btSave.setFont(new Font("Optima", Font.BOLD, 16));
        btSave.addActionListener(e -> onSave());

        JButton btPwd = new JButton("Changer mot de passe");
        btPwd.setFont(new Font("Optima", Font.BOLD, 16));
        btPwd.addActionListener(e -> openChangePasswordDialog());

        p.add(btSave);
        p.add(btPwd);
        return p;
    }

    /*
     * =========================================================
     * ACTIONS
     * =========================================================
     */

    private void onSave() {
        // COMMENTED: ProfileUpdateRequest, ProfileUpdateResult, and data methods do not
        // exist
        Alert.error(this, "Profile save functionality is not available (missing DTOs).");
        // clearErrors();
        // if (data == null || data.id() == null) {
        // Alert.error(this, "Impossible de sauvegarder : profil non chargé.");
        // return;
        // }

        // ProfileUpdateRequest req = ProfileUpdateRequest.builder()
        // .id(data.id())
        // .prenom(readValue(tfPrenom))
        // .nom(readValue(tfNom))
        // .email(readValue(tfEmail))
        // .tel(readValue(tfTel))
        // .adresse(readValue(tfAdresse))
        // .cin(readValue(tfCin))
        // .avatar(tfAvatar.getText()) // stocké (relatif recommandé)
        // // .sexe("Femme".equals(cbSexe.getSelectedItem()) ? Sexe.Femme : Sexe.Homme)
        // //
        // // pour un ComboBox classique
        // .sexe(cbSexe.getSelectedEnum())
        // .dateNaissance(data.dateNaissance()) // plus tard DatePicker

        // // Staff (tu peux exposer ces champs UI plus tard)
        // .salaire(data.salaire())
        // .prime(data.prime())
        // .dateRecrutement(data.dateRecrutement())
        // .soldeConge(data.soldeConge())

        // // Medecin
        // .specialite(tfSpecialite != null ? tfSpecialite.getText() : null)

        // // Secretaire
        // .numCNSS(tfNumCNSS != null ? tfNumCNSS.getText() : null)
        // .commission(parseDouble(tfCommission != null ? tfCommission.getText() :
        // null))
        // .build();

        // ProfileUpdateResult res = service.update(req);

        // if (!res.ok()) {
        // showFieldErrors(res.fieldErrors(), res.message());
        // return;
        // }

        // this.data = res.data();
        // fillFromData();
        // // NOTIFIER LE DASHBOARD EN CAS DE CHANGEMENT POUR NOTIFIER LE HEADERPANEL
        // if (onProfileSaved != null)
        // onProfileSaved.accept(this.data);
        // Alert.success(this, "Profil enregistré.");
    }

    // helper pour onSave
    private String readValue(ma.TeethCare.mvc.ui.palette.fields.CustomTextField tf) {
        if (tf == null)
            return null;
        String s = tf.getText();
        if (s == null)
            return "";
        // COMMENTED: getHint() does not exist
        // if (s.equals(tf.getHint()))
        // return ""; // ignore hint
        return s.trim();
    }

    private void onChooseAvatar() {
        // COMMENTED: data.id() does not exist
        Alert.error(this, "Avatar functionality is not available (missing DTOs).");
        // if (data == null || data.id() == null) {
        // Alert.error(this, "Profil non chargé.");
        // return;
        // }

        // JFileChooser fc = new JFileChooser();
        // fc.setDialogTitle("Choisir une image d’avatar");
        // fc.setFileFilter(new FileNameExtensionFilter("Images (png, jpg, jpeg, webp)",
        // "png", "jpg", "jpeg", "webp"));
        // fc.setAcceptAllFileFilterUsed(false);

        // int r = fc.showOpenDialog(this);
        // if (r != JFileChooser.APPROVE_OPTION)
        // return;

        // File selected = fc.getSelectedFile();
        // if (selected == null || !selected.exists()) {
        // Alert.error(this, "Fichier invalide.");
        // return;
        // }

        // try {
        // // 1) vérifier image lisible
        // BufferedImage img = ImageIO.read(selected);
        // if (img == null) {
        // Alert.error(this, "Fichier non reconnu comme image.");
        // return;
        // }

        // // 2) dossier PROD depuis properties
        // Path dir = resolveAvatarDir();
        // Files.createDirectories(dir);

        // // 3) nom unique (userId + uuid) en gardant extension
        // String ext = fileExt(selected.getName());
        // if (ext == null)
        // ext = "png";

        // String fileName = "u" + data.id() + "_" +
        // UUID.randomUUID().toString().replace("-", "") + "." + ext;
        // Path dest = dir.resolve(fileName);

        // // 4) copier
        // Files.copy(selected.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);

        // // 5) stocker un chemin relatif "avatars/<file>"
        // // -> tu peux aussi stocker dest.toString() si tu veux absolu
        // String storedPath = "avatars/" + fileName;

        // tfAvatar.setText(storedPath);

        // // 6) preview
        // // avatarView.setImage(loadImageFromStoredPath(storedPath));
        // avatarView.setImage(img);
        // avatarView.revalidate();
        // avatarView.repaint();

        // } catch (Exception ex) {
        // Alert.error(this, "Erreur upload avatar: " + ex.getMessage());
        // }
    }

    private void openChangePasswordDialog() {
        // COMMENTED: ChangePasswordRequest, ChangePasswordResult, and data.id() do not
        // exist
        Alert.error(this, "Change password functionality is not available (missing DTOs).");
        // if (data == null || data.id() == null) {
        // Alert.error(this, "Profil non chargé.");
        // return;
        // }

        // JDialog d = new JDialog(SwingUtilities.getWindowAncestor(this), "Changer mot
        // de passe",
        // Dialog.ModalityType.APPLICATION_MODAL);
        // d.setUndecorated(true);
        // d.setLayout(new BorderLayout());
        // d.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(220, 220,
        // 220)));

        // JPanel content = new JPanel(new GridBagLayout());
        // content.setBackground(Color.WHITE);
        // content.setBorder(new EmptyBorder(16, 16, 16, 16));

        // GridBagConstraints gc = new GridBagConstraints();
        // gc.insets = new Insets(8, 8, 8, 8);
        // gc.fill = GridBagConstraints.HORIZONTAL;

        // CustomPasswordField pfCur = new CustomPasswordField("Mot de passe actuel");
        // CustomPasswordField pfNew = new CustomPasswordField("Nouveau mot de passe");
        // CustomPasswordField pfCnf = new CustomPasswordField("Confirmer");

        // configurePwdField(pfCur);
        // configurePwdField(pfNew);
        // configurePwdField(pfCnf);

        // JLabel eCur = err();
        // JLabel eNew = err();
        // JLabel eCnf = err();
        // JLabel eG = err();
        // eG.setHorizontalAlignment(SwingConstants.CENTER);

        // int row = 0;
        // row = addRow(content, gc, row, "Actuel", pfCur, eCur);
        // row = addRow(content, gc, row, "Nouveau", pfNew, eNew);
        // row = addRow(content, gc, row, "Confirmer", pfCnf, eCnf);

        // gc.gridy = row;
        // gc.gridx = 0;
        // gc.gridwidth = 2;
        // content.add(eG, gc);

        // JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // footer.setBackground(Color.WHITE);

        // JButton btOk = new JButton("Valider");
        // btOk.setFont(new Font("Optima", Font.BOLD, 15));

        // JButton btCancel = new JButton("Annuler");
        // btCancel.setFont(new Font("Optima", Font.BOLD, 15));
        // btCancel.addActionListener(ev -> d.dispose());

        // btOk.addActionListener(ev -> {
        // eCur.setText(" ");
        // eNew.setText(" ");
        // eCnf.setText(" ");
        // eG.setText(" ");

        // String cur = new String(pfCur.getPassword());
        // String nw = new String(pfNew.getPassword());
        // String cf = new String(pfCnf.getPassword());

        // ChangePasswordRequest req = ChangePasswordRequest.builder()
        // .userId(data.id())
        // .currentPassword(cur)
        // .newPassword(nw)
        // .confirmPassword(cf)
        // .build();

        // ChangePasswordResult res = service.changePassword(req);
        // if (!res.ok()) {
        // Map<String, String> m = res.fieldErrors();
        // if (m != null) {
        // eCur.setText(m.getOrDefault("currentPassword", " "));
        // eNew.setText(m.getOrDefault("newPassword", " "));
        // eCnf.setText(m.getOrDefault("confirmPassword", " "));
        // eG.setText(m.getOrDefault("_global", res.message() != null ? res.message() :
        // " "));
        // } else {
        // eG.setText(res.message() != null ? res.message() : "Erreur.");
        // }
        // return;
        // }

        // Alert.success(this, "Mot de passe modifié.");
        // d.dispose();
        // });

        // footer.add(btOk);
        // footer.add(btCancel);

        // // Key bindings : ENTER => OK, ESC => close
        // InputMap im = d.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        // ActionMap am = d.getRootPane().getActionMap();
        // im.put(KeyStroke.getKeyStroke("ENTER"), "OK");
        // am.put("OK", new AbstractAction() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // btOk.doClick();
        // }
        // });
        // im.put(KeyStroke.getKeyStroke("ESCAPE"), "CLOSE");
        // am.put("CLOSE", new AbstractAction() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // d.dispose();
        // }
        // });
        // d.getRootPane().setDefaultButton(btOk);

        // d.add(content, BorderLayout.CENTER);
        // d.add(footer, BorderLayout.SOUTH);
        // d.pack();
        // d.setLocationRelativeTo(this);
        // SwingUtilities.invokeLater(pfCur::requestFocusInWindow);
        // d.setVisible(true);
    }

    /*
     * =========================================================
     * DATA -> UI
     * =========================================================
     */

    private void fillFromData() {
        // COMMENTED: data methods do not exist
        // if (data == null)
        // return;

        // setValue(tfPrenom, data.prenom());
        // setValue(tfNom, data.nom());
        // setValue(tfEmail, data.email());
        // setValue(tfTel, data.tel());
        // setValue(tfAdresse, data.adresse());
        // setValue(tfCin, data.cin());

        // tfAvatar.setText(safe(data.avatar()));

        // // cbSexe.setSelectedItem(data.sexe() != null ? data.sexe().name() :
        // "Homme");
        // // pour un Combo classique
        // cbSexe.setSelectedEnum(data.sexe());

        // if (tfSpecialite != null)
        // setValue(tfSpecialite, data.specialite());
        // if (tfNumCNSS != null)
        // setValue(tfNumCNSS, data.numCNSS());
        // if (tfCommission != null)
        // setValue(tfCommission,
        // data.commission() != null ? String.valueOf(data.commission()) : "");

        // // header left
        // String fullName = (safe(data.prenom()) + " " + safe(data.nom())).trim();
        // lblName.setText(fullName.isBlank() ? "Mon Profil" : fullName);
        // lblEmail.setText(safe(data.email()));

        // // avatar
        // BufferedImage img = loadImageFromStoredPath(data.avatar());
        // avatarView.setImage(img);
    }

    private void setValue(CustomTextField tf, String value) {
        if (tf == null)
            return;

        String v = value == null ? "" : value.trim();

        if (v.isBlank()) {
            // mode hint / lost - COMMENTED: getHint(), getLostFont(), getLostColor() do not
            // exist
            // tf.setText(tf.getHint());
            // tf.setFont(tf.getLostFont());
            // tf.setForeground(tf.getLostColor());
            tf.setText("");
        } else {
            // mode valeur / gain - COMMENTED: getGainFont(), getGainColor() do not exist
            tf.setText(v);
            // tf.setFont(tf.getGainFont());
            // tf.setForeground(tf.getGainColor());
        }

        tf.repaint();
    }

    /*
     * =========================================================
     * ERRORS
     * =========================================================
     */

    private void showFieldErrors(Map<String, String> errors, String globalMsg) {
        if (errors == null)
            return;

        errPrenom.setText(errors.getOrDefault("prenom", " "));
        errNom.setText(errors.getOrDefault("nom", " "));
        errEmail.setText(errors.getOrDefault("email", " "));
        errGlobal.setText(errors.getOrDefault("_global", globalMsg != null ? globalMsg : " "));
    }

    private void clearErrors() {
        errPrenom.setText(" ");
        errNom.setText(" ");
        errEmail.setText(" ");
        errGlobal.setText(" ");
    }

    /*
     * =========================================================
     * HELPERS (UI)
     * =========================================================
     */

    private CustomTextField ctf(String hint) {
        CustomTextField t = new CustomTextField(hint);
        t.setPreferredSize(new Dimension(320, 42));
        // Optionnel: harmoniser
        // t.setArcWeight(20);
        return t;
    }

    private JLabel err() {
        JLabel l = new JLabel(" ");
        l.setFont(new Font("Optima", Font.BOLD, 13));
        l.setForeground(new Color(222, 112, 112));
        return l;
    }

    private int addRow(JPanel form, GridBagConstraints gc, int row, String label, JComponent field, JLabel err) {
        gc.gridy = row;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.weightx = 0;

        JLabel l = new JLabel(label);
        l.setFont(new Font("Optima", Font.BOLD, 14));
        form.add(l, gc);

        gc.gridx = 1;
        gc.weightx = 1;
        form.add(field, gc);

        if (err != null) {
            gc.gridy = row + 1;
            gc.gridx = 1;
            gc.weightx = 1;
            form.add(err, gc);
            return row + 2;
        }
        return row + 1;
    }

    private void configurePwdField(CustomPasswordField pf) {
        pf.setPreferredSize(new Dimension(320, 42));
        pf.setFont(new Font("Optima", Font.PLAIN, 16));
        // tu peux ajuster les couleurs si besoin:
        // pf.setBorderColor(...); pf.setBackgroundColor(...);
    }

    /*
     * =========================================================
     * HELPERS (Avatar paths)
     * =========================================================
     */

    private JButton createAvatarEditButton() {
        // edit.png : mets-le dans resources, ex: src/main/resources/icons/edit.png
        ImageIcon icon = null;
        try {
            var url = getClass().getClassLoader().getResource("static/icons/profilePage/edit.png");
            if (url != null) {
                Image img = new ImageIcon(url).getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
            }
        } catch (Exception ignored) {
        }

        JButton b = new JButton(icon != null ? icon : new ImageIcon());
        b.setToolTipText("Modifier l’avatar");
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addActionListener(e -> onChooseAvatar());
        // petite marge pour le coin bas-droit
        b.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 2));
        return b;
    }

    private void installAvatarHover(JComponent target, JComponent button) {
        var ml = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setVisible(true);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(p, target);
                if (!target.contains(p))
                    button.setVisible(false);
            }
        };
        target.addMouseListener(ml);
        button.addMouseListener(ml);
    }

    /**
     * On lit profile.avatars.dir (ex: /Users/.../avatars)
     * On stocke dans DB un chemin relatif "avatars/<file>".
     */
    private Path resolveAvatarDir() {
        // COMMENTED: ApplicationContext.getInstance() does not exist
        // String dir =
        // ApplicationContext.getInstance().getProperty("profile.avatars.dir",
        // "/Users/mac/Desktop/DesktopAppData/avatars");
        String dir = "/Users/mac/Desktop/DesktopAppData/avatars"; // Fallback
        // dir pointe vers ".../avatars" => OK
        // Si tu mets un autre dossier (ex: ".../static"), à toi de décider.
        return Paths.get(dir);
    }

    /**
     * Convertit un storedPath (ex: "avatars/u1_x.png") en image réelle:
     * - si storedPath est relatif => on prend dirParent + filename
     * - si storedPath est absolu => on lit direct
     */
    private BufferedImage loadImageFromStoredPath(String storedPath) {
        if (storedPath == null || storedPath.isBlank())
            return null;

        try {
            // absolu ?
            Path p = Paths.get(storedPath);
            if (p.isAbsolute() && Files.exists(p)) {
                return ImageIO.read(p.toFile());
            }

            // relatif "avatars/<file>" -> on ne garde que le fileName et on lit depuis
            // profile.avatars.dir
            String fn = storedPath.replace("\\", "/");
            if (fn.contains("/"))
                fn = fn.substring(fn.lastIndexOf('/') + 1);

            Path file = resolveAvatarDir().resolve(fn);
            if (!Files.exists(file))
                return null;

            return ImageIO.read(file.toFile());
        } catch (Exception e) {
            return null;
        }
    }

    private String fileExt(String name) {
        if (name == null)
            return null;
        int i = name.lastIndexOf('.');
        if (i < 0 || i == name.length() - 1)
            return null;
        return name.substring(i + 1).toLowerCase();
    }

    /*
     * =========================================================
     * HELPERS (String/Number)
     * =========================================================
     */

    private String safe(String s) {
        return s == null ? "" : s;
    }

    private Double parseDouble(String s) {
        if (s == null || s.isBlank())
            return null;
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * =========================================================
     * INNER CLASS : avatar component (cercle)
     * =========================================================
     */

    private static class AvatarView extends JComponent {
        private final int size;
        private BufferedImage image;

        AvatarView(int size) {
            this.size = size;
            setPreferredSize(new Dimension(size, size));
            setMinimumSize(new Dimension(size, size));
            setMaximumSize(new Dimension(size, size));
        }

        void setImage(BufferedImage img) {
            this.image = img;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // fond cercle gris clair
            g2.setColor(new Color(245, 245, 245));
            g2.fillOval(0, 0, size, size);

            // image dans cercle
            if (image != null) {
                BufferedImage scaled = scaleToFill(image, size, size);
                Shape clip = new Ellipse2D.Double(0, 0, size, size);
                g2.setClip(clip);
                g2.drawImage(scaled, 0, 0, null);
                g2.setClip(null);
            } else {
                // placeholder "👤"
                g2.setColor(new Color(130, 130, 130));
                g2.setFont(new Font("Optima", Font.BOLD, 34));
                FontMetrics fm = g2.getFontMetrics();
                String s = "👤";
                int x = (size - fm.stringWidth(s)) / 2;
                int y = (size - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(s, x, y);
            }

            // bordure
            g2.setColor(new Color(220, 220, 220));
            g2.drawOval(0, 0, size - 1, size - 1);

            g2.dispose();
        }

        private static BufferedImage scaleToFill(BufferedImage src, int w, int h) {
            // crop-center puis scale (cover)
            int sw = src.getWidth();
            int sh = src.getHeight();
            if (sw <= 0 || sh <= 0)
                return src;

            double rSrc = (double) sw / sh;
            double rDst = (double) w / h;

            int cw, ch, cx, cy;
            if (rSrc > rDst) {
                // trop large
                ch = sh;
                cw = (int) (sh * rDst);
                cx = (sw - cw) / 2;
                cy = 0;
            } else {
                // trop haut
                cw = sw;
                ch = (int) (sw / rDst);
                cx = 0;
                cy = (sh - ch) / 2;
            }

            BufferedImage cropped = src.getSubimage(Math.max(0, cx), Math.max(0, cy),
                    Math.min(cw, sw), Math.min(ch, sh));

            Image scaled = cropped.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = out.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(scaled, 0, 0, null);
            g2.dispose();
            return out;
        }
    }
}
