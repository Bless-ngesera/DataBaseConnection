package Lib_GUI;

import Library.DatabaseAccessObject.BookDAO;
import Library.model.Book;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookPanel extends JPanel {
    private final BookDAO bookDAO;
    private final JTable table;
    private final JTextField titleField, authorField, genreField, searchField;
    private Book selectedBook;

    public BookPanel(Connection connection) {
        this.bookDAO = new BookDAO(connection);

        titleField = new JTextField();
        authorField = new JTextField();
        genreField = new JTextField();
        searchField = new JTextField();
        table = new JTable(new BookTableModel());

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(createSearchPanel(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(createInputPanel(), BorderLayout.SOUTH);
        add(createControlPanel(), BorderLayout.PAGE_END);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> handleSelection());

        refreshTable();
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Book Details"));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("Genre:"));
        panel.add(genreField);
        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.add(createButton("Add", this::addBook));
        panel.add(createButton("Update", this::updateBook));
        panel.add(createButton("Delete", this::deleteBook));
        panel.add(createButton("Clear", this::clearFields));
        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Search"));
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchBooks());
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.EAST);
        return panel;
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        return button;
    }

    private void handleSelection() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            selectedBook = ((BookTableModel) table.getModel()).getBookAt(row);
            titleField.setText(selectedBook.getTitle());
            authorField.setText(selectedBook.getAuthor());
            genreField.setText(selectedBook.getGenre());
        }
    }

    private void refreshTable() {
        try {
            ((BookTableModel) table.getModel()).setBooks(bookDAO.readAllBooks());
        } catch (SQLException e) {
            showError("Error loading books: " + e.getMessage());
        }
    }

    private void searchBooks() {
        try {
            List<Book> results = bookDAO.searchBooks(searchField.getText().trim());
            ((BookTableModel) table.getModel()).setBooks(results);
        } catch (SQLException e) {
            showError("Search error: " + e.getMessage());
        }
    }

    private void addBook() {
        if (!validateInput()) return;

        try {
            Book newBook = new Book();
            newBook.setTitle(titleField.getText().trim());
            newBook.setAuthor(authorField.getText().trim());
            newBook.setGenre(genreField.getText().trim());
            newBook.setAvailable(true);

            bookDAO.createBook(newBook);
            clearFields();
            refreshTable();
        } catch (SQLException e) {
            showError("Add failed: " + e.getMessage());
        }
    }

    private void updateBook() {
        if (selectedBook == null) {
            showError("No book selected!");
            return;
        }

        if (!validateInput()) return;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Update this book?",
                "Confirm Update",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Book updatedBook = new Book();
                updatedBook.setId(selectedBook.getId());
                updatedBook.setTitle(titleField.getText().trim());
                updatedBook.setAuthor(authorField.getText().trim());
                updatedBook.setGenre(genreField.getText().trim());
                updatedBook.setAvailable(selectedBook.isAvailable());

                bookDAO.updateBook(updatedBook);
                clearFields();
                refreshTable();
            } catch (SQLException e) {
                showError("Update failed: " + e.getMessage());
            }
        }
    }

    private void deleteBook() {
        if (selectedBook == null) {
            showError("No book selected!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete '" + selectedBook.getTitle() + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                bookDAO.deleteBook(selectedBook.getId());
                clearFields();
                refreshTable();
            } catch (SQLException e) {
                showError("Delete failed: " + e.getMessage());
            }
        }
    }

    private boolean validateInput() {
        if (titleField.getText().trim().isEmpty()) {
            showError("Title is required!");
            return false;
        }
        if (authorField.getText().trim().isEmpty()) {
            showError("Author is required!");
            return false;
        }
        return true;
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        genreField.setText("");
        searchField.setText("");
        table.clearSelection();
        selectedBook = null;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private class BookTableModel extends AbstractTableModel {
        private final String[] COLUMNS = {"ID", "Title", "Author", "Genre", "Available"};
        private List<Book> books = new ArrayList<>();

        public void setBooks(List<Book> books) {
            this.books = books;
            fireTableDataChanged();
        }

        public Book getBookAt(int row) {
            return books.get(row);
        }

        @Override
        public int getRowCount() {
            return books.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int row, int column) {
            Book book = books.get(row);
            return switch (column) {
                case 0 -> book.getId();
                case 1 -> book.getTitle();
                case 2 -> book.getAuthor();
                case 3 -> book.getGenre();
                case 4 -> book.isAvailable() ? "Yes" : "No";
                default -> null;
            };
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }
    }
}