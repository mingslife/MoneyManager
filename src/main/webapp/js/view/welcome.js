app.controller("welcomeController", function($scope) {
	$scope.init = function() {
		$.ajax({
			url: "user_welcome.action",
			type: "POST",
			dataType: "json",
			data: {},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				$scope.userName = data.userName;
				$scope.numberOfUncheck = data.numberOfUncheck;
				$scope.amountOfUncheck = data.amountOfUncheck;
				$scope.numberOfUnpaid = data.numberOfUnpaid;
				$scope.amountOfUnpaid = data.amountOfUnpaid;
				$scope.$apply();
				if (data.result)
					alert(data.result);
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.init();
});
/* By Ming */
