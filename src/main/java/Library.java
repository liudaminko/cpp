import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Library implements Serializable {
    private List<Book> books;
    private List<Subscription> subscriptions;
    private List<User> users;
    private Admin admin;
    private List<BorrowingRecord> borrowingRecords;

    public Library(Admin admin) {
        this.books = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
        this.users = new ArrayList<>();
        this.admin = admin;
        this.borrowingRecords = new ArrayList<>();
    }

    public List<User> getReadersWithMoreThanTwoBooks() {
        return users.stream()
                .filter(reader -> reader.getBorrowedBooks().size() > 2)
                .collect(Collectors.toList());
    }

    public long countReadersByAuthor(String author) {
        return users.stream()
                .filter(reader -> reader.getBorrowedBooks().stream()
                        .anyMatch(book -> book.getAuthor().equals(author)))
                .count();
    }
    public Map.Entry<User, Long> findReaderWithMaxBorrowedBooks() {
        return users.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        reader -> (long) reader.getBorrowedBooks().size()
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
    }
    public void borrowBook(User user, String bookInfo) {
        Book bookToBorrow = getBookByTitleAndAuthor(bookInfo);

        LocalDateTime borrowingDate = LocalDateTime.now();
        LocalDateTime plannedReturnDate = borrowingDate.plusMinutes(2);

        BorrowingRecord borrowingRecord = new BorrowingRecord(user, bookToBorrow, borrowingDate, plannedReturnDate);
        admin.recordBorrowing(user, borrowingRecord);

        user.borrowBook(bookToBorrow);
        borrowingRecords.add(borrowingRecord);
    }

    public void returnBook(User user, String bookInfo) {
        String[] bookTitleAuthor = bookInfo.split(",");
        Book bookToReturn = user.getBorrowedBooks()
                .stream()
                .filter(book -> book.getTitle().equals(bookTitleAuthor[0]) && book.getAuthor().equals(bookTitleAuthor[1]))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("such book doesn't exist"));

        BorrowingRecord borrowingRecord = borrowingRecords.stream()
                .filter(record -> record.getReader().equals(user) && record.getBook().equals(bookToReturn) && record.getActualReturnDate() == null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("corresponding borrowing record not found"));

        borrowingRecord.setActualReturnDate(LocalDateTime.now());
        admin.recordReturn(user, borrowingRecord);

        user.returnBook(bookToReturn);
    }
    private void notifyObservers(List<User> users, Observer observer) {
        observer.notify(users);
    }
    public void addBook(String bookInfo) {
        String[] book = bookInfo.split(",");
        books.add(new Book(book[0], book[1], Integer.valueOf(book[2])));
    }
    public void editBookTitle(String newTitle, String bookInfo) {
        getBookByTitleAndAuthor(bookInfo).setTitle(newTitle);
    }
    public void editBookAuthor(String newAuthor, String bookInfo) {
        getBookByTitleAndAuthor(bookInfo).setAuthor(newAuthor);
    }
    public void editBookYear(String newYear, String bookInfo) {
        getBookByTitleAndAuthor(bookInfo).setYear(Integer.valueOf(newYear));

    }
    private Book getBookByTitleAndAuthor(String bookInfo) {
        String[] bookToGetInfo = bookInfo.split(",");
        return books.stream()
                .filter(book -> book.getTitle().equals(bookToGetInfo[0]) && book.getAuthor().equals(bookToGetInfo[1]))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("book not found"));
    }
    public void deleteBook(String bookInfo) {
        Book bookToDelete = getBookByTitleAndAuthor(bookInfo);
        books.remove(bookToDelete);
    }
    public void addBooks(List<Book> books) {
        this.books = books;
    }
    public void addReaders(List<User> users) {
        this.users = users;
    }
    public void sendNewBooksNotifications() {
        List<User> group = users.stream()
                .filter(reader -> !getReadersWithMoreThanTwoBooks().contains(reader))
                .collect(Collectors.toList());

        int totalBooks = books.size();
        int startIndex = Math.max(0, totalBooks - 5);
        List<Book> newBooks = new ArrayList<>(books.subList(startIndex, totalBooks));

        notifyObservers(group, new NewReleasesObserver(newBooks));
    }
    public boolean findBook(String input) {
        String[] bookInfo = input.split(",");
        return books.stream()
                .anyMatch(book -> book.getTitle().equals(bookInfo[0]) && book.getAuthor().equals(bookInfo[1]));
    }
    public void sendReturnBooksNotifications() {
        List<User> group = getReadersWithMoreThanTwoBooks();
        notifyObservers(group, new ReturnReminderObserver(this));
    }
    public void sendOverdueNotifications() {
        LocalDateTime currentDate = LocalDateTime.now();

        List<User> overdueUsers = borrowingRecords.stream()
                .filter(record -> currentDate.isAfter(record.getPlannedReturnDate()) && (record.getActualReturnDate() == null || currentDate.isAfter(record.getActualReturnDate()))                )
                .map(BorrowingRecord::getReader)
                .distinct()
                .collect(Collectors.toList());

        notifyObservers(overdueUsers, new OverdueReminderObserver(this));
    }
    public Map<String, Long> getOverdueInfoForReader(User user) {
        LocalDateTime currentDate = LocalDateTime.now();

        return borrowingRecords.stream()
                .filter(record -> record.getReader().equals(user))
                .filter(record -> record.getActualReturnDate() == null || record.getActualReturnDate().isAfter(record.getPlannedReturnDate()))
                .filter(record -> currentDate.isAfter(record.getPlannedReturnDate()))
                .collect(Collectors.toMap(
                        record -> record.getBook().getTitle(),
                        record -> {
                            LocalDateTime returnDate = record.getActualReturnDate() != null ?
                                    record.getActualReturnDate() : currentDate;
                            return ChronoUnit.DAYS.between(record.getPlannedReturnDate(), returnDate);
                        }
                ));
    }
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
    public List<User> getReaders() {
        return users;
    }

    public void setReaders(List<User> users) {
        this.users = users;
    }
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    public List<BorrowingRecord> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void setBorrowingRecords(List<BorrowingRecord> borrowingRecords) {
        this.borrowingRecords = borrowingRecords;
    }
}
