/*
 * Created by JFormDesigner on Tue Mar 27 14:53:45 EEST 2018
 */

package presentation;

import business.AdminService;
import business.ProfileException;
import business.StudentService;
import dataAccess.entity.AdminProfile;
import dataAccess.entity.Profile;
import dataAccess.entity.StudentProfile;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Vlad Ciucescu
 */
class ProfileGUI extends JFrame {
    private boolean modOwn;
    private final SessionData sessionData;
    private final AccountGUI accountGUI;
    private final SelectStudentGUI ssGUI;

    public ProfileGUI(SessionData sessionData, AccountGUI accountGUI) {
        this.sessionData = sessionData;
        this.ssGUI = new SelectStudentGUI(sessionData, this);
        this.accountGUI = accountGUI;
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Profile");
        this.pack();
        this.setLocationRelativeTo(null);

    }

    public void setTitleL(String title) {
        lblTitle.setText(title);
    }

    private void setError(boolean error, String message) {
        lblError.setText(message);
        lblError.setVisible(error);
    }

    public void showStudentEditPnl() {
        pnlData.setVisible(false);
        pnlChgStudData.setVisible(true);
        pnlChgProfData.setVisible(true);
    }

    public void displayProfileData(String info) {
        taProfileInfo.setText(info);
    }

    public void displayStudentButtons() {
        pnlAdmin.setVisible(false);
    }

    public void displayAdminButtons() {
        pnlAdmin.setVisible(true);
    }

    private void btnBackActionPerformed(ActionEvent e) {
        showAccount();
    }

    private void showAccount() {
        sessionData.setCurrentAdminProfile(null);
        sessionData.setCurrentStudentProfile(null);
        accountGUI.setVisible(true);
        this.dispose();
    }

    private void btnAdmChgStudActionPerformed(ActionEvent e) {
        modOwn = false;
        openSSWindow(false);
    }

    private void openSSWindow(boolean reqE) {
        setVisible(false);
        ssGUI.forEnrollments(reqE);
        ssGUI.setVisible(true);
    }

    private void btnAdmChgOwnActionPerformed(ActionEvent e) {
        modOwn = true;
        pnlData.setVisible(false);
        pnlChgProfData.setVisible(true);
    }

    private void btnBack2ActionPerformed(ActionEvent e) {
        pnlData.setVisible(true);
        tfAddr.setText("");
        tfGroup.setText("");
        tfName.setText("");
        tfYr.setText("");
        lblSuccess.setText("");
        lblError.setText("");
        pnlChgProfData.setVisible(false);
        pnlChgStudData.setVisible(false);
    }

    private void btnUpdNameActionPerformed(ActionEvent e) {
        lblSuccess.setText("");
        setError(false, "");
        String name = tfName.getText();
        if (name.length() == 0) {
            setError(true, "Please enter the new name.");
            return;
        }
        if (updateName(name, modOwn)) {
            lblSuccess.setVisible(true);
            lblSuccess.setText("Updated name");
        }
    }

    private boolean updateName(String newN, boolean admin) {
        Profile profile;
        try {
            if (admin)
                profile = new AdminService().updateName(sessionData.getCurrentAdminProfile(), newN);
            else
                profile = new StudentService().updateName(sessionData.getCurrentStudentProfile(), newN);
        } catch (ProfileException e) {
            setError(true, e.getMessage());
            return false;
        }
        if (admin) sessionData.setCurrentAdminProfile((AdminProfile) profile);
        else sessionData.setCurrentStudentProfile((StudentProfile) profile);
        setError(false, "");
        return true;
    }

    private void btnUpdAddrActionPerformed(ActionEvent e) {
        lblSuccess.setText("");
        setError(false, "");
        String addr = tfAddr.getText();
        if (addr.length() == 0) {
            setError(true, "Please enter the new address.");
            return;
        }
        if (updateAddress(addr, modOwn)) {
            lblSuccess.setVisible(true);
            lblSuccess.setText("Updated address");
        }
    }

    private boolean updateAddress(String addr, boolean admin) {
        Profile profile;
        try {
            if (admin)
                profile = new AdminService().updateAddress(sessionData.getCurrentAdminProfile(), addr);
            else
                profile = new StudentService().updateAddress(sessionData.getCurrentStudentProfile(), addr);
        } catch (ProfileException e) {
            setError(true, e.getMessage());
            return false;
        }
        if (admin) sessionData.setCurrentAdminProfile((AdminProfile) profile);
        else sessionData.setCurrentStudentProfile((StudentProfile) profile);
        setError(false, "");
        return true;
    }

    private void btnEnrollmentsActionPerformed(ActionEvent e) {
        showEnrollments();
    }

    private void showEnrollments() {
        if (sessionData.isAdmin()) {
            openSSWindow(true);
        } else {
            setVisible(false);
            EnrollmentGUI enrollmentGUI = new EnrollmentGUI(sessionData, this);
            enrollmentGUI.setVisible(true);
            enrollmentGUI.studentView(true);
            enrollmentGUI.setTitleL("Enrollments of " + sessionData.getCurrentStudentProfile().getName());
            enrollmentGUI.updateTables();
        }
    }

    private void btnUpdYrActionPerformed(ActionEvent e) {
        lblSuccess.setText("");
        setError(false, "");
        String aux = tfYr.getText();
        int yr;
        if(aux.length()==0) {
            setError(true,"Please enter the new year");
            return;
        }
        try {
            yr = Integer.valueOf(aux);
        }catch (NumberFormatException er) {
            setError(true, "Year must be a number");
            return;
        }
        if (yr<1 || yr>6) {
            setError(true, "Invalid year");
            return;
        }
        if(updateYear(yr)){
            lblSuccess.setVisible(true);
            lblSuccess.setText("Updated year");
        }
    }

    private boolean updateYear(int year) {
        StudentProfile profile;
        try {
            profile = new StudentService().updateStudentYear(sessionData.getCurrentStudentProfile(), year);
        } catch (ProfileException e) {
            setError(true, e.getMessage());
            return false;
        }
        sessionData.setCurrentStudentProfile(profile);
        setError(false, "");
        return true;
    }


    private void btnUpdGrActionPerformed(ActionEvent e) {
        lblSuccess.setText("");
        setError(false, "");
        String aux = tfGroup.getText();
        int gr;
        if(aux.length()==0) {
            setError(true,"Please enter the new group");
            return;
        }
        try {
            gr = Integer.valueOf(aux);
        }catch (NumberFormatException er) {
            setError(true, "Group must be a number");
            return;
        }
        if (gr<0) {
            setError(true, "Invalid group");
            return;
        }
        if(updateGroup(gr)){
            lblSuccess.setVisible(true);
            lblSuccess.setText("Updated group");
        }
    }

    private boolean updateGroup(int group) {
        StudentProfile profile;
        try {
            profile = new StudentService().updateStudentGroup(sessionData.getCurrentStudentProfile(), group);
        } catch (ProfileException e) {
            setError(true, e.getMessage());
            return false;
        }
        sessionData.setCurrentStudentProfile(profile);
        setError(false, "");
        return true;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vlad Ciucescu
        pnlData = new JPanel();
        JButton btnBack = new JButton();
        lblTitle = new JLabel();
        taProfileInfo = new JTextArea();
        pnlAdmin = new JPanel();
        JButton btnAdmChgStud = new JButton();
        JButton btnAdmChgOwn = new JButton();
        JButton btnEnrollments = new JButton();
        pnlChgProfData = new JPanel();
        JButton btnUpdName = new JButton();
        tfName = new JTextField();
        tfAddr = new JTextField();
        JLabel lblAddress = new JLabel();
        JLabel lblName = new JLabel();
        JButton btnBack2 = new JButton();
        JButton btnUpdAddr = new JButton();
        lblError = new JLabel();
        lblSuccess = new JLabel();
        pnlChgStudData = new JPanel();
        tfYr = new JTextField();
        JLabel lblYr = new JLabel();
        tfGroup = new JTextField();
        JLabel lblGroup = new JLabel();
        JButton btnUpdYr = new JButton();
        JButton btnUpdGr = new JButton();

        //======== this ========
        setResizable(false);
        setMinimumSize(new Dimension(450, 450));
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== pnlData ========
        {

            // JFormDesigner evaluation mark
            pnlData.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), pnlData.getBorder())); pnlData.addPropertyChangeListener(e -> {if("border".equals(e.getPropertyName()))throw new RuntimeException();});

            pnlData.setLayout(null);

            //---- btnBack ----
            btnBack.setText("Back");
            btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btnBack.addActionListener(this::btnBackActionPerformed);
            pnlData.add(btnBack);
            btnBack.setBounds(50, 365, 65, btnBack.getPreferredSize().height);

            //---- lblTitle ----
            lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            lblTitle.setMinimumSize(new Dimension(100, 20));
            lblTitle.setText("  ");
            pnlData.add(lblTitle);
            lblTitle.setBounds(95, 50, 225, lblTitle.getPreferredSize().height);

            //---- taProfileInfo ----
            taProfileInfo.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            taProfileInfo.setEditable(false);
            pnlData.add(taProfileInfo);
            taProfileInfo.setBounds(45, 120, 340, 145);

            //======== pnlAdmin ========
            {
                pnlAdmin.setVisible(false);
                pnlAdmin.setLayout(null);

                //---- btnAdmChgStud ----
                btnAdmChgStud.setText("Change Student Data");
                btnAdmChgStud.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                btnAdmChgStud.addActionListener(e -> {
			btnAdmChgStudActionPerformed(e);
			btnAdmChgStudActionPerformed(e);
		});
                pnlAdmin.add(btnAdmChgStud);
                btnAdmChgStud.setBounds(10, 10, 195, 40);

                //---- btnAdmChgOwn ----
                btnAdmChgOwn.setText("Change Own Data");
                btnAdmChgOwn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                btnAdmChgOwn.addActionListener(this::btnAdmChgOwnActionPerformed);
                pnlAdmin.add(btnAdmChgOwn);
                btnAdmChgOwn.setBounds(10, 70, 195, 40);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < pnlAdmin.getComponentCount(); i++) {
                        Rectangle bounds = pnlAdmin.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = pnlAdmin.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    pnlAdmin.setMinimumSize(preferredSize);
                    pnlAdmin.setPreferredSize(preferredSize);
                }
            }
            pnlData.add(pnlAdmin);
            pnlAdmin.setBounds(220, 270, 215, 125);

            //---- btnEnrollments ----
            btnEnrollments.setText("Enrollments");
            btnEnrollments.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btnEnrollments.addActionListener(this::btnEnrollmentsActionPerformed);
            pnlData.add(btnEnrollments);
            btnEnrollments.setBounds(45, 295, 125, 36);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < pnlData.getComponentCount(); i++) {
                    Rectangle bounds = pnlData.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = pnlData.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                pnlData.setMinimumSize(preferredSize);
                pnlData.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(pnlData);
        pnlData.setBounds(0, 0, 445, 420);

        //======== pnlChgProfData ========
        {
            pnlChgProfData.setVisible(false);
            pnlChgProfData.setLayout(null);

            //---- btnUpdName ----
            btnUpdName.setText("Update");
            btnUpdName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btnUpdName.addActionListener(this::btnUpdNameActionPerformed);
            pnlChgProfData.add(btnUpdName);
            btnUpdName.setBounds(225, 75, 80, btnUpdName.getPreferredSize().height);
            pnlChgProfData.add(tfName);
            tfName.setBounds(30, 80, 170, tfName.getPreferredSize().height);
            pnlChgProfData.add(tfAddr);
            tfAddr.setBounds(30, 160, 295, tfAddr.getPreferredSize().height);

            //---- lblAddress ----
            lblAddress.setText("Address");
            lblAddress.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlChgProfData.add(lblAddress);
            lblAddress.setBounds(30, 130, 55, lblAddress.getPreferredSize().height);

            //---- lblName ----
            lblName.setText("Name");
            lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlChgProfData.add(lblName);
            lblName.setBounds(35, 55, 55, lblName.getPreferredSize().height);

            //---- btnBack2 ----
            btnBack2.setText("Back");
            btnBack2.addActionListener(this::btnBack2ActionPerformed);
            pnlChgProfData.add(btnBack2);
            btnBack2.setBounds(new Rectangle(new Point(35, 10), btnBack2.getPreferredSize()));

            //---- btnUpdAddr ----
            btnUpdAddr.setText("Update");
            btnUpdAddr.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btnUpdAddr.addActionListener(this::btnUpdAddrActionPerformed);
            pnlChgProfData.add(btnUpdAddr);
            btnUpdAddr.setBounds(345, 155, 80, 36);

            //---- lblError ----
            lblError.setForeground(new Color(255, 51, 51));
            lblError.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlChgProfData.add(lblError);
            lblError.setBounds(135, 15, 295, 20);

            //---- lblSuccess ----
            lblSuccess.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            pnlChgProfData.add(lblSuccess);
            lblSuccess.setBounds(170, 45, 240, 20);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < pnlChgProfData.getComponentCount(); i++) {
                    Rectangle bounds = pnlChgProfData.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = pnlChgProfData.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                pnlChgProfData.setMinimumSize(preferredSize);
                pnlChgProfData.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(pnlChgProfData);
        pnlChgProfData.setBounds(0, 5, 450, 200);

        //======== pnlChgStudData ========
        {
            pnlChgStudData.setVisible(false);
            pnlChgStudData.setLayout(null);
            pnlChgStudData.add(tfYr);
            tfYr.setBounds(25, 45, 115, 24);

            //---- lblYr ----
            lblYr.setText("Year");
            lblYr.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlChgStudData.add(lblYr);
            lblYr.setBounds(25, 20, 45, 20);
            pnlChgStudData.add(tfGroup);
            tfGroup.setBounds(25, 125, 170, 24);

            //---- lblGroup ----
            lblGroup.setText("Group");
            lblGroup.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlChgStudData.add(lblGroup);
            lblGroup.setBounds(25, 95, 55, 20);

            //---- btnUpdYr ----
            btnUpdYr.setText("Update");
            btnUpdYr.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btnUpdYr.addActionListener(this::btnUpdYrActionPerformed);
            pnlChgStudData.add(btnUpdYr);
            btnUpdYr.setBounds(155, 40, 80, 36);

            //---- btnUpdGr ----
            btnUpdGr.setText("Update");
            btnUpdGr.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btnUpdGr.addActionListener(this::btnUpdGrActionPerformed);
            pnlChgStudData.add(btnUpdGr);
            btnUpdGr.setBounds(215, 120, 80, 36);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < pnlChgStudData.getComponentCount(); i++) {
                    Rectangle bounds = pnlChgStudData.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = pnlChgStudData.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                pnlChgStudData.setMinimumSize(preferredSize);
                pnlChgStudData.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(pnlChgStudData);
        pnlChgStudData.setBounds(0, 205, 450, 215);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
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

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Vlad Ciucescu
    private JPanel pnlData;
    private JLabel lblTitle;
    private JTextArea taProfileInfo;
    private JPanel pnlAdmin;
    private JPanel pnlChgProfData;
    private JTextField tfName;
    private JTextField tfAddr;
    private JLabel lblError;
    private JLabel lblSuccess;
    private JPanel pnlChgStudData;
    private JTextField tfYr;
    private JTextField tfGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
