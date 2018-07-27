package zst.core.service;

import java.util.List;

import zst.core.entity.ResultBean;
import zst.core.entity.SysRoleCategory;

public interface ISysRoleCategoryService {

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
    
    /**
     * 拷贝角色类别
     * @param categoryId
     * @return
     * @throws Exception
     */
    ResultBean insertSameObjectById(Integer categoryId,String realName) throws Exception;
    
    /**
     * 查询改角色类型下是否有角色，角色下有用户
     * @param categoryId
     * @return
     * @throws Exception
     */
    ResultBean selectRelatedRole(Integer categoryId) throws Exception;

    /**
     * 更新客户端和管理端的权限
     * @param roleCategoryId
     * @param funtIds 
     * @param funtGroup 
     * @return
     */
	ResultBean updateDoubleFuntByCategoryId(Integer roleCategoryId, Integer funtGroup, Integer[] funtIds) throws Exception;
}
