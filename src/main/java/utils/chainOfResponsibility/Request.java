package src.main.java.utils.chainOfResponsibility;

public class Request {

    private String email;
    private String dateOfBith;

    public Request(String email, String dateOfBith){
        this.email = email;
        this.dateOfBith = dateOfBith;
    }

    public String getEmail() {
        return email;
    }

    public String getDateOfBith() {
        return dateOfBith;
    }

}
