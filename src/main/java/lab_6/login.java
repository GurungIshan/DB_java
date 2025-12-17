package lab_6;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.GridLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;

public class login extends JFrame {

    JTextField txtUsername;
    JPasswordField txtPassword;
    JButton btnSubmit;

    public login() {
        setTitle("User Form");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnSubmit = new JButton("Submit");
        add(new JLabel());   // empty cell
        add(btnSubmit);

        btnSubmit.addActionListener(e -> insertUser());

        setVisible(true);
    }

    private void insertUser() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javalab",
                    "root",
                    ""
            );

            String sql = "INSERT INTO user(username, password) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Inserted Successfully");

            con.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new login();
    }
}
