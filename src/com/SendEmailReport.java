package com;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SendEmailReport
 */
@WebServlet("/SendEmailReport")
public class SendEmailReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendEmailReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("tableData"));
		System.out.println("Get saving;");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fromString = request.getParameter("emailFrom");
		String toString = request.getParameter("emailTo");
		String ccString = request.getParameter("emailCC");
		String subjectString = request.getParameter("emailSubject");
		String emailString = request.getParameter("tableData");
		
		
		System.out.println(fromString);
		System.out.println(toString);
		System.out.println(ccString);
		System.out.println(subjectString);
		System.out.println(emailString);
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "false");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", request.getParameter("snmpIP"));
		props.put("mail.smtp.port", request.getParameter("snmpPort"));
		Session session = Session.getInstance(props);
		
		try {

			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(fromString));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toString));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccString));
			message.setSubject(subjectString);
			message.setContent(emailString,"text/html");
			Transport.send(message);

		} catch (MessagingException e) {
			System.out.println(e);
		}
		
		
		//Email.sendMessage(Session.getInstance(Email.props) ,"jenkin.reporter@nice.com","vadimsn@nice.com","","Jenkins report", request.getParameter("tableData"));
	
		System.out.println("Email sent.;");
	}

}
