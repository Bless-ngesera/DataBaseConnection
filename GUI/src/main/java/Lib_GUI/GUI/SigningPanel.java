package Lib_GUI.GUI;

import javax.swing.*;
import java.awt.*;

public class SigningPanel {

    private JPanel SigningPanel;
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton registerButton;
    private JButton cancelButton;

    public SigningPanel(JFrame parent){
        JDialog dialog = new JDialog(parent, true);
        dialog.setTitle("Sign In");
        dialog.setContentPane(SigningPanel);
        dialog.setMinimumSize(new Dimension(450,474));
        dialog.setModal(true);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
        cancelButton.setBounds(20,20,150,20);
    }
    public static void main(String[] args) {
        SigningPanel signingPanel = new SigningPanel(null);
//        JFrame frame = new JFrame("SigningPanel");
//        frame.setContentPane(new SigningPanel(frame).SigningPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
    }
}
