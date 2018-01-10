package com.qmd.util;

import java.util.Random;

/**
 * 生成令牌工具类
 * @author admin
 *
 */
public class TokenProccessor {

	
	private TokenProccessor(){}
	
	private static final TokenProccessor instance=new TokenProccessor();

	public static TokenProccessor getInstance(){
		return instance;
	}

	
	public String makeToken(){
		String tokenFlg=(System.currentTimeMillis()+new Random().nextInt(99999999))+"";		
		return tokenFlg;	
	}
	
	
	public boolean isRepeatSubmit(String tokenFlg,Object tokenSession){
		if(tokenFlg==null){
			return true;
		}
		
		if(tokenSession==null){
			return true;
		}
		
		if(!tokenFlg.equals(tokenSession.toString())){
			return true;
		}
		
		
		
		
		return false;
	}
	
	
	public static void main(String[] args) {
		
		System.out.println("tokenFlg="+TokenProccessor.getInstance().makeToken());
	}


}
