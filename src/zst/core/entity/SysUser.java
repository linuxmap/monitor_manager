package zst.core.entity;

import java.util.Date;

/**
 * 用户信息
 * @author: liuyy
 * @date: 2017年10月17日 下午4:15:55
 */
public class SysUser extends PageNum {
	
	private static final long serialVersionUID = 1L;
	
	private Integer userId;//用户编号
	
    private String userName;//用户名称
    
    private String loginName;//登录名
    
    private String loginPwd;//登录密码
    
    private String authType;//验证方式，0：本地验证，1：AD域集中验证
    
    private String roleId;//角色编号
    
    private String address;//地址
    
    private String phone;//手机号
    
    private String tell;//座机号
    
    private String email;//邮箱
    
    private Date lastLoginTime;//最后一次登录时间
    
    private String lastLoginIp;//最后一次登录IP地址
    
    private Integer loginErrorTimes;//登录错误次数
    
    private Date createTime;//用户信息创建时间
    
    private Integer creatorId;//用户信息创建人员编号
    
    private Date updateTime;//用户信息修改时间
    
    private String description;//用户描述
    
    private Integer status;//状态：0为删除，1为正常
    
    private String photoPath;//头像路径
    
    private Integer orderName;//姓名排序
    
    private Integer orderOrg;//所属组织排序

    /** 扩展属性 **/
    private Integer orgId;//组织机构主键
    private String orgName;//组织机构名称
    private String orgPinyin;//组织机构拼音
    
    /**
     * 从数据库中查询出来的用户的角色
     */
    private Integer roleIdDb;

	//用户名  模糊查询冗余变量
    private String fuzzyLoginName;
    //姓名  模糊查询冗余变量
    private String fuzzyUserName;
    //组织机构名称  模糊查询冗余变量
    private String fuzzyOrgName;
    
    private Integer orderLoginName;//用户名排序 就两个值 1 2
    private Integer orderUserName;//姓名排序 就两个值 1 2
    private Integer orderOrgName;//组织机构排序 就两个值 1 2
    
    //当前用户登录的IP
    private String currentLoginIp;
    
    //角色全局
    private Integer globalRoleId;
    //非角色id，负责存储值
    private Integer oppositeRoleId;
    
    public Integer getRoleIdDb() {
		return roleIdDb;
	}

	public void setRoleIdDb(Integer roleIdDb) {
		this.roleIdDb = roleIdDb;
	}
    
    public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType == null ? null : authType.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell == null ? null : tell.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public Integer getLoginErrorTimes() {
        return loginErrorTimes;
    }

    public void setLoginErrorTimes(Integer loginErrorTimes) {
        this.loginErrorTimes = loginErrorTimes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath == null ? null : photoPath.trim();
    }

    public Integer getOrderName() {
        return orderName;
    }

    public void setOrderName(Integer orderName) {
        this.orderName = orderName;
    }

    public Integer getOrderOrg() {
        return orderOrg;
    }

    public void setOrderOrg(Integer orderOrg) {
        this.orderOrg = orderOrg;
    }

	public String getOrgPinyin() {
		return orgPinyin;
	}

	public void setOrgPinyin(String orgPinyin) {
		this.orgPinyin = orgPinyin;
	}

	public String getFuzzyLoginName() {
		return fuzzyLoginName;
	}

	public void setFuzzyLoginName(String fuzzyLoginName) {
		this.fuzzyLoginName = fuzzyLoginName;
	}

	public String getFuzzyUserName() {
		return fuzzyUserName;
	}

	public void setFuzzyUserName(String fuzzyUserName) {
		this.fuzzyUserName = fuzzyUserName;
	}

	public Integer getOrderLoginName() {
		return orderLoginName;
	}

	public void setOrderLoginName(Integer orderLoginName) {
		this.orderLoginName = orderLoginName;
	}

	public Integer getOrderUserName() {
		return orderUserName;
	}

	public void setOrderUserName(Integer orderUserName) {
		this.orderUserName = orderUserName;
	}

	public Integer getOrderOrgName() {
		return orderOrgName;
	}

	public void setOrderOrgName(Integer orderOrgName) {
		this.orderOrgName = orderOrgName;
	}

	public String getFuzzyOrgName() {
		return fuzzyOrgName;
	}

	public void setFuzzyOrgName(String fuzzyOrgName) {
		this.fuzzyOrgName = fuzzyOrgName;
	}

	public String getCurrentLoginIp() {
		return currentLoginIp;
	}

	public void setCurrentLoginIp(String currentLoginIp) {
		this.currentLoginIp = currentLoginIp;
	}

	public Integer getGlobalRoleId() {
		return globalRoleId;
	}

	public void setGlobalRoleId(Integer globalRoleId) {
		this.globalRoleId = globalRoleId;
	}

	public Integer getOppositeRoleId() {
		return oppositeRoleId;
	}

	public void setOppositeRoleId(Integer oppositeRoleId) {
		this.oppositeRoleId = oppositeRoleId;
	}

	@Override
	public String toString() {
		return "SysUser [userId=" + userId + ", userName=" + userName
				+ ", loginName=" + loginName + ", loginPwd=" + loginPwd
				+ ", authType=" + authType + ", roleId=" + roleId
				+ ", address=" + address + ", phone=" + phone + ", tell="
				+ tell + ", email=" + email + ", lastLoginTime="
				+ lastLoginTime + ", lastLoginIp=" + lastLoginIp
				+ ", loginErrorTimes=" + loginErrorTimes + ", createTime="
				+ createTime + ", creatorId=" + creatorId + ", updateTime="
				+ updateTime + ", description=" + description + ", status="
				+ status + ", photoPath=" + photoPath + ", orderName="
				+ orderName + ", orderOrg=" + orderOrg + ", orgId=" + orgId
				+ ", orgName=" + orgName + ", orgPinyin=" + orgPinyin
				+ ", fuzzyLoginName=" + fuzzyLoginName + ", fuzzyUserName="
				+ fuzzyUserName + ", fuzzyOrgName=" + fuzzyOrgName
				+ ", orderLoginName=" + orderLoginName + ", orderUserName="
				+ orderUserName + ", orderOrgName=" + orderOrgName
				+ ", currentLoginIp=" + currentLoginIp + ", globalRoleId="
				+ globalRoleId + ", oppositeRoleId=" + oppositeRoleId + "]";
	}
	public String toLoginString() {
		return "SysUser [userId=" + userId + ", lastLoginTime=" + lastLoginTime 
				+ ", lastLoginIp=" + lastLoginIp + ", loginErrorTimes=" + loginErrorTimes 
				+ ", createTime=" + createTime + ", status=" + status 
				+ ", currentLoginIp=" + currentLoginIp + "]";
	}
	
	

}