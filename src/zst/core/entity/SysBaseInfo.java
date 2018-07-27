package zst.core.entity;

/**
 * 存储基本的配置
 * @author Ablert
 *
 */
public class SysBaseInfo extends PageNum {
    
	private static final long serialVersionUID = 7512778909376669121L;

	private Integer baseId;//主键

    private String name;//名称

    private String value;//值

    private String group;//模块分组

    public Integer getBaseId() {
        return baseId;
    }

    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group == null ? null : group.trim();
    }
}