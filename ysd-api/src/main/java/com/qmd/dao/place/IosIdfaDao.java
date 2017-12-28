package com.qmd.dao.place;

import com.qmd.dao.BaseDao;
import com.qmd.mode.place.IosIdfa;

public interface IosIdfaDao extends BaseDao<IosIdfa, Integer> {
	
	/**
	 * 激活
	 * @param idfaOrMac
	 * @param type 1idfa,2mac
	 * @return
	 */
	public IosIdfa activate(String idfa, Integer type);

	/**
	 * 投资完成 渠道回调
	 * @param idfaOrMac
	 * @param type
	 * @return
	 */
	public void tenderBack(String idfaOrMac, Integer type);
}