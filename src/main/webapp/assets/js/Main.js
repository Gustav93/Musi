function subirArchivo(elemento)
{
    var files = elemento.files;
    var nombreArchivo = document.form.nombreArchivo;
    var nombreArchivos = [];

    for(var i=0; i<files.length; i++)
    {
        nombreArchivos.push(files[i].name);
    }

    nombreArchivo.value = nombreArchivos;
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
