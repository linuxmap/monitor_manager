package zst.core.entity;

public class VRoleTreeOrgAsset extends PageNum {
	
	private static final long serialVersionUID = 1L;

	private String assetUUID;//视图自身产生的MD5UUID
	
	private Integer assetId;//资产id
	
	private Integer deviceTypeId;//设备类型

    private String assetName;//资产名称

    private Integer assetOrgid;//组织机构id

    private String assetOrgname;//组织机构名称
    
    private Integer type;//用于在树形页面定义类型
    
    private Boolean assetorgvisible;//对应的组织是否可见
    
    private Integer assetstatus;//资产对应的组织的状态
    
    private Boolean assetvisible;//设备可见性
    
    private Boolean deletetatus;//是否删除状态
    
    private String ztreeDisplay;//用于ztree树页面展示
    
    private Boolean ztreeCheckFlag;//勾选状态
    
    private Boolean roleOutsideFlag;//角色之外标识，用于ztree页面
    
    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName == null ? null : assetName.trim();
    }

    public Integer getAssetOrgid() {
        return assetOrgid;
    }

    public void setAssetOrgid(Integer assetOrgid) {
        this.assetOrgid = assetOrgid;
    }

    public String getAssetOrgname() {
        return assetOrgname;
    }

    public void setAssetOrgname(String assetOrgname) {
        this.assetOrgname = assetOrgname == null ? null : assetOrgname.trim();
    }

	public String getAssetUUID() {
		return assetUUID;
	}

	public void setAssetUUID(String assetUUID) {
		this.assetUUID = assetUUID;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getZtreeDisplay() {
		return ztreeDisplay;
	}

	public void setZtreeDisplay(String ztreeDisplay) {
		this.ztreeDisplay = ztreeDisplay;
	}

	public Boolean getAssetorgvisible() {
		return assetorgvisible;
	}

	public void setAssetorgvisible(Boolean assetorgvisible) {
		this.assetorgvisible = assetorgvisible;
	}

	public Integer getAssetstatus() {
		return assetstatus;
	}

	public void setAssetstatus(Integer assetstatus) {
		this.assetstatus = assetstatus;
	}

	public Boolean getAssetvisible() {
		return assetvisible;
	}

	public void setAssetvisible(Boolean assetvisible) {
		this.assetvisible = assetvisible;
	}

	public Boolean getDeletetatus() {
		return deletetatus;
	}

	public void setDeletetatus(Boolean deletetatus) {
		this.deletetatus = deletetatus;
	}

	public Boolean getZtreeCheckFlag() {
		return ztreeCheckFlag;
	}

	public void setZtreeCheckFlag(Boolean ztreeCheckFlag) {
		this.ztreeCheckFlag = ztreeCheckFlag;
	}

	public Boolean getRoleOutsideFlag() {
		return roleOutsideFlag;
	}

	public void setRoleOutsideFlag(Boolean roleOutsideFlag) {
		this.roleOutsideFlag = roleOutsideFlag;
	}

}