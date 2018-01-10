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
	
	public static String LoginName = "zj_hy"; //用户名称
	public static String Password = "hy0613"; //密码；例如：123.com
	public static String SpCode = "210130"; //企业编号
	
	// ###################此部分参数需要修改########################
	
	public static void main(String[] args) throws Exception {
		//测试
		HttpUtil hu=new HttpUtil();
		NoteResult nr=hu.sendSms("您的验证码为555555","13777410262");
		//NoteResult nr=hu.sendSms("尊敬的阿宝您于2014年1月1号投的标不动产标已满管理员已于2014年1月1号审核成功现正在还款请注意查看","18668082221");
		//NoteResult nr=hu.sendSms("尊敬的{郑先森您}好,用户名为zhengl的个人实名认证成功,请注意查看,","18668082221");
		// 输出结果
		System.out.println("————————————————————————————————————————————————————————————————————————————————");
		System.out.println("代码:"+nr.getResult());
		System.out.println("描述:"+nr.getDescription());
	}
	
	/**
	 * 
	 * 短信发送方法
	 * 参数content：短信内容，按照模版编辑
	 * 参数phoneNumber：手机号码，多个用  ,号隔开
	 * 返回NoteResult:封装返回的代码和结果描述
	 *
	 **/
	/**
	public NoteResult sendSms(String content, String phoneNumber) throws Exception {
		//发送内容
				 
				// 创建StringBuffer对象用来操作字符串
				StringBuffer sb = new StringBuffer("http://zx.ums86.com:8899/sms/Api/Send.do?");
				 
				// 向StringBuffer追加用户名
				sb.append("LoginName="+LoginName);
				 
				// 向StringBuffer追加密码
				sb.append("&Password="+Password);
				
				//向StringBuffer追加企业编号
				sb.append("&SpCode="+SpCode);
				
				// 向StringBuffer追加手机号码
				sb.append("&UserNumber="+phoneNumber);
				 
				// 向StringBuffer追加消息内容转URL标准码  短信内容必须按照模版设置 ，如：您的验证码为123456
				if(!"".equals(content)&&content!=null){
					System.out.println("************短信内容***************");
					System.out.println(content);
					sb.append("&MessageContent="+URLEncoder.encode(content, "GB2312"));
				}
				
				// 向StringBuffer追加流水号
					String SerialNumber=HttpUtil.serialNumberUtil();
				sb.append("&SerialNumber="+SerialNumber);
				
				// 向StringBuffer追加发送时间:立刻发送 20090901010101
				sb.append("&ScheduleTime=20090901010101");
				
				// 向StringBuffer追加提交时检测方式
				sb.append("&f=1");
				
				 
				// 创建url对象
				URL url = new URL(sb.toString());
				System.out.println("************短信发送url是***************");
				System.out.println(url);
				 
				// 打开url连接
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				 
				// 设置url请求方式 ‘get’ 或者 ‘post’
				connection.setRequestMethod("POST");
				 
				// 发送
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GB2312"));
				 
				// 返回发送结果
				String inputline = in.readLine();

				 System.out.println("************短信返回结果是**************");
				// 输出结果
				System.out.println(inputline);
				
				//结果封装
				HttpUtil hu=new HttpUtil();
				NoteResult noteResult=hu.resultUtil(inputline);
				return noteResult;
	}**/

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
			String paramters = "username="+username+"&password="+password+"&mobile="+phoneNumber+"&content="+content+"&dstime=&productid=712712&xh=";
			
			
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
	
	/**
	public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
	**/

	public static String sendGet(String urllink){

		URL url = null;
		HttpURLConnection con = null;
		try {
			url = new URL(urllink);
			con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");

			OutputStream out = con.getOutputStream();
			out.flush();
			out.close();
			
			BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			String inputLine=in.readLine();							
			in.close();
			con.getInputStream().close();
			con.disconnect();	
			System.out.println("返回参数：" + inputLine);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 // 使用finally块来关闭输入流
        finally {
            try {
                if (con.getInputStream() != null) {
                	con.getInputStream().close();
                	con.disconnect();	
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
		return "";
	}
	
}
