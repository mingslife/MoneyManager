angular.module("app", []).controller("controller", function($scope) {
	$scope.timestamp = new Date().getTime();
	$scope.changeValidCode = function() {
		$scope.timestamp = new Date().getTime();
	};
	$scope.login = function() {
		$.ajax({
			url: "user_login.action",
			data: {
				username: hex_md5($scope.username),
				password: hex_md5($scope.password),
				validCode: $scope.validCode
			},
			type: "POST",
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				if (data.result === "success") {
					window.location.href = data.url;
				} else {
					alert(data.result);
					$scope.changeValidCode();
				}
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.init = function() {
		$.ajax({
			url: "user_test.action",
			data: {},
			type: "POST",
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				if (data.result === "success") {
					document.write("检测到您已登录，正在跳转中...");
					window.location.href = data.url;
				}
			},
			error: function() {}
		});
	};
	$scope.init();
});
/* By Ming */
