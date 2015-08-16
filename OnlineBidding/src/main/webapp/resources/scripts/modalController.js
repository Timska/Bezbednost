app.controller('ModalController', ['$scope', '$modalInstance', function($scope, $modalInstance){
	$scope.credit;
	
	$scope.ok = function(){
		$modalInstance.close($scope.credit);
	};
	
	$scope.cancel = function(){
		$modalInstance.dismiss('cancel');
	};
	
}]);