package zst.core.entity;

import java.io.Serializable;

/**
 * 用于记录日志，跟踪对象字段的变化，将变动的信息整合，作为springmvc的参数传入进来进行比较
 * @author Ablert
 *
 */
public class ChangeFieldForLog implements Serializable {

	private static final long serialVersionUID = 1L;

	private String oldObjName;//名称变动
	
	private Integer oldLevel;//级别变动 组织
	
	private Double oldLongitude;//经度 组织
	
	private Double oldLatitude;//纬度 组织
	
	private Integer oldOrderNum;//排序级别
	
	private String oldDescription;//备注 通用
	
	private String oldMail;//邮箱 用户
	
	private String oldPhone;//手机号 用户
	
	private String oldFixedPhone;//座机 用户
	
	private String oldRoleCategoryName;//角色对应的角色类别 新
	
	private String newRoleCategoryName;//角色对应的角色类别 新
	
	private Integer oldLogouttime;//角色类别 时长
	
	private String oldColor;//颜色 告警
	
	private String oldCode;//编码 告警

	public String getOldObjName() {
		return oldObjName;
	}

	public void setOldObjName(String oldObjName) {
		this.oldObjName = oldObjName;
	}

	public Integer getOldLevel() {
		return oldLevel;
	}

	public void setOldLevel(Integer oldLevel) {
		this.oldLevel = oldLevel;
	}

	public Double getOldLongitude() {
		return oldLongitude;
	}

	public void setOldLongitude(Double oldLongitude) {
		this.oldLongitude = oldLongitude;
	}

	public Double getOldLatitude() {
		return oldLatitude;
	}

	public void setOldLatitude(Double oldLatitude) {
		this.oldLatitude = oldLatitude;
	}

	public Integer getOldOrderNum() {
		return oldOrderNum;
	}

	public void setOldOrderNum(Integer oldOrderNum) {
		this.oldOrderNum = oldOrderNum;
	}

	public String getOldDescription() {
		return oldDescription;
	}

	public void setOldDescription(String oldDescription) {
		this.oldDescription = oldDescription;
	}

	public String getOldMail() {
		return oldMail;
	}

	public void setOldMail(String oldMail) {
		this.oldMail = oldMail;
	}

	public String getOldPhone() {
		return oldPhone;
	}

	public void setOldPhone(String oldPhone) {
		this.oldPhone = oldPhone;
	}

	public String getOldFixedPhone() {
		return oldFixedPhone;
	}

	public void setOldFixedPhone(String oldFixedPhone) {
		this.oldFixedPhone = oldFixedPhone;
	}

	public String getOldRoleCategoryName() {
		return oldRoleCategoryName;
	}

	public void setOldRoleCategoryName(String oldRoleCategoryName) {
		this.oldRoleCategoryName = oldRoleCategoryName;
	}

	public String getNewRoleCategoryName() {
		return newRoleCategoryName;
	}

	public void setNewRoleCategoryName(String newRoleCategoryName) {
		this.newRoleCategoryName = newRoleCategoryName;
	}

	public Integer getOldLogouttime() {
		return oldLogouttime;
	}

	public void setOldLogouttime(Integer oldLogouttime) {
		this.oldLogouttime = oldLogouttime;
	}

	public String getOldColor() {
		return oldColor;
	}

	public void setOldColor(String oldColor) {
		this.oldColor = oldColor;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	
}
