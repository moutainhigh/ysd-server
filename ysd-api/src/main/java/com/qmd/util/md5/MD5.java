package com.qmd.util.md5;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {
	/**
	 * MD5 加密
	 */
	static Logger logger = Logger.getLogger(MD5.class);
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			//messageDigest = MessageDigest.getInstance("SHA");
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			logger.info("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}
	/**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException  
     */
    public static String EncoderByMd5(String str){
    	String newstr = null;
    	try{
            //确定计算方法
            MessageDigest md5=MessageDigest.getInstance("SHA");
            //MessageDigest md5=MessageDigest.getInstance("MD5");
//            sun.misc.BASE64Encoder base64en = new sun.misc.BASE64Encoder();

            //加密后的字符串
//            newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
            newstr = Base64.encodeBase64String(md5.digest(str.getBytes("utf-8")));
        }catch(Exception e){
    		e.printStackTrace();
    	}
        return newstr;
    }
    
    public static void main(String[] args) {
    	String  aa = getMD5Str("00015b8b27369b94c33eab70359d6481"+"92495826");
    	System.out.println(aa);
    }
    
    
   
}
