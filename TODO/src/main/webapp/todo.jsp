<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
	<title>TODO</title>
	<link href="${contextPath}/resources/css/todo.css" rel="stylesheet">
</head>

<body>
	<div class="container">
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<form id="logoutForm" method="POST" action="${contextPath}/logout">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
			<h2> Welcome ${pageContext.request.userPrincipal.name} | <button onclick="document.forms['logoutForm'].submit()">Logout</button> </h2>
		</c:if>
	</div>

	<div class="container">
		<section>
			<header id="header">
				<h1>todo</h1>
				<form action="<c:url value="insert"/>" method="POST">
					<input type="hidden" name="filter" value="${filter}" /> 
					<input id="new-todo" name="desc" placeholder="Add task" autofocus>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form>
			</header>
			<section id="main">
				<ul id="todo-list">
					<c:forEach var="task" items="${tasks}" varStatus="status">
						<li id="task_${status.count}" class="<c:if test="${task.isCompleted}">completed</c:if>" ondblclick="javascript:document.getElementById('task_${status.count}').className += ' editing';document.getElementById('taskDesc_${status.count}').focus();">
							<div>
								<form id="toggleForm_${status.count}" action="<c:url value="toggleCompleted"/>" method="POST">
									<input type="hidden" name="id" value="${task.id}" /> 
									<input type="hidden" name="filter" value="${filter}" />
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<input name="toggle" type="checkbox" <c:if test="${task.isCompleted}">checked</c:if> onchange="javascript:document.getElementById('toggleForm_${status.count}').submit();">
								</form>
								Task Desc: <label>${task.desc}</label>
								Created On : <label>${task.crtDt}</label>
								Modified On : <label>${task.lastUpdDt}</label>
								<form action="<c:url value="delete"/>" method="POST">
									<input type="hidden" name="id" value="${task.id}" /> 
									<input type="hidden" name="filter" value="${filter}" />
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<button class="delete"></button>
								</form>
							</div>
							<form id="updateForm_${status.count}" action="<c:url value="update"/>" method="POST">
								<input type="hidden" name="id" value="${task.id}" /> 
								<input type="hidden" name="filter" value="${filter}" />
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								Update task desc here : <input id="taskDesc_${status.count}" name="desc" value="${task.desc}" onblur="javascript:document.getElementById('updateForm_${status.count}').submit();" />
							</form>
						</li>
					</c:forEach>
				</ul>
			</section>
		</section>
	</div>
</body>
</html>
