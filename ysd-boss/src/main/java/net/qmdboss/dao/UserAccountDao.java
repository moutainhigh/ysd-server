package net.qmdboss.dao;

import net.qmdboss.DTO.UserAccountDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.entity.User;
import net.qmdboss.entity.UserAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Dao接口 - 用户账户类
 * @author	zhanf
 *
 */
public interface UserAccountDao extends BaseDao<UserAccount, Integer> {
	/**
	 * 根据不同类型查询账户数据【Xsf】
	 * @param pager
	 * @param type
	 * @param amount
	 * @return
	 */
	public Pager findPagerByMoney(Pager pager,String type,String amount);
	
	/**
	 * 根据不同类型查询账户数据
	 * @param username
	 * @param type
	 * @param amount
	 * @return
	 */
	public List<User> findAccountList(String username,String type,String amount);
	

	/**
	 * 根据用户条件查询用户账户信息
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByUser(Pager pager,Map<String,Object> map);
	
	/**
	 * 根据用户条件查询用户账户信息【导出数据用】
	 * @param pager
	 * @param map
	 * @return
	 */
	public List<User> getPagerByUser(Map<String,Object> map);
	public List<UserAccountDTO> findAccountDTOByUser(Map<String,Object> map);//原生sql导出
	
	/**
	 * 所有账户的可用金额总和
	 * @return
	 */
	public BigDecimal getAbleMoneyAll(Integer userTypeId);
	
	/**
	 * 所有账户金额总额
	 * @param userTypeId 用户类型
	 * @param sumName 统计字段
	 * @return
	 */
	public BigDecimal getUserAccountSum(Integer userTypeId,String sumName);
	
	public UserAccount loadLockTable(User user);
}
