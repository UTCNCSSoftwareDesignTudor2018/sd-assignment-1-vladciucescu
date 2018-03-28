/*
 * Created by JFormDesigner on Wed Mar 28 00:41:19 EEST 2018
 */

package presentation;

import dataAccess.entity.Course;
import dataAccess.entity.Enrollment;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;

/**
 * @author Vlad Ciucescu
 */
class EnrollmentGUI extends JFrame {
    private final Presenter presenter;

    public EnrollmentGUI(Presenter presenter) {
        this.presenter = presenter;
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Enrollments");
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void setTitleL(String title) {
        lblTitle.setText(title);
    }

    public void updateTables() {
        List<Enrollment> enrollmentList = presenter.getStudentEnrollments();
        List<Course> courseList = presenter.getAvailableCourses();
        updateAvailableTable(courseList);
        updateEnrolledTable(enrollmentList);

        cbEnrolled.removeAllItems();
        for (Enrollment e: enrollmentList) cbEnrolled.addItem(e);

        cbAvailable.removeAllItems();
        for (Course c: courseList) cbAvailable.addItem(c);
    }

    public void setError(boolean error, String message) {
        lblError.setText(message);
        lblError.setVisible(error);
    }

    public void studentView(boolean student) {
        pnlStud.setVisible(student);
        pnlAdmin.setVisible(!student);
    }

    private void btnBackActionPerformed(ActionEvent e) {
        presenter.backToProfileWindow();
        this.setVisible(false);
        this.dispose();
    }

    private Object[][] toEObjectArray(List<Enrollment> enrollments) {
        Object res[][] = null;
        if (enrollments != null) {
            res = new Object[enrollments.size()][];
            for (int i = 0; i < enrollments.size(); i++) {
                res[i] = convertToArray(enrollments.get(i));
            }
        }
        return res;
    }

    private static Object[] convertToArray(Enrollment e) {
        return new Object[]{ e.getCourse().getName(),  e.getCourse().getEndDate(), e.getGrade() };
    }

    private Object[][] toCObjectArray(List<Course> courses) {
        Object res[][] = null;
        if (courses != null) {
            res = new Object[courses.size()][];
            for (int i = 0; i < courses.size(); i++) {
                res[i] = convertToArray(courses.get(i));
            }
        }
        return res;
    }

    private static Object[] convertToArray(Course c) {
        return new Object[]{ c.getName(), c.getStartDate(), c.getEndDate(), c.getExam().getDate() };
    }

    private void updateAvailableTable(List<Course> courseList) {
        tblAvailable = new JTable(toCObjectArray(courseList),
                new Object[] { "Course", "Starts", "Ends", "Exam Date"});
        tblAvailable.setEnabled(false);
        tblAvailable.getColumnModel().getColumn(0).setPreferredWidth(200);
        tblAvailable.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblAvailable.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblAvailable.getColumnModel().getColumn(3).setPreferredWidth(100);
        spAvailable.setViewportView(tblAvailable);
    }

    private void updateEnrolledTable(List<Enrollment> enrollments) {
        tblEnrolled = new JTable(toEObjectArray(enrollments),
                new Object[] { "Course", "Ends", "Grade"});
        tblEnrolled.setEnabled(false);
        tblEnrolled.getColumnModel().getColumn(0).setPreferredWidth(150);
        tblEnrolled.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblEnrolled.getColumnModel().getColumn(2).setPreferredWidth(50);
        spEnrolled.setViewportView(tblEnrolled);
    }

    private void btnDetailsActionPerformed(ActionEvent e) {
        setError(false, "");
        lblSuccess.setText("");
        Enrollment enrollment = (Enrollment)cbEnrolled.getSelectedItem();
        if (enrollment == null) return;
        JDialog d = new JDialog(this , "Details", true);
        d.setLayout( new FlowLayout());
        d.setLocationRelativeTo(null);
        JButton b = new JButton ("OK");
        b.addActionListener ( ev-> d.dispose());
        d.add( new JTextArea("Course " + enrollment.getCourse().getName() + "\n" +
                            "Started on " + enrollment.getCourse().getStartDate() + ", ends on " + enrollment.getCourse().getEndDate() + "\n" +
                            "Exam on " + enrollment.getCourse().getExam().getDate() + " at " + enrollment.getCourse().getExam().getTime() + "\n" +
                            "Grade " + enrollment.getGrade()));
        d.add(b);
        d.setSize(300,150);
        d.setVisible(true);
    }

    private void btnEnrollActionPerformed(ActionEvent e) {
        setError(false, "");
        lblSuccess.setText("");
        Course course = (Course)cbAvailable.getSelectedItem();
        if (course == null) return;
        boolean ok = presenter.addEnrollment(course);
        if (ok) lblSuccess.setText("Successfully enrolled");
        updateTables();
    }

    private void btnDelActionPerformed(ActionEvent e) {
        setError(false, "");
        lblSuccess.setText("");
        Enrollment enrollment = (Enrollment)cbEnrolled.getSelectedItem();
        if (enrollment == null) return;
        if (presenter.deleteEnrollment(enrollment)) lblSuccess.setText("Enrollment deleted");
        updateTables();
    }

    private void btnGradeActionPerformed(ActionEvent e) {
        lblSuccess.setText("");
        setError(false, "");
        String aux = tfGrade.getText();
        double grade;
        Enrollment enrollment = (Enrollment)cbEnrolled.getSelectedItem();
        if (enrollment == null) return;
        if(aux.length()==0) {
            setError(true,"Please enter the new grade");
            return;
        }
        try {
            grade = Double.valueOf(aux);
        }catch (NumberFormatException er) {
            setError(true, "Grade must be a number");
            return;
        }
        if (grade<1.0 || grade>10.0) {
            setError(true, "Invalid grade");
            return;
        }
        if(presenter.updateGrade(enrollment, grade)){
            lblSuccess.setVisible(true);
            lblSuccess.setText("Updated grade");
        }
        updateTables();
    }

    private void btnReportActionPerformed(ActionEvent e) {
        lblSuccess.setText("");
        setError(false, "");
        String d1 = tfStart.getText();
        String d2 = tfEnd.getText();
        LocalDate start, end;
        try {
            start = LocalDate.parse(d1);
            end = LocalDate.parse(d2);
        } catch (DateTimeParseException ex) {
            setError(true, "Invalid date");
            return;
        }
        File report = presenter.getReport(start,end);
        if (report == null) return;
        try {
            java.awt.Desktop.getDesktop().edit(report);
        } catch (IOException ex) {
            lblError.setText("Can not open report");
            return;
        }
        setError(false,"");
        lblSuccess.setText("Report created");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vlad Ciucescu
        btnBack = new JButton();
        spEnrolled = new JScrollPane();
        tblEnrolled = new JTable();
        lblEnrolled = new JLabel();
        cbEnrolled = new JComboBox();
        lblGetDetails = new JLabel();
        lblTitle = new JLabel();
        btnDetails = new JButton();
        pnlStud = new JPanel();
        btnEnroll = new JButton();
        cbAvailable = new JComboBox();
        lblEnroll = new JLabel();
        spAvailable = new JScrollPane();
        tblAvailable = new JTable();
        lblAvailable = new JLabel();
        pnlAdmin = new JPanel();
        btnDel = new JButton();
        tfStart = new JTextField();
        tfEnd = new JTextField();
        lblStart = new JLabel();
        lblEnd = new JLabel();
        btnReport = new JButton();
        tfGrade = new JTextField();
        lblGrade = new JLabel();
        btnGrade = new JButton();
        lblError = new JLabel();
        lblSuccess = new JLabel();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- btnBack ----
        btnBack.setText("Back");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBack.addActionListener(this::btnBackActionPerformed);
        contentPane.add(btnBack);
        btnBack.setBounds(20, 485, 90, btnBack.getPreferredSize().height);

        //======== spEnrolled ========
        {
            spEnrolled.setViewportView(tblEnrolled);
        }
        contentPane.add(spEnrolled);
        spEnrolled.setBounds(15, 115, 260, 170);

        //---- lblEnrolled ----
        lblEnrolled.setText("Enrolled in:");
        lblEnrolled.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(lblEnrolled);
        lblEnrolled.setBounds(110, 85, 100, lblEnrolled.getPreferredSize().height);

        //---- cbEnrolled ----
        cbEnrolled.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPane.add(cbEnrolled);
        cbEnrolled.setBounds(50, 330, 215, cbEnrolled.getPreferredSize().height);

        //---- lblGetDetails ----
        lblGetDetails.setText("Select enrollment to get details");
        lblGetDetails.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPane.add(lblGetDetails);
        lblGetDetails.setBounds(55, 300, 210, lblGetDetails.getPreferredSize().height);

        //---- lblTitle ----
        lblTitle.setText(" ");
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPane.add(lblTitle);
        lblTitle.setBounds(140, 30, 305, lblTitle.getPreferredSize().height);

        //---- btnDetails ----
        btnDetails.setText("Details");
        btnDetails.addActionListener(this::btnDetailsActionPerformed);
        contentPane.add(btnDetails);
        btnDetails.setBounds(105, 375, 90, btnDetails.getPreferredSize().height);

        //======== pnlStud ========
        {
            pnlStud.setVisible(false);

            // JFormDesigner evaluation mark
            pnlStud.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), pnlStud.getBorder())); pnlStud.addPropertyChangeListener(e -> {if("border".equals(e.getPropertyName()))throw new RuntimeException();});

            pnlStud.setLayout(null);

            //---- btnEnroll ----
            btnEnroll.setText("Enroll");
            btnEnroll.addActionListener(this::btnEnrollActionPerformed);
            pnlStud.add(btnEnroll);
            btnEnroll.setBounds(130, 355, 90, 32);

            //---- cbAvailable ----
            cbAvailable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlStud.add(cbAvailable);
            cbAvailable.setBounds(70, 310, 215, 30);

            //---- lblEnroll ----
            lblEnroll.setText("Select course to enroll");
            lblEnroll.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlStud.add(lblEnroll);
            lblEnroll.setBounds(105, 280, 170, 20);

            //======== spAvailable ========
            {
                spAvailable.setViewportView(tblAvailable);
            }
            pnlStud.add(spAvailable);
            spAvailable.setBounds(15, 95, 350, 170);

            //---- lblAvailable ----
            lblAvailable.setText("Available courses:");
            lblAvailable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            pnlStud.add(lblAvailable);
            lblAvailable.setBounds(95, 55, 145, 40);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < pnlStud.getComponentCount(); i++) {
                    Rectangle bounds = pnlStud.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = pnlStud.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                pnlStud.setMinimumSize(preferredSize);
                pnlStud.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(pnlStud);
        pnlStud.setBounds(275, 20, 370, 475);

        //======== pnlAdmin ========
        {
            pnlAdmin.setLayout(null);

            //---- btnDel ----
            btnDel.setText("Delete Enrollment");
            btnDel.addActionListener(this::btnDelActionPerformed);
            pnlAdmin.add(btnDel);
            btnDel.setBounds(new Rectangle(new Point(20, 265), btnDel.getPreferredSize()));
            pnlAdmin.add(tfStart);
            tfStart.setBounds(55, 80, 75, tfStart.getPreferredSize().height);
            pnlAdmin.add(tfEnd);
            tfEnd.setBounds(160, 80, 75, 24);

            //---- lblStart ----
            lblStart.setText("Start date");
            lblStart.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlAdmin.add(lblStart);
            lblStart.setBounds(60, 55, 65, lblStart.getPreferredSize().height);

            //---- lblEnd ----
            lblEnd.setText("End date");
            lblEnd.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlAdmin.add(lblEnd);
            lblEnd.setBounds(165, 55, 65, 20);

            //---- btnReport ----
            btnReport.setText("Generate Report");
            btnReport.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btnReport.addActionListener(this::btnReportActionPerformed);
            pnlAdmin.add(btnReport);
            btnReport.setBounds(new Rectangle(new Point(80, 135), btnReport.getPreferredSize()));
            pnlAdmin.add(tfGrade);
            tfGrade.setBounds(30, 340, 70, tfGrade.getPreferredSize().height);

            //---- lblGrade ----
            lblGrade.setText("Grade for course");
            lblGrade.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pnlAdmin.add(lblGrade);
            lblGrade.setBounds(new Rectangle(new Point(30, 310), lblGrade.getPreferredSize()));

            //---- btnGrade ----
            btnGrade.setText("Assign grade");
            btnGrade.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btnGrade.addActionListener(this::btnGradeActionPerformed);
            pnlAdmin.add(btnGrade);
            btnGrade.setBounds(new Rectangle(new Point(135, 335), btnGrade.getPreferredSize()));

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
        contentPane.add(pnlAdmin);
        pnlAdmin.setBounds(270, 65, 275, 415);

        //---- lblError ----
        lblError.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblError.setForeground(new Color(255, 51, 51));
        contentPane.add(lblError);
        lblError.setBounds(40, 420, 230, 20);

        //---- lblSuccess ----
        lblSuccess.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPane.add(lblSuccess);
        lblSuccess.setBounds(40, 455, 230, 20);

        contentPane.setPreferredSize(new Dimension(660, 570));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Vlad Ciucescu
    private JButton btnBack;
    private JScrollPane spEnrolled;
    private JTable tblEnrolled;
    private JLabel lblEnrolled;
    private JComboBox cbEnrolled;
    private JLabel lblGetDetails;
    private JLabel lblTitle;
    private JButton btnDetails;
    private JPanel pnlStud;
    private JButton btnEnroll;
    private JComboBox cbAvailable;
    private JLabel lblEnroll;
    private JScrollPane spAvailable;
    private JTable tblAvailable;
    private JLabel lblAvailable;
    private JPanel pnlAdmin;
    private JButton btnDel;
    private JTextField tfStart;
    private JTextField tfEnd;
    private JLabel lblStart;
    private JLabel lblEnd;
    private JButton btnReport;
    private JTextField tfGrade;
    private JLabel lblGrade;
    private JButton btnGrade;
    private JLabel lblError;
    private JLabel lblSuccess;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
