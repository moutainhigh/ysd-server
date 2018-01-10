package com.hzzj.lianghao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EpaySignJsonBodyDemo {
	
	static HashMap<String,String> jsonBody = new HashMap<String,String>();
	static{
		
		//3774644d643043776952564d64555a4f64555a4d644a50565a426b73446d5658
		//430105196310252516
		//绑定银行卡  TAIRAN_UNPY_BIND1
		String jsonBody011 ="{\"accId\": \"120101197602122583\",\"accName\": \"吕2\", \"cardNo\": \"4367420061470738987\",\"exCode\": \"HZJCB_UNPY_BIND1\",\"merId\": \"1000000503\",\"orderNo\": \"201601540970\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\"}";
		//绑定银行卡  TAIRAN_UNPY_BIND2
		String jsonBody012 ="{\"accId\": \"420621198707300058\",\"accName\": \"徐少锋\", \"cardNo\": \"6214855715975195\",\"cvn\": \"\",\"exCode\": \"HZJCB_UNPY_BIND2\",\"expire\": \"\",\"merId\": \"1000000503\",\"mobile\": \"13777410262\",\"orderNo\": \"201604290030\",\"tranDateTime\": \"20160429\",\"timestamp\": \"20160429\"}";	
		//绑定银行卡  TAIRAN_UNPY_BIND3
		String jsonBody013 ="{\"accId\": \"320926197812037239\",\"accName\": \"唐锋\", \"cardNo\": \"6212261202023412720\",\"cvn\": \"123\",\"exCode\": \"HZJCB_UNPY_BIND3\",\"expire\": \"1729\",\"merId\": \"1000000503\",\"mobile\": \"18914695716\",\"orderNo\": \"201601190002\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\",\"code\": \"567458\"}";
		//绑定银行卡  HZJCB_UNPY_BIND4
		String jsonBody014 ="{\"accId\": \"320926197812037239\",\"accName\": \"唐锋\", \"cardNo\": \"6212261202023412720\",\"exCode\": \"HZJCB_UNPY_BIND4\",\"amount\": \"4000\",\"merId\": \"1000000503\",\"orderNo\": \"201601190002\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\"}";
		//绑定银行卡  TAIRAN_UNPY_BIND5
		String jsonBody015 ="{\"accName\": \"唐锋\", \"cardNo\": \"6212261202023412720\",\"exCode\": \"HZJCB_UNPY_BIND5\",\"merId\": \"1000000503\",\"orderNo\": \"201601190002\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\"}";
		//绑定银行卡  TAIRAN_UNPY_BIND6
		String jsonBody016 ="{\"accName\": \"唐锋\", \"cardNo\": \"6212261202023412720\",\"cvn\": \"123\",\"exCode\": \"HZJCB_UNPY_BIND6\",\"expire\": \"1729\",\"merId\": \"1000000503\",\"mobile\": \"18914695716\",\"orderNo\": \"201601190002\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\"}";
		//绑定银行卡  TAIRAN_UNPY_BIND7
		String jsonBody017 ="{\"accName\": \"唐锋\", \"cardNo\": \"6212261202023412720\",\"cvn\": \"123\",\"exCode\": \"HZJCB_UNPY_BIND7\",\"expire\": \"1729\",\"merId\": \"1000000503\",\"mobile\": \"18914695716\",\"orderNo\": \"201601190002\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\",\"code\": \"098765\"}";

		//解绑银行卡  HZJCB_UNPY_UNBIND
		String jsonBody02 ="{\"bindId\": \"3774644d64306e32314d564d6455567464555a4d646d40632372396351686251\", \"exCode\": \"HZJCB_UNPY_UNBIND\",\"merId\": \"1000000503\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\"}";
		//查询绑定信息(根据卡号)  HZJCB_UNPY_QUERY_BIND
		String jsonBody021 ="{\"bindId\":\"3774644d64306e32314d564d6455567464555a4d646d40632372396351686251\",\"exCode\":\"HZJCB_UNPY_QUERY_BIND\",\"merId\":\"1000000503\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\"}";
		//查询绑定信息(根据卡号)  HZJCB_UNPY_QUERY_CBIND
		String jsonBody03 ="{\"cardNo\":\"123456789\",\"exCode\":\"HZJCB_UNPY_QUERY_CBIND\",\"merId\":\"1000000503\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\"}";
		
		//扣款  TAIRAN_UNPY_SUB_BIND 
		String jsonBody04 ="{\"amount\":\"1\",\"bindId\":\"3774644d64306e32314d564d6455567464555a4d646d40632372396351686251\",\"exCode\":\"HZJCB_UNPY_SUB_BIND\",\"merId\":\"1000000504\",\"orderNo\":\"E2016017800001\",\"remarks\":\"\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\"}";
		//付款 TAIRAN_UNPY_ADD_BIND 
		String jsonBody041 ="{\"amount\":\"10\",\"bindId\":\"3774644d64306e32314d564d6455567464555a4d646d40632372396351686251\",\"exCode\":\"HZJCB_UNPY_ADD_BIND\",\"merId\":\"1000000504\",\"orderNo\":\"E2016011900001\",\"payType\":\"1\",\"remarks\":\"\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\"}";
		//对公付款  TAIRAN_UNPY_SUB_COMP
		String jsonBody042 ="{\"amount\":\"\",\"exCode\":\"HZJCB_UNPY_SUB_COMP\",\"merId\":\"1000000503\",\"orderNo\":\"\",\"accName\": \"唐锋\",\"cardNo\": \"6212261202023412720\",\"bankName\": \"工商银行\", \"bankCode\": \"6225095810641053\",\"province\": \"浙江省\", \"city\": \"杭州市\",\"remark\": \"对公付款 \", \"payType\": \"1\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\"}";
		//对公付款  TAIRAN_UNPY_ADD_FORCE
		String jsonBody043 ="{\"amount\":\"\",\"exCode\":\"HZJCB_UNPY_ADD_FORCE\",\"merId\":\"1000000503\",\"orderNo\":\"\",\"accName\": \"唐锋\",\"cardNo\": \"6212261202023412720\",\"remark\": \"付款 \", \"payType\": \"1\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\",\"mobile\": \"18914695716\"}";
		 //订单查询 TAIRAN_UNPY_QUERY_CHKFILE
		String jsonBody07 ="{\"exCode\":\"HZJCB_UNPY_QUERY_CHKFILE\",\"fileType\":\"2\",\"merId\":\"1000000503\",\"tranDate\":\"20160119\",\"tranType\":\"1\"}";
		//交易撤销（退货）接口  TAIRAN_UNPY_REVOKE
		//String jsonBody05 ="{\"amount\":\"\",\"exCode\":\"HZJCB_UNPY_REVOKE\",\"merId\":\"1000000503\",\"orderNo\":\"\",\"queryId\":\"\",\"revokeType\":\"1\"}";
		//发送验证码  TAIRAN_UNPY_SMSCODE
		String jsonBody051 ="{\"mobile\":\"13777410262\",\"exCode\":\"HZJCB_UNPY_SMSCODE\",\"merId\":\"1000000503\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\"}";	
		//随机付款  TAIRAN_UNPY_SUB_RANDOM
		String jsonBody052 ="{\"exCode\":\"HZJCB_UNPY_SUB_RANDOM\",\"merId\":\"1000000503\", \"cardNo\": \"6212261202023412720\",\"orderNo\":\"E2016017800007\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\",\"accName\": \"唐锋\",\"accId\": \"320926197812037239\"}";	
		//订单查询 TAIRAN_UNPY_QUERY_ORDER
		String jsonBody06 ="{\"exCode\":\"HZJCB_UNPY_QUERY_ORDER\",\"merId\":\"1000000503\",\"orderNo\":\"515165161\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\"}";	
		//获取银行卡信息 TAIRAN_UNPY_QUERY_BANKCARD
		String jsonBody08 ="{\"cardNo\":\"120101197602122023\",\"exCode\":\"HZJCB_UNPY_QUERY_BANKCARD\",\"merId\":\"1000000503\",\"timestamp\": \"20160119\"}";
		//公安身份实名认证 TAIRAN_UNPY_SFZCONFIRM
		String jsonBody09 ="{\"accId\":\"120101197602122453\",\"accName\":\"吕2咏\",\"exCode\":\"HZJCB_UNPY_SFZCONFIRM\",\"merId\":\"1000000503\",\"orderNo\":\"201601540979\",\"tranDateTime\": \"20160119\",\"timestamp\": \"20160119\"}";
		
		jsonBody.put("TAIRAN_UNPY_BIND1",jsonBody011);
		jsonBody.put("TAIRAN_UNPY_BIND2",jsonBody012);
		jsonBody.put("TAIRAN_UNPY_BIND3",jsonBody013);
		jsonBody.put("TAIRAN_UNPY_BIND4",jsonBody014);
		jsonBody.put("TAIRAN_UNPY_BIND5",jsonBody015);
		jsonBody.put("TAIRAN_UNPY_BIND6",jsonBody016);
		jsonBody.put("TAIRAN_UNPY_BIND7",jsonBody017);
		jsonBody.put("TAIRAN_UNPY_UNBIND",jsonBody02);
		jsonBody.put("TAIRAN_UNPY_QUERY_BIND",jsonBody021);
		jsonBody.put("TAIRAN_UNPY_QUERY_CBIND",jsonBody03);
		jsonBody.put("TAIRAN_UNPY_SUB_BIND",jsonBody04);
		jsonBody.put("TAIRAN_UNPY_ADD_BIND",jsonBody041);
		jsonBody.put("TAIRAN_UNPY_SUB_COMP",jsonBody042);
		jsonBody.put("TAIRAN_UNPY_ADD_FORCE",jsonBody043);
		jsonBody.put("TAIRAN_UNPY_SMSCODE",jsonBody051);
		jsonBody.put("TAIRAN_UNPY_SUB_RANDOM",jsonBody052);
		jsonBody.put("TAIRAN_UNPY_QUERY_ORDER",jsonBody06);
		jsonBody.put("TAIRAN_UNPY_QUERY_CHKFILE",jsonBody07);
		jsonBody.put("TAIRAN_UNPY_QUERY_BANKCARD",jsonBody08);
		jsonBody.put("TAIRAN_UNPY_SFZCONFIRM",jsonBody09);	
	}
	

	public static void main(String[] argv) {
//		String jsonBody012 ="{\"accId\": \"420621198707300058\",\"accName\": \"徐少锋\", \"cardNo\": \"6214855715975195\",\"cvn\": \"\",\"exCode\": \"HZJCB_UNPY_BIND2\",\"expire\": \"\",\"merId\": \"1000000503\",\"mobile\": \"13777410262\",\"orderNo\": \"201604290031\",\"tranDateTime\": \"20160429\",\"timestamp\": \"20160429\"}";	
		
//		UnionPayUtils.unionToPay(jsonBody012);
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_BIND1"); // TAIRAN_UNPY_BIND1   - 绑定银行卡    OK
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_BIND2"); // TAIRAN_UNPY_BIND2   - 绑定银行卡	【预留手机号码验证受限,请稍候再试 - OK】	
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_BIND3"); // TAIRAN_UNPY_BIND3   - 绑定银行卡    x	 (测试数据有误而已,程序本身正确)
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_BIND4"); // TAIRAN_UNPY_BIND4   - 绑定银行卡    x  (测试数据有误而已,程序本身正确)
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_BIND5"); // TAIRAN_UNPY_BIND5   - 绑定银行卡    OK (测试数据有误而已,程序本身正确)
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_BIND6"); // TAIRAN_UNPY_BIND6   - 绑定银行卡    X  (测试数据有误而已,程序本身正确)
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_BIND7"); // TAIRAN_UNPY_BIND7   - 绑定银行卡    X  (测试数据有误而已,程序本身正确)	
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_UNBIND"); // TAIRAN_UNPY_UNBIND - 解绑银行卡    OK
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_QUERY_BIND"); // TAIRAN_UNPY_UNBIND - 查询绑定信息     -OK
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_ADD_BIND"); // TAIRAN_UNPY_ADD_BIND - 付款     -OK	
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_SUB_BIND"); // TAIRAN_UNPY_SUB_BIND - 扣款    -OK	
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_SUB_COMP"); // TAIRAN_UNPY_SUB_COMP
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_ADD_FORCE"); // TAIRAN_UNPY_ADD_FORCE	
		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_SMSCODE"); // TAIRAN_UNPY_SMSCODE   - 发送验证码    -OK	
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_SUB_RANDOM"); // TAIRAN_UNPY_SUB_RANDOM - 随机付款    -OK		
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_QUERY_ORDER"); // TAIRAN_UNPY_QUERY_ORDER
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_QUERY_CHKFILE"); // TAIRAN_UNPY_CHECK - 对账文件查询   -OK
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_QUERY_BANKCARD"); // TAIRAN_UNPY_QUERY_BANKCARD -获取银行卡信息  -OK
//		EpaySignJsonBodyDemo.httpClientTestWithSign("TAIRAN_UNPY_SFZCONFIRM"); // TAIRAN_UNPY_SFZCONFIRM - 公安身份实名认证   -OK
	}
	
	public static void httpClientTestWithSign(String traceCode) {
		try {
			URL localURL = new URL("http://183.131.23.150:8080/ddes/signjson");
			HttpURLConnection httpConn = (HttpURLConnection) localURL.openConnection();
			String jsonData = jsonBody.get(traceCode);
			String message = CodeUtil.encryptBASE64(jsonData.getBytes("utf-8"));
			System.out.println("request json message :\n" + jsonData);
			String path ="D:/java/Workspaces/jcb_www/src/main/webapp/WEB-INF/conf/cert/hzjcb/rsa_priv_hzjcb_client_test_key_java.pem";
			String privateKeyCert = CodeUtil.readLoacalKeyFile(path);
			byte[] keyBytes = CodeUtil.decryptBASE64(privateKeyCert);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
			Signature signatureInstance = Signature.getInstance("MD5withRSA");
			signatureInstance.initSign(privateKey);
			signatureInstance.update(message.getBytes());
			byte[] signByte = signatureInstance.sign();
			String signature =  CodeUtil.encryptBASE64(signByte);

			List<NameValuePair> paramArray = new ArrayList<NameValuePair>();
			NameValuePair nameValeuMessage = new NameValuePair("message", message);
			NameValuePair nameValeuSignature = new NameValuePair("signature", signature);
			paramArray.add(nameValeuMessage);
			paramArray.add(nameValeuSignature);
			httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST");
			httpConn.setConnectTimeout(50000);
			httpConn.setReadTimeout(50000);
			OutputStream outputStream = httpConn.getOutputStream();
			String clientJsonData = (new HttpData(paramArray, "utf-8")).getData();
			outputStream.write(clientJsonData.getBytes("utf-8"));
			outputStream.flush();
			outputStream.close();
		
			InputStream inputStream = httpConn.getInputStream();
			byte[] arrayOfByte = read(inputStream, 2048);
			inputStream.close();
			String resMsg = new String(arrayOfByte, "utf-8");
//			String resMsg = "message=eyJleENvZGUiOiJUQUlSQU5fVU5QWV9RVUVSWV9PUkRFUiIsIm1lcklkIjoiMTAwMDAwMDUwMSIs%0D%0AIm9yZGVyTm8iOiI1MTUxNjUxNjEiLCJ0cmFuRGF0ZVRpbWUiOiAiMjAxNjAxMTkiLCJ0aW1lc3Rh%0D%0AbXAiOiAiMjAxNjAxMTkifQ%3D%3D%0D%0A&signature=SncNg%2By2fMWw8vwB4%2BG8prQFvYYnAamlWTdpVJHRfrhMVSZfMLBNsYNC%2FZojcvbZ8hvQHqLi0hS1%0D%0ADtULiqQVosYrU%2Bcf9XwDwBTc9vvT2AbH4pYthEiVzvSvJlC35u%2BcDMGFlfDKdaiAV%2F7ncCaCOkyR%0D%0Ac5BhxCOz28EDGrKw1zs%3D%0D%0A";
			
			System.out.println("Receive server response json Message :\n [" + resMsg + "]");
			Map<String, Object> resJsonMap = new HashMap<String,Object>();
			String[] resParamArray = resMsg.split("&");
			for (String value : resParamArray) {
				String[] keyValuePair = value.split("=");// ArrayIndexOutOfBoundsException ?
				resJsonMap.put(keyValuePair[0], keyValuePair[1]);
			}
			String resMessage = (String) resJsonMap.get("message");
			resMessage = new String(CodeUtil.decryptBASE64(URLDecoder.decode(resMessage)),"utf-8");
			System.out.println("response body : " + resMessage);
			String signatureC = (String) resJsonMap.get("signature");
			signatureC = URLDecoder.decode(signatureC);
			String publicKeyPath ="D:/java/Workspaces/jcb_www/src/main/webapp/WEB-INF/conf/cert/hzjcb/rsa_pub_hzjcb_server_test_key.pem";
			String publicKey = CodeUtil.readLoacalKeyFile(publicKeyPath);
			byte[] publicKeyByte = CodeUtil.decryptBASE64(publicKey);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyByte);
			KeyFactory verKeyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = verKeyFactory.generatePublic(keySpec);
			if (pubKey != null) {
				Signature verSignature = Signature.getInstance("MD5withRSA");
				verSignature.initVerify(pubKey);
				verSignature.update((URLDecoder.decode((String) resJsonMap.get("message"))).getBytes());
				boolean verti =  verSignature.verify(CodeUtil.decryptBASE64(signatureC));
				if(verti){
					System.out.println("Verify signature success");
					System.out.println("Response Message:\n" + resMessage);
				}else{
					System.out.println("Verify signature error!");
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static byte[] read(InputStream inputStream, int length) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] arrayOfByte = new byte[length];
		int pos = inputStream.read(arrayOfByte);
		while (pos != -1) {
			outputStream.write(arrayOfByte, 0, pos);
			pos = inputStream.read(arrayOfByte);
		}
		outputStream.flush();
		return outputStream.toByteArray();
	}


	
}
