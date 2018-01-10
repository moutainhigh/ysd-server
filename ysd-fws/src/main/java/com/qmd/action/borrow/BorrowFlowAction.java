package com.qmd.action.borrow;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.mode.borrow.Borrow;
import com.qmd.service.borrow.BorrowService;
import com.qmd.util.CommonUtil;
import com.qmd.util.JsonUtil;
import com.qmd.util.bean.NoteImg;
import com.qmd.util.bean.RateStep;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@InterceptorRefs({ @InterceptorRef(value = "userVerifyInterceptor"),
		@InterceptorRef(value = "qmdDefaultStack") })
@Service("borrowFlowAction")
public class BorrowFlowAction extends BaseAction {

	private static final long serialVersionUID = -310973213940491195L;

	Logger log = Logger.getLogger(BorrowFlowAction.class);

	private Borrow borrow=new Borrow();
	private String borImageFirst;// 标的图片地址

	private String[] vouchers;// 借款凭证图片地址
	private String[] vouchersTitle;// 借款凭证标题
	private NoteImg[] voucherBeans;

	Map<String, Object> root = new HashMap<String, Object>();
	private String errorMsg;
	private RateStep rateStep;
	@Resource
	BorrowService borrowService;
	
	
	/**
	 * 编辑标
	 */
	@Action(value="/borrow/borrowInputWork",
			results={@Result(name="success", location="/content/borrow/borrowInputWork.ftl", type="freemarker")})
	public String borrowInputWork(){
		
		Integer bbid = Integer.parseInt(id);
		borrow = this.borrowService.getBorrowById(bbid);
		
		if (StringUtils.isNotEmpty(borrow.getBorImage())) {
			voucherBeans = net.arnx.jsonic.JSON.decode(borrow.getBorImage(),NoteImg[].class);
			for(NoteImg ni:voucherBeans) {
				ni.setUrl(CommonUtil.encodeUrl(ni.getUrl()));
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 保存修改
	 * 
	 * @return
	 */
//	@Action(value = "/borrow/saveBorrowWork", results = {
//			@Result(name = "success", location = "/content/success.ftl", type = "freemarker") })
	@Action(value = "/borrow/ajaxSaveBorrowWork",results={@Result(type="json")})
	@InputConfig(resultName = "ajaxError")
	public String saveBorrowWork() {
		
		if (borrow.getName() == null || "".equals(borrow.getName())) {
			addActionError("标题不能为空，请重新输入!");
			return ajax(Status.warn,"标题不能为空，请重新输入!");
		}
		
		if (vouchers != null && vouchers.length > 0 && vouchersTitle != null
				&& vouchersTitle.length > 0) {
			List<NoteImg> noteImgList = new ArrayList<NoteImg>();
			for (int i = 0; i < vouchers.length; i++) {
				String devc = CommonUtil.decodeUrl(vouchers[i]);
				NoteImg nt = new NoteImg();
				nt.setUrl(devc);
				nt.setName(vouchersTitle[i]);
				noteImgList.add(nt);
			}

			String tempVcs = JsonUtil.listToJson(noteImgList);

			borrow.setBorImage(tempVcs);
		} else {
			borrow.setBorImage(null);
		}
		
		Borrow obj = borrowService.getBorrowById(borrow.getId());
		obj.setName(borrow.getName());
		obj.setModifyDate(new Date());
		obj.setBorImage(borrow.getBorImage());

		
		borrowService.updateBorrow(borrow);
		
		redirectUrl = getContextPath()+ "/borrow/userBorrowMgmt.do";

		return ajax(Status.success,"操作成功!");
	}

	// --------------------------------------

	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	public String getBorImageFirst() {
		return borImageFirst;
	}

	public void setBorImageFirst(String borImageFirst) {
		this.borImageFirst = borImageFirst;
	}

	public String[] getVouchers() {
		return vouchers;
	}

	public void setVouchers(String[] vouchers) {
		this.vouchers = vouchers;
	}

	public String[] getVouchersTitle() {
		return vouchersTitle;
	}

	public void setVouchersTitle(String[] vouchersTitle) {
		this.vouchersTitle = vouchersTitle;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public RateStep getRateStep() {
		return rateStep;
	}

	public void setRateStep(RateStep rateStep) {
		this.rateStep = rateStep;
	}

	
	public NoteImg[] getVoucherBeans() {
		return voucherBeans;
	}

	public void setVoucherBeans(NoteImg[] voucherBeans) {
		this.voucherBeans = voucherBeans;
	}

}
