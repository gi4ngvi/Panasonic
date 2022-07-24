package isobar.panasonic.utility;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendMail {
    public void sendGMail(String from, String pass, String to, String cc, String subject, String body, String pathAttachedFile) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.tls.enable", "true");
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        //Session session = Session.getDefaultInstance(props);
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            // override the getPasswordAuthentication
            // method
            protected PasswordAuthentication
            getPasswordAuthentication() {
                return new PasswordAuthentication(from,
                        pass);
            }
        });

        MimeMessage message = new MimeMessage(session);
        try {
            //Set from address
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            if (cc != null) {
                String[] str = cc.split(";");
                for (String strCC : str) {
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(strCC));
                }
            }
            //Set subject
            message.setSubject(subject);
            message.setText(body);
            BodyPart objMessageBodyPart = new MimeBodyPart();
            objMessageBodyPart.setText("Please Find The Attached Report File!");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(objMessageBodyPart);
            objMessageBodyPart = new MimeBodyPart();
            //Create data source to attach the file in mail
            DataSource source = new FileDataSource(pathAttachedFile);
            objMessageBodyPart.setDataHandler(new DataHandler(source));
            objMessageBodyPart.setFileName(pathAttachedFile);
            multipart.addBodyPart(objMessageBodyPart);
            message.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
