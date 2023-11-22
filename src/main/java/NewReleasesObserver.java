import java.util.List;
import java.util.stream.Collectors;

public class NewReleasesObserver implements Observer{
    private List<Book> newBooks;
    public NewReleasesObserver(List<Book> newBooks) {
        this.newBooks = newBooks;
    }
    @Override
    public void notify(List<User> users) {
        for (User user : users) {
            List<String> bookNames = newBooks.stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());

            user.addNotification("new releases notification. new books: " + String.join(", ", bookNames));
        }
    }
}
