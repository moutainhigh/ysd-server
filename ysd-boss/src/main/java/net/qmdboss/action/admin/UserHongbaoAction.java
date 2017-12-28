package net.qmdboss.action.admin;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import net.qmdboss.bean.Pager.Order;
import net.qmdboss.entity.User;
import net.qmdboss.entity.UserHongbao;
import net.qmdboss.service.ListingService;
import net.qmdboss.service.UserHongbaoService;
import net.qmdboss.service.UserService;
import net.qmdboss.util.CommonUtil;
import net.qmdboss.util.ExcelWorkUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

@ParentPackage("admin")
public class UserHongbaoAction extends BaseAdminAction  {

	private static final long serialVersionUID = -4532731237680522009L;
	
	private UserHongbao userHongbao;
	private User user;
	private String check1;
	private String check2;
	private String check3;
	
	private Integer num1;
	private Integer num2;
	private Integer num3;
	private File fileExc;
	
	
	
	private List<String> userIdList;//批量用户id组；
	private List<String> hbMoneyList;// 红包金额
	private List<Integer> hbEndTimeList;// 使用有效期
	private List<Integer> hbLimitStartList;
	private List<Integer> hbLimitEndList;
	private List<Integer> hbLimitMaxMoney;
	private String hongbaoName;
	private String username;
	private String status;
	
	@Resource(name = "userHongbaoServiceImpl")
	private UserHongbaoService userHongbaoService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "listingServiceImpl")
	private ListingService listingService;
	
	public String input(){
		
		return INPUT;
	}
	
	
	public String inputPL(){
		
		return "user_hongbao_input_pl";
	}
	
	
	//红包批量路口 简单拦截数据
	public String addHongbaoPL(){
		
		
		if (hbMoneyList != null && hbMoneyList.size() > 0) {
			
			for (int i = 0; i < hbMoneyList.size(); i++) {
				
				if (StringUtils.isNotEmpty(hbMoneyList.get(i)) && new BigDecimal(hbMoneyList.get(i)).compareTo(new BigDecimal(0)) > 0) {
					
				}else{
					addErrorMessages("请输入红包金额!");
					return ERROR;
				}
				if (hbEndTimeList.get(i) == null) {
	
					addErrorMessages("请输入红包使用有效期!");
					return ERROR;
				}

				if (hbLimitStartList.get(i) == null) {
					
					addErrorMessages("请输入可用项目期限起始天数!");
					return ERROR;
				}
				if (hbLimitEndList.get(i) == null) {
		
					addErrorMessages("请输入可用项目期限截止天数!");
					return ERROR;
				}
				
				
				if(hbLimitStartList.get(i)>hbLimitEndList.get(i)){
					addErrorMessages("项目期限起始天数必须小于截止天数!");
					return ERROR;
				}
				
				
				if(hbLimitMaxMoney.get(i)<100){
					addErrorMessages("红包使用上限过低!");
					return ERROR;
				}
					
			}
		}else {
			addErrorMessages("请设置红包!");
			return ERROR;
				
		}
		
		
		ExcelWorkUtil e=new ExcelWorkUtil();
		userIdList=e.getDateId(fileExc);
		
		System.out.println("红包多发用户选择数="+userIdList.size());
		if(userIdList.size()>5000){
			addErrorMessages("用户选择大于5000");
			return ERROR;
		}
		//红包批量保存数据
		
		if(userIdList.size()<=1000){
			userHongbaoService.savePLHongbao(userIdList,hbMoneyList,hbEndTimeList,hbLimitMaxMoney,hbLimitStartList,hbLimitEndList,userHongbao.getName());
		}else{
			
			 List<List<String>> result = createList(userIdList, 500);  
	          
		      for(List<String> subArr:result) {  		            
		           userHongbaoService.savePLHongbao(subArr,hbMoneyList,hbEndTimeList,hbLimitMaxMoney,hbLimitStartList,hbLimitEndList,userHongbao.getName());            
		      } 		
		}
		
		
		
		
	return SUCCESS;	
	}
	

	
	
	//多个红包               废弃  性能不好
	/**
	public String addHongbaoPLH() {
		
		ExcelWorkUtil e=new ExcelWorkUtil();
		userIdList=e.getDateId(fileExc);
		
		System.out.println("红包多发用户选择数="+userIdList.size());
		if(userIdList.size()>5001){
			addErrorMessages("用户选择大于5000");
			return ERROR;
		}
		String x=userIdList.get(0);
	      if(x.length()==11){
	    	  List ll=new ArrayList();
	    	  
	    	  User u1=new User();
	    	  for(int i=0;i<userIdList.size();i++){
	    		  
	    		  u1 = userService.getUserByUsername(userIdList.get(i));
	    		  if(u1!=null){
	    			  ll.add(u1.getId().toString());
	    		  }  
	    	  }
	    	
	    	  userIdList=ll;
	      }
	        	      
		if (hbMoneyList != null && hbMoneyList.size() > 0) {
			
			for (int i = 0; i < hbMoneyList.size(); i++) {
				
				if (StringUtils.isNotEmpty(hbMoneyList.get(i)) && new BigDecimal(hbMoneyList.get(i)).compareTo(new BigDecimal(0)) > 0) {
					
				
				}else{
					addErrorMessages("请输入红包金额!");
					return ERROR;
				}
				if (hbEndTimeList.get(i) == null) {
	
					addErrorMessages("请输入红包使用有效期!");
					return ERROR;
				}

				if (hbLimitStartList.get(i) == null) {
					
					addErrorMessages("请输入可用项目期限起始天数!");
					return ERROR;
				}
				if (hbLimitEndList.get(i) == null) {
		
					addErrorMessages("请输入可用项目期限截止天数!");
					return ERROR;
				}
				
				
				if(hbLimitStartList.get(i)>hbLimitEndList.get(i)){
					addErrorMessages("项目期限起始天数必须小于截止天数!");
					return ERROR;
				}
				
				
				if(hbLimitMaxMoney.get(i)<100){
					addErrorMessages("红包使用上限过低!");
					return ERROR;
				}
					
			}
		}else {
			addErrorMessages("请设置红包!");
			return ERROR;
				
		}
				
		for(int t=0;t<userIdList.size();t++){
			
			User u = new User();
			u.setId(Integer.parseInt(userIdList.get(t)));
			u = userService.get(u.getId());
		    for(int i=0;i<hbMoneyList.size();i++){
				UserHongbao hb = new UserHongbao();
				
				hb.setName(userHongbao.getName());//红包标题
				hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")+CommonUtil.getRandomString(5));
				hb.setUser(u);
				hb.setSource(5);
				hb.setStatus(-1);//待发放
				hb.setIsApp(1);
				hb.setIsHfive(1);
				hb.setIsPc(1);
				hb.setIsLooked(0);
				hb.setUsedMoney(new BigDecimal(0));
				hb.setExpDate(hbEndTimeList.get(i));
				hb.setInvestFullMomey(hbLimitMaxMoney.get(i));
				hb.setLimitStart(hbLimitStartList.get(i));
				hb.setLimitEnd(hbLimitEndList.get(i));
				hb.setMoney(new BigDecimal(hbMoneyList.get(i)));
				hb.setSourceString(userHongbao.getSourceString());
			
				userHongbaoService.save(hb);
			}	
				
		}	
		return SUCCESS;

	}
	**/
	/**
	// 10 20 50 元红包
	public String save(){
		User u = new User();
		List<UserHongbao> hbList = new ArrayList<UserHongbao>();
		if(user != null && StringUtils.isNotEmpty( user.getUsername())){

			u = userService.getUserByUsername(user.getUsername());
		}
		if(userHongbao == null){
			userHongbao = new UserHongbao();
		}
		
		if("on".equals(check1) ){//10元红包
			if(num1 != null){
				if( num1 >0){
					for(int i=0;i<num1 ;i++){
						UserHongbao hb = new UserHongbao();
						hb.setMoney(new BigDecimal(10));
						hb.setUser(u);
						hb.setSource(5);
						hb.setStatus(-1);//待发放
						hb.setSourceString(userHongbao.getSourceString());
						hbList.add(hb);
					}
				}
			}else{
				addActionError("请输入10元红包个数!");
				return ERROR;
			}
		}
		if("on".equals(check2) && num2 >0){//20元红包
			if(num2 != null){
				if( num2 >0){
					for(int i=0;i<num2 ;i++){
						UserHongbao hb = new UserHongbao();
						hb.setMoney(new BigDecimal(20));
						hb.setUser(u);
						hb.setSource(5);
						hb.setStatus(-1);//待发放
						hb.setSourceString(userHongbao.getSourceString());
						hbList.add(hb);
					}
				}
			}else{
				addActionError("请输入20元红包个数!");
				return ERROR;
			}
		}
		if("on".equals(check3) && num3 >0){//50元红包
			userHongbao.setMoney(new BigDecimal(50));
			if(num3 != null){
				if( num3 >0){
					for(int i=0;i<num3 ;i++){
						UserHongbao hb = new UserHongbao();
						hb.setMoney(new BigDecimal(50));
						hb.setUser(u);
						hb.setSource(5);
						hb.setStatus(-1);//待发放
						hb.setSourceString(userHongbao.getSourceString());
						hbList.add(hb);
					}
				}
			}else{
				addActionError("请输入50元红包个数!");
				return ERROR;
			}
		}
		userHongbaoService.saveLosts(hbList);
		redirectUrl = "user_hongbao!list.action";
		return SUCCESS;
	}
	**/
	
	/**
	 * 新单发红包
	 * @author zdl 
	 */
	@InputConfig(resultName = "error")
	public String saveNew(){
		UserHongbao hb = new UserHongbao();
		
		if(user == null || StringUtils.isBlank(user.getUsername())){
			addErrorMessages("请输入用户名!");
			return ERROR;
		}
		if(userHongbao == null){
			addErrorMessages("请输入红包信息!");
			return ERROR;
		}
		//验证:用户
		User u = userService.getUserByUsername(user.getUsername());
		if(u != null && u.getId() != null){
			hb.setUser(u);
		}else{
			addErrorMessages("用户不存在!");
			return ERROR;
		}
		//验证:红包标题
		if(StringUtils.isNotBlank(userHongbao.getName())){
			hb.setName(userHongbao.getName());
		}else{
			addErrorMessages("请输入红包标题！");
			return ERROR;
		}
		//验证：红包金额
		if(userHongbao.getMoney() != null && userHongbao.getMoney().compareTo(new BigDecimal(0)) > 0){
			hb.setMoney(userHongbao.getMoney());
		}else{
			addErrorMessages("请输入红包金额!");
			return ERROR;
		}
		//验证：使用有效期
		if(userHongbao.getExpDate() != null){
			hb.setExpDate(userHongbao.getExpDate());
		}else{
			addErrorMessages("请输入使用有效期!");
			return ERROR;
		}
		//验证：可用项目期限
		if(userHongbao.getLimitStart() != null && userHongbao.getLimitEnd() != null){
			if(userHongbao.getLimitStart() >= userHongbao.getLimitEnd()){
				addErrorMessages("项目期限起始天数必须小于截止天数!");
				return ERROR;
			}
		}else{
			addErrorMessages("请输入项目期限!");
			return ERROR;
		}
		//验证:投资金额
		if(userHongbao.getInvestFullMomey() != null && userHongbao.getInvestFullMomey() > 0){
			hb.setInvestFullMomey(userHongbao.getInvestFullMomey());
		}else{
			addErrorMessages("请输入投资金额!");
			return ERROR;
		}
		//验证：备注
		if(StringUtils.isBlank(userHongbao.getSourceString())){
			addErrorMessages("请输入备注!");
			return ERROR;
		}
		hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")+CommonUtil.getRandomString(5));
		hb.setSource(5);//红包奖励
		hb.setStatus(-1);//待发放
		hb.setLimitStart(userHongbao.getLimitStart());
		hb.setLimitEnd(userHongbao.getLimitEnd());
		hb.setIsLooked(0);
		hb.setIsPc(1);
		hb.setIsApp(1);
		hb.setIsHfive(1);
		hb.setSourceString(userHongbao.getSourceString());
		userHongbaoService.save(hb);
		
		redirectUrl = "user_hongbao!list.action";
		return SUCCESS;
	}
	
	//单个红包
	@InputConfig(resultName = "error")
	public String save(){
		User u = new User();
		if(user != null && StringUtils.isNotEmpty( user.getUsername())){
			u = userService.getUserByUsername(user.getUsername());
		}
		
		UserHongbao hb = new UserHongbao();
		hb.setName("红包奖励");
		hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")+CommonUtil.getRandomString(5));
		hb.setUser(u);
		hb.setSource(5);
		hb.setStatus(-1);//待发放
		if(userHongbao.getLimitStart() != null && userHongbao.getLimitEnd() != null){
			if(userHongbao.getLimitStart() >= userHongbao.getLimitEnd()){
				addErrorMessages("项目期限起始天数必须小于截止天数!");
				return ERROR;
			}
		}else{
			addErrorMessages("请输入项目期限!");
			return ERROR;
		}
//		hb.setStartTime(new Date());
//		if(userHongbao.getEndTime()!= null){
//			if(CommonUtil.getDateSubtractDay(new Date(),userHongbao.getEndTime()) >0){
//				hb.setEndTime(userHongbao.getEndTime());
//			}else{
//				addErrorMessages("使用有效期必须大于当前日期!");
//				return ERROR;
//			}
//		}else{
//			addErrorMessages("请输入使用有效期!");
//			return ERROR;
//		}
		if(userHongbao.getExpDate() != null){
			hb.setExpDate(userHongbao.getExpDate());
		}else{
			addErrorMessages("请输入使用有效期!");
			return ERROR;
		}
		
		hb.setLimitStart(userHongbao.getLimitStart());
		hb.setLimitEnd(userHongbao.getLimitEnd());
		if("on".equals(check1)){
			hb.setIsPc(1);
		}else{
			hb.setIsPc(0);
		}
		if("on".equals(check2)){
			hb.setIsApp(1);
		}else{
			hb.setIsApp(0);
		}
		if("on".equals(check3)){
			hb.setIsHfive(1);
		}else{
			hb.setIsHfive(0);
		}
		
		if(userHongbao.getMoney() != null && userHongbao.getMoney().compareTo(new BigDecimal(0)) > 0){
			hb.setMoney(userHongbao.getMoney());
		}else{
			addErrorMessages("请输入红包金额!");
			return ERROR;
		}
		
		hb.setSourceString(userHongbao.getSourceString());
		userHongbaoService.save(hb);
		redirectUrl = "user_hongbao!list.action";
		return SUCCESS;
	}
	
	
	
	public String ajaxVerify(){
		
		userHongbao = userHongbaoService.get(id);

		Integer day_qixian = userHongbao.getExpDate();
		Date d = new Date();
		userHongbao.setStartTime(d);
		userHongbao.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d, day_qixian)));
		userHongbao.setStatus(0);
		userHongbaoService.verifyHongbao(userHongbao);
		return ajax(Status.success,"审核成功");
	}
	/**
	 * 新的查询红包列表
	 * @author zdl
	 */
	public String list(){
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(hongbaoName)){
			map.put("hongbaoName", hongbaoName);
		}
		if(StringUtils.isNotBlank(status)){
			map.put("status", status);
		}
		
		if(StringUtils.isNotBlank(username)){
			map.put("username", username);
		}
		if(beginDate != null && endDate != null){
			map.put("beginDate", CommonUtil.getDate2String(CommonUtil.date2begin(beginDate), "yyyy-MM-dd HH:mm:ss"));
			
			map.put("endDate", CommonUtil.getDate2String(CommonUtil.date2end(endDate), "yyyy-MM-dd HH:mm:ss"));
		}
		pager = userHongbaoService.userHongbaoPage(pager,map);
		return LIST;
	}
	/**
	 * 旧的查询红包明细列表
	 * @author zdl
	 */
	public String listOld(){
		
		pager.setOrder(Order.asc);
		pager.setOrderBy("status");
		pager = userHongbaoService.findPager(pager);
		return LIST;
	}
	public UserHongbao getUserHongbao() {
		return userHongbao;
	}

	public void setUserHongbao(UserHongbao userHongbao) {
		this.userHongbao = userHongbao;
	}

	public User getUser() {
		return user;
	}

	public String getCheck1() {
		return check1;
	}

	public String getCheck2() {
		return check2;
	}

	public String getCheck3() {
		return check3;
	}

	public Integer getNum1() {
		return num1;
	}

	public Integer getNum2() {
		return num2;
	}

	public Integer getNum3() {
		return num3;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setCheck1(String check1) {
		this.check1 = check1;
	}

	public void setCheck2(String check2) {
		this.check2 = check2;
	}

	public void setCheck3(String check3) {
		this.check3 = check3;
	}

	public void setNum1(Integer num1) {
		this.num1 = num1;
	}

	public void setNum2(Integer num2) {
		this.num2 = num2;
	}

	public void setNum3(Integer num3) {
		this.num3 = num3;
	}

	public String getHongbaoName() {
		return hongbaoName;
	}

	public void setHongbaoName(String hongbaoName) {
		this.hongbaoName = hongbaoName;
	}
	


	public List<String> getHbMoneyList() {
		return hbMoneyList;
	}


	public void setHbMoneyList(List<String> hbMoneyList) {
		this.hbMoneyList = hbMoneyList;
	}


	public List<Integer> getHbEndTimeList() {
		return hbEndTimeList;
	}


	public void setHbEndTimeList(List<Integer> hbEndTimeList) {
		this.hbEndTimeList = hbEndTimeList;
	}


	public List<Integer> getHbLimitStartList() {
		return hbLimitStartList;
	}


	public void setHbLimitStartList(List<Integer> hbLimitStartList) {
		this.hbLimitStartList = hbLimitStartList;
	}


	public List<Integer> getHbLimitEndList() {
		return hbLimitEndList;
	}


	public void setHbLimitEndList(List<Integer> hbLimitEndList) {
		this.hbLimitEndList = hbLimitEndList;
	}
	
	public File getFileExc() {
		return fileExc;
	}


	public void setFileExc(File fileExc) {
		this.fileExc = fileExc;
	}




	public List<String> getUserIdList() {
		return userIdList;
	}


	public void setUserIdList(List<String> userIdList) {
		this.userIdList = userIdList;
	}


	public List<Integer> getHbLimitMaxMoney() {
		return hbLimitMaxMoney;
	}


	public void setHbLimitMaxMoney(List<Integer> hbLimitMaxMoney) {
		this.hbLimitMaxMoney = hbLimitMaxMoney;
	}	
	
	/**
	 * 拆分list
	 * @param 
	 * @param size
	 * @return
	 */
	public static List<List<String>>  createList(List<String> targe,int size) {  
        List<List<String>> listArr = new ArrayList<List<String>>();  
        //获取被拆分的数组个数  
        int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;  
        for(int i=0;i<arrSize;i++) {  
            List<String>  sub = new ArrayList<String>();  
            //把指定索引数据放入到list中  
            for(int j=i*size;j<=size*(i+1)-1;j++) {  
                if(j<=targe.size()-1) {  
                    sub.add(targe.get(j));  
                }  
            }  
            listArr.add(sub);  
        }  
        return listArr;  
    }


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}  
	
	private Date beginDate;
	private Date endDate;

	public Date getBeginDate() {
		return beginDate;
	}


	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
	

}
