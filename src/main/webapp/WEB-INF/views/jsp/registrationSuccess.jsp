<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<title>Spring MVC.RegistrationSuccess</title>
</head>

<body>
	<H1>Creación de pista correcta</H1>

	<h3>La pista (${court.courtId}) ha sido creada satisfactoriamente</h3>

	<p><a href="<c:url value='/court-list' />">Ir a Lista de Pistas</a></p>

	<p>UPM-MIW --- ${now}</p>

</body>
</html>