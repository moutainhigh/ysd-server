package com.qmd.payment;

import com.google.gson.Gson;
import com.hzzj.lianghao.CodeUtil;
import com.hzzj.lianghao.EpaySignJsonBodyDemo;
import com.hzzj.lianghao.HttpData;
import com.hzzj.lianghao.NameValuePair;
import com.qmd.util.bean.UnionPayInfo;

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

public class UnionPayUtils {

	public static UnionPayInfo unionToPay(String jsonBody){
		String resMessage = "";
		try {
			URL localURL = new URL("http://183.131.23.150:8080/ddes/signjson");
			HttpURLConnection httpConn = (HttpURLConnection) localURL.openConnection();
			String jsonData = jsonBody;
			String message = CodeUtil.encryptBASE64(jsonData.getBytes("utf-8"));
			System.out.println("request json message :\n" + jsonData);
//本地地址
//			String path ="D:/java/Workspaces/jcb_www/src/main/webapp/WEB-INF/conf/cert/hzjcb/rsa_priv_hzjcb_client_test_key_java.pem";
//240测试地址			
//			String path ="/data/www/wjac/www_tomcat/webapps/ROOT/WEB-INF/conf/cert/hzjcb/rsa_priv_hzjcb_client_test_key_java.pem";
//真实上线地址			
			String path ="/data/www/jcb_api/WEB-INF/conf/cert/hzjcb/rsa_priv_hzjcb_client_test_key_java.pem";
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
			byte[] arrayOfByte = EpaySignJsonBodyDemo.read(inputStream, 2048);
			inputStream.close();
			String resMsg = new String(arrayOfByte, "utf-8");

			System.out.println("Receive server response json Message :\n [" + resMsg + "]");
			Map<String, Object> resJsonMap = new HashMap<String,Object>();
			String[] resParamArray = resMsg.split("&");
			for (String value : resParamArray) {
				String[] keyValuePair = value.split("=");// ArrayIndexOutOfBoundsException ?
				resJsonMap.put(keyValuePair[0], keyValuePair[1]);
			}
			resMessage = (String) resJsonMap.get("message");
			resMessage = new String(CodeUtil.decryptBASE64(URLDecoder.decode(resMessage)),"utf-8");
			System.out.println("response body : " + resMessage);
			String signatureC = (String) resJsonMap.get("signature");
			signatureC = URLDecoder.decode(signatureC);
//			String publicKeyPath ="D:/java/Workspaces/jcb_www/src/main/webapp/WEB-INF/conf/cert/hzjcb/rsa_pub_hzjcb_server_test_key.pem";
//			String publicKeyPath ="/data/www/wjac/www_tomcat/webapps/ROOT/WEB-INF/conf/cert/hzjcb/rsa_pub_hzjcb_server_test_key.pem";
//真实上线地址			
String publicKeyPath ="/data/www/jcb_api/WEB-INF/conf/cert/hzjcb/rsa_pub_hzjcb_server_test_key.pem";
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
		UnionPayInfo info = new Gson().fromJson(resMessage, UnionPayInfo.class);
		return info;
	}
	
}
