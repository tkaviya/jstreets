package net.streets.utilities;

import net.streets.utilities.concurrency.ThreadPoolManager;
import net.streets.utilities.mail.EMailer;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Logger;

import static net.streets.persistence.enumeration.StrConfig.CONFIG_EMAIL_ALERT_TO;
import static net.streets.persistence.helper.DaoManager.getStrConfigDao;
import static net.streets.utilities.mail.EMailer.DEFAULT_CONTENT_TYPE;

/**
 * Created by photon on 2016/01/01.
 */
public class NetworkUtilities {

    private static Logger logger = Logger.getLogger(NetworkUtilities.class.getSimpleName());

    public static String execReadToString(String execCommand) throws IOException {
        Process proc = Runtime.getRuntime().exec(execCommand);
        try (InputStream stream = proc.getInputStream()) {
            try (Scanner s = new Scanner(stream).useDelimiter("\\A")) {
                return s.hasNext() ? s.next() : "";
            }
        }
    }

    public static String getHostName() {
        try {
            String hostname = execReadToString("hostname").trim();
            logger.info("Got hostname of the system as " + hostname);
            return hostname;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFQDN() {
        try {
            String hostname = java.net.InetAddress.getLocalHost().getHostName();
            logger.info("Got hostname of the system as " + hostname);
            return hostname;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendEmailAlert(String strSystem, String alertSubject, String alertMessage) {
        logger.info("Sending alert email from " + strSystem + " with subject: " + alertSubject);
        logger.info(alertMessage);
        ThreadPoolManager.schedule(new EMailer(
                new String[]{getStrConfigDao().getConfig(CONFIG_EMAIL_ALERT_TO)},
                strSystem + " alert! " + alertSubject, alertMessage, DEFAULT_CONTENT_TYPE));
    }

    public static void sendEmail(String strSystem, String recipient, String emailSubject, String emailMessage) {
        logger.info("Sending email from " + strSystem + " with subject: " + emailSubject);
        ThreadPoolManager.schedule(new EMailer(new String[]{recipient}, emailSubject, emailMessage, DEFAULT_CONTENT_TYPE));
    }

    public static void sendEmail(String strSystem, String[] recipients, String emailSubject, String emailMessage,
                                 String contentType) {
        logger.info("Sending email from " + strSystem + " with subject: " + emailSubject);
        ThreadPoolManager.schedule(new EMailer(recipients, emailSubject, emailMessage, contentType));
    }
}
