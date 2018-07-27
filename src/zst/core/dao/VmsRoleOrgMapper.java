package zst.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import zst.core.entity.VmsRoleOrg;

@Repository
public interface VmsRoleOrgMapper {
	
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
    int deleteByRoleId(Integer roleId) throws Exception;
    
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
    
    /**
     * 根据批量角色批量查找关系
     * @param roleId
     * @return
     * @throws Exception
     */
    List<VmsRoleOrg> selectByRoleIdList(List<Integer> roleIdList) throws Exception;
    
    /**
     * 根据批量角色批量删除关系
     * @param roleIdList
     * @return
     * @throws Exception
     */
    int deleteByRoleIdList(List<Integer> roleIdList) throws Exception;

	int deleteByRoleOrg(List<VmsRoleOrg> delRoleOrgList);
	
	/**
	 * 设为不可见时将角色-组织表的关联关系删除 
	 * @param orgIdList
	 * @return
	 * @throws Exception
	 */
	int deleteByOrgList(List<Integer> orgIdList) throws Exception;
}