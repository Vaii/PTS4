package models.util;

import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import javax.inject.Inject;
import java.io.File;
import org.apache.commons.mail.EmailAttachment;

public class MailService {
    private MailerClient mailerClient;

    public MailService(MailerClient mailerClient){
        this.mailerClient = mailerClient;
    }

    public void sendEmail() {
        String cid = "1234";
        Email email = new Email()
                .setSubject("Test")
                .setFrom("mpw.meijer@gmail.com")
                .addTo("max1_meijer@hotmail.com")
                // sends text, HTML or both...
                .setBodyText("A test email");
        mailerClient.send(email);
    }
}