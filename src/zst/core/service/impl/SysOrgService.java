package zst.core.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zst.core.dao.SysOrgMapper;
import zst.core.dao.SysUserAssetMapper;
import zst.core.dao.SysUserOrgMapper;
import zst.core.dao.VmsOrgAssetMapper;
import zst.core.dao.VmsRoleAssetMapper;
import zst.core.dao.VmsRoleOrgMapper;
import zst.core.entity.ResultBean;
import zst.core.entity.SysOrg;
import zst.core.service.ISysOrgService;
import zst.extend.exception.PlatformException;
import zst.extend.util.PinyinUtil;

@Service
@Transactional
public class SysOrgService implements ISysOrgService {

	@Resource
	private SysOrgMapper sysOrgMapper;
	
	@Resource
	private VmsOrgAssetMapper orgAssetMapper;
	
	@Resource
	private VmsRoleOrgMapper roleOrgMapper;
	
	@Resource
	private VmsRoleAssetMapper roleAssetMapper;
	
	@Resource
	private SysUserOrgMapper userOrgMapper;
	
	@Resource
	private SysUserAssetMapper userAssetMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception{
		return sysOrgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SysOrg record) throws Exception{
		return sysOrgMapper.insert(record);
	}

	@Override
	public int insertSelective(SysOrg record) throws Exception{
		return sysOrgMapper.insertSelective(record);
	}

	@Override
	public SysOrg selectByPrimaryKey(Integer id) throws Exception{
		return sysOrgMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysOrg record) throws Exception{
		Date date = new Date();
		record.setUpdateTime(date);
		return sysOrgMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysOrg record) throws Exception{
		return sysOrgMapper.updateByPrimaryKey(record);
	}

	@Override
	public long selectCountByObj(SysOrg record) throws Exception{
		return sysOrgMapper.selectCountByObj(record);
	}

	@Override
	public List<SysOrg> selectListByObj(SysOrg record) throws Exception{
		return sysOrgMapper.selectListByObj(record);
	}

	@Override
	public List<SysOrg> selectListOrgTree(SysOrg record) throws Exception {
		return sysOrgMapper.selectListOrgTree(record);
	}

	@Override
	public SysOrg selectParOrgByObj(SysOrg record) throws Exception {
		return sysOrgMapper.selectParOrgByObj(record);
	}
	
	/**  获取新建组织机构编号  **/
	public String getNextOrgId(Integer orgParId) throws Exception {
		String orgId = "";
		return orgId;
	}

	@Override
	public int deleteBatchByOrgIds(List<Integer> list) throws Exception {
		return sysOrgMapper.updateBatchByOrgIds(list);
	}

	@Override
	public SysOrg selectOrgId(Integer orgId) throws Exception {
		return sysOrgMapper.selectOrgId(orgId);
	}

	@Override
	public List<SysOrg> selectListOrgByIds(List<Integer> idList)
			throws Exception {
		return sysOrgMapper.selectListOrgByIds(idList);
	}

	@Override
	public List<SysOrg> selectChildsByParId(Integer parentId) throws Exception {
		return sysOrgMapper.selectChildsByParId(parentId);
	}

	@Override
	public int updateBatchOrgVisible(Integer[] orgIds, Boolean visibleFlag) throws Exception {
		//设置组织可见性
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("visibleflag", visibleFlag);
		map.put("ids", orgIds);
		//设置组织可见性
		orgAssetMapper.updateOrgAssetVisible(map);
		//如果是设置为不可见，则删除 角色-组织，角色-设备，用户-组织（+-），用户-设备（+-）表中的关联关系   +-表示flag字段是0还是1无论是要看还是不需要看的都删除关联
		if (visibleFlag!=null && !visibleFlag && orgIds!=null && orgIds.length>0) {
			List<Integer> orgIdList = Arrays.asList(orgIds);
			roleOrgMapper.deleteByOrgList(orgIdList);
			roleAssetMapper.deleteByOrgList(orgIdList);
			userOrgMapper.deleteByOrgList(orgIdList);
			userAssetMapper.deleteByOrgList(orgIdList);
		}
		return sysOrgMapper.updateBatchOrgVisible(map);
	}

	@Override
	public int insertBatch(List<SysOrg> list) throws Exception {
		return sysOrgMapper.insertBatch(list);
	}

	@Override
	public List<SysOrg> selectListOrgTreeOrderId(SysOrg record)
			throws Exception {
		return sysOrgMapper.selectListOrgTreeOrderId(record);
	}

	@Override
	public ResultBean insertTransactionSelective(SysOrg sysOrg, Integer userId)
			throws Exception {
		ResultBean resultBean = new ResultBean();
		resultBean.setFlag(true);
		resultBean.setFeedBack("保存成功!");
		try {
			Date date = new Date();
			sysOrg.setStatus(0);
			sysOrg.setCreatorId(userId);
			sysOrg.setCreateTime(date);
			sysOrg.setUpdateTime(date);
			sysOrg.setOrgSource(0);
			//后台添加首字母
			if (sysOrg.getOrgName()!=null) {
				sysOrg.setPinyin(PinyinUtil.getPinYinHeadChar(sysOrg.getOrgName(), 2));
			}
			//如果增加的名称是英文，则按照原来添加的名字赋值，并转成大写
			if (sysOrg.getPinyin()== null || "".equals(sysOrg.getPinyin().trim())) {
				sysOrg.setPinyin(sysOrg.getOrgName().toUpperCase());
			}
			//对encoding进行编辑
			SysOrg parOrg = sysOrgMapper.selectByPrimaryKey(sysOrg.getParentId());
			//根据parentId查出所有的子节点 为空，则直接append01, 不为空找出最大的，值加一
			List<SysOrg> currenChildOrgs = sysOrgMapper.selectChildsByParId(sysOrg.getParentId());
			if (currenChildOrgs != null && currenChildOrgs.size() == 0 && parOrg != null ) {
				StringBuffer enSb = new StringBuffer();
				enSb.append(parOrg.getEncoding()).append("01");
				sysOrg.setEncoding(enSb.toString());
			} else if (currenChildOrgs != null && currenChildOrgs.size()>0 && parOrg != null) {
				Integer max = 0;
				String encoding = null;
				Integer IntegerWithoutZero = null;
				for (SysOrg org : currenChildOrgs) {
					encoding = org.getEncoding();
					if (encoding != null && !"".equals(encoding)) {
						IntegerWithoutZero = Integer.valueOf(encoding);
						if(IntegerWithoutZero > max){
							max = IntegerWithoutZero;
						}
					}
				}
				if (max != 0) {
					sysOrg.setEncoding("0"+String.valueOf(max+1));
				}
			}
			sysOrgMapper.insertSelective(sysOrg);
			//将父部门修改为有子节点的部门
			Byte haveChild = 1;
			//处理父部门，haveChild为true
			SysOrg pOrg = new SysOrg();
			pOrg.setOrgId(sysOrg.getParentId());
			pOrg.setHaveChild(haveChild);
			sysOrgMapper.updateByPrimaryKeySelective(pOrg);
			resultBean.setPrimaryKey(sysOrg.getOrgId());
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setFeedBack("保存失败!");
			resultBean.setFlag(false);
			throw new PlatformException(e.getMessage());
		}
	}

	@Override
	public long selectContainCountByObj(SysOrg record) throws Exception {
		return sysOrgMapper.selectContainCountByObj(record);
	}

	@Override
	public List<SysOrg> selectContainListByObj(SysOrg record) throws Exception {
		return sysOrgMapper.selectContainListByObj(record);
	}

	@Override
	public long selectChildCount(List<Integer> list) throws Exception {
		return sysOrgMapper.selectChildCount(list);
	}

}
