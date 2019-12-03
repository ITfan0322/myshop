<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title>登录</title>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery.js"></script>
<script src="js/pintuer.js"></script>
<script src="js/cookies.js"></script>
</head>
<body>
	<div class="bg" style="background-image: url(image/bg.jpg)"></div>
	<div class="container">
		<div class="line bouncein">
			<div class="xs6 xm4 xs3-move xm4-move">
				<div style="height: 150px;"></div>
				<div class="media media-y margin-big-bottom"></div>
				<!-- 管理员登陆 -->
				<div class="panel loginbox" id="admins">
					<div class="text-center margin-big padding-big-top">
						<h1>公司管理系统</h1>
					</div>
					<div class="panel-body"
						style="padding: 30px; padding-bottom: 10px; padding-top: 10px;">
						<div class="form-group">
							<div class="field field-icon-right">
								<input type="number" class="input input-big" name="aname"
									id="aname" placeholder="输入账号" data-validate="required:输入账号" />
								<span class="icon icon-user margin-small"></span>
							</div>
						</div>
						<div class="form-group">
							<div class="field field-icon-right">
								<input type="password" class="input input-big" name="apassword"
									id="apassword" placeholder="输入密码" data-validate="required:输入密码" />
								<span class="icon icon-key margin-small"></span>
							</div>
						</div>
						<!--       <div class="form-group">
                        <div class="field">
                            <input type="text" class="input input-big" name="code" placeholder="å¡«åå³ä¾§çéªè¯ç " data-validate="required:è¯·å¡«åå³ä¾§çéªè¯ç " />
                           <img src="images/passcode.jpg" alt="" width="100" height="32" class="passcode" style="height:43px;cursor:pointer;" onclick="this.src=this.src+'?'">  
                        </div>
                    </div> -->
						<div class="form-group"
							style="text-align: right; margin-bottom: 0px; padding-bottom: 0px;">
							<a href="javascript:void(0)" style="margin-right: 30px;"
								onclick="pinpai()">品牌商登录</a> <a href="javascript:void(0)"
								onclick="mendian()">店鋪登录</a>
						</div>
					</div>
					<div style="padding: 30px; padding-top: 20px;">
						<input type="button" onclick="alogins()"
							class="button button-block bg-main text-big input-big" value="登录">
					</div>
				</div>
				<!-- 品牌商登录 -->
				<div class="panel loginbox" style="display: none" id="pinpai">
					<div class="text-center margin-big padding-big-top">
						<h1>品牌商管理系统</h1>
					</div>
					<div class="panel-body"
						style="padding: 30px; padding-bottom: 10px; padding-top: 10px;">
						<div class="form-group">
							<div class="field field-icon-right">
								<input type="number" class="input input-big" name="pphone"
									id="pphone" placeholder="输入账号" data-validate="required:输入账号" />
								<span class="icon icon-user margin-small"></span>
							</div>
						</div>
						<div class="form-group">
							<div class="field field-icon-right">
								<input type="password" class="input input-big" name="ppassword"
									id="ppassword" placeholder="输入密码" data-validate="required:输入密码" />
								<span class="icon icon-key margin-small"></span>
							</div>
						</div>
						<!--<div class="form-group">
                        		<div class="field">
                            		<input type="text" class="input input-big" name="code" placeholder="å¡«åå³ä¾§çéªè¯ç " data-validate="required:è¯·å¡«åå³ä¾§çéªè¯ç " />
                           			<img src="images/passcode.jpg" alt="" width="100" height="32" class="passcode" style="height:43px;cursor:pointer;" onclick="this.src=this.src+'?'">  
                        		</div>
                    		</div> -->
						<div class="form-group"
							style="text-align: right; margin-bottom: 0px; padding-bottom: 0px;">
							<a href="javascript:void(0)" style="margin-right: 20px;"
								onclick="gly()">管理员登录</a> <a href="javascript:void(0)"
								style="margin-right: 20px;" onclick="mendian()">店鋪登录</a> <a
								href="javascript:void(0)" onclick="res()">品牌商注册</a>
						</div>
					</div>
					<div style="padding: 30px; padding-top: 20px;">
						<input type="button" onclick="plogin()"
							class="button button-block bg-main text-big input-big" value="登录">
					</div>
				</div>


				<!-- 品牌商註冊 -->
				<div class="panel loginbox" style="display: none" id="pinpaires">
					<div class="text-center margin-big padding-big-top">
						<h1>品牌商管理系统</h1>
					</div>
					<div class="panel-body"
						style="padding: 30px; padding-bottom: 10px; padding-top: 10px;">
						<div class="form-group">
							<div class="field field-icon-right">
								<input type="text" class="input input-big" name="resname"
									id="resname" placeholder="输入名称" data-validate="required:输入名称" />
								<span class="icon icon-user margin-small"></span>
							</div>
						</div>
						<div class="form-group">
							<div class="field field-icon-right">
								<input type="number" class="input input-big" name="resphone"
									id="resphone" placeholder="输入手机号"
									data-validate="required:输入手机号" /> <span
									class="icon icon-phone margin-small"></span>
							</div>
						</div>
						<div class="form-group">
							<div class="field field-icon-right">
								<input type="password" class="input input-big" name="respwd"
									id="respwd" placeholder="输入密码" data-validate="required:输入密码" />
								<span class="icon icon-key margin-small"></span>
							</div>
						</div>
						<div class="form-group">
							<div class="field field-icon-right">
								<input type="password" class="input input-big" name="ressurepwd"
									id="ressurepwd" placeholder="确认密码"
									data-validate="required:确认密码" /> <span
									class="icon icon-key margin-small"></span>
							</div>
						</div>
						<!--       <div class="form-group">
                        <div class="field">
                            <input type="text" class="input input-big" name="code" placeholder="å¡«åå³ä¾§çéªè¯ç " data-validate="required:è¯·å¡«åå³ä¾§çéªè¯ç " />
                           <img src="images/passcode.jpg" alt="" width="100" height="32" class="passcode" style="height:43px;cursor:pointer;" onclick="this.src=this.src+'?'">  
                        </div>
                    </div> -->
						<div class="form-group"
							style="text-align: right; margin-bottom: 0px; padding-bottom: 0px;">
							<a href="javascript:void(0)" onclick="pinpai()">品牌商登录</a>
						</div>
					</div>
					<div style="padding: 30px; padding-top: 20px;">
						<input type="button" onclick="pres()"
							class="button button-block bg-main text-big input-big" value="注册">
					</div>
				</div>

				<!-- 门店登录 -->
				<div class="panel loginbox" style="display: none" id="mendian">
					<div class="text-center margin-big padding-big-top">
						<h1>店鋪管理系统</h1>
					</div>
					<div class="panel-body"
						style="padding: 30px; padding-bottom: 10px; padding-top: 10px;">
						<div class="form-group">
							<div class="field field-icon-right">
								<input type="number" class="input input-big" name="mphone"
									id="mphone" placeholder="输入手机号" data-validate="required:输入手机号" />
								<span class="icon icon-user margin-small"></span>
							</div>
						</div>
						<div class="form-group">
							<div class="field field-icon-right">
								<input type="text" class="input input-big" name="mpassword"
									id="mpassword" placeholder="输入店铺编号"
									data-validate="required:输入店铺编号" /> <span
									class="icon icon-key margin-small"></span>
							</div>
						</div>
						<!--<div class="form-group">
                        <div class="field">
                            <input type="text" class="input input-big" name="code" placeholder="å¡«åå³ä¾§çéªè¯ç " data-validate="required:è¯·å¡«åå³ä¾§çéªè¯ç " />
                           <img src="images/passcode.jpg" alt="" width="100" height="32" class="passcode" style="height:43px;cursor:pointer;" onclick="this.src=this.src+'?'">  
                        </div>
                    </div> -->
						<div class="form-group"
							style="text-align: right; margin-bottom: 0px; padding-bottom: 0px;">
							<a href="javascript:void(0)" style="margin-right: 30px;"
								onclick="gly()">管理员登录</a> <a href="javascript:void(0)"
								onclick="pinpai()">品牌商登录</a>
						</div>


					</div>
					<div style="padding: 30px; padding-top: 20px;">
						<input type="button" onclick="mlogin()"
							class="button button-block bg-main text-big input-big" value="登录">
					</div>
				</div>

			</div>
		</div>
	</div>
	<script type="text/javascript">
		function gly() {
			console.log("admin")
			$("#admins").show()
			$("#mendian").hide()
			$("#pinpai").hide()
			$("#pinpaires").hide()
		}
		function mendian() {
			console.log("mendian")
			$("#admins").hide()
			$("#mendian").show()
			$("#pinpaires").hide()
			$("#pinpai").hide()
		}
		function pinpai() {
			console.log("pinpai")
			$("#admins").hide()
			$("#mendian").hide()
			$("#pinpai").show()
			$("#pinpaires").hide()
		}
		function res() {
			$("#admins").hide()
			$("#mendian").hide()
			$("#pinpai").hide()
			$("#pinpaires").show()
		}

		function alogins() {
			console.log("alogin")
			var phone = $("#aname").val()
			var pwd = $("#apassword").val()
			$.ajax({
				url : "findByPhone",
				method : "post",
				data : {
					phone : phone,
					pwd : pwd
				},
				dataType : "json",
				success : function(data) {
					console.log(data)
					if (data == 0) {
						alert("手机或密码错误，请重试")
					} else {
						
						console.log("登陆成功")
						setCookie("types", "管理员")
						setCookie("name", data.name)
						setCookie("phone", data.phone)
						setCookie("id", data.id)
						setCookie("pwd", data.pwd)
						window.location.href="index.jsp"
					}
				}
			})
		}

		function plogin() {
			var phone = $("#pphone").val()
			var pwd = $("#ppassword").val()
			console.log(phone)
			console.log(pwd)
			$.ajax({
				url : "findGhShop",
				method : "post",
				data : {
					phone : phone,
					pwd : pwd
				},
				dataType : "json",
				success : function(data) {
					console.log(data)
					if (data == null) {
						alert("手机或密码错误，请重试")
					} else {
						if(data.status==0){
							alert("请耐心等待审核")
						}else{
							
						
						console.log("登陆成功")
						setCookie("types", "品牌商")
						setCookie("name", data.name)
						setCookie("phone", data.phone)
						setCookie("id", data.id)
						setCookie("pwd", data.pwd)
						window.location.href="index.jsp"
						}
					}
				}
			})
		}

		function mlogin() {
			var phone = $("#mphone").val()
			var pwd = $("#mpassword").val()
			$.ajax({
				url : "findShopsByPhoneAndNumber",
				method : "post",
				data : {
					phone : phone,
					number : pwd
				},
				dataType : "json",
				success : function(data) {
					console.log(data)
					if (data == null) {
						alert("手机或编号错误，请重试")
					} else {
						if(data.status==0||data.status==1){
							alert("请耐心等待审核")
						}else{
						console.log("登陆成功")
						setCookie("types", "店铺")
						setCookie("name", data.name)
						setCookie("phone", data.phone)
						setCookie("id", data.id)
						setCookie("number", data.number)
						window.location.href="index.jsp"
						}
					}
				}
			})
		}

		function pres() {
			var name = $("#resname").val()
			var phone = $("#resphone").val()
			var pwd = $("#respwd").val()
			var spwd = $("#ressurepwd").val()
			name == "" ? alert("请输入名称") : phone == "" ? alert("请输入手机号")
					: pwd == "" ? alert("请输入密码")
							: pwd != spwd ? alert("两次密码不一致") : $.ajax({
								url : "insertGhShop",
								method : "post",
								data : {
									name : name,
									phone : phone,
									pwd : pwd
								},
								success : function(data) {
									console.log(data)
									if (data == 1) {
										alert("注册完成")
										console.log("pinpai")
										$("#admins").hide()
										$("#mendian").hide()
										$("#pinpai").show()
										$("#pinpaires").hide()
									} else if (data == 2) {
										alert("账号已存在")
									} else {
										alert("注册失败，请重试")
									}
								}
							})
		}

		function alogin() {

		}
	</script>
</body>
</html>