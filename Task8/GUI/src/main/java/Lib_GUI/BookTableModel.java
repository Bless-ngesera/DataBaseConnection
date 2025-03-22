package Lib_GUI;

import Lib_GUI.Book;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class BookTableModel extends AbstractTableModel {
    private List<Book> books;
    private String[] columnNames = {"Book ID", "Title", "Author", "Genre", "Availability"};

    public BookTableModel() {
        this.books = new ArrayList<>();
    }

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
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book book = books.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return book.getbookId();
            case 1:
                return book.getTitle();
            case 2:
                return book.getAuthor();
            case 3:
                return book.getGenre();
            case 4:
                return book.isAvailable() ? "Available" : "Not Available";
            default:
                return null;
        }
    }
}