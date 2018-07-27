package zst.core.service;

import java.util.List;
import java.util.Map;

import zst.core.entity.SysOrg;

/**
 * 处理资源整合的业务类
 * @author Ablert
 * @date 2018-2-9 下午5:29:35
 */
public interface IVmsAssetIntegrationService {

	/**
	 * 将组织和设备关联到某一个组织
	 * @param nodeIdArray
	 * @param parIdArray
	 * @param orgParId
	 * @return
	 * @throws Exception
	 */
	Map<Integer,List<SysOrg>> insertHandleParChildRelation(Integer[] nodeIdArray, Integer[] parIdArray, Integer orgParId) throws Exception;
	
}
