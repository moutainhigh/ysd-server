package net.qmdboss.directive;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Component("overdueMethod")
public class OverdueMethod implements TemplateMethodModel {
	
	public static final String TAG_NAME = "overdue";
	
	@SuppressWarnings("unchecked")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments.size() == 1) {
			String str = arguments.get(0).toString();
			if(StringUtils.isNotEmpty(str)){
				System.out.println("天数:"+getDaysBetween(str,new Date()));
				return new SimpleScalar(String.valueOf(getDaysBetween(str,new Date())));
			}else{
				return new SimpleScalar("");
			}
		}else {
			throw new TemplateModelException("Wrong arguments");
		}
	}
	
	public static int getDaysBetween (String beginDate ,Date endDate) { 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Date bDate = null;
		try {
			bDate = format.parse(beginDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//		Date eDate = format.parse(endDate); 
		Calendar d1 = new GregorianCalendar(); 
		d1.setTime(bDate); 
		Calendar d2 = new GregorianCalendar(); 
		d2.setTime(endDate); 
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR); 
        int y2 = d2.get(Calendar.YEAR); 
	        if (d1.get(Calendar.YEAR) != y2) { 
	            d1 = (Calendar) d1.clone(); 
	            do { 
	                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数 
	                d1.add(Calendar.YEAR, 1); 
	            } while (d1.get(Calendar.YEAR) != y2); 
	          
	        } 
	        return days; 
	        
	    }

}
