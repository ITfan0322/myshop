$(document)
		.ready(
				function() {
					$
							.ajax({
								type : "post",
								url : "../movieController/findAll",
								data : {},
								dataType : "json",
								success : function(data) {

									var list = eval(data);
									// var msg = "";

									for (var i = 0; i < list.length; i++) {

										var tr = "<tr id='tr_"
												+ list[i].mno
												+ "'><td>"
												+ list[i].mname
												+ "</td><td>"
												+ "<img src='.."
												+ list[i].mimg
												+ "' width='80' height='100'/>"
												+ "</td><td>"
												+ list[i].mdirector
												+ "</td><td>"
												+ list[i].mactor
												+ "</td><td><div title='"
												+ list[i].minfo
												+ "' style='text-overflow:ellipsis; overflow:hidden; white-space:nowrap;width: 20em;'>"
												+ list[i].minfo
												+ "</div></td><td>"
												+ list[i].mtime
												+ "</td><td>"
												+ list[i].mprice
												+ "</td><td><div class='button-group'> <a class='button border-main' href='javascript:void(0)' onclick='updateMovie("
												+ list[i].mno
												+ ")'><span class='icon-edit'></span> 修改</a><a class='button border-main' href='javascript:void(0)' onclick='updateSigns("
												+ list[i].mno
												+ ")'><span class='icon-edit'></span> 上架</a> <a class='button border-main' href='javascript:void(0)' onclick='uploadForeshow("
												+ list[i].mno
												+ ")'><span class='icon-edit'></span> 宣传</a> <a class='button border-main' href='javascript:void(0)' onclick='upGroup("
												+ list[i].mno
												+ ")'><span class='icon-edit'></span> 剧组</a></div></td></tr>";
										$("#tbo").append(tr);
									}
								}
							})
					$.ajax({
						type : "post",
						url : "../mclassController/getMclass",
						data : {},
						dataType : "json",
						success : function(data) {

							var clist = eval(data);
							// var msg = "";

							for (var i = 0; i < clist.length; i++) {
								var op = "<option value='" + clist[i].classno
										+ "'>" + clist[i].mclass + "</option>"
								$("#classno").append(op);
							}
						}
					})
				})
// 检查是否是图片
function checkPic() {
	var picPath = document.getElementById("idFile").value;
	var type = picPath.substring(picPath.lastIndexOf(".") + 1, picPath.length)
			.toLowerCase();
	if (type != "jpg" && type != "bmp" && type != "gif" && type != "png") {
		alert("请上传正确的图片格式！");
		return false;
	}
	return true;
}
// 实现预览同时控制提交按钮的可用性
function setImagePreview(obj, localImagId, imgObjPreview) {
	//document.querySelector("#submit").setAttribute("disabled", "disabled");
	document.querySelector("#preview").setAttribute("src", "../img/none.png");
	if (!checkPic()) {
		""
		alert("格式!!!")
		return false;
	}
	var fileContentType = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; // 这个文件类型正则很有用
	// 循环判断图片的格式是否正确
	if (obj.files && obj.files[0]) {
		// 火狐下，直接设img属性
		imgObjPreview.style.display = 'block';
		imgObjPreview.style.width = '180px';
		imgObjPreview.style.height = '258px';
		// 火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
		imgObjPreview.src = window.URL.createObjectURL(obj.files[0]);
	} else { // IE下，使用滤镜
		obj.select();
		var imgSrc = document.selection.createRange().text;
		// 必须设置初始大小
		localImagId.style.width = "180px";
		localImagId.style.height = "258px";
		// 图片异常的捕捉，防止用户修改后缀来伪造图片
		localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
		localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		imgObjPreview.style.display = 'none';
		document.selection.empty();
	}
	//document.querySelector("#submit").removeAttribute("disabled");
	return true;
}
/**
 * 动态查询未上映的电影
 */
function selectBy() {
	var mtimes = $('#mtimes').val();
	var mnames = $("#mnames").val();
	var mactors = $("#mactors").val();
	$.ajax({
				type : "post",
				url : "../movieController/selectBy",
				data : {
					mtime : mtimes,
					mname : mnames,
					mactor : mactors,
				},
				dataType : "json",
				success : function(data) {

					var list = eval(data);
					// var msg = "";
					$("#tbo").html("");
					for (var i = 0; i < list.length; i++) {
						var tr = "<tr id='tr_"
								+ list[i].mno
								+ "'><td>"
								+ list[i].mname
								+ "</td><td>"
								+ "<img src='.."
								+ list[i].mimg
								+ "' width='80' height='100'/>"
								+ "</td><td>"
								+ list[i].mdirector
								+ "</td><td>"
								+ list[i].mactor
								+ "</td><td><div title='"
								+ list[i].minfo
								+ "' style='text-overflow:ellipsis; overflow:hidden; white-space:nowrap;width: 20em;'>"
								+ list[i].minfo
								+ "</div></td><td>"
								+ list[i].mtime
								+ "</td><td>"
								+ list[i].mprice
								+ "</td><td><div class='button-group'> <a class='button border-main' href='javascript:void(0)' onclick='updateMovie("
								+ list[i].mno
								+ ")'><span class='icon-edit'></span> 修改</a>  <a class='button border-main' href='javascript:void(0)' onclick='updateSigns("
								+ list[i].mno
								+ ")'><span class='icon-edit'></span> 上架</a>  <a class='button border-main' href='javascript:void(0)' onclick='uploadForeshow("
								+ list[i].mno
								+ ")'><span class='icon-edit'></span> 宣传</a><a class='button border-main' href='javascript:void(0)' onclick='upGroup("
												+ list[i].mno
												+ ")'><span class='icon-edit'></span> 剧组</a> </div></td></tr>";
						$("#tbo").append(tr);
					}
				}
			})
}
function updateMovie(mno) {
	window.location = "modifyMovie.jsp?mno=" + mno + "";
}
/**
 * 
 * @param mno上架电影
 */
function updateSigns(mno) {
	/*alert(0);
	openAddressShow(mno);*/
	$.ajax({
		type : "post",
		url : "../movieController/updateSign",
		data : {
			mno : mno
		},
		dataType : "json",
		success : function(data) {
			var res = eval(data);
			if (res > 0) {
				$("#tr_" + mno).remove();
			}

		}
	})
}
// add by wangshuliang 20170527 弹出窗口设置上映的时间地址
function uploadForeshow(mno) {
	window.location = "uploadForeshow.jsp?mno=" + mno + "";
}
function upGroup(mno) {
	window.location="Mgroup.jsp?mno="+mno+"";
}