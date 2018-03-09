<%@page import="ejemplo.entidad_usuario.Usuario"%>
<%@page import="utilidades.FechaUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="ejemplo.database_ejemplo.UsuarioServicio"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/css/lista.css" >
	<title>Lista de usuarios</title>
</head>
<body>

	<h1>Lista de Usuarios</h1>
	<button id="backButton">Volver</button>

	<table>
		<thead>
		<tr>
			<th>Dni</th>
			<th>Nombre</th>
			<th>Fecha de nacimiento</th>
			<th></th>
			<th></th>
		</tr>
		</thead>
		<tbody id="grilla">
		</tbody>
	</table>
	
	<button id="boton"> Cargar</button>
	
	
	<script src="<%=request.getContextPath()%>/assets/js/lista.js"></script>
	<script type="text/javascript">
		ctx = '<%=request.getContextPath()%>';
	</script>
	
</body>
</html>