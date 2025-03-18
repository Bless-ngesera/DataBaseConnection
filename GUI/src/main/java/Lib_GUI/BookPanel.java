package Lib_GUI;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookPanel {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField SearchField;
    private JTable table;
    private JButton deleteButton;
    private JButton addButton;
    private JButton cancelButton;
    private JButton searchButton;
    private JPanel BookPanel;
    private JLabel Search;
    private JLabel Title;
    private JLabel Author;
    private JLabel Genre;
    private JLabel title;
    private JLabel logo;
    private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;
    private BookTableModel tableModel;

    public BookPanel(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Book Management", true);
        dialog.setContentPane(BookPanel);
        dialog.setMinimumSize(new Dimension(900, 600));
        dialog.setModal(true);
        dialog.setLocationRelativeTo(parent);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Group the radio buttons
        ButtonGroup availabilityGroup = new ButtonGroup();
        availabilityGroup.add(yesRadioButton);
        availabilityGroup.add(noRadioButton);
        yesRadioButton.setSelected(true); // Default selection

        tableModel = new BookTableModel();
        table.setModel(tableModel);

        loadBooks();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBooks();
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

    private void loadBooks() {
        List<Book> books = fetchBooksFromDB();
        tableModel.setBooks(books);
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();
        boolean availability = yesRadioButton.isSelected(); // Get availability from radio button

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
            JOptionPane.showMessageDialog(BookPanel, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book book = addBookToDB(title, author, genre, availability);

        if (book != null) {
            JOptionPane.showMessageDialog(BookPanel, "Book added successfully: " + book.getTitle(), "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            loadBooks();
        } else {
            JOptionPane.showMessageDialog(BookPanel, "Failed to add book", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(BookPanel, "Please select a book to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int bookId = tableModel.getBookAt(selectedRow).getbookId();
        boolean deleted = deleteBookFromDB(bookId);

        if (deleted) {
            JOptionPane.showMessageDialog(BookPanel, "Book deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadBooks();
        } else {
            JOptionPane.showMessageDialog(BookPanel, "Failed to delete book", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchBooks() {
        String searchTerm = SearchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadBooks();
            return;
        }

        List<Book> books = searchBooksInDB(searchTerm);
        tableModel.setBooks(books);
    }

    private Book addBookToDB(String title, String author, String genre, boolean availability) {
        Book book = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/library_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "2006";

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO books (title, author, genre, availability) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, title);
                stmt.setString(2, author);
                stmt.setString(3, genre);
                stmt.setBoolean(4, availability);

                int addedRows = stmt.executeUpdate();
                if (addedRows > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        book = new Book();
                        book.setbookId(rs.getInt(1));
                        book.setTitle(title);
                        book.setAuthor(author);
                        book.setGenre(genre);
                        book.setAvailable(availability);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    private boolean deleteBookFromDB(int bookId) {
        final String DB_URL = "jdbc:mysql://localhost:3306/library_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "2006";

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "DELETE FROM books WHERE bookId = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, bookId);
                int deletedRows = stmt.executeUpdate();
                return deletedRows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private List<Book> fetchBooksFromDB() {
        List<Book> books = new ArrayList<>();
        final String DB_URL = "jdbc:mysql://localhost:3306/library_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "2006";

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM books";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Book book = new Book();
                    book.setbookId(rs.getInt("bookId"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setGenre(rs.getString("genre"));
                    book.setAvailable(rs.getBoolean("availability"));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    private List<Book> searchBooksInDB(String searchTerm) {
        List<Book> books = new ArrayList<>();
        final String DB_URL = "jdbc:mysql://localhost:3306/library_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "2006";

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR genre LIKE ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, "%" + searchTerm + "%");
                stmt.setString(2, "%" + searchTerm + "%");
                stmt.setString(3, "%" + searchTerm + "%");

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Book book = new Book();
                    book.setbookId(rs.getInt("bookId"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setGenre(rs.getString("genre"));
                    book.setAvailable(rs.getBoolean("availability"));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        genreField.setText("");
        yesRadioButton.setSelected(true); // Reset radio button to default
    }

}