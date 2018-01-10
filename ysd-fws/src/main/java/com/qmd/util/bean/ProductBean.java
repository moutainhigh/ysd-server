package com.qmd.util.bean;

import com.qmd.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductBean implements Serializable {

	private static final long serialVersionUID = 2364989723968719547L;
	
	private Integer id;
	private Date createDate;//添加时间
	private Date modifyDate;//修改时间
	private Integer jigouId;//机构服务商ID
	private Integer userId;//发标人ID
	private String code;//项目编号
	private String name;//标题
	private String content;//描述
	private String productType;//【0：理财产品；1：标】
	
	//状态【标：0-发表未审核；1-审核通过；2-审核未通过；3-满标审核通过；4-满标审核未通过；5-等待满标审核；6-过期或撤回；7-已还完;8-删除状态】
	//【理财产品：0：发布审核中；1：预定中；2：初审失败；3：预定完成；4：预定结束；5：产品过期；6：已撤回；9：已删除】
	private Integer status;
	
	//类型【01:不动产；02：动产；1：理财产品】
	private String type;
	
	
	//类型【0-秒标，1-质押标，2-流转标，3-信用标，4-月标,5-债权流转标,6-卡趣月标，7:-卡趣新标(债权流转)，8:-卡趣新标（抵押质押），9:-卡趣新标（信用）,10:体验标,20:债权转让】,【理财产品：1：保险理财】
	private String btype;//区分type
	
	private Date verifyTime;// 审核时间
	private String validTime;// 有效时间(天)
	private Date endTime;// 标结束时间
	
	private BigDecimal money;//总金额
//	private BigDecimal meonyYes;//完成金额
	private BigDecimal residueMoney; // 剩余金额
	
	private BigDecimal perMoney;//每份金额
	private BigDecimal rateHigh;//最高利率
	private BigDecimal rateLow;//最低利率

	private BigDecimal rateYearLow;//最低利率（年化）
	private BigDecimal rateYearHeight;//最低利率（年化）
	
	private String schedule;//完成进度
	private String timeLimit;//期限
	
	private String timeLimitMonth;//期限月数，用于查询条件
	
	private String firstImg;//封面
	private String serviceImg;//服务商封面
	private String province;//省
    private String city;//市
    private String area;//区
	private String areaStore;//地址

	private Integer tasteRule;//【理财：-1；标:体验金规则：1均可，2只有体验金，3不能体检金】

	private Integer isAssign;//是否可转让标【0：不可转让；1：可转让】
	
	private Integer assignDays;//封闭期天数
	
	private BigDecimal repayInterest;//代收利息
	
	private BigDecimal zrjr;//转让金额
	
	private BigDecimal zrbj;//原始金额
	
	private Date finalRepayDate;//最后还款日
	
	private Integer showSort;//排序
	
	private Integer showTop;//是否置顶【0：不置顶；1：置顶】
	
	private Integer tenderSubject;//新客标记
	
	private String style;//还款方式
	
	private Integer repaymentPeriod;//回款周期 （天）
	
	private Integer deferStatus;//展期状态【0：默认-不展期(status=0)；1：已申请展期(status=0)；  2：展期申请中(status=0)；3：申请展期未通过(status=2,deferId=null)；4：等待展期发布(点击还款)(status=0)；5：展期发布失败(status=2,deferId=null)；6：发布成功展期(status=1)】
	
	private String awardStatus;//奖励状态【0：默认-没有奖励；1：投资成功发放；2：最后一期还款发放】
	/**
	 * 未过期：1，过期0,空 -2
	 * @return
	 */
	public int getEndFlg() {
		if (endTime==null) {
			return -2;
		}
		
		if(CommonUtil.isEndDate(endTime) > 0) {
			return 1;
		}
		return 0;
	}
	
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getJigouId() {
		return jigouId;
	}

	public void setJigouId(Integer jigouId) {
		this.jigouId = jigouId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

//	public BigDecimal getMeonyYes() {
//		return meonyYes;
//	}
//
//	public void setMeonyYes(BigDecimal meonyYes) {
//		this.meonyYes = meonyYes;
//	}

	public BigDecimal getPerMoney() {
		return perMoney;
	}

	public void setPerMoney(BigDecimal perMoney) {
		this.perMoney = perMoney;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getFirstImg() {
		return firstImg;
	}

	public void setFirstImg(String firstImg) {
		this.firstImg = firstImg;
	}

	public String getAreaStore() {
		return areaStore;
	}

	public void setAreaStore(String areaStore) {
		this.areaStore = areaStore;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	/**
	 * 剩余份数
	 * @return
	 */
	public BigDecimal getShowResidueSize() {
		if (residueMoney==null) return BigDecimal.valueOf(0);
		if (perMoney==null||perMoney.longValue()==0)return BigDecimal.valueOf(0);
		
		return residueMoney.divide(perMoney);
	}
	
	/**
	 * 已投份数
	 * @return
	 */
	public BigDecimal getShowInvestedSize() {
		if (residueMoney==null) return BigDecimal.valueOf(0);
		if (money==null) return BigDecimal.valueOf(0);
		if (perMoney==null || perMoney.longValue()==0)
			return BigDecimal.valueOf(0);
		
		return money.subtract(residueMoney).divide(perMoney);
	}

	public BigDecimal getResidueMoney() {
		return residueMoney;
	}

	public void setResidueMoney(BigDecimal residueMoney) {
		this.residueMoney = residueMoney;
	}

	public BigDecimal getRateHigh() {
		return rateHigh;
	}

	public void setRateHigh(BigDecimal rateHigh) {
		this.rateHigh = rateHigh;
	}

	public BigDecimal getRateLow() {
		return rateLow;
	}

	public void setRateLow(BigDecimal rateLow) {
		this.rateLow = rateLow;
	}

	public String getServiceImg() {
		return serviceImg;
	}

	public void setServiceImg(String serviceImg) {
		this.serviceImg = serviceImg;
	} 

	public String getMd5ServiceImg(){
		String str = null;
		if(StringUtils.isNotEmpty(serviceImg)){
			str = CommonUtil.encodeUrl(serviceImg);
		}
		return str;
	}
	

	public String getPstatus(){
		String ret = "";
		if("0".equals(productType)){//理财产品
			if("1".equals(btype)){ //保险理财
				if(status == 0){
					ret = "待审中";
				}else if(status == 1){
					ret = "募集中";
				}else if(status ==2){
					ret = "审核拒绝";
				}else if(status ==3){
					ret = "募集完成";
				}else if(status ==4){
					ret = "已过期";
				}else if(status ==5){
					ret = "已过期";
				}else if(status ==6){
					ret = "失效";
				}
				
				if (getShowResidueSize().longValue() > 0) {
					ret = "募集中";
				} else {
					ret = "募集完成";
				}
			}
		}else if("1".equals(productType)){//标
			if (status == 0) {
				ret = "待审中";
			} else if (status == 1) {
				
				ret = "募集中";
				if("2".equals(btype)) {
					if (getShowResidueSize().longValue() > 0) {
						ret = "募集中";
					} else {
						ret = "募集完成";
					}
				} else if("5".equals(btype)) {
					
					if (getEndFlg() !=1) {// 已过期
						return "募集结束";
					}
					
					if (getShowResidueSize().longValue() > 0) {
						ret = "募集中";
					} else {
						ret = "募集完成";
					}
				} else if("4".equals(btype)){				
					if(residueMoney!=null && residueMoney.compareTo(new BigDecimal(0)) == 0){
						ret = "募集完成";
					}else{
						ret = "募集中";
					}
				} else if("7".equals(btype)||"8".equals(btype)||"9".equals(btype)||"10".equals(btype)) {
					if (getEndFlg() !=1) {// 已过期
						return "投资完成";
					}
					if (getShowResidueSize().longValue() > 0) {
						ret = "募集中";
					} else {
						ret = "募集达成";
					}
				}
			} else if (status == 2) {
				ret = "审核拒绝";
			} else if (status == 3) {
				ret = "履约中";
			} else if (status == 4) {
				ret = "审核失败";
			} else if (status == 5) {
				ret = "募集达成";
				 if("7".equals(btype)||"8".equals(btype)||"9".equals(btype)||"10".equals(btype)) {
					if (getEndFlg() !=1) {// 已过期
						return "投资完成";
					}
				}
			} else if (status == 6) {
				ret = "失效";
			} else if (status == 7) {
				ret = "已还完";
			}
			if (!"2".equals(btype) && status == 1) {

				Date end = CommonUtil.getDateAfter(verifyTime,
						Integer.parseInt((validTime==null||"".equals(validTime))?"0":validTime));
				Date now = new Date();
				if (end.before(now)) {
					ret = "已过期";
				}

			}
		}
		
		return ret;
	}


	public String getZstatus(){
		String ret = "";
		if("20".equals(btype)){
			if(status ==0){
				ret = "待审中";
			}else if(status ==1){
				ret = "转让中";
			}else if(status ==2){
				ret = "初审拒绝";
			}else if(status ==3){
				ret = "转让成功";
			}else if(status ==4){
				ret = "转让失败";
			}else if(status ==5){
				ret = "转让成功，等待审核";
			}else if(status ==6){
				ret = "失效";
			}	
		}
		return ret;
	}
	
	
	public Date getVerifyTime() {
		return verifyTime;
	}


	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}


	public String getValidTime() {
		return validTime;
	}


	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public BigDecimal getRateYearLow() {
		return rateYearLow;
	}


	public void setRateYearLow(BigDecimal rateYearLow) {
		this.rateYearLow = rateYearLow;
	}

	public BigDecimal getRateYearHeight() {
		return rateYearHeight;
	}

	public void setRateYearHeight(BigDecimal rateYearHeight) {
		this.rateYearHeight = rateYearHeight;
	}

	public String getBtype() {
		return btype;
	}


	public void setBtype(String btype) {
		this.btype = btype;
	}


	public Integer getTasteRule() {
		return tasteRule;
	}


	public void setTasteRule(Integer tasteRule) {
		this.tasteRule = tasteRule;
	}


	public String getTimeLimitMonth() {
		return timeLimitMonth;
	}


	public void setTimeLimitMonth(String timeLimitMonth) {
		this.timeLimitMonth = timeLimitMonth;
	}


	public Integer getShowSort() {
		return showSort;
	}


	public void setShowSort(Integer showSort) {
		this.showSort = showSort;
	}


	public Integer getIsAssign() {
		return isAssign;
	}


	public void setIsAssign(Integer isAssign) {
		this.isAssign = isAssign;
	}


	public BigDecimal getRepayInterest() {
		return repayInterest;
	}


	public void setRepayInterest(BigDecimal repayInterest) {
		this.repayInterest = repayInterest;
	}


	public Date getFinalRepayDate() {
		return finalRepayDate;
	}


	public void setFinalRepayDate(Date finalRepayDate) {
		this.finalRepayDate = finalRepayDate;
	}


	public Integer getAssignDays() {
		return assignDays;
	}


	public void setAssignDays(Integer assignDays) {
		this.assignDays = assignDays;
	}


	public BigDecimal getZrjr() {
		return zrjr;
	}


	public void setZrjr(BigDecimal zrjr) {
		this.zrjr = zrjr;
	}


	public BigDecimal getZrbj() {
		return zrbj;
	}


	public void setZrbj(BigDecimal zrbj) {
		this.zrbj = zrbj;
	}


	public Integer getShowTop() {
		return showTop;
	}


	public void setShowTop(Integer showTop) {
		this.showTop = showTop;
	}


	public Integer getTenderSubject() {
		return tenderSubject;
	}


	public void setTenderSubject(Integer tenderSubject) {
		this.tenderSubject = tenderSubject;
	}
	

	//剩余期限
	public Long getResidueDays(){
		Long day = 0l ;
		if("1".equals(productType) && "20".equals(btype)){
			Date s_date = new Date();
			if(status != 1){//不是正在转让中
				s_date = endTime;
			}
			if("3".equals(style)){
				day = CommonUtil.getDateSubtractDay(CommonUtil.date2begin( s_date ), finalRepayDate);
			}else{
				day = CommonUtil.getDateSubtractDay(CommonUtil.date2begin( s_date ), finalRepayDate) + repaymentPeriod ;
			}
		}
		return day;
	}


	public String getStyle() {
		return style;
	}


	public void setStyle(String style) {
		this.style = style;
	}


	public Integer getRepaymentPeriod() {
		return repaymentPeriod;
	}


	public void setRepaymentPeriod(Integer repaymentPeriod) {
		this.repaymentPeriod = repaymentPeriod;
	}


	public Integer getDeferStatus() {
		return deferStatus;
	}


	public void setDeferStatus(Integer deferStatus) {
		this.deferStatus = deferStatus;
	}


	public String getAwardStatus() {
		return awardStatus;
	}


	public void setAwardStatus(String awardStatus) {
		this.awardStatus = awardStatus;
	}
	
}
