<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<meta charset="utf-8">
<title>Login form</title>
<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
</head>

<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>

<body>
	<div class="container">
		<form action="">
		<h2>Внесете податоци</h2>
		<label for="inputUsername" class="sr-only">Корисничко име</label>
		<input type="text" id="inputUsername" class="form-control" required autofocus>
		<label for="inputPassword" class="sr-only">Лозинка</label>
		<input type="password" id="inputPassword" class="form-control" required>
		<br>
		<button class="btn btn-lg btn-primary btn-block" type="submit">Најави се</button>
		</form>
	</div>
	
</body>
</html>