package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VmsCameraMapper;
import zst.core.entity.VmsCamera;
import zst.core.service.IVmsCameraService;

@Service
public class VmsCameraService implements IVmsCameraService {

	@Resource
	private VmsCameraMapper cameraMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer cameraId) throws Exception {
		return cameraMapper.deleteByPrimaryKey(cameraId);
	}

	@Override
	public int insert(VmsCamera record) throws Exception {
		return cameraMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsCamera record) throws Exception {
		return cameraMapper.insertSelective(record);
	}

	@Override
	public VmsCamera selectByPrimaryKey(Integer cameraId) throws Exception {
		return cameraMapper.selectByPrimaryKey(cameraId);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsCamera record) throws Exception {
		return cameraMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsCamera record) throws Exception {
		return cameraMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsCamera> selectListByObj(VmsCamera record) throws Exception {
		return cameraMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsCamera record) throws Exception {
		return cameraMapper.selectCountByObj(record);
	}
}
