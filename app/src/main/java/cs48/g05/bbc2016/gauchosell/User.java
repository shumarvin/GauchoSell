package cs48.g05.bbc2016.gauchosell;

/**
 * Created by dav on 4/17/16.
 */
public class User {
    private int birthYear;
    private int birthMonth;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    //TODO: Photo

    public User() {}

    public User(int birthYear, int birthMonth, String firstName, String lastName, String username, String email) {
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
    }


    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
