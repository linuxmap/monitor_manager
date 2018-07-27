package zst.core.service;

import java.util.List;

import zst.core.entity.ResultBean;
import zst.core.entity.SysOrg;

/**
 * 组织机构
 * @author: liuyy
 * @date: 2017-6-12 下午1:31:30
 */
public interface ISysOrgService {

	int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(SysOrg record) throws Exception;

    int insertSelective(SysOrg record) throws Exception;
    
    ResultBean insertTransactionSelective(SysOrg record,Integer userId) throws Exception;

    SysOrg selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(SysOrg record) throws Exception;

    int updateByPrimaryKey(SysOrg record) throws Exception;
    
    /**查询组织机构总条数 **/
    long selectCountByObj(SysOrg record) throws Exception;
    
    /**分页查询组织机构集合 **/
    List<SysOrg> selectListByObj(SysOrg record) throws Exception;
    
    List<SysOrg> selectListOrgTreeOrderId(SysOrg record) throws Exception;
    
    /**查询组织机构tree **/
    List<SysOrg> selectListOrgTree(SysOrg record) throws Exception;
    
    /**查询该节点的父类节点**/
    SysOrg selectParOrgByObj(SysOrg record) throws Exception;
    
    /**批量删除(逻辑删除，不能删除非叶子节点及挂载了用户的) **/
    int deleteBatchByOrgIds(List<Integer> list) throws Exception;
    
    /**根据orgId查询组织机构信息 **/
    SysOrg selectOrgId(Integer orgId) throws Exception;
    /**
     * 根据idlist查询集合
     * @param idList
     * @return
     * @throws Exception
     */
    List<SysOrg> selectListOrgByIds(List<Integer> idList) throws Exception;
    
    /**
     * 根据父节点查出所有的子节点
     * @param parentId
     * @return
     * @throws Exception
     */
    List<SysOrg> selectChildsByParId(Integer parentId) throws Exception;
    
    /**
     * 批量设置可见性
     * @param map
     * @return
     * @throws Exception
     */
    int updateBatchOrgVisible(Integer[] orgIds, Boolean visibleFlag) throws Exception;
    
    /**批量添加*/
    int insertBatch(List<SysOrg> list) throws Exception;
    
    /**
     * 包含子组织查询机构总和 
     * @param record
     * @return
     * @throws Exception
     */
    long selectContainCountByObj(SysOrg record) throws Exception;
    
    /**
     * 包含子组织分页查询
     * @param record
     * @return
     * @throws Exception
     */
    List<SysOrg> selectContainListByObj(SysOrg record) throws Exception;
    
    /**
     * 查询正常状态组织子节点的个数
     * @param list
     * @return
     * @throws Exception
     */
    long selectChildCount(List<Integer> list) throws Exception;
}
