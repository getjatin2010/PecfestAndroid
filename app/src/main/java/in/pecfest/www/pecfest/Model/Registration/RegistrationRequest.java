package in.pecfest.www.pecfest.Model.Registration;

/**
 * Created by jatin on 20/8/16.
 */
public class RegistrationRequest {
    CharSequence name;
    CharSequence college;
    CharSequence email;
    CharSequence phone;



    public RegistrationRequest(CharSequence college, CharSequence name, CharSequence email, CharSequence phone) {
        this.college = college;
        this.name = name;
        this.email = email;
        this.phone = phone;

    }
}
