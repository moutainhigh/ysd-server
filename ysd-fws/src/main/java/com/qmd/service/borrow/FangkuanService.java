package com.qmd.service.borrow;

import com.qmd.bean.BorrowFangkuanBean;
import com.qmd.mode.borrow.Fangkuan;
import com.qmd.service.BaseService;
import com.qmd.util.Pager;

import java.util.Map;

public interface FangkuanService extends BaseService<Fangkuan,Integer> {
	
	
	public Pager findBorrowFangkuanPager(Map<String,Object> qMap,Pager pager);

	/**
	 * 放款申请撤回
	 * @param id
	 * @return
	 */
	public Integer updateByAgency(Integer id,String ip);
	
	public Integer saveFangkuan(Integer id,String ip);
	
	public BorrowFangkuanBean getBorrowFangkuan(Integer id);
}
