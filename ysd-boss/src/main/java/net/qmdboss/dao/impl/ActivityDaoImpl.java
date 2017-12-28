package net.qmdboss.dao.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.ActivityDao;
import net.qmdboss.entity.Activity;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
@Repository("activityDaoImpl")
public class ActivityDaoImpl extends BaseDaoImpl<Activity,Integer> implements ActivityDao {

	@Override
	public Pager findPager(Pager pager, Map<String, Object> map) {

		Criteria criteria = getSession().createCriteria(Activity.class);
		if(map!= null){
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
			Date startTime=null;
			Date endTime=null;
			try {
				startTime = map.get("startTime")!=null?dateformat.parse(map.get("startTime").toString()):null;
				endTime = map.get("endTime")!=null?dateformat.parse(map.get("endTime").toString()):null;
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(startTime != null && endTime != null){
				// 结束时间处理一下
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endTime);
				calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
				endTime = calendar.getTime();
				criteria.add(Restrictions.between("endTime", startTime, endTime));//结束时间
			}
			
			if(map.get("status")!=null){
				criteria.add(Restrictions.eq("status", Integer.parseInt(map.get("status").toString())));
			}
			
			if(StringUtils.isNotBlank((String)map.get("title"))){
				criteria.add(Restrictions.like("title", "%"+map.get("title").toString()+"%"));
			}
		}
		
		return super.findPager(pager, criteria);
	}
	
	
}