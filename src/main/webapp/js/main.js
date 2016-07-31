function windowResize() {
	var minHeight = document.documentElement.clientHeight - 165 + "px";
	$("#main").css("minHeight", minHeight);
}
function doubleToDate(data) {
	data = String(data);
	return new Date(data.substr(0, 4) + "/" + data.substr(4, 2) + "/" + data.substring(6));
}
function dateToDouble(data) {
	data = new Date(data);
	var year = data.getFullYear();
	var month = data.getMonth() + 1;
	var day = data.getDate();
	return year + (month > 9 ? "" : "0") + month + (day > 9 ? "" : "0") + day;
}
function dateFormat(data) {
	data = String(data);
	return data.substr(0, 4) + "/" + data.substr(4, 2) + "/" + data.substring(6);
}
function dateTimeFormat(data) {
	data = String(data);
	return data.substr(0, 4) + "/" + data.substr(4, 2) + "/" + data.substr(6, 2) + " " + data.substr(8, 2) + ":" + data.substr(10, 2) + ":" + data.substring(12);
}
function showAlert(message, type, time) {
//	$("#message-alert").html(message).attr("class", "alert alert-" + type).fadeIn();
//	if (time > 0)
//		setTimeout("hideAlert()", time);
}
function hideAlert() {
//	$("#message-alert").fadeOut();
}

var mainScope;
var messageNumber;
var app = angular.module("app", ["ngRoute"]);
app.config(["$routeProvider", function($routeProvider) {
	$routeProvider
		.when("/",
		{
			templateUrl: "view/welcome.html",
			controller: "welcomeController"
		})
	.when("/user",
		{
			templateUrl: "view/user.html",
			controller: "userController"
		})
	.when("/billforin",
		{
			templateUrl: "view/billforin.html",
			controller: "billForInController"
		})
	.when("/billforout",
		{
			templateUrl: "view/billforout.html",
			controller: "billForOutController"
		})
	.when("/apply",
		{
			templateUrl: "view/apply.html",
			controller: "applyController"
		})
	.when("/review",
		{
			templateUrl: "view/review.html",
			controller: "reviewController"
		})
	.when("/pay",
		{
			templateUrl: "view/pay.html",
			controller: "payController"
		})
	.when("/about",
		{
			templateUrl: "view/about.html",
			controller: "aboutController"
		});
}]);
app.controller("mainController", function($scope, $location) {
	$scope.dateTimeFormat = function(data) {
		return dateTimeFormat(data);
	};
	$scope.alertType = function(type) {
		switch (type) {
		case "0": return "alert-success"; break;
		case "1": return "alert-info"; break;
		case "2": return "alert-warning"; break;
		case "3": return "alert-danger"; break;
		default: return "";
		}
	};
	$scope.$watch(function() {return $location.path();}, function() {
		$scope.currentPage = $location.path();
	});
	$scope.loadMenus = function() {
		$.ajax({
			url: "menu_loadMine.action",
			type: "POST",
			dataType: "json",
			data: {},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				$scope.menus = data;
				$scope.$apply();
				if (data.result)
					alert(data.result);
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.loadRoles = function() {
		$.ajax({
			url: "role_load.action",
			type: "POST",
			dataType: "json",
			data: {},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				$scope.roles = data;
				$scope.$apply();
				if (data.result)
					alert(data.result);
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.logout = function() {
		if (confirm("确认注销？")) {
			$.ajax({
				url: "user_logout.action",
				type: "POST",
				dataType: "json",
				data: {},
				beforeSend: function() {},
				complete: function() {
					window.location.href = "index.html";
				},
				success: function(data) {
				},
				error: function() {}
			});
		}
	};
	$scope.resetBillForOut = function() {
		$scope.apply = {
			date: new Date()
		};
	};
	$scope.applyBillForOut = function() {
		var billForOut = {
			date: dateToDouble($scope.apply.date),
			event: $.trim($scope.apply.event),
			amount: $.trim($scope.apply.amount),
			remark: $.trim($scope.apply.remark)
		};
		$.ajax({
			url: "billForOut_apply.action",
			type: "POST",
			dataType: "json",
			data: {
				billForOut: JSON.stringify(billForOut),
				validCode: $scope.apply.validCode
			},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				if (data.result === "success") {
					alert("报账提交成功，请耐心等待审核");
					$("#apply-modal").modal("hide");
				} else {
					alert(data.result);
				}
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.countMessages = function() {
		$.ajax({
			url: "message_countMine.action",
			type: "POST",
			dataType: "json",
			data: {},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				if (data.count > 0) {
					$scope.messageNumber = data.count;
					messageNumber = data.count;
				} else {
					$scope.messageNumber = null;
					messageNumber = null;
				}
				$scope.$apply();
				if (data.result)
					alert(data.result);
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.loadMessages = function() {
		$.ajax({
			url: "message_loadMine.action",
			type: "POST",
			dataType: "json",
			data: {},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				$scope.countMessages();
				$scope.messages = data;
				$scope.$apply();
				if (data.result)
					alert(data.result);
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.deleteMessage = function(id) {
		$.ajax({
			url: "message_deleteMine.action",
			type: "POST",
			dataType: "json",
			data: {
				id: id
			},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				if (data.result === "success") {
					$scope.loadMessages();
				} else {
					alert(data.result);
				}
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.changeValidCode = function() {
		$scope.timestamp = new Date().getTime();
	};
	$scope.loadSelf = function() {
		$scope.changePassword = false;
		$scope.timestamp = new Date().getTime();
		$scope.loadRoles();
		$.ajax({
			url: "user_loadMine.action",
			type: "POST",
			dataType: "json",
			data: {},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				$scope.self = data;
				$scope.self.birthday = doubleToDate(data.birthday);
				$scope.$apply();
				if (data.result)
					alert(data.result);
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.toggleChangePassword = function() {
		$scope.changePassword = !$scope.changePassword;
		setTimeout("$('#self-modal').modal('handleUpdate')", 0);
	};
	$scope.saveSelf = function() {
		var data = {};
		var user = {
			birthday: dateToDouble($scope.self.birthday),
			email: $.trim($scope.self.email),
			qq: $.trim($scope.self.qq)
		};
		if ($scope.changePassword) {
			user.password = hex_md5($scope.self.newPassword);
			data.password = hex_md5($scope.self.oldPassword);
		}
		data.user = JSON.stringify(user);
		data.validCode = $scope.self.validCode;
		$.ajax({
			url: "user_editMine.action",
			type: "POST",
			dataType: "json",
			data: data,
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				if (data.result === "success") {
					alert("修改成功");
					$("#self-modal").modal("hide");
				} else {
					alert(data.result);
				}
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.init = function() {
		mainScope = $scope;
		$scope.loadMenus();
		$scope.countMessages();
		setInterval("mainScope.countMessages()", 30000); // 轮询
	};
	$scope.init();
});

$(document).ready(function() {
	$("[data-toggle='tooltip']").tooltip();
	$(window).resize(function() {
		windowResize();
	});
	windowResize();
});
/* By Ming */
