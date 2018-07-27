package zst.core.entity;

import java.util.Date;

public class VmsAsset extends PageNum {
   
	private static final long serialVersionUID = 1L;

	private Integer assetId;//主键

    private Integer devicetypeId;//类型

    private String name;//名称

    private String code;//资产编号

    private String pinyin;//首字母缩写

    private Integer ordernumber;//排序

    private Boolean deleteflag;//删除状态：0 未删除，1 已删除

    private String level;//等级

    private Date buytime;//采购时间

    private Date setuptime;//安装时间

    private Date guaranteetime;//维保时间

    private String maintainman;//维护人

    private String maintainphone;//维修电话

    private String setupposition;//安装位置

    private String setupprovider;//工程商

    private String pictureurl;//图片存储路径

    private Integer status;//状态
    
    private Integer vendorId;//厂商id
    
    private Integer productId;//产品id
    
    private Integer modelId;//设备型号id
    
    
    //以下属性用于查询 展示
    private Integer orgId;//所属组织部门，用于封装值
    
    private String orgName;//所属组织部门名称
    
    private String deviceTypeName;//设备类型名称
    
    private String vendorName;//厂商name
    
    private String productName;//产品name
    
    private String modelName;//设备型号名称
    
    private Integer loginFlag;//两种情况 0、不需要登录信息的 ; 1、需要登录信息的
    
    private Integer loginSource;//判断选择的是哪一个框 1：直接选择下拉框  0 直接填写
    
    private Boolean visibleFlagX;//节点是否可见：1 可见，0 不可见
    
    private Integer sourceFlagX;//数据源标识：1 原始数据 2 复制数据
    
    public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getDevicetypeId() {
        return devicetypeId;
    }

    public void setDevicetypeId(Integer devicetypeId) {
        this.devicetypeId = devicetypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    public Integer getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(Integer ordernumber) {
        this.ordernumber = ordernumber;
    }

    public Boolean getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(Boolean deleteflag) {
        this.deleteflag = deleteflag;
    }

    public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Date getBuytime() {
        return buytime;
    }

    public void setBuytime(Date buytime) {
        this.buytime = buytime;
    }

    public Date getSetuptime() {
        return setuptime;
    }

    public void setSetuptime(Date setuptime) {
        this.setuptime = setuptime;
    }

    public Date getGuaranteetime() {
        return guaranteetime;
    }

    public void setGuaranteetime(Date guaranteetime) {
        this.guaranteetime = guaranteetime;
    }

    public String getMaintainman() {
        return maintainman;
    }

    public void setMaintainman(String maintainman) {
        this.maintainman = maintainman == null ? null : maintainman.trim();
    }

    public String getMaintainphone() {
        return maintainphone;
    }

    public void setMaintainphone(String maintainphone) {
        this.maintainphone = maintainphone == null ? null : maintainphone.trim();
    }

    public String getSetupposition() {
        return setupposition;
    }

    public void setSetupposition(String setupposition) {
        this.setupposition = setupposition == null ? null : setupposition.trim();
    }

    public String getSetupprovider() {
        return setupprovider;
    }

    public void setSetupprovider(String setupprovider) {
        this.setupprovider = setupprovider == null ? null : setupprovider.trim();
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl == null ? null : pictureurl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getLoginSource() {
		return loginSource;
	}

	public void setLoginSource(Integer loginSource) {
		this.loginSource = loginSource;
	}

	public Integer getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(Integer loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public Boolean getVisibleFlagX() {
		return visibleFlagX;
	}

	public void setVisibleFlagX(Boolean visibleFlagX) {
		this.visibleFlagX = visibleFlagX;
	}

	public Integer getSourceFlagX() {
		return sourceFlagX;
	}

	public void setSourceFlagX(Integer sourceFlagX) {
		this.sourceFlagX = sourceFlagX;
	}

}