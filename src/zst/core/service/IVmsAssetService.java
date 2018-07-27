package zst.core.service;

import java.util.List;
import java.util.Map;

import zst.core.entity.ResultBean;
import zst.core.entity.VmsAsset;
import zst.core.entity.VmsCamera;
import zst.core.entity.VmsDeviceLogin;
import zst.core.entity.VmsOrgAsset;

public interface IVmsAssetService {

    int deleteByPrimaryKey(Integer assetId) throws Exception;

    int insert(VmsAsset record) throws Exception;

    int insertSelective(VmsAsset record) throws Exception;

    VmsAsset selectByPrimaryKey(Integer assetId) throws Exception;

    int updateByPrimaryKeySelective(VmsAsset record) throws Exception;

    int updateByPrimaryKey(VmsAsset record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsAsset> selectListByObj(VmsAsset record) throws Exception;
    
    
    long selectCountByObj(VmsAsset record) throws Exception;
    
    /**
     * 联表批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsAsset> selectMultiListByObj(VmsAsset record) throws Exception;
    
    /**
     * 联表查询
     * @param record
     * @return
     * @throws Exception
     */
    long selectMultiCountByObj(VmsAsset record) throws Exception;
    
    /**
     * 批量逻辑删除
     * @param list
     * @return
     * @throws Exception
     */
    int updateBatchByAssetIds(List<Integer> list) throws Exception;
    
    /**
     * 根据设备主键id批量查询设备
     * @param assetIds
     * @return
     * @throws Exception
     */
    List<VmsAsset> selectListByIds(List<Integer> assetIds) throws Exception;
    
    /**
     * 批量设置可见性
     * @param map
     * @return
     * @throws Exception
     */
    int updateBatchAssetVisible(Map<String, Object> map) throws Exception;
    
    /**
     * 批量设置等级
     * @param map
     * @return
     * @throws Exception
     */
    int updateBatchAssetLevel(Map<String, Object> map) throws Exception;
    
    /**
     * 根据组织结构id批量查询设备
     * @param orgList
     * @return
     * @throws Exception
     */
    List<VmsAsset> selectListByOrgList(List<Integer> orgList) throws Exception;
    
    /**
     * 携带部门查询总和
     * @param map
     * @return
     * @throws Exception
     */
    long selectMultiCountByMap(Map<String, Object> map) throws Exception;
    
    /**
     * 携带部门分页查询
     * @param map
     * @return
     * @throws Exception
     */
    List<VmsAsset> selectMultiListByMap(Map<String, Object> map) throws Exception;
    
    /**
     * 添加设备 有事务
     * @param vmsAsset
     * @param vmsCamera
     * @param vmsDeviceLogin
     * @param vmsOrgAsset
     * @return
     * @throws Exception
     */
    ResultBean insertAssetTransaction(VmsAsset vmsAsset ,VmsCamera vmsCamera, VmsDeviceLogin vmsDeviceLogin ,VmsOrgAsset vmsOrgAsset) throws Exception;
    
    /**
     * 编辑设备 有事务
     * @param vmsAsset
     * @param vmsCamera
     * @param vmsDeviceLogin
     * @param vmsOrgAsset
     * @return
     * @throws Exception
     */
    ResultBean updateAssetTransaction(VmsAsset vmsAsset ,VmsCamera vmsCamera, VmsDeviceLogin vmsDeviceLogin ,VmsOrgAsset vmsOrgAsset) throws Exception;
    
    /**
     * 修改登录设备  有事务
     * @param vmsAsset
     * @param vmsCamera
     * @param vmsDeviceLogin
     * @param vmsOrgAsset
     * @return
     * @throws Exception
     */
    ResultBean updateLoginAssetTransaction(VmsAsset vmsAsset ,VmsCamera vmsCamera, VmsDeviceLogin vmsDeviceLogin ,VmsOrgAsset vmsOrgAsset) throws Exception;
    
    /**
     * 设备管理-关联设备操作-保存设备和摄像机直接的关联关系
     * @param assetId
     * @param cameraIdArray
     * @return
     * @throws Exception
     */
    ResultBean updateAssetCameraRelTransaction(Integer assetId,Integer[] cameraIdArray) throws Exception;
}
