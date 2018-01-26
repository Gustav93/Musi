function subirArchivo(elemento)
{
    var file = elemento.files[0];
    var nombreArchivo = document.form.nombreArchivo;
    nombreArchivo.value = file.name;


    // document.form.submit();
}

function validarExtension(archivo)
{
    var extensionPermitida = ".csv";

    if(!archivo)
        alert("Primero selecciona un archivo");

    else
    {
        var extensionArchivo = archivo.substring(archivo.lastIndexOf(".")).toLowerCase();

        if(extensionPermitida == extensionArchivo)
        {
            // alert("Se empezará a subir el archivo");
            document.form.action = "subir_archivo"
            document.form.submit();
        }

        else
        {
            alert("Extension del archivo invalida");
        }
    }
}

function alertaMail() {

    alert("Se enviara un mail con el feed procesado");

}