package zst.core.entity;

/**
 * 组织机构资产关联关系
 * @author Ablert
 *
 */
public class VmsOrgAsset extends PageNum {
	
	private static final long serialVersionUID = 1L;

	private Integer id;//主键

    private Integer orgId;//组织机构id

    private Integer assetId;//资产id

    private Integer ordernumber;//排序

    private Boolean visibleflag;//节点是否可见：1 可见，0 不可见
    
    private Integer sourceflag;//数据源标识：1 原始数据 2 复制数据

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(Integer ordernumber) {
        this.ordernumber = ordernumber;
    }

    public Boolean getVisibleflag() {
        return visibleflag;
    }

    public void setVisibleflag(Boolean visibleflag) {
        this.visibleflag = visibleflag;
    }

	public Integer getSourceflag() {
		return sourceflag;
	}

	public void setSourceflag(Integer sourceflag) {
		this.sourceflag = sourceflag;
	}
}