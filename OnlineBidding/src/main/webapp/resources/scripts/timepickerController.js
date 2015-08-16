app.controller('StartTimepickerController', ['$scope', 'newAuctionService', function($scope, newAuctionService){
	 $scope.starttime = new Date();

	  $scope.hstep = 1;
	  $scope.mstep = 15;

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
		newAuctionService.startDate.setHours(starttime.getHours());
		newAuctionService.startDate.setMinutes(starttime.getMinutes());
	  };
	  
	  $scope.clear = function() {
	    $scope.starttime = null;
	  };
}]);

app.controller('EndTimepickerController', ['$scope', 'newAuctionService', function($scope, newAuctionService){
	 $scope.endtime = new Date();

	  $scope.hstep = 1;
	  $scope.mstep = 15;

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
		  newAuctionService.endDate.setHours(endtime.getHours());
		  newAuctionService.endDate.setMinutes(endtime.getMinutes());
	  };

	  $scope.clear = function() {
	    $scope.endtime = null;
	  };
}]);