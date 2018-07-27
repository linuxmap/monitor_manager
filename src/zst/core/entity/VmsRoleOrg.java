package zst.core.entity;

public class VmsRoleOrg extends PageNum {
    
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer roleId;

    private Integer orgId;
    
    private Boolean alreadyChanged;//前台页面判断，是否已经是变动的标识，存在于user_org中
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

	public Boolean getAlreadyChanged() {
		return alreadyChanged;
	}

	public void setAlreadyChanged(Boolean alreadyChanged) {
		this.alreadyChanged = alreadyChanged;
	}

}