app.controller("billForInController", function($scope) {
	$scope.dateFormat = function(data) {
		return dateFormat(data);
	};
	$scope.currentUserId = function() {
		$.ajax({
			url: "user_currentId.action",
			type: "POST",
			dataType: "json",
			data: {},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				if (data.id) {
					$scope.currentId = data.id;
					$scope.$apply();
				}
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.loadUsers = function() {
		$.ajax({
			url: "user_loadAll.action",
			type: "POST",
			dataType: "json",
			data: {},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				$scope.users = data;
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
			toName: $scope.toName
		};
		
		return condition;
	};
	$scope.load = function() {
		$.ajax({
			url: "billForIn_load.action",
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
				$scope.billForIns = data;
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
		$scope.billForIn = {
			date: new Date(),
			amount: null,
			recordId: $scope.currentId || null
		};
	};
	$scope.add = function() {
		$scope.modalTitle = "新增入账";
		$scope.mode = "save";
		$scope.reset();
	};
	$scope.edit = function(id) {
		$scope.modalTitle = "编辑入账";
		$scope.mode = "edit";
		$scope.reset();
		$.ajax({
			url: "billForIn_loadOne.action",
			type: "POST",
			dataType: "json",
			data: {
				id: id
			},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				$scope.billForIn = data;
				$scope.billForIn.date = doubleToDate(data.date);
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
				url: "billForIn_delete.action",
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
		var billForIn = {
			date: dateToDouble($scope.billForIn.date),
			amount: $.trim($scope.billForIn.amount),
			fromId: $scope.billForIn.fromId,
			toId: $scope.billForIn.toId,
			recordId: $scope.billForIn.recordId,
			remark: $scope.billForIn.remark
		};
		if ($scope.mode === "edit") {
			billForIn.id = $scope.billForIn.id
		}
		$.ajax({
			url: "billForIn_" + $scope.mode + ".action",
			type: "POST",
			dataType: "json",
			data: {
				billForIn: JSON.stringify(billForIn)
			},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				if (data.result === "success") {
					alert("数据保存成功");
					$scope.init();
					$("#billForIn-modal").modal("hide");
				} else {
					alert(data.result);
				}
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.sumUp = function() {
		$.ajax({
			url: "billForIn_sumUp.action",
			type: "POST",
			dataType: "json",
			data: {
				condition: JSON.stringify($scope.getCondition())
			},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				if (data.amount > 0)
					$scope.totalAmount = data.amount;
				else
					$scope.totalAmount = 0;
				$scope.$apply();
				if (data.result)
					alert(data.result);
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.sumUpMoney = function() {
		$.ajax({
			url: "billForIn_sumUpMoney.action",
			type: "POST",
			dataType: "json",
			data: {},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				$scope.totalMoney = data.amount;
				$scope.$apply();
				if (data.result)
					alert(data.result);
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
	$scope.sumUpNeed = function() {
		$.ajax({
			url: "billForIn_sumUpNeed.action",
			type: "POST",
			dataType: "json",
			data: {},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				$scope.needMoney = data.amount;
				$scope.$apply();
				if (data.result)
					alert(data.result);
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
		$scope.loadUsers();
		$scope.currentUserId();
		$.ajax({
			url: "billForIn_count.action",
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
					$scope.sumUp();
					$scope.sumUpMoney();
					$scope.sumUpNeed();
					$scope.load();
				} else {
					$scope.billForIns = [];
					$scope.curPage = 0;
					$scope.pages = 0;
					$scope.totalAmount = 0;
					$scope.totalMoney = 0;
					$scope.needMoney = 0;
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
