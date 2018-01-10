package com.qmd.util.bean;

public class RaiseTypeBean {

	private String typeCode;
	private String typeName;
	
	
	public RaiseTypeBean() {}
	public RaiseTypeBean(String code,String name) {
		this.typeCode = code;
		this.typeName = name;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
