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
<script type="text/javascript">
var id =1
var pag = 1;
var pschool='全部'
	$(document).ready(function() {
		/* $("select#status").change(function(){
			    // alert($(this).val());
			     fenye();
			     selectActivityWsl()
			 }); */
			 if (getCookie("types") == null) {
					window.location.href = "ndl.jsp"
				} else if (getCookie("types") == "店铺"||getCookie("types") == "品牌商") {
					window.location.href = "mqx.jsp"
				} else {
					selectActivityWsl();
				}
		/* findSchool(); */
	});
	/* function findSchool(){
		$
		.ajax({
			method : "post",
			url : "findSchool",
			data : {
				
			},
			dataType : "json",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					var tr = "<option value='"+data[i]+"'>"+data[i]+"</option>"
					$(".select").append(tr)
				}
			}
			})
	} */
	/* function selectByFenye(id, page) {
		$
				.ajax({
					method : "post",
					url : "findshop",
					data : {
					},
					dataType : "json",
					success : function(data) {
						console.log(data)
						$("#selectActivity").empty();
						var pstate = ''
						for (var i = 0; i < data.length; i++) {
							
							var str = "<tr id='sela'>"
								+ "<td style='text-align:left; padding-left:20px;'>"
								+ "<input type='checkbox' name='id[]' value='' />"
								+ (i + 1)
								+ "</td>"
								+ "<td>"
								+ data[i].name
								+ "</td>"
								+ "<td> "
								+ data[i].phone
								+ "</td>"
								+ "<td>"
								+ data[i].pwd
								+ "</td>"
								+ "<td>"
								+ "<div class='button-group'> <a class='button border-red' href='javascript:void(0)' onclick='del("
								+ data[i].id
								+ ")'>"
								+ "<span class='icon-edit'></span> <span id='del"+data[i].id+"'>刪除</span></a> </div>"

								+"</td></tr>";
						$("#selectActivity").append(str);
						}
						pages(id, page);
					}
				});
	} */
	/* function fenye() {
		$.ajax({
			method : "post",
			url : "countAdmin",
			data : {
			},
			dataType : "json",
			success : function(data) {
				pages(1, data.page);
			}
		});
	} */
	var list = []
	function selectActivityWsl() {
		$
				.ajax({
					method : "post",
					url : "findTypes",
					data : {
					},
					dataType : "json",
					success : function(data) {
						$("#selectActivity").empty();
						var pstate = ''
						list=data
						for (var i = 0; i < data.length; i++) {
							var str = "<tr id='sela'>"
								+ "<td style='text-align:left; padding-left:20px;'>"
								+ "<input type='checkbox' name='id[]' value='' />"
								+ (i + 1)
								+ "</td>"
								+ "<td style='height:60px;line-height:60px;vertical-align:top;'><input onchange='changeImg("+data[i].id+")' style='width:60px;height:60px;position:absolute;z-index:1;opacity: 0;' type='file' accept='image/*' class='selfile' id='file"+data[i].id+"'/><img style='width:60px;height:60px;position:absolute;z-index:0;' src='"
								+ data[i].img
								+ "'></td>"
								+ "<td><input style='width:300px;border:none;border-bottom:1px solid #d4d4d4' id='tid"+data[i].id+"' type='text'  value='"
								+ data[i].one
								+ "'/></td>"
								+ "<td><input style='width:300px;border:none;border-bottom:1px solid #d4d4d4' id='tids"+data[i].id+"' type='text'  value='"
								+ data[i].jj
								+ "'/></td>"
								+ "<td>"
								+ "<div class='button-group'><a class='button border-main' href='javascript:void(0)' onclick='sxup("
								+ i
								+ ")'>"
								+ "<span class='icon-edit'></span> <span id='del"+data[i].id+"'>上移</span></a><a class='button border-main' href='javascript:void(0)' onclick='sxdown("
								+ i
								+ ")'>"
								+ "<span class='icon-edit'></span> <span id='del"+data[i].id+"'>下移</span></a> <a class='button border-main' href='javascript:void(0)' onclick='update("
								+ data[i].id
								+ ")'>"
								+ "<span class='icon-edit'></span> <span id='del"+data[i].id+"'>修改</span></a><a class='button border-red' href='javascript:void(0)' onclick='deltype("
								+ data[i].id
								+ ")'>"
								+ "<span class='icon-edit'></span> <span id='del"+data[i].id+"'>删除</span></a> </div>"

								+"</td></tr>";
						$("#selectActivity").append(str);
						}

					}
				})
	}
	
	function changeImg(id) {
		console.log(id)
		console.log($("#file"+id)[0].files[0].name)
		 UpladFile1($("#file"+id)[0].files[0],id)
	}
	function UpladFile1(fileObj,id) {
        var url = "后台图片上传接口"; // 接收上传文件的后台地址 
        
        var form = new FormData(); // FormData 对象

        if(fileObj.size/1024 > 500) { //大于1M，进行压缩上传
            photoCompress(fileObj, {
                quality: 0.2
            }, function(base64Codes){
                //console.log("压缩后：" + base.length / 1024 + " " + base);
                var bl = convertBase64UrlToBlob(base64Codes);
                form.append("file", bl, "file_"+Date.parse(new Date())+".jpg"); // 文件对象
                form.append('id', id);
                $.ajax({
        			url: 'updateTypesImg',
        			type: 'POST',
        			cache: false, //上传文件不需要缓存
        			data: form,
        			processData: false, // 告诉jQuery不要去处理发送的数据
        			contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        			success: function(data) {
        				console.log(data)
        				selectActivityWsl();
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
            form.append('id', id);
            $.ajax({
    			url: 'updateTypesImg',
    			type: 'POST',
    			cache: false, //上传文件不需要缓存
    			data: form,
    			processData: false, // 告诉jQuery不要去处理发送的数据
    			contentType: false, // 告诉jQuery不要去设置Content-Type请求头
    			success: function(data) {
    				console.log("图片"+data)
    				selectActivityWsl();
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
	function sxup(i){
		console.log(list[i])
		if(i==0){
			alert("已经是第一个")
		}else{
			$
			.ajax({
				method : "post",
				url : "updateSxup",
				data : {
					num:list[i].two,
					id1:list[i].id,
					id2:list[i-1].id
				},
				dataType : "json",
				success : function(data) {
					selectActivityWsl();
				}})
		}
	}
	function sxdown(i){
		console.log(list[i])
		if(i==list.length-1){
			alert("已经是最后个")
		}else{
			$
			.ajax({
				method : "post",
				url : "updateSxdown",
				data : {
					num:list[i].two,
					id1:list[i].id,
					id2:list[i+1].id
				},
				dataType : "json",
				success : function(data) {
					selectActivityWsl();
				}})
		}
	}
/* 	function pages(id,page) {
		pag=page*1
		$("#pagelist").empty();
		if(id==1&&id==page&&page!=0){
		var str = "<span onclick='tou(" + id + "," + page
		+ ")'>首页</span> <span >上一页</span> <span'>第"+id+"/"+page+"页</span> <span>下一页</span> <span onclick='wei(" + id + "," + page
		+ ")'>尾页</span>";
		$("#pagelist").append(str);
		}else if(id==1&&id!=page&&page!=0){
			var str = "<span onclick='tou(" + id + "," + page
			+ ")'>首页</span> <span >上一页</span> <span'>第"+id+"/"+page+"</span> <span class='current' onclick='nexts(" + id + "," + page
			+ ")'>下一页</span> <span onclick='wei(" + id + "," + page
			+ ")'>尾页</span>";
			$("#pagelist").append(str);
		}else if(id!=1&&id==page&&page!=0){
			var str = "<span onclick='tou(" + id + "," + page
			+ ")'>首页</span> <span class='current' onclick='tops(" + id + "," + page
			+ ")'>上一页</span> <span'>第"+id+"/"+page+"页</span> <span >下一页</span> <span onclick='wei(" + id + "," + page
			+ ")'>尾页</span>";
			$("#pagelist").append(str);
		}else if(id!=1&&id!=page&&page!=0){
			var str = "<span onclick='tou(" + id + "," + page
			+ ")'>首页</span> <span class='current' onclick='tops(" + id + "," + page
			+ ")'>上一页</span> <span'>第"+id+"/"+page+"</span> <span class='current' onclick='nexts(" + id + "," + page
			+ ")'>下一页</span> <span onclick='wei(" + id + "," + page
			+ ")'>尾页</span>";
			$("#pagelist").append(str);
		}else {
			
		}
		
	} */
	/* function tops(id,page){
		id--;
		selectByFenye(id, page)
		pages(id,page)
	}
	function nexts(id,page){
		id++;
		selectByFenye(id, page)
		pages(id,page)
	}
	function tou(id,page){
		id=1;
		selectByFenye(id, page)
		pages(id,page)
	}
	function wei(id,page){
		id=page;
		selectByFenye(id, page)
		pages(id,page)
	} */
	function update(id){
		console.log(id)
		/* window.location.href="orderDetail.jsp?id="+id */
				var one = $("#tid"+id).val()
		if(window.confirm("确定修改吗？")){
		$.ajax({
			method:"post",
			url:"updateTypes",
			data:{
				id:id,
				one:one,
				jj:$("#tids"+id).val()
			},
			dataType:"json",
			success:function(data){
				console.log(data)
				selectActivityWsl();
			}
		})
		}
	}
	
	function deltype(id){
		console.log(id)
		/* window.location.href="orderDetail.jsp?id="+id */
				var one = $("#tid"+id).val()
		if(window.confirm("确定删除吗？")){
		$.ajax({
			method:"post",
			url:"delTypes",
			data:{
				id:id
			},
			dataType:"json",
			success:function(data){
				console.log(data)
				selectActivityWsl();
			}
		})
		}
	}
	
	var typesimg=''
	function upimg(){
var url = "后台图片上传接口"; // 接收上传文件的后台地址 
        var fileObj=$("#typeimgs")[0].files[0]
        var form = new FormData(); // FormData 对象

        if(fileObj.size/1024 > 500) { //大于1M，进行压缩上传
            photoCompress(fileObj, {
                quality: 0.2
            }, function(base64Codes){
                //console.log("压缩后：" + base.length / 1024 + " " + base);
                var bl = convertBase64UrlToBlob(base64Codes);
                form.append("file", bl, "file_"+Date.parse(new Date())+".jpg"); // 文件对象
                form.append('id', id);
                $.ajax({
        			url: 'uploadImg',
        			type: 'POST',
        			cache: false, //上传文件不需要缓存
        			data: form,
        			processData: false, // 告诉jQuery不要去处理发送的数据
        			contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        			success: function(data) {
        				console.log(data)
        				typesimg=data
        				$(".typeimg").attr("src",typesimg.replace(/"/g,""))
        				$(".typeimg").show()
        				//selectActivityWsl();
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
            form.append('id', id);
            $.ajax({
    			url: 'uploadImg',
    			type: 'POST',
    			cache: false, //上传文件不需要缓存
    			data: form,
    			processData: false, // 告诉jQuery不要去处理发送的数据
    			contentType: false, // 告诉jQuery不要去设置Content-Type请求头
    			success: function(data) {
    				console.log("图片"+data)
    				typesimg=data
    				$(".typeimg").attr("src",typesimg.replace(/"/g,""))
    				$(".typeimg").show()
    				/* selectActivityWsl(); */
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
	function add(){
		$(".model").css("display","flex")
		typesimg=""
			$(".typeimg").hide()
	}
	function addcancle(){
		$(".model").css("display","none")
	}
	function addsure(){
		$.ajax({
			type:"post",
			url:"insertTypes",
			data:{
				one:$("#typeone").val(),
				img:typesimg.replace(/"/g,""),
				jj:$("#jj").val(),
			},
			dataType:"json",
			success:function(data){
				console.log(data)
				$(".model").css("display","none")
				selectActivityWsl();
			}
		})
	}
</script>
<script src="js/pintuer.js"></script>
<style type="text/css">
#sela td input {
	text-align: center;
}

.table td {
	padding: 0;
}

.model {
	width: 100%;
	height: 100%;
	display: none;
	align-items: center;
	justify-content: center;
	background-color: rgba(0, 0, 0, 0.5);
	position: fixed;
	z-index: 999;
}

.main {
	width: 300px;
	height: 340px;
	padding: 20px;
	border-radius: 5px;
	background-color: #fff;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
}

.m_title {
	font-size: 15px;
	font-weight: 600;
	text-align: center;
}

.m_middle {
	display: flex;
	flex-direction: column;
}

.m_bottom {
	display: flex;
	flex-direction: row;
	align-items: center;
	justify-content: space-around;
}

.input {
	border: 1px solid #0099CC;
}

#typeimgs {
	position: absolute;
	width: 88px;
	height: 42px;
	opacity: 0;
}
</style>
</head>
<body>
	<div class="model">
		<div class="main">
			<div class="m_title">添加新分类</div>
			<div class="m_middle">
				分类名称: <input type="text" class="input" style="margin-top: 10px;margin-bottom: 10px;"  id="typeone"
					placeholder="请输入分类名称">
					推荐: <input type="text" class="input" style="margin-top: 10px;" id="jj"
					placeholder="如：耐克精选">
				<div style="margin-top: 20px;">
					<input type="file" id="typeimgs" accept="image/*"
						onchange="upimg()"> <a href="javascript:void(0)"
						class="button border-main" onclick="add()"> 选择图片</a> <img alt=""
						hidden="hidden"
						style="height: 42px; width: 42px; margin-top: 10px;" class="typeimg" src="">

				</div>
			</div>
			<div class="m_bottom" style="margin-top: 0;">
				<a href="javascript:void(0)" class="button border-main"
					onclick="addcancle()"> 取消</a> <a href="javascript:void(0)"
					class="button border-main" onclick="addsure()"> 确定</a>
			</div>
		</div>
	</div>
	<form method="post" action="" id="listform">
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 内容列表</strong> <a href=""
					style="float: right; display: none;">添加字段</a>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left: 10px;">
					<li>添加分类：</li>
					<li><a href="javascript:void(0)"
						class="button border-main icon-edit" onclick="add()"> 添加</a></li>
					<!-- <li><select  class="select" id="status"
						style="border-radius: 5px; height: 42px; width: 200px; border: 1px solid #00AAEE; color: #00AAEE; text-align: center; align-item: center">
							 <option value="全部">全部</option>
							<option value="0">未接单</option>
							<option value="1">待取</option>
							<option value="2">已取</option>
							<option value="3">送回中</option>
							<option value="4">已完成</option>
					</select></li> -->
				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>
					<th width="100" style="text-align: left; padding-left: 20px;"><input
						type="checkbox" id="checkall" />编号</th>
					<th>图片</th>
					<th>品牌</th>
					<th>推荐</th>
					<th width="310">操作</th>
				</tr>
				<volist name="list" id="vo">
				<tbody id="selectActivity">

				</tbody>
				<tr>
				</tr>
				<tr>
					<td colspan="8"><div class="pagelist" id="pagelist"></div></td>
				</tr>
			</table>
		</div>
	</form>
	<script type="text/javascript">
		//搜索
		/* function changesearch() {
			var name = $("#keywords").val();
			$
					.ajax({
						method : "post",
						url : "/company/policySelectName",
						data : {
							name : name
						},
						dataType : "json",
						success : function(data) {
							$("#sela").remove();
							for (var i = 0; i < data.length; i++) {
								var str = "<tr id='sela'>"
										+ "<td style='text-align:left; padding-left:20px;'>"
										+ "<input type='checkbox' name='id[]' value='' />"
										+ data[i].id
										+ "</td>"
										+ "<td width='10%'>"
										+ data[i].name
										+ "</td>"
										+ "<td>"
										+ data[i].level
										+ "</td>"
										+ "<td>"
										+ data[i].stuats
										+ "</font></td>"
										+ "<td>"
										+ data[i].stype
										+ "</td>"
										+ " <td>"
										+ data[i].publishTime
										+ "</td>"
										+ "<td></td>"
										+ "</tr>";
								$("#selectActivity").append(str);
							}
						}
					})
		}
	/* 	 */
		//单个删除
	/* 	function del(id) {
			if (confirm("您确定要删除吗?")) {
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
			}
		} */ 
		//修改
		/* function update(id) {
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
			} 
		} */
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