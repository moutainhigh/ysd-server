package net.qmdboss.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * 实体类 - 日志
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXF75093F3168097AE8B8496F61E075DA3
 * ============================================================================
 */

@Entity
public class Log extends BaseEntity {

	private static final long serialVersionUID = -4494144902110236826L;

	private String operation;// 操作名称
	private String operator;// 操作员
	private String actionClass;// Action名称
	private String actionMethod;// Action方法名称
	private String ip;// IP
	private String info;// 日志信息

	@Column(nullable = false, updatable = false)
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Column(nullable = false, updatable = false)
	public String getOperator() {
		return operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Column(nullable = false, updatable = false)
	public String getActionClass() {
		return actionClass;
	}
	
	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}
	
	@Column(nullable = false, updatable = false)
	public String getActionMethod() {
		return actionMethod;
	}
	
	public void setActionMethod(String actionMethod) {
		this.actionMethod = actionMethod;
	}
	
	@Column(nullable = false, updatable = false)
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(length = 3000, updatable = false)
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (operator == null) {
			operator = "未知";
		}
	}
	
}