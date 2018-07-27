package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.SysRoleCategory;

@Repository
public interface SysRoleCategoryMapper {
	
    int deleteByPrimaryKey(Integer categoryId) throws Exception;

    int insert(SysRoleCategory record) throws Exception;

    int insertSelective(SysRoleCategory record) throws Exception;

    SysRoleCategory selectByPrimaryKey(Integer categoryId) throws Exception;

    int updateByPrimaryKeySelective(SysRoleCategory record) throws Exception;

    int updateByPrimaryKey(SysRoleCategory record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<SysRoleCategory> selectListByObj(SysRoleCategory record) throws Exception;
    
    /**
     * 查询个数
     * @param record
     * @return
     * @throws Exception
     */
    long selectCountByObj(SysRoleCategory record) throws Exception;
}