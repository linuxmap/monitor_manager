package zst.core.entity;

/**
 * 用户组织机构信息
 * @author: liuyy
 * @date: 2017年10月17日 下午3:49:23
 */
public class SysOrgUser {
	
    private Integer orgUserId;//主键id
    private Integer orgId;//组织机构id
    private Integer userId;//用户id
    
	public Integer getOrgUserId() {
		return orgUserId;
	}
	public void setOrgUserId(Integer orgUserId) {
		this.orgUserId = orgUserId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

    
}