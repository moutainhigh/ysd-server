package com.qmd.bean.user;

/**
 * 数据配置表【包含下拉列表数据.比如：民族】
 * @author xsf
 *
 */
public class BorrowListingItem  {

	
	
//    private Integer id;//编号
    private String name;//名称
    private String keyValue;//Key值
    private String description;//描述
    

//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
//	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	
}
