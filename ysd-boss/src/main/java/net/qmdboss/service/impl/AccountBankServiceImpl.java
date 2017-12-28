package net.qmdboss.service.impl;

import net.qmdboss.bean.AccountBankItem;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.AccountBankDao;
import net.qmdboss.dao.ListingDao;
import net.qmdboss.entity.AccountBank;
import net.qmdboss.service.AccountBankService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("accountBankServiceImpl")
public class AccountBankServiceImpl extends
		BaseServiceImpl<AccountBank, Integer> implements AccountBankService {
	@Resource(name = "accountBankDaoImpl")
	private AccountBankDao accountBankDao;
	@Resource(name = "listingDaoImpl")
	private ListingDao listingDao;

	@Resource(name = "accountBankDaoImpl")
	public void setBaseDao(AccountBankDao accountBankDao) {
		super.setBaseDao(accountBankDao);
	}

	@Override
	public Pager findPagerByMap(Pager pager,Map<String, Object> map) {
		pager = accountBankDao.findPagerByMap(pager, map);
		List<AccountBank> list = (List<AccountBank>)pager.getResult();
		List<AccountBankItem> result = new ArrayList<AccountBankItem>();
		for(AccountBank bank : list){
			AccountBankItem item = new AccountBankItem(bank);
			
			if(bank.getBankId() != null){
				item.setBankName(listingDao.findChildListingByKeyValue("account_bank", bank.getBankId()));
			}
			
			result.add(item);
		}
		pager.setResult(result);
		return pager;
	}
}
