import java.util.Objects;

public class Book {
    private final long isbn;
    private final String title;
    private final String author;
    private String section;

    public Book(String sectionName, long isbn, String title, String author) {
        this.section = sectionName;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public long getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Double.compare(book.isbn, isbn) == 0 && title.equals(book.title) && author.equals(book.author) && section.equals(book.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, author, section);
    }

    @Override
    public String toString() {
        return title + " : " + author + "\tisbn: " + isbn;
    }
}
