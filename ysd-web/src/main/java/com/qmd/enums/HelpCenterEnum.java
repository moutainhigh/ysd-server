package com.qmd.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum HelpCenterEnum {
	/**
	A-会员账号
	B-理财产品
	C-投资
	D-回款与收益
	E-我的优惠
	F-名词解释
	
	
	*/
	A1("a1","注册",""),
    A2("a2","登录",""),
    A3("a3","密码",""),
    A4("a4","更换手机号码",""),
    
    B1("b1","新手标",""),
    B3("b2","金兑宝",""),
    B4("b3","金企宝",""),
    B5("b4","特定项目融资",""),

    
    C1("c1","绑卡认证",""),
    C3("c2","解绑银行卡",""),
    C4("c3","更换银行卡",""),
    
    D1("d1","收益计算",""),
    D2("d2","提现到账",""),
    
    E1("e1","红包",""),
    E2("e2","奖励",""),
    
    F1("f1","年化收益",""),
    F2("f2","计息方式",""),
    F3("f3","还款方式",""),
    
    A("A","a1,a2,a3,a4","会员账号"),
    B("B","b1,b2,b3,b4","理财产品"),
    C("C","c1,c2,c3","投资"),
    D("D","d1,d2","回款与收益"),
    E("E","e1,e2","我的优惠"),
    F("F","f1,f2,f3","名词解释")
  
    ;
	
	private String key;
	private String value;
	private String title;
	
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	 public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	HelpCenterEnum(String key, String value, String title) {
		this.key = key;
		this.value = value;
		this.title = title;
	}
	
	
	public static HelpCenterEnum findValueByKey(String key) {
        for(HelpCenterEnum e : HelpCenterEnum.values()) {
            if(e.getKey().equals(key)) {
                return e;
            }
        }

        return null;
    }
	
	
	
	public static List<Map<String,Object>> getListMapBy(String [] key) {
		List<Map<String,Object>> l=new ArrayList<Map<String,Object>>();
		for(int i=0;i<key.length;i++){
        	
           	
        	if(findValueByKey(key[i])!=null){
        		Map map=new HashMap(); 
        		map.put("key", key[i]);
        		map.put("value",findValueByKey(key[i]).getValue());
        		l.add(map);
        	}
        
       
        	
        }
		return l;
		
		
		
    }
	
	
	
}
