package com.qmd.action.index;

import com.qmd.action.base.BaseAction;
import com.qmd.mode.article.Article;
import com.qmd.mode.article.ArticleCategory;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.util.Listing;
import com.qmd.mode.util.Scrollpic;
import com.qmd.service.article.ArticleService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.borrow.BorrowTenderService;
import com.qmd.service.user.UserAccountService;
import com.qmd.service.util.ListingService;
import com.qmd.util.ConfigUtil;
import com.qmd.util.JsonUtil;
import com.qmd.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("indexAction")
@InterceptorRefs({
	@InterceptorRef(value = "qmdDefaultStack")
})
public class IndexAction extends BaseAction {
	/**
	 * 	
	 */
	private static final long serialVersionUID = -8977624206621602690L;
	Pager page;
	private String orderBy;
	private String bType;
	private List<BorrowTender> borrowTenderList;
	@Resource
	BorrowService borrowService;
	@Resource
	ListingService listingService;
	@Resource
	ArticleService articleService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	BorrowTenderService borrowTenderService;
	
//	 private List<User> rankingUserList;
	 private List<Borrow> xsBorrowPledgeList;
	 private List<Borrow> borrowPledgeList;
	 private List<Scrollpic> scrollpicList;
	 private List<Scrollpic> huobanpicList;
	 private List<Article> articleListMt;
	 
	 private List<Borrow> list1;
	 private List<Borrow> list2;
	 private List<Borrow> list3;
	 private List<Borrow> list4;
	 
	 @Action(value="/slb",results={@Result(name="success", type="freemarker",location="/content/slb.ftl")})
	 public String slb() throws Exception {
			
			return SUCCESS;
		}
	 
	
	
	@Action(value="/index",results={@Result(name="success", type="freemarker",location="/content/index.ftl"),
			@Result(name="phone", type="freemarker",location="/content/index.ftl")})// TODO 暂时都跳转到一个页面
	public String index() throws Exception {
		//page = this.getBorrowList();
		
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("limitSize", 3);
//		rankingUserList = userService.queryByRanking(map);
//		if (rankingUserList == null)
//			rankingUserList = new ArrayList<User>();
		
		String agent = getRequest().getHeader("User-Agent");
		if(StringUtils.isEmpty(agent)){
			return SUCCESS;
		}
		agent = agent.toLowerCase();
//		if( agent.indexOf("iphone")>-1  ||agent.indexOf("android")>-1  ||agent.indexOf("ipad")>-1  ||agent.indexOf("windows phone")>-1 ){
//				return "phone";
//		}
		
		scrollpicList = listingService.findScrollpicList(0);
		
		//		map.clear();
		
		/**
		page = new Pager();
		page.setPageSize(8);
		map.put("orderBy", "CAST(b.schedule as SIGNED) asc ,b.modify_date desc"); 
		int[] array = {1,3,5,7};
		map.put("array", array);
		map.put("bType", 16);
		page = borrowService.queryBorrowList(page,map);	
		xsBorrowPledgeList = (List<Borrow>)page.getResult();
		
		**/
		//首页表分类 
		list1=borrowService.selectListByRongXunFlg(1);
		list2=borrowService.selectListByRongXunFlg(2);
	    list3=borrowService.selectListByRongXunFlg(3);
		list4=borrowService.selectListByRongXunFlg(4);
		
		
		/**
		map.clear();
		page = new Pager();
		page.setPageSize(8);
		map.put("orderBy", "b.status asc,b.type desc, CAST(b.schedule as SIGNED) asc ,b.modify_date desc"); 
		//qMap.put("bType", "1");
		map.put("array", array);
		int[] types = {14,15};
		map.put("types", types);
		page = borrowService.queryBorrowList(page,map);	
		borrowPledgeList = (List<Borrow>)page.getResult();
		
		**/
		map.clear();
		map.put("start", 0);
		map.put("end", 20);
		borrowTenderList = this.borrowTenderService.queryListByMap(map);
		
		
		
		huobanpicList = listingService.findScrollpicList(1);
		
		//媒体报道
	
		//articleService.getChildArticleCategoryByIdList(ac.getId());
		String about_mt="media_report";//媒体’
		map.clear();
		map.put("sign", about_mt);
		ArticleCategory ac = articleService.getArticleCategory(map);
		map.clear();
		map.put("max", 4);
		map.put("articleCategoryId",ac.getId());
		articleListMt=articleService.getArticleByArticleCategoryId(map);
		
		return SUCCESS;
	}
	
	public Pager getPage() {
		return page;
	}

	public void setPage(Pager page) {
		this.page = page;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getbType() {
		return bType;
	}

	public void setbType(String bType) {
		this.bType = bType;
	}
	
	
	
//	public List<User> getRankingUserList() {
//		return rankingUserList;
//	}
//
//	public void setRankingUserList(List<User> rankingUserList) {
//		this.rankingUserList = rankingUserList;
//	}

	public List<Article> getArticleListMt() {
		return articleListMt;
	}

	public void setArticleListMt(List<Article> articleListMt) {
		this.articleListMt = articleListMt;
	}

	public List<ArticleCategory> getAboutArticleCategoryList(){
		String about="about_jdt";//一级目录标识 ‘关于我们’
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sign", about);
		ArticleCategory ac = articleService.getArticleCategory(map);
		return articleService.getChildArticleCategoryByIdList(ac.getId());//二级分类列表
	}

	public List<BorrowTender> getBorrowTenderList() {
		return borrowTenderList;
	}

	public void setBorrowTenderList(List<BorrowTender> borrowTenderList) {
		this.borrowTenderList = borrowTenderList;
	}

	public List<Borrow> getXsBorrowPledgeList() {
		return xsBorrowPledgeList;
	}

	public List<Borrow> getBorrowPledgeList() {
		return borrowPledgeList;
	}

	public List<Scrollpic> getScrollpicList() {
		return scrollpicList;
	}

	public void setXsBorrowPledgeList(List<Borrow> xsBorrowPledgeList) {
		this.xsBorrowPledgeList = xsBorrowPledgeList;
	}

	public void setBorrowPledgeList(List<Borrow> borrowPledgeList) {
		this.borrowPledgeList = borrowPledgeList;
	}

	public void setScrollpicList(List<Scrollpic> scrollpicList) {
		this.scrollpicList = scrollpicList;
	}

	public List<Scrollpic> getHuobanpicList() {
		return huobanpicList;
	}

	public void setHuobanpicList(List<Scrollpic> huobanpicList) {
		this.huobanpicList = huobanpicList;
	}

	public List<Borrow> getList1() {
		return list1;
	}

	public void setList1(List<Borrow> list1) {
		this.list1 = list1;
	}

	public List<Borrow> getList2() {
		return list2;
	}

	public void setList2(List<Borrow> list2) {
		this.list2 = list2;
	}

	public List<Borrow> getList3() {
		return list3;
	}

	public void setList3(List<Borrow> list3) {
		this.list3 = list3;
	}

	public List<Borrow> getList4() {
		return list4;
	}

	public void setList4(List<Borrow> list4) {
		this.list4 = list4;
	}
	
	
	// 根据key查询首页3个实时数据
	@Action(value="/index/partData")
	public String partData() {

        Map<String, Object> map = new HashMap<String, Object>();
        String totalUserNum = "0";
        String totalTenderMoney = "0";
        String totalgetMoney = "0";

        String signForTotalUserNum = "total_user_num";
        Listing listingForTotalUserNum = listingService.getListingBysign(signForTotalUserNum);
        if (listingForTotalUserNum != null && listingForTotalUserNum.getIsEnabled()) {
            String totalUserNumKeyValue = listingForTotalUserNum.getKeyValue();
            if (StringUtils.isNumeric(totalUserNumKeyValue)) {
                totalUserNum = totalUserNumKeyValue;
            }
        } else {
            map.put("key", 1);
            totalUserNum= listingService.selectSumHomeData(map);
        }

        String signForTotalTenderMoney = "total_tender_money";
        Listing listingForTotalTenderMoney = listingService.getListingBysign(signForTotalTenderMoney);
        if (listingForTotalTenderMoney != null && listingForTotalTenderMoney.getIsEnabled()) {
            String totalTenderKeyValue = listingForTotalTenderMoney.getKeyValue();
            if (StringUtils.isNumeric(totalTenderKeyValue)) {
                totalTenderMoney = totalTenderKeyValue;
            }
        } else {
            map.put("key", 2);
            totalTenderMoney=listingService.selectSumHomeData(map);
        }

        String signForGetMoney = "total_get_money";
        Listing listingForGetMoney = listingService.getListingBysign(signForGetMoney);
        if (listingForGetMoney != null && listingForGetMoney.getIsEnabled()) {
            String getMoneyKeyValue = listingForGetMoney.getKeyValue();
            if (StringUtils.isNumeric(getMoneyKeyValue)) {
                totalgetMoney = getMoneyKeyValue;
            }
        } else {
            map.put("key", 3);
            totalgetMoney= listingService.selectSumHomeData(map);
        }


        map.put("totalUserNum", totalUserNum);
        map.put("totalTenderMoney", totalTenderMoney);
        map.put("totalgetMoney", totalgetMoney);

		return ajax(JsonUtil.toJson(map));
	}
	
	
}
