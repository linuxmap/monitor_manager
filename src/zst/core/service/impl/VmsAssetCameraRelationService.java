package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VmsAssetCameraRelationMapper;
import zst.core.entity.VmsAssetCameraRelation;
import zst.core.service.IVmsAssetCameraRelationService;

@Service
public class VmsAssetCameraRelationService implements IVmsAssetCameraRelationService {

	@Resource
	private VmsAssetCameraRelationMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(VmsAssetCameraRelation record) throws Exception {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(VmsAssetCameraRelation record) throws Exception {
		return mapper.insertSelective(record);
	}

	@Override
	public VmsAssetCameraRelation selectByPrimaryKey(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsAssetCameraRelation record)
			throws Exception {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsAssetCameraRelation record)
			throws Exception {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsAssetCameraRelation> selectListByObj(
			VmsAssetCameraRelation record) throws Exception {
		return mapper.selectListByObj(record);
	}

	@Override
	public int insertBatch(List<VmsAssetCameraRelation> list) throws Exception {
		return mapper.insertBatch(list);
	}

	@Override
	public int deleteByObj(VmsAssetCameraRelation record) throws Exception {
		return mapper.deleteByObj(record);
	}

	@Override
	public long selectCountByAsset(List<Integer> assetIdList) throws Exception {
		return mapper.selectCountByAsset(assetIdList);
	}

}
