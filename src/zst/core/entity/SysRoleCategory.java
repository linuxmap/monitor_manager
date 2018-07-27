package zst.core.entity;

/**
 * 角色的类别
 * @author Ablert
 * @date 2018-1-23 下午4:34:51
 */
public class SysRoleCategory extends PageNum {
    
	private static final long serialVersionUID = 1L;

	private Integer categoryId;//主键

    private String name;//角色类别名称

    private Integer logouttime;//角色登录超时时间
    
    private Integer status;//角色类别状态 状态：0为删除，1为正常，2为隐藏

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getLogouttime() {
        return logouttime;
    }

    public void setLogouttime(Integer logouttime) {
        this.logouttime = logouttime;
    }
}