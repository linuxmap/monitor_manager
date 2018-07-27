package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.SysUserOrg;

@Repository
public interface SysUserOrgMapper {
	
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
    
    /**
     * 根据userid的集合删除
     * @param list
     * @return
     * @throws Exception
     */
    int deleteByUserIdList(List<Integer> list) throws Exception;
    
    /**
     * 查询正常状态组织子节点的个数
     * @param list
     * @return
     * @throws Exception
     */
    long selectCountByOrg(List<Integer> list) throws Exception;
    
    /**
	 * 设为不可见时将用户-组织表的关联关系删除  flag字段是0还是1无论是要看还是不需要看的都删除关联
	 * @param orgIdList
	 * @return
	 * @throws Exception
	 */
	int deleteByOrgList(List<Integer> orgIdList) throws Exception;
}