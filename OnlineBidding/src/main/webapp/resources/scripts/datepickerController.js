app.controller('StartDatepickerController', ['$scope', 'newAuctionService', function($scope, newAuctionService){
	
	$scope.start = new Date();
	
	$scope.today = function() {
	    $scope.start = new Date();
	};

	
	$scope.toggleMin = function() {
	    $scope.minDate = $scope.minDate ? null : new Date();
	};
	$scope.toggleMin();
	
	$scope.open = function($event) {
	    $scope.status.opened = true;
	};
	
	$scope.status = {
		opened: false
	};
	
	$scope.dateOptions = {
		formatYear: 'yy',
		startingDay: 1
	};
	
	var tomorrow = new Date();
	tomorrow.setDate(tomorrow.getDate() + 1);
	var afterTomorrow = new Date();
	afterTomorrow.setDate(tomorrow.getDate() + 2);
	$scope.events = [{
		date: tomorrow,
	    status: 'full'
	 },
	 {
	    date: afterTomorrow,
	    status: 'partially'
	 }];

	$scope.getDayClass = function(date, mode) {
		if (mode === 'day') {
			var dayToCheck = new Date(date).setHours(0,0,0,0);

			for (var i=0;i<$scope.events.length;i++){
				var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

				if (dayToCheck === currentDay) {
					return $scope.events[i].status;
				}
			}
	    }
		
		return '';
	 };
	 
	 $scope.init = function() {
		 $scope.start = new Date();
		 newAuctionService.startDate = new Date();
		 newAuctionService.startDate.setFullYear($scope.start.getFullYear());
		 newAuctionService.startDate.setMonth($scope.start.getMonth());
		 newAuctionService.startDate.setDate($scope.start.getDate());
	 }
	 
	 $scope.changed = function () {
		newAuctionService.startDate = new Date();
		newAuctionService.startDate.setFullYear($scope.start.getFullYear());
		newAuctionService.startDate.setMonth($scope.start.getMonth());
		newAuctionService.startDate.setDate($scope.start.getDate());
	 };
}]);


app.controller('EndDatepickerController', ['$scope', '$window', 'newAuctionService', function($scope, $window, newAuctionService){
	$scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
	$scope.format = $scope.formats[0];
	
	$scope.end = new Date();
	
	$scope.today = function() {
	    $scope.end = new Date();
	    $window.alert(end);
	};

	$scope.clear = function () {
	    $scope.end = null;
	};
	
	$scope.toggleMin = function() {
	    $scope.minDate = $scope.minDate ? null : new Date();
	};
	$scope.toggleMin();
	
	$scope.open = function($event) {
		$scope.end = new Date();
	    $scope.status.opened = true;
	};
	
	$scope.status = {
		opened: false
	};
	
	$scope.dateOptions = {
		formatYear: 'yy',
		startingDay: 1
	};
	
	var tomorrow = new Date();
	tomorrow.setDate(tomorrow.getDate() + 1);
	var afterTomorrow = new Date();
	afterTomorrow.setDate(tomorrow.getDate() + 2);
	$scope.events = [{
		date: tomorrow,
	    status: 'full'
	 },
	 {
	    date: afterTomorrow,
	    status: 'partially'
	 }];

	$scope.getDayClass = function(date, mode) {
		if (mode === 'day') {
			var dayToCheck = new Date(date).setHours(0,0,0,0);

			for (var i=0;i<$scope.events.length;i++){
				var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

				if (dayToCheck === currentDay) {
					return $scope.events[i].status;
				}
			}
	    }
		
		return '';
	 };
	 
	 $scope.init = function() {
		 $scope.end = new Date();
		 newAuctionService.endDate = new Date();
		 newAuctionService.endDate.setFullYear($scope.end.getFullYear());
		 newAuctionService.endDate.setMonth($scope.end.getMonth());
		 newAuctionService.endDate.setDate($scope.end.getDate());
	 }
	 
	 $scope.changed = function () {
		newAuctionService.endDate = new Date();
		newAuctionService.endDate.setFullYear($scope.end.getFullYear());
		newAuctionService.endDate.setMonth($scope.end.getMonth());
		newAuctionService.endDate.setDate($scope.end.getDate());
	 };
}]);