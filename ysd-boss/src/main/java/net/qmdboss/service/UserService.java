package net.qmdboss.service;

import net.qmdboss.DTO.UserSpreadInviteAwardDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.bean.UserListBean;
import net.qmdboss.entity.Agency;
import net.qmdboss.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserService  extends BaseService<User, Integer> {
	
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
	public Pager getUserPager(Integer verifyType , Pager pager);
	
	/**
	 * 根据审核类型，获取用户分页对象
	 * @param verifyType
	 * @param pager
	 * @return
	 */
	public Pager getUserPager(Integer status,Integer verifyType , Pager pager);
	
	
	/**
	 * 修改用户信息
	 * @param user
	 * @param verifyType  【1：实名认证；2：借款人审核;3:vip审核(扣费)】
	 * @param request
	 */
	public void update(User user,Integer verifyType,HttpServletRequest request);
	
	public List queryCensusUserList(Date date,String username);
	
	public Pager queryCensusUserPage(Date date, Pager pager);
	
	

	
	/**
	 * 修改平台服务商
	 * @param user
	 * @param agency
	 */
	public void updateUserAndAgency(Integer id,User user,Agency agency);
	

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
	

	public Pager findInviteAwardPagerBySql(Pager pager,Map<String, Object> map);
	
	public List<UserSpreadInviteAwardDTO> getListInviteAwardBySql(Map<String, Object> map);
	
}
