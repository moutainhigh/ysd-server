package com.ysd.pay;

import java.util.Map;

public class PayResult {

	public static final PayResult ERROR = new PayResult("4444");
	
	private String result;
	
	private String code;
	
	public PayResult() {
		
	}
	
	public PayResult(String code) {
		this.code = code;
	}
	
	private Map<String,Object> jsonMap;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}
	
}