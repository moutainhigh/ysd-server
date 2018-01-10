package com.qmd.dao.user;

import com.qmd.dao.BaseDao;
import com.qmd.mode.admin.Admin;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.util.Pager;

import java.util.List;
import java.util.Map;

public interface UserDao extends BaseDao<User, Integer>{
	/**
	 * 根据用户参数查询用户信息
	 * @param map
	 * @return
	 */
	public User getUser(Map<String,Object> map);
	
	public List<User> getUserList(Map<String,Object> map);
	
	public Pager queryUser(Pager pager,Map<String,Object> map);

	/**
	 * 注册添加用户信息
	 * @param user
	 */
	public void addUser(User user);
	
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
	 *  修改会员实名认证信息
	 * @param user
	 */
	public void updateRealName(User user);
	
	/**
	 * 会员上传头像
	 * @param user
	 */
	public void updateLitpic(User user);
	
	/**
	 * 修改会员邮件认证信息
	 * @param user
	 */
	public void updateEmail(User user);
	

	/**
	 * 会员申请手机认证
	 * @param user
	 */
	public void updatePhone(User user);
	
	/**
	 * 修改企业资料认证
	 * @param user
	 */
	public void updateAvatar(User user);
	/**
	 * 会员申请成为借款人
	 * @param user
	 */
	public void updateTypeId(User user);
	
	
	/**
	 * 修改密码
	 * @param loginUser
	 */
	public void updatePassowrd(User user);
	
	/**
	 * 修改安全问题
	 * @param loginUser
	 */
	public void updateQuestion(User user);
	
	
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
	public User getByUserId(Integer id);
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
	 * 新版-我的好友
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager queryMyFriendsListByMap(Pager pager,Map<String,Object> map);
}
