package com.qmd.service.borrow;

import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.service.BaseService;
import com.qmd.util.Pager;

import java.util.List;
import java.util.Map;

public interface BorrowService extends BaseService<BorrowTender,Integer> {
	public Pager queryBorrowList(Pager page,Map<String,Object> qMap);
	/**
	 * 取得还款未完成的标(质押,流转)
	 * @param page
	 * @param qMap
	 * @return
	 */
	public Pager queryBorrowListForRepay(Pager page,Map<String,Object> qMap);
	
	public Pager queryUserTenderBorrowList(Pager page,Map<String,Object> qMap);
	public Borrow getBorrowById(Integer id);
	public List<Borrow> queryUserBorrowList(Map<String, Object> map);
	
	public Integer addBorrow(Borrow borrow);//添加borrrow值进数据库
	

	/**
	 * 用户投标
	 *  @return 0 投标成功,1已满标,2可用余额不足,3续投余额不足
	 */
//	public int borrowInvestDo(User user,BorrowTender borrowTender,String ip);//用户投标
	
//	public int monthBorrowInvestDo(User user,BorrowTender borrowTender,String ip,Integer borrowInvestReadyId);//用户投标
	
	public Double queryBorrowAccountSum(Map<String,Object> map);
	
/*	
	public void miaoBorrowRepayment(Borrow borrow);//秒标自动审核和还款
*/	
	/**
	 * 修改未审核发布的标信息
	 * @param borrow
	 */
	public void updateBorrowMess(Borrow borrow);
	
	/**
	 * 删除标
	 * @param id
	 */
//	public void delectBorrow(Integer id);
	
	/**
	 * 查询标
	 * @param map
	 * @return
	 */
//	public List<ProductBean> getProductBeanList(Map<String,Object> map);
	
	public void updateBorrow(Borrow borrow);
	
	/**
	 * 申请展期项目
	 * @param borrow
	 */
	public void addZqBorrow(Borrow borrow);
	
	/**
	 * 展期撤回
	 * @param borrow
	 */
	public int recallZq(Borrow borrow);
	
	
	/**
	 * 获取预约信息
	 * @param page
	 * @param qMap
	 * @return
	 */
	public Pager queryReservationList(Pager page,Map<String,Object> qMap);
	
	public Pager queryBorrowListCopy(Pager page,Map<String,Object> qMap) ;
	
	public Pager queryBorrowList2(Pager page,Map<String,Object> qMap);
	
}
