app.controller("aboutController", function($scope) {
	$scope.timestamp = new Date().getTime();
	$scope.resetReply = function() {
		$scope.replyContent = "";
	};
	$scope.changeValidCode = function() {
		$scope.timestamp = new Date().getTime();
	};
	$scope.submitReply = function() {
		$.ajax({
			url: "message_reply.action",
			type: "POST",
			dataType: "json",
			data: {
				content: $scope.replyContent,
				validCode: $scope.validCode
			},
			beforeSend: function() {},
			complete: function() {},
			success: function(data) {
				if (data.result === "success") {
					alert("您的反馈已提交成功，谢谢您的宝贵意见");
					$("#reply-modal").modal("hide");
					$scope.resetReply();
				} else {
					alert(data.result);
				}
			},
			error: function() {
				alert("请求错误");
			}
		});
	};
});
/* By Ming */
