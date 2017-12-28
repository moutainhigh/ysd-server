package net.qmdboss.service;


import net.qmdboss.DTO.UserAccountCashDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.entity.AccountCash;
import net.qmdboss.entity.Admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;




public interface AccountCashService extends BaseService<AccountCash, Integer> {
	
	/**
	 * 查询提现各状态的列表
	 * @param accountCash
	 * @param pager
	 * @return
	 */
	public Pager getCashPage( AccountCash accountCash ,Pager pager,Date startDate,Date endDate);
	
	
	public List<AccountCash> getAccountCashList(AccountCash accountCash ,Date startDate,Date endDate);
	
	public List<AccountCash>queryAccountCashList(AccountCash accountCash );
	
	/**
	 * 审核提现记录
	 * @param accountCash
	 * @param id
	 * @param admin
	 * @param fee
	 * @return 0 审核不通过，1通过，2状态不对，3处理中
	 */
	public int updateCashMoney(AccountCash accountCash, Integer id,Admin admin,StringBuffer logInfoStringBuffer);
	public boolean updateCashMoney(AccountCash accountCash, Integer id,Admin admin,BigDecimal fee);
	public Integer queryAccountCashListCount(Map<String, Object> map) ;

	/**
	 * 提现记录导出 qiu
	 * @param accountCash
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<UserAccountCashDTO> getAccountCashDTOList(AccountCash accountCash, Date startDate, Date endDate);

}
