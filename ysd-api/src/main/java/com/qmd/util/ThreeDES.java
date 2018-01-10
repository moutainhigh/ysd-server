package com.qmd.util;

import com.alibaba.fastjson.JSONObject;
import com.qmd.util.md5.MD5;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Security;
import java.util.*;
import java.util.Map.Entry;

//import sun.misc.BASE64Encoder;

public class ThreeDES {
	private static final String Algorithm = "DESede"; // 定义 加密算法,可用
														// DES,DESede,Blowfish

	// keybyte为加密密钥，长度为24字节
	// src为被加密的数据缓冲区（源）
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 转换成十六进制字符串
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	/**
	 * 对url参数按照自己的规则加密,并返回加密后的参数字符串
	 * 
	 * @param params
	 * @return
	 */
	public static String encryptHttpGetParams(Map<String, Object> params,
			String appKey) {

		StringBuffer sb = new StringBuffer();// 结果buffer

		// 排序 按照key的字母升序
		TreeMap<String, Object> map = new TreeMap<String, Object>(
				new Comparator<String>() {

					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}

				});
		map.putAll(params);

		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();

			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());

			if (!key.equals("appId")) {// 判断参数是否是appid,如果不是的话需要进行加密

//				BASE64Encoder enc = new BASE64Encoder();

				try {
					byte[] encoded = encryptMode(appKey.getBytes("utf-8"),
							value.getBytes("utf-8"));

					// value = new String(encoded, "utf-8");
//					value = URLEncoder.encode(enc.encode(encoded), "utf-8");
					value = URLEncoder.encode(org.apache.commons.codec.binary.Base64.encodeBase64String(encoded), "utf-8");

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

			if (sb.length() > 0) {
				sb.append("&");
			}

			sb.append(key).append("=").append(value);
		}

		String sign = StringUtils.lowerCase(MD5.getMD5Str(sb.toString()));
		sb.append("&sign=").append(sign);

		return sb.toString();
	}

	/**
	 * 对url参数按照自己的规则加密,并返回加密后的参数字符串
	 * 
	 * @param params
	 * @return
	 */
	public static JSONObject encryptHttpPostParams(Map<String, Object> params,
			String appKey) {

		StringBuffer sb = new StringBuffer();// 结果buffer
		JSONObject jo = new JSONObject();

		// 排序 按照key的字母升序
		TreeMap<String, Object> map = new TreeMap<String, Object>(
				new Comparator<String>() {

					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}

				});
		map.putAll(params);

		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();

			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());

			try {
				if (!key.equals("appId")) {// 判断参数是否是appid,如果不是的话需要进行加密

//					BASE64Encoder enc = new BASE64Encoder();

					byte[] encoded = encryptMode(appKey.getBytes("utf-8"),
							value.getBytes("utf-8"));

					// value = new String(encoded, "utf-8");
					// value = URLEncoder.encode(enc.encode(encoded), "utf-8");
//					value = enc.encode(encoded);
                    value = Base64.encodeBase64String(encoded);

				}

				jo.put(key, value);

				if (sb.length() > 0) {
					sb.append("&");
				}

				sb.append(key).append("=")
						.append(URLEncoder.encode(value, "utf-8"));// 此处要注意,虽然是post请求,但是在拼接用来计算sign的字符串时,依然还是统一需要urlencode
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		String sign = StringUtils.lowerCase(MD5.getMD5Str(sb.toString()));
		jo.put("sign", sign);

		return jo;
	}
	
	
	/**
	 * md5排序加密
	 * 
	 * @param params
	 * @return
	 */
	public static String getMD5Sign(Map<String, Object> params) {

		StringBuffer sb = new StringBuffer();// 结果buffer

		// 排序 按照key的字母升序
		TreeMap<String, Object> map = new TreeMap<String, Object>(
				new Comparator<String>() {

					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}

				});
		map.putAll(params);

		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();

			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			if (sb.length() > 0) {
				sb.append("&");
			}

			try {
				sb.append(key).append("=").append(URLEncoder.encode(value, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String sign = StringUtils.lowerCase(MD5.getMD5Str(sb.toString()));

		return sign;
	}


	public static void main(String[] args) throws UnsupportedEncodingException {
		// 添加新安全算法,如果用JCE就要把它添加进去
		Security.addProvider(new com.sun.crypto.provider.SunJCE());

		final byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10,
				0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD,
				0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36,
				(byte) 0xE2 }; // 24字节的密钥
		String szSrc = "This is a 3DES test. 测试";
		String key = "123456789012sda678901234";
		System.out.println("秘钥:" + key);

		System.out.println("加密前的字符串:" + szSrc);

		byte[] encoded = encryptMode(key.getBytes("utf-8"), szSrc.getBytes());
//		BASE64Encoder enc = new BASE64Encoder();
		String cipherString = Base64.encodeBase64String(encoded);
		System.out.println("加密后的字符串:" + cipherString);

		byte[] srcBytes = decryptMode(key.getBytes("utf-8"), encoded);
		System.out.println("解密后的字符串:" + (new String(srcBytes)));

		byte[] encoded1 = encryptMode(key.getBytes("utf-8"), "0097".getBytes());
//		BASE64Encoder enc1 = new BASE64Encoder();
		String cipherString1 = Base64.encodeBase64String(encoded1);
		System.out.println("加密后的字符串1:" + cipherString1 + "        "
				+ URLEncoder.encode(cipherString1, "utf-8"));
		byte[] encoded2 = encryptMode(key.getBytes("utf-8"),
				"13023680197".getBytes());
//		BASE64Encoder enc2 = new BASE64Encoder();
		String cipherString2 = Base64.encodeBase64String(encoded2);
		System.out.println("加密后的字符串phone:" + cipherString2 + "        "
				+ URLEncoder.encode(cipherString2, "utf-8"));
		byte[] encoded3 = encryptMode(key.getBytes("utf-8"),
				"蛋蛋".getBytes("utf-8"));
//		BASE64Encoder enc3 = new BASE64Encoder();
		String cipherString3 = Base64.encodeBase64String(encoded3);
		System.out.println("加密后的字符串username:" + cipherString3 + "        "
				+ URLEncoder.encode(cipherString3, "utf-8"));
		byte[] srcb = decryptMode(key.getBytes("utf-8"), encoded3);
		System.out.println("解密后的username:" + (new String(srcb)));

		HashMap hmap = new HashMap();
		hmap.put("1", "sssss");
		hmap.put("2", "xxxx");

		TreeMap tmap = new TreeMap();
		tmap.putAll(hmap);
		System.out.println(tmap.get("1"));
		System.out.println(tmap.toString());
	}
}