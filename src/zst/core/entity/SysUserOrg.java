package zst.core.entity;

public class SysUserOrg extends PageNum {
    
	private static final long serialVersionUID = 1L;

	private Integer id;//主键

    private Integer userId;//用户主键

    private Integer orgId;//组织主键

    private Boolean flag;//变动标识

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}