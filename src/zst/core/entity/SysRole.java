package zst.core.entity;

import java.util.Date;

/**
 * 角色 新概念，主要用于用户分配所看到的设备和组织机构
 * @author Ablert
 * @date 2018-1-25 下午2:14:08
 */
public class SysRole {
	
    private Integer roleId;

    private String roleName;
    
    private String roleLevel;

    private Date createTime;

    private Date updateTime;

    private Integer creatorId;

    private String description;

    private Integer status;
    
    private Integer categoryId;
    
    private Integer parentId;
    
    private Boolean ztreeCheck;//用户列表中分配角色使用的树形标识

    public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Boolean getZtreeCheck() {
		return ztreeCheck;
	}

	public void setZtreeCheck(Boolean ztreeCheck) {
		this.ztreeCheck = ztreeCheck;
	}
}