package domain;

import java.time.LocalDate;

public class Delivery {
    private Long id;
    private Book book;
    private AppUser user;
    private LocalDate dateInput;
    private LocalDate dateOutput;

    public Delivery() {}

    public Delivery(Long id, Book book, AppUser user, LocalDate dateInput, LocalDate dateOutput) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.dateInput = dateInput;
        this.dateOutput = dateOutput;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }

    public LocalDate getDateInput() { return dateInput; }
    public void setDateInput(LocalDate dateInput) { this.dateInput = dateInput; }

    public LocalDate getDateOutput() { return dateOutput; }
    public void setDateOutput(LocalDate dateOutput) { this.dateOutput = dateOutput; }
}