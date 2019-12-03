<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery-2.2.4.min.js"></script>
<script src="js/cookies.js"></script>
<script src="js/imageUtil.js"></script>
<style type="text/css">
#sela td input {
	text-align: center;
}
</style>
<script type="text/javascript">
	var sid = 1
	var id = 1
	var pag = 1;
	var names = ''
	var status=''
	$(document).ready(function() {
		/* $("select#status").change(function(){
			    // alert($(this).val());
			     fenye();
			     selectActivityWsl()
			 }); */
			 if (getCookie("types") == null) {
					window.location.href = "mdl.jsp"
				} else if (getCookie("types") == "店铺") {
					window.location.href = "mqx.jsp"
				} else {
					selectActivityWsl();
					fenye();
				}
		/* findSchool(); */
	});
	function selectByFenye(id, page) {
		console.log(sid)
		console.log(id)
		$
				.ajax({
					method : "post",
					url : "fenyeMenu",
					data : {
						id : id,
						name:names,
						status:status,
						ghid:getCookie("types")=="管理员"?"":"and ghid="+getCookie("id")
					},
					dataType : "json",
					success : function(data) {
						$("#selectActivity").empty();
						var pstate = ''
						for (var i = 0; i < data.length; i++) {
							var status=""
								if(data[i].status==1){
									status="下架"
								}else{
									status="上架"
								}
							var str = "<tr id='sela'>"
								+ "<td style='text-align:left; padding-left:20px;'>"
								+ "<input type='checkbox' id='selects' name='id[]' value='"+data[i].id+"' />"
								+ (i + 1)
								+ "</td>"
								+ "<td>"
								+ data[i].name
								+ "</td>"
								+ "<td><div style='height:60px;display:flex;align-items:center;justify-content:center;'><input style='width: 60px;height: 60px;position: absolute;z-index: 2;opacity: 0;' accept='image/gif,image/jpeg,image/jpg,image/png,image/svg' type='file' id='input_file"+data[i].id+"' onchange='selectImg("+data[i].id+")'><image style='width:60px;height:60px;z-index:1;' src='"
								+ JSON.parse(data[i].img)[0]
								+ "'></div></td>"
								+ "<td>"
								+ (data[i].price * 1).toFixed(2)
								+ "</td>"
								+ "<td>"
								+ (data[i].num * 1)
								+ "</td>"
								+ "<td>"
								+ data[i].types
								+ "</td>"
							+ "<td>"
							+ "<div class='button-group'><a class='button border-blue' href='javascript:void(0)' onclick='see("
							+ data[i].id
							+ ")'>"
							+ "<span></span> <span id='del"+data[i].id+"'>查看详情</span></a> <a class='button border-red' href='javascript:void(0)' onclick='del("
							+ data[i].id
							+ ")'>"
							+ "<span></span> <span id='del"+data[i].id+"'>刪除</span></a> <a class='button border-red' href='javascript:void(0)' onclick='updown("
							+ data[i].id+","+data[i].status
							+ ")'>"
							+ "<span></span> <span id='updown"+data[i].id+"'>"+status+"</span></a></div>"
							+ "</td></tr>";
						$("#selectActivity").append(str);
						}
						pages(id, page);
					}
				});
	}
	function fenye() {
		$.ajax({
			method : "post",
			url : "countMenu",
			data : {
				name:names,
				status:status,
				ghid:getCookie("types")=="管理员"?"":"and ghid="+getCookie("id")
			},
			dataType : "json",
			success : function(data) {
				pages(1, data.page);
				pag=data.page
				
			}
		});
	}
	
	function selectActivityWsl() {
		$
				.ajax({
					method : "post",
					url : "fenyeMenu",
					data : {
						id : 1,
						name:names,
						status:status,
						ghid:getCookie("types")=="管理员"?"":"and ghid="+getCookie("id")
					},
					dataType : "json",
					success : function(data) {
						$("#selectActivity").empty();
						var pstate = ''
						
						for (var i = 0; i < data.length; i++) {
							var status=""
								if(data[i].status==1){
									status="下架"
								}else{
									status="上架"
								}
							
							var str = "<tr id='sela'>"
									+ "<td style='text-align:left; padding-left:20px;'>"
									+ "<input type='checkbox' id='selects' name='id[]' value='"+data[i].id+"' />"
									+ (i + 1)
									+ "</td>"
									+ "<td>"
									+ data[i].name
									+ "</td>"
									+ "<td><div style='height:60px;display:flex;align-items:center;justify-content:center;'><input style='width: 60px;height: 60px;position: absolute;z-index: 2;opacity: 0;' accept='image/gif,image/jpeg,image/jpg,image/png,image/svg' type='file' id='input_file"+data[i].id+"' onchange='selectImg("+data[i].id+")'><image style='width:60px;height:60px;z-index:1;' src='"
									+ JSON.parse(data[i].img)[0]
									+ "'></div></td>"
									+ "<td>"
									+ (data[i].price * 1).toFixed(2)
									+ "</td>"
									+ "<td>"
									+ (data[i].num * 1)
									+ "</td>"
									+ "<td>"
									+ data[i].types
									+ "</td>"
								+ "<td>"
								+ "<div class='button-group'> <a class='button border-blue' href='javascript:void(0)' onclick='see("
								+ data[i].id
								+ ")'>"
								+ "<span></span> <span id='del"+data[i].id+"'>查看详情</span></a><a class='button border-red' href='javascript:void(0)' onclick='del("
								+ data[i].id
								+ ")'>"
								+ "<span></span> <span id='del"+data[i].id+"'>刪除</span></a> <a class='button border-red' href='javascript:void(0)' onclick='updown("
								+ data[i].id+","+data[i].status
								+ ")'>"
								+ "<span></span> <span id='updown"+data[i].id+"'>"+status+"</span></a></div>"
								+ "</td></tr>";
							$("#selectActivity").append(str);

						}
					}
				})
	}
	
	function selectImg(fileid) {
		console.log($('#input_file'+fileid)[0].files[0].name)
		UpladFile1($('#input_file'+fileid)[0].files[0],fileid)
		//var formData = new FormData();
		/* formData.append('file', $('#input_file')[0].files[0]); //添加图片信息的参数
		formData.append('id', fileid); //添加图片信息的参数
		$.ajax({
			url: 'uploadImgs',
			type: 'POST',
			cache: false, //上传文件不需要缓存
			data: formData,
			processData: false, // 告诉jQuery不要去处理发送的数据
			contentType: false, // 告诉jQuery不要去设置Content-Type请求头
			success: function(data) {
				console.log(data)
				selectByFenye(sid, pag);
				//var rs = eval("(" + data + ")");
//				if(rs.state == 1) {
//					//tipTopShow('上传成功！');
//					console.log('上传成功！')
//				} else {
//					//tipTopShow(rs.msg);
//				}
			},
			error: function(data) {
				//tipTopShow("上传失败");
			}
		}) */

	}
	function updown(id,status){
		console.log(status,id)
		$.ajax({
			method : "post",
			url : "updateMeneStatus",
			data : {
				id:id,
				status:(status==1?0:1)
			},
			dataType : "json",
			success : function(data) {
				selectByFenye(sid, pag);
				console.log(data)
				
			}
		});
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
        			url: 'uploadImgs',
        			type: 'POST',
        			cache: false, //上传文件不需要缓存
        			data: form,
        			processData: false, // 告诉jQuery不要去处理发送的数据
        			contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        			success: function(data) {
        				console.log(data)
        				selectByFenye(sid, pag);
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
    			url: 'uploadImgs',
    			type: 'POST',
    			cache: false, //上传文件不需要缓存
    			data: form,
    			processData: false, // 告诉jQuery不要去处理发送的数据
    			contentType: false, // 告诉jQuery不要去设置Content-Type请求头
    			success: function(data) {
    				console.log("图片"+data)
    				selectByFenye(sid, pag);
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
	function pages(id, page) {
		console.log("当前页码："+pag)
		$("#pagelist").empty();
		if (id == 1 && id == page && page != 0) {
			var str = "<span onclick='tou(" + id + "," + page
					+ ")'>首页</span> <span >上一页</span> <span'>第" + id + "/"
					+ page + "页</span> <span>下一页</span> <span onclick='wei("
					+ id + "," + page + ")'>尾页</span>";
			$("#pagelist").append(str);
		} else if (id == 1 && id != page && page != 0) {
			var str = "<span onclick='tou(" + id + "," + page
					+ ")'>首页</span> <span >上一页</span> <span'>第" + id + "/"
					+ page + "</span> <span class='current' onclick='nexts("
					+ id + "," + page + ")'>下一页</span> <span onclick='wei("
					+ id + "," + page + ")'>尾页</span>";
			$("#pagelist").append(str);
		} else if (id != 1 && id == page && page != 0) {
			var str = "<span onclick='tou(" + id + "," + page
					+ ")'>首页</span> <span class='current' onclick='tops(" + id
					+ "," + page + ")'>上一页</span> <span'>第" + id + "/" + page
					+ "页</span> <span >下一页</span> <span onclick='wei(" + id
					+ "," + page + ")'>尾页</span>";
			$("#pagelist").append(str);
		} else if (id != 1 && id != page && page != 0) {
			var str = "<span onclick='tou(" + id + "," + page
					+ ")'>首页</span> <span class='current' onclick='tops(" + id
					+ "," + page + ")'>上一页</span> <span'>第" + id + "/" + page
					+ "</span> <span class='current' onclick='nexts(" + id
					+ "," + page + ")'>下一页</span> <span onclick='wei(" + id
					+ "," + page + ")'>尾页</span>";
			$("#pagelist").append(str);
		} else {

		}

	}
	function tops(id, page) {
		id--;
		sid--;
		selectByFenye(id, page)
		pages(id, page)
	}
	function nexts(id, page) {
		id++;
		sid++;
		console.log(id)
		selectByFenye(id, page)
		pages(id, page)
	}
	function tou(id, page) {
		id = 1;
		sid = 1;
		selectByFenye(id, page)
		pages(id, page)
	}
	function wei(id, page) {
		id = page;
		sid = page;
		selectByFenye(id, page)
		pages(id, page)
	}
	function del(id) {
		console.log(id)
		/* window.location.href="orderDetail.jsp?id="+id */
		if (window.confirm("确定删除吗？")) {
			$.ajax({
				method : "post",
				url : "delMenu",
				data : {
					id : id
				},
				dataType : "json",
				success : function(data) {
					selectByFenye(sid, pag);
				}
			})
		}
	}
	function go(){
		var va = $(".gopage").val()
		sid=va
		selectByFenye(va, pag)
		
	}
	function see(id){
		window.location.href="mdetail.jsp?id="+id
	}
	function xiajia(ty){
		var chk_value =[];//定义一个数组    
        $('#selects:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
        chk_value.push($(this).val());//将选中的值添加到数组chk_value中    
        });
		console.log(chk_value)
		if(chk_value.length==0){
			alert("未选择")
		}else{
		$.ajax({
				method : "post",
				url : "updateMeneAllStatus",
				data : {
					a : JSON.stringify(chk_value),
					status:ty
				},
				dataType : "json",
				success : function(data) {
					console.log(data)
					selectByFenye(sid, pag);
					/* selectActivityWsl();
					
					fenye(); */
				}
			})
	}}
	function xgfl(){
		var chk_value =[];//定义一个数组    
        $('#selects:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
        chk_value.push($(this).val());//将选中的值添加到数组chk_value中    
        });
		console.log(chk_value)
		if(chk_value.length==0){
			alert("未选择")
		}else{
			
		
		$.ajax({
				method : "post",
				url : "updateAllMenuType",
				data : {
					a : JSON.stringify(chk_value),
					types:$("#changetype").val()
				},
				dataType : "json",
				success : function(data) {
					console.log(data)
					selectByFenye(sid, pag);
					$("#xgflmodel").css("display","none")
					/* selectActivityWsl();
					
					fenye(); */
				}
			})
		}
	}
	function xgfls(){
		$("#xgflmodel").css("display","flex")
	}
	function xcancle(){
		$("#xgflmodel").css("display","none")
	}
</script>
<script src="js/pintuer.js"></script>
</head>
<body>
	<div id="xgflmodel" style="position: fixed;top: 0;left: 0;width: 100%;height: 100%;display: none;align-items: center;justify-content: center;background-color: rgba(0,0,0,0.5);">
		<div style="padding: 20px;background-color: #fff;border-radius: 3px;width:300px;">
			<select id="changetype" class="button border-main" style="width: 100%">

			</select>
			<div style="display: flex;flex-direction: row;align-items: center;justify-content:space-around;margin-top: 15px;width: 100%">
			<a href="javascript:void(0)" class="button border-main" style="width: 50%;text-align: center;"
						onclick="xcancle()"> 取消</a>
			<a href="javascript:void(0)" class="button border-main" style="width: 50%;text-align: center;" 
						onclick="xgfl()"> 确定</a>
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
					<li>搜索：</li>
					<li><input type="text" placeholder="请输入搜索关键字" name="keywords"
						id="keywords" class="input"
						style="width: 250px; line-height: 17px; display: inline-block" />
						<a href="javascript:void(0)"
						class="button border-main icon-search" onclick="changesearch()">
							搜索</a></li>
					<li><a href="javascript:void(0)" class="button border-main"
						onclick="xiajia(1)"> 批量上架</a></li>
					<li>
					<li><a href="javascript:void(0)" class="button border-main"
						onclick="xiajia(0)"> 批量下架</a></li>
					<!-- <li><a href="javascript:void(0)" class="button border-main"
						onclick="xgfls()"> 修改分类</a></li> -->
					<!-- <li><select class="select" id="status" onchange="mytypes()"
						style="border-radius: 5px; height: 42px; width: 200px; border: 1px solid #00AAEE; color: #00AAEE; text-align: center; align-item: center">
 -->


							<!-- <option value='新鲜蔬菜'>新鲜蔬菜</option>
							<option value='网红食品'>网红食品</option>
							<option value='进口专区'>进口专区</option>
							<option value='奶品水饮'>奶品水饮</option>
							<option value='罐头熟食'>罐头熟食</option>
							<option value='冲调速食'>冲调速食</option>
							<option value='粮油调料'>粮油调料</option>
							<option value='肉蛋冷鲜'>肉蛋冷鲜</option>
							<option value='个护家洁'>个护家洁</option> -->
					<!-- </select></li> -->
				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>
					<th width="100" style="text-align: left; padding-left: 20px;"><input
						type="checkbox" id="checkall" />编号</th>
					<th>商品名称</th>
					<th>图片</th>
					<th>价格</th>
					<th>已售</th>
					<th>品牌</th>
					<th width="310">操作</th>
				</tr>
				<volist name="list" id="vo">
				<tbody id="selectActivity">

				</tbody>
				<tr>
				</tr>
				<tr>
					<td colspan="8"><span class="pagelist" id="pagelist"></span> <span>跳转到
							<input type="text" class="gopage" style="width: 50px;"></input>
					</span> 页 <input type="button" value="GO" onclick="go()"></td>
				</tr>
			</table>
		</div>
	</form>
	<script type="text/javascript">
		//搜索
		function changesearch() {
			var name = $("#keywords").val();
			names=name
			selectActivityWsl()
			fenye()
		}
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
		function isSelected(id) {
			var opt = $("#test"+id).val();
			console.log(opt)
			types = opt
			$.ajax({
				method : "post",
				url : "findTypes",
				data : {
					str : opt
				},
				dataType : "json",
				success : function(data) {
					console.log(data)
					seltwo.html("");
					for (var i = 0; i < data.length; i++) {
						var str = '<option value="'+data[i].two+'">'
								+ data[i].two + '</option>'
						seltwo.append(str)
					}
					/* $("#seltwo" + id + " option[value='" + two + "']")
							.attr("selected", true); */
				}
			})
		}
		function update(ids) {
			//alert($("#phone"+id).val())
			var update = $("#update" + ids).html();
			var name = $("#name" + ids).val();
			var test = $("#test" + ids)
			var num = $("#num" + ids).val()
			var kucun = $("#kucun" + ids).val()
			var oldprice = $("#oldprice" + ids).val()
			var price = $("#price" + ids).val();
			var types = $("#types" + ids).val();
			var message = $("#message" + ids).val();
			
			console.log(name)
			console.log(price)
			console.log(types)
			console.log(message)
			if (update == '修改') {
				$.ajax({
					method : "post",
					url : "findTypes",
					data : {
					},
					dataType : "json",
					success : function(data) {
						$(".ttypes").html("")
						for(var i=0;i<data.length;i++){
							var str="<option value='"+data[i].one+"'>"+data[i].one+"</option>";
							$(".ttypes").append(str)
						}
						$("#test" + ids + " option[value='" + types + "']").attr(
								"selected", true);
					}
				});
				
				$("#update" + ids).html("保存");
				$("#name" + ids).attr("disabled", false);
				$("#price" + ids).attr("disabled", false);
				$("#num" + ids).attr("disabled", false);
				$("#kucun" + ids).attr("disabled", false);
				$("#oldprice" + ids).attr("disabled", false);
				$("#types" + ids).css("display", "none");
				test.css("display", "")
				$("#message" + ids).attr("disabled", false);
			} else if (update == "保存") {
				console.log("id="+id)
				$("#update" + ids).html("修改");
				$("#name" + ids).attr("disabled", true);
				$("#price" + ids).attr("disabled", true);
				$("#kucun" + ids).attr("disabled", true);
				$("#oldprice" + ids).attr("disabled", true);
				$("#price" + ids).attr("disabled", true);
				$("#types" + ids).css("display", "");
				test.css("display", "none")
				$("#message" + ids).attr("disabled", true);
				console.log(test.val())
				$.ajax({
					method : "post",
					url : "updateMenu",
					data : {
						id : ids,
						name : name,
						price : (price * 1).toFixed(2),
						num:num,
						kucun:kucun,
						oldprice:oldprice,
						types : test.val(),
						message : message
					},
					dataType : 'json',
					success : function(data) {
						selectByFenye(sid, pag);
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
		/* function pass(id) {
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
		} */
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