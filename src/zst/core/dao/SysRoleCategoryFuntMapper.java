package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.SysRoleCategoryFunt;

@Repository
public interface SysRoleCategoryFuntMapper {
	
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(SysRoleCategoryFunt record) throws Exception;

    int insertSelective(SysRoleCategoryFunt record) throws Exception;

    SysRoleCategoryFunt selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(SysRoleCategoryFunt record) throws Exception;

    int updateByPrimaryKey(SysRoleCategoryFunt record) throws Exception;
    /**根据角色id查找对应的功能id*/
    List<SysRoleCategoryFunt> selectByCategoryid(Integer roleId) throws Exception;
    /**批量添加*/
    int addList(List<SysRoleCategoryFunt> list) throws Exception;
    /**根据categoryId删除相应的功能*/
    int deleteByCategoryid(Integer roleId) throws Exception;
    
    /**
     * 根据对象信息删除
     * @param record
     * @return
     * @throws Exception
     */
    int deleteByCategoryFunt(SysRoleCategoryFunt record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<SysRoleCategoryFunt> selectListByObj(SysRoleCategoryFunt record) throws Exception;
}