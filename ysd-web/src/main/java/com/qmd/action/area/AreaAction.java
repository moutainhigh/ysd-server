package com.qmd.action.area;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.area.Area;
import com.qmd.service.area.AreaService;
import com.qmd.util.JsonUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("areaAction")
public class AreaAction extends BaseAction {

	private static final long serialVersionUID = 1321099291073671591L;

	Logger log = Logger.getLogger(AreaAction.class);
	@Resource
	AreaService areaService;

	// 根据ID获取地区
	@Action(value="/area/ajaxArea")
	public String ajaxArea() {
		List<Area> areaList = areaService.getAreaList(id);
		log.info("一级地区数量："+areaList.size());
		List<Map<String, String>> optionList = new ArrayList<Map<String, String>>();
		for (Area area : areaList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", area.getName());
			map.put("value", area.getId().toString());
			optionList.add(map);
		}
		return ajax(JsonUtil.toJson(optionList));
	}
}