package net.qmdboss.payment;

import net.qmdboss.bean.QueryDetailsGfb;
import net.qmdboss.entity.RechargeConfig;
import net.qmdboss.entity.UserAccountRecharge;
import net.qmdboss.util.CommonUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 国付宝
 * @author Xsf
 *
 */
public class GopayUtils {
	/**
     * 网关地址 
     */
	public static String  gopay_gateway = CommonUtil.GFB_WEB + "/PGServer/Trans/WebClientAction.do";

	public static Map<String,String> resMap = new HashMap<String,String>();
	public static Map<String,String> orgTxnStatMap = new HashMap<String,String>();	
	/**
     * 字符编码格式，目前支持 GBK 或 UTF-8
     */
	public static String input_charset = "UTF-8";
	
	static{
		resMap.put("0000", "交易成功");
		
		resMap.put("GT01", "系统内部错误");
		resMap.put("GT03", "通讯失败");
		resMap.put("GT11", "虚拟账户不存在");
		resMap.put("GT12", "用户没有注册国付宝账户");
		resMap.put("GT14", "该笔记录不存在");
		resMap.put("GT16", "缺少字段");
		
		resMap.put("MR01", "验证签名失败");
		resMap.put("MR02", "报文某字段格式错误");
		resMap.put("MR03", "报文某字段超出允许范围");
		resMap.put("MR04", "报文某必填字段为空");
		resMap.put("MR05", "交易类型不存在");
		resMap.put("MR06", "证书验证失败或商户校验信息验证失败");
		resMap.put("MR99", "商户指定的返回通知编码错误");
		
		resMap.put("MN01", "超过今日消费上限");
		resMap.put("MN02", "超过单笔消费上限");
		resMap.put("MN03", "超过今日提现上限");
		resMap.put("MN04", "超过单笔提现上限");
		resMap.put("MN05", "累计退货金额大于原交易金额 ");
		resMap.put("MN07", "余额不足");
		resMap.put("MN08", "交易金额大于预授权金额");
		resMap.put("MN09", "返券金额不足");
		resMap.put("MN10", "交易返券金额大于预授权返券金额  ");
		resMap.put("MN11", "商户上送的单笔订单交易金额超限 ");
		resMap.put("MN12", "返券金额大于交易金额 ");
		
		resMap.put("SM01", "商户不存在 ");
		resMap.put("SM02", "商户状态非正常");
		resMap.put("SM03", "商户不允许支付");
		resMap.put("SM04", "商户不具有该权限");
		
		resMap.put("SC06", "卡已注销");
		resMap.put("SC09", "卡不存在");
		resMap.put("SC10", "支付密码错误");
		resMap.put("SC11", "卡状态非正常");
		resMap.put("SC13", "国付宝号不正确");
		resMap.put("SC14", "卡不处于有效期内");
		resMap.put("SC16", "未绑定卡不允许做返券交易");

		resMap.put("ST01", "原订单不存在");
		resMap.put("ST02", "预授权号不符");
		resMap.put("ST03", "原交易类型不符");
		resMap.put("ST04", "原交易状态非成功或已发生过退货");
		resMap.put("ST07", "支付类交易代码匹配失败");
		resMap.put("ST08", "已完成（取消）的交易不能再完成（取消）");
		resMap.put("ST10", "交易重复，订单号已存在");
		resMap.put("ST11", "返券交易不允许退货");
		resMap.put("ST12", "不允许（商户||企业）给个人转账");
		resMap.put("ST13", "不允许自己给自己支付或转帐");
		resMap.put("ST28", "该资源已被锁定，不能重复进行锁定保证金交易");
		
		resMap.put("SU05", "不存在该用户");
		resMap.put("SU10", "用户状态非正常");
		resMap.put("SU12", "Email和卡号不匹配");
		
		orgTxnStatMap.put("0000", "订单成功");
		orgTxnStatMap.put("4444", "订单失败（银行交易状态失败）");
		orgTxnStatMap.put("5555", "订单处理中（支付平台处理中）");
		orgTxnStatMap.put("6666", "订单失败（企业复核不通过）");
		orgTxnStatMap.put("7777", "订单处理中（企业财务未复核）");
		orgTxnStatMap.put("8888", "订单处理中（银行交易状态未知）");
		orgTxnStatMap.put("9999", "订单处理中");
		orgTxnStatMap.put("其它", "订单失败");
	}
	

	public QueryDetailsGfb getExamineVerify(RechargeConfig rechargeConfig,UserAccountRecharge userAccountRecharge,HttpServletRequest request){
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddHHmmss");
		String tranCode = "4020";//交易代码
		String tranDateTime = dateformat.format(new Date());//交易时间
		String merOrderNum = CommonUtil.PR + CommonUtil.getRandomString(15);//订单号 必
		String merchantID = rechargeConfig.getPaymentProductId(); //商户代码
		String orgOrderNum = userAccountRecharge.getTradeNo();//原订单号
		String orgtranDateTime = dateformat.format(userAccountRecharge.getCreateDate()); //原交易时间 必
		String orgTxnType = "";//原交易类型
		String orgtranAmt = userAccountRecharge.getMoney().toString();//原交易金额
		String orgTxnStat = "";//原交易状态
		String authID="";//预授权号
		String isLocked="";//是否锁定
		String respCode = "";//响应码
		String tranIP=request.getLocalAddr();//用户浏览器IP
		String msgExt="";//附加信息
		String merchantEncode = "";//商户指定响应编码
		String VerficationCode =  rechargeConfig.getBargainorKey();//识别码
		
		// 组织加密明文
		String plain = "tranCode=[" + tranCode + "]merchantID=["
				+ merchantID + "]merOrderNum=[" + merOrderNum
				+ "]tranAmt=[]ticketAmt=[]tranDateTime=["
				+ tranDateTime + "]currencyType=[]merURL=[]customerEMail=[]authID=["+ authID +"]orgOrderNum=[" + orgOrderNum
				+ "]orgtranDateTime=[" + orgtranDateTime
				+ "]orgtranAmt=[" +orgtranAmt + "]orgTxnType=["+ orgTxnType +"]orgTxnStat=["+ orgTxnStat +"]msgExt=["
				+ msgExt + "]virCardNo=[]virCardNoIn=[]tranIP=[" + tranIP + "]isLocked=["+ 
				 isLocked + "]feeAmt=[]respCode=["+ respCode +"]VerficationCode=[" + VerficationCode + "]";
		//参数
		String parameters = "tranCode=" + tranCode + "&merchantID="
				+ merchantID + "&merOrderNum=" + merOrderNum
				+ "&tranAmt=&ticketAmt=&tranDateTime="
				+ tranDateTime + "&currencyType=&merURL=&customerEMail=&authID="+ authID +"&orgOrderNum=" + orgOrderNum
				+ "&orgtranDateTime=" + orgtranDateTime
				+ "&orgtranAmt=" +orgtranAmt + "&orgTxnType="+ orgTxnType +"&orgTxnStat="+ orgTxnStat +"&msgExt="
				+ msgExt + "&virCardNo=&virCardNoIn=&tranIP=" + tranIP + "&isLocked="+ 
				 isLocked + "&feeAmt=&respCode="+ respCode +"&VerficationCode=" + VerficationCode;
		
		String signValue = md5(plain.toString());
		plain = parameters +"&merchantEncode=" + merchantEncode + "&signValue="+signValue;
		String str="";
		QueryDetailsGfb qd = new QueryDetailsGfb();
		try {
			str = CommonUtil.getHtml(gopay_gateway+"?"+plain.toString(), "utf-8");
			System.out.println("str=="+str);
//			str = xmlElements(str,"orgTxnStat");
			qd.setOrgTxnStat(xmlElements(str,"orgTxnStat"));
			qd.setOrgtranAmt(xmlElements(str,"orgtranAmt"));
			qd.setOrgTxnType(xmlElements(str,"orgTxnType"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return qd;
	}
	
	//校验respcode
	public String errorCode(String str){
		String respcode= resMap.get(str);
		if(respcode== null){
			respcode = "交易失败，通知技术人员";
		}
		return respcode;
	}
	
	//校验respcode
	public String errorOrgTxnStat(String str){
		String orgTxnStat= orgTxnStatMap.get(str);
		if(orgTxnStat== null){
			orgTxnStat = "校验失败，请在官网查询原始记录";
		}
		return orgTxnStat;
	}
	
	  /**
     * 对字符串进行MD5签名
     * 
     * @param text
     *            明文
     * 
     * @return 密文
     */
    public static String md5(String text) {
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    
    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
    
    public static String xmlElements(String xmlDoc,String namespace) {
    	String retcode="";//0 1代表成功，9代表撤消
        //创建一个新的字符串
        StringReader read = new StringReader(xmlDoc);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            //获得XML中的命名空间（XML中未定义可不写）
            Namespace ns = root.getNamespace();
            retcode= root.getChild(namespace,ns).getText();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retcode;
    }
    
}
