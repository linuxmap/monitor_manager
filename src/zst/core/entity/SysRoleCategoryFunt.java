package zst.core.entity;

public class SysRoleCategoryFunt {
	
    private Integer id;

    private Integer roleCategoryId;//角色类别id

    private Integer funtId;//功能id
    
    private Integer funtGroup;//菜单分类：1 管理端，2 PC端

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public Integer getFuntGroup() {
		return funtGroup;
	}

	public void setFuntGroup(Integer funtGroup) {
		this.funtGroup = funtGroup;
	}

	public Integer getFuntId() {
		return funtId;
	}

	public void setFuntId(Integer funtId) {
		this.funtId = funtId;
	}

	public Integer getRoleCategoryId() {
		return roleCategoryId;
	}

	public void setRoleCategoryId(Integer roleCategoryId) {
		this.roleCategoryId = roleCategoryId;
	}
}