package zst.core.entity;

import java.util.Date;
import java.util.List;

public class SysOrg extends PageNum {
    
	private static final long serialVersionUID = 1L;

	private Integer orgId;//主键

    private String orgName;//组织名

    private Integer parentId;//父节点

    private Integer orgLevel;//树级别

    private Integer orderNum;//排序级别

    private String description;//备注 描述

    private Date createTime;

    private Date updateTime;

    private Integer creatorId;

    private Integer status;//对应数据库的deleteflag

    private String orgCode;//编码

    private Byte haveDevice;//是否有设备

    private Byte haveChild;//是否子节点

    private Boolean isVisible;//是否可见

    private Integer zoom;//缩放级别

    private Double longitude;//经度

    private Double latitude;//纬度

    private Integer type;//节点类型
    
    private Integer orgSource;//是否是自定义，管理端增加时默认为0
    
    private String encoding;//用户客户端展示 01 0101 010101
    
    private String pinyin;//组织机构名称的拼音首字母
    
    private Boolean ztreeCheckFlag;//ztree树是否勾选
    
    private Boolean roleOutsideFlag;//角色之外标识，用于ztree页面
    
    private List<Integer> childIds;
    
    public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Integer getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(Integer orgLevel) {
        this.orgLevel = orgLevel;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Byte getHaveDevice() {
        return haveDevice;
    }

    public void setHaveDevice(Byte haveDevice) {
        this.haveDevice = haveDevice;
    }

    public Byte getHaveChild() {
        return haveChild;
    }

    public void setHaveChild(Byte haveChild) {
        this.haveChild = haveChild;
    }


    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Integer getOrgSource() {
		return orgSource;
	}

	public void setOrgSource(Integer orgSource) {
		this.orgSource = orgSource;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
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

	public List<Integer> getChildIds() {
		return childIds;
	}

	public void setChildIds(List<Integer> childIds) {
		this.childIds = childIds;
	}

}