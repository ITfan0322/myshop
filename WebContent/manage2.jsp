<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<title>Insert title here</title>
<style type="text/css">
.title {
	margin-top: 10px;
}
</style>
</head>
<script src="js/jquery.js"></script>
<script src="js/cookies.js"></script>
<script type="text/javascript">
	var state;
	var orderbum;
	$(function() {
		 if (getCookie("types") == null) {
				window.location.href = "mdl.jsp"
			} else if (getCookie("types") == "店铺"||getCookie("types") == "管理员") {
				window.location.href = "mqx.jsp"
			} else {
		show()
			}
	})
	function show() {
		$.ajax({
			method : "post",
			url : "findFenxiaoById",
			data : {
				id:getCookie("id")
			},
			dataType : "json",
			success : function(data) {
				console.log("分销", data)
				$("#grade").val(data.grade)
				$("#one").val(data.one)
				$("#two").val(data.two)
			}
		})
		$.ajax({
			method : "post",
			url : "findGrade",
			data : {
			},
			dataType : "json",
			success : function(data) {
				console.log(data)
				 $("#oneg").val(0)
				 $("#twog").val(data.two)
				$("#threeg").val(data.three)
				$("#fourg").val(data.four)
				$("#fiveg").val(data.five) 
			}
		})
	}
	function fenxiao(){
		$(".fx input").removeAttr("disabled")
		$(".fx select").removeAttr("disabled")
		if($("#fxs").html()=="修改"){
			$("#fxs").html("保存")
		}else{
			$.ajax({
				method : "post",
				url : "updateFenxiao",
				data : {
					grade:$("#grade").val(),
					one:$("#one").val(),
					two:$("#two").val(),
					ghid:getCookie("id")
				},
				dataType : "json",
				success : function(data) {
					console.log(data)
					show()
					$(".fx input").attr("disabled","disabled")
					$(".fx select").attr("disabled","disabled")
					$("#fxs").html("修改")
				}
			}) 
		}
	} 
	function grade(){
		$(".dj input").removeAttr("disabled")
		if($("#djx").html()=="修改"){
			$("#djx").html("保存")
		}else{
		$.ajax({
			method : "post",
			url : "updateMGrade",
			data : {
				two:$("#twog").val(),
				three:$("#threeg").val(),
				four:$("#fourg").val(),
				five:$("#fiveg").val(),
				
			},
			dataType : "json",
			success : function(data) {
				console.log(data)
				show()
				$(".dj input").attr("disabled","disabled")
				$("#djx").html("修改")
			}
		})
		}
	} 
</script>
<style>
.title {
	display: flex;
	flex-direction: row;
	font-size: 15px;
	align-items: center;
}

.t_left {
	width: 100px;
}

.li {
	display: flex;
	flex-direction: row;
	font-size: 15px;
	align-items: center;
}

.li img {
	width: 100px;
	height: 100px;
}

.l_right {
	margin-left: 20px;
}

input {
	padding: 5px;
	width: 100px;
}
</style>
<body>
	<div style="font-size:16px;font-weight:600;">分销管理</div>
	<div class="fx">
	<div class="title">
		<div class="t_left">分销级数:</div>
		<div class="t_right" ><select id="grade" style="width:100px;height:34px;" disabled="disabled">
		<option vlaue="0">0</option>
			<option vlaue="1">1</option>
			<option vlaue="2">2</option>
		</select></div>
	</div>
	<div class="title">
		<div class="t_left">1级收益:</div>
		<div class="t_right" ><input disabled="disabled" id="one" type="text"></div>
	</div>
	<div class="title">
		<div class="t_left">2级收益:</div>
		<div class="t_right"><input disabled="disabled" id="two" type="text"></div>
	</div>
	<div class="title">
		<a href="javascript:void(0)" class="button border-main icon-edit" id="fxs"
		onclick="fenxiao()">修改</a>
	</div>
</div> 
<!-- <div class="dj">
	<div style="font-size:16px;font-weight:600;margin-top:30px">用户等级管理</div>
	<div class="title">
		<div class="t_left">1级消费额:</div>
		<div class="t_right"><input disabled="disabled" id="oneg" type="number"></div>
	</div>
	<div class="title">
		<div class="t_left">2级消费额:</div>
		<div class="t_right"><input disabled="disabled" id="twog" type="number"></div>
	</div>
	<div class="title">
		<div class="t_left">3级消费额:</div>
		<div class="t_right"><input disabled="disabled" id="threeg" type="number"></div>
	</div>
	<div class="title">
		<div class="t_left">4级消费额:</div>
		<div class="t_right"><input disabled="disabled" id="fourg" type="number"></div>
	</div>
	<div class="title">
		<div class="t_left">5级消费额:</div>
		<div class="t_right"><input disabled="disabled" id="fiveg" type="number"></div>
	</div>
	<div class="title">
		<a href="javascript:void(0)" class="button border-main icon-edit" id="djx"
		onclick="grade()">修改</a>
	</div>
	</div> -->
</body>
</html>