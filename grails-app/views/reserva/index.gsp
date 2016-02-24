
<%@ page import="qcreserva.Reserva" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reserva.label', default: 'Reserva')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-reserva" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-reserva" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="restaurante" title="${message(code: 'reserva.restaurante.label', default: 'Restaurante')}" />
					
						<g:sortableColumn property="comensales" title="${message(code: 'reserva.comensales.label', default: 'Comensales')}" />
					
						<g:sortableColumn property="fecha" title="${message(code: 'reserva.fecha.label', default: 'Fecha')}" />
					
						<g:sortableColumn property="hora" title="${message(code: 'reserva.hora.label', default: 'Hora')}" />
					
						<g:sortableColumn property="usuario" title="${message(code: 'reserva.usuario.label', default: 'Usuario')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${reservaInstanceList}" status="i" var="reservaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${reservaInstance.id}">${fieldValue(bean: reservaInstance, field: "restaurante")}</g:link></td>
					
						<td>${fieldValue(bean: reservaInstance, field: "comensales")}</td>
					
						<td><g:formatDate date="${reservaInstance.fecha}" /></td>
										
						<td>${fieldValue(bean: reservaInstance, field: "hora")}</td>
					
						<td>${fieldValue(bean: reservaInstance, field: "usuario")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${reservaInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
