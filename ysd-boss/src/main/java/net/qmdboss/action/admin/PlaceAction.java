package net.qmdboss.action.admin;

import net.qmdboss.entity.Place;
import net.qmdboss.entity.PlaceChilder;
import net.qmdboss.service.PlaceChilderService;
import net.qmdboss.service.PlaceService;
import net.qmdboss.util.CommonUtil;
import net.qmdboss.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@ParentPackage("admin")
public class PlaceAction extends BaseAdminAction  {


	private static final long serialVersionUID = 6212323108971439030L;
	
	private Place place;
	private String name;
	
	private PlaceChilder placeChilder;
	private String dateStart;
	private String dateEnd;
	private String random;//链接地址
	private File img;
	
	@Resource(name = "placeServiceImpl")
	private PlaceService placeService;
	@Resource(name = "placeChilderServiceImpl")
	private PlaceChilderService placeChilderService;
	
	public String list(){
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(name)){
			map.put("name", name);
		}
		pager = placeService.findPager(pager, map);
		return LIST;
	}
	
	public String input(){
		return INPUT;
	}
	public String save(){
		if(id !=null){
			Place persisten = placeService.get(id);
			persisten.setName(place.getName());
			persisten.setRemark(place.getRemark());
			
			placeService.update(persisten);
		}else{
			placeService.save(place);
		}
		redirectUrl = "place!list.action";
		return SUCCESS;
	}
	
	public String edit(){
		place = placeService.get(id);
		return INPUT;
	}
	
	//渠道活动列表
	public String childerList(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("placeId", id);
		map.put("status", 1);
		pager = placeChilderService.findPager(pager, map);
		return "childer_list";
	}
	
	//路演统计
	public String tongji(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", 1);
		if(StringUtils.isNotEmpty(name)){
			map.put("name", name);
		}
		
		if(StringUtils.isNotEmpty(dateStart)){
			map.put("dateStart", CommonUtil.date2begin(CommonUtil.getString2Date(dateStart, "yyyy-MM-dd")));
		}
		if(StringUtils.isNotEmpty(dateEnd)){
			map.put("dateEnd", CommonUtil.date2end(CommonUtil.getString2Date(dateEnd, "yyyy-MM-dd")));
		}
		pager = placeChilderService.findPager(pager, map);
		return "tongji";
	}
	
	
	private String url;
	//发布活动
	public String childerinput(){
		place = placeService.get(id);
		random = CommonUtil.getRandomString(6)+place.getId();
		
		url = CommonUtil.WEB_SITE+"/pr/"+random;
		return "childer_input";
	}
	//编辑活动
	public String childerToedit(){
		placeChilder = placeChilderService.get(id);
		return "childer_edit";
	}
	
	//删除活动
	public String childerdelete(){
		PlaceChilder childer = placeChilderService.get(id);
		childer.setStatus(0);
		placeChilderService.update(childer);
		
		return ajax(Status.success,"删除成功");
	}
	

	public String childersave(){
		place = placeService.get(id);
		if(img!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), img);
			placeChilder.setImg(path);
		}
		placeChilder.setStatus(1);
		placeChilder.setUrl(CommonUtil.WEB_SITE+"/pr/"+random);
		placeChilder.setRandom(random);
		placeChilder.setPlace(place);
		placeChilder.setLoginNum(0);
		placeChilder.setRegNum(0);
		placeChilder.setViewNum(0);
		
		placeChilderService.save(placeChilder);
		
		redirectUrl = "place!childerList.action?id="+id;
		return SUCCESS;
	}
	
	public String childeredit(){
		PlaceChilder pc = placeChilderService.get(id);
		
		
		if(img!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), img);
			pc.setImg(path);
		}
		
		pc.setName(placeChilder.getName());
		pc.setRemark(placeChilder.getRemark());
		
		placeChilderService.update(pc);

		redirectUrl = "place!childerList.action?id="+pc.getPlace().getId();
		return SUCCESS;
	}
	
	
	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PlaceChilder getPlaceChilder() {
		return placeChilder;
	}

	public void setPlaceChilder(PlaceChilder placeChilder) {
		this.placeChilder = placeChilder;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	
	

}
