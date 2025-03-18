package Lib_GUI;

import Lib_GUI.componets.BookTableModel;
import core.DAO.BookDAO;
import core.model.Book;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

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
    private final BookDAO bookDAO;

    public BookPanel(Connection connection) {
        this.bookDAO = new BookDAO(connection);
        initializeComponents();
        setupLayout();
        setupListeners();
        loadData();
    }

    private void initializeComponents() {
        BookPanel = new JPanel(new BorderLayout());
        titleField = new JTextField(20);
        authorField2 = new JTextField(20);
        genreField = new JTextField(20);
        SearchField = new JTextField(20);
        table = new JTable(new BookTableModel());
        deleteButton = new JButton("Delete");
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        Search = new JLabel("Search:");
        Title = new JLabel("Title:");
        Author = new JLabel("Author:");
        Genre = new JLabel("Genre:");
        title = new JLabel("Book Management");
        logo = new JLabel(new ImageIcon("logo.png"));
    }

    private void setupLayout() {
        BookPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel header = new JPanel(new BorderLayout());
        header.add(logo, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(Search, BorderLayout.WEST);
        searchPanel.add(SearchField, BorderLayout.CENTER);
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.add(Title);
        inputPanel.add(titleField);
        inputPanel.add(Author);
        inputPanel.add(authorField2);
        inputPanel.add(Genre);
        inputPanel.add(genreField);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        BookPanel.add(header, BorderLayout.NORTH);
        BookPanel.add(searchPanel, BorderLayout.PAGE_START);
        BookPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        BookPanel.add(inputPanel, BorderLayout.SOUTH);
        BookPanel.add(buttonPanel, BorderLayout.PAGE_END);
    }

    private void setupListeners() {
        addButton.addActionListener(e -> addBook());
        deleteButton.addActionListener(e -> deleteBook());
        cancelButton.addActionListener(e -> clearFields());
        SearchField.addActionListener(e -> searchBooks());
    }

    private void loadData() {
        try {
            ((BookTableModel) table.getModel()).setBooks(bookDAO.readAll());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(BookPanel, "Error: " + e.getMessage());
        }
    }

    private void addBook() {
        try {
            Book book = new Book();
            book.setTitle(titleField.getText());
            book.setAuthor(authorField2.getText());
            book.setGenre(genreField.getText());
            book.setAvailable(true);
            bookDAO.create(book);
            clearFields();
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(BookPanel, "Add error: " + e.getMessage());
        }
    }

    private void deleteBook() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            try {
                Book book = ((BookTableModel) table.getModel()).getBookAt(row);
                bookDAO.delete(book.getId());
                loadData();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(BookPanel, "Delete error: " + e.getMessage());
            }
        }
    }

    private void searchBooks() {
        try {
            ((BookTableModel) table.getModel()).setBooks(bookDAO.search(SearchField.getText()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(BookPanel, "Search error: " + e.getMessage());
        }
    }

    private void clearFields() {
        titleField.setText("");
        authorField2.setText("");
        genreField.setText("");
        SearchField.setText("");
        table.clearSelection();
    }

    public JPanel getBookPanel() {
        return BookPanel;
    }
}