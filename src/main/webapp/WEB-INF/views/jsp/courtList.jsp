<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<title>Spring MVC. Court List</title>
</head>

<body>
    <H1>Listado de pistas</H1>
	<table border="1">
		<thead>
			<tr>
				<th>Id</th>
				<th>Active</th>
				
				<th>#</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${courtList}" var="court">
				<tr>
					<td>${court.courtId}</td>
					<td>${court.active}</td>
					<td><a href="<c:url value='/delete-court/${court.courtId}' />">delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<p><a href="<c:url value='/create-court'/>">Create court</a></p>
	<p><a href="<c:url value='/home'/>">Home</a></p>

    <p>UPM-MIW --- ${now}</p>

</body>
</html>