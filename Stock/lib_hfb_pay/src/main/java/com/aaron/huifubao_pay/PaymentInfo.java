package com.aaron.huifubao_pay;

/**
 * 交易实体
 *
 * @author hy
 *
 */
public class PaymentInfo {
	// 支付初始化后返回的一个支付码 初始化才返回
	private String tokenID;
	// 商家生成的订单号 初始化才回返回
	private String billNo;
	private String agentId;
	// 返回是否有误
	private boolean hasError;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	// 返回给前端的错误信息
	private String message;
	// 订单信息，查询接口才回返回
	private String billInfo;

	public String getBillInfo() {
		return billInfo;
	}

	public void setBillInfo(String billInfo) {
		this.billInfo = billInfo;
		this.billNo = this.getBillInfoItem("agent_bill_id");
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	private String getBillInfoItem(String name) {
		if (billInfo == null)
			return null;

		final String pattern = name + "=";
		int pos = billInfo.indexOf(pattern);
		if (pos < 0) {
			return null;
		}

		int pos2 = billInfo.indexOf('|', pos);
		String val = "";
		if (pos2 >= 0) {
			val = billInfo.substring(pos + pattern.length(), pos2);
		} else {
			val = billInfo.substring(pos + pattern.length());
		}

		return val;
	}

	public int getPayType() {
		String val = getBillInfoItem("pay_type");
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			return -1;
		}
	}

	public int getPayResult() {
		String val = getBillInfoItem("result");
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			return -1;
		}
	}

	public String getPayAmount() {
		String val = getBillInfoItem("pay_amt");
		return val;
	}

	public String getJunnetBillNo() {
		String val = getBillInfoItem("jnet_bill_no");
		return val;
	}

	public String getPayTypeName() {
		int payType = getPayType();
		switch (payType) {
			case 2:
				return "余额支付";
			case 10:
				return "骏卡支付";
			case 13:
				return "神州行";
			case 14:
				return "联通卡";
			case 15:
				return "电信卡";
			case 17:
				return "银行无卡支付";
			case 18:
				return "银行快捷支付";
			case 19:
				return "银行WAP网银支付";
			case 29:
				return "信用卡无卡支付";
			case 30:
				return "微信支付";
			case 35:
				return "盛大卡支付";
			case 40:
				return "第三方卡支付";
			case 41:
				return "盛大一卡通";
			case 42:
				return "网易一卡通";
			case 43:
				return "征途一卡通";
			case 44:
				return "完美一卡通";
			case 45:
				return "金山一卡通";
			case 46:
				return "搜狐一卡通";
			case 47:
				return "久游一卡通";
			case 48:
				return "乐都一卡通";
			case 49:
				return "盛科一卡通";
			case 50:
				return "中广一卡通";
			case 51:
				return "麒麟一卡通";
			case 52:
				return "梦工厂网络";
			case 53:
				return "网龙91币卡";
			case 54:
				return "极光一卡通";
			case 55:
				return "纵游一卡通";
			case 56:
				return "多多卡（原奥比建设卡）";
			case 57:
				return "QQ币卡";
			default:
				return "【未知支付方式】";
		}
	}

	public String getPayResultName() {
		int status = getPayResult();
		return getPayResultName(status);
	}

	public static String getPayResultName(int status) {
		String resultText = "";
		switch (status) {
			case 0:
				resultText = "处理中...";
				break;
			case -1:
				resultText = "支付失败";
				break;
			case 1:
				resultText = "支付成功";
				break;
			case -2:
				resultText = "支付未完成";
				break;
		}
		return resultText;
	}
}
