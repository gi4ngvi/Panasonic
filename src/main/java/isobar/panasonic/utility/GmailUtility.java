package isobar.panasonic.utility;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.StringUtils;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import com.relevantcodes.extentreports.LogStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class GmailUtility {
    private Gmail service;
    private String user;
    private String credentialPath;
    private String storePath;
    private MimeMessage message;
    private static final String MIME_TYPE_HTML = "text/html";
    private static final String DEFAULT_USER = "me";

    public GmailUtility(String credential) {
        this.user = DEFAULT_USER;
        this.credentialPath = credential;
        storePath = configStorePath(credentialPath);
        try {
            service = getGmailService();
        } catch (IOException ex) {
        }
    }

    public String getCredentialPath() {
        return credentialPath;
    }

    public String getStorePath() {
        return storePath;
    }

    public String configStorePath(String credentialPath) {
        credentialPath = credentialPath.replace(".json", "");
        String folderName = credentialPath.substring(credentialPath.lastIndexOf("\\"), credentialPath.length());
        return System.getProperty("user.home") + "\\.credentials" + folderName;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setMessage(MimeMessage message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public MimeMessage getMessage() {
        return message;
    }

    private static final String APPLICATION_NAME = "Gmail API Java";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;
    private static final List<String> SCOPES =
            Arrays.asList(GmailScopes.MAIL_GOOGLE_COM);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public Credential authorize() throws IOException {
        // Load client secrets.
        String api_key = getCredentialPath();
        InputStream in = new FileInputStream(api_key);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        File data_store_dir = new File(getStorePath());
        FileDataStoreFactory data_store_factory = new FileDataStoreFactory(data_store_dir);
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(data_store_factory)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    public Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<Label> getAccountLabels() throws IOException {
        return service.users().labels().list(user).execute().getLabels();
    }

    public List<Message> getListAllMessage() throws IOException {
        List<Message> list = service.users().messages().list(user).execute().getMessages();
        if (list.size() > 0) return list;
        else throw new IndexOutOfBoundsException("No message has been found");
    }

    public ImapSettings getImapSetting() throws IOException {
        return service.users().settings().getImap(user).execute();
    }

    public String getMessageBodyAsHTML(String messageID) throws IOException {
        Message msg = service.users().messages().get(user, messageID).execute();
        List<MessagePart> parts = msg.getPayload().getParts();
        for (MessagePart part : parts) {
            Base64 base64Url = new Base64();
            byte[] data = base64Url.decodeBase64(part.getBody().getData());
            if (part.getMimeType().equalsIgnoreCase(MIME_TYPE_HTML)) return StringUtils.newStringUtf8(data);
        }
        return null;
    }

    public String getMessageBodyAsText(String messageID) throws IOException {
        Message msg = service.users().messages().get(user, messageID).execute();
        MessagePartBody body = msg.getPayload().getBody();
        Base64 base64Url = new Base64();
        byte[] data = base64Url.decodeBase64(body.getData());
        return StringUtils.newStringUtf8(data);
    }

    public MimeMessage getMessage(String messageId)
            throws IOException, MessagingException {
        Message msg = service.users().messages().get(user, messageId).setFormat("raw").execute();
        Base64 base64Url = new Base64();
        byte[] emailBytes = base64Url.decodeBase64(msg.getRaw().getBytes());
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        message = new MimeMessage(session, new ByteArrayInputStream(emailBytes));
        return message;
    }

    public String getMessageSubject(String messageId)
            throws IOException, MessagingException {
        MimeMessage message = getMessage(messageId);
        return message.getSubject();
    }

    public Message getLastestMessage() throws IOException {
        List<Message> list = getListAllMessage();
        if (list.size() > 0) return list.get(0);
        else throw new IndexOutOfBoundsException("No message has been found");
    }

    public String getLastestMessageID() throws IOException {
        List<Message> list = getListAllMessage();
        if (list.size() > 0) return list.get(0).getId();
        else throw new IndexOutOfBoundsException("No message has been found");
    }

    public String getLastestMessageSubject() {
        try {
            String id = getLastestMessageID();
            return getMessageSubject(id);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (MessagingException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    public String getLastestMessageBodyAsHTML() throws IOException {
        String id = getLastestMessageID();
        return getMessageBodyAsHTML(id);
    }

    public String getLatestMessageBodyAsText() throws IOException {
        String id = getLastestMessageID();
        return getMessageBodyAsText(id);
    }

    private String parseInvitationLinkFromEmailBody() throws IOException {
        String html = getLatestMessageBodyAsText();
        Document doc = Jsoup.parse(html, "UTF-8");
        Element link = doc.getElementsByAttributeValueContaining("href", "invitation/customer_account/create/invitation").first();
        if (link == null) {
            ReportUtility.getInstance().log(LogStatus.ERROR, String.format("Link invitation register account is null because can't receive mail"));
            return null;
        }
        return link.attr("href");
    }

    private String parseRecoveryLinkFromEmailBody() throws IOException {
        String html = getLatestMessageBodyAsText();
        Document doc = Jsoup.parse(html, "UTF-8");
        Element link = doc.getElementsByAttributeValueContaining("href", "customer/account/createPassword").first();
        return link.attr("href");
    }

    public String getRegistrationLink() {
        try {
            String link = parseInvitationLinkFromEmailBody();
            String subject = getLastestMessageSubject();
            if (link != null) return link;
            else {
                ReportUtility.getInstance().log(LogStatus.ERROR, String.format("Link is null, email subject is: %s", subject));
                return null;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public String getRecoveryPasswordLink() {
        try {
            String link = parseRecoveryLinkFromEmailBody();
            String subject = getLastestMessageSubject();
            if (link != null) return link;
            else {
                ReportUtility.getInstance().log(LogStatus.ERROR, String.format("Link is null, email subject is: %s", subject));
                return null;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }


    /*Improvement get latest email*/

    public MimeMessage getMessageById(String messageId)
            throws IOException, MessagingException {
        Message msg = service.users().messages().get(user, messageId).setFormat("raw").execute();
        Base64 base64Url = new Base64();
        byte[] emailBytes = base64Url.decodeBase64(msg.getRaw().getBytes());
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        message = new MimeMessage(session, new ByteArrayInputStream(emailBytes));
        return message;
    }

    /*
     * Get list latest email
     ** @param senderName: name of sender
     *  @param emailSender: email address of sender
     *  @param subject: subject of email
     *  @param fromDate: Search for email has send date after fromDate
     *  @param newerThanHour: Search for emails newer than a time period(hour)
     */
    public List<MimeMessage> getListEmails(String senderName, String emailSender, String subject, LocalDate fromDate, int newerThanHour) {
        List<MimeMessage> messages = new ArrayList<MimeMessage>();
        String query = "", sendDateStr, mailFromStr = "";
        if (senderName != null && !senderName.isEmpty()) {
            mailFromStr += String.format("%s ", senderName);
        }
        if (emailSender != null && !emailSender.isEmpty()) {
            mailFromStr += emailSender;
        }
        if (!mailFromStr.isEmpty()) {
            query += String.format("from: %s", mailFromStr);
        }
        if (subject != null && !subject.isEmpty()) {
            query += query.isEmpty() ? String.format("subject: %s", subject) : String.format(";subject: %s", subject);
        }
        if (fromDate != null) {
            sendDateStr = new DateUtility().formatDate(fromDate, "yyyy/MM/dd");
            query += query.isEmpty() ? String.format("after: %s", sendDateStr) : String.format(";after: %s", sendDateStr);
        }
        int newerThanHourValue = newerThanHour <= 1 ? 1 : newerThanHour;
        query += query.isEmpty() ? String.format("newer_than:%sh", newerThanHourValue) : String.format(";newer_than:%sh", newerThanHourValue);
        try {
            messages = listMessagesMatchingQuery(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }

    private List<MimeMessage> listMessagesMatchingQuery(String query) throws IOException {
        ListMessagesResponse response = service.users().messages().list(user).setQ(query).execute();
        List<Message> messages = new ArrayList<>();
        List<MimeMessage> mimeMessages = new ArrayList<>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(user).setQ(query)
                        .setPageToken(pageToken).execute();
            } else {
                break;
            }
        }
        for (Message message : messages) {
            try {
                mimeMessages.add(getMessageById(message.getId()));
            } catch (MessagingException e) {
            }
        }
        return mimeMessages;
    }

    /*
     * get list latest email by sender name and subject
     * @param senderName: name of sender email
     * @param subject: subject of email
     */
    public List<MimeMessage> getListEmails(String senderName, String subject) {
        return getListEmails(senderName, null, subject, null, 0);
    }

    /*
     * get list latest email by sender name
     * @param senderName: name of sender email
     */
    public List<MimeMessage> getListEmailBySenderName(String senderName) {
        return getListEmails(senderName, null, null, null, 0);
    }

    /*
     * get list latest email by subject
     * @param subject: subject of email
     */
    public List<MimeMessage> getListEmailBySubject(String subject) {
        return getListEmails(null, null, subject, null, 0);
    }

    /*
     * get list latest email by from date
     * @param fromDate: Search for email has send date after fromDate
     */
    public List<MimeMessage> getListEmails(LocalDate fromDate) {
        return getListEmails(null, null, null, fromDate, 0);
    }

    /*
     * get list latest email by newerThanHour
     * @param newerThanHour: Search for messages newer than a time period(hour)
     */
    public List<MimeMessage> getListEmails(int newerThanHour) {
        return getListEmails(null, null, null, null, newerThanHour);
    }

    public List<MimeMessage> getListEmails(String senderName, String subject, int newerThanHour) {
        return getListEmails(senderName, null, subject, null, newerThanHour);
    }

    /*
     * Get latest email
     *  @param senderName: name of email sender
     *  @param emailSender: email address of email sender
     *  @param subject: subject of email
     *  @param fromDate: Search for email has send date after fromDate
     *  @param newerThanHour: Search for messages newer than a time period(hour)
     */
    public MimeMessage getLatestEmail(String senderName, String emailSender, String subject, LocalDate fromDate, int newerThanHour) {
        List<MimeMessage> listEmails = getListEmails(senderName, emailSender, subject, fromDate, newerThanHour);
        if (listEmails.size() > 0) {
            return listEmails.get(0);
        }
        return null;
    }

    public MimeMessage getLatestEmailBySenderName(String senderName) {
        return getLatestEmail(senderName, null, null, null, 0);
    }

    public MimeMessage getLatestEmailBySubject(String subject) {
        return getLatestEmail(null, null, subject, null, 0);
    }

    public MimeMessage getLatestEmail(String senderName, String subject) {
        return getLatestEmail(senderName, null, subject, null, 0);
    }

    public MimeMessage getLatestEmail(LocalDate fromDate) {
        return getLatestEmail(null, null, null, fromDate, 0);
    }

    public MimeMessage getLatestEmail(int newerThanHour) {
        return getLatestEmail(null, null, null, null, newerThanHour);
    }

    public MimeMessage getEmailIndex(String subject, int index) {
        List<MimeMessage> listEmail = getListEmails(null, null, subject, null, 0);
        try {
            return listEmail.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public MimeMessage getLatestEmail(String senderName, String subject, int newerThanHour) {
        return getLatestEmail(senderName, null, subject, null, newerThanHour);
    }

    /* Get link from email.
     * @param email: email to get link.
     * @param hrefMatch : keyword contained in link need to return.
     */
    private String getLinkInEmail(MimeMessage email, String hrefMatch) {
        try {
            String emailContent = email.getContent().toString();
            Document doc = Jsoup.parse(emailContent);
            Element element = doc.getElementsByAttributeValueContaining("href", hrefMatch).first();
            return element.attr("href");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * Get email subject
     * @param email: email need to get subject.
     */
    public String getEmailSubject(MimeMessage email) {
        try {
            return email.getSubject();
        } catch (MessagingException e) {
            return null;
        }
    }

    public MimeMessage getLatestEmail() {
        try {
            String messageId = getLastestMessageID();
            return getMessage(messageId);
        } catch (MessagingException | IOException e) {
            return null;
        }
    }

    public String getEmailContent(MimeMessage email) {
        try {
            return email.getContent().toString();
        } catch (MessagingException | IOException e) {
            return null;
        }
    }

    public String getResetPasswordLink(MimeMessage email) {
        return getLinkInEmail(email, "customer/account/createPassword");
    }

    public String getRegistrationLink(MimeMessage email) {
        return getLinkInEmail(email, "invitation/customer_account/create/invitation");
    }


    public Date getSendDate(MimeMessage mimeMessage) {
        try {
            return mimeMessage.getSentDate();
        } catch (MessagingException | NullPointerException e) {
            return new Date(100000l);
        }
    }


    public void waitForEmailSentBySenderName(String senderName, long timeSentLastestEmail) {
        waitForEmailSent(senderName, null, timeSentLastestEmail);
    }

    public void waitForEmailSent(String senderName, String subject, long timeSentLastestEmail) {
        /*
         * Check continuously for 5 minute(300000 millisecond) until you receive the mail
         * */
        int count = 300000;
        long startTimeResponse, endTimeResponse, tmp;
        MimeMessage newEmail;
        while (count > 0) {
            /*
             * Each time getLatestEmail costs about 300 millisecond
             */
            startTimeResponse = System.currentTimeMillis();
            newEmail = getLatestEmail(senderName, null, subject, null, 0);
            endTimeResponse = System.currentTimeMillis();
            count -= endTimeResponse - startTimeResponse;
            tmp = getSendDate(newEmail).getTime() - timeSentLastestEmail;
            if (newEmail != null && (tmp > 0)) {
                return;
            }

        }
        throw new IllegalArgumentException(String.format("No email sent with information: sender: %s - subject: %s", senderName, subject));
    }
}
