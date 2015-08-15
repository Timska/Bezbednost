var app = angular.module('myApp', []);

app.factory('transformRequestAsFormPost', function(){
	function transformRequest(data, getHeaders) {
		var headers = getHeaders();
		headers["Content-type"] = "application/x-www-form-urlencoded; charset=utf-8";
		return(serializeData(data));
	}
	
	return transformRequest;
	
	function serializeData(data){
		if(!angular.isObject(data)){
			return data == null ? "" : data.toString();
		}
		
		var buffer = [];
		for(var name in data){
			if(!data.hasOwnProperty(name)){
				continue;
			}
			var value = data[name];
			buffer.push(encodeURIComponent(name) + "=" + encodeURIComponent(value == null ? "" : value));
		}
		
		var source = buffer.join("&").replace(/%20/g,"+");
		
		return source;
	}
});

app.controller('LoginController', [ '$http', 'transformRequestAsFormPost', function($scope, $http, transformRequestAsFormPost){
	$scope.username='';
	$scope.password='';
	$scope.result = '';
	$scope.submit = function(){
		var request = $http({
			method: "post",
			url: "https://spiritbreakers.com.mk:8443/OnlineBidding/checkloginadministrator",
			data: {
				userName: $scope.username, 
				password: $scope.password
			}
		});
		request.success(function(data){
			$scope.result = data;
		});
	}
}]);

app.value("$sanitize", function(html) {
	return(html);
});
