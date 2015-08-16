app.controller('AuctionsController', ['$scope', '$http', '$window', 'userService', function($scope, $http, $window, userService){
	var my = $scope;
	my.auctions = [];
	my.userauctions = [];
	
	$scope.addClicked = false;
	$scope.editClicked = false;
	
	$scope.initAuctions = function(){
		$http.get('https://spiritbreakers.com.mk:8443/OnlineBidding/getallauctions').success(function(data){
			my.auctions = data;
		});
		
		var request = $http({
			method: "post",
			url: "https://spiritbreakers.com.mk:8443/OnlineBidding/userauctions",
			data: userService,
		});
		
		request.success(function(data){
			my.userauctions = data;
		});
	};
	
	$scope.myauction = function(userauction){
		for(auction in $scope.userauctions){
			if(auction.id === userauction.id){
				return true;
			}
		}
		return false;
	};
	
	$scope.addAuction = function(){
		$scope.addClicked = true;
		$window.alert($scope.addClicked);
		$scope.editClicked = false;
	};
}]);