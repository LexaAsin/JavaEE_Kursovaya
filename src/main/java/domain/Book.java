package domain;

public class Book {
    private Long id;
    private String title;
    private String binding;
    private String publisher;
    private Integer year;
    private String genre;
    private Long authorId;
    private Author author;

    public Book() {}

    public Book(Long id, String title, String binding, String publisher, Integer year, String genre, Long authorId, Author author) {
        this.id = id;
        this.title = title;
        this.binding = binding;
        this.publisher = publisher;
        this.year = year;
        this.genre = genre;
        this.authorId = authorId;
        this.author = author;
    }

    public Book(String title, String binding, String publisher, Integer year, String genre, Author author) {
        this.title = title;
        this.binding = binding;
        this.publisher = publisher;
        this.year = year;
        this.genre = genre;
        this.author = author;
        if (author != null) {
            this.authorId = author.getId();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBinding() { return binding; }
    public void setBinding(String binding) { this.binding = binding; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public String getAuthorName() {
        return author != null ? author.getFullName() : null;
    }
}