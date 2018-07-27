package zst.core.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VmsRoleOrgMapper;
import zst.core.entity.VmsRoleOrg;
import zst.core.service.IVmsRoleOrgService;

@Service
public class VmsRoleOrgService implements IVmsRoleOrgService {

	@Resource
	private VmsRoleOrgMapper roleOrgMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return roleOrgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(VmsRoleOrg record) throws Exception {
		return roleOrgMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsRoleOrg record) throws Exception {
		return roleOrgMapper.insertSelective(record);
	}

	@Override
	public VmsRoleOrg selectByPrimaryKey(Integer id) throws Exception {
		return roleOrgMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsRoleOrg record) throws Exception {
		return roleOrgMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsRoleOrg record) throws Exception {
		return roleOrgMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsRoleOrg> selectListByObj(VmsRoleOrg record) throws Exception {
		return roleOrgMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsRoleOrg record) throws Exception {
		return roleOrgMapper.selectCountByObj(record);
	}

	@Override
	public int insertBatch(List<VmsRoleOrg> list) throws Exception {
		return roleOrgMapper.insertBatch(list);
	}

	@Override
	public int deleteByRoleid(Integer roleId) throws Exception {
		return roleOrgMapper.deleteByRoleId(roleId);
	}

	@Override
	public List<VmsRoleOrg> selectByRoleId(Integer roleId) throws Exception {
		return roleOrgMapper.selectByRoleId(roleId);
	}

	@Override
	public List<VmsRoleOrg> selectListByPersonalizeMap(Map<String, Object> map)
			throws Exception {
		return roleOrgMapper.selectListByPersonalizeMap(map);
	}

}
