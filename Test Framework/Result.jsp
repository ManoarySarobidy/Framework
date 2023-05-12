<%@page import="etu2032.packages.Employe"%>
<%
	Employe emp = (Employe)request.getAttribute("Employe");

%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Resultats de la recherche</title>
</head>
<body>
	<div class="resultats">
		Voici le nom d l'employe
		<%
			if( emp != null ){
				out.println( emp.getName() + " , id = " + emp.getId()  );
			}else{
				out.println("Aucun employe pour cette id");
			}
		%>
	</div>
</body>
</html>