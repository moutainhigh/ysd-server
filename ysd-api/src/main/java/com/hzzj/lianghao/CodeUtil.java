package com.hzzj.lianghao;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CodeUtil {


	public static String encryptBASE64(byte[] key) {
//		return (new BASE64Encoder()).encodeBuffer(key);
        return Base64.encodeBase64String(key);
    }

	public static byte[] decryptBASE64(String message) {
        return Base64.decodeBase64(message);
        /*try {
			return (new BASE64Decoder()).decodeBuffer(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;*/
	}
	
	public static byte char2byte(char paramChar) {
		switch (paramChar) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case 'A':
			return 10;
		case 'B':
			return 11;
		case 'C':
			return 12;
		case 'D':
			return 13;
		case 'E':
			return 14;
		case 'F':
			return 15;
		case ':':
		case ';':
		case '<':
		case '=':
		case '>':
		case '?':
		case '@':
		}
		return 0;
	}

	public static String bytes2hex(byte[] paramArrayOfByte) {
		String str1 = "";
		String str2 = "";
		if (null == paramArrayOfByte)
			return null;
		for (int i = 0; i < paramArrayOfByte.length; i++) {
			str2 = Integer.toHexString(paramArrayOfByte[i] & 0xFF);
			if (str2.length() == 1) {
				str2 = "0" + str2;
			}
			str1 = str1 + str2;
		}
		return str1.toUpperCase();
	}

	public static byte[] hex2bytes(String paramString) {
		paramString = paramString.toUpperCase();
		char[] arrayOfChar = paramString.toCharArray();
		byte[] arrayOfByte = new byte[arrayOfChar.length / 2];
		int i = 0;
		for (int j = 0; j < arrayOfChar.length; j += 2) {
			int k = 0;
			k = (byte) (k | char2byte(arrayOfChar[j]));
			k = (byte) (k << 4);
			k = (byte) (k | char2byte(arrayOfChar[(j + 1)]));
			arrayOfByte[i] = (byte) k;
			i++;
		}
		return arrayOfByte;
	}

	
	public static String readLoacalKeyFile(String fileName){
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			String readLine = null;
			StringBuilder buffer = new StringBuilder();
			while ((readLine = reader.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					buffer.append(readLine);
					buffer.append('\r');
				}
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
		}
		return null;
	}
}
