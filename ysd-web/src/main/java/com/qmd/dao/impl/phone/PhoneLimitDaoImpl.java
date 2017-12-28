package com.qmd.dao.impl.phone;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.phone.PhoneLimitDao;
import com.qmd.mode.phone.PhoneLimit;
import org.springframework.stereotype.Repository;
@Repository("phoneLimitDao")
public class PhoneLimitDaoImpl extends BaseDaoImpl<PhoneLimit,Integer> implements PhoneLimitDao {
}