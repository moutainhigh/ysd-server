package net.qmdboss.service;

import net.qmdboss.DTO.UserAccountDetailDTO;
import net.qmdboss.bean.AccountDetail;
import net.qmdboss.bean.Pager;
import net.qmdboss.entity.UserAccountDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserAccountDetailService extends BaseService<UserAccountDetail,Integer> {
	
	/**
	 * 资金变动操作-充值操作【Xsf】
	 * @param userAccountDetail
	 * @return
	 */
//	public Integer saveUserAccountDetailByRecharge(UserAccountDetail userAccountDetail);
	
	
	/**
	 * 资金变动操作-【type:0 扣费操作；type:1添加奖励操作】【Xsf】
	 * @param userAccountDetail
	 * @return
	 */
	public Integer saveUserAccountDetailByDebits(UserAccountDetail userAccountDetail,String type);
	
	/**
	 * 修改资金记录
	 * @param userAccountDetail
	 * @return
	 */
	public Integer saveUserAccountDetail(UserAccountDetail userAccountDetail);
	
	/**
	 * 查询资金使用记录【Xsf】
	 * @param pager 
	 * @param type 资金使用类型
	 * @param startDate 查询起始时间
	 * @param endDate 查询截止时间
	 * @return String type, Date startDate , Date endDate
	 */
	public Pager findPager(Pager pager,Map<String ,Object> map);
	
	
	/**
	 * 根据账目明细查询总金额
	 * @param type 账目明细
	 * @param stareDate
	 * @param endDate
	 * @return
	 */
	public BigDecimal getFeeByType(String type,Date stareDate, Date endDate);
	
	/**
	 * 根据分类获取总和
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<AccountDetail> getUserAccountDetailTotalByType(String type,Date startDate,Date endDate);
	
	/**
	 * 资金明细
	 * @param map
	 * @return
	 */
	public List<UserAccountDetail> getAccountDetailList(Map<String ,Object> map);

	public Pager findPagerByHql(Pager pager, Map<String, Object> map);
	/**
	 * 资金明细 优化
	 * @param map
	 * @return
	 */
	public List<UserAccountDetailDTO> getAccountDetailListByHql(Map<String, Object> map);
	
}
