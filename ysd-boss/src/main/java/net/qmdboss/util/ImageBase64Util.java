package net.qmdboss.util;

/*import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;*/

import org.apache.commons.codec.binary.Base64;

@SuppressWarnings("restriction")
public class ImageBase64Util {

	/**
	 * 解密
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String decode(String str) throws Exception {
		
		String ret = new String(decryptBASE64(str),"UTF8");
		
		return ret;
	}

	/**
	 * 加密
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String encode(String str) throws Exception {
		return encryptBASE64(str.getBytes("UTF8"));
	}

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
//		return (new BASE64Decoder()).decodeBuffer(key);
        return Base64.decodeBase64(key);
    }

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
//		return (new BASE64Encoder()).encodeBuffer(key);
        return Base64.encodeBase64String(key);
    }

	public static void main(String[] args) throws Exception {
		String data = ImageBase64Util.encryptBASE64("/data/upfiles/images/201306/61a508fb3e4142378aaa859e15932af5.jpg"
				.getBytes());
		System.out.println("加密前：" + data);
		System.out.println("加密前：" + encode(data));
		

		byte[] byteArray = ImageBase64Util.decryptBASE64(data);
		System.out.println("解密后：" + new String(byteArray));
		
		System.out.println("解密后：" + decode(data));
		
	}

}