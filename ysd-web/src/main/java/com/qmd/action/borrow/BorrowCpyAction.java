package com.qmd.action.borrow;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.borrow.BorCompany;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.user.User;
import com.qmd.service.borrow.BorrowCpyService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.user.UserService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("borCpyAction")
@InterceptorRefs({
	@InterceptorRef(value = "userVerifyInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
public class BorrowCpyAction extends BaseAction {

	private static final long serialVersionUID = -7490914437238146463L;
	Logger log = Logger.getLogger(BorrowCpyAction.class);
	@Resource
	BorrowCpyService borCpyService;
	@Resource
	UserService userService;
	@Resource
	BorrowService borrowService;
	
	BorCompany borCompany;
	
	private Borrow borrow;
	
	private Integer memberType;// 会员类型 0:个人;1:企业
	private Integer i=0;
	
	public BorCompany getBorCompany() {
		return borCompany;
	}
	public void setBorCompany(BorCompany borCompany) {
		this.borCompany = borCompany;
	}
	public BorrowCpyService getBorCpyService() {
		return borCpyService;
	}
	public void setBorCpyService(BorrowCpyService borCpyService) {
		this.borCpyService = borCpyService;
	}
	
	/**
	 * 选择标类型
	 * @return
	 */
	@Action(value="/borrow/borrowChoose",
			results={
					@Result(name="credit", location="/content/borrow/borrowSelect.ftl", type="freemarker"),
					@Result(name="fail", location="/content/borrow/toSuccess.ftl", type="freemarker")})
	public String borrowChoose() {
		
		if (getLoginUser().getTypeId().intValue() != 1) {
			addActionError("只有借款人才能访问！");
			return "error_ftl";
		}
		
		return "credit";
	}
	
	/**
	 * 添加秒标页面
	 */
	@Action(value="/borrow/borrowInputSecond",
			results={
					@Result(name="credit", location="/content/borrow/borrowInputSecond.ftl", type="freemarker"),
					@Result(name="fail", location="/content/borrow/toSuccess.ftl", type="freemarker")})
	public String borrowInputSecond(){
		log.info("跳转到添加秒标页面===============");
		User user = getLoginUser();
		System.out.println("======>"+user.getTypeId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", user.getId());
		User user1 = userService.getUser(map);
		Map<String,Object> bMap = new HashMap<String,Object>();
		bMap.put("userId", user.getId());
		int[] array = {0};
		bMap.put("array", array);
		
//		List<Borrow> userBorrowList = this.getBorrowService().queryUserBorrowList(bMap);//借款者招标中记录取得
//		for (Borrow borrow : userBorrowList){
//			if((!"".equals(borrow.getType()))&&Integer.parseInt(borrow.getType())==0){
//				i++;
//			}
//		}
//		if(i>0){
//			addActionError("您已经有发布中的标,请自行撤销或等审核后再发布!");
//			return "error_ftl";
//		}else{
			if(user.getTypeId()!=1||user1.getTypeId()!=1){
				return "fail";
			} else {
				memberType = user.getMemberType();
				return "credit";
			}
//		}
	}
	
	/**
	 * 添加流转标页面
	 */
	@Action(value="/borrow/borrowInputWander",
			results={
					@Result(name="credit", location="/content/borrow/borrowInputWander.ftl", type="freemarker"),
					@Result(name="fail", location="/content/borrow/toSuccess.ftl", type="freemarker")})
	public String borrowInputWander(){
		log.info("跳转到添加秒标页面===============");
		User user = getLoginUser();
		System.out.println("======>"+user.getTypeId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", user.getId());
		User user1 = userService.getUser(map);
		Map<String,Object> bMap = new HashMap<String,Object>();
		bMap.put("userId", user.getId());
		int[] array = {0};
		bMap.put("array", array);
		
//		List<Borrow> userBorrowList = this.getBorrowService().queryUserBorrowList(bMap);//借款者招标中记录取得
//		for (Borrow borrow : userBorrowList){
//			if((!"".equals(borrow.getType()))&&Integer.parseInt(borrow.getType())==2){
//				i++;
//			}
//		}
//		if(i>0){
//			addActionError("您已经有发布中的标,请自行撤销或等审核后再发布!");
//			return "error_ftl";
//		}else{
			if(user.getTypeId()!=1||user1.getTypeId()!=1){
				return "fail";
			} else {
				memberType = user.getMemberType();
				borrow = new Borrow();
				return "credit";
			}
//		}
	}
	
	/**
	 * 添加月标页面
	 */
	@Action(value="/borrow/borrowInputMonth",
			results={
					@Result(name="credit", location="/content/borrow/borrowInputMonth.ftl", type="freemarker"),
					@Result(name="fail", location="/content/borrow/toSuccess.ftl", type="freemarker")})
	public String borrowInputMonth(){
		log.info("跳转到添加月标页面===============");
		User user = getLoginUser();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", user.getId());
		User user1 = userService.getUser(map);
		Map<String,Object> bMap = new HashMap<String,Object>();
		bMap.put("userId", user.getId());
		int[] array = {0};
		bMap.put("array", array);
		
//		List<Borrow> userBorrowList = this.getBorrowService().queryUserBorrowList(bMap);//借款者招标中记录取得
//		for (Borrow borrow : userBorrowList){
//			if((!"".equals(borrow.getType()))&&Integer.parseInt(borrow.getType())==4){
//				i++;
//			}
//		}
//		if(i>0){
//			addActionError("您已经有发布中的标,请自行撤销或等审核后再发布!");
//			return "error_ftl";
//		}else{
			if(user.getTypeId()!=1||user1.getTypeId()!=1){
				return "fail";
			} else {
				memberType = user.getMemberType();
				borrow = new Borrow();
				return "credit";
			}
//		}
	}
	
	/**
	 * 
	 */
	@Action(value="/borrow/addBorrow",results={@Result(name="credit", location="/content/borrow/setborrow.ftl", type="freemarker"),@Result(name="fail", location="/content/borrow/toSuccess.ftl", type="freemarker")})
	public String addBorrow(){
		log.info("跳转到添加天标===============");
		User user = getLoginUser();
		System.out.println("======>"+user.getTypeId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", user.getId());
		User user1 = userService.getUser(map);
		Map<String,Object> bMap = new HashMap<String,Object>();
		bMap.put("userId", user.getId());
		int[] array = {0};
		bMap.put("array", array);
		
//		List<Borrow> userBorrowList = this.getBorrowService().queryUserBorrowList(bMap);//借款者招标中记录取得
//		for (Borrow borrow : userBorrowList){
//			if((!"".equals(borrow.getType()))&&Integer.parseInt(borrow.getType())==1){
//				i++;
//			}
//		}
//		if(i>0){
//			addActionError("您已经有发布中的标,请自行撤销或等审核后再发布!");
//			return "error_ftl";
//		}else {
			if(user.getTypeId()!=1||user1.getTypeId()!=1){
				return "fail";
			}
			
	//		String type = borCompany.getType();
	//		log.info(type);
	//		log.info(borCompany.getType());
	//		if(type.equals("1")){
	//			log.info("type==1");
	//			return "success";	
	//		}else{
	//			return "credit";
	//		}
			else{
				memberType = user.getMemberType();
				return "credit";
			}
//		}
	}
	
	/**
	 * 
	 * @return
	 */
	@Action(value="/borrow/addBorCpy",results={@Result(name="success", location="/content/borrow/setborrow.ftl", type="freemarker"),@Result(name="fail", location="/content/user/login.ftl", type="freemarker")})
	public String addBorCpy(){
//		Map<String,User> map = (Map<String, User>) this.getMemcachedByCookie(ConstantUtil.USER_ID_COOKIE_NAME);
//		User user = map.get(ConstantUtil.USER_NAME);
		User user = getLoginUser();
		borCompany.setUserId(user.getId());
		this.getBorCpyService().add(borCompany);
		return "success";
		
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public BorrowService getBorrowService() {
		return borrowService;
	}
	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}
	public Borrow getBorrow() {
		return borrow;
	}
	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}
	
	
}
