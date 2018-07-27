package zst.core.entity;

public class VmsThreshold extends PageNum {
    
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private Integer devicePropertyId;

    private String expression;

    private Integer orderNumber;

    private Integer alarmLevel;
    
    //关联对象属性，拥有三个对象 ,监控项,设备类型,告警分组,告警等级
    private VmsDeviceProperty deviceProperty;
    
    private VmsDeviceItem deviceItem;
    
    private VmsDeviceType deviceType;
    
    private VmsGroup group;
    
    private VmsAlarmLevel level;
    
    private String alarmLevelName;//用于
    

    public VmsDeviceProperty getDeviceProperty() {
		return deviceProperty;
	}

	public void setDeviceProperty(VmsDeviceProperty deviceProperty) {
		this.deviceProperty = deviceProperty;
	}

	public VmsDeviceItem getDeviceItem() {
		return deviceItem;
	}

	public void setDeviceItem(VmsDeviceItem deviceItem) {
		this.deviceItem = deviceItem;
	}

	public VmsDeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(VmsDeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public VmsGroup getGroup() {
		return group;
	}

	public void setGroup(VmsGroup group) {
		this.group = group;
	}

	public VmsAlarmLevel getLevel() {
		return level;
	}

	public void setLevel(VmsAlarmLevel level) {
		this.level = level;
	}

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

    public Integer getDevicePropertyId() {
        return devicePropertyId;
    }

    public void setDevicePropertyId(Integer devicePropertyId) {
        this.devicePropertyId = devicePropertyId;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression == null ? null : expression.trim();
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(Integer alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

	public String getAlarmLevelName() {
		return alarmLevelName;
	}

	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}
}