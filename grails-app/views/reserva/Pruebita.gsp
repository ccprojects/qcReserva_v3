<!doctype html>
<html>
<head>
<title>Seleccione Restaurante para Reserva</title>

<!--  Muestra lista de restaurantes segùn especialidad seleccionada 04_05 -->

</head>
<body>
	<g:form  action="Reservar">  <!-- method="GET" -->
		<!-- action = "Enviar" id="Cod" -->
		<!-- <  g:hiddenField name="id" value="$ {datos.id }"/> -->
		<br>
	
	es resto?: ${otra }
	
	desgloce REserva?:${otra.comensales}	
	
	<g:each in="${otra}" var="thisReservas">
				<table border= 2><tr>
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
					 <td><g:submitButton name="reservar" value="Confirmar"/></td> 
				</tr>

</table>
</g:each>
fin de otra!!




<br>
ver.id: ${ver.id } rest: ${ver.restaurante } ver.comens: ${ver.comensales } ver.fecha: ${ver.fecha } ver.hora: ${ver.hora } 
		<br>		
		fechhhaa: ${f }
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
				<td>Accion</td>
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
					 <td><g:submitButton name="reservar" value="Confirmar"/></td> 




				</tr>
				</g:each>
		</br>
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