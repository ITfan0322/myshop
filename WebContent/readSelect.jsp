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
	var id = 1
	var pag = 1
	var names = ''
	$(document).ready(function() {
		console.log("登录信息",getCookie("types")+"-"+getCookie("number"))
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
				var price = 0;
				for(var i = 0;i<data.length;i++){
					price = price+(data[i].price * 1)
				}
				$("#allprice").html("总收入："+price.toFixed(2))
			}})
		/* $("select#status").change(function(){
			    // alert($(this).val());
			     fenye();
			     selectActivityWsl()
			 }); */

		setInterval(function() {
			selectByFenye(id, pag)
			console.log("刷新")
		}, 5000)
		if (getCookie("types") == null) {
			window.location.href = "mdl.jsp"
		} else if (getCookie("types") == "品牌商") {
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
					url : "fenyeOrder",
					data : {
						id : id,
						name : names,
						sid : getCookie("types") == "管理员" ? ""
								: getCookie("number")
					},
					dataType : "json",
					success : function(data) {
						$("#selectActivity").empty();
						var pstate = ''
						for (var i = 0; i < data.length; i++) {
							
							if (data[i].state == 1) {
								pstate = '未接单'
							} else if (data[i].state == 2) {
								pstate = '配送中'
							} else if (data[i].state == 3) {
								pstate = '已完成'
							} else if (data[i].state == 4) {
								pstate = '用户申请取消'
							} else if (data[i].state == 5) {
								pstate = '订单已取消'
							}
							var str = "<tr id='sela'>"
									+ "<td style='text-align:left; padding-left:20px;'>"
									+ "<input type='checkbox' name='id[]' value='' />"
									+ (i + 1)
									+ "</td>"
									+ "<td>"
									+ data[i].ordernum
									+ "</td>"
									+ "<td>"
									+ data[i].name
									+ "</td>"
									+ "<td>"
									+ data[i].phone
									+ "</td>"
									+ "<td>"
									+ data[i].address
									+ "</td>"
									+ " <td>"
									+ (data[i].price * 1).toFixed(2)
									+ "</td><td>"
									+ pstate
									+ "</td><td>"
									+ data[i].beizhu
									+ "</td><td>"
									+ data[i].ziqu
									+ "</td><td>"
									+ data[i].dates
									+ "</td>"
									+ "<td>"
									+ "<div class='button-group'> <a class='button border-main' href='javascript:void(0)' onclick='look("
									+ data[i].id
									+ ")'>"
									+ "<span class='icon-edit'></span> <span id='look"+data[i].id+"'>查看详情</span></a>  </div>"
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
			url : "countOrder",
			data : {
				name : names,
				sid : getCookie("types") == "管理员" ? "" : getCookie("number")
			},
			dataType : "json",
			success : function(data) {
				pages(1, data.page);
				pag = data.page
			}
		});
	}
	function selectActivityWsl() {
		$
				.ajax({
					method : "post",
					url : "fenyeOrder",
					data : {
						id : 1,
						name : names,
						sid : getCookie("types") == "管理员" ? ""
								: getCookie("number")
					},
					dataType : "json",
					success : function(data) {
						console.log(data)
						$("#selectActivity").empty();
						var pstate = ''
						var allprice = 0
						for (var i = 0; i < data.length; i++) {
							
							if (data[i].state == 1) {
								pstate = '未接单'
							} else if (data[i].state == 2) {
								pstate = '配送中'
							} else if (data[i].state == 3) {
								pstate = '已完成'
							} else if (data[i].state == 4) {
								pstate = '用户申请取消'
							} else if (data[i].state == 5) {
								pstate = '订单已取消'
							}
							var str = "<tr id='sela'>"
									+ "<td style='text-align:left; padding-left:20px;'>"
									+ "<input type='checkbox' name='id[]' value='' />"
									+ (i + 1)
									+ "</td>"
									+ "<td>"
									+ data[i].ordernum
									+ "</td>"
									+ "<td>"
									+ data[i].name
									+ "</td>"
									+ "<td>"
									+ data[i].phone
									+ "</td>"
									+ "<td>"
									+ data[i].address
									+ "</td>"
									+ " <td>"
									+ (data[i].price * 1).toFixed(2)
									+ "</td><td>"
									+ pstate
									+ "</td><td>"
									+ data[i].beizhu
									+ "</td><td>"
									+ data[i].ziqu
									+ "</td><td>"
									+ data[i].dates
									+ "</td>"
									+ "<td>"
									+ "<div class='button-group'> <a class='button border-main' href='javascript:void(0)' onclick='look("
									+ data[i].id
									+ ")'>"
									+ "<span class='icon-edit'></span> <span id='zhuanfa"+data[i].id+"'>查看详情</span></a></div>"
									+ "</td></tr>";
							$("#selectActivity").append(str);
						}
						
						
					}
				})
	}
	function pages(id, page) {
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
		selectByFenye(id, page)
		pages(id, page)
	}
	function nexts(id, page) {
		id++;
		selectByFenye(id, page)
		pages(id, page)
	}
	function tou(id, page) {
		id = 1;
		selectByFenye(id, page)
		pages(id, page)
	}
	function wei(id, page) {
		id = page;
		selectByFenye(id, page)
		pages(id, page)
	}
	function look(id) {
		console.log(id)
		window.location.href = "menu2.jsp?id=" + id
	}
	function go() {
		var va = $(".gopage").val()
		selectByFenye(va, pag)
	}
	var zid = 0
	function zhuanfa(id) {
		console.log(id)
		zid = id
		$(".zhuanfa").css("display", "flex")
	}
	function zfc() {
		$(".zhuanfa").hide()
	}
	function zfs() {
		var shop = $("#zf").val()
		$.ajax({
			url : "updateShops",
			data : {
				id : zid,
				shops : shop
			},
			success : function(r) {
				console.log(r)
				$(".zhuanfa").hide()
			}
		})

	}
</script>
<script src="js/pintuer.js"></script>
<style type="text/css">
#sela td input {
	text-align: center;
}

.zhuanfa {
	display: none;
	align-items: center;
	justify-content: center;
	width: 100%;
	height: 100%;
	position: fixed;
	top: 0;
	left: 0;
	background-color: rgba(0, 0, 0, 0.5);
	z-index: 999
}

.zf {
	padding: 20px;
	background-color: #fff;
	width: 300px;
	height: 300px;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: space-between;
}

.zf div {
	text-align: center;
}

.zf select {
	width: 100%;
}
</style>
</head>
<body style="width: 1800px;">
	<div class="zhuanfa">
		<div class="zf">
			<select class='button border-main' id="zf">
			</select>
			<div style="display: flex; flex-direction: row; width: 100%;">
				<div style="width: 50%;" class='button border-main' onclick="zfc()">取消</div>
				<div style="width: 50%;" onclick="zfs()" class='button border-main'>确定</div>
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
					<li><span class="button border-main" id="allprice"></span></li>
				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>
					<th width="100" style="text-align: left; padding-left: 20px;"><input
						type="checkbox" id="checkall" />编号</th>
					<th>订单号</th>
					<th>姓名</th>
					<th>手机</th>
					<th>地址</th>
					<th>价格</th>
					<th>订单状态</th>
					<th>备注</th>
					<th>配送方式</th>
					<th>下单时间</th>
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
			names = name
			selectActivityWsl()
			fenye()
		}
		//单个删除
		function del(id) {
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