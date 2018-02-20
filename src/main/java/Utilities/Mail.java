package Utilities;

import DataBase.DBMedia;
import DataBase.DBPrice;
import DataBase.DBProduct;
import DataBase.DBStock;
import Reporte.Reporte;
import servlet.mail.Empresa;

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
    private final String PWD = "fff0303456fff";
//    private String[] destinatarios = {"gsanchez@musi.com.ar", "cbaez@musi.com.ar", "jbasombr@musi.com.ar"};
    private String[] destinatarios = {"gsanchez@musi.com.ar"};


    public Mail()
    {
        writer = new CSV.Writer();
    }

    public void enviarRegistrosStockSinProcesarCorrectamente(Empresa empresa)
    {
        String asuntoMail;

        if(empresa.equals(Empresa.CARSA))
            asuntoMail = "Stock No Procesado Correctamente (CARSA)";

        else if (empresa.equals(Empresa.EMSA))
            asuntoMail = "Stock No Procesado Correctamente (EMSA)";

        else
            asuntoMail ="Stock No Procesado Correctamente";

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

        File file = writer.getCsvStockNoProcesadoCorrectamente();
        String fileName = file.getName();

        Transport t;
        try
        {
            DBStock dbStock = new DBStock();
            int cantProcsadosConError = dbStock.getCantidadRegistrosProcesadosConError();
            int cantNoProcesados = dbStock.getCantidadRegistrosNoProcesados();
            MimeMultipart multipart = new MimeMultipart();

            //Si todos los registros fueron procesados correctamente, el mail no se envia.
            if(cantNoProcesados == 0 && cantProcsadosConError == 0)
            {
                file.delete();
                return;
            }
            //Agrego el archivo adjunto
            BodyPart attachedFile = new MimeBodyPart();

            attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
            attachedFile.setFileName(fileName);
            multipart.addBodyPart(attachedFile);

//            Agrego el cuerpo del mail
            BodyPart texto = new MimeBodyPart();
            StringBuilder sb = new StringBuilder();
            List<Reporte> reportes = dbStock.getReportes();

            for(Reporte reporte : reportes)
                sb.append(reporte.toString() + "\n\n");

            texto.setText(sb.toString());
            multipart.addBodyPart(texto);

            MimeMessage message = new MimeMessage(session);

            // Quien envia el correo
            message.setFrom(new InternetAddress(EMAILSENDER));

            // A quien va dirigido
            message.addRecipients(Message.RecipientType.TO, agregarDestinatarios());

            message.setSubject(asuntoMail);

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

    public void enviarRegistrosProductosSinProcesarCorrectamente(Empresa empresa)
    {
        String asuntoMail;

        if(empresa.equals(Empresa.CARSA))
            asuntoMail = "Productos No Procesados Correctamente (CARSA)";

        else if (empresa.equals(Empresa.EMSA))
            asuntoMail = "Productos No Procesados Correctamente (EMSA)";

        else
            asuntoMail ="Productos No Procesados Correctamente";

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

        File file = writer.getCsvProductosNoProcesadosCorrectamente();
        String fileName = file.getName();

        Transport t;
        try
        {
            DBProduct dbProduct = new DBProduct();
            int cantProcsadosConError = dbProduct.getCantidadRegistrosProcesadosConError();
            int cantNoProcesados = dbProduct.getCantidadRegistrosNoProcesados();
            MimeMultipart multipart = new MimeMultipart();

            //Si todos los registros fueron procesados correctamente, el mail no se envia.
            if(cantNoProcesados == 0 && cantProcsadosConError == 0)
            {
                file.delete();
                return;
            }
            //Agrego el archivo adjunto
            BodyPart attachedFile = new MimeBodyPart();

            attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
            attachedFile.setFileName(fileName);
            multipart.addBodyPart(attachedFile);

//            Agrego el cuerpo del mail
            BodyPart texto = new MimeBodyPart();
            StringBuilder sb = new StringBuilder();
            List<Reporte> reportes = dbProduct.getReportes();

            for(Reporte reporte : reportes)
                sb.append(reporte.toString() + "\n\n");

            texto.setText(sb.toString());
            multipart.addBodyPart(texto);

            MimeMessage message = new MimeMessage(session);

            // Quien envia el correo
            message.setFrom(new InternetAddress(EMAILSENDER));

            // A quien va dirigido
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.addRecipients(Message.RecipientType.TO, agregarDestinatarios());

//            message.setSubject("Productos No Procesados Correctamente");
            message.setSubject(asuntoMail);

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

    public void enviarRegistrosPreciosSinProcesarCorrectamente(Empresa empresa)
    {
        String asuntoMail;

        if(empresa.equals(Empresa.CARSA))
            asuntoMail = "Precios No Procesados Correctamente (CARSA)";

        else if (empresa.equals(Empresa.EMSA))
            asuntoMail = "Precios No Procesados Correctamente (EMSA)";

        else
            asuntoMail ="Precios No Procesados Correctamente";

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

        File file = writer.getCsvPreciosNoProcesadosCorrectamente();
        String fileName = file.getName();

        Transport t;
        try
        {
            DBPrice dbPrice = new DBPrice();
            int cantProcsadosConError = dbPrice.getCantidadRegistrosProcesadosConError();
            int cantNoProcesados = dbPrice.getCantidadRegistrosNoProcesados();
            MimeMultipart multipart = new MimeMultipart();

            //Si todos los registros fueron procesados correctamente, el mail no se envia.
            if(cantNoProcesados == 0 && cantProcsadosConError == 0)
            {
                file.delete();
                return;
            }
            //Agrego el archivo adjunto
            BodyPart attachedFile = new MimeBodyPart();

            attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
            attachedFile.setFileName(fileName);
            multipart.addBodyPart(attachedFile);

//            Agrego el cuerpo del mail
            BodyPart texto = new MimeBodyPart();
            StringBuilder sb = new StringBuilder();
            List<Reporte> reportes = dbPrice.getReportes();

            for(Reporte reporte : reportes)
                sb.append(reporte.toString() + "\n\n");

            texto.setText(sb.toString());
            multipart.addBodyPart(texto);

            MimeMessage message = new MimeMessage(session);

            // Quien envia el correo
            message.setFrom(new InternetAddress(EMAILSENDER));

            // A quien va dirigido
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.addRecipients(Message.RecipientType.TO, agregarDestinatarios());

            message.setSubject(asuntoMail);

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

    public void enviarRegistrosMediaSinProcesarCorrectamente(Empresa empresa)
    {
        String asuntoMail;

        if(empresa.equals(Empresa.CARSA))
            asuntoMail = "Media No Procesados Correctamente (CARSA)";

        else if (empresa.equals(Empresa.EMSA))
            asuntoMail = "Media No Procesados Correctamente (EMSA)";

        else
            asuntoMail ="Media No Procesados Correctamente";

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

        File file = writer.getCsvMediaNoProcesadoCorrectamente();
        String fileName = file.getName();

        Transport t;
        try
        {
            DBMedia dbMedia = new DBMedia();
            int cantProcsadosConError = dbMedia.getCantidadRegistrosProcesadosConError();
            int cantNoProcesados = dbMedia.getCantidadRegistrosNoProcesados();
            MimeMultipart multipart = new MimeMultipart();

            //Si todos los registros fueron procesados correctamente, el mail no se envia.
            if(cantNoProcesados == 0 && cantProcsadosConError == 0)
            {
                file.delete();
                return;
            }
            //Agrego el archivo adjunto
            BodyPart attachedFile = new MimeBodyPart();

            attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
            attachedFile.setFileName(fileName);
            multipart.addBodyPart(attachedFile);

//            Agrego el cuerpo del mail
            BodyPart texto = new MimeBodyPart();
            StringBuilder sb = new StringBuilder();
            List<Reporte> reportes = dbMedia.getReportes();

            for(Reporte reporte : reportes)
                sb.append(reporte.toString() + "\n\n");

            texto.setText(sb.toString());
            multipart.addBodyPart(texto);

            MimeMessage message = new MimeMessage(session);

            // Quien envia el correo
            message.setFrom(new InternetAddress(EMAILSENDER));

            // A quien va dirigido
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.addRecipients(Message.RecipientType.TO, agregarDestinatarios());

            message.setSubject(asuntoMail);

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
//        File products = writer.getCsvProductosProcesadosCorrectamente();
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

//        mail.enviarRegistrosProductosSinProcesarCorrectamente("gustavsanchez@yahoo.com.ar");
//        mail.enviarRegistrosPreciosSinProcesarCorrectamente("gustavsanchez@yahoo.com.ar");
//        mail.enviarRegistrosStockSinProcesarCorrectamente("gustavsanchez@yahoo.com.ar");
//        mail.enviarRegistrosMediaSinProcesarCorrectamente("gustavsanchez@yahoo.com.ar");

    }

    //transforma el arreglo de string con las direcciones de mail a un arreglo de Address para poder enviar los correos
    private Address[] agregarDestinatarios() throws AddressException
    {
        Address[] destinos = new Address[destinatarios.length];

        for(int i=0; i < destinatarios.length; i++)
            destinos[i] = new InternetAddress(destinatarios[i]);

        return destinos;
    }
}
