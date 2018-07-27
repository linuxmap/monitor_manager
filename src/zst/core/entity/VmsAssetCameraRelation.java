package zst.core.entity;

/**
 * 设备和摄像机的管理表
 * @author Ablert
 * @date 2018-3-9 上午10:14:39
 */
public class VmsAssetCameraRelation extends PageNum {
	
	private static final long serialVersionUID = 1L;

	private Integer id;//主键

    private Integer assetId;//设备id

    private Integer cameraId;//设备关联的摄像机的id

    private Integer type;//使用方式，比如配置设备和设备之间的关系有多个使用场景

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getCameraId() {
        return cameraId;
    }

    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}