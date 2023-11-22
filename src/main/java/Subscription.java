public class Subscription {
    private String lastName;
    private String firstName;
    private String patronymic;
    private String email;

    public Subscription(String lastName, String firstName, String patronymic, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.email = email;
    }
    @Override
    public String toString() {
        return "Subscription{" + "lastName='" + lastName + '\'' + ", firstName='" + firstName + '\'' + ", patronymic='" + patronymic + '\'' + ", email='" + email + '\'' + '}';
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
}
