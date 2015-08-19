app.controller('LoginController', [ '$scope', '$http', '$window', '$log', '$cookies', 'transformRequestAsFormPost', function($scope, $http, $window, $log, $cookies, transformRequestAsFormPost){
	$scope.username='';
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
		
		var secondRequest = $http({
			method: "post",
			url: "https://spiritbreakers.com.mk:8443/OnlineBidding/getuser",
			data: $scope.username,
		});
		
		secondRequest.success(function(data){
			$scope.result = data;
			$cookies.put('username', data.userName);
			$cookies.putObject('user', data);
		});
		
	}
	
	var isNullOrEmptyOrUndefined = function (value) {
	    if (value === "" || value === null || typeof value === "undefined") {
	        return true;
	    }
	    return false;
	};
	
	
	$scope.submit = function(){
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