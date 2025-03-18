//package core.DAO;
//
//import core.model.Book;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class BookDAO {
//    private final Connection connection;
//
//    public BookDAO(Connection connection) {
//        this.connection = connection;
//    }
//
//    public void create(Book book) throws SQLException {
//        String sql = "INSERT INTO books (title, author, genre, availability) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, book.getTitle());
//            stmt.setString(2, book.getAuthor());
//            stmt.setString(3, book.getGenre());
//            stmt.setBoolean(4, book.isAvailable());
//            stmt.executeUpdate();
//        }
//    }
//
//    public List<Book> readAll() throws SQLException {
//        List<Book> books = new ArrayList<>();
//        String sql = "SELECT * FROM books";
//        try (Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                books.add(mapResultSetToBook(rs));
//            }
//        }
//        return books;
//    }
//
//    public void delete(int id) throws SQLException {
//        String sql = "DELETE FROM books WHERE bookId = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            stmt.executeUpdate();
//        }
//    }
//
//    public List<Book> search(String query) throws SQLException {
//        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR genre LIKE ?";
//        List<Book> results = new ArrayList<>();
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            String searchTerm = "%" + query + "%";
//            stmt.setString(1, searchTerm);
//            stmt.setString(2, searchTerm);
//            stmt.setString(3, searchTerm);
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    results.add(mapResultSetToBook(rs));
//                }
//            }
//        }
//        return results;
//    }
//
//    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
//        Book book = new Book();
//        book.setId(rs.getInt("bookId"));
//        book.setTitle(rs.getString("title"));
//        book.setAuthor(rs.getString("author"));
//        book.setGenre(rs.getString("genre"));
//        book.setAvailable(rs.getBoolean("availability"));
//        return book;
//    }
//}
