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
			$window.alert("vo administrator success");
			userService.onlineAdministrator = data;
			$log.log(data);
		});
		
		var secondRequest = $http({
			method: "post",
			url: "https://spiritbreakers.com.mk:8443/OnlineBidding/getuser",
			data: $scope.username,
		});
		
		secondRequest.success(function(data){
			
			$window.alert("vo user success");
			
			userService.isNotUser = isNullOrEmptyOrUndefined(data);
			
			$window.alert(userService.isNotUser);
			
			
			userService.onlineUser = data;
			
			$window.alert(userService.onlineUser.userName);
			$log.log(data);
		});
		
		var isNullOrEmptyOrUndefined = function (value) {
		    if (value === "" || value === null || typeof value === "undefined") {
		        return true;
		    }
		    return false;
		};
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