<%@page import="utilidades.FechaUtil"%>
<%@page import="ejemplo.entidad_usuario.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Editar Usuario</title>
</head>
<style>

	text, submit, h1, legend, p {font-family: Arial, Helvetica, sans-serif;}
	h1 {text-align: center;}
	form
	{
		margin: 0 auto;
		width: 60%;
	}
	
	input[type=submit]
	{
    background-color: rgb(44, 102, 255);
    border: none;
    color: white;
    padding: 16px 32px;
    text-decoration: none;
    margin: 4px 2px;
    cursor: pointer;
	}
	
</style>
<body>
	<% 
	Usuario u = (Usuario) request.getAttribute("user");
	%>
	
	<h1>Editar Usuario</h1>

	<form action="edit_user" method="POST">
	<fieldset>
		<legend>Usuario:</legend>
		<p>Dni:</p>
		<input type="text" name="dni_usuario" value="<%=u.getDni() %>">
		<p>Nombre:</p>
		<input type="text" name="nombre_usuario" value="<%=u.getNombre() %>">
		<p>Fecha de Nacimiento:</p>
		<input type="text" name="fecha_nacimiento" value="<%=FechaUtil.formatear(u.getFechaDenacimiento()) %>"> <br><br>
		<input type="submit" value="Editar">
	</fieldset>

	</form>
</body>
</html>