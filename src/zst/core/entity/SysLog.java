package zst.core.entity;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 日志
 * @author Ablert
 * @date 2018-1-25 下午5:43:34
 */
public class SysLog extends PageNum {
	
	private static final long serialVersionUID = 1L;

	private Integer id;//日志id

    private Integer userId;//用户主键
    
    private String userName;//用户名
    
    private String loginIp;//登录IP

    private Date operatorDate;//操作时间
    
    private String operatorModule;//操作模块

    private Integer operatorType;//操作类型

    private String operatorDesc;//操作详情

    private Integer logGroup;//日志平台

    private Integer logLevel;//日志级别
    
    private List<Integer> queryLevelList;//用于查询日志级别以下的日志
    
    private Date createTime;//服务器创建日志时间
    
    //查询开始时间
    private Date queryStartTime;
    
    //查询结束时间
    private Date queryEndTime;

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

    public Date getOperatorDate() {
        return operatorDate;
    }

    public void setOperatorDate(Date operatorDate) {
        this.operatorDate = operatorDate;
    }

    public String getOperatorModule() {
        return operatorModule;
    }

    public void setOperatorModule(String operatorModule) {
        this.operatorModule = operatorModule == null ? null : operatorModule.trim();
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperatorDesc() {
        return operatorDesc;
    }

    public void setOperatorDesc(String operatorDesc) {
        this.operatorDesc = operatorDesc == null ? null : operatorDesc.trim();
    }

    public Integer getLogGroup() {
        return logGroup;
    }

    public void setLogGroup(Integer logGroup) {
        this.logGroup = logGroup;
    }

    public Integer getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Integer logLevel) {
        this.logLevel = logLevel;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@DateTimeFormat(pattern="YYYY-MM-DD hh:mm:ss")
	public Date getQueryStartTime() {
		return queryStartTime;
	}

	public void setQueryStartTime(Date queryStartTime) {
		this.queryStartTime = queryStartTime;
	}

	@DateTimeFormat(pattern="YYYY-MM-DD hh:mm:ss")
	public Date getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(Date queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public List<Integer> getQueryLevelList() {
		return queryLevelList;
	}

	public void setQueryLevelList(List<Integer> queryLevelList) {
		this.queryLevelList = queryLevelList;
	}
}