package GUI;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
    private final String emailUsername;
    private final String emailPassword;

    public MyAuthenticator(String emailUsername, String emailPassword) {
        this.emailUsername = emailUsername;
        this.emailPassword = emailPassword;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(emailUsername, emailPassword);
    }
}