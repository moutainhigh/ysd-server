package com.qmd.service.impl.phone;

import com.qmd.dao.phone.PhoneLimitDao;
import com.qmd.mode.phone.PhoneLimit;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.phone.PhoneLimitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("phoneLimitService")
public class PhoneLimitServiceImpl extends BaseServiceImpl<PhoneLimit, Integer> implements PhoneLimitService {
	@SuppressWarnings("unused")
	@Resource
	private PhoneLimitDao phoneLimitDao;

	@Resource
	public void setBaseDao(PhoneLimitDao phoneLimitDao) {
		super.setBaseDao(phoneLimitDao);
	}

	public synchronized List<PhoneLimit> getPhoneLimitList(String phone) {
		Map<String, Object> map_p = new HashMap<String, Object>();
		map_p.put("phone", phone);
		return phoneLimitDao.queryListByMap(map_p);
	}
}
