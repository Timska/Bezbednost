app.controller('MainController', ['$scope', '$http', '$window', '$cookies', 'newAuctionService', function($scope, $http, $window, $cookies, newAuctionService){
	var my = $scope;
	my.auctions = [];
	
	$scope.username = $cookies.get('username');
	$scope.user = $cookies.getObject('user');
	
	$scope.addClicked = false;
	$scope.editClicked = false;
	
	$scope.initAuctions = function(){
		$http.get('https://spiritbreakers.com.mk:8443/OnlineBidding/getallauctions').success(function(data){
			my.auctions = data;
		});
		
	};
	
	$scope.checkRedirect = function(){
		if($scope.username === undefined){
			$window.location.href = 'https://spiritbreakers.com.mk:8443/OnlineBidding/loginadministrator';
		}
		else{
			$scope.initAuctions();
		}
	};
	
	$scope.deleteAuction = function(){
		var request = $http({
			method: "post",
			url: "https://spiritbreakers.com.mk:8443/OnlineBidding/deleteauction",
			data: $scope.opennedAuction,
			transformResponse: []
		});
		
		request.success(function(data){
			$window.alert("daaa");
			$scope.initAuctions();
			$scope.editClicked = false;
		}) 
	}
	
	$scope.started = false;
	$scope.opennedAuction = {};
	$scope.editAuction = function(auction){
		$scope.opennedAuction = auction;
		
		$scope.editClicked = true;
		$scope.addClicked = false;
		$scope.editAuctionName = auction.auctionName;
		$scope.editItemName = auction.item.itemName;
		$scope.editItemDescription = auction.item.description;
		$scope.editAuctonCreator = auction.creator.userName;
		$scope.editAuctionWinner = auction.winner.userName;
		$scope.editAuctionStart = new Date(parseInt(auction.startDate,10)).toUTCString();
		$scope.editAuctionEnd = new Date(parseInt(auction.endDate,10)).toUTCString();
		$scope.editStarttime = auction.startDate;
		$scope.editEndtime = auction.endDate;
		$scope.editItemPrice = auction.currentPrice;
		var now = new Date();
		if(now >= auction.startDate){
			$scope.started = true;
		}
		else{
			$scope.started = false;
		}
		
	}
	
	
	
	
	var newAuctionService;
	
	$scope.showNewAuction = function(){
		$scope.addClicked = true;
		$scope.editClicked = false;
	}
	
	$scope.addAuction = function(){
		$window.alert("vo add");
		var request = $http({
			method: "post",
			url: "https://spiritbreakers.com.mk:8443/OnlineBidding/addauction",
			data: newAuctionService,
			transformResponse: []
		});
		
		request.success(function(data){
			$scope.addClicked = false;
			$scope.initAuctions();
		})
		
	};
	
	
	
	$scope.auctionName = '';
	$scope.itemName = '';
	$scope.itemDescription = '';
	$scope.itemPrice = '';
	
	$scope.createAuction = function(){
		newAuctionService.auctionName = $scope.auctionName;
		newAuctionService.creator = $scope.user;
		newAuctionService.winner = $scope.user;
		newAuctionService.item.itemName = $scope.itemName;
		newAuctionService.item.description = $scope.itemDescription;
		newAuctionService.currentPrice = $scope.itemPrice;
		
		$scope.addAuction();
	}
	
}]);