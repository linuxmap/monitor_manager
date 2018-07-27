package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.VmsAssetCameraRelation;

/**
 * 设备和摄像机关系的存储id
 * @author Ablert
 * @date 2018-3-9 上午10:16:16
 */
@Repository
public interface VmsAssetCameraRelationMapper {
	
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(VmsAssetCameraRelation record) throws Exception;

    int insertSelective(VmsAssetCameraRelation record) throws Exception;

    VmsAssetCameraRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VmsAssetCameraRelation record) throws Exception;

    int updateByPrimaryKey(VmsAssetCameraRelation record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsAssetCameraRelation> selectListByObj(VmsAssetCameraRelation record) throws Exception;
    
    /**
     * 批量添加
     * @param list
     * @return
     * @throws Exception
     */
    int insertBatch(List<VmsAssetCameraRelation> list) throws Exception;
    
    /**
     * 通过设备id和使用方式type两个参数批量删除
     * @param record
     * @return
     * @throws Exception
     */
    int deleteByObj(VmsAssetCameraRelation record) throws Exception;
    
    /**
     * 删除设备时根据设备id查询是否有关联设备
     * @param assetIdList
     * @return
     * @throws Exception
     */
    long selectCountByAsset(List<Integer> assetIdList) throws Exception;
}