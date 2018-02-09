package Utilities;

import DataBase.DBMedia;
import DataBase.DBPrice;
import DataBase.DBProduct;
import DataBase.DBStock;
import Reporte.Reporte;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.List;
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

        Transport t = null;
        try
        {
//            Agrego el archivo adjunto
            BodyPart attachedFile = new MimeBodyPart();

            attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
            attachedFile.setFileName(fileName);
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachedFile);

//            Agrego el cuerpo del mail
            BodyPart texto = new MimeBodyPart();
            DBStock dbStock = new DBStock();
            StringBuilder sb = new StringBuilder();
            List<Reporte> reportes = dbStock.getReportes();

            if(reportes.size() == 0)
            {
                file.delete();
                return;
            }

            for(Reporte reporte : reportes)
                sb.append(reporte.toString() + "\n\n");

            texto.setText(sb.toString());
            multipart.addBodyPart(texto);


            MimeMessage message = new MimeMessage(session);

            // Quien envia el correo
            message.setFrom(new InternetAddress(EMAILSENDER));

            // A quien va dirigido
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            message.setSubject("Stock No Procesados Correctamente");

            message.setContent(multipart);

            t = session.getTransport("smtp");
            t.connect(EMAILSENDER, PWD);

            t.sendMessage(message,message.getAllRecipients());

            t.close();
            file.delete();


        }
        catch (MessagingException e)
        {
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
        try
        {
//            Agrego el archivo adjunto
            BodyPart attachedFile = new MimeBodyPart();

            attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
            attachedFile.setFileName(fileName);
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachedFile);

//            Agrego el cuerpo del mail
            BodyPart texto = new MimeBodyPart();
            DBProduct dbProduct = new DBProduct();
            StringBuilder sb = new StringBuilder();
            List<Reporte> reportes = dbProduct.getReportes();

//            si no hay registros procesados, no se envia nada
            if(reportes.size() == 0)
            {
                file.delete();
                return;
            }


            for(Reporte reporte : reportes)
                sb.append(reporte.toString() + "\n\n");

            texto.setText(sb.toString());
            multipart.addBodyPart(texto);


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


        }
        catch (MessagingException e)
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

        Transport t = null;
        try
        {
//            Agrego el archivo adjunto
            BodyPart attachedFile = new MimeBodyPart();

            attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
            attachedFile.setFileName(fileName);
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachedFile);

//            Agrego el cuerpo del mail
            BodyPart texto = new MimeBodyPart();
            DBPrice dbPrice = new DBPrice();
            StringBuilder sb = new StringBuilder();
            List<Reporte> reportes = dbPrice.getReportes();

            if(reportes.size() == 0)
            {
                file.delete();
                return;
            }

            for(Reporte reporte : reportes)
                sb.append(reporte.toString() + "\n\n");

            texto.setText(sb.toString());
            multipart.addBodyPart(texto);


            MimeMessage message = new MimeMessage(session);

            // Quien envia el correo
            message.setFrom(new InternetAddress(EMAILSENDER));

            // A quien va dirigido
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            message.setSubject("Precios No Procesados Correctamente");



            message.setContent(multipart);

            t = session.getTransport("smtp");
            t.connect(EMAILSENDER, PWD);

            t.sendMessage(message,message.getAllRecipients());

            t.close();
            file.delete();


        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }

    public void enviarRegistrosMediaSinProcesarCorrectamente(String email)
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

        File file = writer.getCsvMediaListNotProcessedOk();
        String fileName = file.getName();

        Transport t = null;
        try
        {
//            Agrego el archivo adjunto
            BodyPart attachedFile = new MimeBodyPart();

            attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
            attachedFile.setFileName(fileName);
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachedFile);

//            Agrego el cuerpo del mail
            BodyPart texto = new MimeBodyPart();
            DBMedia dbMedia = new DBMedia();
            StringBuilder sb = new StringBuilder();
            List<Reporte> reportes = dbMedia.getReportes();

//            si no hay registros procesados, no se envia nada
            if(reportes.size() == 0)
            {
                file.delete();
                return;
            }

            for(Reporte reporte : reportes)
                sb.append(reporte.toString() + "\n\n");

            texto.setText(sb.toString());
            multipart.addBodyPart(texto);


            MimeMessage message = new MimeMessage(session);

            // Quien envia el correo
            message.setFrom(new InternetAddress(EMAILSENDER));

            // A quien va dirigido
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            message.setSubject("Media No Procesados Correctamente");

            message.setContent(multipart);

            t = session.getTransport("smtp");
            t.connect(EMAILSENDER, PWD);

            t.sendMessage(message,message.getAllRecipients());

            t.close();
            file.delete();


        }
        catch (MessagingException e)
        {
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

//        mail.sendProductFeedNotProcessedOk("gustavsanchez@yahoo.com.ar");
//        mail.sendPriceFeedNotProcessedOk("gustavsanchez@yahoo.com.ar");
//        mail.sendStockFeedNotProcessedOk("gustavsanchez@yahoo.com.ar");
        mail.enviarRegistrosMediaSinProcesarCorrectamente("gustavsanchez@yahoo.com.ar");

    }
}
