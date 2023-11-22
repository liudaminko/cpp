import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String lastName;
    private String firstName;
    private String patronymic;
    private String email;
    private String password;
    private List<Book> borrowedBooks;
    private List<String> notifications;

    public User() {
        this.borrowedBooks = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public User(String lastName, String firstName, String patronymic, String email, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
        this.borrowedBooks = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
    public void addNotification(String notification) {
        notifications.add(notification);
    }
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    public List<String> getNotifications() {
        return notifications;
    }
    public String getFullName() {
        return getLastName() + " " + getFirstName() + " " + getPatronymic();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }
}
