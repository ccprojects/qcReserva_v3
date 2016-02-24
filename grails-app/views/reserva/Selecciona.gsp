<!doctype html>
<html>
<head>
<title>Seleccione Restaurante para Reserva</title>

<!--  Muestra lista de restaurantes segùn especialidad seleccionada 04_05 -->

</head>
<body>
	<g:form  action="Reservar">  <!-- method="GET" -->
		<!-- action = "Enviar" id="Cod" -->
		<!--g:hiddenField name="id" value="$ {datos.id }"/-->
		<br>
		<h3>Datos de Restaurantes:</h3>
		<table border="1">
			<tr>
				<td>CODIGO</td>
				<td>Restaurante</td>
				<td>Especialidad</td>
				<td>Direccion</td>
				<td>Barrio</td>
				<td>Capacidad</td>
				<td>Accion</td>
			</tr>

			<g:each in="${restaurants}" var="thisRestaurants">
				<tr>
					<td>
						${thisRestaurants.id}
					</td>
					<td>
						${thisRestaurants.restaurante}
					</td>
					<td>
						${thisRestaurants.especialidad}
					</td>
					<td>
						${thisRestaurants.calle_nro}
					</td>
					<td>
						${thisRestaurants.barrio}
					</td>
					<td>
						${thisRestaurants.capacidad}
					</td>

	<g:hiddenField name="id" value="${thisRestaurants.id}"/>
					 <td><g:submitButton name="reservar" value="Reservar"/></td> 




				</tr>
				</g:each>
		</br>
	</g:form>



</body>
</html>