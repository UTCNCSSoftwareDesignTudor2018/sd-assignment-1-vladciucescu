/*
 * Created by JFormDesigner on Tue Mar 27 22:34:00 EEST 2018
 */

package presentation;

import java.awt.event.*;
import dataAccess.entity.StudentProfile;

import java.awt.*;
import javax.swing.*;

/**
 * @author Vlad Ciucescu
 */
class SelectStudentGUI extends JFrame {
    private final Presenter presenter;
    private boolean fe;
    public SelectStudentGUI(Presenter presenter) {
        this.presenter = presenter;
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
        cbStudents = new JComboBox<>(presenter.getStudentsVector());
    }

    private void btnBackActionPerformed(ActionEvent e) {
        presenter.backToProfileWindow();
    }

    private void btnDoneActionPerformed(ActionEvent e) {
        StudentProfile student = (StudentProfile) cbStudents.getSelectedItem();
        this.setVisible(false);
        if (fe) presenter.studentSelectedForEnroll(student);
        else presenter.studentSelectedForChange(student);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vlad Ciucescu
        createUIComponents();

        btnBack = new JButton();
        btnDone = new JButton();

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

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Vlad Ciucescu
    private JButton btnBack;
    private JButton btnDone;
    private JComboBox cbStudents;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
