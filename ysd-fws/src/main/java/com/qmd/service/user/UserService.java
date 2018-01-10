package com.qmd.service.user;

import com.qmd.mode.admin.Admin;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserInfo;
import com.qmd.service.BaseService;
import com.qmd.util.Pager;
import com.qmd.util.bean.ShowBean;

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
//	public String buildRecoverKey();
	
	/**
	 * 根据找回Key获取生成日期
	 * 
	 * @return 生成日期
	 */
//	public Date getRecoverKeyBuildDate(String RecoverKey);
	

	/**
	 * 注册添加用户信息
	 * @param user
	 */
	public void addUser(User user,UserInfo userInfo,AccountBank accountBank,String ip,Integer AgencyId);
	
	/**
	 * 修改用户信息
	 * @param user
	 */
	public void updateUser(User user,UserInfo userInfo,AccountBank accountBank);
	
	/**
	 * 用户登录成功修改用户信息
	 * @param user
	 */
	public void updateUserByLoginSuccess(User user);
	
	/**
	 * 修改用户个人资料
	 * @param user
	 */
//	public void updateProfile(User user);
	
	/**
	 * 提交实名认证申请
	 * @param user
	 */
//	public void updateRealName(User user);
	
	/**
	 * 会员申请实名认证
	 * @param user
	 */
	public void updateRealName(User user,String ip);
	
	/**
	 * 会员上传头像
	 * @param user
	 */
//	public void updateLitpic(User user);

	/**
	 * 会员申请邮件认证
	 * @param user
	 */
//	public void updateEmail(User user);
	
//	public void updateEmailCheck(User user,String ip);
	

	/**
	 * 会员申请手机认证
	 * @param user
	 */
//	public void updatePhone(User user);
	
	
	/**
	 * 借款人资质认证
	 * @param user
	 */
//	public void updateAvatar(User user);
	/**
	 * 申请成为借款人
	 * @param applyBorrower
	 */
//	public void addApplyBorrower(ApplyBorrower applyBorrower,User user);
	
	/**
	 * 申请成为VIP
	 * @param applyVip
	 * @param loginUser
	 */
//	public void addApplyVip(ApplyVip applyVip,User user);
	
	/**
	 * 修改密码 包括支付密码
	 * @param user
	 */
	public void updatePassowrd(User user);
	
	/**
	 * 修改安全问题
	 * @param loginUser
	 */
//	public void updateQuestion(User user);
	
	/**
	 * 添加银行账户
	 * @param accountBank
	 */
	public void addAccountBank(AccountBank accountBank);
	
	/**
	 * 修改银行账户
	 * @param accountBank
	 */
//	public void updateAccountBank(AccountBank accountBank);
	
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
	 * 在线预约
	 * @param onlineBooking
	 */
//	public void addOnlineBooking(OnlineBooking onlineBooking);
	
	/**
	 * 判断密码 是否 正确
	 * @param username 用户名
	 * @param password 密码
	 * @param passwordType 登录密码还是安全密码 【0：登录密码；1：安全密码】
	 * @return
	 */
	public Boolean isPassword(String username,String password,String passwordType);
	
	/**
	 * 添加线下投资-借款
	 * @param offLine
	 */
//	public void addOffLine(OffLine offLine);
	
	/**
	 * 获取线下投资-借款申请记录
	 * @param map
	 * @return
	 */
//	public List<OffLine> getOffLineListByUserid(Map<String ,Object> map);
	
	/**
	 * 获取线下投资-借款申请记录-分页
	 * @param pager
	 * @param map
	 * @return
	 */
//	public Pager queryOffLineListByUserid(Pager pager,Map<String,Object> map);
	
	/**
	 * 根据用户ID查询担保用户信息
	 * @param userid
	 * @return
	 */
//	public Agency getAgencyByUserid(Integer userid);
	
	/**
	 * 修改担保用户信息
	 * @param agency
	 */
//	public void updateAgency(Agency agency);
	
	/**
	 * 添加机构服务申请
	 * @param league
	 */
//	public void addLeague(League league);
	
	/**
	 * 激活体验金
	 * @param id:user_id
	 * @return 0 激活，1 网站没开通体验金，2 已经做过激活，3 体验金状态有效不需激活，4 无体检金，5 待收小于制定金额
	 */
//	public int updateTasteActivation(Integer id,String ip);
	
	/**
	 * 激活体验金
	 * @param user 用户
	 * @return 0 可以激活，1 网站没开通体验金，2 已经做过激活，3 体验金状态有效不需激活，4 无体检金，5 待收小于制定金额
	 */
//	public int checkTasteActivation(User user);
	
	/**
	 * 发放体验金
	 * @param id 用户ID
	 * @param ip
	 * @return 0 领取成功。-1 领取失败。1 网站没有开通体验金。2 已经领取过体验金。3 实名未认证
	 */
//	public int receiveTaste(Integer id,String ip);
	
	/**
	 * 登录绑定会员卡
	 * @param user
	 */
//	public void loginUserJhCard(User user);

	/**
	 * 分页 获取好友奖励
	 * @param pager
	 * @param map
	 * @return
	 */
//	public Pager querySpreadListByMap(Pager pager,Map<String,Object> map);
	
//	public void updateUserIdentify(boolean result,User user,UserInfo userInfo,String ip);
	
//	public void updateUserIdentify(boolean realname,boolean result,User user,UserInfo userInfo,String ip);
	
	/**
	 * 修改自动投标信息
	 * @param user
	 */
//	public void updateTenderAuto(User user);
	
	/**
	 * 用户自投排名
	 * @param id
	 * @return
	 */
//	public Integer queryTenderAutoRank(Integer id);
	
//	public Integer 	queryCountUserByMap(Map<String,Object> map3) ;
	public List<ShowBean> queryDirectInfo(String type,Map<String,Object> map);
	
	/**
	 * 借款人列表
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager queryJkrlb(Pager pager,Map<String,Object> map);
	
	
	
}
