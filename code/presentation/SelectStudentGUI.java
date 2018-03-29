/*
 * Created by JFormDesigner on Tue Mar 27 22:34:00 EEST 2018
 */

package presentation;

import java.awt.event.*;

import business.ProfileException;
import business.StudentService;
import dataAccess.entity.StudentProfile;

import java.awt.*;
import java.util.Vector;
import javax.swing.*;

/**
 * @author Vlad Ciucescu
 */
class SelectStudentGUI extends JFrame {
    private final SessionData sessionData;
    private final ProfileGUI profileGUI;
    private boolean fe;

    public SelectStudentGUI(SessionData sessionData, ProfileGUI profileGUI) {
        this.sessionData = sessionData;
        this.profileGUI = profileGUI;
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Select student");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }

    public void forEnrollments(boolean fe) {
        this.fe=fe;
    }

    private void createUIComponents() {
        Vector<StudentProfile> students = new Vector<>();
        try {
            students = new StudentService().getStudentVector();
        } catch (ProfileException e) {
        }
        cbStudents = new JComboBox<>(students);
    }

    private void btnBackActionPerformed(ActionEvent e) {
        profileGUI.setVisible(true);
        setVisible(false);
    }

    private void btnDoneActionPerformed(ActionEvent e) {
        StudentProfile student = (StudentProfile) cbStudents.getSelectedItem();
        this.setVisible(false);
        if (fe) studentSelectedForEnroll(student);
        else studentSelectedForChange(student);
    }

    private void studentSelectedForEnroll(StudentProfile student) {
        EnrollmentGUI enrollmentGUI = new EnrollmentGUI(sessionData, profileGUI);
        enrollmentGUI.setVisible(true);
        sessionData.setCurrentStudentProfile(student);
        profileGUI.setVisible(false);
        enrollmentGUI.setTitleL("Enrollments of " + sessionData.getCurrentStudentProfile().getName());
        enrollmentGUI.updateTables();
        enrollmentGUI.studentView(false);
    }

    private void studentSelectedForChange(StudentProfile student) {
        profileGUI.setVisible(true);
        profileGUI.showStudentEditPnl();
        sessionData.setCurrentStudentProfile(student);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vlad Ciucescu
        createUIComponents();

        JButton btnBack = new JButton();
        JButton btnDone = new JButton();

        //======== this ========
        setResizable(false);
        setAlwaysOnTop(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- btnBack ----
        btnBack.setText("Back");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBack.addActionListener(this::btnBackActionPerformed);
        contentPane.add(btnBack);
        btnBack.setBounds(30, 95, 70, btnBack.getPreferredSize().height);

        //---- btnDone ----
        btnDone.setText("Done");
        btnDone.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnDone.addActionListener(this::btnDoneActionPerformed);
        contentPane.add(btnDone);
        btnDone.setBounds(280, 90, 70, 36);
        contentPane.add(cbStudents);
        cbStudents.setBounds(70, 20, 255, cbStudents.getPreferredSize().height);

        contentPane.setPreferredSize(new Dimension(400, 175));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private JComboBox cbStudents;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
