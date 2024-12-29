package src.main.java.clases;

public class Client {

    private String firstname;
    private String lastname;
    private String dateOfBirth;
    private String email;
    private String nationality;
    private String phoneNumber;

    public Client(String firstname, String lastname, String dateOfBirth, String email, String nationality, String phoneNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.nationality = nationality;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
