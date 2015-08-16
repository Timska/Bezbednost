app.controller('NewAuctionController', ['$scope', '$http', 'userService', 'newAuctionService', function($scope, $http, userService, newAuctionService){
	var newAuctionService;
	$scope.addAuction = function(){
		
		createAuction();
		
		var request = $http({
			method: "post",
			url: "https://spiritbreakers.com.mk:8443/OnlineBidding/addauction",
			data: newAuctionService,
		});
		
	}
	
	var createAuction = function(){
		newAuctionService.auctionName = $scope.auctionName;
		newAuctionService.creator = userService;
		newAuctionService.winner = userService;
		newAuctionService.item.itemName = $scope.itemName;
		newAuctionService.item.description = $scope.itemDescription;
		newAuctionService.currentPrice = $scope.itemPrice;
	}
}]);