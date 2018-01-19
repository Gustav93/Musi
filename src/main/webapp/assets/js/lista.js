	
	$(document).ready(function(){
		$(".editButton").click(redireccionar);
		$(".deleteButton").click(redireccionar);
		$("#backButton").click(redireccionar);
		
		$("#boton").click(function() {
			$.get(ctx +"/users_list_json", function(response) {
				var userList = response;
				$("#grilla").html("");
				for(var i=0; i<userList.length; i++) {
					var html = "";
					var id = new Date().getTime();
					var unUsuario = userList[i];
                    html += "<tr id='"+id+"' >";
                    html += "<td>"+unUsuario.dni+"</td>";
                    html += "<td>"+unUsuario.nombre+"</td>"
					html += "<td>"+unUsuario.fechaDenacimiento+"</td>"
					html += "<td><button value='"+unUsuario.dni+"' class='editButton'> Editar</button></td>";
					html += "<td><button value='"+unUsuario.dni+"' class='deleteButton'> Eliminar</button></td>";
					html += "</tr>";
					$("#grilla").append(html);
					$("tr#"+id).find("button.editButton").click(redireccionar);
					$("tr#"+id).find("button.deleteButton").click(redireccionar);
				}
			})
		});
		
	})
	
	
	function redireccionar(event) 
	{
		if(this.id == "backButton")
			window.location = getContextPath() + "/Main.html"

		else if(this.className == "editButton")
			window.location =   getContextPath() + "/edit_user?dni=" + this.value
			
		else if(this.className == "deleteButton")
		    window.location = getContextPath() + "/delete_user?dni=" + this.value			
	}
	
	function getContextPath() 
	{
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	