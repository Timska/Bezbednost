<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<script src= "https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="https://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.13.3.js"></script>

<script src="<c:url value="/resources/scripts/myApp.js" />"></script>
<script src="<c:url value="/resources/scripts/auctionsController.js" />"></script>
<script src="<c:url value="/resources/scripts/loginController.js" />"></script>
<script src="<c:url value="/resources/scripts/userController.js" />"></script>
<script src="<c:url value="/resources/scripts/modalController.js" />"></script>
<script src="<c:url value="/resources/scripts/datepickerController.js" />"></script>
<script src="<c:url value="/resources/scripts/timepickerController.js" />"></script>
<script src="<c:url value="/resources/scripts/newAuctionController.js" />"></script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Administrator page</title>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"
	rel="stylesheet">

<link href="<c:url value="/resources/css/administrator.css" />" rel="stylesheet">

</head>
<body ng-app="myApp" ng-controller="AuctionsController" ng-init="initAuctions()">
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
			<table class="table table-striped">
  				<thead>
    				<tr>
      					<th></th>
      					<th>Аукција</th>
     					<th>Креатор</th>
     					<th>Цена</th>
    				</tr>
  				</thead>
  				<tbody>
    				<tr ng-repeat="auction in auctions">
      					<td>
        					<button class="btn" ng-show="myauction(auction)">
         						<span class="glyphicon glyphicon-pencil"></span>  Edit
        					</button>
      					</td>
      					<td>{{ auction.auctionName }}</td>
      					<td>{{ auction.creator.userName }}</td>
      					<td>{{ auction.currentPrice }}</td>
   					</tr>
  				</tbody>
			</table>
			
			<hr>
				<button class="btn btn-success" ng-click="addAuction()" ng-disabled="isNotUser">
					 Креирај аукција
				</button>
			<hr>
		</div>
		<div id="users" class="tab-pane fade" ng-controller="UsersController" ng-init="initUsers()">
			<h3>Корисници</h3>
			<table class="table table-striped">
  				<thead>
    				<tr>
      					<th></th>
      					<th>Корисничко име</th>
      					<th>Маил</th>
      					<th>Кредит</th>
      					<th></th>
    				</tr>
  				</thead>
  				<tbody>
    				<tr ng-repeat="user in users">
      					<td>
        					<button class="btn" ng-click="addCredit(user)">
        						Додади кредит
        					</button>
      					</td>
      					<td>{{user.userName}}</td>
      					<td>{{user.mail}}</td>
      					<td>{{user.credit}}</td>
      					<td>
        					<button class="btn" ng-click="deleteUser(user)">
        						Избриши
        					</button>
      					</td>
    				</tr>
  				</tbody>
			</table>
		</div>
	</div>
</div>

<div id="add-auction" ng-show="addClicked" ng-controller="NewAuctionController">
	<form>
		<h2>Нова аукција</h2>
			<label for="inputName" class="sr-only">Име на аукција</label>
			<input type="text" id="inputName" ng-model="auctionName" placeholder="Име на аукција" class="form-control">
			
			<label for="inputItemName" class="sr-only">Име на предмет</label>
			<input type="text" id="inputItemName" ng-model="itemName" placeholder="Име на предмет" class="form-control">
			
			<label for="inputItemDescription" class="sr-only">Опис на предмет</label>
			<input type="text" id="inputItemDescription" ng-model="itemDescription" placeholder="Опис на предмет" class="form-control">
			
			<div>
			<div ng-controller="StartDatepickerController" class="col-md-6">
            	<p class="input-group">
            		<label for="inputStartDate">Почеток на аукција</label>
              		<input type="text" class="form-control" ng-change="changed()" datepicker-popup="{{}}" ng-model="start" is-open="status.opened" min-date="minDate" max-date="'2020-06-22'" datepicker-options="dateOptions" ng-required="true" close-text="Close" />
              		<span class="input-group-btn" class="form-control">
              			<button type="button" class="btn btn-default" ng-click="open($event)"><i class="glyphicon glyphicon-calendar"></i></button>
            		</span>
            	</p>
            </div>
            <div ng-controller="StartTimepickerController">
        		<p class="input-group">
        			<timepicker ng-model="starttime" ng-change="changed()" hour-step="hstep" minute-step="mstep" show-meridian="ismeridian"></timepicker>
        		</p>
        	</div>
            </div>
            
            <div>
            <div ng-controller="EndDatepickerController" class="col-md-6">
            	<p class="input-group">
            		<label for="inputEndDate">Крај на аукција</label>
              		<input type="text" class="form-control" ng-change="changed()" datepicker-popup="{{}}" ng-model="end" is-open="status.opened" min-date="minDate" max-date="'2020-06-22'" datepicker-options="dateOptions" ng-required="true" close-text="Close" />
              		<span class="input-group-btn">
              			<button type="button" class="btn btn-default" ng-click="open($event)"><i class="glyphicon glyphicon-calendar"></i></button>
            		</span>
            	</p>
        	</div>
        	<div ng-controller="EndTimepickerController">
        		<p class="input-group">
        			<timepicker ng-model="endtime" ng-change="changed()" hour-step="hstep" minute-step="mstep" show-meridian="ismeridian"></timepicker>
        		</p>
        	</div>
        	</div>
        	
        	<label for="inputItemPrice" class="sr-only">Цена на предмет</label>
			<input type="text" id="inputItemPrice" ng-model="itemPrice" placeholder="Цена на предмет" class="form-control">
        	<br>
			<button class="btn btn-lg btn-primary btn-block" type="submit" ng-click="addAuction()">
				Додади
			</button>
		</form>
</div>
</body>
</html>