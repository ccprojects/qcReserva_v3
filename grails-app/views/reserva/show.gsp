
<%@ page import="qcreserva.Reserva" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reserva.label', default: 'Reserva')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-reserva" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-reserva" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list reserva">
			
				<g:if test="${reservaInstance?.restaurante}">
				<li class="fieldcontain">
					<span id="restaurante-label" class="property-label"><g:message code="reserva.restaurante.label" default="Restaurante" /></span>
					
						<span class="property-value" aria-labelledby="restaurante-label"><g:fieldValue bean="${reservaInstance}" field="restaurante"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reservaInstance?.comensales}">
				<li class="fieldcontain">
					<span id="comensales-label" class="property-label"><g:message code="reserva.comensales.label" default="Comensales" /></span>
					
						<span class="property-value" aria-labelledby="comensales-label"><g:fieldValue bean="${reservaInstance}" field="comensales"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reservaInstance?.fecha}">
				<li class="fieldcontain">
					<span id="fecha-label" class="property-label"><g:message code="reserva.fecha.label" default="Fecha" /></span>
					
						<span class="property-value" aria-labelledby="fecha-label"><g:formatDate date="${reservaInstance?.fecha}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${reservaInstance?.hora}">
				<li class="fieldcontain">
					<span id="hora-label" class="property-label"><g:message code="reserva.hora.label" default="Hora" /></span>
					
						<span class="property-value" aria-labelledby="hora-label"><g:fieldValue bean="${reservaInstance}" field="hora"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reservaInstance?.usuario}">
				<li class="fieldcontain">
					<span id="usuario-label" class="property-label"><g:message code="reserva.usuario.label" default="Usuario" /></span>
					
						<span class="property-value" aria-labelledby="usuario-label"><g:fieldValue bean="${reservaInstance}" field="usuario"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:reservaInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${reservaInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
