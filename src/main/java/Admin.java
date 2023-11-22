import java.io.Serializable;

public class Admin implements Serializable {
    private String email;
    private String password;

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public void recordBorrowing(User user, BorrowingRecord borrowingRecord){
        System.out.println("Recording borrowing event:");
        System.out.println("Reader: " + user.getFullName());
        System.out.println("Book: " + borrowingRecord.getBook().getTitle());
        System.out.println("Borrowing Date: " + borrowingRecord.getBorrowingDate());
        System.out.println("Planned Return Date: " + borrowingRecord.getPlannedReturnDate());

    }
    public void recordReturn(User user, BorrowingRecord borrowingRecord) {
        System.out.println("Recording return event:");
        System.out.println("Reader: " + user.getFullName());
        System.out.println("Book: " + borrowingRecord.getBook().getTitle());
        System.out.println("Actual Return Date: " + borrowingRecord.getActualReturnDate());
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
}
