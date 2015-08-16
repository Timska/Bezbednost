app.controller('StartDatepickerController', ['$scope', 'newAuctionService', function($scope, newAuctionService){
	$scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
	$scope.format = $scope.formats[0];
	
	$scope.start;
	
	$scope.today = function() {
	    $scope.start = new Date();
	};
	
	$scope.today();

	$scope.clear = function () {
	    $scope.start = null;
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
	 
	 $scope.changed = function () {
		newAuctionService.startDate.setFullYear(start.getFullYear());
		newAuctionService.startDate.setMonth(start.getMonth());
		newAuctionService.startDate.setDate(start.getDate());
	 };
}]);


app.controller('EndDatepickerController', ['$scope', 'newAuctionService', function($scope, newAuctionService){
	$scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
	$scope.format = $scope.formats[0];
	
	$scope.end;
	
	$scope.today = function() {
	    $scope.end = new Date();
	};
	
	$scope.today();

	$scope.clear = function () {
	    $scope.end = null;
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
	 
	 $scope.changed = function () {
		newAuctionService.endDate.setFullYear(end.getFullYear());
		newAuctionService.endDate.setMonth(end.getMonth());
		newAuctionService.endDate.setDate(end.getDate());
	 };
}]);