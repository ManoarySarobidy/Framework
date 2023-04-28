<%@page import="java.util.HashMap,java.util.Vector"%>
<%@page import="etu2032.packages.Employe"%>
<%
	Vector<Employe> employes = (Vector<Employe>)request.getAttribute("emp-list");
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Document</title>
</head>
<body>
	
	<div class="container">
		<div class="container">
			<table class="table">
				<thead>
					<th>Nom de l'employe</th>
				</thead>
				<tbody>
					<%
						for( Employe emp : employes ){ %>
							<tr>
								<td>
									<%= emp.getName() %>		
								</td>
								<td>
									<%= emp.getId()%>
								</td>
							</tr>
					<% } %>
				</tbody>
			</table>
		</div>
	</div>	

</body>
</html>