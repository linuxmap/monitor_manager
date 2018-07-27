package zst.core.entity;

import java.util.Date;

/**
 * 字典条目
 * @author songxiang
 */
public class DictionaryItem extends PageNum{
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 字典条目编号
	 */
	private String itemId;
	/**
	 * 字典条目名称
	 */
	private String itemName;
	/**
	 * 字典类型id
	 */
	private String dictionaryId;
	/**
	 * 状态(0为失效,1为正常,默认为1)
	 */
	private String itemStatus;
	/**
	 * 备注
	 */
	private String itemDesc;
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
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getDictionaryId() {
		return dictionaryId;
	}
	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
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
		return "DictionaryItem [id=" + id + ", itemId=" + itemId
				+ ", itemName=" + itemName + ", dictionaryId=" + dictionaryId
				+ ", itemStatus=" + itemStatus + ", itemDesc=" + itemDesc
				+ ", createUserId=" + createUserId + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	
}
