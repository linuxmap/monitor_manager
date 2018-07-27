package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.SysFunt;

@Repository
public interface SysFuntMapper {
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(SysFunt record) throws Exception;

    int insertSelective(SysFunt record) throws Exception;

    SysFunt selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(SysFunt record) throws Exception;

    int updateByPrimaryKey(SysFunt record) throws Exception;
    /**查找所有菜单*/
    List<SysFunt> selectAll(SysFunt record) throws Exception;
    /**根据id查找下一级所有菜单 管理端*/
    List<SysFunt> selectSubFunt(Integer id) throws Exception;
    /**根据funtid修改菜单功能*/
    int updateByFuntId(SysFunt record) throws Exception;
    /**根据功能id删除功能*/
    int deleteByFuntId(Integer funtId) throws Exception;
    /**根据FuntId查询功能*/
    List<SysFunt> selectByFuntId(Integer id) throws Exception;
    /**通过用户id查询对应的菜单权限 **/
    List<SysFunt> selectFunByUserId(Integer userId) throws Exception;
    
    /**根据id查找下一级所有菜单 客户端*/
    List<SysFunt> selectClientFunt() throws Exception;
    
    /**根据id查找下一级所有菜单 客户端*/
    List<SysFunt> selectClientSubFunt(Integer id) throws Exception;
    
    /**根据roleId查询功能*/
    List<SysFunt> selectFunByRoleId(Integer roleId) throws Exception;
}