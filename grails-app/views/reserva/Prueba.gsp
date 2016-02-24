<!doctype html>
<html>
<head>
<title>Seleccione Restaurante para Reserva</title>

<!--  Muestra lista de restaurantes segùn especialidad seleccionada 04_05 -->

</head>
<body>
	<g:form  action="Confirma">  <!-- method="GET" -->
		<!-- action = "Enviar" id="Cod" -->
		<!-- <  g:hiddenField name="id" value="$ {datos.id }"/> -->
		<br>
	<g:hiddenField name="suma" value="${suma}"/>
	<g:hiddenField name="comensales" value="${params.comensales}"/>	
	<g:hiddenField name="fecha" value="${params.fecha}"/>	
	<g:hiddenField name="hora" value="${params.hora}"/>	
	<g:hiddenField name="usuario" value="${params.usuario}"/>	
	<g:hiddenField name="restaurante" value="${params.restaurante}"/>	
	
	
	<g:hiddenField name="reserva" value="${params}"/>	
	fecha convertida a String: ${fech }
	<br>
	<!-- es resto?: $  {otra } -->
	
	<!-- desgloce REserva?:$  {otra.comensales} -->	
	




<br>

		<br>		
		Comensales a reservar:${params.comensales}
		<br>
		<h3>Datos de reservas:</h3>
		<table border="1">
			<tr>
				<td>idReserva</td>
				<td>Restaurante</td>
				<td>Comensales</td>
				<td>Fecha</td>
				<td>Hora</td>
				<td>Usuario</td>
				
			</tr>

			<g:each in="${listaReservas}" var="thisReservas">
				<tr>
					<td>
						${thisReservas.id}
					</td>
					<td>
						${listaReservas.restaurante}
					</td>
					<td>
						${thisReservas.comensales}
					</td>
					<td>
						${thisReservas.fecha}
					</td>
					<td>
						${thisReservas.hora}
					</td>
					<td>
						${thisReservas.usuario}
					</td>
					
	<g:hiddenField name="id" value="${thisReservas.id}"/>
					 




				</tr>
				</g:each>
				
				<g:submitButton name="reservar" value="Confirma"/>
				
				
		</br>
		total: ${suma}
		
		<br>
		<table border=1>
		<td>${Id }</td>
		<td>${restaurante }</td>
		<td>${Comensales }</td>
		<td>${Fecha }</td>
		<td>${Hora}</td>
		<td>${Usuario }</td>
		</tr>
		<g:each in= "${reservadas }" var= "thisReservada">
				<td>${thisReservada.id }</td>
		<tr><td>${thisReservada.restaurante }</td>
		<tr><td>${thisReservada.Comensales }</td>
		<tr><td>${thisReservada.Fecha }</td>
		<tr><td>${thisReservada.Hora}</td>
		<tr><td>${thisReservada.Usuario }</td>
		</g:each>
	
	
	</g:form>



</body>
</html>