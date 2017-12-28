package net.qmdboss.action.admin;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.qmdboss.DTO.UserHongbaoBossDTO;
import net.qmdboss.DTO.UserSpreadInviteAwardDTO;
import net.qmdboss.entity.User;
import net.qmdboss.service.UserHongbaoService;
import net.qmdboss.service.UserService;
import net.qmdboss.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;



@ParentPackage("admin")
public class DeriveReportNewAction  extends BaseAdminAction {

	private static final long serialVersionUID = 5759030605544502091L;

	private String username;
	private Date beginDate;//开始时间
	private Date endDate;//结束时间
	private String status;
	private String hongbaoName;
	
	
	
	
	
	
	
	
	
	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	@Resource(name = "userHongbaoServiceImpl")
	private UserHongbaoService userHongbaoService;

	public String exceSpreadlistMoney(){
		Map<String,Object> map = new HashMap<String, Object>();
		

		if(StringUtils.isNotEmpty(username)) {
			User u=userService.getUserByUsername(username.trim());
			if(u!=null){
				map.put("username",u.getId());		
			}		
		}


		List<UserSpreadInviteAwardDTO> dataList = new ArrayList<UserSpreadInviteAwardDTO>();
		
		dataList =userService.getListInviteAwardBySql(map);
		OutputStream out = null;
		try {
			 out = getResponse().getOutputStream();
			 getResponse().reset();//清空buffer,设置页面不缓存
			 getResponse().setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("邀请好友投资收益表"+  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xls", "UTF-8"));
			 getResponse().setContentType("application/msexcel;charset=UTF-8");
			 
			WritableWorkbook wwb = Workbook.createWorkbook(out);
			WritableSheet ws = wwb.createSheet("投资记录报表", 0);
		
			Label l= new Label(0, 0, "被邀请人");
			ws.addCell(l);
			l= new Label(1, 0, "姓名");
			ws.addCell(l);
			l= new Label(2, 0, "投资项目");
			ws.addCell(l);
			l= new Label(3, 0, "投资时间");
			ws.addCell(l);
			l= new Label(4, 0, "项目状态");
			ws.addCell(l);
			l= new Label(5, 0, "项目收益");
			ws.addCell(l);
			l= new Label(6, 0, "邀请人");
			ws.addCell(l);
			l= new Label(7, 0, "邀请人姓名");
			ws.addCell(l);
			
			int i=1;
			for(UserSpreadInviteAwardDTO item:dataList){
				l=new Label(0,i,item.getUsername() );
				ws.addCell(l);
				l= new Label(1, i, item.getRealname());
				ws.addCell(l);
				l= new Label(2, i, item.getBorrowName());
				ws.addCell(l);
				if(item.getCreateDate()!=null){
					l= new Label(3, i, CommonUtil.getDate2String(item.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
				}else{
					l= new Label(3, i, "");
				}
			
				ws.addCell(l);
				
				
				String borrowStatusVal="";
				if(item.getBorrowStatus()!=null){
					switch(item.getBorrowStatus()) {  
					    case 1: borrowStatusVal="正在招标中";break;  
					    case 3: borrowStatusVal="还款中";break;
					    case 6: borrowStatusVal="撤回的标";break; 
					    case 7: borrowStatusVal="已完成的标";break; 
					    default: break;  
					}
				}
				l= new Label(4, i, borrowStatusVal);
				
				
				
				ws.addCell(l);
				l= new Label(5, i,item.getBorrowInterest()  );
				ws.addCell(l);
				l= new Label(6, i, item.getInviteName());
				ws.addCell(l);
				l= new Label(7, i, item.getInviteRealName());
				ws.addCell(l);
				i++;
			}
			ws.setColumnView(0, 15);// 设置列宽
            ws.setColumnView(1, 15);
            ws.setColumnView(2, 15);
            ws.setColumnView(3, 25);
            ws.setColumnView(4, 15);
            ws.setColumnView(5, 15);
            ws.setColumnView(6, 15);
            ws.setColumnView(7, 15);
         
            ws.setRowView(0, 600);// 设置行高
            
            wwb.write();
            wwb.close();
            out.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	public String execUserhongbaoList(){
		
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		
		if(beginDate!=null){
			if(endDate == null){
				addErrorMessages("截止时间必填!");
				return ERROR;
			}
		}
		if(endDate!=null){
			if(beginDate == null){
				addErrorMessages("起始时间必填!");
				return ERROR;
			}
		}
		
		if(beginDate!=null&&endDate!=null){
			map.put("beginDate",CommonUtil.getDate2String(CommonUtil.date2begin(beginDate), "yyyy-MM-dd HH:mm:ss"));	
			map.put("endDate",CommonUtil.getDate2String(CommonUtil.date2end(endDate), "yyyy-MM-dd HH:mm:ss"));	
		}
		
		
		
		if(StringUtils.isNotEmpty(username)) {		
				map.put("username",username);				
		}
		
		if(StringUtils.isNotEmpty(hongbaoName)) {
			
			try {
				map.put("hongbaoName",URLDecoder.decode(hongbaoName, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			}			
		}
		
		if(StringUtils.isNotEmpty(status)) {	
			map.put("status",status);			
		}

		System.out.println(map);
	

		List<UserHongbaoBossDTO> dataList = new ArrayList<UserHongbaoBossDTO>();
		

		dataList =userHongbaoService.findUserHongbaoList(map);
		
		
		OutputStream out = null;
		try {
			 out = getResponse().getOutputStream();
			 getResponse().reset();//清空buffer,设置页面不缓存
			 getResponse().setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("用户红包表"+  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xls", "UTF-8"));
			 getResponse().setContentType("application/msexcel;charset=UTF-8");
			 
			WritableWorkbook wwb = Workbook.createWorkbook(out);
			WritableSheet ws = wwb.createSheet("红包记录报表", 0);
			
			Label l= new Label(0, 0, "ID");
			ws.addCell(l);
			l= new Label(1, 0, "红包归属");
			ws.addCell(l);
			l= new Label(2, 0, "红包标题");
			ws.addCell(l);
			l= new Label(3, 0, "红包金额");
			ws.addCell(l);
			l= new Label(4, 0, "红包有效期");
			ws.addCell(l);
			l= new Label(5, 0, "红包过期时间");
			ws.addCell(l);
			l= new Label(6, 0, "适用项目期限");
			ws.addCell(l);
			l= new Label(7, 0, "满多少可用");
			ws.addCell(l);
			l= new Label(8, 0, "投资项目名");
			ws.addCell(l);
			l= new Label(9, 0, "状态");
			ws.addCell(l);
			l= new Label(10, 0, "状态修改时间");
			ws.addCell(l);
			
			int i=1;
			for(UserHongbaoBossDTO item:dataList){
				l=new Label(0,i,String.valueOf(item.getId()) );
				ws.addCell(l);
				l= new Label(1, i, item.getUsername());
				ws.addCell(l);
				l= new Label(2, i, item.getHongbaoName());
				ws.addCell(l);
				l= new Label(3, i, String.valueOf(item.getHongbaoMoney()));
				ws.addCell(l);
				l= new Label(4, i, String.valueOf(item.getExpDate()));
				ws.addCell(l);
					
				if(item.getEndTime()!=null){
					l= new Label(5, i, CommonUtil.getDate2String(item.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
				}else{
					l= new Label(5, i, "");
				}
			
				ws.addCell(l);
				l= new Label(6, i, String.valueOf(item.getLimitStart()));
				ws.addCell(l);
				l= new Label(7, i, String.valueOf(item.getInvestFullMomey()));
				ws.addCell(l);
				l= new Label(8, i,item.getBorrowName());
				ws.addCell(l);
				
			
				
				
				String StatusVal="";
				if(String.valueOf(item.getStatus())!=null){
					switch(item.getStatus()) {  
					    case -1: StatusVal="待发放";break;  
					    case 0: StatusVal="未使用";break;
					    case 1: StatusVal="已使用";break; 
					    default: StatusVal="失效";break;  
					}
				}
				l= new Label(9, i, StatusVal);
				ws.addCell(l);
				
				if(item.getModifyDate()!=null){
					l= new Label(10, i, CommonUtil.getDate2String(item.getModifyDate(), "yyyy-MM-dd HH:mm:ss"));
				}else{
					l= new Label(10, i, "");
				}

			
				ws.addCell(l);
				i++;
			}
			ws.setColumnView(0, 15);// 设置列宽
            ws.setColumnView(1, 15);
            ws.setColumnView(2, 15);
            ws.setColumnView(3, 15);
            ws.setColumnView(4, 15);
            ws.setColumnView(5, 20);
            ws.setColumnView(6, 15);
            ws.setColumnView(7, 15);
            ws.setColumnView(8, 20);
            ws.setColumnView(9, 20);
            ws.setColumnView(10, 20);
         
            ws.setRowView(0, 600);// 设置行高
            
            wwb.write();
            wwb.close();
            out.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}




	public Date getBeginDate() {
		return beginDate;
	}




	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}




	public Date getEndDate() {
		return endDate;
	}




	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}




	public String getHongbaoName() {
		return hongbaoName;
	}




	public void setHongbaoName(String hongbaoName) {
		this.hongbaoName = hongbaoName;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
