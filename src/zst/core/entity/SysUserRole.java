package zst.core.entity;

/**
 * 用户角色信息
 * @author: liuyy
 * @date: 2017-6-12 下午1:19:22
 */
public class SysUserRole extends PageNum {
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer userId;//用户编号

    private Integer roleId;//角色编号
    
    //用于数据展示名称
    private String userName;//用户名称
    
    private Integer pId;//用于ztree树默认的父节点的id

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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

}