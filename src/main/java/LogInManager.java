public class LogInManager {
    public User logInUser(String email, String password, Library library) {
        return library.getReaders()
                .stream()
                .filter(reader -> reader.getEmail().equals(email) && reader.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
    public Boolean logInAdmin(String email, String password, Library library) {
        return library.getAdmin().getEmail().equals(email) && library.getAdmin().getPassword().equals(password);
    }
}
