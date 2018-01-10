package com.qmd.action.user;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.PageBean;
import com.qmd.bean.user.AwawdCashBean;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserRepaymentDetail;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.service.user.AccountCashService;
import com.qmd.service.user.UserAwardDetailService;
import com.qmd.service.user.UserRepaymentDetailService;
import com.qmd.service.user.UserService;
import com.qmd.service.util.ListingService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.JsonUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
	 
/**
 * 用户个人中心 表详情及首页数据统计
 * @author qxw
 *
 */
@Service("apiUserCenterNewAction")
@InterceptorRefs({
	@InterceptorRef(value = "apiUserInterceptor"),
	@InterceptorRef(value = "qmdDefaultStack")
})
public class ApiUserCenterNewAction extends ApiBaseAction {
		
	private static final long serialVersionUID = 6996957697026269082L;
		Logger log = Logger.getLogger(ApiUserCenterNewAction.class);
		
		private Logger moneyLog = Logger.getLogger("userWithdrawMoneyLog");
		
		private Date reTime;
		private Borrow borrow;
		private String id;
		BorrowTender borrowTender;
		List <BorrowTender> bl;
		List <UserRepaymentDetail> userRepDetailList;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
		

		@Resource
		BorrowService borrowService;
		@Resource
		UserService userService;
		@Resource
		BorrowTenderService borrowTenderService;
		@Resource
		UserRepaymentDetailService userRepaymentDetailService;
		@Resource
		AccountCashService  accountCashService;
		@Resource
		ListingService  listingService;
		@Resource
		UserAwardDetailService  userAwardDetailService;

	
		

		@Action(value = "/api/userCenterBorrowRepList", results={@Result(type="json")})
		public String tzborrowDetail() {
			
			BorrowTender bt = new BorrowTender();
		
			borrowTender = borrowTenderService.queryBorrowTenderById(Integer.valueOf(id));
			if(borrowTender==null){
				 return ajaxJson("M0010",ApiConstantUtil.M0010);
			}
			Map map=new HashMap();
			map.put("rcd", "R0001");
			map.put("rmg","");
			map.put("borrowName",borrowTender.getName());
		  
			map.put("createTime",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(borrowTender.getCreateTime()));
			map.put("account",borrowTender.getAccount());
			
			
			map.put("account",borrowTender.getAccount());
			map.put("hongbaoAmount",borrowTender.getHongbaoAmount());
			map.put("interest",borrowTender.getInterest());
			map.put("account",borrowTender.getAccount());
			
			borrow = borrowService.getBorrowById(borrowTender.getBorrowId());
			if(borrow==null){
				 return ajaxJson("M0010",ApiConstantUtil.M0010);
			}
			map.put("borrowStatus",borrow.getStatus());
			switch(Integer.valueOf(borrow.getStatus())) {  
		    case 3: map.put("borrowStatusVal","还款中"); break;  
		    case 7: map.put("borrowStatusVal","已还完");break;  
		    default: map.put("borrowStatusVal",borrowTender.getStatus());break;  
			}  
			map.put("timeLimit",borrow.getTimeLimit());
			map.put("apr",CommonUtil.setPriceScale(borrow.getVaryYearRate().multiply(new BigDecimal(100))).doubleValue());
			
			Map<String,Object> map1 = new HashMap<String, Object>();
			map1.put("userId", getLoginUser().getId());
			map1.put("borrowId", borrowTender.getBorrowId());
			userRepDetailList =userRepaymentDetailService.queryUserRepaymentDetailList(map1);
			List<Map> list=new ArrayList<Map>();
			for(UserRepaymentDetail u:userRepDetailList){
				Map m=new HashMap();
				if(u.getRepaymentTime()!=null){
					m.put("repaymentDate",new SimpleDateFormat("yyyy-MM-dd").format(u.getRepaymentTime()));
				}else{
					m.put("repaymentDate","");
				}
				m.put("waitInterest",u.getWaitInterest());
				m.put("waitAccount",u.getWaitAccount());
				list.add(m);
			}
			
			map.put("userRepDetailList",list);
	
			
			return ajax(map);
		}
	
		
		@Action(value = "/api/ajaxIndex", results={@Result(type="json")})
		public String ajaxIndex() {
			Map<String,Object> map1 = new HashMap<String, Object>();
			Integer userId=getLoginUser().getId();
			map1.put("id", userId);
			User u=userService.getUser(map1);
			if(u==null){
				return ajaxJson("M0008_14",ApiConstantUtil.M0008_14);
			}
			
			Map<String,Object> ajaxmap = new HashMap<String, Object>();
			ajaxmap.put("rcd","R0001");
			ajaxmap.put("rmg","");
			ajaxmap.put("username", u.getUsername());
			ajaxmap.put("ableMoney", u.getAbleMoney());
			ajaxmap.put("total", u.getTotal());
			ajaxmap.put("investorCollectionCapital", u.getInvestorCollectionCapital());
			ajaxmap.put("investorCollectionInterest", u.getInvestorCollectionInterest());
			ajaxmap.put("investorTotal", u.getInvestorCollectionCapital().add(u.getInvestorCollectionInterest()));
		
			ajaxmap.put("unableMoney", u.getUnableMoney());
			 Map<String, Object> mapXX = new HashMap<String, Object>();  
			 mapXX.put("key", 10);
			 mapXX.put("userId", userId);
	
			 //冻结情况
			BigDecimal b=new BigDecimal(listingService.selectSumHomeData(mapXX));
			ajaxmap.put("unableMoneyTx", b);//冻结金额
			ajaxmap.put("unableMoneyTz", u.getUnableMoney().subtract(b));//冻结的投资额
			//用户投资记录
			 mapXX.put("key", 4);
			 String  tzNum= listingService.selectSumHomeData(mapXX);
			 ajaxmap.put("tzNum",tzNum);
			 //红包可用格式
			 mapXX.put("key", 5);
			 String  hbNum=listingService.selectSumHomeData(mapXX);
			 ajaxmap.put("hbNum",hbNum);
			 //查看新增资金记录
			 mapXX.put("key", 6);
			 String  zjjlNum=listingService.selectSumHomeData(mapXX);
			 ajaxmap.put("zjjlNum",zjjlNum);
			 //查看奖励记录
			 mapXX.put("key", 7);
			 String  jljlNum=listingService.selectSumHomeData(mapXX);
			 ajaxmap.put("jljlNum",jljlNum);
			 

			return ajax(ajaxmap);
		}
		
		
		
		@Action(value = "/api/centerJL")
		public String cash() {
			initPage();
			User user = getLoginUser();


			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", user.getId());
	
			map.put("keyHb", 2);
			map.put("keyHbType", 0);
			
			
			pager = this.userAwardDetailService.queryHbDetailList(map, pager.getPageSize(), pager.getPageNumber());
			
			
			AwawdCashBean bean = new AwawdCashBean();
			
			PageBean pb = new PageBean();
			pb.setPageNumber(pager.getPageNumber());
			pb.setPageCount(pager.getPageCount());
			pb.setPageSize(pager.getPageSize());
			pb.setTotalCount(pager.getTotalCount());
			bean.setPageBean(pb);
			

			return ajax(JsonUtil.toJson(bean));
		}
		
		
		
}
