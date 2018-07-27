package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.SysOrgUser;

/**
 * 用户组织机构中间信息Mapper
 * @author: liuyy
 * @date: 2017年10月17日 下午3:48:46
 */
@Repository
public interface SysOrgUserMapper {
    int deleteByPrimaryKey(Integer orgUserId);

    int insert(SysOrgUser record);

    int insertSelective(SysOrgUser record);

    SysOrgUser selectByPrimaryKey(Integer orgUserId);

    int updateByPrimaryKeySelective(SysOrgUser record);

    int updateByPrimaryKey(SysOrgUser record);
    
    /**批量删除用户组织架构关系**/
    int deleteBatchByUserIds(List<Integer> list) throws Exception;
    
    /**
     * 查询正常状态组织子节点的个数
     * @param list
     * @return
     * @throws Exception
     */
    long selectCountByOrg(List<Integer> list) throws Exception;

    
}