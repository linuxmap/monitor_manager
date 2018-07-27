package zst.core.entity;

public class VmsDeviceType extends PageNum {
    
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;//设备类型
    
    private Integer loginflag;//设备类型是否有登录信息1：有；0无

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

	public Integer getLoginflag() {
		return loginflag;
	}

	public void setLoginflag(Integer loginflag) {
		this.loginflag = loginflag;
	}
}