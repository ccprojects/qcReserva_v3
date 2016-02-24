
<%@ page import="qcreserva.Restaurante" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'restaurante.label', default: 'Restaurante')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-restaurante" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-restaurante" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="restaurante" title="${message(code: 'restaurante.restaurante.label', default: 'Restaurante')}" />
					
						<g:sortableColumn property="especialidad" title="${message(code: 'restaurante.especialidad.label', default: 'Especialidad')}" />
					
						<g:sortableColumn property="email" title="${message(code: 'restaurante.email.label', default: 'Email')}" />
					
						<g:sortableColumn property="telefono" title="${message(code: 'restaurante.telefono.label', default: 'Telefono')}" />
					
						<g:sortableColumn property="calle_nro" title="${message(code: 'restaurante.calle_nro.label', default: 'Callenro')}" />
					
						<g:sortableColumn property="barrio" title="${message(code: 'restaurante.barrio.label', default: 'Barrio')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${restauranteInstanceList}" status="i" var="restauranteInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${restauranteInstance.id}">${fieldValue(bean: restauranteInstance, field: "restaurante")}</g:link></td>
					
						<td>${fieldValue(bean: restauranteInstance, field: "especialidad")}</td>
					
						<td>${fieldValue(bean: restauranteInstance, field: "email")}</td>
					
						<td>${fieldValue(bean: restauranteInstance, field: "telefono")}</td>
					
						<td>${fieldValue(bean: restauranteInstance, field: "calle_nro")}</td>
					
						<td>${fieldValue(bean: restauranteInstance, field: "barrio")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${restauranteInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
