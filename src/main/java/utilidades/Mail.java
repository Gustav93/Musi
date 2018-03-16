package utilidades;

import db.temporal.*;
import utilidades.enums.Empresa;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.List;
import java.util.Properties;

public class Mail
{
    private csv.Writer writer;
    private final String EMAILSENDER = "gsanchez@musi.com.ar";
    private final String PWD = "fff0303456fff";
    private String[] destinatarios = {"gsanchez@musi.com.ar"};
//    private String[] destinatariosCARSA = {"gsanchez@musi.com.ar", "cbaez@musi.com.ar", "marcelo.hassan@grupocarsa.com", "nestor.gatter@grupocarsa.com", "federico.henchoz@grupocarsa.com"};
//    private String[] destinatariosEMSA = {"gsanchez@musi.com.ar", "cbaez@musi.com.ar", "alejandro.brun@emusimundo.com"};
    private String[] destinatariosCARSA = {"gsanchez@musi.com.ar"};
    private String[] destinatariosEMSA = {"gsanchez@musi.com.ar"};

    public Mail()
    {
        writer = new csv.Writer();
    }

    //envia un mail un informe sobre los archivos que fueron procesados. Si existen registros que se procesaron con
    //errores o que no fueron procesados, se adjunta un archivo csv con dichos registros
    public void enviarInformeStock(Empresa empresa)
    {
        String asuntoMail;

        if(empresa.equals(Empresa.CARSA))
        {
            asuntoMail = "Stock Procesado (CARSA)";
            destinatarios = destinatariosCARSA;
        }

        else if (empresa.equals(Empresa.EMSA))
        {
            asuntoMail = "Stock Procesado(EMSA)";
            destinatarios = destinatariosEMSA;
        }

        else
            asuntoMail ="Stock Procesado";

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

            //Se adjunta el archivo solo si hay registros que no fueron procesados o fueron procesados con error
            if(cantNoProcesados != 0 || cantProcsadosConError != 0)
            {
                BodyPart attachedFile = new MimeBodyPart();
                attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
                attachedFile.setFileName(fileName);
                multipart.addBodyPart(attachedFile);
            }

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

    public void enviarInformeProductos(Empresa empresa)
    {
        String asuntoMail;

        if(empresa.equals(Empresa.CARSA))
        {
            asuntoMail = "Productos Procesados (CARSA)";
            destinatarios = destinatariosCARSA;
        }


        else if (empresa.equals(Empresa.EMSA))
        {
            asuntoMail = "Productos Procesados (EMSA)";
            destinatarios = destinatariosEMSA;
        }

        else
            asuntoMail ="Productos Procesados";

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

            if(cantNoProcesados != 0 || cantProcsadosConError != 0)
            {
                BodyPart attachedFile = new MimeBodyPart();
                attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
                attachedFile.setFileName(fileName);
                multipart.addBodyPart(attachedFile);
            }

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

    public void enviarInformePrecios(Empresa empresa)
    {
        String asuntoMail;

        if(empresa.equals(Empresa.CARSA))
        {
            asuntoMail = "Precios Procesados (CARSA)";
            destinatarios = destinatariosCARSA;
        }


        else if (empresa.equals(Empresa.EMSA))
        {
            asuntoMail = "Precios Procesados (EMSA)";
            destinatarios = destinatariosEMSA;
        }


        else
            asuntoMail ="Precios Procesados";

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

            if(cantNoProcesados != 0 || cantProcsadosConError != 0)
            {
                BodyPart attachedFile = new MimeBodyPart();

                attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
                attachedFile.setFileName(fileName);
                multipart.addBodyPart(attachedFile);
            }

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

    public void enviarInformeMedia(Empresa empresa)
    {
        String asuntoMail;

        if(empresa.equals(Empresa.CARSA))
            asuntoMail = "Media Procesados (CARSA)";

        else if (empresa.equals(Empresa.EMSA))
            asuntoMail = "Media Procesados (EMSA)";

        else
            asuntoMail ="Media Procesados";

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

            if(cantNoProcesados != 0 || cantProcsadosConError != 0)
            {
                BodyPart attachedFile = new MimeBodyPart();
                attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
                attachedFile.setFileName(fileName);
                multipart.addBodyPart(attachedFile);
            }

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

    public void enviarInformeMerchandise(Empresa empresa)
    {
        String asuntoMail;

        if(empresa.equals(Empresa.CARSA))
            asuntoMail = "Merchandise Procesados (CARSA)";

        else if (empresa.equals(Empresa.EMSA))
            asuntoMail = "Merchandise Procesados (EMSA)";

        else
            asuntoMail ="Merchandise Procesados";

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

        File file = writer.getCsvMerchandiseNoProcesadoCorrectamente();
        String fileName = file.getName();

        Transport t;
        try
        {
            DBMerchandise dbMerchandise = new DBMerchandise();
            int cantProcsadosConError = dbMerchandise.getCantidadRegistrosProcesadosConError();
            int cantNoProcesados = dbMerchandise.getCantidadRegistrosNoProcesados();
            MimeMultipart multipart = new MimeMultipart();

            if(cantNoProcesados != 0 || cantProcsadosConError != 0)
            {
                BodyPart attachedFile = new MimeBodyPart();
                attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
                attachedFile.setFileName(fileName);
                multipart.addBodyPart(attachedFile);
            }

//            Agrego el cuerpo del mail
            BodyPart texto = new MimeBodyPart();
            StringBuilder sb = new StringBuilder();
            List<Reporte> reportes = dbMerchandise.getReportes();

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

    public void enviarInformeClasificacion(Empresa empresa)
    {
        String asuntoMail;

        if(empresa.equals(Empresa.CARSA))
            asuntoMail = "Clasificacion Procesado (CARSA)";

        else if (empresa.equals(Empresa.EMSA))
            asuntoMail = "Clasificacion Procesado (EMSA)";

        else
            asuntoMail ="Clasificacion Procesado";

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

        File file = writer.getCsvClasificacionNoProcesadoCorrectamente();
        String fileName = file.getName();

        Transport t;
        try
        {
            DBClasificacion dbClasificacion = new DBClasificacion();
            int cantProcsadosConError = dbClasificacion.getCantidadRegistrosProcesadosConError();
            int cantNoProcesados = dbClasificacion.getCantidadRegistrosNoProcesados();
            MimeMultipart multipart = new MimeMultipart();

            if(cantNoProcesados != 0 || cantProcsadosConError != 0)
            {
                BodyPart attachedFile = new MimeBodyPart();
                attachedFile.setDataHandler(new DataHandler(new FileDataSource(fileName)));
                attachedFile.setFileName(fileName);
                multipart.addBodyPart(attachedFile);
            }

//            Agrego el cuerpo del mail
            BodyPart texto = new MimeBodyPart();
            StringBuilder sb = new StringBuilder();
            List<Reporte> reportes = dbClasificacion.getReportes();

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

    //transforma el arreglo de string con las direcciones de mail a un arreglo de Address para poder enviar los correos
    private Address[] agregarDestinatarios() throws AddressException
    {
        Address[] destinos = new Address[destinatarios.length];

        for(int i=0; i < destinatarios.length; i++)
            destinos[i] = new InternetAddress(destinatarios[i]);

        return destinos;
    }

    public static void main(String[] args) throws MessagingException
    {

//        csv.Writer writer = new csv.Writer();
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

//        mail.enviarInformeProductos("gustavsanchez@yahoo.com.ar");
//        mail.enviarInformePrecios("gustavsanchez@yahoo.com.ar");
//        mail.enviarInformeStock("gustavsanchez@yahoo.com.ar");
//        mail.enviarInformeMedia("gustavsanchez@yahoo.com.ar");

    }


}