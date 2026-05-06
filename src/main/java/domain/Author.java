package domain;

public class Author {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private Double rating;

    public Author() {}

    public Author(Long id, String fullName, String phone, String email, Double rating) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.rating = rating;
    }

    public Author(String fullName, String phone, String email, Double rating) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.rating = rating;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    @Override
    public String toString() {
        return fullName;
    }
}