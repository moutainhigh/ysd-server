package com.qmd.util;

import java.io.UnsupportedEncodingException;

public class Demo_Client {
	


	public static void main(String[] args) throws UnsupportedEncodingException
	{

		
		Client client=new Client("18668082221","Hello world,++-- ，【上网贷】");
		
		client.mdsmssend();
		
//		System.out.println(">>>>"+client.getMD5("admin"));
//		System.out.println(">>>>"+client.getMD5("QQQ-WWW-EEE-TTTYY"+"admin").length());
		//获取信息
//		String result = client.mdgetSninfo();
//		System.out.print(result);
		//短信发送
//      String   content   =   java.net.URLEncoder.encode("接口测试[您报备的签名]",  "utf-8");  
//		String result_mt = client.mdsmssend("137*****115", content, "", "", "", "");		
	
//		System.out.print(result_mt);
		//个性短信发送
//		String result_gxmt = client.mdgxsend("138*****000", "测试", "", "", "", "");
//		System.out.print(result_gxmt);

	}
}
