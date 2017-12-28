package net.qmdboss.dao;

import net.qmdboss.DTO.UserAccountCashDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.entity.AccountCash;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AccountCashDao extends BaseDao<AccountCash, Integer> {

	/**
	 * 根据accountCash和Pager对象,获取此分类下的标分页对象（）
	 * 
	 * @param borrow    标属性          
	 * @param pager 分页对象
	 * @return Pager
	 */
	public Pager getCashPage(AccountCash accountCash, Pager pager,Date startDate,Date endDate);
	
	/**
	 * 获取提现记录列表
	 * @param accountCash 查询对象条件
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public List<AccountCash> getCashList(AccountCash accountCash,Date startDate, Date endDate);
	
	public List<AccountCash>queryAccountCashList(AccountCash accountCash);
	
	public Integer queryAccountCashListCount(Map<String, Object> map);

	public List<UserAccountCashDTO> getCashDTOList(AccountCash accountCash,Date startDate, Date endDate);
}
