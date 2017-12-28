package net.qmdboss.dao;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.SourceRecord;

import java.util.List;
import java.util.Map;

public interface SourceRecordDao  extends BaseDao<SourceRecord, Integer>{

	/**
	 * 批量插入
	 * @param sourceRecordList
	 */
	public void saveLosts(List<SourceRecord> sourceRecordList);

	
	/**
	 * 查询原始充值记录
	 * @return
	 */
	public Pager getSourceRecordPager(Map<String,String> map,Pager pager);
	/**
	 * 根据订单号查询
	 * @param orderNumber
	 * @return
	 */
	public SourceRecord getSourceRecord(String orderNumber);
	
	/**
	 * 查询所有第三方充值记录
	 * @param map
	 * @return
	 */
	public List<SourceRecord> getSourceRecordList(Map<String,String> map);
}
