package net.qmdboss.dao;

import net.qmdboss.entity.IosIdfa;

public interface IosIdfaDao  extends BaseDao<IosIdfa, Integer>{

	public IosIdfa getIosIdfa(String idfa,String mac);
}
