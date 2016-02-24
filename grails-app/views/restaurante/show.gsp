
<%@ page import="qcreserva.Restaurante" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'restaurante.label', default: 'Restaurante')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-restaurante" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-restaurante" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list restaurante">
			
				<g:if test="${restauranteInstance?.restaurante}">
				<li class="fieldcontain">
					<span id="restaurante-label" class="property-label"><g:message code="restaurante.restaurante.label" default="Restaurante" /></span>
					
						<span class="property-value" aria-labelledby="restaurante-label"><g:fieldValue bean="${restauranteInstance}" field="restaurante"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restauranteInstance?.especialidad}">
				<li class="fieldcontain">
					<span id="especialidad-label" class="property-label"><g:message code="restaurante.especialidad.label" default="Especialidad" /></span>
					
						<span class="property-value" aria-labelledby="especialidad-label"><g:fieldValue bean="${restauranteInstance}" field="especialidad"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restauranteInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="restaurante.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${restauranteInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restauranteInstance?.telefono}">
				<li class="fieldcontain">
					<span id="telefono-label" class="property-label"><g:message code="restaurante.telefono.label" default="Telefono" /></span>
					
						<span class="property-value" aria-labelledby="telefono-label"><g:fieldValue bean="${restauranteInstance}" field="telefono"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restauranteInstance?.calle_nro}">
				<li class="fieldcontain">
					<span id="calle_nro-label" class="property-label"><g:message code="restaurante.calle_nro.label" default="Callenro" /></span>
					
						<span class="property-value" aria-labelledby="calle_nro-label"><g:fieldValue bean="${restauranteInstance}" field="calle_nro"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restauranteInstance?.barrio}">
				<li class="fieldcontain">
					<span id="barrio-label" class="property-label"><g:message code="restaurante.barrio.label" default="Barrio" /></span>
					
						<span class="property-value" aria-labelledby="barrio-label"><g:fieldValue bean="${restauranteInstance}" field="barrio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${restauranteInstance?.capacidad}">
				<li class="fieldcontain">
					<span id="capacidad-label" class="property-label"><g:message code="restaurante.capacidad.label" default="Capacidad" /></span>
					
						<span class="property-value" aria-labelledby="capacidad-label"><g:fieldValue bean="${restauranteInstance}" field="capacidad"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:restauranteInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${restauranteInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
