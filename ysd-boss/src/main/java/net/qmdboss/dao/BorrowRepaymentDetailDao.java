package net.qmdboss.dao;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.BorrowRepaymentDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface BorrowRepaymentDetailDao extends BaseDao<BorrowRepaymentDetail, Integer> {

	/**
	 * 查询逾期标
	 * @param pager
	 * @return
	 */
	public Pager findLate(Pager pager);
	
	public Pager findRepaymentNot(Pager pager);
	
	public Pager findRepayment(Pager pager,Integer status);
	
	
	
	
	/**
	 * 查询已还总金额-未还总金额-xsf
	 * @param status 还款状态【0：未还; 1：已还】
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public BigDecimal getRepaymentAccountTotal(Integer status,Date startDate,Date endDate);
	
	/**
	 * 按标ID查询标还款列表
	 * @param borrowId
	 * @return
	 */
	List<BorrowRepaymentDetail> getByBorrowId(Integer borrowId);

	void deleteByBorrowId(Integer borrowId);
}
