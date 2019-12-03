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
</style>
<script type="text/javascript">
var id =1
var pschool='全部'
	$(document).ready(function() {
		/* $("select#status").change(function(){
			    // alert($(this).val());
			     fenye();
			     selectActivityWsl()
			 }); */
			 if (getCookie("types") == null) {
					window.location.href = "mdl.jsp"
				} else if (getCookie("types") == "店铺"||getCookie("types") == "品牌商") {
					window.location.href = "mqx.jsp"
				} else {
					selectActivityWsl();
					fenye();
				}
		/* findSchool(); */
	});
	function selectByFenye(id, page) {
		$
				.ajax({
					method : "post",
					url : "findCashList",
					data : {
						id : id,
						openid:""
					},
					dataType : "json",
					success : function(data) {
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
								+ "<td>"
								+ data[i].phone
								+ "</td>"
								+ "<td>"
								+ data[i].types
								+ "</td>"
								+ "<td>"
								+ data[i].zh
								+ "</td>"
								+ "<td>"
								+ data[i].number
								+ "</td>"
								+ "<td>"
								+ data[i].money
								+ "</td>"
								+ "<td>"
								+ (data[i].states==1?'已完成':'待处理')
								+ "</td>"
								+ "<td>"
								+ data[i].dates
								+ "</td>"
								+ "<td><div class='button-group'> <a class='button border-blue' href='javascript:void(0)' onclick='del("
								+ data[i].id
								+ ",\""+data[i].phone+ "\",\""+data[i].number+"\")'>"
								+ "<span class='icon-edit'></span> <span id='del"+data[i].id+"'>"+(data[i].states==1?"已完成":"确认完成")+"</span></a> </div>"

								+"</td></tr>";
						$("#selectActivity").append(str);
						}
						pages(id, page);
					}
				});
	}
	function fenye() {
		$.ajax({
			method : "post",
			url : "countCash",
			data : {
				openid:""
			},
			dataType : "json",
			success : function(data) {
				pages(1, data.page);
			}
		});
	}
	function selectActivityWsl() {
		$
				.ajax({
					method : "post",
					url : "findCashList",
					data : {
						id : 1,
						openid:""
					},
					dataType : "json",
					success : function(data) {
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
								+ "<td>"
								+ data[i].phone
								+ "</td>"
								+ "<td>"
								+ data[i].types
								+ "</td>"
								+ "<td>"
								+ data[i].zh
								+ "</td>"
								+ "<td>"
								+ data[i].number
								+ "</td>"
								+ "<td>"
								+ data[i].money
								+ "</td>"
								+ "<td>"
								+ (data[i].states==1?'已完成':'待处理')
								+ "</td>"
								+ "<td>"
								+ data[i].dates
								+ "</td>"
								+ "<td><div class='button-group'> <a class='button border-blue' href='javascript:void(0)' onclick='del("
								+ data[i].id
								+ ",\""+data[i].phone+ "\",\""+data[i].number+"\")'>"
								+ "<span class='icon-edit'></span> <span id='del"+data[i].id+"'>"+(data[i].states==1?"已完成":"确认完成")+"</span></a> </div>"

								+"</td></tr>";
						$("#selectActivity").append(str);
						}

					}
				})
	}
	function pages(id,page) {
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
		
	}
	function tops(id,page){
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
	}
	/* function look(id){
		console.log(id)
		window.location.href="orderDetail.jsp?id="+id
	} */
</script>
<script src="js/pintuer.js"></script>
</head>
<body>
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
					<th>姓名</th>
					<th>手机</th>
					<th>提现方式</th>
					<th>提现账号</th>
					<th>店铺编码</th>
					<th>提现金额</th>
					<th>状态</th>
					<th>申请时间</th>
					<th>操作</th>
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
		 */
		//单个删除
		function del(id) {
			 if($("#del"+id).html()=="已完成"){
				 
			 }else{
				 if (confirm("您确定完成吗?")) {
						$.ajax({
							method : "post",
							url : "updateCashState",
							data : {
								id : id,
								states:1
							},
							dataType : 'json',
							success : function(data) {
								selectActivityWsl();
							}
						})
					}
			 }
			
		}
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