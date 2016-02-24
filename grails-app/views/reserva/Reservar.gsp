<!doctype html>
<html>
<head>
<title>Seleccione Restaurante para Reserva</title>

<!--  Muestra lista de restaurantes segùn especialidad seleccionada 04_05 -->

</head>
<body>
	<g:form  action="Prueba">
	<g:hiddenField name="id" value="${datos.id }"/>
	<g:hiddenField name="cap" value="${datos.capacidad }"/>
		<br>
		<h4>Ingrese los datos de su Reserva:</h4>

CòdigoRestaurante: <input type="integer" name="id" value="${datos.id}">
same= : <input type="integer" name="id" value="${datos.id}">
Restaurante: <input type="integer" name="restaurante" value="${datos.restaurante }">
Capacidad: <input type="integer" name="cap" value="${datos.capacidad }">
		<br>
		<br>
Dia: <input type="date" name="fecha" value="">

<!-- Dia: input type="" name="fecha" value=""  -->
		<br>
		<!--  <br>
 Hora: <input type="integer" name="hora" value=[20..23]><br> -->

		</br>
Hora: <select name="hora">
			<option value="20">20</option>
			<option value="21">21</option>
			<option value="22">22</option>
			<option value="23">23</option>

		</select>
		<br>
		<br>
Comensales: <input type="integer" name="comensales" value="">
		<br>
		<br>
email: <input type="integer" name="usuario" value="">
		<br>
		</br>
		<br>

		<g:actionSubmit name="submit" value="Prueba" />
		</br>
	</g:form>



</body>
</html>