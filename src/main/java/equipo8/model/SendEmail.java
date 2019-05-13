package equipo8.model;
 import javax.mail.*; 
 import javax.mail.internet.*; 
 import java.util.*; 
 
 public class SendEmail { 
	 private final String senderEmailID = "beercompany.abogados@gmail.com"; 
	 private final String senderPassword = "contrasena1"; 
	 private final String emailSMTPserver = "smtp.gmail.com"; 
	 private final String emailServerPort = "465"; 
	 private String receiverEmailID = null; 
	 private static String emailSubject = "Alerta exceso de temperaturas durante el transporte"; 
	 private static String emailBody; 
	 
	 public SendEmail(String receiverEmailID,String Subject, String Body,int idPedido){ 
		 // Receiver Email Address this.receiverEmailID=receiverEmailID;  
		 Properties props = new Properties(); 
		 props.put("mail.smtp.user",
				 senderEmailID); 
		 props.put("mail.smtp.host", emailSMTPserver); 
		 props.put("mail.smtp.port", emailServerPort); 
		 props.put("mail.smtp.starttls.enable", "true"); 
		 props.put("mail.smtp.auth", "true"); 
		 props.put("mail.smtp.socketFactory.port", emailServerPort); 
		 props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		 props.put("mail.smtp.socketFactory.fallback", "false"); 
		 emailBody = "El pedido con id " + idPedido + " ha excedido los 31ºC";
		 try{ 
			 Authenticator auth = new SMTPAuthenticator(); 
			 Session session = Session.getInstance(props, auth); 
			 MimeMessage msg = new MimeMessage(session); 
			 msg.setText(emailBody); 
			 msg.setSubject(emailSubject); 
			 msg.setFrom(new InternetAddress(senderEmailID)); 
			 msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmailID)); 
			 Transport.send(msg); 
			 System.out.println("Message send Successfully:)"); 
		 } 
		 catch (Exception mex){ 
			 mex.printStackTrace();
		 } 
	} 
	 public class SMTPAuthenticator extends javax.mail.Authenticator { 
		 public PasswordAuthentication getPasswordAuthentication() { 
			 return new PasswordAuthentication(senderEmailID, senderPassword); 
			 }
		 }
	 
//	 public static void main(String [] args) {
//		 SendEmail email;
//		 int temperaturaMax=40;
//		 
//		 if(temperaturaMax>31)
//			 email=new SendEmail("beercompany.abogados@gmail.com","Alerta","Temperaturas");
//	 }
 }
	 
 
