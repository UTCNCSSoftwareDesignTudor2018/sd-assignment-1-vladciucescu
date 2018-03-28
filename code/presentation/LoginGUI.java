package presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class LoginGUI extends JFrame{
    private final Presenter presenter;

    public LoginGUI(Presenter presenter) {
        this.presenter = presenter;
        this.setTitle("Login");
        initComponents();
        this.setContentPane(loginPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void setError(Boolean error, String message) {
        lblError.setVisible(error);
        lblError.setText(message);
    }

    private void btnLoginActionPerformed(ActionEvent e) {
        setError(false, "");
        String username = tfUsername.getText();
        char[] password = passwordField1.getPassword();
        if (username.length()==0) {
            setError(true, "Please input username");
            return;
        }
        if (password.length==0) {
            setError(true, "Please input password");
            return;
        }
        presenter.attemptLogin(username, password);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vlad Ciucescu
        loginPanel = new JPanel();
        loginDataPanel = new JPanel();
        lblLogin = new JLabel();
        tfUsername = new JTextField();
        lblUser = new JLabel();
        lblPass = new JLabel();
        btnLogin = new JButton();
        passwordField1 = new JPasswordField();
        lblError = new JLabel();
        lblTitle = new JLabel();

        //======== loginPanel ========
        {
            loginPanel.setBackground(new Color(187, 187, 187));
            loginPanel.setBorder(null);

            // JFormDesigner evaluation mark
            loginPanel.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), loginPanel.getBorder())); loginPanel.addPropertyChangeListener(e -> {if("border".equals(e.getPropertyName()))throw new RuntimeException();});

            loginPanel.setLayout(null);

            //======== loginDataPanel ========
            {
                loginDataPanel.setLayout(null);

                //---- lblLogin ----
                lblLogin.setText("Login");
                lblLogin.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                loginDataPanel.add(lblLogin);
                lblLogin.setBounds(new Rectangle(new Point(205, 65), lblLogin.getPreferredSize()));

                //---- tfUsername ----
                tfUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                loginDataPanel.add(tfUsername);
                tfUsername.setBounds(160, 135, 140, tfUsername.getPreferredSize().height);

                //---- lblUser ----
                lblUser.setText("Username");
                lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                loginDataPanel.add(lblUser);
                lblUser.setBounds(new Rectangle(new Point(195, 105), lblUser.getPreferredSize()));

                //---- lblPass ----
                lblPass.setText("Password");
                lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                loginDataPanel.add(lblPass);
                lblPass.setBounds(new Rectangle(new Point(200, 200), lblPass.getPreferredSize()));

                //---- btnLogin ----
                btnLogin.setText("Log In");
                btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                btnLogin.addActionListener(this::btnLoginActionPerformed);
                loginDataPanel.add(btnLogin);
                btnLogin.setBounds(190, 285, 80, 43);

                //---- passwordField1 ----
                passwordField1.setFont(new Font("Monospaced", Font.PLAIN, 14));
                loginDataPanel.add(passwordField1);
                passwordField1.setBounds(160, 230, 140, passwordField1.getPreferredSize().height);

                //---- lblError ----
                lblError.setText("Invalid username or password");
                lblError.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                lblError.setForeground(new Color(255, 51, 51));
                lblError.setVisible(false);
                loginDataPanel.add(lblError);
                lblError.setBounds(160, 360, 185, lblError.getPreferredSize().height);

                //---- lblTitle ----
                lblTitle.setText("Student Management");
                lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                loginDataPanel.add(lblTitle);
                lblTitle.setBounds(140, 25, 185, 25);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < loginDataPanel.getComponentCount(); i++) {
                        Rectangle bounds = loginDataPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = loginDataPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    loginDataPanel.setMinimumSize(preferredSize);
                    loginDataPanel.setPreferredSize(preferredSize);
                }
            }
            loginPanel.add(loginDataPanel);
            loginDataPanel.setBounds(0, 0, 460, 410);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < loginPanel.getComponentCount(); i++) {
                    Rectangle bounds = loginPanel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = loginPanel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                loginPanel.setMinimumSize(preferredSize);
                loginPanel.setPreferredSize(preferredSize);
            }
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Vlad Ciucescu
    private JPanel loginPanel;
    private JPanel loginDataPanel;
    private JLabel lblLogin;
    private JTextField tfUsername;
    private JLabel lblUser;
    private JLabel lblPass;
    private JButton btnLogin;
    private JPasswordField passwordField1;
    private JLabel lblError;
    private JLabel lblTitle;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
