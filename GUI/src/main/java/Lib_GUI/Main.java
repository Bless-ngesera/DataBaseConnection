//package Lib_GUI;
//
//import core.util.DBUtil;
//
//import javax.swing.*;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class Main {
//    public static void main(String[] args) {
//        try {
//            Connection connection = DBUtil.getConnection();
//            SwingUtilities.invokeLater(() -> {
//                JFrame frame = new JFrame("Library Management System");
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setSize(800, 600);
//
//                BookPanel bookPanel = new BookPanel(connection);
//                frame.add(bookPanel.getBookPanel());
//
//                frame.setLocationRelativeTo(null);
//                frame.setVisible(true);
//            });
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null,
//                    "Database connection failed: " + e.getMessage(),
//                    "Error",
//                    JOptionPane.ERROR_MESSAGE);
//        }
//    }
//}