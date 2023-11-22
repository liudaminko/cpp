import java.util.List;
import java.util.stream.Collectors;

public class SearchManager {
    public List<Book> findBooksByAuthor(String author, Library library) {
        return library.getBooks()
                .stream()
                .filter(book -> book.getAuthor().equals(author))
                .collect(Collectors.toList());
    }
    public List<Book> findBooksByTitle(String title, Library library) {
        return library.getBooks()
                .stream()
                .filter(book -> book.getTitle().equals(title))
                .collect(Collectors.toList());
    }
    public List<Book> findBooksByYear(String year, Library library) {
        return library.getBooks()
                .stream()
                .filter(book -> book.getYear()==Integer.valueOf(year))
                .collect(Collectors.toList());
    }
}
