package com.qmd.util;

import java.util.ArrayList;
import java.util.List;

public class AttackWordUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		PushUtil push = new PushUtil();
//		try {
//			push.pushAndoridUnicast("Aq211DB55fHTLZ1epRT1WllkC17Txm2rdrFjdB3XArH2", "11", "22", "33","44");
//			push.pushIosUnicast("b05c8d4650f26b7e4e439c873bfc10631bf20b933545cd520320ebc48c4831c8", "充值提示内容", "22", "hk");
//		} catch (Exception e) {
//			 TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public static  List<String> initWord() {
		
		List<String> list = new ArrayList<String>();

		list.add("<script>");
		list.add("<img>");
		list.add("<iframe>");
		list.add("script");
		list.add("java");
		list.add("src");
		list.add("select");
		
		return list;
	}
	
    private String cleanXSS(String value) {   
    	  
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");   
        value = value.replaceAll("(", "& #40;").replaceAll(")", "& #41;");   
        value = value.replaceAll("'", "& #39;");             
        value = value.replaceAll("eval((.*))", "");   
       // value = value.replaceAll("[\"\'][\s]*javascript:(.*)[\"\']", """");   
        value = value.replaceAll("script", "");   
        return value;   
    }   

}
