package cs48.g05.bbc2016.gauchosell.user;

import java.util.Date;

import cs48.g05.bbc2016.gauchosell.util.EmbeddedImage;

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
    //private EmbeddedImage image;
    private long timeJoined;

    //http://stackoverflow.com/questions/7625783/jsonmappingexception-no-suitable-constructor-found-for-type-simple-type-class
    //We need this so that firebase is happy with construction an Account
    public Account() { } //dummy constructor

    public Account(int birthMonth, int birthYear, String email, String firstName, String lastName, long timeJoined, String username) {
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeJoined = timeJoined;
        this.username = username;
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

//    public EmbeddedImage getImage() { return image; }
//
//    public void setImage(EmbeddedImage image) { this.image = image; }

    public long getTimeJoined() { return timeJoined; }

    public void setTimeJoined(long timeJoined) { this.timeJoined = timeJoined; }
}
