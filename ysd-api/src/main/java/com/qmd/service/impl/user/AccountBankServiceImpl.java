package com.qmd.service.impl.user;

import com.qmd.dao.user.AccountBankDao;
import com.qmd.dao.user.UserDao;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.payment.UnionPayUtils;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.AccountBankService;
import com.qmd.util.SerialNumberUtil;
import com.qmd.util.bean.UnionPayInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 查询用户银行卡列表Service实现类
 * @author zhanf
 *
 */
@Service("accountBankService")
public class AccountBankServiceImpl extends
		BaseServiceImpl<AccountBank, Integer> implements AccountBankService {
	@Resource
	AccountBankDao accountBankDao;
	@Resource
	UserDao userDao;
	
	@Resource
	public void setBaseDao(AccountBankDao accountBankDao) {
		super.setBaseDao(accountBankDao);
	}

	@Override
	public List<AccountBank> getAccountBankList(Integer userId) {
		// TODO Auto-generated method stub
		return this.accountBankDao.getAccountBankList(userId);
	}

	@Override
	public AccountBank getAccountBank(Integer id) {
		// TODO Auto-generated method stub
		return this.accountBankDao.getAccountBank(id);
	}

	public int insertBankInfo(User user,AccountBank bank){
		int ret=0;
		
		//校验是否绑定银行卡成功
		String exCode = "HZJCB_UNPY_BIND2";
		String cvn="";
		String expire="";
		String accName = user.getRealName();
		String accId = user.getCardId();
		String cardNo=bank.getAccount();
		String merId = "1000000503"; // 第三方商户平台接入号
		String mobile = user.getPhone();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String orderNo = SerialNumberUtil.buildPaymentSn();
		Date d = new Date();
		String tranDateTime = simpleDateFormat.format(d);
		String timestamp = d.getTime()+"";
		String jsonBody012 ="{\"accId\": \""+accId+"\",\"accName\": \""+accName+"\", \"cardNo\": \""+cardNo+"\",\"cvn\": \""+cvn+"\",\"exCode\": \""+exCode+"\",\"expire\": \""+expire+"\",\"merId\": \""+merId+"\",\"mobile\": \""+mobile+"\",\"orderNo\": \""+orderNo+"\",\"tranDateTime\": \""+tranDateTime+"\",\"timestamp\": \""+timestamp+"\"}";	
		UnionPayInfo info = UnionPayUtils.unionToPay(jsonBody012);
		if("0000".equals(info.getRetCode())){
			ret= 1;
			//签约成功
			bank.setStatus(1);
			bank.setBindId(info.getBindId());
			userDao.addAccountBank(bank);
			user.setRealStatus(1);
			userDao.updateRealName(user);
		}else{
			ret = 0;
		}
		return ret;
	}
}
