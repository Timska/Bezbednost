<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<script src= "https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Administrator page</title>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"
	rel="stylesheet">

<link href="<c:url value="/resources/css/administrator.css" />" rel="stylesheet">

</head>
<body>
<div id="tab-container">
	<div class="container">
		<ul class="nav nav-pills">
			<li class="active"><a data-toggle="pill" href="#auctions">Аукции</a></li>
			<li><a data-toggle="pill" href="#users">Корисници</a></li>
		</ul>
	</div>
	
	<div class="tab-content">
		<div id="auctions" class="tab-pane fade in active">
			<h3>Аукции</h3>
		</div>
		<div id="users" class="tab-pane fade">
			<h3>Корисници</h3>
		</div>
	</div>
</div>

<div id="add-auction">
	<form>
		<h2>Внесете податоци</h2>
			<label for="inputUsername" class="sr-only">Корисничко име</label> <input
				type="text" id="inputUsername" ng-model="username" placeholder="Корисничко име"
				class="form-control"> <label for="inputPassword"
				class="sr-only">Лозинка</label> <input type="password"
				id="inputPassword" ng-model="password" placeholder="Лозинка" class="form-control">
			<br>
			<button class="btn btn-lg btn-primary btn-block" type="submit">
			Најави се
			</button>
		</form>
</div>
</body>
</html>