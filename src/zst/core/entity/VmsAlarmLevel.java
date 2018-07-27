package zst.core.entity;

public class VmsAlarmLevel extends PageNum {
    
	private static final long serialVersionUID = 1L;

	private Integer levelId;

    private String name;

    private Integer value;

    private String color;

    private String description;

    private Integer orderNum;
    
    private String checkAttr;

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

	public String getCheckAttr() {
		return checkAttr;
	}

	public void setCheckAttr(String checkAttr) {
		this.checkAttr = checkAttr;
	}
}