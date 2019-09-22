package net.streets.utilities.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;

import static net.streets.persistence.enumeration.StrConfig.*;
import static net.streets.persistence.helper.DaoManager.getStrConfigDao;

public class EMailer implements Runnable {

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    public static final String DEFAULT_CONTENT_TYPE = "text/plain";
    public static final String CONTENT_TYPE_HTML = "text/html";
    private String recipients[];
    private String subject;
    private String message;
    private String contentType;
    private String imageLocation;
    private String contentID;
    private boolean isMultipart = false;
    private String attachmentFilenames[] = null;
    private MimeMultipart multipart;

    public EMailer(String recipients[], String subject, String message, String contentType) {
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
        this.contentType = contentType;
    }
    
    public EMailer(String recipients[], String subject, String message, String contentType, String imageLocation, String contentID, String attachmentFilenames[]) {
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
        this.contentType = contentType;
        this.imageLocation = imageLocation;
        this.contentID = contentID;
        this.attachmentFilenames = attachmentFilenames;
        isMultipart = true;
    }

    public EMailer(String recipients[], String subject, String message, String contentType, String imageLocation, String contentID) {
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
        this.contentType = contentType;
        this.imageLocation = imageLocation;
        this.contentID = contentID;
        isMultipart = true;
    }

    public EMailer(String recipients[], String subject, MimeMultipart multipart) {
        this.recipients = recipients;
        this.subject = subject;
        this.multipart = multipart;
        isMultipart = true;
    }

    public EMailer(String recipients[], String subject, String message) {
        this(recipients, subject, message, DEFAULT_CONTENT_TYPE);
    }

    public EMailer(String recipients[], String subject, String message, String contentType, String imageLocation) {
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
        this.contentType = contentType;
        this.imageLocation = imageLocation;
        isMultipart = true;
    }

    //Send the email
    public void run() {
        try {
            // create some properties and get the default Session
            Authenticator auth = new PopupAuthenticator(
                    getStrConfigDao().getConfig(CONFIG_EMAIL_USERNAME),
                    getStrConfigDao().getConfig(CONFIG_EMAIL_PASSWORD));

            Properties emailProps = new Properties();
            emailProps.put("mail.transport.protocol", getStrConfigDao().getConfig(CONFIG_EMAIL_PROTOCOL));
            emailProps.put("mail.smtp.host", getStrConfigDao().getConfig(CONFIG_EMAIL_HOST));
            emailProps.put("mail.smtp.auth", getStrConfigDao().getConfig(CONFIG_EMAIL_SMTP_AUTH));
            emailProps.put("mail.smtp.starttls.enable", getStrConfigDao().getConfig(CONFIG_EMAIL_SMTP_STARTTLS_ENABLE));
            emailProps.put("mail.smtp.port", getStrConfigDao().getConfig(CONFIG_EMAIL_PORT));

            Session session = Session.getInstance(emailProps, auth);

            // create a message
            Message msg = new MimeMessage(session);

            // set the from and to address
            InternetAddress addressFrom = new InternetAddress(getStrConfigDao().getConfig(CONFIG_EMAIL_FROM));
            msg.setFrom(addressFrom);

            InternetAddress[] addressTo = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                addressTo[i] = new InternetAddress(recipients[i]);
                addressTo[i].validate();
            }

            StringBuilder addressesBuilder = new StringBuilder("{");
            for (int c = 0; c < addressTo.length; c++) {
                addressesBuilder.append(addressTo[c].getAddress());
                if (c + 1 != addressTo.length)
                    addressesBuilder.append(',');
            }
            String addresses = addressesBuilder.toString();
            addresses += "}";

            logger.info("Sending email with subject: " + subject + " to addresses " + addresses + " using host: " + getStrConfigDao().getConfig(CONFIG_EMAIL_HOST));

            msg.setRecipients(Message.RecipientType.TO, addressTo);
            msg.setSubject(subject);

            if (isMultipart)
                if (multipart == null)
                    msg = setMultiPartMessage(msg);
                else
                    msg.setContent(multipart);
            else
                msg.setContent(message, contentType);

            Transport.send(msg);

            StringBuilder allRecipientsBuilder = new StringBuilder();
            for (String recipient : recipients) {
                allRecipientsBuilder.append(recipient).append(",");
            }
            String allRecipients = allRecipientsBuilder.toString();

            logger.info("Email with subject: " + subject + " sent to: {" +
                    allRecipients.substring(0, allRecipients.length() - 1) + "}");
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.severe("An error occurred sending mail. Message: " + ex.getMessage());
        }
    }

    private Message setMultiPartMessage(Message msg) {
        try {
            // This HTML mail has to 2 parts, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // First add the html part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, contentType);
            multipart.addBodyPart(messageBodyPart);

            // Add the image part
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(imageLocation);
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", contentID);
            multipart.addBodyPart(messageBodyPart);

            if (attachmentFilenames != null)
                for (String attachmentFilename : attachmentFilenames) {
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachmentFilename);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    String filename = attachmentFilename.substring(attachmentFilename.lastIndexOf(File.separatorChar) + 1);
                    messageBodyPart.setFileName(filename);
                    multipart.addBodyPart(messageBodyPart);
                }

            msg.setContent(multipart);
            return msg;
        } catch (Exception ex) {
            ex.printStackTrace();
            return msg;
        }
    }

    public static MimeMultipart createMultipartMessage(ByteArrayDataSource dataSource, String message, String fileName) {
        try {
            MimeMultipart multipart = new MimeMultipart("related");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/html");
            multipart.addBodyPart(messageBodyPart);

            // Add the image part
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(dataSource));
            messageBodyPart.setHeader("Content-ID", fileName);
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            return multipart;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static MimeMultipart createMultipartMessage(byte[] attachment, String message, String fileName, String attachmentFileType) {
        try {
            MimeMultipart multipart = new MimeMultipart("related");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/html");
            multipart.addBodyPart(messageBodyPart);


            //Add attachment
            messageBodyPart = new MimeBodyPart();
            DataSource att = new javax.mail.util.ByteArrayDataSource(attachment, attachmentFileType);
            messageBodyPart.setDataHandler(new DataHandler(att));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);


            // Add the image part
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(fileName);
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", fileName);
            multipart.addBodyPart(messageBodyPart);

            return multipart;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public class PopupAuthenticator extends Authenticator {
        String username;
        String password;

        PopupAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }
}
