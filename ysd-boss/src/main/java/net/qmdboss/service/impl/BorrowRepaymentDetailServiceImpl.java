package net.qmdboss.service.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.BorrowRepaymentDetailDao;
import net.qmdboss.entity.BorrowRepaymentDetail;
import net.qmdboss.service.BorrowRepaymentDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 发表待审Service实现类
 * @author Administrator
 *
 */
@Service("borrowRepaymentDetailServiceImpl")
public class BorrowRepaymentDetailServiceImpl extends BaseServiceImpl<BorrowRepaymentDetail, Integer>
		implements BorrowRepaymentDetailService {

	@Resource(name = "borrowRepaymentDetailDaoImpl")
	private BorrowRepaymentDetailDao  borrowRepaymentDetailDao;
	@Resource(name = "borrowRepaymentDetailDaoImpl")
	public void setBaseDao(BorrowRepaymentDetailDao borrowRepaymentDetailDao) {
		super.setBaseDao(borrowRepaymentDetailDao);
	}
	
	
	public Pager findLate(Pager pager){
		return borrowRepaymentDetailDao.findLate(pager);
	}
	
	public Pager findRepaymentNot(Pager pager) {
		return borrowRepaymentDetailDao.findRepaymentNot(pager);
	}
	
	public Pager findRepayment(Pager pager,Integer status) {
		return borrowRepaymentDetailDao.findRepayment(pager,status);
	}
	
	public BigDecimal getRepaymentAccountTotal(Integer status,Date startDate,Date endDate){
		BigDecimal ret = borrowRepaymentDetailDao.getRepaymentAccountTotal(status, startDate, endDate);
		if (ret==null) {
			ret = BigDecimal.valueOf(0);
		}
		return ret;
	}


	@Override
	public List<BorrowRepaymentDetail> getByBorrowId(Integer borrowId) {
		// TODO Auto-generated method stub
		return borrowRepaymentDetailDao.getByBorrowId(borrowId);
	}
	
	
	

}
