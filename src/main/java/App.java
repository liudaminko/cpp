import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    private User curUser;
    private LogInManager logInManager;

    private Library library;
    boolean loggedIn;
    public App() {
        this.logInManager = new LogInManager();
        this.library = createLibrary();
    }
    private void showSortUI() {
        System.out.println("choose a parameter to sort books by:\n1.author's name\n2.title\n3.year\n4.cancel");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        SortManager sortManager = new SortManager();
        switch (input) {
            case "1":
                System.out.println("choose an order:\n1.ascending\n2.descending");
                String order = scanner.nextLine().trim();
                if (order.equals("1"))
                    sortManager.sortList(library.getBooks(),sortManager.authorComparator(),true).stream().forEach(System.out::println);
                else if (order.equals("2"))
                    sortManager.sortList(library.getBooks(),sortManager.authorComparator(),false).stream().forEach(System.out::println);
                else System.out.println("wrong input");
                break;
            case "2":
                System.out.println("choose an order:\n1.ascending\n2.descending");
                order = scanner.nextLine().trim();
                if (order.equals("1"))
                    sortManager.sortList(library.getBooks(),sortManager.titleComparator(),true).stream().forEach(System.out::println);
                else if (order.equals("2"))
                    sortManager.sortList(library.getBooks(),sortManager.titleComparator(),false).stream().forEach(System.out::println);
                else System.out.println("wrong input");
                break;
            case "3":
                System.out.println("choose an order:\n1.ascending\n2.descending");
                order = scanner.nextLine().trim();
                if (order.equals("1"))
                    sortManager.sortList(library.getBooks(),sortManager.yearComparator(),true).stream().forEach(System.out::println);
                else if (order.equals("2"))
                    sortManager.sortList(library.getBooks(),sortManager.yearComparator(),false).stream().forEach(System.out::println);
                else System.out.println("wrong input");
                break;
            case "4":
                return;
            default:
                System.out.println("wrong input");
                break;
        }
    }
    private void showSearchUI() {
        System.out.println("choose a parameter by which search for books by:\n1.author's name\n2.title\n3.year\n4.cancel");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        SearchManager searchManager = new SearchManager();
        switch (input) {
            case "1":
                System.out.println("enter author (last name and first name):");
                String author = scanner.nextLine().trim();
                searchManager.findBooksByAuthor(author, library).stream().forEach(System.out::println);
                break;
            case "2":
                System.out.println("enter title:");
                String title = scanner.nextLine().trim();
                searchManager.findBooksByTitle(title, library).stream().forEach(System.out::println);
                break;
            case "3":
                System.out.println("enter year of publication:");
                String year = scanner.nextLine().trim();
                searchManager.findBooksByYear(year, library).stream().forEach(System.out::println);
                break;
            case "4":
                return;
            default:
                System.out.println("wrong input");
                break;
        }
    }
    private void showReaderUI() {
        System.out.println("choose an action:\n1.sort books\n2.search for books\n3.borrow a book\n4.return a book\n5.log out");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        switch (input) {
            case "1":
                showSortUI();
                break;
            case "2":
                showSearchUI();
                break;
            case "3":
                System.out.println("enter title and author of the book you want to borrow using ',' as a delimiter:");
                String bookInfo = scanner.nextLine().trim();
                library.borrowBook(curUser, bookInfo);
                break;
            case "4":
                System.out.println("enter title and author of the book you want to return using ',' as a delimiter:");
                bookInfo = scanner.nextLine().trim();
                library.returnBook(curUser, bookInfo);
                break;
            case "5":
                System.out.println("you logged out");
                loggedIn = false;
                return;
            default:
                System.out.println("please choose an action:\n1.sort books\n2.search for books\n3.borrow a book\n4.return a book\n5.log out");
                break;
        }
    }

    private boolean showUserLogInUI() {
        Scanner scanner = new Scanner(System.in);
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.println("please enter your email:");
            String email = scanner.nextLine().trim();

            while (email.isEmpty()) {
                System.out.println("invalid input. please enter your email:");
                email = scanner.nextLine().trim();
            }
            System.out.println("please enter your password:");
            String password = scanner.nextLine().trim();
            while (password.isEmpty()) {
                System.out.println("invalid input. please enter your password:");
                password = scanner.nextLine().trim();
            }
            User authenticated = logInManager.logInUser(email, password, library);

            if (authenticated != null) {
                System.out.println("authentication successful. you are now logged in as a user.");
                curUser = authenticated;
                System.out.println(curUser.getNotifications());
                return true;
            } else {
                System.out.println("wrong email or password. please try again.");
            }

            if (attempt == 3) {
                System.out.println("maximum login attempts reached.");
                return false;
            }
        }
        return false;
    }
    private boolean showAdminLogInUI() {Scanner scanner = new Scanner(System.in);
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.println("please enter your email:");
            String email = scanner.nextLine().trim();

            while (email.isEmpty()) {
                System.out.println("invalid input. please enter your email:");
                email = scanner.nextLine().trim();
            }

            System.out.println("please enter your password:");
            String password = scanner.nextLine().trim();

            while (password.isEmpty()) {
                System.out.println("invalid input. please enter your password:");
                password = scanner.nextLine().trim();
            }


            boolean authenticated = logInManager.logInAdmin(email, password, library);

            if (authenticated) {
                System.out.println("authentication successful. you are now logged in as admin.");
                return true;
            } else {
                System.out.println("wrong email or password. please try again.");
            }

            if (attempt == 3) {
                System.out.println("maximum login attempts reached.");
                return false;
            }
        }
        return false;

    }
    private void showAdminUI() {
        System.out.println("choose an action:\n1.add book\n2.edit book\n3.delete book\n4.see amount of readers, that have books of a certain author\n5.find a user with the max amount of borrowed books\n6.send notifications about new books\n7.send notifications about returning the books\n8.send notifications about overdue books\n9.log out");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        switch (input) {
            case "1":
                System.out.println("enter title, author's name and year using ',' as a delimiter:");
                String bookInfo = scanner.nextLine().trim();
                library.addBook(bookInfo);
                break;
            case "2":
                showEditBookUI();
                break;
            case "3":
                System.out.println("enter title and author's name using ',' as a delimiter:");
                bookInfo = scanner.nextLine().trim();
                library.deleteBook(bookInfo);
                break;
            case "4":
                System.out.println("enter author' name:");
                String author = scanner.nextLine().trim();
                long userCount = library.countReadersByAuthor(author);
                System.out.println("amount of readers that have a book of " + author + " is " + userCount);
                break;
            case "5":
                Map.Entry<User, Long> maxBorrowedBooksUserInfo = library.findReaderWithMaxBorrowedBooks();
                if (maxBorrowedBooksUserInfo == null)
                    break;
                System.out.println("reader " + maxBorrowedBooksUserInfo.getKey().getFirstName() + " has " + maxBorrowedBooksUserInfo.getValue() + " borrowed books");
                break;
            case "6":
                library.sendNewBooksNotifications();
                break;
            case "7":
                library.sendReturnBooksNotifications();
                break;
            case "8":
                library.sendOverdueNotifications();
                break;
            case "9":
                System.out.println("you logged out");
                loggedIn = false;
                return;
            default:
                System.out.println("please choose an action:\n1.add book\n2.edit book\n3.delete book\n4.see amount of readers, that have a book of a certain author\n5.find a user with the max amount of borrowed books\n6.send notifications about new books\n7.send notifications about returning the books\n8.send notifications about overdue books\n9.log out");
                break;
        }
    }

    private void showEditBookUI() {
        System.out.println("enter title and author of the book using ',' as a delimiter");
        Scanner scanner = new Scanner(System.in);
        String bookInfo = scanner.nextLine().trim();
        if (!library.findBook(bookInfo)) {
            System.out.println("no books found");
            return;
        }

        System.out.println("choose a parameter to change:\n1.title\n2.author\n3.year of publication\n4.cancel edit");
        String input = scanner.nextLine().trim();
        switch (input) {
            case "1":
                System.out.println("enter new title");
                String title = scanner.nextLine().trim();
                library.editBookTitle(title, bookInfo);
                break;
            case "2":
                System.out.println("enter new author");
                String author = scanner.nextLine().trim();
                library.editBookAuthor(author, bookInfo);
                break;
            case "3":
                System.out.println("enter new year of publication");
                String year = scanner.nextLine().trim();
                library.editBookYear(year, bookInfo);
                break;
            case "4":
                return;
            default:
                System.out.println("wrong input");
                break;
        }
    }

    private void loop() {
        while (true) {
            System.out.println("choose an action:\n1.log in as reader\n2.log in as admin\n3.exit");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    loggedIn = showUserLogInUI();
                    while (loggedIn) {
                        showReaderUI();
                    }
                    break;
                case "2":
                    loggedIn = showAdminLogInUI();
                    while (loggedIn)
                        showAdminUI();
                    break;
                case "3":
                    System.out.println("closing the program");
                    return;
                default:
                    System.out.println("please enter a number between 1 and 3:\n1.log in as user\n2.log in as admin\n3.exit");
                    break;
            }
        }


    }

    private Library createLibrary() {
        Admin admin = DataDeserializer.deserializeAdmin("D:\\uni\\3\\cpp\\lab6\\admin.txt");

        if (admin != null) {
            return new Library(admin);
        } else {
            return null;
        }
    }
    public void start() {
        //greeting
        System.out.println("welcome to the RIDR");

        //load data from files
        List<Book> books = DataDeserializer.deserializeBooks("D:\\uni\\3\\cpp\\lab6\\books.txt");
        List<User> users = DataDeserializer.deserializeReaders("D:\\uni\\3\\cpp\\lab6\\readers.txt");

        library.addBooks(books);
        library.addReaders(users);
        //loop actions
        loop();
    }
}
