package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.SysBaseInfoMapper;
import zst.core.entity.SysBaseInfo;
import zst.core.service.ISysBaseService;

@Service
public class SysBaseService implements ISysBaseService {

	@Resource
	private SysBaseInfoMapper baseInfoMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer baseId) throws Exception {
		return baseInfoMapper.deleteByPrimaryKey(baseId);
	}

	@Override
	public int insert(SysBaseInfo record) throws Exception {
		return baseInfoMapper.insert(record);
	}

	@Override
	public int insertSelective(SysBaseInfo record) throws Exception {
		return baseInfoMapper.insertSelective(record);
	}

	@Override
	public SysBaseInfo selectByPrimaryKey(Integer baseId) throws Exception {
		return baseInfoMapper.selectByPrimaryKey(baseId);
	}

	@Override
	public int updateByPrimaryKeySelective(SysBaseInfo record) throws Exception {
		return baseInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysBaseInfo record) throws Exception {
		return baseInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SysBaseInfo> selectListByObj(SysBaseInfo record)
			throws Exception {
		return baseInfoMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(SysBaseInfo record) throws Exception {
		return baseInfoMapper.selectCountByObj(record);
	}

}
