package com.ysd.pay;

public interface IPay {

	public IPay getInstance() ;
	
	/**实名认证*/
	public PayResult realNameVerify(UserPayInfo up) ;
	
	/**充值*/
	public PayResult recharge(UserPayInfo up );
	
	
	/**提现*/
	public PayResult getMoney(UserPayInfo up);
}
