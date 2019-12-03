<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<script type="text/javascript" src="js/jquery-2.2.4.min.js"></script>
<script type="text/javascript" src="js/cookies.js"></script>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<style>
.main {
	display: flex;
	flex-direction: column;
	padding: 20px; 
}

.li {
	margin-top: 10px;
}

.li div input {
	margin-top: 10px;
	width: 300px;
}
.clo div{
	display: flex;
	flex-direction: row;
}
.moves{
	text-decoration: none;
	font-size:15px;
	margin-top: 10px;
} 
.clo {
	float: left;
	display:flex;
	margin-left: 10px;
	margin-top: 10px;
	flex-direction: column;
	align-items: center;
	justify-content: center;
}

.img div img {
	width: 150px;
	height: 150px;
}
.img {
	
}

</style>
<script type="text/javascript">
	var id = <%=request.getParameter("id")%>
	var color = []
	var size = []
	var img = []
	var imgs = []
	var two = []
	$(function() {
		if (getCookie("types") == null) {
			window.location.href = "mdl.jsp"
		} else if (getCookie("types") == "店铺") {
			window.location.href = "mqx.jsp"
		} else {
		}
		console.log(id)
		$.ajax({
			method : "post",
			url : "findMenuById",
			data : {
				mid : id
			},
			dataType : "json",
			success : function(data) {
				console.log(data.colors)
				console.log(JSON.parse(data.colors))
				color = JSON.parse(data.colors)
				size = JSON.parse(data.size)
				img = JSON.parse(data.img) 
				imgs = JSON.parse(data.imgs)
				two=JSON.parse(data.two)
				$("#name").val(data.name)
				$("#price").val(data.price)
				$("#num").val(data.num)
				$("#pinpai").val(data.types)
				var c = color[0]
				for(var i=1;i<color.length;i++){
					c=c+"/"+color[i]
				}
				var s = size[0]
				for(var i=1;i<size.length;i++){
					s=s+"/"+size[i]
				}
				var t = two[0]
				for(var i=1;i<two.length;i++){
					t=t+"/"+two[i]
				}
				myimg()
				myimgs()
				$(".moves").css("display","none")
				$("#color").val(c)
				$("#size").val(s)
				$("#two").val(t)
				
				console.log(s)
			}
		})
	})
	function myimg(){
		$("#img").html("")
		for(var i=0;i<img.length;i++){
			var str = '<div class="clo">'
			+'<img alt="" src="'+img[i]+'"> '
			+'<div class="moves">'
			+'<a href="javascript:void(0)" onclick="up('+i+')">前移</a>'
			+'<a style="margin-left:20px" href="javascript:void(0)" onclick="mdown('+i+')">后移</a>'
			+'<a style="margin-left:20px" href="javascript:void(0)" onclick="dell('+i+')">删除</a>'
			+'</div>'
			+'</div>' 
			$("#img").append(str)
		}
	}
	function myimgs(){
		$("#imgs").html("") 
		for(var i=0;i<imgs.length;i++){
			var str = '<div class="clo">'
			+'<img alt="" src="'+imgs[i]+'"> '
			+'<div class="moves">'
			+'<a href="javascript:void(0)" onclick="ups('+i+')">前移</a>'
			+'<a href="javascript:void(0)" style="margin-left:20px;" onclick="mdowns('+i+')">后移</a>'
			+'<a href="javascript:void(0)" style="margin-left:20px;" onclick="dells('+i+')">删除</a>'
			+'</div>'
			+'</div>'
			$("#imgs").append(str)
		}
	}
	function fmian(){
		for(var i=0;i<$('#fengmian')[0].files.length;i++){
			var formData = new FormData()  //创建一个forData 
		    formData.append('file', $('#fengmian')[0].files[i])
			$.ajax({
				method : "post",
				url : "uploadImg",
				async: false,
		        cache: false,
		        contentType: false,
		        processData: false,
				data : formData,
				dataType : "json",
				success : function(data) {
					console.log(data)
					img.push(data)
					myimg()
				}
				})
		}
		
	}
	
	function xqing(){
		for(var i=0;i<$('#xiangqing')[0].files.length;i++){
			var formData = new FormData()  //创建一个forData 
		    formData.append('file', $('#xiangqing')[0].files[i])
			$.ajax({
				method : "post",
				url : "uploadImg",
				async: false,
		        cache: false,
		        contentType: false,
		        processData: false,
				data : formData,
				dataType : "json",
				success : function(data) {
					console.log(data)
					imgs.push(data)
					myimgs()
				}
				})
		}
		
	}
	function up(i){
		console.log(i)
		if(i>0){
			var image=img[i]
			img[i]=img[i-1]
			img[i-1]=image
			myimg()
		}else{
			alert("已经是第一个")
		}
		
	}
	function mdown(i){
		console.log(i)
		if(i<img.length-1){
			var image=img[i]
			img[i]=img[i+1]
			img[i+1]=image
			myimg()
		}else{
			alert("已经是最后一个")
		}
		
	}
	function ups(i){
		console.log(i)
		if(i>0){
			var image=imgs[i]
			imgs[i]=imgs[i-1]
			imgs[i-1]=image
			myimgs()
		}else{
			alert("已经是第一个")
		}
		
	}
	function dell(i){
		console.log(i)

			img.splice(i,1)
			myimg()
		
		
	}
	
	function dells(i){
		console.log(i)

			imgs.splice(i,1)
			myimgs()
		
		
	}
	
	function mdowns(i){
		console.log(i)
		if(i<imgs.length-1){
			var image=imgs[i]
			imgs[i]=imgs[i+1]
			imgs[i+1]=image
			myimgs() 
		}else{
			alert("已经是最后一个")
		}
		
	}
	function saveMenu(){
		console.log($("#commit").html())
		if($("#commit").html()=="修改"){
			$("#commit").html("保存")
			$("#fengmian").show()
			$("#xiangqing").show()
			$(".li div input").removeAttr("disabled")
			$(".moves").css("display","block")
		}else{
			$("#commit").html("修改")
			$("#fengmian").hide()
			$("#xiangqing").hide()
			$(".li div input").attr("disabled","disabled")
			$(".moves").css("display","none")
			$.ajax({
				method:"POST",
				url:"updateMenu",
				data:{
					id:id,
					name:$("#name").val(),
					types:$("#pinpai").val(),
					price:$("#price").val(),
					colors:JSON.stringify($("#color").val().split("/")),
					size:JSON.stringify($("#size").val().split("/")),
					img:JSON.stringify(img),
					imgs:JSON.stringify(imgs),
					two:JSON.stringify($("#two").val().split("/")),
				},
				success:function(data){
					console.log(data)
					if(data>0){ 
						location.reload()
					}else{
						alert("修改失败，请重试")
					}
				}
			})
		}
	}
</script>
<body>
	<div class="main">
		<div class="li">
			商品名称：
			<div>
				<input disabled="disabled" class="input w50"  value="" id="name">
			</div>
		</div>
		<div class="li">
			<div class="img li">
			<div>商品封面图：<input hidden="hidden" id="fengmian" onchange="fmian()" type="file" accept="image/*" multiple="multiple"> </div>
			<div class="img" id="img">
				
			</div>
		</div>
		</div>
		<div class="li">
			商品价格：
			<div>
				<input disabled="disabled" class="input w50"  value="" id="price">
			</div>
		</div>
		<div class="li">
			商品销量：
			<div>
				<input disabled="disabled" class="input w50"  value="" id="num">
			</div>
		</div>
		<div class="li">
			商品颜色：
			<div>
				<input disabled="disabled" class="input w50"  value="" id="color">
			</div>
		</div>
		<div class="li">
			商品尺码：
			<div>
				<input disabled="disabled" class="input w50"  value="" id="size">
			</div>
		</div>
		<div class="li">
			商品品牌：
			<div>
				<input disabled="disabled" class="input w50"  value="" id="pinpai">
			</div>
		</div>
		<div class="li">
			商品特点：
			<div>
				<input disabled="disabled" class="input w50"  value="" id="two">
			</div>
		</div>
		<div class="img li">
			<div>商品详情图：<input hidden="hidden" id="xiangqing" onchange="xqing()" type="file" accept="image/*" multiple="multiple"></div>
			<div class="img" id="imgs">
				
			</div> 
		</div>
		<div>
		<a href="javascript:void(0)" id="commit" class="button bg-main icon-check-square-o" style="width: 150px;margin-top: 20px;text-align: center;" 
						type="button" onclick="saveMenu()">修改</a>
		</div>
	</div>
</body>

</html>