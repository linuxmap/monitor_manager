package zst.core.entity;

import java.util.Date;
import java.util.List;

public class SysFunt {
	
    private Integer id;

    private String funtName;//功能名称

    private String funtUrl;//url路径

    private Integer funtParId;//上级节点编号

    private Integer funtLevel;//菜单层级

    private Integer funtStatus;//菜单状态(0为失效,1为正常,默认为1)

    private Integer funtSort;//顺序号

    private String funtDesc;//备注

    private String className;//css样式名

    private String funtNo;//

    private Integer createUserId;//创建人

    private Date createTime;//创建时间

    private Date updateTime;//更新时间
    
    private Integer funtGroup;//菜单分类：1 管理端，2 PC端
    
    private List<SysFunt> subMenuList;//下级菜单集合
    
    private List<String> subMenuStyle;//权限按钮样式

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFuntName() {
        return funtName;
    }

    public void setFuntName(String funtName) {
        this.funtName = funtName == null ? null : funtName.trim();
    }

    public String getFuntUrl() {
        return funtUrl;
    }

    public void setFuntUrl(String funtUrl) {
        this.funtUrl = funtUrl == null ? null : funtUrl.trim();
    }

    public Integer getFuntStatus() {
        return funtStatus;
    }

    public void setFuntStatus(Integer funtStatus) {
        this.funtStatus = funtStatus;
    }

    public String getFuntDesc() {
        return funtDesc;
    }

    public void setFuntDesc(String funtDesc) {
        this.funtDesc = funtDesc == null ? null : funtDesc.trim();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getFuntNo() {
        return funtNo;
    }

    public void setFuntNo(String funtNo) {
        this.funtNo = funtNo == null ? null : funtNo.trim();
    }

    public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
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

	public List<SysFunt> getSubMenuList() {
		return subMenuList;
	}

	public void setSubMenuList(List<SysFunt> subMenuList) {
		this.subMenuList = subMenuList;
	}

	public Integer getFuntSort() {
		return funtSort;
	}

	public void setFuntSort(Integer funtSort) {
		this.funtSort = funtSort;
	}

	public Integer getFuntLevel() {
		return funtLevel;
	}

	public void setFuntLevel(Integer funtLevel) {
		this.funtLevel = funtLevel;
	}

	public Integer getFuntGroup() {
		return funtGroup;
	}

	public void setFuntGroup(Integer funtGroup) {
		this.funtGroup = funtGroup;
	}

	public Integer getFuntParId() {
		return funtParId;
	}

	public void setFuntParId(Integer funtParId) {
		this.funtParId = funtParId;
	}

	public List<String> getSubMenuStyle() {
		return subMenuStyle;
	}

	public void setSubMenuStyle(List<String> subMenuStyle) {
		this.subMenuStyle = subMenuStyle;
	}

}