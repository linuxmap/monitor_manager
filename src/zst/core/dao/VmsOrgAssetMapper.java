package zst.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import zst.core.entity.VmsOrgAsset;

@Repository
public interface VmsOrgAssetMapper {
	
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(VmsOrgAsset record) throws Exception;

    int insertSelective(VmsOrgAsset record) throws Exception;

    VmsOrgAsset selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(VmsOrgAsset record) throws Exception;

    int updateByPrimaryKey(VmsOrgAsset record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsOrgAsset> selectListByObj(VmsOrgAsset record) throws Exception;
    
    
    long selectCountByObj(VmsOrgAsset record) throws Exception;
    
    /**批量添加*/
    int insertBatch(List<VmsOrgAsset> list) throws Exception;
    
    /**批量删除*/
    int deleteByOrgAssetField(List<VmsOrgAsset> list) throws Exception;
    
    /**批量删除 根据来源*/
    int deleteByOrgAssetAllField(List<VmsOrgAsset> list) throws Exception;
    
    /**
     * 批量设置可见性
     * @param map
     * @return
     * @throws Exception
     */
    int updateBatchOrgAssetVisible(Map<String, Object> map) throws Exception;
    
    /**
     * 根据批量组织id查询设备
     * @param orgIdList
     * @return
     * @throws Exception
     */
    List<VmsOrgAsset> selectByOrgIdList(List<Integer> orgIdList) throws Exception;
    
    /**
     * 根据批量组织id查询设备个数
     * @param orgIdList
     * @return
     * @throws Exception
     */
    long selectCountByOrg(List<Integer> orgIdList) throws Exception;

    /**
     * 批量设置组织机构下设备的可见性
     * @param map
     * @return
     */
	int updateOrgAssetVisible(Map<String, Object> map) throws Exception;
}