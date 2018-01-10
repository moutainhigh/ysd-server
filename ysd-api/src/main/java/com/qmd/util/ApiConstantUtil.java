package com.qmd.util;


public class ApiConstantUtil {

	public static final String M0010 = "此标不存在！";

	public static final String M0011 = "此标未发布！";
	
	
	public static final String M0001 = "您的用户名或密码错误!";
	public static final String M0002 = "您的账号已被禁用,无法登录，如有疑问请联系客服人员!";
	public static final String M0003 = "您的账号已被锁定，如有疑问请联系客服人员!";
	private static final String M0004_1 = "若连续";
	private static final String M0004_2 ="次密码输入错误,您的账号将被锁定!";
	public static String getM0004(Integer i) {
		return M0004_1+i+M0004_2;
	}
	
	/**
	 * 查看项目投资记录提示消息
	 */
	public static final String M0005 = "投资记录不存在!";
	public static final String M0006 = "投资记录为空!";
	
	/**
	 * 投资提示消息
	 */
	public static final String M0007_0 ="账户余额不足!";
	public static final String M0007_1 ="请输入投资金额!";
	public static final String M0007_2 ="投资金额必须为数字!";
	public static final String M0007_3 ="投资金额必须为100的整数倍!";
	public static final String M0007_4 ="投资金额至少大于";
	public static final String M0007_4_1="元!";
	public static String getM0007_4(String money){
		return M0007_4 + money + M0007_4_1;
	}
	

	public static final String M0007_5 ="投资金额不能超过";
	public static String getM0007_5(String money){
		return M0007_5 + money + M0007_4_1;
	}
	public static final String M0007_6 = "项目已满!";
	public static final String M0007_7 = "续投余额不足!";
	public static final String M0007_8 = "项目的状态不正确!";
	public static final String M0007_9 = "投标未成功!";
	public static final String M0007_10 = "请输入定向密码";
	public static final String M0007_11 = "定向密码输入错误";
	
	
	public static final String M0008_0 = "短信验证码不能为空！";
	public static final String M0008_1 = "短信验证码不正确！";
	public static final String M0008_2 = "用户已存在！";
	public static final String M0008_3 = "密码必须包含字母！";
	public static final String M0008_4 = "密码长度必须在8-16个字符之间！";
	public static final String M0008_5 = "系统无法获取手机号！";
	public static final String M0008_6 = "该手机号获取验证码太频繁，请5分钟后再尝试！";
	public static final String M0008_7 = "手机号已经注册过帐号！";
	public static final String M0008_8 = "手机接口商返回的错误！";
	public static final String M0008_9 = "原邮箱输入有误！";
	public static final String M0008_10 = "同一账户只能添加一张提现银行卡！";
	public static final String M0008_11 = "用户未实名认证！";
	public static final String M0008_12= "请获取短信验证码！";
	public static final String M0008_13= "请输入手机号码！";
	public static final String M0008_14= "用户不存在";
	public static final String M0008_15= "客户端类型不能为空";
	public static final String M0008_16= "用户名不能为空";
	public static final String M0008_17= "密码不能为空";
	public static final String M0008_18= "真实姓名不能为空";
	public static final String M0008_19= "身份证号不能为空";
	public static final String M0008_20= "身份证号不正确";
	
	
	public static final String M0008 = "实名已通过!";
	public static final String M0009 = "实名已申请!";
	public static final String M0009_1 = "此身份证已认证!";
	public static final String M0009_2 = "姓名和身份证不匹配!";
	

	public static final String M00010 = "请绑定银行卡";
	/**
	 * 提现操作消息提示
	 */
	public static final String M00015_0 = "提现金额必须大于100元";
	public static final String M00015_1 = "申请提现金额必须为整数";
	public static final String M00015_2 = "申请提现金额大于实际可提现额度";
	public static final String M00015_3 = "请设置提现账户";
	
	public static final String M00015_4 = "请输入正确的提现金额";
	public static final String M00015_5 = "您账户所有金额为24小时内充值金额，不能提现";
	public static final String M00015_6 = "您账户没有可提现金额";
	public static final String M00015_7 = "提现金额不能高于";
	public static final String M00015_7_2 = "提现金额不能小于";
	public static final String M00015_7_1 = "元";
	public static final String M00016 ="上传文件不能为空";
	public static final String M00017 ="金额必须大于零";
	
	
	
	
	
	public static String getM00015_7(String money){
		return M00015_7 + money + M00015_7_1;
	}
	public static String getM00015_7_2(String money){
		return M00015_7_2 + money + M00015_7_1;
	}
	public static final String M00015_8 = "您申请提现的金额中包含受15天内充值资金提现规则限制,超出部分将收取(超出额 乘以";
	public static final String M00015_8_1 = ")的提现手续费,还请注意!";
	public static String getM00015_8(String money){
		return M00015_8 + money + M00015_8_1;
	}
	
	public static final String S0404 = "接口不存在";
	public static final String S0001 = "程序内部错误";
	public static final String S0002 = "交易密码输入错误!";
	public static final String S0003 = "登录密码输入错误!";
	public static final String S0004 = "请设置交易密码!";
	
	public static final String E0001 = "请登录！";
	public static final String E0002 = "参数错误";
	

	

	
	

}
