package zst.core.dao;

import java.util.List;

import zst.core.entity.SysRole;

public interface SysRoleMapper {
	
    int deleteByCategoryId(Integer roleId) throws Exception;

    int insert(SysRole record) throws Exception;

    int insertSelective(SysRole record) throws Exception;

    SysRole selectByPrimaryKey(Integer roleId) throws Exception;
    
    List<SysRole> selectAll(SysRole record) throws Exception;

    int updateByPrimaryKeySelective(SysRole record) throws Exception;

    int updateByPrimaryKey(SysRole record) throws Exception;
    
    
    /**
     * 根据角色类型id查询角色
     * @param categoryId
     * @return
     * @throws Exception
     */
    List<SysRole> selectByCategoryId(Integer categoryId) throws Exception;
    
    /**
     * 根据角色类型删除角色 
     * @param categoryId
     * @return
     * @throws Exception
     */
    int deleteByPrimaryKey(Integer categoryId) throws Exception;
}