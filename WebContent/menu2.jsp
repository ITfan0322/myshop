<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">

<script src="js/jquery.js"></script>
<script src="js/cookies.js"></script>
<style type="text/css">
#sela td input {
	text-align: center;
}
.title{
    	display: flex;
    	flex-direction: row;
    	font-size:15px;
    	align-items: center;
    	margin: 20px;
    	}
    	.t_left{
    	width: 100px;
    	}
    	.li{
    	display: flex;
    	flex-direction: row;
    	font-size:15px;
    	align-items: center;
    	margin-left: 20px;
    	padding-bottom: 20px;
    	}
    	.li img{
    	width:100px;
    	height:100px; 
    	
    	}
    	.l_right{
    		margin-left: 20px;
    	}
    	input{
    	padding:5px;
    	width: 100px;
    	margin: 20px;
    	}
    	.button{
    		margin:20px;
    	}
</style>
 <script type="text/javascript">
    var id = <%=request.getParameter("id")%>
    console.log("id="+id)
    var state;
    var orderbum;
    var refund_fee;
    $(function(){
    	 if (getCookie("types") == null) {
				window.location.href = "mdl.jsp"
			} else if (getCookie("types") == "品牌商") {
				window.location.href = "mqx.jsp"
			} else {
    			show()
			}
    })
    var types=""
    function show(){
    	 $.ajax({
    	    	method:"post",
    	    	url:"findOrderById",
    	    	data:{
    	    		id:id
    	    	},
    	    	dataType:"json",
    	    	success:function(data){
    	    		console.log(data.ziqu)
    	    		ordernum=data.ordernum
    	    		state=data.state
    	    		types=data.ziqu
    	    		refund_fee=data.price
    	    		if(data.state==5){
    	    			$("#cancle").hide()
    	    			$(".tipss").hide()
    	    		}
    	    		if(types=="到店自取"){
    	    			if(data.state==1){
        	    			$("#wan").show()
        	    			$("#jie").hide()
        	    		}else{
        	    			$("#jie").hide()
        	    			$("#wan").hide()
        	    		}
    	    		}else{
    	    			if(data.state==1){
        	    			$("#jie").show()
        	    			$("#wan").hide()
        	    		}else if(data.state==2){
        	    			$("#wan").show()
        	    			$("#jie").hide()
        	    		}else{
        	    			$("#jie").hide()
        	    			$("#wan").hide()
        	    		}
    	    		}
    	    		
    	    		console.log(JSON.parse(data.content))
    	    		var list = JSON.parse(data.content)
    	    		$("#dingdanhao").html(data.ordernum)
    	    		$("#name").html(data.name)
    	    		$("#phone").html(data.phone)
    	    		$("#address").html(data.address)
    	    		$("#shops").html(data.shops)
    	    		$("#pphone").html(data.pphone)
    	    		$("#pname").html(data.pname)
    	    		$("#ziqu").html(data.ziqu)
    	    		$("#state").html(data.state==0?'未支付':data.state==1?'待接单':data.state==2?'配送中':data.state==3?'已完成':data.state==4?'取消中':'已取消')
    	    		$("#list").html('')
    	    			var str = '<div class="li">'
    	    				+'<div class="l_left"><img alt="" src="'+list.menus.img+'"></div>'
    	    				+'<div class="l_right">'
    	    					+'<div class="name">'
    	    						+'商品：'+list.menus.name
    	    					+'</div>'
    	    					+'<div class="num">'
    	    						+'数量：'+list.num
    	    					+'</div>'
    	    					+'<div class="price">'
    	    						+'价格：'+list.menus.price
    	    					+'</div>'
    	    					+'<div class="type">'
    	    						+'商品类型：'+list.menus.types
    	    					+'</div>'
    	    					+'<div class="message">'
    	    						+'颜色：'+list.colors
    	    					+'</div>'
    	    					+'<div class="message">'
	    						+'尺码：'+list.size
	    					+'</div>'
    	    				+'</div>'
    	    				+'</div>';
    	    			$("#list").append(str)
    	    	}
    	    })
    }
   	function pei(){
   		$.ajax({
   			method:"post",
   			url:"updateStates",
   			data:{
   				state:2,
   				id:id
   			},
   			dataType:"json",
   			success:function(data){
   				if(data>0){
   					show()
   				}
   			}
   		})
   	}
	function wan(){
   		$.ajax({
   			method:"post",
   			url:"updateStates",
   			data:{
   				state:3,
   				id:id
   			},
   			dataType:"json",
   			success:function(data){
   				if(data>0){
   					show()
   				}
   			}
   		})
   	}
	function cancle(){
		if(confirm("您确认取消订单吗？")){
			$.ajax({
	   			method:"post",
	   			url:"refound",
	   			data:{
	   				out_trade_no:ordernum,
	   				total_fee:(refund_fee*100).toFixed(0),
	   				refund_fee:(refund_fee*100).toFixed(0)
	   			},
	   			dataType:"json",
	   			success:function(data){
	   				if(data>0){
	   					alert("已取消")
	   					show()
	   				}else{
	   					alert("取消失败")
	   				}
	   			}
	   		})
		}
   		
   	}
    </script>
<script src="js/pintuer.js"></script>
</head>
<body style="width:1800px;">
	<form method="post" action="" id="listform">
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 内容列表</strong> <a href=""
					style="float: right; display: none;">添加字段</a>
			</div>
			<div class="title">
		<div class="t_left">订单号:</div>
		<div class="t_right" id="dingdanhao"></div>
	</div>
	<div class="title">
		<div class="t_left">订单状态:</div>
		<div class="t_right" id="state"></div>
	</div>
	<div class="title">
		<div class="t_left">收货人:</div>
		<div class="t_right" id="name"></div>
	</div>
	<div class="title">
		<div class="t_left">配送方式:</div>
		<div class="t_right" id="ziqu"></div>
	</div>
	<div class="title">
		<div class="t_left">店铺:</div>
		<div class="t_right" id="shops"></div>
	</div>
	<div class="title">
		<div class="t_left">电话:</div>
		<div class="t_right" id="phone"></div>
	</div>
	<div class="title">
		<div class="t_left">地址:</div>
		<div class="t_right" id="address"></div>
	</div>
	<!-- <div class="title">
		<div class="t_left">负责人姓名:</div>
		<div class="t_right" id="pname"></div>
	</div>
	<div class="title">
		<div class="t_left">负责人电话：</div>
		<div class="t_right" id="pphone"></div>
	</div> -->
	<div class="list">
	<div style="font-size:15px;margin:20px;">商品信息：</div>
	<div id="list"></div>
	</div>
	<a href="jsvascript:void(0);" class='button border-main' id="jie"  onclick="pei()">接单配送</a>
	<a href="jsvascript:void(0);" class='button border-red' id="wan"  onclick="wan()">完成订单</a></br>
	<a href="jsvascript:void(0);" class='button border-red' id="cancle"  onclick="cancle()" style="margin-top:10px''">取消订单并退款</a>
	<span style="color:darkgray;" class="tipss">如用户申请取消订单，请在完成退款后点击取消</span>
		</div>
	</form>
	<script type="text/javascript">
		
		//修改
		function update(id) {
			//alert($("#phone"+id).val())
			var update = $("#update" + id).html();
			var phone = $("#phone" + id).val();
			var name = $("#name" + id).val();
			var area = $("#area" + id).val();
			var pwd = $("#pwd" + id).val();
			var work = $("#work" + id).val();
			if (update == '修改') {
				$("#update" + id).html("保存");
				$("#phone" + id).attr("disabled", false);
				$("#name" + id).attr("disabled", false);
				$("#area" + id).attr("disabled", false);
				$("#work" + id).attr("disabled", false);
				$("#pwd" + id).attr("disabled", false);
			} else if (update == "保存") {
				$("#update" + id).html("修改");
				$("#phone" + id).attr("disabled", true);
				$("#name" + id).attr("disabled", true);
				$("#area" + id).attr("disabled", true);
				$("#work" + id).attr("disabled", true);
				$("#pwd" + id).attr("disabled", true);
				$.ajax({
					method : "post",
					url : "updatePeisong",
					data : {
						id : id,
						phone : phone,
						name : name,
						area : area,
						ptype : work,
						pwd : pwd
					},
					dataType : 'json',
					success : function(data) {
						selectActivityWsl();
					}
				})
			}

			//修改

			/* if (confirm("您确定要删除吗?")) {
				alert(11)
				$.ajax({
					method : "post",
					url : "deletePeisong",
					data : {
						id : id
					},
					dataType : 'json',
					success : function(data) {
						$("#sela").remove();
						selectActivityWsl();
					}
				})
			} */
		}
		function pass(id) {
			//alert($("#phone"+id).val())
			var update = $("#pass" + id).html();
			//alert(update)
			if (update == '通过审核') {
				$.ajax({
					method : "post",
					url : "updatePstate",
					data : {
						id : id,
					},
					dataType : 'json',
					success : function(data) {
						//alert(data)

						selectActivityWsl();
					}
				})
			} else {

			}
		}
		//全选
		$("#checkall").click(function() {
			$("input[name='id[]']").each(function() {
				if (this.checked) {
					this.checked = false;
				} else {
					this.checked = true;
				}
			});
		})

		//批量删除
		function DelSelect() {
			var Checkbox = false;
			$("input[name='id[]']").each(function() {
				if (this.checked == true) {
					Checkbox = true;
				}
			});
			if (Checkbox) {
				var t = confirm("您确认要删除选中的内容吗？");
				if (t == false)
					return false;
				$("#listform").submit();
			} else {
				alert("请选择您要删除的内容!");
				return false;
			}
		}

		//批量排序
		function sorts() {
			var Checkbox = false;
			$("input[name='id[]']").each(function() {
				if (this.checked == true) {
					Checkbox = true;
				}
			});
			if (Checkbox) {

				$("#listform").submit();
			} else {
				alert("请选择要操作的内容!");
				return false;
			}
		}

		//批量首页显示
		function changeishome(o) {
			var Checkbox = false;
			$("input[name='id[]']").each(function() {
				if (this.checked == true) {
					Checkbox = true;
				}
			});
			if (Checkbox) {

				$("#listform").submit();
			} else {
				alert("请选择要操作的内容!");

				return false;
			}
		}

		//批量推荐
		function changeisvouch(o) {
			var Checkbox = false;
			$("input[name='id[]']").each(function() {
				if (this.checked == true) {
					Checkbox = true;
				}
			});
			if (Checkbox) {

				$("#listform").submit();
			} else {
				alert("请选择要操作的内容!");

				return false;
			}
		}

		//批量置顶
		function changeistop(o) {
			var Checkbox = false;
			$("input[name='id[]']").each(function() {
				if (this.checked == true) {
					Checkbox = true;
				}
			});
			if (Checkbox) {

				$("#listform").submit();
			} else {
				alert("请选择要操作的内容!");

				return false;
			}
		}

		//批量移动
		function changecate(o) {
			var Checkbox = false;
			$("input[name='id[]']").each(function() {
				if (this.checked == true) {
					Checkbox = true;
				}
			});
			if (Checkbox) {

				$("#listform").submit();
			} else {
				alert("请选择要操作的内容!");

				return false;
			}
		}

		//批量复制
		function changecopy(o) {
			var Checkbox = false;
			$("input[name='id[]']").each(function() {
				if (this.checked == true) {
					Checkbox = true;
				}
			});
			if (Checkbox) {
				var i = 0;
				$("input[name='id[]']").each(function() {
					if (this.checked == true) {
						i++;
					}
				});
				if (i > 1) {
					alert("只能选择一条信息!");
					$(o).find("option:first").prop("selected", "selected");
				} else {

					$("#listform").submit();
				}
			} else {
				alert("请选择要复制的内容!");
				$(o).find("option:first").prop("selected", "selected");
				return false;
			}
		}
	</script>
</body>
</html>