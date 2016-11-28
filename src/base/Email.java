package base;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	public static Properties props;
	
	public static void initProperties( String smtpHost, String smtpPort) {
		props = new Properties();
		props.put("mail.smtp.auth", "false");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
	}
	
	public static boolean sendMessage(Session session, String emailFrom, String toEmail, String ccEmail, String subject, String context) {
		try {

			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(emailFrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail));
			message.setSubject(subject);
//			message.setText("Dear Mail Crawler,"
//				+ "\n\n No spam to my email, please!");
			message.setContent(context,"text/html");
			Transport.send(message);
			return true;

		} catch (MessagingException e) {
			System.out.println(e);
			return false;
		}
	}
}
