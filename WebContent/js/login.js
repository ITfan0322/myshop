/**
 * login.js
 */

function login() {
	var id = document.getElementById("id").value;
	var pw = document.getElementById("password").value;
	if (id == "" || pw == "") {
		document.getElementById("div").innerHTML = "账号或密码不能为空!";
		return false;
	} else {
		return true;
	}
}