package Lib_GUI.GUI;

import javax.swing.*;
import java.awt.*;

public class MemberPanel {
    private JPanel MemberPanel;
    private JTextField nameF;
    private JTextField emailF;
    private JTextField phoneF;
    private JTextField addressF;
    private JPasswordField passwordF;
    private JPasswordField CpasswordF;
    private JButton registerButton;
    private JButton nextButton;
    private JButton backButton;


    public MemberPanel(JFrame parent){
        JDialog dialog=new JDialog(parent,true);
        dialog.setTitle("Create a new account");
        dialog.setContentPane(MemberPanel);
        dialog.setMinimumSize(new Dimension(450,674));
        dialog.setModal(true);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
}
