package com.qmd.service.borrow;

import com.qmd.mode.borrow.BorrowRecharge;
import com.qmd.service.BaseService;
import com.qmd.util.Pager;

import java.util.Map;

/**
 * 
 * @author Administrator
 * 
 */
public interface BorrowRechargeService extends BaseService<BorrowRecharge, Integer> {

	public int saveBorrowRecharge(BorrowRecharge bean);

	public Pager queryPagerBorrowRecharge(Pager page, Map<String, Object> qMap);

	public int updateByAgency(Integer id, String ip);
}
