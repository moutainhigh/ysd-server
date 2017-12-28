package com.qmd.util.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 审核内容 参数
 * 
 */
public class AuditContent {

	private String name;//名字
	private Integer flag;//标记
	private String status;//状态
	// private String time;
//	public static Map<Integer, String> map = new HashMap<Integer, String>();

	public static Map<Integer, String> mapPar = new HashMap<Integer, String>();
	
	public static Map<Integer, String> mapInit() {
		Map<Integer, String> ret = new HashMap<Integer, String>();
		for (int i = 0; i < 17; i++) {
			ret.put(i, "0");
		}
		return ret;
	}

	//默认都为审核不通过
	static {
//		for (int i = 0; i < 17; i++) {
//			map.put(i, "0");
//		}
		
		mapPar.put(0, "身份证");
		mapPar.put(1, "户口簿");
		mapPar.put(2, "征集报告");
		mapPar.put(3, "收入证明");
		mapPar.put(4, "车辆行驶证");
		mapPar.put(5, "车辆完税证明");
		mapPar.put(6, "购车发票");
		mapPar.put(7, "机动车登记证");
		mapPar.put(8, "企业营业执照");
		mapPar.put(9, "企业税务登记证");
		mapPar.put(10, "企业组织机构代码证");
		mapPar.put(11, "房产证");
		mapPar.put(12, "土地证");
		mapPar.put(13, "房产契证");
		mapPar.put(14, "房产评估报告");
		mapPar.put(15, "他项权证");
		mapPar.put(16, "财务报表");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	// public String getTime() {
	// return time;
	// }
	// public void setTime(String time) {
	// this.time = time;
	// }

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}
