package com.qmd.util;

import com.qmd.util.md5.MD5;
import com.qmd.util.md5.Md5ToPhp;

import java.util.Date;
import java.util.Random;

public class TokenUtil {

	private static final Random random = new Random(System.currentTimeMillis());
	
	public final static String getToken(String userid) {
		Date dt = new Date();
		return getToken(userid, dt);
	}
	
	public final static String getToken(String userid, Date dt) {
		long time = dt.getTime() / 1000;
		System.out.println(time);
		String b = Md5ToPhp.MMMD5(Md5ToPhp.MMMD5(userid) + "asdf8");
		String token = MD5.getMD5Str(b	+ String.valueOf(time)+random);
		return token;
	}
	  public static void main(String[] args) {
	    	String  aa = getToken("1");
	    	System.out.println(aa);
	    }
}
