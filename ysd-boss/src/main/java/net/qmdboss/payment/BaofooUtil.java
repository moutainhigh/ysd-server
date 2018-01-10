package net.qmdboss.payment;

import net.qmdboss.bean.QueryDetailsBaofoo;
import net.qmdboss.entity.RechargeConfig;
import net.qmdboss.entity.UserAccountRecharge;
import net.qmdboss.util.CommonUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class BaofooUtil {
public static Map<String,String> resMap = new HashMap<String,String>();
	
	static{
		resMap.put("Y", "成功");
		resMap.put("F", "失败");
		resMap.put("P", "处理中 ");
		resMap.put("N", "没有订单");
	}
	public QueryDetailsBaofoo getExamineVerify(RechargeConfig rechargeConfig,UserAccountRecharge userAccountRecharge){
		StringBuffer sb = new StringBuffer();
		String MID=rechargeConfig.getBargainorId();//商户号
		String Md5key = rechargeConfig.getBargainorKey(); //md5密钥（KEY）
		String TID="";//商户订单号
		
		if(userAccountRecharge!=null){
			TID = userAccountRecharge.getTradeNo();
		}

		sb.append("MerchantID="+MID);
		sb.append("&TransID="+TID);
		String Md5Sign = MID+TID+Md5key;
		System.out.println("Md5Sign加密前"+Md5Sign);
		Md5Sign = GopayUtils.md5(Md5Sign);
		System.out.println("Md5Sign加密后:"+Md5Sign);
		sb.append("&Md5Sign="+Md5Sign);
		String str="";
		try {
			str = CommonUtil.getHtml(CommonUtil.BF_WEB+"/Check/OrderQuery.aspx?"+sb.toString(), "tudou");
			System.out.println("宝付接口返回结果:"+str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		QueryDetailsBaofoo baofoo = new QueryDetailsBaofoo();
		if(!"".equals(str)){
			String[] s;
			StringTokenizer st = new StringTokenizer(str,"\\|");
			s=new String[st.countTokens()];
			for(int i=0;st.hasMoreTokens();i++)
			{
				s[i]=st.nextToken();
			}
			String MerchantID = s[0]; //商户号
			String TransID = s[1]; //商户流水号
			String CheckResult = s[2]; //支付结果
			String factMoney = s[3];//实际成功金额,分为单位
			
			BigDecimal f_money = new BigDecimal(factMoney).divide(new BigDecimal(100));//成功金额，使用元单位
			String SuccTime = s[4];//支付完成时间
			String WaitSign = s[5];//MD5签名
			String MD5 = new String(MerchantID+TransID+CheckResult+factMoney+SuccTime+Md5key);//MD5签名格式
			String MD5Sign = GopayUtils.md5(MD5);//计算MD5值
		
		
		if(WaitSign.compareTo(MD5Sign)==0){	
			baofoo.setMerchantID(MerchantID);
			baofoo.setTransId(TransID);
			baofoo.setCheckResult(CheckResult);
			baofoo.setFactMoney(f_money);
			baofoo.setSuccTime(SuccTime);
			baofoo.setMd5Sign(Md5Sign);
		}
		}
		return baofoo;
	}
	
	public String resCode(String s){
		String str = resMap.get(s);
		if(str == null){
			str = "数据异常";
		}
		return str;
	}
}
