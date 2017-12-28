package com.qmd.service.user;

import com.qmd.mode.admin.Admin;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.service.BaseService;
import com.qmd.util.Pager;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service接口 - 用户基本信息
 */
public interface UserService  extends BaseService<User, Integer>{
	/**
	 * 根据用户参数查询用户信息
	 * @param map
	 * @return
	 */
	public User getUser(Map<String,Object> map);
	
	public List<User> getUserList(Map<String,Object> map);
	
	
	public Pager queryUser(Pager pager,Map<String,Object> map);


	/**
	 * 生成找回Key
	 * 
	 * @return 密码找回Key
	 */
	public String buildRecoverKey();
	
	/**
	 * 根据找回Key获取生成日期
	 * 
	 * @return 生成日期
	 */
	public Date getRecoverKeyBuildDate(String RecoverKey);
	
	/**
	 * 注册添加用户信息
	 * @param user
	 */
	public int addUser(User user);
	
	/**
	 * 用户登录成功修改用户信息
	 * @param user
	 */
	public void updateUserByLoginSuccess(User user);
	
	/**
	 * 修改用户个人资料
	 * @param user
	 */
	public void updateProfile(User user);
	
	/**
	 * 会员申请实名认证
	 * @param user
	 */
	public void updateRealName(User user);
	
	/**
	 * 会员上传头像
	 * @param user
	 */
	public void updateLitpic(User user);


	/**
	 * 会员申请手机认证
	 * @param user
	 */
	public void updatePhone(User user);
	
	/**
	 * 修改密码 包括支付密码
	 * @param loginUser
	 */
	public void updatePassowrd(User user);
	
	/**
	 * 添加银行账户
	 * @param accountBank
	 */
	public void addAccountBank(AccountBank accountBank);
	
	/**
	 * 修改银行账户
	 * @param accountBank
	 */
	public void updateAccountBank(AccountBank accountBank);
	
	/**
	 * 获取银行账户列表
	 * @param userId
	 * @return
	 */
	public List<AccountBank> queryAccountBank(Integer userId);
	
	/**
	 * 删除银行账户
	 */
	public void deleteAccountBank(Integer id);
	
	/**
	 * 获取客服管理员列表
	 */
	public List<Admin> getKefuAdminList();
	
	
	/**
	 * 判断密码 是否 正确
	 * @param username 用户名
	 * @param password 密码
	 * @param passwordType 登录密码还是安全密码 【0：登录密码；1：安全密码】
	 * @return
	 */
	public Boolean isPassword(String username,String password,String passwordType);
	
	
	/**
	 * 修改自动投标信息
	 * @param user
	 */
	public void updateTenderAuto(User user);
	
	/**
	 * 用户自投排名
	 * @param id 用户ID
	 * @return
	 */
	public Integer queryTenderAutoRank(Integer id);
	
	public List<User> queryByRanking(Map<String ,Object> map);
	/**
	 * 分页 获取好友奖励
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager querySpreadListByMap(Pager pager,Map<String,Object> map);
	/**
	 * 保存手机验证码
	 *
	 */
	public void updatePhoneCode(User user);
	

	/**
	 * 
	 * 实名认证
	 * @param user
	 * @return 0  通过
	 */
	public int authRealName(User user);

	/**
	 * 新版-我的好友
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager queryMyFriendsListByMap(Pager pager,Map<String,Object> map);
	
	/**
	 * 保存token
	 * @param user
	 */
	public void updateToken(User user);
	
	/**
	 * 通过token 获取用户部分信息
	 * @param token
	 * @return
	 */
	public User getByToken(String token);
	
	public void saveToken(User loginUser);
	
	public void logout(String token);
}
