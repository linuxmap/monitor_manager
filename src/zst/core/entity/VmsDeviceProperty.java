package zst.core.entity;

public class VmsDeviceProperty extends PageNum {
    
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer devicetypeId;

    private Integer deviceitemId;

    private Integer orderNumber;

    private Integer groupId;
    
    //页面展示字段
    private String deviceItemName;//监控项名称
    private String deviceItemUnit;//监控项单位
    private String groupName;//告警分组名称
    private String expression;//表达式
    private String alarmName;//告警等级名称
    private String alarmColor;//告警等级颜色
    private Integer alarmLevel;//告警等级级别
    private String thresholdName;//告警名称

    public String getDeviceItemName() {
		return deviceItemName;
	}

	public void setDeviceItemName(String deviceItemName) {
		this.deviceItemName = deviceItemName;
	}

	public String getDeviceItemUnit() {
		return deviceItemUnit;
	}

	public void setDeviceItemUnit(String deviceItemUnit) {
		this.deviceItemUnit = deviceItemUnit;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public String getAlarmColor() {
		return alarmColor;
	}

	public void setAlarmColor(String alarmColor) {
		this.alarmColor = alarmColor;
	}

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDevicetypeId() {
        return devicetypeId;
    }

    public void setDevicetypeId(Integer devicetypeId) {
        this.devicetypeId = devicetypeId;
    }

    public Integer getDeviceitemId() {
        return deviceitemId;
    }

    public void setDeviceitemId(Integer deviceitemId) {
        this.deviceitemId = deviceitemId;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

	public String getThresholdName() {
		return thresholdName;
	}

	public void setThresholdName(String thresholdName) {
		this.thresholdName = thresholdName;
	}
}