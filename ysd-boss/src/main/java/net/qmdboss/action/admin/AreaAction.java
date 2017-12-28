package net.qmdboss.action.admin;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import net.qmdboss.entity.Area;
import net.qmdboss.entity.Areashop;
import net.qmdboss.service.AreaService;
import net.qmdboss.service.AreashopService;
import net.qmdboss.util.JsonUtil;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台Action类 - 地区
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXAECC1B974FB9EE5128F3B8A118CCEC03
 * ============================================================================
 */

@ParentPackage("admin")
public class AreaAction extends BaseAdminAction {

	private static final long serialVersionUID = 6254431866456845575L;

	private Areashop areashop;
	private Integer parentId;
	private Areashop parent;
	private List<Areashop> areaList = new ArrayList<Areashop>();

	@Resource(name = "areashopServiceImpl")
	private AreashopService areashopService;

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	// 根据ID获取地区
	@InputConfig(resultName = "ajaxError")
	public String ajaxArea() {
		List<Area> areaList = areaService.getAreaList(id);
		List<Map<String, String>> optionList = new ArrayList<Map<String, String>>();
		for (Area area : areaList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", area.getName());
			map.put("value", area.getId().toString());
			optionList.add(map);
		}
		return ajax(JsonUtil.toJson(optionList));
	}
	// 添加
	public String add() {
		if (parentId != null) {
			parent = areashopService.load(parentId);
		}
		return INPUT;
	}

	// 编辑
	public String edit() {
		areashop = areashopService.load(id);
		parent = areashop.getParent();
		return INPUT;
	}

	// 列表
	public String list() {
		if (parentId != null) {
			parent = areashopService.load(parentId);
			areaList = new ArrayList<Areashop>(parent.getChildren());
		} else {
			areaList = areashopService.getRootAreaList();
		}
		return LIST;
	}

	// 删除
	public String delete() {
		areashopService.delete(id);
		return ajax(Status.success, "地区删除成功!");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "area.name", message = "名称不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "area.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (parentId != null) {
			areashop.setParent(areashopService.load(parentId));
		} else {
			areashop.setParent(null);
		}
		areashopService.save(areashop);
		redirectUrl = "area!list.action?parentId=" + parentId;
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "area.name", message = "名称不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "area.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	public String update() {
		Areashop persistent = areashopService.load(id);
		Areashop parent = persistent.getParent();
		if (parent != null) {
			parentId = parent.getId();
		}
		persistent.setName(areashop.getName());
		persistent.setOrderList(areashop.getOrderList());
		areashopService.update(persistent);
		redirectUrl = "area!list.action?parentId=" + parentId;
		return SUCCESS;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<Areashop> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Areashop> areaList) {
		this.areaList = areaList;
	}

	public Areashop getAreashop() {
		return areashop;
	}

	public void setAreashop(Areashop areashop) {
		this.areashop = areashop;
	}

	public Areashop getParent() {
		return parent;
	}

	public void setParent(Areashop parent) {
		this.parent = parent;
	}

}