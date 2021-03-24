<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Log in</title>

</head>

<body>
	<div class="container">
		<form method="POST" action="${contextPath}/login">
			<h2>Log In</h2>
			<div ${error != null ? 'has-error' : ''}">
				<span>${message}</span> <input name="username" type="text" placeholder="Username" autofocus="true" />
				<input name="password" type="password" placeholder="Password" />
				<span>${error}</span> 
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<button type="submit">Log In</button>
				<h4>
					<a href="${contextPath}/registration">Create an account</a>
				</h4>
			</div>
		</form>
	</div>


</body>
</html>
