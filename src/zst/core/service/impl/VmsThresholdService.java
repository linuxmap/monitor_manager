package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VmsThresholdMapper;
import zst.core.entity.VmsThreshold;
import zst.core.service.IVmsThresholdService;

@Service
public class VmsThresholdService implements IVmsThresholdService {
	
	@Resource
	private VmsThresholdMapper vmsThresholdMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return vmsThresholdMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(VmsThreshold record) throws Exception {
		return vmsThresholdMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsThreshold record) throws Exception {
		return vmsThresholdMapper.insertSelective(record);
	}

	@Override
	public VmsThreshold selectByPrimaryKey(Integer id) throws Exception {
		return vmsThresholdMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsThreshold record)
			throws Exception {
		return vmsThresholdMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsThreshold record) throws Exception {
		return vmsThresholdMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsThreshold> selectListByObj(VmsThreshold record)
			throws Exception {
		return vmsThresholdMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsThreshold record) throws Exception {
		return vmsThresholdMapper.selectCountByObj(record);
	}

}
