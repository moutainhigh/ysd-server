package net.qmdboss.dao.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.SourceRecordDao;
import net.qmdboss.entity.SourceRecord;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("sourceRecordDaoImpl")
public class SourceRecordDaoImpl extends BaseDaoImpl<SourceRecord, Integer> implements SourceRecordDao{

	
	public void saveLosts(List<SourceRecord> sourceRecordList) {
		int i=0;
		for(SourceRecord s: sourceRecordList){
			super.save(s);
			if (i % 20 == 0) {
                flush();
                clear();
            }
			i++;
		}
	}
	
	public Pager getSourceRecordPager(Map<String,String> map,Pager pager){
		Criteria criteria = getSession().createCriteria(SourceRecord.class);
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate=null;
		Date endDate=null;
		try {
			startDate = map.get("startDate")!=null?dateformat.parse(map.get("startDate").toString()):null;
			endDate = map.get("endDate")!=null?dateformat.parse(map.get("endDate").toString()):null;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//按提交时间或者完成时间查询
		if(startDate!= null && endDate!= null ){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
			endDate = calendar.getTime();
			if(map.get("dateType").toString().equals("1") ){
				criteria.add(Restrictions.between("completionDate", startDate, endDate));
			}else{
				criteria.add(Restrictions.between("addDate", startDate, endDate));
			}
		}
		if(StringUtils.isNotEmpty(map.get("rechargeConfigId"))){
			criteria.add(Restrictions.eq("rechargeConfig.id", Integer.parseInt(map.get("rechargeConfigId").toString())));
		}
		if(StringUtils.isNotEmpty(map.get("tradeState"))){
			criteria.add(Restrictions.eq("tradeState", map.get("tradeState").toString()));
		}
		if(StringUtils.isNotEmpty(map.get("orderNumber"))){
			criteria.add(Restrictions.eq("orderNumber", map.get("orderNumber").toString()));
		}
//		if(StringUtils.isNotEmpty(map.get("tradeState").toString())){
//			criteria.add(Restrictions.eq("tradeState", map.get("tradeState").toString()));
//		}
		
		return super.findPager(pager, criteria);
	}

	
	
	/**
	 * 根据订单号查询
	 * @param orderNumber
	 * @return
	 */
	public SourceRecord getSourceRecord(String orderNumber){
		String hql = "from SourceRecord as sourceRecord where sourceRecord.orderNumber = :orderNumber";
		return (SourceRecord) getSession().createQuery(hql).setParameter("orderNumber", orderNumber).uniqueResult();
	}
	
	public List<SourceRecord> getSourceRecordList(Map<String,String> map){
		Criteria criteria = getSession().createCriteria(SourceRecord.class);
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate=null;
		Date endDate=null;
		try {
			startDate = map.get("startDate")!=null?dateformat.parse(map.get("startDate").toString()):null;
			endDate = map.get("endDate")!=null?dateformat.parse(map.get("endDate").toString()):null;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//按提交时间或者完成时间查询
		if(startDate!= null && endDate!= null ){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
			endDate = calendar.getTime();
			if(map.get("dateType").toString().equals("1") ){
				criteria.add(Restrictions.between("completionDate", startDate, endDate));
			}else{
				criteria.add(Restrictions.between("addDate", startDate, endDate));
			}
		}
		if(StringUtils.isNotEmpty(map.get("rechargeConfigId"))){
			criteria.add(Restrictions.eq("rechargeConfig.id", Integer.parseInt(map.get("rechargeConfigId").toString())));
		}
		if(StringUtils.isNotEmpty(map.get("tradeState"))){
			criteria.add(Restrictions.eq("tradeState", map.get("tradeState").toString()));
		}
		if(StringUtils.isNotEmpty(map.get("orderNumber"))){
			criteria.add(Restrictions.eq("orderNumber", map.get("orderNumber").toString()));
		}
//		if(StringUtils.isNotEmpty(map.get("tradeState").toString())){
//			criteria.add(Restrictions.eq("tradeState", map.get("tradeState").toString()));
//		}
		
		return criteria.list();
	}
	
}
