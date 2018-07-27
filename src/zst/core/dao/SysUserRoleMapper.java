package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.SysUserRole;

/**
 * 用户角色中间信息Mapper
 * @author: liuyy
 * @date: 2017-8-7 下午6:17:56
 */
@Repository
public interface SysUserRoleMapper {
	
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(SysUserRole record) throws Exception;

    int insertSelective(SysUserRole record) throws Exception;

    SysUserRole selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(SysUserRole record) throws Exception;

    int updateByPrimaryKey(SysUserRole record) throws Exception;
    
    int deleteByUserId(Integer userId) throws Exception;
    /**批量插入 **/
    int insertBatch(List<SysUserRole> list) throws Exception;
    
    List<SysUserRole> selectByRoleId(Integer roleId) throws Exception;
    
    /**
     * 根据用户id查找所有关联角色关系
     * @param userId
     * @return
     * @throws Exception
     */
    List<SysUserRole> selectByUserId(Integer userId) throws Exception;
    
    int deleteByRoleId(Integer roleId) throws Exception;
    
    /**
     * 批量删除
     * @param list
     * @return
     * @throws Exception
     */
    int deleteBatch(List<SysUserRole> list) throws Exception;
    
    /**
     * 根据角色的id集合查询关系
     * @param roleIdList
     * @return
     * @throws Exception
     */
    List<SysUserRole> selectByRoleIdList(List<Integer> roleIdList) throws Exception;
    
    /**
     * 根据用户id集合删除用户角色信息
     * @param list
     * @return
     * @throws Exception
     */
    int deleteByUserIdList(List<Integer> list) throws Exception;
}