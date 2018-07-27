package zst.core.service;

import java.util.List;

import zst.core.entity.SysUserOrg;

public interface ISysUserOrgAuthorityService {

    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(SysUserOrg record) throws Exception;

    int insertSelective(SysUserOrg record) throws Exception;

    SysUserOrg selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(SysUserOrg record) throws Exception;

    int updateByPrimaryKey(SysUserOrg record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<SysUserOrg> selectListByObj(SysUserOrg record) throws Exception;
    
    
    long selectCountByObj(SysUserOrg record) throws Exception;
    
    /**批量添加*/
    int insertBatch(List<SysUserOrg> list) throws Exception;
    
    /**
     * 根据用户id查询
     * @param userId
     * @return
     * @throws Exception
     */
    List<SysUserOrg> selectByUserId(Integer userId) throws Exception;
    
    /**批量删除*/
    int deleteBatch(List<SysUserOrg> list) throws Exception;
    
    /**
     * 根据用户id删除关联信息 
     * @param userId
     * @return
     * @throws Exception
     */
    int deleteByUserId(Integer userId) throws Exception;
}
