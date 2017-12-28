package com.qmd.service.phone;

import com.qmd.mode.phone.PhoneLimit;
import com.qmd.service.BaseService;

import java.util.List;

public interface PhoneLimitService extends BaseService<PhoneLimit,Integer> {

	public List<PhoneLimit> getPhoneLimitList(String phone);
}