package zst.core.service;

import java.util.List;
import java.util.Map;

import zst.core.entity.VmsRoleAsset;

public interface IVmsRoleAssetService {

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
    int deleteByRoleid(Integer roleId) throws Exception;
    
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
    
    /**
     * 按组织机构id和设备id查询出符合条件的记录
     * @param map
     * @return
     * @throws Exception
     */
    long selectCountByMap(Map<String, Object> map) throws Exception;
}
