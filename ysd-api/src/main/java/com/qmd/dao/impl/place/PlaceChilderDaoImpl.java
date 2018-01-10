package com.qmd.dao.impl.place;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.place.PlaceChilderDao;
import com.qmd.mode.place.PlaceChilder;
import org.springframework.stereotype.Repository;

@Repository("placeChilderDao")
public class PlaceChilderDaoImpl extends BaseDaoImpl<PlaceChilder, Integer> implements PlaceChilderDao {

}
