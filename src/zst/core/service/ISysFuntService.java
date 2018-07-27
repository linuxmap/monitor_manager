package zst.core.service;

import java.util.List;

import zst.core.entity.SysFunt;

public interface ISysFuntService {
	
	int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(SysFunt record) throws Exception;

    int insertSelective(SysFunt record) throws Exception;

    SysFunt selectByPrimaryKey(Integer id) throws Exception;
    /**查询所有菜单*/
    List<SysFunt> selectAll(SysFunt record)throws Exception;

    int updateByPrimaryKeySelective(SysFunt record) throws Exception;

    int updateByPrimaryKey(SysFunt record) throws Exception;
    /**根据功能id查找下一级所有菜单 r如果id为根节点则查询系统所有子菜单*/
    List<SysFunt> selectSubFunt(Integer id)throws Exception;
    /**根据功能id更改功能*/
    int updateByFuntId(SysFunt record)throws Exception;
    /**根据功能id删除功能*/
    int deleteByFuntId(Integer id)throws Exception;
    /**根据FuntId查询功能*/
    List<SysFunt>  selectByFuntId(Integer id)throws Exception;
    /**通过用户id查询对应的菜单权限 **/
    List<SysFunt> selectFunByUserId(Integer userId) throws Exception;
    
    /**根据客户端所有菜单 客户端*/
    List<SysFunt> selectClientFunt() throws Exception;
    
    /**根据id查找下一级所有菜单 客户端*/
    List<SysFunt> selectClientSubFunt(Integer id) throws Exception;
    
    /**根据roleId查询功能*/
    List<SysFunt> selectFunByRoleId(Integer id) throws Exception;
    
}
