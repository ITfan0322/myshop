<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
    <script src="js/jquery.js"></script>
    <script src="js/cookies.js"></script>
    <script type="text/javascript">
    var id = <%=request.getParameter("id")%>
    console.log("id="+id)
    var state;
    var orderbum;
    $(function(){
    	 if (getCookie("types") == null) {
				window.location.href = "mdl.jsp"
			} else if (getCookie("types") == "品牌商") {
				window.location.href = "mqx.jsp"
			} else {
    	show()
			}
    })
    function show(){
    	 $.ajax({
    	    	method:"post",
    	    	url:"findOrderById",
    	    	data:{
    	    		id:id
    	    	},
    	    	dataType:"json",
    	    	success:function(data){
    	    		console.log("111111111111111111111111111111")
    	    		console.log(JSON.parse(data.content)[0].menus)
    	    		ordernum=data.ordernum
    	    		state=data.state
    	    		if(data.state==1){
    	    			$("#jie").removeAttr("hidden")
    	    			$("#wan").attr("hidden","hidden")
    	    		}else if(data.state==2){
    	    			$("#wan").removeAttr("hidden")
    	    			$("#jie").attr("hidden","hidden")
    	    		}else{
    	    			$("#jie").attr("hidden","hidden")
    	    			$("#wan").attr("hidden","hidden")
    	    		}
    	    		console.log(JSON.parse(data.content)[0].menus)
    	    		var list = JSON.parse(data.content)
    	    		$("#dingdanhao").html(data.ordernum)
    	    		$("#name").html(data.name)
    	    		$("#phone").html(data.phone)
    	    		$("#address").html(data.address)
    	    		$("#list").html('')
    	    		for(var i=0;i<list.length;i++){
    	    			var str = '<div class="li">'
    	    				+'<div class="l_left"><img alt="" src="'+list[i].menus.img+'"></div>'
    	    				+'<div class="l_right">'
    	    					+'<div class="name">'
    	    						+'商品：'+list[i].menus.name
    	    					+'</div>'
    	    					+'<div class="num">'
    	    						+'数量：'+list[i].num
    	    					+'</div>'
    	    					+'<div class="price">'
    	    						+'价格：'+list[i].menus.price
    	    					+'</div>'
    	    					+'<div class="type">'
    	    						+'商品类型：'+list[i].menus.types
    	    					+'</div>'
    	    					+'<div class="message">'
    	    						+'商品规格：'+list[i].menus.message
    	    					+'</div>'
    	    				+'</div>'
    	    				+'</div>';
    	    				
    	    			$("#list").append(str)
    	    		}
    	    	}
    	    })
    }
   	function peis(){
   		console.log("111111111111111111")
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
    </script>
    <style>
    	.title{
    	display: flex;
    	flex-direction: row;
    	font-size:15px;
    	align-items: center;
    	}
    	.t_left{
    	width: 100px;
    	}
    	.li{
    	display: flex;
    	flex-direction: row;
    	font-size:15px;
    	align-items: center;
    	
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
    	}
    </style>
<body>
	<div class="title">
		<div class="t_left">订单号:</div>
		<div class="t_right" id="dingdanhao"></div>
	</div>
	<div class="title">
		<div class="t_left">收货人:</div>
		<div class="t_right" id="name"></div>
	</div>
	<div class="title">
		<div class="t_left">电话:</div>
		<div class="t_right" id="phone"></div>
	</div>
	<div class="title">
		<div class="t_left">地址:</div>
		<div class="t_right" id="address"></div>
	</div>
	<div class="list">
	<div style="font-size:15px;">商品信息：</div>
	<div id="list"></div>
	</div>
	<input id="jie" type="button" value="接单配送" onclick="peis()">
	<input id="wan" type="button" value="完成订单" onclick="wan()">
</body>
</html>