app.controller('StartTimepickerController', ['$scope', 'newAuctionService', function($scope, newAuctionService){
	 $scope.starttime = new Date();

	 $scope.hstep = 1;
	 $scope.mstep = 1;

	 $scope.options = {
	    hstep: [1, 2, 3],
	    mstep: [1, 5, 10, 15, 25, 30]
	  };

	 $scope.ismeridian = false;

	 $scope.update = function() {
	    var d = new Date();
	    d.setHours( 14 );
	    d.setMinutes( 0 );
	    $scope.starttime = d;
	 };

	 $scope.changed = function () {
		 newAuctionService.startDate = new Date();
		newAuctionService.startDate.setHours($scope.starttime.getHours());
		newAuctionService.startDate.setMinutes($scope.starttime.getMinutes());
	 };
	  
	  $scope.clear = function() {
	    $scope.starttime = null;
	  };
}]);

app.controller('EndTimepickerController', ['$scope', 'newAuctionService', function($scope, newAuctionService){
	 $scope.endtime = new Date();

	  $scope.hstep = 1;
	  $scope.mstep = 1;

	  $scope.options = {
	    hstep: [1, 2, 3],
	    mstep: [1, 5, 10, 15, 25, 30]
	  };

	  $scope.ismeridian = false;

	  $scope.update = function() {
	    var d = new Date();
	    d.setHours( 14 );
	    d.setMinutes( 0 );
	    $scope.endtime = d;
	  };
	  
	  $scope.changed = function () {
		  newAuctionService.endDate = new Date();
		  newAuctionService.endDate.setHours($scope.endtime.getHours());
		  newAuctionService.endDate.setMinutes($scope.endtime.getMinutes());
	  };

	  $scope.clear = function() {
	    $scope.endtime = null;
	  };
}]);