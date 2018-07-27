package zst.core.service;

import java.util.List;

import zst.core.entity.SysOrgUser;

/**
 * 用户组织机构中间信息Service
 * @author: liuyy
 * @date: 2017年10月17日 下午3:53:58
 */
public interface ISysOrgUserService {

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
