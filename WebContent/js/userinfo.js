/**
 * userinfo主页面
 */

function importExcel() {// 上传
	var file = document.getElementById("uploadFile").value;
	if (file == null || file == "") {
		alert("请选择文件");
		return false;
	} else {
		var filetype = file.substring(file.lastIndexOf(".")).toLowerCase();
		if (filetype == ".xlsx"||filetype == ".xls") {
			return true;
		} else {
			alert("选择Excel文件")
			return false;
		}
	}
}

function edit(id) {// 编辑
	window.location.href = "userinfo/edit?idd="+id;
}

function drop(id, th) {// 删除
	if (confirm("确定删除这条信息?")) {
		window.location.href = 'userinfo/drop?id=' + id + '&th=' + th;
	}
	return false;
}

function jump(th) {// 分页
	window.location.href = "userinfo/main?th=" + th;
}

function search() {// 安条件搜索用户
	var phone = document.getElementById("phone").value;
	var username = document.getElementById("username").value;
	if (phone == '' && username == '') {
		window.location.href = "userinfo/main";
	} else {
		window.location.href = "userinfo/search?phone=" + phone + "&username=" + encodeURIComponent(encodeURIComponent(username));
	}
}

function checkall(th) {// 全选
	var cbox = document.getElementsByName("cname");
	for (var i = 0; i < cbox.length; i++) {
		if (cbox[i].checked == true) {
			cbox[i].checked = false;
		} else {
			cbox[i].checked = true;
		}
	}
}

function dropall(th) {// 批量删除
	var arr = new Array();
	var cbox = document.getElementsByName("cname");
	for (var i = 0; i < cbox.length; i++) {
		if (cbox[i].checked) {
			arr.push(cbox[i].value);
		}
	}
	if (arr == '') {
		alert("请先选择用户");
		return;
	} else {
		if (confirm("确定删除选中信息?")) {
			window.location.href = 'userinfo/dropall?arr=' + arr + '&th=' + th;
		}
		return false;
	}
}

function first(th) {// 首页
	window.location.href = "userinfo/main";
}

function before(th) {// 上一页
	if (th < 1) {
		th = 1;
	}
	window.location.href = "userinfo/main?th=" + th;
}

function next(th, pages) {// 下一页
	if (th >= pages) {
		th = pages;
	}
	window.location.href = "userinfo/main?th=" + th;
}

function end(pages) {// 末页
	window.location.href = "userinfo/main?th=" + pages;
}
