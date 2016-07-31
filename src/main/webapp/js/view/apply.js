app.controller("applyController", function($scope) {
	$scope.dateFormat = function(data) {
		return dateFormat(data);
	};
	$scope.statusFormat = function(data) {
		switch (data) {
		case 0: return "未支付"; break;
		case 1: return "已支付"; break;
		case 2: return "待审核"; break;
		case 3: return "不通过"; break;
		default: return "";
		}
	};
	$scope.statusClass = function(data) {
		switch (data) {
//		case 0: return "info"; break;
		case 1: return "success"; break;
		case 2: return "warning"; break;
		case 3: return "danger"; break;
		default: return "";
		}
	};
	$scope.getCondition = function() {
		var condition = {
			status: $scope.status
		};
		
		return condition;
	};
	$scope.load = function() {
		$.ajax({
			url: "billForOut_loadMine.action",
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
				$scope.billForOuts = data;
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
	$scope.sumUp = function() {
		$.ajax({
			url: "billForOut_sumUpMine.action",
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
	$scope.init = function() {
		$scope.pager = [1, null, null];
		$scope.curPage = 1;
		$scope.limit = 10;
		$scope.pages = 0;
		$.ajax({
			url: "billForOut_countMine.action",
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
					$scope.load();
				} else {
					$scope.billForOuts = [];
					$scope.curPage = 0;
					$scope.pages = 0;
					$scope.totalAmount = 0;
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
