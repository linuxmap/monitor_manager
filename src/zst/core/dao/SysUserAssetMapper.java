package zst.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import zst.core.entity.SysUserAsset;

@Repository
public interface SysUserAssetMapper {
	
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
    
    /**
     * 根据userid的集合删除
     * @param list
     * @return
     * @throws Exception
     */
    int deleteByUserIdList(List<Integer> list) throws Exception;
    
    /**
     * 按组织机构id和设备id查询出符合条件的记录
     * @param map
     * @return
     * @throws Exception
     */
    long selectCountByMap(Map<String, Object> map) throws Exception;
    
    /**
	 * 设为不可见时将用户-设备表的关联关系删除   flag字段是0还是1无论是要看还是不需要看的都删除关联
	 * @param orgIdList
	 * @return
	 * @throws Exception
	 */
	int deleteByOrgList(List<Integer> orgIdList) throws Exception;
	
	/**
     * 设置设备不可见时,根据组织id和设备id删除role_asset表中的记录
     * @param orgAssetMap
     * @return
     */
	int deleteByOrgAsset(Map<String, Object> orgAssetMap);
}