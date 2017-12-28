package net.qmdboss.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



public class RedPackUtil {
	
	
	
	
	
	/***
	 * 
	 * ,614090
	  ,614082
	  ,614084
	  ,614083
	  ,614087
	  ,614088
	  ,614085
	  ,614086
	  ,614089
	  614090           5000.0  10
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		
		Map map = new HashMap();
		double[] x1={5000.0,50};//红的满多少额度，红包金额
		double[] x2={5000.0,20};
		double[] x3={5000.0,10};
		double[] x4={5000.0,100};
		double[] x5={5000.0,100};
		double[] x6={5000.0,50.00};
		double[] x7={5000.0,50.00};
		double[] x8={5000.0,100};
		double[] x9={5000.0,100};
		
		
		
		
		map.put(614090, x1);
		map.put(614082, x2);
		map.put(614084, x3);
		map.put(614083, x4);
		map.put(614087, x5);
		
		map.put(614088, x6);
		map.put(614085, x7);
		map.put(614086, x8);
		map.put(614089, x9);
		RedPackUtil.getPackAmount(map, 5000.00);
		
	}
	
	/**
	 * 
	 * @param hbMaps key 为1,2,3,4,5红包递增序号  val位数组0为满多少可用,1位红包的金额
	 * @param amount
	 * @return  返回为组合红包的序号拼装,以逗号分隔   1,2,3,4
	 */
	public static String getPackAmount(Map<Integer,double[]> hbMaps, Double amount){
		List combAmount = new ArrayList();//组合金额
		Map<String, Double> combAmountMap = new HashMap<String, Double>();
		if (hbMaps!=null&&hbMaps.size()>1) {
			Object str[] = hbMaps.keySet().toArray();  
	        int nCnt = str.length;  
	        int nBit = (0xFFFFFFFF >>> (32 - nCnt));  
	        for (int i = 1; i <= nBit; i++) {
	        	StringBuffer combKey = new StringBuffer();//组合key
	        	Double sumAmount=0.0;
	        	Double pakAmount=0.0;//红包的叠加额度
	            for (int j = 0; j < nCnt; j++) {
	                if ((i << (31 - j)) >> 31 == -1) {  
	                	
	                    //System.out.print(str[j]);
	                    combKey.append(","+str[j]);
	                    double[] hb = hbMaps.get(str[j]);
	                    if (hb.length==2) {
	                    	sumAmount += hb[0];
	                    	pakAmount += hb[1];
						}
	                }  
	            }
	            //组合结束
	            //判断组合红包满多少金额相加是否<=总投资额,符合放入集合里，然后取最大的返回出去
	            if (sumAmount<=amount) {
	            	combAmountMap.put(combKey.toString(), pakAmount);
	            	//System.out.println("组合金额为"+sumAmount+"<=总投额"+amount+"可用红包叠加额度为"+pakAmount); 
	            	//System.out.print( "=="+combKey.toString()+"===金额为"+pakAmount);
				}
	            sumAmount=0.0;combKey.setLength(0);pakAmount=0.0;
	            //System.out.println("");  
	        }
		}
		if (combAmountMap!=null&&combAmountMap.size()>0) {
			List keys =  getKeyByValue(combAmountMap,getMaxValue(combAmountMap));//符合的组合金额
			if (keys!=null && keys.size()>0) {
				return removed((String) keys.get(0));
			}
			return null;
		}
		return null;
	}
	
	/**
	 * 根据val拿kes
	 * @param map
	 * @param value
	 * @return
	 */
	public static List getKeyByValue(Map map, Object value) {  
		List keys = new ArrayList();
        Iterator it = map.entrySet().iterator();  
        while (it.hasNext()) {  
            Map.Entry entry = (Entry) it.next();  
            Object obj = entry.getValue();  
            if (obj != null && obj.equals(value)) {  
                //keys=(String) entry.getKey(); 
                keys.add((String) entry.getKey());
            }  
        }
        
//        for (int i = 0; i < keys.size(); i++) {
//        	//System.out.println(keys.get(i));
//		}
        return keys;  
     } 
	
	
	/**
	* 求Map<K,V>中Value(值)的最大值
	* @param map
	* @return
	*/
	public static Object getMaxValue(Map<String, Double> map) {
		if (map == null) return null;
		Collection<Double> c = map.values();
		Object[] obj = c.toArray();
		Arrays.sort(obj);
		//System.out.println("最大红包叠加金额为"+obj[obj.length-1]);
		return obj[obj.length-1];
	}
	public static String removed(String str) {
		if (str!=null && str.length()>0) {
			if (str.subSequence(0, 1).equals(",")) {
				str = (String) str.subSequence(1, str.length());
				return str;
			}
		}
		return "";
	}

	}
