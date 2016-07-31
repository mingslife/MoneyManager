app.controller("userController", function($scope) {
	$scope.dateFormat = function(data) {
		return dateFormat(data);
	};
	$scope.roleFormat = function(data) {
		if ($scope.roles && $scope.roles.length > 0) {
			for (var i = 0, length = $scope.roles.length; i < length; i++) {
				var role = $scope.roles[i];
				if (role.roleId == data)
					return role.roleName;
			}
		}
		return "";
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
	$scope.getCondition = function() {
		var condition = {
			userName: $scope.userName
		};
		
		return condition;
	};
	$scope.load = function() {
		$.ajax({
			url: "user_load.action",
			type: "POST",
			dataType: "json",
			data: {
				curPage: $scope.curPage,
				limit: $scope.limit,
				condition: JSON.stringify($scope.getCondition())
			},
			beforeSend: function() {
				showAlert("正在加载数据", "warning", 0);
			},
			complete: function() {
				showAlert("数据加载完成", "success", 1000);
			},
			success: function(data) {
				$scope.users = data;
				$scope.refreshPager();
				$scope.$apply();
				if (data.result)
					alert(data.result);
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.refreshPager = function() {
		switch ($scope.pages) {
		case 0: $scope.pager = [null, null, null]; break;
		case 1: $scope.pager = [1, null, null]; break;
		case 2: $scope.pager = [1, 2, null]; break;
		case 3: $scope.pager = [1, 2, 3]; break;
		default:
			if ($scope.curPage <= 2)
				$scope.pager = [1, 2, 3];
			else if ($scope.curPage >= $scope.pages - 1)
				$scope.pager = [$scope.pages - 2, $scope.pages - 1, $scope.pages];
			else
				$scope.pager = [$scope.curPage - 1, $scope.curPage, $scope.curPage + 1];
		}
	};
	$scope.firstPage = function() {
		$scope.curPage = 1;
		$scope.load();
	};
	$scope.lastPage = function() {
		$scope.curPage = $scope.pages;
		$scope.load();
	};
	$scope.prevPage = function() {
		$scope.curPage--;
		$scope.load();
	};
	$scope.nextPage = function() {
		$scope.curPage++;
		$scope.load();
	};
	$scope.changePage = function(page) {
		$scope.curPage = page;
		$scope.load();
	};
	$scope.reset = function() {
		$scope.user = {
			sex: 0,
			birthday: new Date(),
			email: "",
			roleId: $scope.roles[0].roleId,
			isActive: 0
		};
	};
	$scope.add = function() {
		$scope.modalTitle = "新增用户";
		$scope.mode = "save";
		$scope.reset();
	};
	$scope.edit = function(id) {
		$scope.modalTitle = "编辑用户";
		$scope.mode = "edit";
		$scope.reset();
		$.ajax({
			url: "user_loadOne.action",
			type: "POST",
			dataType: "json",
			data: {
				id: id
			},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				$scope.user = data;
				$scope.user.birthday = doubleToDate(data.birthday);
				$scope.$apply();
				if (data.result)
					alert(data.result);
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.delete = function(id) {
		if (confirm("确认删除？")) {
			$.ajax({
				url: "user_delete.action",
				type: "POST",
				dataType: "json",
				data: {
					id: id
				},
				beforeSend: function() {},
				complete: function() {},
				success: function(data) {
					if (data.result === "success") {
						alert("数据删除成功");
						$scope.init();
					} else {
						alert(data.result);
					}
				},
				error: function() {
					alert("请求错误");
				}
			});
		}
	};
	$scope.ok = function() {
		var user = {
			userName: $.trim($scope.user.userName),
			sex: $scope.user.sex,
			birthday: dateToDouble($scope.user.birthday),
			email: $.trim($scope.user.email),
			qq: $.trim($scope.user.qq),
			roleId: $scope.user.roleId,
			isActive: $scope.user.isActive
		};
		if ($scope.mode === "edit") {
			user.id = $scope.user.id
			if ($scope.user.password && $scope.user.password.length > 0) {
				user.password = hex_md5($scope.user.password);
			}
		} else {
			user.loginName = hex_md5($.trim($scope.user.loginName)),
			user.password = hex_md5($scope.user.password)
		}
		$.ajax({
			url: "user_" + $scope.mode + ".action",
			type: "POST",
			dataType: "json",
			data: {
				user: JSON.stringify(user)
			},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				if (data.result === "success") {
					alert("数据保存成功");
					$scope.init();
					$("#user-modal").modal("hide");
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
		$scope.pager = [1, null, null];
		$scope.curPage = 1;
		$scope.limit = 10;
		$scope.pages = 0;
		$scope.loadRoles();
		$.ajax({
			url: "user_count.action",
			type: "POST",
			dataType: "json",
			data: {
				condition: JSON.stringify($scope.getCondition())
			},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				if (data.count > 0) {
					$scope.total = data.count;
					$scope.pages = Math.ceil(data.count / $scope.limit);
					$scope.load();
				} else {
					$scope.users = [];
					$scope.curPage = 0;
					$scope.pages = 0;
					$scope.$apply();
				}
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
