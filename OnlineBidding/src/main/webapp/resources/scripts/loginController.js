app.controller('LoginController', [ '$scope', '$http', '$window', '$log', 'userService', 'transformRequestAsFormPost', function($scope, $http, $window, $log, userService, transformRequestAsFormPost){
	$scope.username='SpiritBreakersAdmin';
	$scope.password='';
	$scope.result = '';
	
	$scope.invalidUsername = false;
	$scope.invalidPassword = false;
	
	$scope.usernameChange = function(){
		$scope.invalidUsername = false;
	}
	
	$scope.passwordChange = function(){
		$scope.invalidPassword = false;
	}
	
	
	getUserFromDatabase = function(username){
		var request = $http({
			method: "post",
			url: "https://spiritbreakers.com.mk:8443/OnlineBidding/getadministrator",
			data: $scope.username,
		});
		
		request.success(function(data){
			userService = data;
			$log.log(data);
		});
	}
	
	
	$scope.submit = function(){
		$window.alert('vleze vo submit');
		var request = $http({
			method: "post",
			url: "https://spiritbreakers.com.mk:8443/OnlineBidding/checkloginadministrator",
			data: {
				userName: $scope.username, 
				password: $scope.password
			},
			headers: { 
				'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
			},
			transformRequest: transformRequestAsFormPost,
			transformResponse: []
		});
		
		request.success(function(data){
			$scope.result = data;
			$log.log(data);
			processResult();
		});
		
		processResult = function(){
			if($scope.result == '1'){
				$scope.invalidUsername = true;
			}
			else if($scope.result == '2'){
				$scope.invalidPassword = true;
			}
			else if($scope.result == 'correct'){
				getUserFromDatabase($scope.username);
				$window.location.href = 'https://spiritbreakers.com.mk:8443/OnlineBidding/administrator';
			}
		}
	}
}]);