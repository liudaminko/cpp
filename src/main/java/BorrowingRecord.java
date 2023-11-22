import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BorrowingRecord implements Serializable {
    private User user;
    private Book book;
    private LocalDateTime borrowingDate;
    private LocalDateTime plannedReturnDate;
    private LocalDateTime actualReturnDate;

    public BorrowingRecord(User user, Book book, LocalDateTime borrowingDate, LocalDateTime plannedReturnDate) {
        this.user = user;
        this.book = book;
        this.borrowingDate = borrowingDate;
        this.plannedReturnDate = plannedReturnDate;
    }

    public User getReader() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public LocalDateTime getBorrowingDate() {
        return borrowingDate;
    }

    public LocalDateTime getPlannedReturnDate() {
        return plannedReturnDate;
    }

    public LocalDateTime getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDateTime actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public void setBorrowingDate(LocalDateTime borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public void setPlannedReturnDate(LocalDateTime plannedReturnDate) {
        this.plannedReturnDate = plannedReturnDate;
    }
}
