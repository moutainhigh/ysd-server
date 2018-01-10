package net.qmdboss.util;

public class PWDUtil {

	public final static String encode(String pws, String nb) {
		String b = Md5ToPhp.MMMD5(Md5ToPhp.MMMD5(pws) + "dai665");
		return MD5.getMD5Str(b	+ nb);
	}

}
