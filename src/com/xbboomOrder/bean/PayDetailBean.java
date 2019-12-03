package com.xbboomOrder.bean;

public class PayDetailBean {
	//交易时间
    private String trade_time;
    // 公众账号ID
    private String appid;
    //商户号
    private String mch_id;
    //特约商户号
    private String mch_appid;
    // 设备号
    private String device_info;
    //微信订单号
    private String transaction_id;
    // 商户订单号
    private String out_trade_no;
    // 用户标识
    private String openid;
    // 交易类型
    private String trade_type;
    //交易状态
    private String trade_status;
    //付款银行
    private String pay_bank;
    //货币种类
    private String money_type;
    //应结订单金额
    private String order_pay;
    //代金券金额
    private String voucher_amount;
    //微信退款单号
    private String refund_number;
    //商户退款单号
    private String out_refund_no;
    //退款金额
    private String refund_amount;
    //充值券退款金额
    private String refund_amount_voucher;
    //退款类型
    private String refunds_type;
    //退款状态
    private String refunds_status;
    //商品名称
    private String commodity_name;
    //商户数据包
    private String data_packet;
    //手续费
    private String service_charge;
    //费率
    private String rate;
    //订单金额
    private String order_amount;
    //申请退款金额
    private String application_refund_amount;
    //费率备注
    private String rate_notes;
	public String getTrade_time() {
		return trade_time;
	}
	public void setTrade_time(String trade_time) {
		this.trade_time = trade_time;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getMch_appid() {
		return mch_appid;
	}
	public void setMch_appid(String mch_appid) {
		this.mch_appid = mch_appid;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	public String getPay_bank() {
		return pay_bank;
	}
	public void setPay_bank(String pay_bank) {
		this.pay_bank = pay_bank;
	}
	public String getMoney_type() {
		return money_type;
	}
	public void setMoney_type(String money_type) {
		this.money_type = money_type;
	}
	public String getOrder_pay() {
		return order_pay;
	}
	public void setOrder_pay(String order_pay) {
		this.order_pay = order_pay;
	}
	public String getVoucher_amount() {
		return voucher_amount;
	}
	public void setVoucher_amount(String voucher_amount) {
		this.voucher_amount = voucher_amount;
	}
	public String getRefund_number() {
		return refund_number;
	}
	public void setRefund_number(String refund_number) {
		this.refund_number = refund_number;
	}
	public String getOut_refund_no() {
		return out_refund_no;
	}
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}
	public String getRefund_amount() {
		return refund_amount;
	}
	public void setRefund_amount(String refund_amount) {
		this.refund_amount = refund_amount;
	}
	public String getRefund_amount_voucher() {
		return refund_amount_voucher;
	}
	public void setRefund_amount_voucher(String refund_amount_voucher) {
		this.refund_amount_voucher = refund_amount_voucher;
	}
	public String getRefunds_type() {
		return refunds_type;
	}
	public void setRefunds_type(String refunds_type) {
		this.refunds_type = refunds_type;
	}
	public String getRefunds_status() {
		return refunds_status;
	}
	public void setRefunds_status(String refunds_status) {
		this.refunds_status = refunds_status;
	}
	public String getCommodity_name() {
		return commodity_name;
	}
	public void setCommodity_name(String commodity_name) {
		this.commodity_name = commodity_name;
	}
	public String getData_packet() {
		return data_packet;
	}
	public void setData_packet(String data_packet) {
		this.data_packet = data_packet;
	}
	public String getService_charge() {
		return service_charge;
	}
	public void setService_charge(String service_charge) {
		this.service_charge = service_charge;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}
	public String getApplication_refund_amount() {
		return application_refund_amount;
	}
	public void setApplication_refund_amount(String application_refund_amount) {
		this.application_refund_amount = application_refund_amount;
	}
	public String getRate_notes() {
		return rate_notes;
	}
	public void setRate_notes(String rate_notes) {
		this.rate_notes = rate_notes;
	}
	@Override
	public String toString() {
		return "PayDetailBean [trade_time=" + trade_time + ", appid=" + appid + ", mch_id=" + mch_id + ", mch_appid="
				+ mch_appid + ", device_info=" + device_info + ", transaction_id=" + transaction_id + ", out_trade_no="
				+ out_trade_no + ", openid=" + openid + ", trade_type=" + trade_type + ", trade_status=" + trade_status
				+ ", pay_bank=" + pay_bank + ", money_type=" + money_type + ", order_pay=" + order_pay
				+ ", voucher_amount=" + voucher_amount + ", refund_number=" + refund_number + ", out_refund_no="
				+ out_refund_no + ", refund_amount=" + refund_amount + ", refund_amount_voucher="
				+ refund_amount_voucher + ", refunds_type=" + refunds_type + ", refunds_status=" + refunds_status
				+ ", commodity_name=" + commodity_name + ", data_packet=" + data_packet + ", service_charge="
				+ service_charge + ", rate=" + rate + ", order_amount=" + order_amount + ", application_refund_amount="
				+ application_refund_amount + ", rate_notes=" + rate_notes + "]";
	}
    
	
	
}
