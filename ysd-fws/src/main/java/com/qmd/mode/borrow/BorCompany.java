package com.qmd.mode.borrow;

import java.io.Serializable;

/**
 * BorCompany 是否为个人或企业借标的属性类
 * @time 2012-10-16
 * @author 詹锋
 *
 */
public class BorCompany implements Serializable {

	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private int id;//id
		private int isqiye;//是否为企业的代码，0表示个人，1表企业
		private String yongtu;//贷款用途
		private String mortgage;//有无抵押
		private String diya;//抵押类型
		private String ddlAge;//年龄范围
		private String ddlOccupation;//职位
		private String ddlInCome;//工资范围
		private String ddlIndustry;//所属行业
		private String ddlTurnover;//年营业额
		private String ddlStaff;//员工人数
		private String txtRemark;//备注
		private String txtContact;//联系人名称
		private String sex;//性别
		private String txtTelephone;//联系电话
		private int userId;//借款人ID
		private int borrowId;//借款标ID
		private String companyname;//公司名称
		private String type;
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDdlInCome() {
			return ddlInCome;
		}
		public void setDdlInCome(String ddlInCome) {
			this.ddlInCome = ddlInCome;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getIsqiye() {
			return isqiye;
		}
		public void setIsqiye(int isqiye) {
			this.isqiye = isqiye;
		}
		public String getYongtu() {
			return yongtu;
		}
		public void setYongtu(String yongtu) {
			this.yongtu = yongtu;
		}
		public String getMortgage() {
			return mortgage;
		}
		public void setMortgage(String mortgage) {
			this.mortgage = mortgage;
		}
		public String getDiya() {
			return diya;
		}
		public void setDiya(String diya) {
			this.diya = diya;
		}
		public String getDdlAge() {
			return ddlAge;
		}
		public void setDdlAge(String ddlAge) {
			this.ddlAge = ddlAge;
		}
		public String getDdlOccupation() {
			return ddlOccupation;
		}
		public void setDdlOccupation(String ddlOccupation) {
			this.ddlOccupation = ddlOccupation;
		}
		public String getDdlIndustry() {
			return ddlIndustry;
		}
		public void setDdlIndustry(String ddlIndustry) {
			this.ddlIndustry = ddlIndustry;
		}
		public String getDdlTurnover() {
			return ddlTurnover;
		}
		public void setDdlTurnover(String ddlTurnover) {
			this.ddlTurnover = ddlTurnover;
		}
		public String getDdlStaff() {
			return ddlStaff;
		}
		public void setDdlStaff(String ddlStaff) {
			this.ddlStaff = ddlStaff;
		}
		public String getTxtRemark() {
			return txtRemark;
		}
		public void setTxtRemark(String txtRemark) {
			this.txtRemark = txtRemark;
		}
		public String getTxtContact() {
			return txtContact;
		}
		public void setTxtContact(String txtContact) {
			this.txtContact = txtContact;
		}
	
		public String getTxtTelephone() {
			return txtTelephone;
		}
		public void setTxtTelephone(String txtTelephone) {
			this.txtTelephone = txtTelephone;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public int getBorrowId() {
			return borrowId;
		}
		public void setBorrowId(int borrowId) {
			this.borrowId = borrowId;
		}
		public String getCompanyname() {
			return companyname;
		}
		public void setCompanyname(String companyname) {
			this.companyname = companyname;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
}
