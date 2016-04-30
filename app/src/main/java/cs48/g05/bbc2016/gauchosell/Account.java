package cs48.g05.bbc2016.gauchosell;

/**
 * Created by icema_000 on 4/29/2016.
 */
public class Account {
    private int birthYear;
    private int birthMonth;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private EmbeddedImage image;
    private char[] password;

    public Account(int birthYear, int birthMonth, String firstName, String lastName, String username, String email, EmbeddedImage image, char[] password) {
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.image = image;
        this.password = password;
    }

    public int getBirthYear() { return birthYear; }

    public void setBirthYear(int birthYear) { this.birthYear = birthYear; }

    public int getBirthMonth() { return birthMonth; }

    public void setBirthMonth(int birthMonth) { this.birthMonth = birthMonth; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public EmbeddedImage getImage() { return image; }

    public void setImage(EmbeddedImage image) { this.image = image; }

    public char[] getPassword() { return password; }

    public void setPassword(char[] password) { this.password = password; }
}
