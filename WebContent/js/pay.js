

function getyouhui() {
	var name = document.getElementById("name").value;// 姓名
	var tel = document.getElementById("tel").value;// 电话
	var yhtip = document.getElementById("yhtip");// 优惠提示
	var nultip = document.getElementById("nultip");// 没有提示
	var yhdiv = document.getElementById("yhdiv");// 优惠div
	var nuldiv = document.getElementById("nuldiv");// 空div
	var yh = document.getElementById("yh");// 选择优惠
	var get = document.getElementById("activity");// 优惠打折div
	$.ajax({
		type : "POST",
		url : "userinfo/getyouhui",
		dataType : "json",
		data : {
			name : name,
			tel : tel,
		},
		success : function(data) {
			
			if (data.length == 0) {
				alert("没有可用优惠券");
				get.style.display = "none";
				yh.value = "无";
			} else {
				if (data[0].coupons!= '') {
					$("#yhstyle").val(data[0].youhui)
					if(data[0].coupons=='减免'){
					yh.value = data[0].coupons;
					yhdiv.style.display = "block";
					yhtip.innerHTML = "优惠方式：减免"+data[0].youhui+"元";
					}else if(data[0].coupons=='折扣'){
						yh.value = data[0].coupons;
						yhdiv.style.display = "block";
						yhtip.innerHTML = "优惠方式："+data[0].youhui+"折";
					}else{
						yh.value = data[0].coupons;
						yhdiv.style.display = "block";
						yhtip.innerHTML = "优惠方式：无优惠";
					}
				}
				
				get.style.display = "flex";
			}
		}
	})
}















