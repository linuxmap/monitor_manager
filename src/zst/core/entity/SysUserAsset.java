package zst.core.entity;

public class SysUserAsset extends PageNum {
    
	private static final long serialVersionUID = 1L;

	private Integer id;//主键

    private Integer userId;//用户id

    private Integer assetId;//设备id

    private Integer orgId;//组织id

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

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
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