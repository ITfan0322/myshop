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
if (getCookie("types") == null) {
	window.location.href = "mdl.jsp"
} else if (getCookie("types") == "店铺"||getCookie("types") == "品牌商") {
	window.location.href = "mqx.jsp"
} else {
}
var list=[]
var types=""
var times = GetDateStr(-1)
	$(document)
			.ready(
					function() {
						$.ajax({
							method : "post",
							url : "findShop",
							data : {
								
							},
							dataType : "json",
							success : function(data) {
								$("#myshop").html('<option value="">全部</option>')
								for(var i = 0;i<data.length;i++){
									var str = '<option value="'+data[i].name+'">'+data[i].name+'</option>'
									$("#myshop").append(str)
								}
							}
							})
						show("")
						})
	function show(){
	$(".ppp").css("display","flex")
	$.ajax({
		method : "post",
		url : "insertPayDetail",
		data : {
			times : times
		},
		dataType : "json",
		success : function(data) {
			if(data!=0){
			$(".ppp").css("display","none")
			console.log(data);
			$("#selectActivity").html("")
			var list = []
			if(types==""){
				list = data
			}else{
				for(var i=0;i<data.length;i++){
					if(types==data[i].commodity_name){
						list.push(data[i])
					}
				}
			}
			var price = 0
			var feilv = 0
			for (var i = 0; i < list.length; i++) {
				
				price = price+(list[i].order_pay*1)
				feilv = feilv+(list[i].service_charge*1)
				var str = "<tr id=\'sela\'>"
						+ "<td style=\'text-align:left; padding-left:20px;\'>"
						+ "<input type=\'checkbox\' name=\'id[]\' value='' /></td>"
						+ "<td>"
						+ list[i].trade_time
						+ "</td>"
						+ "<td>"
						+ list[i].appid
						+ "</td>"
						+ "<td>"
						+ list[i].mch_id
						+ "</td>"
						+ "<td style='width:40px;'>"
						+ list[i].transaction_id
						+ "</td>"
						+ "<td>"
						+ list[i].out_trade_no
						+ "</td>"
						+ "<td>"
						+ list[i].openid
						+ "</td>"
						+ "<td>"
						+ list[i].trade_status
						+ "</td>"
						+ "<td>"
						+ list[i].order_pay
						+ "</td>"
						/* + "<td>"
						+ data[i].refund_number
						+ "</td>" */
						+ "<td>"
						+ list[i].refund_amount
						+ "</td>"
						+ "<td>"
						+ data[i].refunds_status
						+ "</td>"
						+ "<td>"
						+ list[i].commodity_name
						+ "</td>"
						+ "<td>"
						+ list[i].service_charge
						+ "</td>"
						+ "<td>"
						+ list[i].rate
						+ "</td>"
						+ "</tr>";
				$("#selectActivity").append(str);
			
		}
			$("#allprice").html("总收入额："+price.toFixed(2)+"元")
			$("#allfeilv").html("手续费："+feilv.toFixed(2)+"元")
		}else{
			$(".ppp").css("display","none")
			$("#allprice").html("总收入额："+0+"元")
			$("#allfeilv").html("手续费："+0+"元")
			alert("无订单")
		}
		}
	})
	}
	function myshops(){
		console.log($("#myshop").val())
		types=$("#myshop").val()
		show()
	}
function GetDateStr(AddDayCount) {
	var dd = new Date();
	dd.setDate(dd.getDate() + AddDayCount);//获取AddDayCount天后的日期 
	var y = dd.getFullYear();
	var m = dd.getMonth() + 1;//获取当前月份的日期 
	var d = dd.getDate();
	if(m<10){
		m = "0"+m
	}
	if(d<10){
		d="0"+d
	}
	return y + "-" + m + "-" + d;
}
function changesearch() {
	times = $("#keywords").val();
	show()
	/* $
			.ajax({
				method : "post",
				url : "selectAllmp",
				data : {
					name : name
				},
				dataType : "json",
				success : function(data) {
					$("#sela").remove();
					for (var i = 0; i < data.length; i++) {
						var html = "<tr id=\'sela\'>"
								+ "<td style=\'text-align:left; padding-left:20px;\'>"
								+ "<input type=\'checkbox\' name=\'id[]\' value='' /></td>"
								+ "<td>门票</td>"
								+ "<td>"
								+ data[i].menpiao
								+ "</td>"
								+ "<td>"
								+ data[i].paytime
								+ "</td>"
								+ "<td><div class='button-group' onclick=\"user()\"> <a class='\button border-main\' href='javascript:void(0)'>"
								+ "<span class='icon-edit'></span>使用</a></div></td>"
								+ "</tr>"
						$("#selectActivity").append(str);
					}
				}
			}) */
} 
function addPay(){
	$.ajax({
		method : "post",
		url : "insertPayDetail",
		data : {
			times : GetDateStr(-1)
		},
		dataType : "json",
		success : function(data) {
			alert("昨日共"+(data-1)+"条订单")
			show();
		}
		})
}
function updatePay(){
	$.ajax({
		method : "post",
		url : "updateErrorStatuc",
		data : {
			
		},
		dataType : "json",
		success : function(data) {
			alert("更新"+data+"条差异账单");
			show();
		}
		})
}
</script>
<script src="js/pintuer.js"></script>
</head>
<style>
<!--

-->
.ppp{
width: 100%;
height: 100%;
align-items: center;
justify-content: center;
position: fixed;
top:0;
font-size:20px;
font-weight:600;
display:none;
color:#fff;
left: 0;
background-color: rgba(0,0,0,0.5);
z-index:999;
}
</style>
<body>
<div class="ppp">请求数据中，请稍后...</div>
	<input id="num" type="hidden" value="<%=request.getParameter("num")%>">
	<form method="post" action="" id="listform">
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 内容列表</strong> <a href=""
					style="float: right; display: none;">添加字段</a>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left: 10px;">
					<li>搜索：</li>
					<li><input type="date" placeholder="请输入卡券编码" name="keywords"
						id="keywords" class="input"
						style="width: 250px; line-height: 17px; display: inline-block" />
						<a href="javascript:void(0)"
						class="button border-main icon-search" onclick="changesearch()">
							搜索</a></li>
							<li>
						<a href="javascript:void(0)"
						class="button border-main icon-add" id="allprice">
							</a></li>
							<li>
						<a href="javascript:void(0)"
						class="button border-main icon-add" id="allfeilv">
							</a></li>
							<li>
							<select class="button border-main icon-add" id = "myshop" onchange="myshops()">
								
							</select>
						</li>
				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>
					<th width="100" style="text-align: left; padding-left: 20px;"><input
						type="checkbox" id="checkall" /></th>
					<th>交易时间</th>
					<th>公众账号ID</th>
					<th>商户号</th>
					<th  width="250">微信订单号</th>
					<th>商户订单号</th>
					<th>用户标识</th>
					<th>交易状态</th>
					<th>总金额</th>
					<!-- <th>微信退款单号</th> -->
					<th>退款金额</th>
					<th>退款状态</th>
					<th>商品名称</th>
					<th>手续费</th>
					<th>费率</th>
					
					<!-- <th>交易时间</th>
					<th>公众账号ID</th>
					<th>商户号</th>
					<th>特约商户号</th>
					<th>设备号</th>
					<th>微信订单号</th>
					<th>商户订单号</th>
					<th>用户标识</th>
					<th>交易类型</th>
					<th>交易状态</th>
					<th>付款银行</th>
					<th>货币种类</th>
					<th>订单金额</th>
					<th>代金券金额</th>
					<th>商品名称</th>
					<th>商户数据包</th>
					<th>手续费</th>
					<th>费率</th> -->
					
				</tr>
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
		/* 
		 function del(id){
		 if(confirm("您确定要删除吗?")){
		 $.ajax({
		 method:"post",
		 url:"/company/delRead",
		 data:{id:id},
		 dataType:'json',
		 sunccess:function(data){
		 $("#sela").remove();
		 selectActivityWsl();
		 }
		 })
		 }
		 }

		 //全选
		 $("#checkall").click(function(){ 
		 $("input[name='id[]']").each(function(){
		 if (this.checked) {
		 this.checked = false;
		 }
		 else {
		 this.checked = true;
		 }
		 });
		 })

		 //批量删除
		 function DelSelect(){
		 var Checkbox=false;
		 $("input[name='id[]']").each(function(){
		 if (this.checked==true) {		
		 Checkbox=true;	
		 }
		 });
		 if (Checkbox){
		 var t=confirm("您确认要删除选中的内容吗？");
		 if (t==false) return false;		
		 $("#listform").submit();		
		 }
		 else{
		 alert("请选择您要删除的内容!");
		 return false;
		 }
		 }

		 //批量排序
		 function sorts(){
		 var Checkbox=false;
		 $("input[name='id[]']").each(function(){
		 if (this.checked==true) {		
		 Checkbox=true;	
		 }
		 });
		 if (Checkbox){	
		
		 $("#listform").submit();		
		 }
		 else{
		 alert("请选择要操作的内容!");
		 return false;
		 }
		 }


		 //批量首页显示
		 function changeishome(o){
		 var Checkbox=false;
		 $("input[name='id[]']").each(function(){
		 if (this.checked==true) {		
		 Checkbox=true;	
		 }
		 });
		 if (Checkbox){
		
		 $("#listform").submit();	
		 }
		 else{
		 alert("请选择要操作的内容!");		
		
		 return false;
		 }
		 }

		 //批量推荐
		 function changeisvouch(o){
		 var Checkbox=false;
		 $("input[name='id[]']").each(function(){
		 if (this.checked==true) {		
		 Checkbox=true;	
		 }
		 });
		 if (Checkbox){
		
		
		 $("#listform").submit();	
		 }
		 else{
		 alert("请选择要操作的内容!");	
		
		 return false;
		 }
		 }

		 //批量置顶
		 function changeistop(o){
		 var Checkbox=false;
		 $("input[name='id[]']").each(function(){
		 if (this.checked==true) {		
		 Checkbox=true;	
		 }
		 });
		 if (Checkbox){		
		
		 $("#listform").submit();	
		 }
		 else{
		 alert("请选择要操作的内容!");		
		
		 return false;
		 }
		 }


		 //批量移动
		 function changecate(o){
		 var Checkbox=false;
		 $("input[name='id[]']").each(function(){
		 if (this.checked==true) {		
		 Checkbox=true;	
		 }
		 });
		 if (Checkbox){		
		
		 $("#listform").submit();		
		 }
		 else{
		 alert("请选择要操作的内容!");
		
		 return false;
		 }
		 }

		 //批量复制
		 function changecopy(o){
		 var Checkbox=false;
		 $("input[name='id[]']").each(function(){
		 if (this.checked==true) {		
		 Checkbox=true;	
		 }
		 });
		 if (Checkbox){	
		 var i = 0;
		 $("input[name='id[]']").each(function(){
		 if (this.checked==true) {
		 i++;
		 }		
		 });
		 if(i>1){ 
		 alert("只能选择一条信息!");
		 $(o).find("option:first").prop("selected","selected");
		 }else{
		
		 $("#listform").submit();		
		 }	
		 }
		 else{
		 alert("请选择要复制的内容!");
		 $(o).find("option:first").prop("selected","selected");
		 return false;
		 }
		 }
		 */
	</script>
</body>
</html>