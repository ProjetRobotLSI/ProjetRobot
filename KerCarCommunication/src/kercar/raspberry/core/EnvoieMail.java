package kercar.raspberry.core;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EnvoieMail {

	public static void sendMail(){
		final String username = "kercart2.2013@gmail.com";
		final String password = "canard56";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("kercart2.2013@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("magicma2@gmail.com"));
			message.setSubject("Je suis arrivé");
			
			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText("Hello ! Tu peux demarrer la video");
			
			// create the Multipart and add its parts to it
		    Multipart mp = new MimeMultipart();
		    mp.addBodyPart(mbp1);


  		    // add the Multipart to the message
		    message.setContent(mp);
		    
		    // set the Date: header
			message.setSentDate(new Date());
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (Exception e) {
			System.out.println("Erreur Ã  l'envoi du mail");
			e.printStackTrace();
		}	
	}
}
