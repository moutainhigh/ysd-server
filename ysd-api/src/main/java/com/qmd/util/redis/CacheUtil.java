package com.qmd.util.redis;

import com.qmd.util.ConfigUtil;
import com.qmd.util.SerializeUtil;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

public class CacheUtil {

	// private static String host =
	// "72ca6e399c8f4174.m.cnhza.kvstore.aliyuncs.com";//控制台显示访问地址
	// private static int port = 6379;
	// private static String auth = "72ca6e399c8f4174:FarOngwaNg2015R";
	//

	// private static String host = "192.168.0.240";//控制台显示访问地址
	// private static int port = 6379;
	// private static String auth = null;

	private final static int JEDIS_DB_SELECT = 1;

	public static String getCashValue(String key) {

		System.out.println("======CacheUtil.getCashValue(" + key + ")");

		String str = null;
		try {

			Jedis jedis = new Jedis(ConfigUtil.getConfigUtil().get(
					ConfigUtil.QMD_REDIS_HOST), Integer.parseInt(ConfigUtil
					.getConfigUtil().get(ConfigUtil.QMD_REDIS_PORT)));
			// 鉴权信息由用户名:密码拼接而成
			if (StringUtils.isNotEmpty(ConfigUtil.getConfigUtil().get(
					ConfigUtil.QMD_REDIS_AUTH))) {
				jedis.auth(ConfigUtil.getConfigUtil().get(
						ConfigUtil.QMD_REDIS_AUTH));// instance_id:password
			}

			jedis.select(JEDIS_DB_SELECT);
			// set一个key
			str = jedis.get(key);
			System.out.println("======return==" + str);
			// System.out.println("Get Key " + key + " ReturnValue: " +
			// getvalue);
			jedis.quit();
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return str;
	}

	public static Object getObjValue(String key) {

		System.out.println("======CacheUtil.getCashValue(" + key + ")");

		try {
			
			return getObjValueWithOutCatch(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public static Object getObjValueWithOutCatch(String key) {


		Object obj = null;

		Jedis jedis = new Jedis(ConfigUtil.getConfigUtil().get(
				ConfigUtil.QMD_REDIS_HOST), Integer.parseInt(ConfigUtil
				.getConfigUtil().get(ConfigUtil.QMD_REDIS_PORT)));
		// 鉴权信息由用户名:密码拼接而成
		if (StringUtils.isNotEmpty(ConfigUtil.getConfigUtil().get(
				ConfigUtil.QMD_REDIS_AUTH))) {
			jedis.auth(ConfigUtil.getConfigUtil()
					.get(ConfigUtil.QMD_REDIS_AUTH));// instance_id:password
		}

		jedis.select(JEDIS_DB_SELECT);

		byte[] bytes = jedis.get(key.getBytes());
		if( bytes!=null ){
			obj = SerializeUtil.unserialize(bytes);
		}
		System.out.println("======return==" + obj);
		// System.out.println("Get Key " + key + " ReturnValue: " +
		// getvalue);
		jedis.quit();
		jedis.close();

		return obj;
	}

	public static String setCashValue(String key, String value) {
		System.out.println("======CacheUtil.setCashValue(" + key + ",..)");
		String ret = null;
		try {
			Jedis jedis = new Jedis(ConfigUtil.getConfigUtil().get(
					ConfigUtil.QMD_REDIS_HOST), Integer.parseInt(ConfigUtil
					.getConfigUtil().get(ConfigUtil.QMD_REDIS_PORT)));
			// 鉴权信息由用户名:密码拼接而成
			if (StringUtils.isNotEmpty(ConfigUtil.getConfigUtil().get(
					ConfigUtil.QMD_REDIS_AUTH))) {
				jedis.auth(ConfigUtil.getConfigUtil().get(
						ConfigUtil.QMD_REDIS_AUTH));// instance_id:password
			}

			jedis.select(JEDIS_DB_SELECT);

			ret = jedis.set(key, value);
			System.out.println("Set Key " + key + " Value: " + value);
			System.out.println("======return==" + ret);

			jedis.quit();
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ret;
	}

	public static Long delCashValue(String key) {
		System.out.println("======CacheUtil.delCashValue(" + key + ",..)");
		Long ret = null;
		try {
			Jedis jedis = new Jedis(ConfigUtil.getConfigUtil().get(
					ConfigUtil.QMD_REDIS_HOST), Integer.parseInt(ConfigUtil
					.getConfigUtil().get(ConfigUtil.QMD_REDIS_PORT)));
			// 鉴权信息由用户名:密码拼接而成
			if (StringUtils.isNotEmpty(ConfigUtil.getConfigUtil().get(
					ConfigUtil.QMD_REDIS_AUTH))) {
				jedis.auth(ConfigUtil.getConfigUtil().get(
						ConfigUtil.QMD_REDIS_AUTH));// instance_id:password
			}

			jedis.select(JEDIS_DB_SELECT);

			ret = jedis.del(key);
			System.out.println("Del Key " + key);
			System.out.println("======return==" + ret);

			jedis.quit();
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ret;
	}
	
	public static Long delObjValue(String key) {
		System.out.println("======CacheUtil.delCashValue(" + key + ",..)");
		Long ret = null;
		try {
			Jedis jedis = new Jedis(ConfigUtil.getConfigUtil().get(
					ConfigUtil.QMD_REDIS_HOST), Integer.parseInt(ConfigUtil
					.getConfigUtil().get(ConfigUtil.QMD_REDIS_PORT)));
			// 鉴权信息由用户名:密码拼接而成
			if (StringUtils.isNotEmpty(ConfigUtil.getConfigUtil().get(
					ConfigUtil.QMD_REDIS_AUTH))) {
				jedis.auth(ConfigUtil.getConfigUtil().get(
						ConfigUtil.QMD_REDIS_AUTH));// instance_id:password
			}

			jedis.select(JEDIS_DB_SELECT);

			ret = jedis.del(key.getBytes());
			System.out.println("Del Key " + key);
			System.out.println("======return==" + ret);

			jedis.quit();
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ret;
	}
	

	public static String setObjValue(String key, Object value) {
		System.out.println("======CacheUtil.setObjValue(" + key + ",..)");
		String ret = null;
		try {
			Jedis jedis = new Jedis(ConfigUtil.getConfigUtil().get(
					ConfigUtil.QMD_REDIS_HOST), Integer.parseInt(ConfigUtil
					.getConfigUtil().get(ConfigUtil.QMD_REDIS_PORT)));
			// 鉴权信息由用户名:密码拼接而成
			if (StringUtils.isNotEmpty(ConfigUtil.getConfigUtil().get(
					ConfigUtil.QMD_REDIS_AUTH))) {
				jedis.auth(ConfigUtil.getConfigUtil().get(
						ConfigUtil.QMD_REDIS_AUTH));// instance_id:password
			}

			jedis.select(JEDIS_DB_SELECT);

			ret = jedis.set(key.getBytes(), SerializeUtil.serialize(value));
			System.out.println("Set Key " + key + " Value: " + value);
			System.out.println("======return==" + ret);

			jedis.quit();
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ret;
	}
	
	/**
	 * 设置时限
	 * @param key
	 * @param expire
	 * @return
	 */
	public static Long setExpire(String key, Integer expire) {
		System.out.println("======CacheUtil.setExpire(" + key + ",..)");
		Long ret = null;
		try {
			Jedis jedis = new Jedis(ConfigUtil.getConfigUtil().get(
					ConfigUtil.QMD_REDIS_HOST), Integer.parseInt(ConfigUtil
					.getConfigUtil().get(ConfigUtil.QMD_REDIS_PORT)));
			// 鉴权信息由用户名:密码拼接而成
			if (StringUtils.isNotEmpty(ConfigUtil.getConfigUtil().get(
					ConfigUtil.QMD_REDIS_AUTH))) {
				jedis.auth(ConfigUtil.getConfigUtil().get(
						ConfigUtil.QMD_REDIS_AUTH));// instance_id:password
			}

			jedis.select(JEDIS_DB_SELECT);

			ret = jedis.expire(key, expire);
			System.out.println("Set Key " + key + " Expire: " + expire);
			System.out.println("======return==" + ret);

			jedis.quit();
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ret;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String val = "{\"rcd\":\"R0001\",\"rmg\":null,\"industryViwepagerList\":[{\"img\":\""
				+ "/upload/image/201512\616eb855f6724da4a263ecc7b7faaf89.jpg"
				+ "\",\"linkUrl\":\""
				+ "http://www.farongwang.com/cmsDetail.html?announce=510"

				+ "\",\"name\":\"5\",\"remark\":\"255,255,255\"},"
				+ "{\"img\":\"/upload/image/201512/7e304e4302524c41ae96cbb30eb94eaa.jpg\","
				+ "\"linkUrl\":\"http://www.farongwang.com/lawOnline.html\",\"name\":\"3\",\"remark\":\"255,195,52\"}]}";

		setCashValue("frw_test_url001", val);

		String test = getCashValue("frw_test_url001");

		System.out.println(test);
		//
		// try {
		// String host = "xx.kvstore.aliyuncs.com";//控制台显示访问地址
		// int port = 6379;
		// Jedis jedis = new Jedis(host, port);
		// //鉴权信息由用户名:密码拼接而成
		// jedis.auth("instance_id:password");//instance_id:password
		// String key = "redis";
		// String value = "aliyun-redis";
		// //select db默认为0
		// jedis.select(1);
		// //set一个key
		// jedis.set(key, value);
		// System.out.println("Set Key " + key + " Value: " + value);
		// //get 设置进去的key
		//
		//
		// // String temp=RedisClinet. getInstance().set(g, key);
		// String getvalue = jedis.get(key);
		// System.out.println("Get Key " + key + " ReturnValue: " + getvalue);
		// jedis.quit();
		// jedis.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}
	
	public static void  setOrderId(String token, String value) {
		setObjValue("order:id:"+token, value);
		
	}
	public static String getOrderId(String token ) {
		
		Object obj =  getObjValue("order:id:"+token);
		if(obj==null) {
			return null;
		}
		return obj.toString();
	}

}
