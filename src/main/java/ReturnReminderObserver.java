import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

class ReturnReminderObserver implements Observer {
    private Library library;

    public ReturnReminderObserver(Library library) {
        this.library = library;
    }
    @Override
    public void notify(List<User> users) {
        for (User user : users) {
            List<String> soonToReturnBooks = getSoonToReturnBooks(user);
            user.addNotification("return reminder notification. books that need to be returned soon: " + String.join(", ", soonToReturnBooks));
        }
    }

    private List<String> getSoonToReturnBooks(User user) {
        LocalDateTime currentDate = LocalDateTime.now();

        return library.getBorrowingRecords().stream()
                .filter(record -> record.getReader().equals(user))
                .filter(record -> currentDate.isBefore(record.getPlannedReturnDate()))
                .map(record -> record.getBook().getTitle())
                .collect(Collectors.toList());
    }
}