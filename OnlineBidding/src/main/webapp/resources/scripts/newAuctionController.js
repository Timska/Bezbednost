app.controller('NewAuctionController', ['$scope', '$http', '$cookies', 'newAuctionService', function($scope, $http, $cookies, newAuctionService){
	var newAuctionService;
	$scope.addAuction = function(){
		
		createAuction();
		
		var request = $http({
			method: "post",
			url: "https://spiritbreakers.com.mk:8443/OnlineBidding/addauction",
			data: newAuctionService,
			transformResponse: []
		});
		
		request.success(function(data){
			$scope.addClicked = false;
		})
		
	};
	
	
	
	$scope.auctionName = '';
	$scope.itemName = '';
	$scope.itemDescription = '';
	$scope.itemPrice = '';
	
	var createAuction = function(){
		var user = $cookies.getObject('user');
		newAuctionService.auctionName = $scope.auctionName;
		newAuctionService.creator = user;
		newAuctionService.winner = user;
		newAuctionService.item.itemName = $scope.itemName;
		newAuctionService.item.description = $scope.itemDescription;
		newAuctionService.currentPrice = $scope.itemPrice;
	}
}]);