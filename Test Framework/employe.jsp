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
					<th>Id de l'employe</th>
					<th>Date d'admission</th>
					<th>Badge</th>
					<th>Taille du Badge</th>
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
								<td>
									<%= emp.getDate()%>
								</td>
								<td>
									<%= emp.getBadge().getName()%>
								</td>
								<td>
									<%= emp.getBadge().getBytes().length%>
								</td>
								<td>
									<ul>
										<%
											for( String day: emp.getDays() ){ %>
												<li><%= day %></li>
										<% } %>
										
									</ul>
								</td>
							</tr>
					<% } %>
				</tbody>
			</table>
			<a href="log-out"> Log out </a>
		</div>
	</div>	

</body>
</html>