package Utilities;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class Mail
{

    private CSV.Writer writer;
    private final String EMAILSENDER = "gsanchez@musi.com.ar";
    public static final String PWD = "fff0303456fff";

    public Mail()
    {
        writer = new CSV.Writer();
    }


    public void sendStockFeedNotProcessedOk(String email)
    {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");

        // Nombre del host de correo, es smtp.gmail.com
        props.setProperty("mail.smtp.host", "smtp.gmail.com");

        // TLS si está disponible
        props.setProperty("mail.smtp.starttls.enable", "true");

        // Puerto de gmail para envio de correos
        props.setProperty("mail.smtp.port","587");

        // Nombre del usuario
        props.setProperty("mail.smtp.user", "gsanchez@musi.com.ar");

        // Si requiere o no usuario y password para conectarse.
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        File file = writer.getCsvStockListNotProcessedOk();
        String fileName = file.getName();

        try {
            MimeBodyPart attachedFile = new MimeBodyPart();

            attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
            attachedFile.setFileName(fileName);
            MimeMultipart multipart = new MimeMultipart();

            multipart.addBodyPart(attachedFile);


            MimeMessage message = new MimeMessage(session);

            // Quien envia el correo
            message.setFrom(new InternetAddress(EMAILSENDER));

            // A quien va dirigido
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            message.setSubject("Stock No Procesado Correctamente");

            message.setContent(multipart);

            Transport t = session.getTransport("smtp");
            t.connect(EMAILSENDER, PWD);

            t.sendMessage(message,message.getAllRecipients());

            t.close();
            file.delete();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendProductFeedNotProcessedOk(String email)
    {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");

        // Nombre del host de correo, es smtp.gmail.com
        props.setProperty("mail.smtp.host", "smtp.gmail.com");

        // TLS si está disponible
        props.setProperty("mail.smtp.starttls.enable", "true");

        // Puerto de gmail para envio de correos
        props.setProperty("mail.smtp.port","587");

        // Nombre del usuario
        props.setProperty("mail.smtp.user", "gsanchez@musi.com.ar");

        // Si requiere o no usuario y password para conectarse.
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        File file = writer.getCsvProductListNotProcessedOk();
        String fileName = file.getName();

        Transport t = null;
        try {
            MimeBodyPart attachedFile = new MimeBodyPart();

            attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
            attachedFile.setFileName(fileName);
            MimeMultipart multipart = new MimeMultipart();

            multipart.addBodyPart(attachedFile);


            MimeMessage message = new MimeMessage(session);

            // Quien envia el correo
            message.setFrom(new InternetAddress(EMAILSENDER));

            // A quien va dirigido
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            message.setSubject("Productos No Procesados Correctamente");

            message.setContent(multipart);

            t = session.getTransport("smtp");
            t.connect(EMAILSENDER, PWD);

            t.sendMessage(message,message.getAllRecipients());

            t.close();
            file.delete();


        } catch (MessagingException e)
        {
            e.printStackTrace();
        }



    }

    public void sendPriceFeedNotProcessedOk(String email)
    {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");

        // Nombre del host de correo, es smtp.gmail.com
        props.setProperty("mail.smtp.host", "smtp.gmail.com");

        // TLS si está disponible
        props.setProperty("mail.smtp.starttls.enable", "true");

        // Puerto de gmail para envio de correos
        props.setProperty("mail.smtp.port","587");

        // Nombre del usuario
        props.setProperty("mail.smtp.user", "gsanchez@musi.com.ar");

        // Si requiere o no usuario y password para conectarse.
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        File file = writer.getCsvPriceListNotProcessedOk();
        String fileName = file.getName();

        try {
            MimeBodyPart attachedFile = new MimeBodyPart();

            attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
            attachedFile.setFileName(fileName);
            MimeMultipart multipart = new MimeMultipart();

            multipart.addBodyPart(attachedFile);


            MimeMessage message = new MimeMessage(session);

            // Quien envia el correo
            message.setFrom(new InternetAddress(EMAILSENDER));

            // A quien va dirigido
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            message.setSubject("Precios No Procesados Correctamente");

            message.setContent(multipart);

            Transport t = session.getTransport("smtp");
            t.connect(EMAILSENDER, PWD);

            t.sendMessage(message,message.getAllRecipients());

            t.close();
            file.delete();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MessagingException {

//        CSV.Writer writer = new CSV.Writer();
//        File products = writer.getCsvProduct();
//
//        System.out.println(products.getName());
//
//
//        Properties props = new Properties();
//        props.setProperty("mail.smtp.host", "smtp.gmail.com");
//
//        // Nombre del host de correo, es smtp.gmail.com
//        props.setProperty("mail.smtp.host", "smtp.gmail.com");
//
//        // TLS si está disponible
//        props.setProperty("mail.smtp.starttls.enable", "true");
//
//        // Puerto de gmail para envio de correos
//        props.setProperty("mail.smtp.port","587");
//
//        // Nombre del usuario
//        props.setProperty("mail.smtp.user", "gsanchez@musi.com.ar");
//
//        // Si requiere o no usuario y password para conectarse.
//        props.setProperty("mail.smtp.auth", "true");
//
//        Session session = Session.getDefaultInstance(props);
//        session.setDebug(true);
//
//
//
////        MimeBodyPart texto = new MimeBodyPart();
////
////        texto.setText("Buenas! \r\r esto es un mail con un archivo adjunto");
//
//
//        MimeBodyPart adjunto = new MimeBodyPart();
//
//        adjunto.setDataHandler(new DataHandler(new FileDataSource(products.getName())));
//        adjunto.setFileName(products.getName());
//        MimeMultipart multiParte = new MimeMultipart();
//
////        multiParte.addBodyPart(texto);
//        multiParte.addBodyPart(adjunto);
//
//
//        MimeMessage message = new MimeMessage(session);
//
//        // Quien envia el correo
//        message.setFrom(new InternetAddress("gsanchez@musi.com"));
//
//        // A quien va dirigido
//        message.addRecipient(Message.RecipientType.TO, new InternetAddress("gustavsanchez@yahoo.com.ar"));
//
//        message.setSubject("Prueba");
//
//        message.setContent(multiParte);
//
////        message.setText("Buenas! \r\r esto es un mail.");
//
//
//
//        Transport t = session.getTransport("smtp");
//        t.connect("gsanchez@musi.com.ar","fff0303456fff");
//
//        t.sendMessage(message,message.getAllRecipients());
//
//        t.close();
//        products.delete();

        Mail mail = new Mail();

        mail.sendProductFeedNotProcessedOk("gustavsanchez@yahoo.com.ar");
        mail.sendPriceFeedNotProcessedOk("gustavsanchez@yahoo.com.ar");
        mail.sendStockFeedNotProcessedOk("gustavsanchez@yahoo.com.ar");

    }
}
