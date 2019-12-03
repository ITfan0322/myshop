<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理中心</title>
<link rel="shortcut icon" href="images/logwsl.jpg">
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery-2.2.4.min.js"></script>
<script src="js/cookies.js"></script>
</head>
<body style="background-color: #f2f9fd;">
	<div class="header bg-main">
		<div class="logo margin-big-left fadein-top">
			<h1>
				<!-- <img src="image/y.jpg" class="radius-circle rotate-hover" height="50" alt="" /> -->
				后台管理中心
			</h1>
		</div>
		<div class="head-l">
			&nbsp;&nbsp;<a class="button button-little bg-red"
				href="javascript:void(0);" onclick="login()"><span
				class="icon-power-off"></span> 退出登录</a>
		</div>
	</div>
	<div class="leftnav">
		<div class="leftnav-title">
			<strong><span class="icon-list"></span>菜单列表</strong>
		</div>
		<!--   <h2><span class="icon-user"></span>基本设置</h2>
  <ul>
    <li><a href="info.html" target="right"><span class="icon-caret-right"></span>网站设置</a></li>
    <li><a href="pass.html" target="right"><span class="icon-caret-right"></span>修改密码</a></li>
    <li><a href="page.html" target="right"><span class="icon-caret-right"></span>单页管理</a></li>  
    <li><a href="adv.html" target="right"><span class="icon-caret-right"></span>首页轮播</a></li>   
    <li><a href="book.html" target="right"><span class="icon-caret-right"></span>留言管理</a></li>     
    <li><a href="column.html" target="right"><span class="icon-caret-right"></span>栏目管理</a></li>
  </ul> -->

		<h2 class="dingdan">
			<span class="icon-pencil-square-o"></span>订单管理
		</h2>
		<ul class="dingdan">
			<li><a href="readSelect.jsp" target="right"><span
					class="icon-caret-right"></span>订单列表</a></li>
		</ul>
		<h2 class="shangpin">
			<span class="icon-pencil-square-o"></span>商品管理
		</h2>
		<ul class="shangpin">
			<li><a id="tianjiashangpin" href="readAdd.jsp" target="right"><span
					class="icon-caret-right"></span>添加商品</a></li>
			<li><a href="menu.jsp" target="right"><span
					class="icon-caret-right"></span>商品列表</a></li>
		</ul>
		<h2 class="yonghu">
			<span class="icon-pencil-square-o"></span>用户管理
		</h2>
		<ul class="yonghu">
			<li><a href="user.jsp" target="right"><span
					class="icon-caret-right"></span>用户列表</a></li>
		</ul>
		<h2 class="yonghu">
			<span class="icon-pencil-square-o"></span>提现管理
		</h2>
		<ul class="yonghu">
			<li><a href="cashlist.jsp" target="right"><span
					class="icon-caret-right"></span>提现列表</a></li>
		</ul>
		<h2 class="guanliyuan">
			<span class="icon-pencil-square-o"></span>管理员管理
		</h2>
		<ul class="guanliyuan">
			<li><a href="addAdmin.jsp" target="right"><span
					class="icon-caret-right"></span>添加管理员</a></li>
			<li><a href="adminlist.jsp" target="right"><span
					class="icon-caret-right"></span>管理员列表</a></li>
		</ul>
		<h2 class="dianpu">
			<span class="icon-pencil-square-o"></span>店铺管理
		</h2>
		<ul class="dianpu">
			<!-- <li><a href="addShop.jsp" target="right"><span class="icon-caret-right"></span>添加店铺</a></li> -->
			<li><a href="shoplist.jsp" target="right"><span
					class="icon-caret-right"></span>店铺列表</a></li>
		</ul>
		<!-- <h2><span class="icon-pencil-square-o"></span>轮播图</h2>
  <ul >
    <li><a href="addSwiper.jsp" target="right"><span class="icon-caret-right"></span>添加轮播图</a></li>
    <li><a href="swiperlist.jsp" target="right"><span class="icon-caret-right"></span>轮播图列表</a></li>
  </ul> -->
		<h2 class="pinpai">
			<span class="icon-pencil-square-o"></span>品牌管理
		</h2>
		<ul class="pinpai">
			<li><a href="typelist.jsp" target="right"><span
					class="icon-caret-right"></span>品牌列表</a></li>
		</ul>
		<h2 class="pinpaishang">
			<span class="icon-pencil-square-o"></span>品牌商
		</h2>
		<ul class="pinpaishang">
			<li><a href="ghshop.jsp" target="right"><span
					class="icon-caret-right"></span>品牌商列表</a></li>
		</ul>
		<h2 class="duizhangdan">
			<span class="icon-pencil-square-o"></span>对账账单
		</h2>
		<ul class="duizhangdan">
			<li><a href="payDetails.jsp" target="right"><span
					class="icon-caret-right"></span>账单列表</a></li>
		</ul>
		<h2 class="fenxiao">
			<span class="icon-pencil-square-o"></span>用户等级
		</h2>
		<ul class="fenxiao">
			<li><a href="manage.jsp" target="right"><span
					class="icon-caret-right"></span>用户等级</a></li>
		</ul>
		<h2 class="fenxiao2">
			<span class="icon-pencil-square-o"></span>分销管理
		</h2>
		<ul class="fenxiao2">
			<li><a href="manage2.jsp" target="right"><span
					class="icon-caret-right"></span>分销管理</a></li>
		</ul>
	</div>
	<script type="text/javascript">
	console.log(getCookie("types"))
	if(getCookie("types")==null){
		$(".dingdan").hide()
		$(".shangpin").hide()
		$(".yonghu").hide()
		$(".guanliyuan").hide()
		$(".dianpu").hide()
		$(".pinpai").hide()
		$(".pinpaishang").hide()
		$(".duizhangdan").hide()
		$(".fenxiao").hide()
		$(".fenxiao2").hide()
		$("#tianjiashangpin").hide()
		window.location.href="login.jsp"
	}else{
		if(getCookie("types")=="管理员"){
			$(".dingdan").show()
			$(".shangpin").show()
			$(".yonghu").show()
			$(".guanliyuan").show()
			$(".dianpu").show()
			$(".pinpai").show()
			$(".pinpaishang").show()
			$(".duizhangdan").show()
			$(".fenxiao").show()
			$(".fenxiao2").hide()
			$("#tianjiashangpin").hide()
		}else if(getCookie("types")=="品牌商"){
			$(".dingdan").hide()
			$(".shangpin").show()
			$(".yonghu").hide()
			$(".guanliyuan").hide()
			$(".dianpu").show()
			$(".pinpai").hide()
			$(".pinpaishang").hide()
			$(".duizhangdan").hide()
			$(".fenxiao").hide()
			$(".fenxiao2").show()
			$("#tianjiashangpin").show()
		}else if(getCookie("types")=="店铺"){
			$(".dingdan").show()
			$(".shangpin").hide()
			$(".yonghu").hide()
			$(".guanliyuan").hide()
			$(".dianpu").hide()
			$(".pinpai").hide()
			$(".pinpaishang").hide()
			$(".duizhangdan").hide()
			$(".fenxiao").hide()
			$(".fenxiao2").hide()
			$("#tianjiashangpin").hide()
		}
	}
		$(function() {
			$(".leftnav h2").click(function() {
				$(this).next().slideToggle(200);
				$(this).toggleClass("on");
			})
			$(".leftnav ul li a").click(function() {
				$("#a_leader_txt").text($(this).text());
				$(".leftnav ul li a").removeClass("on");
				$(this).addClass("on");
			})
			setInterval(function() {
				shows()
				console.log("刷新")
		}, 5000)
			
		});
		
		var number=0
		function shows(){
			$
			.ajax({
				method : "post",
				url : "findMyAllOrders",
				data : {
					sid : getCookie("types") == "管理员" ? ""
							: getCookie("number")
				},
				dataType : "json",
				success : function(data) {
					console.log(data.length)
					if(number==0){
						number = data[0].id
					}else{
						if(number<data[0].id){
							playVoice("img/180528.mp3")
							//length=data[0].id
							number = data[0].id
						}
					}
					var price = 0;
					for(var i = 0;i<data.length;i++){
						price = price+(data[i].price * 1)
					}
					$("#allprice").html("总收入："+price.toFixed(2))
				}})
		}
		function playVoice(file) {
		    $('#voice').html('<audio controls="controls" id="audio_player" style="display:none;"> <source src="' + file + '" > </audio><embed id="MPlayer_Alert" src="' + file + '" loop="false" width="0px" height="0px" /></embed>');
		}
		function login() {
			if (window.confirm("确定退出吗？")) {
				/* window.location.href="./thelogin.jsp" */
				delCookie("name")
				delCookie("types")
				delCookie("phone")
				delCookie("number")
				delCookie("id")
				delCookie("pwd")
				window.location.href="login.jsp"
			}
		}
	</script>
	<ul class="bread">
		<li><a href="javascript:void(0)" target="right" class="icon-home">
				首页</a></li>
		<!-- <li><b>当前语言：</b><span style="color:red;">中文</php></span>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;切换语言：<a href="##">中文</a> &nbsp;&nbsp;<a href="##">英文</a> </li> -->
	</ul>
	<div class="admin">
		<iframe scrolling="auto" rameborder="0" src=""
			name="right" width="100%" height="100%"></iframe>
	</div>
	<div id="voice"></div>
</body>
</html>