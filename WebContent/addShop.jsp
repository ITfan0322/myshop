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
<script src="js/fileUpLod.js"></script>
<script src="js/cookies.js"></script>

</head>
<script type="text/javascript">
if (getCookie("types") == null) {
	window.location.href = "mdl.jsp"
} else if (getCookie("types") == "店铺"||getCookie("types") == "品牌商") {
	window.location.href = "mqx.jsp"
} else {
}
    var User = function() {
        this.init = function() {
            //模拟上传excel  
            $("#EventBtn").unbind("click").bind("click", function() {
                $("#EventFile").click();
            });
            $("#EventFile").bind("change", function() {
                $("#EventPath").attr("value",    $("#EventFile").val());
            });
        };
        //点击上传钮  
        this.Btn = function() {
        	$(".loading").show();
            var EventFile = $("#EventFile").val();
            if (EventFile == '') {
                alert("请择excel,再上传");
            } else if (EventFile.lastIndexOf(".xls") < 0) {//可判断以.xls和.xlsx结尾的excel  
                alert("只能上传Excel文件");
            } else {
                var url = "insertYH";
                var formData = new FormData($('form')[0]);
                user.sendAjaxRequest(url, "POST", formData);
            }
        };
        this.sendAjaxRequest = function(url, type, data) {
        	
             $.ajax({
                url : url,
                type : type,
                data : data,
                dataType : "json",
                success : function() {
                    //alert(result);
                	$(".loading").hide()
                    
                },
                error : function(result) {
                	$(".loading").hide()
                },
                cache : false,
                contentType : false,
                processData : false
            }); 
        };
    };
    var user;
    $(function() {
        user = new User();
        user.init();
    });
</script>
<style>
<!--

-->
.loading{
          height:100%;width:100%;position:fixed;background:rgba(255,255,255,0.2);display:none;

    }
    .loading >p{position:absolute;left:0;right:0;top:0;bottom:0;margin:auto;height:160px;width:160px;text-align: center;line-height:160px;font-size: 30px;color:#f00;}
    .loading p span{position:absolute;display:block;height:140px;width:140px;margin:10px;border-radius:50%;-webkit-box-shadow:0 2px 3px rgba(102,197,37,0.8); animation:loading ease 1s infinite;left:0;top:0;}
 
    @keyframes loading{
       0%{transform:rotate(0deg)}
       100%{transform:rotate(360deg)}

    }
</style>
<body>
<div class="loading">
    
     <p>上传中...<span></span></p>

</div>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>增加商品</strong>
		</div>
		<div class="padding border-bottom">
				<ul class="search" style="padding-left: 10px;">
					<!-- <li>批量导入：</li>
					
					<li>
					 <form  enctype="multipart/form-data" style="width:300px;" id="batch"  action="File" method="post" class="form-horizontal">    
        <button class="button border-main icon-file" id="EventBtn"   type="button" >择文件</button>  
        <input type="file" name="file"  style="width:0px;height:0px;" id="EventFile">  
        <input id="EventPath"  disabled="disabled"  type="text" placeholder="请择excel表" style="border: 1px solid #e6e6e6; height: 26px;width: 200px;" />                                           
     </form> 

					</li>
					<li>
					    <button  type="button" class="button border-main"  onclick="user.Btn()" >上传</button> 
					</li> -->
				</ul>
			</div>
		<div class="body-content">
			<form class="form-x" action="insertShops" method="POST" enctype="multipart/form-data">
				<div class="form-group">
					<div class="label">
						<label>店铺名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="name"
							data-validate="required:请输入姓名" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>店铺地址：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="address"
							data-validate="required:请输入店铺地址" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="field" style="margin-left:10px;">
					
				</div>
			
		</div>
		
		<div class="form-group" style="margin-left:10%;">
			<div class="label">
				<label><button class="button bg-main icon-check-square-o" type="submit">
					提交</button></label>
			</div>
			<div class="field">
				
			</div>
		</div>
		</form>
	</div>
	</div>

</body>

<!-- <script type="text/javascript">
	//实例化编辑器很重要
	/* 	UE.getEditor("container"); */
</script> -->
<script type="text/javascript">
	///document.getElementById('iframeid').contentWindow     获取的是window对象，
	///document.getElementById('iframeid').contentWindow.document     获取的是window对象
	function isSelected() {
		var myselect = document.getElementById("test");
		var index = myselect.selectedIndex;
		var value = myselect.options[index].value;

/* 		$.ajax({
			type : "POST",
			url : "selectAdress",
			data : {
				'value' : value
			},
			contentType : "application/x-www-form-urlencoded",
			success : function(data) {
				console.log(data)
				var ss=JSON.parse(data);
		 			for (var i = 0; i < data.length; i++) {
						var str ="<option value='"+ss[i].dormitory+"'>"+ss[i].dormitory+"</option>"
						$("#text2").append(str);
					}  
			},
			error : function(data) {
			}
		}); */
	}
	function selectD(){
		var myselect = document.getElementById("text2");
		var index = myselect.selectedIndex;
		var value = myselect.options[index].value;
		console.log(value)

	}
	/* 	function submitRead() {
	 alert(0);
	 var str = document.getElementById('readIframe').contentWindow
	 .readContent();
	 ;
	 alert(str);

	 } */
</script>
</html>