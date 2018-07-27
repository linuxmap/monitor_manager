package zst.core.entity;

import java.util.Date;

/**
 * 发送短息的domain查询类
 * @author Ablert
 * @date 2018-3-13 下午5:27:30
 */
public class VmsSms extends PageNum {
    
	private static final long serialVersionUID = 1L;

	private Integer id;//主键

    private Date time;//发送时间

    private String phoneno;//手机号

    private String content;//短信内容

    private Integer userId;//操作人

    private Integer status;//发送状态：1成功，2失败

    private Integer sendcount;//已发送次数
    
    private String userName;//关联查询 发送者的名称
    
    //查询开始时间
    private Date queryStartTime;
    
    //查询结束时间
    private Date queryEndTime;

    public Date getQueryStartTime() {
		return queryStartTime;
	}

	public void setQueryStartTime(Date queryStartTime) {
		this.queryStartTime = queryStartTime;
	}

	public Date getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(Date queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno == null ? null : phoneno.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSendcount() {
        return sendcount;
    }

    public void setSendcount(Integer sendcount) {
        this.sendcount = sendcount;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}