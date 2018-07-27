package zst.core.service;

import java.util.List;

import zst.core.entity.SysUserAsset;

public interface ISysUserAssetAuthorityService {

    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(SysUserAsset record) throws Exception;

    int insertSelective(SysUserAsset record) throws Exception;

    SysUserAsset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserAsset record) throws Exception;

    int updateByPrimaryKey(SysUserAsset record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<SysUserAsset> selectListByObj(SysUserAsset record) throws Exception;
    
    
    long selectCountByObj(SysUserAsset record) throws Exception;
    
    /**批量添加*/
    int insertBatch(List<SysUserAsset> list) throws Exception;
    
    /**
     * 根据用户id查询
     * @param userId
     * @return
     * @throws Exception
     */
    List<SysUserAsset> selectByUserId(Integer userId) throws Exception;
    
    /**批量删除*/
    int deleteBatch(List<SysUserAsset> list) throws Exception;
    
    /**
     * 根据用户id删除关联信息 
     * @param userId
     * @return
     * @throws Exception
     */
    int deleteByUserId(Integer userId) throws Exception;
}
