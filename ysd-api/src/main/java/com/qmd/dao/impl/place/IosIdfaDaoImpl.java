package com.qmd.dao.impl.place;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.place.IosIdfaDao;
import com.qmd.mode.place.IosIdfa;
import com.qmd.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("iosIdfaDao")
public class IosIdfaDaoImpl extends BaseDaoImpl<IosIdfa, Integer> implements
		IosIdfaDao {

	
	@Override
	public IosIdfa activate(String idfaOrMac, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(type == null || type == 1){
			map.put("idfa", idfaOrMac);
		} else if(type == 2){
			map.put("mac", idfaOrMac);
		} else {
			return null;
		}
		
		List<IosIdfa> l = queryListByMap(map);
		IosIdfa iosIdfa = null;
		if(l != null && l.size() > 0){
			iosIdfa = l.get(0);
			//仅 ‘点乐,万普2’接口 回调
			if( StringUtils.isNotEmpty(iosIdfa.getCallback()) && StringUtils.isNotEmpty(iosIdfa.getSource()) && ("02951618".equals(iosIdfa.getSource())||"404726108".equals(iosIdfa.getSource()))){
				iosIdfa.setStatus(1);
				update(iosIdfa);
				try {
					HttpUtil.sendGet(URLDecoder.decode(iosIdfa.getCallback(), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		
		return iosIdfa;
	}
	

	@Override
	public void tenderBack(String idfaOrMac, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(type == null || type == 1){
			map.put("idfa", idfaOrMac);
		} else if(type == 2){
			map.put("mac", idfaOrMac);
		}
		
		List<IosIdfa> l = queryListByMap(map);
		IosIdfa iosIdfa = null;
		if(l != null && l.size() > 0){
			iosIdfa = l.get(0);
			//非‘点乐’接口 回调
			if( StringUtils.isNotEmpty(iosIdfa.getCallback()) && StringUtils.isNotEmpty(iosIdfa.getSource()) && !"02951618".equals(iosIdfa.getSource())&& !"404726108".equals(iosIdfa.getSource())){
				iosIdfa.setStatus(1);
				update(iosIdfa);
				try {
					HttpUtil.sendGet(URLDecoder.decode(iosIdfa.getCallback(), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}