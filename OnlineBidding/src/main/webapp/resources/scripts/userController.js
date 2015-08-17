app.controller('UsersController', ['$scope', '$http', '$window', '$modal', function($scope, $http, $window, $modal){
	
	var my = $scope;
	my.users = [];
	
	$scope.initUsers = function(){
		$http.get('https://spiritbreakers.com.mk:8443/OnlineBidding/getallusers').success(function(data){
			my.users = data;
		});
	};
	
	$scope.addCredit = function(user){
		var modalInstance = $modal.open({
			templateUrl: '/OnlineBidding/resources/html/modal.html',
			controller: 'ModalController',
			size: 'sm'
		});
		
		modalInstance.result.then(function(credit){
			user.credit = parseInt(user.credit, 10) + parseInt(credit, 10);
			user.active = true;
			
			var request = $http({
				method: "post",
				url: "https://spiritbreakers.com.mk:8443/OnlineBidding/updateuser",
				data: user,
				transformResponse: []
			});
			
		});
	};
	
	$scope.deleteUser = function(user){
		var request = $http({
			method: "post",
			url: "https://spiritbreakers.com.mk:8443/OnlineBidding/deleteuser",
			data: user,
			transformResponse: []
		});
		
		request.success(function(){
			$http.get('https://spiritbreakers.com.mk:8443/OnlineBidding/getallusers').success(function(data){
				my.users = data;
			});
		});
	}
}]);