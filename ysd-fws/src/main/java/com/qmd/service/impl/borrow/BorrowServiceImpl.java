package com.qmd.service.impl.borrow;

import com.qmd.dao.borrow.BorrowDaoService;
import com.qmd.dao.borrow.BorrowInvestReadyDao;
import com.qmd.dao.borrow.BorrowRepaymentDetailDaoService;
import com.qmd.dao.borrow.BorrowTenderDaoService;
import com.qmd.dao.user.UserAccountDao;
import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.dao.user.UserRepaymentDetailDao;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.service.borrow.BorrowService;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.util.InterestCalUtil;
import com.qmd.util.Pager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * BorrowServiceImpl 标方法的Service实现类
 * @author jafen
 * @time 2012-10-24
 *
 */
@Repository("borrowService")
public class BorrowServiceImpl extends BaseServiceImpl<BorrowTender,Integer> implements BorrowService {
	Logger log = Logger.getLogger(BorrowServiceImpl.class);
	
	private Logger logPledge = Logger.getLogger("userPledgeLog");
	
	@Resource
	BorrowDaoService borrowDaoService;
	@Resource
	UserAccountDao userAccountDao;
	@Resource
	BorrowTenderDaoService borrowTenderDaoService;
	@Resource
	UserAccountDetailDao userAccountDetailDao;
	@Resource
	ListingDao 	listingDao;
	@Resource
	BorrowRepaymentDetailDaoService borrowRepaymentDetailDao;
	@Resource
	BorrowRepaymentDetailDaoService  borrowRepaymentDetailDaoService;
	@Resource
	InterestCalUtil interestCalUtil;
	@Resource
	UserRepaymentDetailDao userRepaymentDetailDao;
	@Resource
	BorrowInvestReadyDao borrowInvestReadyDao;
	
	public BorrowTenderDaoService getBorrowTenderDaoService() {
		return borrowTenderDaoService;
	}
	public void setBorrowTenderDaoService(
			BorrowTenderDaoService borrowTenderDaoService) {
		this.borrowTenderDaoService = borrowTenderDaoService;
	}
	public BorrowRepaymentDetailDaoService getBorrowRepaymentDetailDao() {
		return borrowRepaymentDetailDao;
	}
	public void setBorrowRepaymentDetailDao(
			BorrowRepaymentDetailDaoService borrowRepaymentDetailDao) {
		this.borrowRepaymentDetailDao = borrowRepaymentDetailDao;
	}
	public UserAccountDetailDao getUserAccountDetailDao() {
		return userAccountDetailDao;
	}
	public void setUserAccountDetailDao(UserAccountDetailDao userAccountDetailDao) {
		this.userAccountDetailDao = userAccountDetailDao;
	}
	public BorrowRepaymentDetailDaoService getBorrowRepaymentDetailDaoService() {
		return borrowRepaymentDetailDaoService;
	}
	public void setBorrowRepaymentDetailDaoService(
			BorrowRepaymentDetailDaoService borrowRepaymentDetailDaoService) {
		this.borrowRepaymentDetailDaoService = borrowRepaymentDetailDaoService;
	}
	public ListingDao getListingDao() {
		return listingDao;
	}
	public void setListingDao(ListingDao listingDao) {
		this.listingDao = listingDao;
	}
	public UserAccountDao getUserAccountDao() {
		return userAccountDao;
	}
	public void setUserAccountDao(UserAccountDao userAccountDao) {
		this.userAccountDao = userAccountDao;
	}
	public BorrowDaoService getBorrowDaoService() {
		return borrowDaoService;
	}
	public void setBorrowDaoService(BorrowDaoService borrowDaoService) {
		this.borrowDaoService = borrowDaoService;
	}
	
	/**
	 * queryBorrowList 分页获取标的列表
	 * return Pager
	 */
	@Override
	public Pager queryBorrowList(Pager page,Map<String,Object> qMap) {
		// TODO Auto-generated method stub
		return this.borrowDaoService.queryBorrowList(page,qMap);
	}
	@Override
	public Pager queryBorrowListForRepay(Pager page,Map<String,Object> qMap) {
		// TODO Auto-generated method stub
		return this.borrowDaoService.queryBorrowListForRepay(page,qMap);
	}
	@Override
	public Pager queryUserTenderBorrowList(Pager page,Map<String,Object> qMap) {
		// TODO Auto-generated method stub
		return this.borrowDaoService.queryUserTenderBorrowList(page,qMap);
	}
	@Override
	public Borrow getBorrowById(Integer id) {
		// TODO Auto-generated method stub
		Borrow borrow = this.borrowDaoService.getBorrowById(id);
		return borrow;
	}
	@Override
	public List<Borrow> queryUserBorrowList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getBorrowDaoService().queryUserBorrowList(map);
	}
	public void doSomething() {
	    // something that should execute on weekdays only
		//System.out.println("a start at :" + System.currentTimeMillis());
	}
	public void doSomethings() {
	    // something that should execute on weekdays only
		//System.out.println("b start at :" + System.currentTimeMillis());
	}
	
	@Override
	public Double queryBorrowAccountSum(Map<String,Object> map) {
		return borrowDaoService.queryBorrowAccountSum(map);
	}
	
	/**
	 * addBorrow 添加标
	 * return 
	 */
	@Override
	public Integer addBorrow(Borrow borrow) {
		// TODO Auto-generated method stub
		return this.borrowDaoService.add(borrow);
	}
	

	/**
	 * 修改未审核的标
	 */
	@Override
	public void updateBorrowMess(Borrow borrow) {
		// TODO Auto-generated method stub
		this.borrowDaoService.update(borrow);
	}
	
	/**
	 * 修改标
	 */
	@Override
	public void updateBorrow(Borrow borrow) {
		this.borrowDaoService.updateBorrow(borrow);
	}
	
	public void addZqBorrow(Borrow borrow){
		//添加新（展期）项目
		borrowDaoService.add(borrow);
		
		Borrow b = borrowDaoService.getBorrowById(1);
//		b.setDeferId(borrow.getId());
		borrowDaoService.update(b);
		
	}

	public int recallZq(Borrow borrow){
		borrow.setStatus(6);
//		borrow.setDeferStatus(3);
		borrowDaoService.update(borrow);
		
		Borrow b = borrowDaoService.getBorrowById(1);//原项目信息
//		b.setDeferId(null);
		borrowDaoService.update(b);
		return 0;
	}
	
	

	/**
	 * 获取预约信息
	 * @param page
	 * @param qMap
	 * @return
	 */
	public Pager queryReservationList(Pager page,Map<String,Object> qMap){
		return borrowDaoService.queryReservationList(page, qMap);
	}
	
	@Override
	public Pager queryBorrowListCopy(Pager page,Map<String,Object> qMap) {
		// TODO Auto-generated method stub
		return this.borrowDaoService.queryBorrowListCopy(page,qMap);
	}
	@Override
	public Pager queryBorrowList2(Pager page,Map<String,Object> qMap) {
		// TODO Auto-generated method stub
		return this.borrowDaoService.queryBorrowList2(page,qMap);
	}
	
	
	/**
	 * 删除标
	 */
//	@Override
//	public void delectBorrow(Integer id) {
//		// TODO Auto-generated method stub
//		this.borrowDaoService.delect(id);
//	}

//	public List<ProductBean> getProductBeanList(Map<String,Object> map){
//		return borrowDaoService.getProductBeanList(map);
//	}
	
}
