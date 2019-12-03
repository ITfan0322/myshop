<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title></title>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery.js"></script>
<script src="js/pintuer.js"></script>
<script src="js/cookies.js"></script>
</head>
<script type="text/javascript">
	if (getCookie("types") == null) {
		window.location.href = "mdl.jsp"
	} else if (getCookie("types") == "管理员" || getCookie("types") == "店铺") {
		window.location.href = "mqx.jsp"
	} else {
	}
	var types = ""
	$(function() {
		show()
	})
	function show() {
		$.ajax({
			method : "post",
			url : "findTypes",
			data : {
				str : types
			},
			dataType : "json",
			success : function(data) {
				console.log(data)
				types = data[0].one
				$("#test").html("")
				for (var i = 0; i < data.length; i++) {
					var str = '<option value="'+data[i].one+'">' + data[i].one
							+ '</option>';
					$("#test").append(str)
				}
			}
		})
	}
	/* function isSelected(){
		var opt=$("#test").val();
	   console.log(opt)
	   types=opt
	   show()
	} */
	
	
	function photoCompress(file,w,objDiv){
        var ready=new FileReader();
        /*开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.*/
        ready.readAsDataURL(file);
        ready.onload=function(){
            var re=this.result;
            canvasDataURL(re,w,objDiv)
        }
    }
    function canvasDataURL(path, obj, callback){
        var img = new Image();
        img.src = path;
        img.onload = function(){
            var that = this;
            // 默认按比例压缩
            var w = that.width,
                h = that.height,
                scale = w / h;
            w = obj.width || w;
            h = obj.height || (w / scale);
            var quality = 0.7;  // 默认图片质量为0.7
            //生成canvas
            var canvas = document.createElement('canvas');
            var ctx = canvas.getContext('2d');
            // 创建属性节点
            var anw = document.createAttribute("width");
            anw.nodeValue = w;
            var anh = document.createAttribute("height");
            anh.nodeValue = h;
            canvas.setAttributeNode(anw);
            canvas.setAttributeNode(anh);
            ctx.drawImage(that, 0, 0, w, h);
            // 图像质量
            if(obj.quality && obj.quality <= 1 && obj.quality > 0){
                quality = obj.quality;
            }
            // quality值越小，所绘制出的图像越模糊
            var base64 = canvas.toDataURL('image/jpeg', quality);
            // 回调函数返回base64的值
            callback(base64);
        }
    }
    /**
     * 将以base64的图片url数据转换为Blob
     * @param urlData
     *            用url方式表示的base64图片数据
     */
    function convertBase64UrlToBlob(urlData){
        var arr = urlData.split(','), mime = arr[0].match(/:(.*?);/)[1],
            bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
        while(n--){
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new Blob([u8arr], {type:mime});
    }

	 //上传文件方法
	 var img=[]
        function UpladFile(fileObj) {
		 var data = ""
		        var url = "后台图片上传接口"; // 接收上传文件的后台地址 

		        var form = new FormData(); // FormData 对象

		        if(fileObj.size/1024 > 500) { //大于1M，进行压缩上传
		            photoCompress(fileObj, {
		                quality: 0.2
		            }, function(base64Codes){
		                //console.log("压缩后：" + base.length / 1024 + " " + base);
		                var bl = convertBase64UrlToBlob(base64Codes);
		                form.append("file", bl, "file_"+Date.parse(new Date())+".jpg"); // 文件对象
		                $.ajax({
		        			url: 'uploadImg',
		        			type: 'POST',
		        			cache: false, //上传文件不需要缓存
		        			data: form,
		        			processData: false, // 告诉jQuery不要去处理发送的数据
		        			contentType: false, // 告诉jQuery不要去设置Content-Type请求头
		        			success: function(data) {
		        				console.log(data)
		        				img.push(data.replace(/"/g,""))
		        				//var rs = eval("(" + data + ")");
//		        				if(rs.state == 1) {
//		        					//tipTopShow('上传成功！');
//		        					console.log('上传成功！')
//		        				} else {
//		        					//tipTopShow(rs.msg);
//		        				}
		        			},
		        			error: function(data) {
		        				//tipTopShow("上传失败");
		        			}
		        		})
		            });
		        }else{ //小于等于1M 原图上传
		            form.append("file", fileObj); // 文件对象
		            $.ajax({
		    			url: 'uploadImg',
		    			type: 'POST',
		    			cache: false, //上传文件不需要缓存
		    			data: form,
		    			processData: false, // 告诉jQuery不要去处理发送的数据
		    			contentType: false, // 告诉jQuery不要去设置Content-Type请求头
		    			success: function(data) {
		    				console.log("图片"+data)
		    				img.push(data.replace(/"/g,""))
		    				
		    				//var rs = eval("(" + data + ")");
//		    				if(rs.state == 1) {
//		    					//tipTopShow('上传成功！');
//		    					console.log('上传成功！')
//		    				} else {
//		    					//tipTopShow(rs.msg);
//		    				}
		    			},
		    			error: function(data) {
		    				//tipTopShow("上传失败");
		    			}
		    		})
		        }
            
        }
    
    
    var imgs=[]
    
    //上传文件方法
    function UpladFiles(fileObj) {
    	var data = ""
        var url = "后台图片上传接口"; // 接收上传文件的后台地址 

        var form = new FormData(); // FormData 对象

        if(fileObj.size/1024 > 500) { //大于1M，进行压缩上传
            photoCompress(fileObj, {
                quality: 0.2
            }, function(base64Codes){
                //console.log("压缩后：" + base.length / 1024 + " " + base);
                var bl = convertBase64UrlToBlob(base64Codes);
                form.append("file", bl, "file_"+Date.parse(new Date())+".jpg"); // 文件对象
                $.ajax({
        			url: 'uploadImg',
        			type: 'POST',
        			cache: false, //上传文件不需要缓存
        			data: form,
        			processData: false, // 告诉jQuery不要去处理发送的数据
        			contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        			success: function(data) {
        				console.log(data)
        				imgs.push(data.replace(/"/g,""))
        				//var rs = eval("(" + data + ")");
//        				if(rs.state == 1) {
//        					//tipTopShow('上传成功！');
//        					console.log('上传成功！')
//        				} else {
//        					//tipTopShow(rs.msg);
//        				}
        			},
        			error: function(data) {
        				//tipTopShow("上传失败");
        			}
        		})
            });
        }else{ //小于等于1M 原图上传
            form.append("file", fileObj); // 文件对象
            $.ajax({
    			url: 'uploadImg',
    			type: 'POST',
    			cache: false, //上传文件不需要缓存
    			data: form,
    			processData: false, // 告诉jQuery不要去处理发送的数据
    			contentType: false, // 告诉jQuery不要去设置Content-Type请求头
    			success: function(data) {
    				console.log("图片"+data)
    				imgs.push(data.replace(/"/g,""))
    				
    				//var rs = eval("(" + data + ")");
//    				if(rs.state == 1) {
//    					//tipTopShow('上传成功！');
//    					console.log('上传成功！')
//    				} else {
//    					//tipTopShow(rs.msg);
//    				}
    			},
    			error: function(data) {
    				//tipTopShow("上传失败");
    			}
    		})
        }
        
    }
	function selimg() {
		var fileObj = document.getElementById("file").files; // js 获取文件对象
		for (var i = 0; i < fileObj.length; i++) {
			console.log("返回",UpladFile(fileObj[i]))
			
		}
		
		console.log("iiii=" + img)

	}
	
	function selimgs() {
		var fileObj = document.getElementById("files").files; // js 获取文件对象
		for (var i = 0; i < fileObj.length; i++) {
			console.log("返回",UpladFiles(fileObj[i]))
			
		}
		console.log("图片=" + JSON.stringify(imgs))
	}
	function upimgs() {

		$.ajax({
			type : "post",
			url : "insertMenu",
			data : {
				name : $("#name").val(),
				num : 0,
				price : $("#price").val(),
				size : JSON.stringify($("#size").val().split("\/")),
				colors : JSON.stringify($("#colors").val().split("\/")),
				img :JSON.stringify(img)+"",
				types : getCookie("name"),
				imgs : JSON.stringify(imgs),
				two : JSON.stringify($("#two").val().split("\/")),
				ghid : getCookie("id"),
				shops : getCookie("name")
			},
			success : function(data) {
				console.log(data)
				window.location.href = "readAdd.jsp"
			}

		});
	}
</script>
<style>
<!--
-->
.loading {
	height: 100%;
	width: 100%;
	position: fixed;
	background: rgba(255, 255, 255, 0.2);
	display: none;
}

.loading>p {
	position: absolute;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
	margin: auto;
	height: 160px;
	width: 160px;
	text-align: center;
	line-height: 160px;
	font-size: 30px;
	color: #f00;
}

.loading p span {
	position: absolute;
	display: block;
	height: 140px;
	width: 140px;
	margin: 10px;
	border-radius: 50%;
	-webkit-box-shadow: 0 2px 3px rgba(102, 197, 37, 0.8);
	animation: loading ease 1s infinite;
	left: 0;
	top: 0;
}

@
keyframes loading { 0%{
	transform: rotate(0deg)
}
100%{
transform
:rotate(360deg)
}
}
</style>
<body>
	<div class="loading">

		<p>
			上传中...<span></span>
		</p>

	</div>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>增加商品</strong>
		</div>
		<div class="padding border-bottom">
			<ul class="search" style="padding-left: 10px;">
			</ul>
		</div>
		<div class="body-content">
			<form class="form-x" method="POST" enctype="multipart/form-data">
				<div class="form-group">
					<div class="label">
						<label>封面图片：</label>
					</div>
					<div class="field">
						<input type="file" class="input w50" value="" accept="image/*"
							id="file" name="file" onchange="selimg()"  multiple/>
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>详情图片：</label>
					</div>
					<div class="field">
						<input type="file" class="input w50" value="" accept="image/*"
							id="files" name="files" onchange="selimgs()" multiple />
						<div class="tips"></div>
					</div>

				</div>
				<!-- <div class="form-group">
				<div class="label">
						<label>图片预览：</label>
					</div>
					<div class="field">
						<div class="tips"></div>
						<div id="showimg">
							<img src="" style="width: 100px; height: 100px; margin-right: 10px; float: left;">
						</div>
						<div class="tips"></div>
					</div>

				</div> -->
				<div class="form-group">
					<div class="label">
						<label>商品名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" id="name"
							name="name" data-validate="required:请输入商品名称"
							placeholder="请输入商品名称" />
						<div class="tips"></div>
					</div>
				</div>
				<!-- <div class="form-group">
					<div class="label">
						<label>商品品牌：</label>
					</div>
					<div class="field">
						<select name="types" class="input w50" id="test">
							<option value="甄选水果">甄选水果</option>
						<option value="新鲜蔬菜">新鲜蔬菜</option>
						<option value="网红食品">网红食品</option>
						<option value="进口专区">进口专区</option>
						<option value="奶品水饮">奶品水饮</option>
						<option value="罐头熟食">罐头熟食</option>
						<option value="冲调速食">冲调速食</option>
						<option value="粮油调料">粮油调料</option>
						<option value="肉蛋冷鲜">肉蛋冷鲜</option>
						<option value="个护家洁">个护家洁</option>

						</select>
						<div class="tips"></div>
					</div>
				</div> -->
				<!-- <div class="form-group">
					<div class="label">
						<label>商品详细分类：</label>
					</div>
					<div class="field">
						<select name="two" class="input w50" id="tye" 
						>
						<option value="食品">食品</option>
						<option value="牛奶">牛奶</option>
						<option value="新鲜水果">新鲜水果</option>
						<option value="酒水饮料">酒水饮料</option>
						<option value="干湿酱调">干湿酱调</option>
						<option value="日用百货">日用百货</option>
						<option value="米面粮油">米面粮油</option>
						<option value="禽蛋">禽蛋</option>
						<option value="新鲜青菜">新鲜青菜</option>
						<option value="辣小鸭">辣小鸭</option>
						<option value="速冻食品">速冻食品</option>
						
					</select>
						<div class="tips"></div>
					</div>
				</div> -->
				<div class="form-group">
					<div class="label">
						<label>商品价格：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="price"
							id="price" data-validate="required:请输入商品价格" placeholder="请输入商品价格" />
						<div class="tips"></div>
					</div>
				</div>
				<!-- <div class="form-group">
					<div class="label">
						<label>商品原价：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="oldprice" id="oldprice"
							data-validate="required:请输入商品原价" />
						<div class="tips"></div>
					</div>
				</div> -->
				<div class="form-group">
					<div class="label">
						<label>颜色分类：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="colors"
							id="colors" data-validate="required:请输入颜色，多个用/分割（如：黑色/白色）"
							placeholder="请输入颜色，多个用/分割（如：黑色/白色）" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>尺码分类：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="size"
							id="size" data-validate="required:请输入尺码，多个用/分割（如：L/XL）"
							placeholder="请输入尺码，多个用/分割（如：L/XL）" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>商品特点：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="two" id="two"
							data-validate="required:请输入商品特点，多个用/分割（如：柔软/纯棉）"
							placeholder="请输入商品特点，多个用/分割（如：柔软/纯棉）" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="field" style="margin-left: 10px;"></div>
		</div>
		<div class="form-group" style="margin-left: 10%;">
			<div class="label">
				<label><button class="button bg-main icon-check-square-o"
						type="button" onclick="upimgs()">提交</button></label>
			</div>
			<div class="field"></div>
		</div>
		</form>
	</div>
	</div>
</body>
<script type="text/javascript">
	/* function isSelected() {
		var myselect = document.getElementById("test");
		var index = myselect.selectedIndex;
		var value = myselect.options[index].value;
	}
	function selectD(){
		var myselect = document.getElementById("text2");
		var index = myselect.selectedIndex;
		var value = myselect.options[index].value;
		console.log(value)

	} */
</script>
</html>