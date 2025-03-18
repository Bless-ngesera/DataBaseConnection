package Lib_GUI;

import Library.Util.DatabaseUtil;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseUtil.getConnection();
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Library Management System");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);

                // Now correctly instantiating BookPanel
                frame.add(new BookPanel(connection));

                frame.setVisible(true);
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Database connection failed: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}