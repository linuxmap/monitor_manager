package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VmsModelMapper;
import zst.core.entity.VmsModel;
import zst.core.service.IVmsModelService;

@Service
public class VmsModelService implements IVmsModelService {
	
	@Resource
	private VmsModelMapper modelMapper;

	@Override
	public int deleteByPrimaryKey(Integer modelId) throws Exception {
		return modelMapper.deleteByPrimaryKey(modelId);
	}

	@Override
	public int insert(VmsModel record) throws Exception {
		return modelMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsModel record) throws Exception {
		return modelMapper.insertSelective(record);
	}

	@Override
	public VmsModel selectByPrimaryKey(Integer modelId) throws Exception {
		return modelMapper.selectByPrimaryKey(modelId);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsModel record) throws Exception {
		return modelMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsModel record) throws Exception {
		return modelMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsModel> selectListByObj(VmsModel record) throws Exception {
		return modelMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsModel record) throws Exception {
		return modelMapper.selectCountByObj(record);
	}

}
