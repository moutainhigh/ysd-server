package net.qmdboss.action.admin;

import net.qmdboss.entity.AccountBank;
import net.qmdboss.entity.Listing;
import net.qmdboss.entity.User;
import net.qmdboss.service.AccountBankService;
import net.qmdboss.service.ListingService;
import net.qmdboss.service.UserService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountBankAction extends BaseAdminAction {

	private static final long serialVersionUID = -8864615817416555400L;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	private AccountBank bank;
	private List<Listing> banks;
	
	public AccountBank getBank() {
		return bank;
	}

	public void setBank(AccountBank bank) {
		this.bank = bank;
	}

	private String phone;
	
	@Resource(name = "accountBankServiceImpl")
	private AccountBankService accountBankService;
	@Resource(name = "listingServiceImpl")
	private ListingService listingService;
	
	public String list() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("phone", phone);
		pager = accountBankService.findPagerByMap(pager, map );
		return LIST;
	}
	
	public String edit(){
		bank = accountBankService.load(id);
		return INPUT;
	}
	
	public String update(){
		AccountBank entity = accountBankService.get(bank.getId());
		if(StringUtils.isNotBlank(bank.getBankId())){
			entity.setBankId(bank.getBankId());
		}
		if(StringUtils.isNotBlank(bank.getBranch())){
			entity.setBranch(bank.getBranch());
		}
		accountBankService.update(entity);
		redirectUrl = "account_bank!list.action";
		return SUCCESS;
	}
	
	public String delete(){
		bank=accountBankService.load(id);
		User user = bank.getUser();
		user.setRealStatus(0);
		userService.update(user);
		accountBankService.delete(bank);
		return ajax(Status.success, "删除成功!");
	}

	public List<Listing> getBanks(){
		List<Listing> banks = listingService.getChildListingBySignList("account_bank");
		return banks;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setBanks(List<Listing> banks) {
		this.banks = banks;
	}
}
