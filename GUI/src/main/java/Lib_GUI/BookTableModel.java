//package Lib_GUI.componets;
//
//
//import core.model.Book;
//
//import javax.swing.table.AbstractTableModel;
//import java.util.List;
//
//public class BookTableModel extends AbstractTableModel {
//    private final String[] COLUMNS = {"ID", "Title", "Author", "Genre", "Available"};
//    private List<Book> books;
//
//    public void setBooks(List<Book> books) {
//        this.books = books;
//        fireTableDataChanged();
//    }
//
//    public Book getBookAt(int row) {
//        return books.get(row);
//    }
//
//    @Override public int getRowCount() { return books.size(); }
//    @Override public int getColumnCount() { return COLUMNS.length; }
//    @Override public String getColumnName(int column) { return COLUMNS[column]; }
//
//    @Override
//    public Object getValueAt(int row, int column) {
//        Book book = books.get(row);
//        switch (column) {
//            case 0: return book.getId();
//            case 1: return book.getTitle();
//            case 2: return book.getAuthor();
//            case 3: return book.getGenre();
//            case 4: return book.isAvailable() ? "Yes" : "No";
//            default: return null;
//        }
//    }
//}