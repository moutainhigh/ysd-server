package net.qmdboss.dao.impl;

import net.qmdboss.dao.IosIdfaDao;
import net.qmdboss.entity.IosIdfa;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("iosIdfaDaoImpl")
public class IosIdfaDaoImpl extends BaseDaoImpl<IosIdfa, Integer> implements IosIdfaDao {

	public IosIdfa getIosIdfa(String idfa,String mac){
		Criteria criteria = getSession().createCriteria(IosIdfa.class);
		if(StringUtils.isNotEmpty(idfa)){
			criteria.add(Restrictions.eq("idfa", idfa));
		}else if(StringUtils.isNotEmpty(mac)){
			criteria.add(Restrictions.eq("mac", mac));
		}		
		List<IosIdfa> iosList =  criteria.list();
		if(iosList != null && iosList.size() >0){
			return iosList.get(0);
		}		
		return null;
	}
}
