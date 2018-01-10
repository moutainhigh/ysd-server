package net.qmdboss.service.impl;

import net.qmdboss.dao.BorrowDao;
import net.qmdboss.dao.BorrowRepaymentDetailDao;
import net.qmdboss.entity.Admin;
import net.qmdboss.entity.Borrow;
import net.qmdboss.entity.BorrowRepaymentDetail;
import net.qmdboss.service.BorrowFlowService;
import net.qmdboss.util.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 *卡趣流转标Service实现类
 */
@Service("borrowFlowServiceImpl")
public class BorrowFlowServiceImpl extends BaseServiceImpl<Borrow, Integer>
		implements BorrowFlowService {

	Logger log = Logger.getLogger(BorrowFlowServiceImpl.class);

	@Resource(name = "borrowDaoImpl")
	private BorrowDao borrowDao;

//	@Resource(name = "userAccountDaoImpl")
//	private UserAccountDao userAccountDao;

//	@Resource(name = "userAccountDetailDaoImpl")
//	private UserAccountDetailDao userAccountDetailDao;

	@Resource(name = "borrowRepaymentDetailDaoImpl")
	private BorrowRepaymentDetailDao borrowRepaymentDetailDao;

//	@Resource(name = "userRepaymentDetailDaoImpl")
//	private UserRepaymentDetailDao userRepaymentDetailDao;

	@Resource(name = "borrowDaoImpl")
	public void setBaseDao(BorrowDao borrowDao) {
		super.setBaseDao(borrowDao);
	}

	/**
	 * 流转标审核通过
	 */
	@Override
	public synchronized int updateBorrowFlow(Borrow borParam, Integer id, Admin admin) {

		// 取得标的信息
		Borrow bor = borrowDao.loadLock(id);

		// 判断标的状态
		if (bor.getStatus() != 0) {
			return 1;
		}

		// 取得操作者信息（管理员）
		//Integer adminId = admin.getId();
		
		bor.setStatus(borParam.getStatus());
		bor.setVerifyRemark(borParam.getVerifyRemark());
		String admin1 = String .valueOf(admin.getId());
		bor.setVerifyUser(admin1);
		bor.setAccountYes(bor.getAccount());
		bor.setBalance(String.valueOf(bor.getAccount().doubleValue()));
		bor.setVerifyTime(new Date());
		bor.setSchedule("0");
		
		bor.setRepamyentRemark(borParam.getVerifyRemark());
		bor.setRepaymentUser(admin.getId());
		bor.setSuccessTime(new Date());
		bor.setRepaymentAccount(new BigDecimal(0));
		
		bor.setFinalRepayDate(CommonUtil.getDateAfter(bor.getVerifyTime(),Integer.parseInt(bor.getTimeLimit())));

		int divides = 0;
		
		if  ("1".equals(bor.getStyle())) {	//到期还本息
		
			int t = Integer.parseInt(bor.getValidTime());
	
			// 添加流转标的分期信息
			for (int i =0;i<t;i++) {
				
				int repayTime = i + Integer.parseInt(bor.getTimeLimit()) ;
				
				Date repayDate = CommonUtil.getDateAfter(bor.getVerifyTime(), repayTime);
				
				String datestr = CommonUtil.getDate2String(repayDate,"yyyyMMdd");
	
				// 添加标的还款信息
				BorrowRepaymentDetail borRepayDetail = new BorrowRepaymentDetail();
				borRepayDetail.setStatus(0);
				borRepayDetail.setOrderNum(i);
				borRepayDetail.setRepaymentTime(repayDate);
				borRepayDetail.setInterest(new BigDecimal(0));// 应还本金
				borRepayDetail.setCapital(new BigDecimal(0));// 应还利息
				borRepayDetail.setBorrow(bor);
				borRepayDetail.setRepaymentAccount(new BigDecimal(0));
				borRepayDetail.setRepaymentYesaccount(new BigDecimal(0));
				borRepayDetail.setRepaymentDateInt(Integer.parseInt(datestr));
				borrowRepaymentDetailDao.save(borRepayDetail);
				divides++;
			}
		} else if("2".equals(bor.getStyle())) {
			int validTime = Integer.parseInt(bor.getValidTime());
			int repaymentPeriod = bor.getRepaymentPeriod();
			int timeLimit = Integer.parseInt(bor.getTimeLimit());
			
			if (validTime < repaymentPeriod) {
				
				int on = 0;
				for (int p =1;p<=timeLimit/repaymentPeriod;p++) {
					
					int d = p * repaymentPeriod;
					
					for (int i =0;i<validTime;i++) {
						
						int repayTime = i + d;
						
						Date repayDate = CommonUtil.getDateAfter(bor.getVerifyTime(), repayTime);
						String datestr = CommonUtil.getDate2String(repayDate,"yyyyMMdd");
						// 添加标的还款信息
						BorrowRepaymentDetail borRepayDetail = new BorrowRepaymentDetail();
						borRepayDetail.setStatus(0);
						borRepayDetail.setOrderNum(on);
						borRepayDetail.setRepaymentTime(repayDate);
						borRepayDetail.setInterest(new BigDecimal(0));// 应还本金
						borRepayDetail.setCapital(new BigDecimal(0));// 应还利息
						borRepayDetail.setBorrow(bor);
						borRepayDetail.setRepaymentAccount(new BigDecimal(0));
						borRepayDetail.setRepaymentYesaccount(new BigDecimal(0));
						borRepayDetail.setRepaymentDateInt(Integer.parseInt(datestr));
						borrowRepaymentDetailDao.save(borRepayDetail);
						divides++;
						on++;
					}
				}
			} else {
				
				for (int i =0;i<(timeLimit+(validTime - repaymentPeriod));i++) {
					int repayTime = i + repaymentPeriod;
					
					Date repayDate = CommonUtil.getDateAfter(bor.getVerifyTime(), repayTime);
					String datestr = CommonUtil.getDate2String(repayDate,"yyyyMMdd");
					// 添加标的还款信息
					BorrowRepaymentDetail borRepayDetail = new BorrowRepaymentDetail();
					borRepayDetail.setStatus(0);
					borRepayDetail.setOrderNum(i);
					borRepayDetail.setRepaymentTime(repayDate);
					borRepayDetail.setInterest(new BigDecimal(0));// 应还本金
					borRepayDetail.setCapital(new BigDecimal(0));// 应还利息
					borRepayDetail.setBorrow(bor);
					borRepayDetail.setRepaymentAccount(new BigDecimal(0));
					borRepayDetail.setRepaymentYesaccount(new BigDecimal(0));
					borRepayDetail.setRepaymentDateInt(Integer.parseInt(datestr));
					borrowRepaymentDetailDao.save(borRepayDetail);
					divides++;
				}
				
			}
		}
		bor.setDivides(divides);
		
		//有效到期时间
		int borrowValidTime = Integer.parseInt(bor.getValidTime());
		Date endTime =CommonUtil.getDateAfter( bor.getVerifyTime(),borrowValidTime-1);
		
		bor.setEndTime(CommonUtil.date2end(endTime));
		
		borrowDao.update(bor);

		return 0;
	}
}
