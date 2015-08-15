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

app.controller('LoginController', [ '$scope', '$http', '$window', 'transformRequestAsFormPost', function($scope, $http, $window, transformRequestAsFormPost){
	$scope.username='SpiritBreakersAdmin';
	$scope.password='';
	$scope.result = '';
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
			transformRequest: transform
		});
		
		var transform = function(data){
	        return $.param(data);
	    }
		
		request.success(function(data){
			$scope.result = data;
			$window.alert(data);
		});
	}
}]);

app.value("$sanitize", function(html) {
	return(html);
});
