package com.pqashed.shed;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailController {

    private final String SMTP = "smtp.gmail.com";
    private final String PORT = "587";
    private final String USERNAME = "pqatoolshed@gmail.com";
    private final String PASSWORD = "szwycnsyokchxbgz";

    private Session sesh;

    public EmailController(){
        Properties prop = new Properties();
        prop.put("mail.smtp.host", SMTP);
        prop.put("mail.smtp.port", PORT);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        this.sesh = Session.getInstance(prop, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
    }

    public void sendEmail(EmailBody emailBody){
        try {
            Message message = new MimeMessage(sesh);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("william.mazerolle@pqa.ca")
            );
            message.setSubject("PQA Toolshed Tool Suggestion");
            message.setText(emailBody.getEmailBody());

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public class EmailBody{

        private String emailBody;
        public EmailBody(){}

        public void setSuggestionBody(String title, String providerEmail, String providerName, String providerBaseCity, String subject){
            emailBody = "============================PQA Toolshed Tool Suggestion============================\n";
            emailBody += "Title:    \t\t                " + title + "\n";
            emailBody += "Sender's Email Address:\t  " + providerEmail + "\n";
            emailBody += "Sender's Name:\t\t  " + providerName + "\n";
            emailBody += "Sender's Base City:\t  " + providerBaseCity + "\n";
            emailBody += "====================================================================================\n";
            emailBody += "Subject:\n";
            emailBody += subject + "\n\n";
            emailBody += ">>Sent From PQA Toolshed Services Email Controller";
        }

        public String getEmailBody(){
            return emailBody;
        }
    }

}
