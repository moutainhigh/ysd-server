package net.qmdboss.service.impl;

import jxl.Sheet;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.SourceRecordDao;
import net.qmdboss.entity.SourceRecord;
import net.qmdboss.service.SourceRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sourceRecordServiceImpl")
public class SourceRecordServiceImpl extends BaseServiceImpl<SourceRecord, Integer> implements SourceRecordService{


	@Resource(name = "sourceRecordDaoImpl")
	private SourceRecordDao sourceRecordDao;
	
	@Resource(name = "sourceRecordDaoImpl")
	public void setBaseDao(SourceRecordDao sourceRecordDao) {
		super.setBaseDao(sourceRecordDao);
	}
	
	public void saveLosts(List<SourceRecord> sourceRecordList){
		sourceRecordDao.saveLosts(sourceRecordList);
	}
	
	public Pager getSourceRecordPager(Map<String,String> map,Pager pager){
		return sourceRecordDao.getSourceRecordPager( map,pager);
	}
	
	public SourceRecord getSourceRecord(String orderNumber){
		return sourceRecordDao.getSourceRecord(orderNumber);
	}
	
	public List<SourceRecord> getSourceRecordList(Map<String,String> map){
		return sourceRecordDao.getSourceRecordList(map);
	}
	
	public List<SourceRecord> getGfbRecord(SourceRecord sourceRecord,Sheet st) throws ParseException{
		List<SourceRecord> sourceRecordList = new ArrayList<SourceRecord>(); 
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=1,j=st.getRows();i<j;i++){
			SourceRecord s = new SourceRecord();
			s.setRechargeConfig(sourceRecord.getRechargeConfig());
			if(StringUtils.isNotEmpty(st.getCell(0, i).getContents())){
				s.setAddDate(simple.parse(st.getCell(0, i).getContents()));
			}
			if(StringUtils.isNotEmpty(st.getCell(1, i).getContents())){
				s.setCompletionDate(simple.parse(st.getCell(1, i).getContents()));
			}
			s.setTransactionNumber(st.getCell(2, i).getContents());
			s.setTradeState(st.getCell(3, i).getContents().equals("成功")?"1":"2");//【2表示与本地充值中的数据一致】
			s.setTreatmentState(st.getCell(4, i).getContents());
			s.setTradeType(st.getCell(5, i).getContents());
			s.setBalanceType(st.getCell(6, i).getContents());
			s.setPayment(st.getCell(7, i).getContents());
			s.setTradingFloor(st.getCell(8, i).getContents());
			s.setCounterparty(st.getCell(9, i).getContents());
			s.setAssociatedBank(st.getCell(10, i).getContents());
			s.setBankOrderNumber(st.getCell(11, i).getContents());
			s.setOrderNumber(st.getCell(12, i).getContents());
			s.setActualAmount(BigDecimal.valueOf(Double.valueOf(st.getCell(13, i).getContents())));
			s.setTransactionAmount(BigDecimal.valueOf(Double.valueOf(st.getCell(14, i).getContents())));
			s.setHandlingCharge(BigDecimal.valueOf(Double.valueOf(st.getCell(15, i).getContents())));
			s.setRemark(st.getCell(16, i).getContents());
			
			sourceRecordList.add(s);
		}
		
		return sourceRecordList;
	}
	
	public List<SourceRecord> getHnapayRecord(SourceRecord sourceRecord,Sheet st){
		List<SourceRecord> sourceRecordList = new ArrayList<SourceRecord>(); 
		return sourceRecordList;
	}
	
	public List<SourceRecord> getBaofooRecord(SourceRecord sourceRecord,Sheet st) throws ParseException{
		
		List<SourceRecord> sourceRecordList = new ArrayList<SourceRecord>(); 
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=1,j=st.getRows();i<j;i++){
			if(StringUtils.isEmpty(st.getCell(0, i).getContents())){
				SourceRecord s = new SourceRecord();
				s.setRechargeConfig(sourceRecord.getRechargeConfig());
				s.setPayment(st.getCell(1, i).getContents());//支付方式
				s.setOrderNumber(st.getCell(2, i).getContents());//商户订单号
				s.setTransactionNumber(st.getCell(3, i).getContents());//宝付订单号
				if(StringUtils.isNotEmpty(st.getCell(4, i).getContents())){
					s.setAddDate(simple.parse(st.getCell(4, i).getContents()));//下单时间
				}
				if(StringUtils.isNotEmpty(st.getCell(5, i).getContents())){//完成时间
					s.setCompletionDate(simple.parse(st.getCell(5, i).getContents()));
				}
				s.setActualAmount( new BigDecimal(st.getCell(8, i).getContents()));//结算金额
				s.setTransactionAmount(new BigDecimal(st.getCell(7, i).getContents()));//成功充值金额
				s.setHandlingCharge(s.getTransactionAmount().subtract(s.getActualAmount()));//手续费
				s.setTradeState(st.getCell(9, i).getContents().equals("成功")?"1":"2");//状态【2表示与本地充值中的数据一致】
				s.setRemark("产品名称:"+st.getCell(10, i).getContents()+";用户名称:"+st.getCell(11, i).getContents());
				
				sourceRecordList.add(s);
			}
		}
		return sourceRecordList;
	}
	
}
