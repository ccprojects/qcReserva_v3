<%@ page import="qcreserva.Reserva" %>



<div class="fieldcontain ${hasErrors(bean: reservaInstance, field: 'restaurante', 'error')} required">
	<label for="restaurante">
		<g:message code="reserva.restaurante.label" default="Restaurante" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="restaurante" required="" value="${reservaInstance?.restaurante}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: reservaInstance, field: 'comensales', 'error')} required">
	<label for="comensales">
		<g:message code="reserva.comensales.label" default="Comensales" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="comensales" type="number" value="${reservaInstance.comensales}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: reservaInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="reserva.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${reservaInstance?.fecha}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: reservaInstance, field: 'hora', 'error')} required">
	<label for="hora">
		<g:message code="reserva.hora.label" default="Hora" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="hora" value="${fieldValue(bean: reservaInstance, field: 'hora')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: reservaInstance, field: 'usuario', 'error')} required">
	<label for="usuario">
		<g:message code="reserva.usuario.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="usuario" required="" value="${reservaInstance?.usuario}"/>

</div>

