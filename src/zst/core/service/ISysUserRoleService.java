package zst.core.service;

import java.util.List;

import zst.core.entity.ResultBean;
import zst.core.entity.SysUserRole;

/**
 * 用户角色
 * @author: liuyy
 * @date: 2017-6-12 下午1:31:58
 */
public interface ISysUserRoleService {

	int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(SysUserRole record) throws Exception;

    int insertSelective(SysUserRole record) throws Exception;

    SysUserRole selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(SysUserRole record) throws Exception;

    int updateByPrimaryKey(SysUserRole record) throws Exception;
    
    /**根据用户id删除用户角色信息 **/
    int deleteByUserId(Integer userId) throws Exception;
    
    /**保存已赋予的角色 **/
    void saveSelectRole(Integer userId, Integer[] roleIds) throws Exception;
    /**批量插入 **/
    int insertBatch(List<SysUserRole> list) throws Exception;
    /**根据roleId查找所有关联用户Id**/
    List<SysUserRole> selectByRoleId(Integer roleId)throws Exception;
    
    /**
     * 根据用户id查找所有关联角色关系
     * @param userId
     * @return
     * @throws Exception
     */
    List<SysUserRole> selectByUserId(Integer userId) throws Exception;
    
    int deleteByRoleId(Integer roleId) throws Exception;
    
    
    /**
     * 批量删除 批量取消关系
     * @param userIds
     * @param roleId
     * @return
     * @throws Exception
     */
    ResultBean deleteBatch(Integer[] userIds,Integer roleId) throws Exception;

    /**
     * 用户管理 批量设置角色
     * @param userIds
     * @param roleId
     * @throws Exception
     */
    ResultBean saveBatchSelectRole(String userIds, Integer roleId) throws Exception;
}
