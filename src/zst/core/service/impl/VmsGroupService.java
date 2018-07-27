package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VmsGroupMapper;
import zst.core.entity.VmsGroup;
import zst.core.service.IVmsGoupService;

@Service
public class VmsGroupService implements IVmsGoupService {

	@Resource
	private VmsGroupMapper groupMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer groupId) throws Exception {
		return groupMapper.deleteByPrimaryKey(groupId);
	}

	@Override
	public int insert(VmsGroup record) throws Exception {
		return groupMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsGroup record) throws Exception {
		return groupMapper.insertSelective(record);
	}

	@Override
	public VmsGroup selectByPrimaryKey(Integer groupId) throws Exception {
		return groupMapper.selectByPrimaryKey(groupId);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsGroup record) throws Exception {
		return groupMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsGroup record) throws Exception {
		return groupMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsGroup> selectListByObj(VmsGroup record) throws Exception {
		return groupMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsGroup record) throws Exception {
		return groupMapper.selectCountByObj(record);
	}

}
