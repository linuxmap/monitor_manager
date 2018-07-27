package zst.core.entity;

public class VmsDeviceLogin extends PageNum {
    
	private static final long serialVersionUID = 1L;

	private Integer deviceloginId;//设备登录信息表id，值来源于资产id

    private Integer type;//设备类型：1 NVR，2 DVR，3 IPC，4 DVS，9 宇视平台，10 执法记录仪平台

    private String ip;//设备IP地址

    private Integer port;//设备端口号

    private Integer dataport;

    private String loginuser;//登录用户名

    private String loginpwd;//登录密码

    private Integer channelcount;//通道数量

    private Integer connecttype;//连接方式：1-SDK，2-Onvif，3-GB28181

    private String connectvalue;//连接值：HikvisionSdk，Onvif地址，GB28181地址等
    
    private String deviceNameDisplay;//对应的设备的名称
    
    private Long connecttypeLong;//展示 el中数值为long

    public Integer getDeviceloginId() {
        return deviceloginId;
    }

    public void setDeviceloginId(Integer deviceloginId) {
        this.deviceloginId = deviceloginId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getDataport() {
        return dataport;
    }

    public void setDataport(Integer dataport) {
        this.dataport = dataport;
    }

    public String getLoginuser() {
        return loginuser;
    }

    public void setLoginuser(String loginuser) {
        this.loginuser = loginuser == null ? null : loginuser.trim();
    }

    public String getLoginpwd() {
        return loginpwd;
    }

    public void setLoginpwd(String loginpwd) {
        this.loginpwd = loginpwd == null ? null : loginpwd.trim();
    }

    public Integer getChannelcount() {
        return channelcount;
    }

    public void setChannelcount(Integer channelcount) {
        this.channelcount = channelcount;
    }

    public Integer getConnecttype() {
        return connecttype;
    }

    public void setConnecttype(Integer connecttype) {
        this.connecttype = connecttype;
    }

    public String getConnectvalue() {
        return connectvalue;
    }

    public void setConnectvalue(String connectvalue) {
        this.connectvalue = connectvalue == null ? null : connectvalue.trim();
    }

	public String getDeviceNameDisplay() {
		return deviceNameDisplay;
	}

	public void setDeviceNameDisplay(String deviceNameDisplay) {
		this.deviceNameDisplay = deviceNameDisplay;
	}

	public Long getConnecttypeLong() {
		return connecttypeLong;
	}

	public void setConnecttypeLong(Long connecttypeLong) {
		this.connecttypeLong = connecttypeLong;
	}
}