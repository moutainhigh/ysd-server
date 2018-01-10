package com.qmd.service.borrow;

import com.qmd.mode.amount.Amount;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowRepaymentDetail;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.mode.util.MailRepayForInvestor;
import com.qmd.service.BaseService;
import com.qmd.util.Pager;

import java.util.List;
import java.util.Map;

public interface BorrowTenderService extends BaseService<BorrowTender,Integer>{
	public List<BorrowTender> getBorrowTenderByBorrowId(Integer borrow_id);

	/**
	 * 还款
	 * @param borrowTenderList
	 * @param borrowRepaymentDetail
	 * @param borrow
	 * 
	 * @return 0 还款成功，1资金不足，2本期已还
	 */
	public int updateBorrowDetail(List<BorrowTender> borrowTenderList,BorrowRepaymentDetail borrowRepaymentDetail,Borrow borrow,List<MailRepayForInvestor> mailList,String ip);//修改borrow_detail中的值
	
	public void updateUserAccount(UserAccount userAccount );//修改投标人账户资金
	
	public void saveUserAccountDetail(UserAccountDetail userAccountDetail);//插入资金日志
	
	public void updateBorrowwRepayDetail(BorrowRepaymentDetail borrowRepaymentDetail);

	
	/**
	 * 查询用户所有已收利息总额 
	 * @param UserId
	 * @return
	 */
	public Amount selectAllrepaymentYesinterestByUserid(Map<String,Object> map);
	
	
	/**
	 * 查询用户投标-列表
	 * @param map
	 * @return
	 */
	public List<BorrowTender> getTenderDetailByUserid(Map<String, Object> map);
	
	/**
	 * 查询用户投标-分页
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager queryUncollectedDetailList(Pager pager,Map<String,Object> map);
	
	
	/**
	 * 查询借款人 借款明细-列表
	 * @param map
	 * @return
	 */
	public List<BorrowTender> getJkmxByUserid(Map<String, Object> map);
	
	/**
	 * 查询借款人 借款明细-分页
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager queryJkmxList(Pager pager,Map<String,Object> map);
	

	/**
	 * 查询投资土豪列表
	 * @param map
	 * @return
	 */
	public List<BorrowTender> getTenderDetailByMaxMoney(Map<String, Object> map);
	
	public Pager queryPagerListByMap(Pager pager,Map<String,Object> map);
	
}
