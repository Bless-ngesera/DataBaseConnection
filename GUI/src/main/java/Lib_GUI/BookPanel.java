package Lib_GUI;

//import Lib_GUI.componets.BookTableModel;
//import core.DAO.BookDAO;
//import core.model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.sql.Connection;
//import java.sql.SQLException;

public class BookPanel {
    private JTextField titleField;
    private JTextField authorField2;
    private JTextField genreField;
    private JTextField SearchField;
    private JTable table;
    private JButton deleteButton;
    private JButton addButton;
    private JButton cancelButton;
    private JPanel BookPanel;
    private JLabel Search;
    private JLabel Title;
    private JLabel Author;
    private JLabel Genre;
    private JLabel title;
    private JLabel logo;
    public Book book;

    public BookPanel(JFrame parent){
        JDialog dialog = new JDialog(parent, "Book Management", true);
        dialog.setContentPane(BookPanel);
        dialog.setMinimumSize(new Dimension(900, 600));
        dialog.setModal(true);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.setVisible(true);
    }

    private void addBook() {
        JDialog dialog = new JDialog();
        String title = titleField.getText();
        String author = authorField2.getText();
        String genre = genreField.getText();
        String search = SearchField.getText();
        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || search.isEmpty()) {
            JOptionPane.showMessageDialog(BookPanel, "Please fill all fields",
                    "try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        book=addUserToDB(title, author, genre);

        if (book != null) {
            dialog.dispose();
        } else {
            JOptionPane.showMessageDialog(BookPanel, "Failed to add book",
                    "Try again", JOptionPane.ERROR_MESSAGE);
        }

    }

    private Book addUserToDB(String title, String author, String genre) {
        Book book= null;
        final String DB_URL = "jdbc:mysql://localhost:3306/library_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "2006";

        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO books (title, author, genre, availability) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, title);
                stmt.setString(2, author);
                stmt.setString(3, genre);
                stmt.setBoolean(4, true);
                stmt.executeUpdate();
            }
            // book = new Book(title, author, genre, true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(BookPanel, "Error: " + e.getMessage());
        }

        return book;
    }

    public static void main(String[] args) {
        new BookPanel(new JFrame());
    }



//    public BookPanel(Connection connection) {
//        this.bookDAO = new BookDAO(connection);
//        initializeComponents();
//        setupLayout();
//        setupListeners();
//        loadData();
//    }
//
//    private void initializeComponents() {
//        BookPanel = new JPanel(new BorderLayout());
//        titleField = new JTextField(20);
//        authorField2 = new JTextField(20);
//        genreField = new JTextField(20);
//        SearchField = new JTextField(20);
//        table = new JTable(new BookTableModel());
//        deleteButton = new JButton("Delete");
//        addButton = new JButton("Add");
//        cancelButton = new JButton("Cancel");
//        Search = new JLabel("Search:");
//        Title = new JLabel("Title:");
//        Author = new JLabel("Author:");
//        Genre = new JLabel("Genre:");
//        title = new JLabel("Book Management");
//        logo = new JLabel(new ImageIcon("logo.png"));
//    }
//
//    private void setupLayout() {
//        BookPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        JPanel header = new JPanel(new BorderLayout());
//        header.add(logo, BorderLayout.WEST);
//        header.add(title, BorderLayout.CENTER);
//        JPanel searchPanel = new JPanel(new BorderLayout());
//        searchPanel.add(Search, BorderLayout.WEST);
//        searchPanel.add(SearchField, BorderLayout.CENTER);
//        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
//        inputPanel.add(Title);
//        inputPanel.add(titleField);
//        inputPanel.add(Author);
//        inputPanel.add(authorField2);
//        inputPanel.add(Genre);
//        inputPanel.add(genreField);
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        buttonPanel.add(addButton);
//        buttonPanel.add(deleteButton);
//        buttonPanel.add(cancelButton);
//        BookPanel.add(header, BorderLayout.NORTH);
//        BookPanel.add(searchPanel, BorderLayout.PAGE_START);
//        BookPanel.add(new JScrollPane(table), BorderLayout.CENTER);
//        BookPanel.add(inputPanel, BorderLayout.SOUTH);
//        BookPanel.add(buttonPanel, BorderLayout.PAGE_END);
//    }
//
//    private void setupListeners() {
//        addButton.addActionListener(e -> addBook());
//        deleteButton.addActionListener(e -> deleteBook());
//        cancelButton.addActionListener(e -> clearFields());
//        SearchField.addActionListener(e -> searchBooks());
//    }
//
//    private void loadData() {
//        try {
//            ((BookTableModel) table.getModel()).setBooks(bookDAO.readAll());
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(BookPanel, "Error: " + e.getMessage());
//        }
//    }
//
//    private void addBook() {
//        try {
//            Book book = new Book();
//            book.setTitle(titleField.getText());
//            book.setAuthor(authorField2.getText());
//            book.setGenre(genreField.getText());
//            book.setAvailable(true);
//            bookDAO.create(book);
//            clearFields();
//            loadData();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(BookPanel, "Add error: " + e.getMessage());
//        }
//    }
//
//    private void deleteBook() {
//        int row = table.getSelectedRow();
//        if (row >= 0) {
//            try {
//                Book book = ((BookTableModel) table.getModel()).getBookAt(row);
//                bookDAO.delete(book.getId());
//                loadData();
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(BookPanel, "Delete error: " + e.getMessage());
//            }
//        }
//    }
//
//    private void searchBooks() {
//        try {
//            ((BookTableModel) table.getModel()).setBooks(bookDAO.search(SearchField.getText()));
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(BookPanel, "Search error: " + e.getMessage());
//        }
//    }
//
//    private void clearFields() {
//        titleField.setText("");
//        authorField2.setText("");
//        genreField.setText("");
//        SearchField.setText("");
//        table.clearSelection();
//    }
//
//    public JPanel getBookPanel() {
//        return BookPanel;
//    }
}