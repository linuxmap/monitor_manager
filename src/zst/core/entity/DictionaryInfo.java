package zst.core.entity;

import java.util.Date;

/**
 * 字典类别
 * @author songxiang
 */
public class DictionaryInfo extends PageNum {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 字典编号
	 */
	private String dictionaryId;
	/**
	 * 字典名称
	 */
	private String dictionaryName;
	/**
	 * 状态(0为失效,1为正常,默认为1)
	 */
	private Integer dictionaryStatus;
	/** 
	 * 备注
	 */
	private String dictionaryDesc;
	/**
	 * 字典级别0:系统级别1:用户级别(默认为0)
	 */
	private String dictionaryLevel;
	/**
     * 创建人id
     */
    private Integer createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDictionaryId() {
		return dictionaryId;
	}
	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	public String getDictionaryName() {
		return dictionaryName;
	}
	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}
	public Integer getDictionaryStatus() {
		return dictionaryStatus;
	}
	public void setDictionaryStatus(Integer dictionaryStatus) {
		this.dictionaryStatus = dictionaryStatus;
	}
	public String getDictionaryDesc() {
		return dictionaryDesc;
	}
	public void setDictionaryDesc(String dictionaryDesc) {
		this.dictionaryDesc = dictionaryDesc;
	}
	public String getDictionaryLevel() {
		return dictionaryLevel;
	}
	public void setDictionaryLevel(String dictionaryLevel) {
		this.dictionaryLevel = dictionaryLevel;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "DictionaryInfo [id=" + id + ", dictionaryId=" + dictionaryId
				+ ", dictionaryName=" + dictionaryName + ", dictionaryStatus="
				+ dictionaryStatus + ", dictionaryDesc=" + dictionaryDesc
				+ ", dictionaryLevel=" + dictionaryLevel + ", createUserId="
				+ createUserId + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
}
