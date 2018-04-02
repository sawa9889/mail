package Mail;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class mailer {

    static PrintStream ps = null;          // посылка сообщений
    static DataInputStream dis = null;

    public static void send(String str) throws IOException
    {
        ps.println(str);      // посылка строки на SMTP
        ps.flush();           // очистка буфера
        System.out.println("Java sent: " + str);
    }

    public static void receive() throws IOException
    {
        String readstr = dis.readLine();  // получение ответа от SMTP
        System.out.println("SMTP respons: " + readstr);
    }

    public mailer(){

    }

    public void sendMail(){
        Properties properties=System.getProperties();
        properties.put("mail.smtp.from","nikita.fare@bk.ru");
        properties.put("mail.smtp.host","smtp.mail.ru");
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session=Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String email1="nikita.fare@bk.ru";
                final String password="sawa9889";
                return new PasswordAuthentication(email1,password);
            }
        });

        try {
            MimeMessage message=new MimeMessage(session);
            message.setFrom(new InternetAddress("nikita.fare@bk.ru"));

            Address[] addres=new Address[1];
            addres[0]=new InternetAddress("margoshaadamova@mail.ru");

            message.addRecipients(Message.RecipientType.TO , addres);

            message.setSubject("Test mail");

            message.setText("Это автоматическая система оповещения о том, что я вас люблю в "+System.currentTimeMillis());

            Transport.send(message);

        }catch (AddressException e) {
            System.out.println("lol");
        }
        catch (MessagingException e) {
            System.out.println(e);
        }
    }

}
