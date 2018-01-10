package com.qmd.util;

import com.opensymphony.xwork2.ActionContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;


public class HttpUtil {
	
	// #####################此部分参数需要修改#######################
	
//	public static String LoginName = "zj_hy"; //用户名称
//	public static String Password = "hy0613"; //密码；例如：123.com
//	public static String SpCode = "210130"; //企业编号
	
	// ###################此部分参数需要修改########################
	
	public static void main(String[] args) throws Exception {
		//测试
		HttpUtil hu=new HttpUtil();
		NoteResult nr=hu.sendSms("您投 资的理财项目已于2016-05-10还款，还款本金为1000元，利息为100元。","13777410262");
		// 输出结果
		System.out.println("————————————————————————————————————————————————————————————————————————————————");
		System.out.println("代码:"+nr.getResult());
		System.out.println("描述:"+nr.getDescription());
	}
	
	public NoteResult sendSms(String content, String phoneNumber) throws Exception {

		URL url = null;
		HttpURLConnection con = null;
		String msg = "";
		NoteResult noteResult=new NoteResult();
		try {
			url = new URL("http://www.ztsms.cn:8800/sendSms.do");
			con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");

			OutputStream out = con.getOutputStream();
			String username = "jcb";
			String password = "5HfzlGVH";
			String paramters = "username="+username+"&password="+password+"&mobile="+phoneNumber+"&content="+content+"&dstime=&productid=727727&xh=";
			
			
			out.write(paramters.getBytes("UTF-8"));
			out.flush();
			out.close();
			
			BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			String inputLine=in.readLine();							
			in.close();
			con.getInputStream().close();
			con.disconnect();
			String[] ret = inputLine.split(",");
			
			Integer count = 0;
			
			if (ret.length > 1) {
				String obj0 = ret[0];
					if (obj0.equals("1")) {// 发送成功
						// 设置短信发送成功 状态
						Double c = (double)(content.length() / 70.00);
						if(c <= 1){
							count = 1;
						}else{
							count = c.intValue() + 1;
						}
					}
				msg = obj0;
			}else{
				msg = "无返回值";
			}
			if(!"1".equals(msg)){
				noteResult.setResult("1");
				noteResult.setDescription("发送短信失败");
			}else{
				noteResult.setResult("0");
				noteResult.setDescription("发送短信成功");
			}
									
			System.out.println("短信返回参数：" + inputLine);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return noteResult;
	}
	
	//创建随机流水号
	public static String serialNumberUtil(){
		SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		Date currTime = new java.util.Date();
		String curTime =formatter.format(currTime);//14位时间数
		
		Random random=new Random() ;
		int n=random.nextInt(999999);//6位随机数
		while(n<100000){
			n=random.nextInt(999999);
		}
		
		curTime=curTime+n;//20位流水号
		return curTime;
	}
	//短信返回结果处理
	public  NoteResult resultUtil(String result){
		NoteResult noteResult=new NoteResult();
		String[] str=result.split("&description=");
		String res=str[0].substring(7);
		String descrip="";
		if("0".equals(res)){
			 descrip="发送短信成功";
		}else{
			 descrip=str[1];
		}
		noteResult.setResult(res);
		noteResult.setDescription(descrip);
		return noteResult;
	}
	/**
	 * 验证手机验证码
	 * 
	 * @return
	 */
	public boolean checkPhoneCode(String sessionName,String code){
		System.out.println("--------------验证手机验证码");
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		String phoneCode_reg=(String) session.get(sessionName);
		if("".equals(phoneCode_reg)||phoneCode_reg==null){
			return false;
		}else{
			if(phoneCode_reg.equals(code)){
				return true;
			}else{
				return false;
			}
		}
	}
	
	
	
	
}
