package zst.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import zst.core.entity.SysUser;

/**
 * 用户信息Mapper
 * @author: liuyy
 * @date: 2017年10月17日 下午5:22:45
 */
@Repository
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**查询总和 **/
    long selectCountByObj(SysUser record) throws Exception;
    /**分页查询 **/
    List<SysUser> selectListByObj(SysUser record) throws Exception;
    /**根据登录名查询 **/
    List<SysUser> selectListByLoginName(SysUser record) throws Exception;
    /**通过用户名查询该用户角色 **/
    List<Map<String,String>> selectRoleByLoginName(String loginName) throws Exception;
    /**通过用户名查询未授权的角色 **/
    List<Map<String,String>> selectUnRoleByLoginName(String loginName) throws Exception;
    /**批量更新状态 **/
    int updateBatchByUserIds(List<Integer> list) throws Exception;
    
    /**
     * 通过角色查询用户信息 
     * @param roleId
     * @return
     * @throws Exception
     */
    List<SysUser> selectListByRoleId(Integer roleId) throws Exception;
    
    /**
     * id集合批量查询 in
     * @param list
     * @return
     * @throws Exception
     */
    List<SysUser> selectListByIdList(List<Integer> list) throws Exception;
    
    /**
     * 携带部门查询总和
     * @param map
     * @return
     * @throws Exception
     */
    long selectCountByMap(Map<String, Object> map) throws Exception;
    
    /**
     * 携带部门分页查询
     * @param map
     * @return
     * @throws Exception
     */
    List<SysUser> selectListByMap(Map<String, Object> map) throws Exception;
    
    /**
     * 携带子部门查询总和 已分配和未分配角色
     * @param map
     * @return
     * @throws Exception
     */
    long selectCountAllocateByMap(Map<String, Object> map) throws Exception;
    
    /**
     * 携带子部门分页查询角色
     * @param map
     * @return
     * @throws Exception
     */
    List<SysUser> selectListAllocateByMap(Map<String, Object> map) throws Exception;
    
    /**
     * 查一个部门查询总和带角色
     * @param record
     * @return
     * @throws Exception
     */
    long selectCountByRoleObj(SysUser record) throws Exception;
    
    /**
     * 查一个部门分页查询，带角色
     * @param record
     * @return
     * @throws Exception
     */
    List<SysUser> selectListByRoleObj(SysUser record) throws Exception;
    
    /**
     * 查询用户所属的权限菜单类别
     * @param record
     * @return
     * @throws Exception
     */
    List<Integer> selectFuntGroupByUser(SysUser record) throws Exception;
}