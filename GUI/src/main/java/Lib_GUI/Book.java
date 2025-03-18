package Lib_GUI;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private String genre;
    private boolean available;

    public int getbookId() { return bookId; }
    public void setbookId(int id) { this.bookId = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}