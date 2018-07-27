package zst.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import zst.core.entity.VmsRoleAsset;

@Repository
public interface VmsRoleAssetMapper {
	
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(VmsRoleAsset record) throws Exception;

    int insertSelective(VmsRoleAsset record) throws Exception;

    VmsRoleAsset selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(VmsRoleAsset record) throws Exception;

    int updateByPrimaryKey(VmsRoleAsset record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsRoleAsset> selectListByObj(VmsRoleAsset record) throws Exception;
    
    
    long selectCountByObj(VmsRoleAsset record) throws Exception;
    
    /**批量添加*/
    int insertBatch(List<VmsRoleAsset> list) throws Exception;
    
    /**根据roelId删除相应的功能*/
    int deleteByRoleId(Integer roleId) throws Exception;
    
    /**
     * 根据角色批量查找关系
     * @param roleId
     * @return
     * @throws Exception
     */
    List<VmsRoleAsset> selectByRoleId(Integer roleId) throws Exception;
    
    /**
     * 用户个性化查询
     * @param map
     * @return
     * @throws Exception
     */
    List<VmsRoleAsset> selectListByPersonalizeMap(Map<String, Object> map) throws Exception;
    
    List<VmsRoleAsset> selectByRoleIdList(List<Integer> roleIdList) throws Exception;
    
    /**
     * 根据批量角色id批量删除
     * @param roleIdList
     * @return
     * @throws Exception
     */
    int deleteByRoleIdList(List<Integer> roleIdList) throws Exception;
    
    int deleteByRoleAsset(List<VmsRoleAsset> delRoleAssetList);

    /**
     * 根据角色id和组织id删除role_asset表中的记录
     * @param delRoleAssetOrgList
     * @return
     */
	int deleteByRoleAssetOrg(List<VmsRoleAsset> delRoleAssetOrgList);
	
	/**
     * 按组织机构id和设备id查询出符合条件的记录
     * @param map
     * @return
     * @throws Exception
     */
    long selectCountByMap(Map<String, Object> map) throws Exception;
    
    /**
	 * 设为不可见时将角色-设备表的关联关系删除 
	 * @param orgIdList
	 * @return
	 * @throws Exception
	 */
	int deleteByOrgList(List<Integer> orgIdList) throws Exception;
	
	/**
     * 设置设备不可见时,根据组织id和设备id删除role_asset表中的记录
     * @param orgAssetMap
     * @return
     */
	int deleteByOrgAsset(Map<String, Object> orgAssetMap);
}