package com.qmd.action.index;

import com.qmd.action.base.BaseAction;
import com.qmd.bean.index.IndexBean;
import com.qmd.bean.index.IndexBorrow;
import com.qmd.bean.index.IndexImageItem;
import com.qmd.bean.index.IndexTypeItem;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.util.Scrollpic;
import com.qmd.mode.util.Setting;
import com.qmd.service.article.ArticleService;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.util.ListingService;
import com.qmd.util.CommonUtil;
import com.qmd.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InterceptorRefs({ @InterceptorRef(value = "qmdDefaultStack") })
@Service("indexAction")
public class IndexAction extends BaseAction {

	private static final long serialVersionUID = 3401759600836783053L;
	@Resource
	BorrowService borrowService;
	@Resource
	ListingService listingService;
	@Resource
	ArticleService articleService;

	private int way;
	private IndexBean indexBean;

	@Action(value = "/index", results = { @Result(name = "success", location = "/content/index.ftl", type = "freemarker") })
	public String apiIndex() {

		indexBean = new IndexBean();

		// 滚动图片
		List<IndexImageItem> indexImageItemList = new ArrayList<IndexImageItem>();
		IndexImageItem indexImageItem = null;
		Map<String, Object> mapp = new HashMap<String, Object>();
		mapp.put("type", 2);

		List<Scrollpic> scrollpicList = listingService.findScrollpicListMap(mapp);
		for (Scrollpic s : scrollpicList) {
			indexImageItem = new IndexImageItem();
			indexImageItem.setImageUrl(s.getImg());
			indexImageItem.setType(1); // 类型：0无1网页2项目类型3文章id
			indexImageItem.setTypeTarget(s.getUrl()); // 类型目标：网页url（类型1时启用）类型2时，为项目类型。类型3时，为文章id
			indexImageItemList.add(indexImageItem);
		}
		indexBean.setIndexImageItemList(indexImageItemList);

		// 推荐项目
		IndexBorrow indexBorrow = new IndexBorrow();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderBy", "b.show_top desc ,b.show_sort desc,CAST(b.schedule as SIGNED) asc,b.create_date desc");

		int[] types = { 14, 15, 16 };
		map.put("types", types);

		int[] array = { 1, 3, 5, 7 };
		map.put("array", array);
		if (pager == null) {
			pager = new Pager();
		}
		pager.setPageSize(1);
		pager = borrowService.queryBorrowList(pager, map);
		List<Borrow> borrowList = (List<Borrow>) pager.getResult();

		if (borrowList != null && borrowList.size() > 0) {
			Borrow borrow = borrowList.get(0);

			indexBorrow.setId(borrow.getId());
			indexBorrow.setName(borrow.getName());
			indexBorrow.setType(borrow.getType());
			indexBorrow.setBusinessType(borrow.getIsVouch());
			indexBorrow.setBusinessCode(borrow.getBusinessCode());// 项目编号
			indexBorrow.setTimeLimit(borrow.getTimeLimit());// 借款天数
			indexBorrow.setAccount(borrow.getAccount());// 借款总金额
			// indexBorrow.setApr(borrow.getApr());// 年利率
			indexBorrow.setApr(CommonUtil.setPriceScale(borrow.getVaryYearRate().multiply(new BigDecimal(100))).doubleValue());// 年利率
			indexBorrow.setAward(borrow.getAward());// 投标奖励方式
			indexBorrow.setFunds(borrow.getFunds());// 投标金额比例奖励
			indexBorrow.setStatus(borrow.getStatus());// 状态码0-发表未审核；1-审核通过；2-审核未通过；3-满标审核通过；4-满标审核未通过；5-等待满标审核；6-过期或撤回；7-已还完;8-删除状态
			indexBorrow.setShowBorrowStatus(borrow.getShowBorrowStatus());// 状态名称
			indexBorrow.setSchedule(borrow.getSchedule()); // 投标的百分比
			indexBorrow.setTenderSize(StringUtils.isEmpty(borrow.getTenderTimes()) ? 0 : Integer.parseInt(borrow.getTenderTimes()));// 已投次数
			indexBorrow.setTenderSubject(0);// 新客标记
			indexBorrow.setVerifyTime(borrow.getVerifyTime());// 审核时间
			indexBorrow.setShowBorrowType(borrow.getType());// 类型名称
			indexBorrow.setLowestAccount(borrow.getLowestAccount());
			indexBorrow.setBaseApr(CommonUtil.setPriceScale(borrow.getBaseApr().multiply(new BigDecimal(100))));
			indexBorrow.setAwardApr(CommonUtil.setPriceScale(borrow.getAwardApr().multiply(new BigDecimal(100))));
			
		}
		indexBean.setIndexBorrow(indexBorrow);

		// 项目类型
		List<IndexTypeItem> indexTypeItemList = new ArrayList<IndexTypeItem>();
		IndexTypeItem indexTypeItem = new IndexTypeItem();
		indexTypeItem.setType("14");
		indexTypeItem.setShowBorrowType("天标");
		indexTypeItem.setTypeDescribe("描述");
		indexTypeItem.setTypeImage("");
		indexTypeItemList.add(indexTypeItem);

		indexTypeItem = new IndexTypeItem();
		indexTypeItem.setType("15");
		indexTypeItem.setShowBorrowType("月标");
		indexTypeItem.setTypeDescribe("描述");
		indexTypeItem.setTypeImage("");
		indexTypeItemList.add(indexTypeItem);

		indexTypeItem = new IndexTypeItem();
		indexTypeItem.setType("16");
		indexTypeItem.setShowBorrowType("新手标");
		indexTypeItem.setTypeDescribe("描述");
		indexTypeItem.setTypeImage("");
		indexTypeItemList.add(indexTypeItem);

		indexBean.setIndexTypeItemList(indexTypeItemList);

		map = new HashMap<String, Object>();
		map.put("sign", "site_notice");
		Integer n = articleService.getArticleBySignCount(map);
		indexBean.setNotReadNum(n == null ? 0 : n);// 未读

		indexBean.setUserFlg(0);//
		if (getLoginUser() != null) {
			indexBean.setUserFlg(1);//
		}
		return SUCCESS;

	}

	private Setting setting;
	/**
	 * 更多
	 * 
	 * @return
	 */
	@Action(value = "/moreTo", results = { @Result(name = "success", location = "/content/article/more.ftl", type = "freemarker") })
	public String moreTo() {
		setting = listingService.getSetting();
		return SUCCESS;
	}

	public int getWay() {
		return way;
	}

	public void setWay(int way) {
		this.way = way;
	}

	public IndexBean getIndexBean() {
		return indexBean;
	}

	public void setIndexBean(IndexBean indexBean) {
		this.indexBean = indexBean;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

}
