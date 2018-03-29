/*
 * Created by JFormDesigner on Mon Mar 26 23:25:44 EEST 2018
 */

package presentation;

import business.AccountException;
import business.AccountService;
import business.ProfileException;
import business.ProfileService;
import dataAccess.entity.Account;
import dataAccess.entity.AdminProfile;
import dataAccess.entity.Profile;
import dataAccess.entity.StudentProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * @author Vlad Ciucescu
 */
class AccountGUI extends JFrame {
    private final SessionData sessionData;

    private final LoginGUI loginGUI;
    private JPanel pnlAccount;
    private JLabel lblUser;
    private JPanel pnlPass;
    private JPasswordField pfOld;
    private JPasswordField pfNew1;
    private JPasswordField pfNew2;
    private JLabel lblPassError;
    private JLabel lblPSuccess;
    private JPanel pnlEmail;
    private JPasswordField pfEmail;
    private JTextField tfEmail;
    private JLabel lblMailError;
    private JLabel lblMSuccess;

    public AccountGUI(SessionData sessionData, LoginGUI loginGUI) {
        this.loginGUI = loginGUI;
        this.sessionData = sessionData;
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.lblUser.setText(getUsername());
    }

    private String getUsername() {
        if (sessionData.getCurrentAccount() == null) return "";
        return sessionData.getCurrentAccount().getUsername();
    }

    private void setPassError(Boolean error, String message) {
        lblPassError.setVisible(error);
        lblPassError.setText(message);
    }

    private void setMailError(Boolean error, String message) {
        lblMailError.setVisible(error);
        lblMailError.setText(message);
    }

    private void btnLogOutActionPerformed(ActionEvent e) {

        sessionData.setCurrentAccount(null);
        loginGUI.setVisible(true);
        dispose();
    }

    private void btnModPassActionPerformed(ActionEvent e) {
        pnlAccount.setVisible(false);
        pnlPass.setVisible(true);
    }

    private void btnCancelActionPerformed(ActionEvent e) {
        pnlPass.setVisible(false);
        pfOld.setText("");
        pfNew1.setText("");
        pfNew2.setText("");
        setPassError(false, "");
        lblPSuccess.setVisible(false);
        pnlAccount.setVisible(true);
    }

    private boolean validatePassword(char[] password) {
        return !sessionData.getCurrentAccount().getPassword().equals(String.valueOf(password));
    }

    private boolean updatePassword(char[] newPass) {
        Account acc;
        try {
            acc = new AccountService().updatePassword(sessionData.getCurrentAccount(), String.valueOf(newPass));
        } catch (AccountException e) {
            setPassError(true, e.getMessage());
            return false;
        }
        sessionData.setCurrentAccount(acc);
        setPassError(false, "");
        return true;
    }


    private boolean updateEmail(String newM) {
        Account acc;
        try {
            acc = new AccountService().updateEmail(sessionData.getCurrentAccount(), newM);
        } catch (AccountException e) {
            setMailError(true, e.getMessage());
            return false;
        }
        sessionData.setCurrentAccount(acc);
        setMailError(false, "");
        return true;
    }

    private void btnConfirmPassActionPerformed(ActionEvent e) {
        lblPSuccess.setVisible(false);
        char[] old = pfOld.getPassword();
        char[] new1 = pfNew1.getPassword();
        char[] new2 = pfNew2.getPassword();
        if (old.length == 0 || new1.length == 0 || new2.length == 0) {
            setPassError(true, "Please fill in all the fields.");
            return;
        }
        if (validatePassword(old)) {
            setPassError(true, "Current password is wrong.");
            return;
        }
        if (!Arrays.equals(new1, new2)) {
            setPassError(true, "Confirmation password does not match.");
            return;
        }
        if (updatePassword(new1)) {
            lblPSuccess.setVisible(true);
            pfOld.setText("");
            pfNew1.setText("");
            pfNew2.setText("");
        }
    }

    private void bntBackActionPerformed(ActionEvent e) {
        pnlEmail.setVisible(false);
        pfEmail.setText("");
        tfEmail.setText("");
        setMailError(false, "");
        lblMSuccess.setVisible(false);
        pnlAccount.setVisible(true);
    }

    private void bntConfirmMailActionPerformed(ActionEvent e) {
        lblMSuccess.setVisible(false);
        char[] pass = pfEmail.getPassword();
        String mail = tfEmail.getText();
        if (pass.length == 0 || mail.length() == 0) {
            setMailError(true, "Please fill in all the fields.");
            return;
        }
        if (validatePassword(pass)) {
            setMailError(true, "Password is wrong.");
            return;
        }
        if (updateEmail(mail)) {
            lblMSuccess.setVisible(true);
            pfEmail.setText("");
            tfEmail.setText("");
        }
    }

    private void btnModEmailActionPerformed(ActionEvent e) {
        pnlAccount.setVisible(false);
        pnlEmail.setVisible(true);
    }

    private void showProfile() {
        setVisible(false);
        ProfileGUI profileGUI = new ProfileGUI(sessionData, this);
        profileGUI.setVisible(true);
        String title = "Administrator ";
        Profile profile;
        try {
            sessionData.setAdmin(true);
            profile = new ProfileService().getAdmin(sessionData.getCurrentAccount().getId());
            sessionData.setCurrentAdminProfile((AdminProfile) profile);
            if (profile == null) {
                title = "Student ";
                sessionData.setAdmin(false);
                profile = new ProfileService().getStudent(sessionData.getCurrentAccount().getId());
                sessionData.setCurrentStudentProfile((StudentProfile) profile);
            }
        } catch (ProfileException e) {
            profileGUI.setTitleL(e.getMessage());
            return;
        }
        profileGUI.setTitleL(title + profile.getName());
        if (sessionData.isAdmin()) {
            profileGUI.displayAdminButtons();
        } else {
            profileGUI.displayStudentButtons();
        }
        profileGUI.displayProfileData(profile.toString());
    }

    private void btnProfileActionPerformed(ActionEvent e) {
        showProfile();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vlad Ciucescu
        pnlAccount = new JPanel();
        JButton btnLogOut = new JButton();
        lblUser = new JLabel();
        JButton btnModPass = new JButton();
        JButton btnModEmail = new JButton();
        JButton btnProfile = new JButton();
        pnlPass = new JPanel();
        pfOld = new JPasswordField();
        pfNew1 = new JPasswordField();
        pfNew2 = new JPasswordField();
        JLabel lblOldPass = new JLabel();
        JLabel lblNewPass1 = new JLabel();
        JLabel lblNewPass2 = new JLabel();
        JButton btnConfirmPass = new JButton();
        JButton btnCancelPass = new JButton();
        lblPassError = new JLabel();
        lblPSuccess = new JLabel();
        pnlEmail = new JPanel();
        JLabel lblEmailPass = new JLabel();
        pfEmail = new JPasswordField();
        JLabel lblEmailPass2 = new JLabel();
        tfEmail = new JTextField();
        JButton bntBack2 = new JButton();
        JButton bntBack3 = new JButton();
        lblMailError = new JLabel();
        lblMSuccess = new JLabel();

        //======== this ========
        setTitle("Account Info");
        setResizable(false);
        setAlwaysOnTop(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== pnlAccount ========
        {
            pnlAccount.setPreferredSize(new Dimension(200, 200));

            // JFormDesigner evaluation mark
            pnlAccount.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                            "", javax.swing.border.TitledBorder.CENTER,
                            javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                            java.awt.Color.red), pnlAccount.getBorder()));
            pnlAccount.addPropertyChangeListener(e -> {
                if ("border".equals(e.getPropertyName())) throw new RuntimeException();
            });

            pnlAccount.setLayout(null);

            //---- btnLogOut ----
            btnLogOut.setText("Log Out");
            btnLogOut.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btnLogOut.addActionListener(this::btnLogOutActionPerformed);
            pnlAccount.add(btnLogOut);
            btnLogOut.setBounds(new Rectangle(new Point(165, 310), btnLogOut.getPreferredSize()));

            //---- lblUser ----
            lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            pnlAccount.add(lblUser);
            lblUser.setBounds(145, 20, 120, 25);

            //---- btnModPass ----
            btnModPass.setText("Change Password");
            btnModPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btnModPass.addActionListener(this::btnModPassActionPerformed);
            pnlAccount.add(btnModPass);
            btnModPass.setBounds(new Rectangle(new Point(135, 85), btnModPass.getPreferredSize()));

            //---- btnModEmail ----
            btnModEmail.setText("Change Email");
            btnModEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btnModEmail.addActionListener(this::btnModEmailActionPerformed);
            pnlAccount.add(btnModEmail);
            btnModEmail.setBounds(135, 135, 139, 36);

            //---- btnProfile ----
            btnProfile.setText("View Profile");
            btnProfile.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btnProfile.addActionListener(this::btnProfileActionPerformed);
            pnlAccount.add(btnProfile);
            btnProfile.setBounds(135, 190, 139, 36);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for (int i = 0; i < pnlAccount.getComponentCount(); i++) {
                    Rectangle bounds = pnlAccount.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = pnlAccount.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                pnlAccount.setMinimumSize(preferredSize);
                pnlAccount.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(pnlAccount);
        pnlAccount.setBounds(0, 0, 400, 355);

        //======== pnlPass ========
        {
            pnlPass.setPreferredSize(new Dimension(100, 100));
            pnlPass.setVisible(false);
            pnlPass.setLayout(null);

            //---- pfOld ----
            pfOld.setFont(new Font("Monospaced", Font.PLAIN, 14));
            pnlPass.add(pfOld);
            pfOld.setBounds(130, 45, 130, pfOld.getPreferredSize().height);

            //---- pfNew1 ----
            pfNew1.setFont(new Font("Monospaced", Font.PLAIN, 14));
            pnlPass.add(pfNew1);
            pfNew1.setBounds(130, 115, 130, 28);

            //---- pfNew2 ----
            pfNew2.setFont(new Font("Monospaced", Font.PLAIN, 14));
            pnlPass.add(pfNew2);
            pfNew2.setBounds(130, 185, 130, 28);

            //---- lblOldPass ----
            lblOldPass.setText("Current Password");
            lblOldPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlPass.add(lblOldPass);
            lblOldPass.setBounds(new Rectangle(new Point(140, 20), lblOldPass.getPreferredSize()));

            //---- lblNewPass1 ----
            lblNewPass1.setText("New Password");
            lblNewPass1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlPass.add(lblNewPass1);
            lblNewPass1.setBounds(150, 90, 95, 25);

            //---- lblNewPass2 ----
            lblNewPass2.setText("Confirm Password");
            lblNewPass2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlPass.add(lblNewPass2);
            lblNewPass2.setBounds(135, 165, 120, 20);

            //---- btnConfirmPass ----
            btnConfirmPass.setText("Confirm");
            btnConfirmPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btnConfirmPass.addActionListener(this::btnConfirmPassActionPerformed);
            pnlPass.add(btnConfirmPass);
            btnConfirmPass.setBounds(190, 255, 95, 36);

            //---- btnCancelPass ----
            btnCancelPass.setText("Back");
            btnCancelPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btnCancelPass.addActionListener(this::btnCancelActionPerformed);
            pnlPass.add(btnCancelPass);
            btnCancelPass.setBounds(100, 255, 81, 36);

            //---- lblPassError ----
            lblPassError.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblPassError.setForeground(new Color(255, 51, 51));
            pnlPass.add(lblPassError);
            lblPassError.setBounds(0, 225, 380, 25);

            //---- lblPSuccess ----
            lblPSuccess.setText("Password Updated");
            lblPSuccess.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblPSuccess.setVisible(false);
            pnlPass.add(lblPSuccess);
            lblPSuccess.setBounds(60, 230, 120, lblPSuccess.getPreferredSize().height);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for (int i = 0; i < pnlPass.getComponentCount(); i++) {
                    Rectangle bounds = pnlPass.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = pnlPass.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                pnlPass.setMinimumSize(preferredSize);
                pnlPass.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(pnlPass);
        pnlPass.setBounds(5, 40, 390, 305);

        //======== pnlEmail ========
        {
            pnlEmail.setPreferredSize(new Dimension(100, 100));
            pnlEmail.setVisible(false);
            pnlEmail.setLayout(null);

            //---- lblEmailPass ----
            lblEmailPass.setText("Password");
            lblEmailPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlEmail.add(lblEmailPass);
            lblEmailPass.setBounds(170, 65, 75, lblEmailPass.getPreferredSize().height);
            pnlEmail.add(pfEmail);
            pfEmail.setBounds(150, 95, 100, pfEmail.getPreferredSize().height);

            //---- lblEmailPass2 ----
            lblEmailPass2.setText("New Email");
            lblEmailPass2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlEmail.add(lblEmailPass2);
            lblEmailPass2.setBounds(170, 165, 70, 20);
            pnlEmail.add(tfEmail);
            tfEmail.setBounds(125, 195, 150, tfEmail.getPreferredSize().height);

            //---- bntBack2 ----
            bntBack2.setText("Back");
            bntBack2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            bntBack2.addActionListener(this::bntBackActionPerformed);
            pnlEmail.add(bntBack2);
            bntBack2.setBounds(new Rectangle(new Point(115, 280), bntBack2.getPreferredSize()));

            //---- bntBack3 ----
            bntBack3.setText("Confirm");
            bntBack3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            bntBack3.addActionListener(this::bntConfirmMailActionPerformed);
            pnlEmail.add(bntBack3);
            bntBack3.setBounds(200, 280, 90, 36);

            //---- lblMailError ----
            lblMailError.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblMailError.setForeground(new Color(255, 51, 51));
            lblMailError.setMinimumSize(new Dimension(100, 20));
            lblMailError.setMaximumSize(new Dimension(100, 20));
            pnlEmail.add(lblMailError);
            lblMailError.setBounds(10, 245, 380, 20);

            //---- lblMSuccess ----
            lblMSuccess.setText("Mail updated");
            lblMSuccess.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblMSuccess.setVisible(false);
            pnlEmail.add(lblMSuccess);
            lblMSuccess.setBounds(145, 250, 90, lblMSuccess.getPreferredSize().height);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for (int i = 0; i < pnlEmail.getComponentCount(); i++) {
                    Rectangle bounds = pnlEmail.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = pnlEmail.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                pnlEmail.setMinimumSize(preferredSize);
                pnlEmail.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(pnlEmail);
        pnlEmail.setBounds(0, 5, 395, 350);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
