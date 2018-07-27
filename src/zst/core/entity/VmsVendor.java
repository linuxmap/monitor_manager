package zst.core.entity;

public class VmsVendor extends PageNum {
    
	private static final long serialVersionUID = 1L;

	private Integer vendorId;//厂商主键

    private String name;//厂商名称

    private Integer flag;//状态 

    private Integer orderNumber;//排序

    public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}