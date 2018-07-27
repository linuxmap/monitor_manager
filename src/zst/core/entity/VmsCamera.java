package zst.core.entity;

public class VmsCamera extends PageNum {
    
	private static final long serialVersionUID = 1L;

	private Integer cameraId;//摄像机id，值来源于资产id

    private Integer no;//通道编号，从1开始连续编号

    private Integer zoom;//显示级别

    private Double longitude;//经度

    private Double latitude;//纬度

    private Integer deviceloginCameraId;//设备id 对应的设备的登录信息id

    private String rescode;//资源编码

    private Integer restype;//资源类型

    private Integer ressubtype;//资源子类型

    private Integer resstatus;//资源状态

    private Integer resextstatus;//资源额外状态

    private Integer resshareflag;//该资源是否是被划归的资源, 1为被划归的资源; 0为非划归的资源

    private Integer resforeignflag;//是否为外域资源，1为外域资源; 0为非外域资源

    private Integer resstreamnum;//支持的流数目，仅当资源类型为摄像机时有效，0:无效值，1:单流，2:双流

    private Integer custometype;//自定义类型 0：普通相机，1：4G移动相机，2：卡口相机

    public Integer getCameraId() {
        return cameraId;
    }

    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
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

    public String getRescode() {
        return rescode;
    }

    public void setRescode(String rescode) {
        this.rescode = rescode == null ? null : rescode.trim();
    }

    public Integer getRestype() {
        return restype;
    }

    public void setRestype(Integer restype) {
        this.restype = restype;
    }

    public Integer getRessubtype() {
        return ressubtype;
    }

    public void setRessubtype(Integer ressubtype) {
        this.ressubtype = ressubtype;
    }

    public Integer getResstatus() {
        return resstatus;
    }

    public void setResstatus(Integer resstatus) {
        this.resstatus = resstatus;
    }

    public Integer getResextstatus() {
        return resextstatus;
    }

    public void setResextstatus(Integer resextstatus) {
        this.resextstatus = resextstatus;
    }

    public Integer getResshareflag() {
        return resshareflag;
    }

    public void setResshareflag(Integer resshareflag) {
        this.resshareflag = resshareflag;
    }

    public Integer getResforeignflag() {
        return resforeignflag;
    }

    public void setResforeignflag(Integer resforeignflag) {
        this.resforeignflag = resforeignflag;
    }

    public Integer getResstreamnum() {
        return resstreamnum;
    }

    public void setResstreamnum(Integer resstreamnum) {
        this.resstreamnum = resstreamnum;
    }

	public Integer getDeviceloginCameraId() {
		return deviceloginCameraId;
	}

	public void setDeviceloginCameraId(Integer deviceloginCameraId) {
		this.deviceloginCameraId = deviceloginCameraId;
	}

	public Integer getCustometype() {
		return custometype;
	}

	public void setCustometype(Integer custometype) {
		this.custometype = custometype;
	}
}