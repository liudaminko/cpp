import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortManager {
    public <T> List<T> sortList(List<T> list, Comparator<T> comparator, boolean ascending){
        return list.stream().sorted(ascending? comparator : comparator.reversed()).collect(Collectors.toList());
    }
    public Comparator<Book> authorComparator() {
        return Comparator.comparing(Book::getAuthor);
    }
    public Comparator<Book> titleComparator() {
        return Comparator.comparing(Book::getTitle);
    }
    public Comparator<Book> yearComparator() {
        return Comparator.comparingInt(Book::getYear);
    }
}
