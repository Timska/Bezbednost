var app = angular.module('myApp', ['ui.bootstrap']);

app.factory('transformRequestAsFormPost', function(){
	function transformRequest(data, getHeaders) {
		var headers = getHeaders();
		headers["Content-type"] = "application/x-www-form-urlencoded; charset=utf-8";
		return(serializeData(data));
	}
	
	return transformRequest;
	
	function serializeData(data){
		if(!angular.isObject(data)){
			return data == null ? "" : data.toString();
		}
		
		var buffer = [];
		for(var name in data){
			if(!data.hasOwnProperty(name)){
				continue;
			}
			var value = data[name];
			buffer.push(encodeURIComponent(name) + "=" + encodeURIComponent(value == null ? "" : value));
		}
		
		var source = buffer.join("&").replace(/%20/g,"+");
		
		return source;
	}
});

app.factory('userService', function(){
	var onlineAdministrator = {};
	var onlineUser = {};
	var isNotUser;
	return {
		onlineAdministrator: onlineAdministrator,
		onlineUser: onlineUser,
		isNotUser: isNotUser
	};
});


app.factory('newAuctionService', function(){
	var auction = {};
	auction.item = {};
	return auction;
});


app.value("$sanitize", function(html) {
	return(html);
});
