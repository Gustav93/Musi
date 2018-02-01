function subirArchivo(elemento)
{
    // var file = elemento.files[0];
    // var nombreArchivo = document.form.nombreArchivo;
    // nombreArchivo.value = file.name;

    var files = elemento.files;
    console.log(files.length);
    var nombreArchivo = document.form.nombreArchivo;
    var nombreArchivos = [];

    for(var i=0; i<files.length; i++)
    {
        nombreArchivos.push(files[i].name);

    }


    // console.log(nombreArchivos);

    // nombreArchivo.value = JSON.parse(JSON.stringify(nombreArchivos));
    nombreArchivo.value = nombreArchivos;

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
