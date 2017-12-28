package net.qmdboss.dao;

import net.qmdboss.DTO.UserDTO;
import net.qmdboss.DTO.UserSpreadInviteAwardDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.bean.UserListBean;
import net.qmdboss.entity.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserDao  extends BaseDao<User, Integer>{
	/**
	 * 根据用户名获取会员对象,若会员不存在,则返回null（不区分大小写）
	 * 
	 */
	public User getUserByUsername(String username);
	
	/**
	 * 根据邮箱获取会员对象,若会员不存在,则返回null（不区分大小写）
	 * 
	 */
	public User getUserByEmail(String email,Integer id);
	
	/**
	 * 根据手机号获取会员对象,若会员不存在,则返回null（不区分大小写）
	 * 
	 */
	public User getUserByPhone(String phone,Integer id);
	
	/**
	 * 根据手机号获取会员对象,若会员不存在,则返回null（不区分大小写）
	 * 
	 */
	public List<User> getByPhone(String phone);
	
	
	/**
	 * 根据审核类型，获取用户分页对象
	 * @param verifyType
	 * @param pager
	 * @return
	 */
	public Pager getUserPager(Integer status, Integer verifyType , Pager pager);
	
	/**
	 * 根据审核类型，获取用户分页对象
	 * @param verifyType
	 * @param pager
	 * @return
	 */
	public Pager getUserPager(Integer verifyType , Pager pager);
	
	public List<Object> getViewTest(int timelimit,BigDecimal rateYear,BigDecimal reward);
	
	public List queryCensusUserList(Integer dateInt, Date dateEnd,String username);
	public Pager queryCensusUserPage(Integer dateInt, Date dateEnd, Pager pager);
	

	/**
	 * 根据服务商信息查询用户
	 * @param map
	 * @return
	 */
	public User getUserByAgency(Integer agencyid,Integer agencytype);
	
	public Pager findPagerByMap(Pager pager, Map<String, Object> map);
	

	public Pager findPager(Pager pager,Map<String,Object> map);
	

	public Pager findPagerByHsql(Pager pager,Map<String, Object> map);
	
	public List<UserListBean> getListByHsql(Map<String, Object> map);
	
	
	public List<UserDTO> selectListByIdsOrUsername(Map<String, Object> map);
	
	
	public String savePL(List<String> userIdList,List<String> hbMoneyList ,List<Integer> hbEndTimeList ,List<Integer> hbLimitMaxMoney ,List<Integer> hbLimitStartList,List<Integer> hbLimitEndList,String name);

	public List<UserSpreadInviteAwardDTO> getListInviteAwardBySql(Map<String, Object> map);

	public Pager findInviteAwardPagerBySql(Pager pager, Map<String, Object> map);
	
	
	
}
