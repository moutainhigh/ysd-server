package net.qmdboss.service.impl;

import net.qmdboss.dao.BorrowDao;
import net.qmdboss.dao.BorrowRepaymentDetailDao;
import net.qmdboss.entity.Admin;
import net.qmdboss.entity.Borrow;
import net.qmdboss.entity.BorrowRepaymentDetail;
import net.qmdboss.service.BorrowWanderService;
import net.qmdboss.util.CommonUtil;
import net.qmdboss.util.WanderRepayPlanDetail;
import net.qmdboss.util.WanderRepayPlanEach;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 秒标Service实现类
 */
@Service("borrowWanderServiceImpl")
public class BorrowWanderServiceImpl extends BaseServiceImpl<Borrow, Integer>
		implements BorrowWanderService {

	Logger log = Logger.getLogger(BorrowWanderServiceImpl.class);

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
	public synchronized int updateBorrowWander(Borrow borParam, Integer id, Admin admin) {

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

		borrowDao.update(bor);


		borrowRepaymentDetailDao.deleteByBorrowId(bor.getId());

		// 添加流转标的分期信息
		WanderRepayPlanDetail wanderRepayPlanDetail = new WanderRepayPlanDetail(
				bor);
		for (WanderRepayPlanEach each : wanderRepayPlanDetail
				.getWanderRepayPlanEach()) {

			// 添加标的还款信息
			BorrowRepaymentDetail borRepayDetail = new BorrowRepaymentDetail();
			borRepayDetail.setStatus(0);
			borRepayDetail.setOrderNum(each.getIssue());
			borRepayDetail.setRepaymentTime(each.getRepayDate());
			borRepayDetail.setInterest(new BigDecimal(0));// 应还本金
			borRepayDetail.setCapital(new BigDecimal(0));// 应还利息
			borRepayDetail.setBorrow(bor);
			borRepayDetail.setRepaymentAccount(new BigDecimal(0));
			borRepayDetail.setRepaymentYesaccount(new BigDecimal(0));
			borrowRepaymentDetailDao.save(borRepayDetail);
		}

		return 0;
	}
}
