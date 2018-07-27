package zst.core.service;

import java.util.List;
import java.util.Map;

import zst.core.entity.VmsRoleOrg;

public interface IVmsRoleOrgService {
	
	int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(VmsRoleOrg record) throws Exception;

    int insertSelective(VmsRoleOrg record) throws Exception;

    VmsRoleOrg selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(VmsRoleOrg record) throws Exception;

    int updateByPrimaryKey(VmsRoleOrg record) throws Exception;

    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsRoleOrg> selectListByObj(VmsRoleOrg record) throws Exception;
    
    
    long selectCountByObj(VmsRoleOrg record) throws Exception;
    
    /**批量添加*/
    int insertBatch(List<VmsRoleOrg> list) throws Exception;
    
    /**根据roelId删除相应的功能*/
    int deleteByRoleid(Integer roleId) throws Exception;
    
    /**
     * 根据角色批量查找关系
     * @param roleId
     * @return
     * @throws Exception
     */
    List<VmsRoleOrg> selectByRoleId(Integer roleId) throws Exception;
    
    /**
     * 用户个性化查询
     * @param map
     * @return
     * @throws Exception
     */
    List<VmsRoleOrg> selectListByPersonalizeMap(Map<String, Object> map) throws Exception;
}
