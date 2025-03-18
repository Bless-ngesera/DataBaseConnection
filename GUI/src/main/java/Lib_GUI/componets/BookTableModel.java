package Lib_GUI.componets;

import Library.model.Book;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BookTableModel extends AbstractTableModel {
    private final String[] COLUMNS = {"ID", "Title", "Author", "Genre", "Available"};
    private List<Book> books;

    public void setBooks(List<Book> books) {
        this.books = books;
        fireTableDataChanged();
    }

    public Book getBookAt(int row) {
        return books.get(row);
    }

    @Override public int getRowCount() { return books != null ? books.size() : 0; }
    @Override public int getColumnCount() { return COLUMNS.length; }
    @Override public String getColumnName(int col) { return COLUMNS[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        Book book = books.get(row);
        return switch (col) {
            case 0 -> book.getId();
            case 1 -> book.getTitle();
            case 2 -> book.getAuthor();
            case 3 -> book.getGenre();
            case 4 -> book.isAvailable() ? "Yes" : "No";
            default -> null;
        };
    }
}