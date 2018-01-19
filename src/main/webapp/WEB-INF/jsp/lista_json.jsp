<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	
	<script src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>

	<link rel="stylesheet" href="//cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css">
	
	
	<title>Lista de usuarios</title>
</head>
<body>

<table id="users" class="display" cellspacing="0" width="100%">
        <thead>
            <tr>
			<th>Dni</th>
			<th>Nombre</th>
			<th>Fecha de nacimiento</th>
            </tr>
        </thead>
        
        <tfoot>
            <tr>
			<th>Dni</th>
			<th>Nombre</th>
			<th>Fecha de nacimiento</th>
            </tr>
        </tfoot>
    </table>
	
	<script src="<%=request.getContextPath()%>/js/lista_json.js"></script>	

</body>
</html>