package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.SysOrgUserMapper;
import zst.core.entity.SysOrgUser;
import zst.core.service.ISysOrgUserService;

@Service
public class SysOrgUserService implements ISysOrgUserService {

	@Resource
	private SysOrgUserMapper sysOrgUserMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer orgUserId) {
		return sysOrgUserMapper.deleteByPrimaryKey(orgUserId);
	}

	@Override
	public int insert(SysOrgUser record) {
		return sysOrgUserMapper.insert(record);
	}

	@Override
	public int insertSelective(SysOrgUser record) {
		return sysOrgUserMapper.insertSelective(record);
	}

	@Override
	public SysOrgUser selectByPrimaryKey(Integer orgUserId) {
		return sysOrgUserMapper.selectByPrimaryKey(orgUserId);
	}

	@Override
	public int updateByPrimaryKeySelective(SysOrgUser record) {
		return sysOrgUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysOrgUser record) {
		return sysOrgUserMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteBatchByUserIds(List<Integer> list) throws Exception {
		return sysOrgUserMapper.deleteBatchByUserIds(list);
	}

	@Override
	public long selectCountByOrg(List<Integer> list) throws Exception {
		return sysOrgUserMapper.selectCountByOrg(list);
	}
}
