<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Login form</title>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"
	rel="stylesheet">
	
<link href="login.css" rel="stylesheet">
	
</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>

<body>
	<div class="container">
		<form class="form-signin" action="">
			<h2>Внесете податоци</h2>
			<label for="inputUsername" class="sr-only">Корисничко име</label> <input
				type="text" id="inputUsername" placeholder="Корисничко име"
				class="form-control"> <label for="inputPassword"
				class="sr-only">Лозинка</label> <input type="password"
				id="inputPassword" placeholder="Лозинка" class="form-control">
			<br>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Најави
				се</button>
		</form>
	</div>

</body>
</html>