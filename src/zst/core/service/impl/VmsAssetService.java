package zst.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zst.core.dao.SysOrgMapper;
import zst.core.dao.VmsAssetCameraRelationMapper;
import zst.core.dao.VmsAssetMapper;
import zst.core.dao.VmsCameraMapper;
import zst.core.dao.VmsDeviceLoginMapper;
import zst.core.dao.VmsOrgAssetMapper;
import zst.core.entity.ResultBean;
import zst.core.entity.SysOrg;
import zst.core.entity.VmsAsset;
import zst.core.entity.VmsAssetCameraRelation;
import zst.core.entity.VmsCamera;
import zst.core.entity.VmsDeviceLogin;
import zst.core.entity.VmsOrgAsset;
import zst.core.service.IVmsAssetService;
import zst.extend.enums.AssetAssociationWayEnum;
import zst.extend.exception.PlatformException;
import zst.extend.util.PinyinUtil;

@Service
@Transactional
public class VmsAssetService implements IVmsAssetService {

	@Resource
	private VmsAssetMapper assetMapper;
	
	@Resource
	private VmsOrgAssetMapper orgAssetMapper;
	
	@Resource
	private VmsDeviceLoginMapper deviceLoginMapper;
	
	@Resource
	private VmsCameraMapper cameraMapper;
	
	@Resource
	private VmsAssetCameraRelationMapper assetCameraRelationMapper;
	
	@Resource
	private SysOrgMapper orgMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer assetId) throws Exception {
		return assetMapper.deleteByPrimaryKey(assetId);
	}

	@Override
	public int insert(VmsAsset record) throws Exception {
		return assetMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsAsset record) throws Exception {
		return assetMapper.insertSelective(record);
	}

	@Override
	public VmsAsset selectByPrimaryKey(Integer assetId) throws Exception {
		return assetMapper.selectByPrimaryKey(assetId);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsAsset record) throws Exception {
		return assetMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsAsset record) throws Exception {
		return assetMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsAsset> selectListByObj(VmsAsset record) throws Exception {
		return assetMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsAsset record) throws Exception {
		return assetMapper.selectCountByObj(record);
	}

	@Override
	public List<VmsAsset> selectMultiListByObj(VmsAsset record)
			throws Exception {
		return assetMapper.selectMultiListByObj(record);
	}

	@Override
	public long selectMultiCountByObj(VmsAsset record) throws Exception {
		return assetMapper.selectMultiCountByObj(record);
	}

	@Override
	public int updateBatchByAssetIds(List<Integer> list) throws Exception {
		return assetMapper.updateBatchByAssetIds(list);
	}

	@Override
	public int updateBatchAssetVisible(Map<String, Object> map)
			throws Exception {
		return assetMapper.updateBatchAssetVisible(map);
	}

	@Override
	public int updateBatchAssetLevel(Map<String, Object> map) throws Exception {
		return assetMapper.updateBatchAssetLevel(map);
	}

	@Override
	public List<VmsAsset> selectListByOrgList(List<Integer> orgList)
			throws Exception {
		return assetMapper.selectListByOrgList(orgList);
	}

	@Override
	public long selectMultiCountByMap(Map<String, Object> map) throws Exception {
		return assetMapper.selectMultiCountByMap(map);
	}

	@Override
	public List<VmsAsset> selectMultiListByMap(Map<String, Object> map)
			throws Exception {
		return assetMapper.selectMultiListByMap(map);
	}

	@Override
	public ResultBean insertAssetTransaction(VmsAsset vmsAsset,VmsCamera vmsCamera, VmsDeviceLogin vmsDeviceLogin,VmsOrgAsset vmsOrgAsset) throws Exception {
		ResultBean resultBean = new ResultBean();
		resultBean.setFlag(true);
		try {
			//handle asset
			vmsAsset.setPinyin(PinyinUtil.getPinYinHeadChar(vmsAsset.getName(), 0));//大写
			//如果增加的名称是英文，则按照原来添加的名字赋值，并转成大写
			if (vmsAsset.getPinyin()== null || "".equals(vmsAsset.getPinyin().trim())) {
				vmsAsset.setPinyin(vmsAsset.getName().toUpperCase());
			}
			vmsAsset.setOrdernumber(1);//TODO 先默认设置为1
			assetMapper.insertSelective(vmsAsset);
			//handle asset_org
			if(vmsAsset.getAssetId() != null){//成功插入asset表
				//handle vms_org_asset
				vmsOrgAsset.setAssetId(vmsAsset.getAssetId());
				vmsOrgAsset.setOrgId(vmsAsset.getOrgId());
				vmsOrgAsset.setSourceflag(1);//设置为原始关联数据
				orgAssetMapper.insertSelective(vmsOrgAsset);
				
				//两种情况 1、不需要登录信息的  2、需要登录信息的
				if (vmsAsset.getLoginFlag() == 1) {
					if(vmsAsset.getLoginSource() == null){
						//新填写信息 asset_deviceLogin camera
						vmsDeviceLogin.setDeviceloginId(vmsAsset.getAssetId());
						vmsDeviceLogin.setType(0);//TODO
						deviceLoginMapper.insertSelective(vmsDeviceLogin);
						//handle asset_camera
						vmsCamera.setCameraId(vmsAsset.getAssetId());//camera自带登录设备的id
						vmsCamera.setDeviceloginCameraId(vmsDeviceLogin.getDeviceloginId());
						cameraMapper.insertSelective(vmsCamera);
					} else if (vmsAsset.getLoginFlag() == 1) {
						//判断选择的是哪一个框 
						//直接从下拉框选择资源   camera中设置字段
						vmsCamera.setCameraId(vmsAsset.getAssetId());
						cameraMapper.insertSelective(vmsCamera);//setDeviceloginCameraId已经从页面携带过来
					}
				}else{
					//不需要登录信息，直接向asset表中插入数据，无额外操作
				}
				//将组织改为有设备
				if (vmsAsset.getOrgId()!=null) {
					SysOrg sysOrg = new SysOrg();
					sysOrg.setOrgId(vmsAsset.getOrgId());
					byte haveChild = 1;
					sysOrg.setHaveDevice(haveChild);
					orgMapper.updateByPrimaryKeySelective(sysOrg);
				}
			}
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setFeedBack("保存失败!");
			resultBean.setFlag(false);
			throw new PlatformException(e.getMessage());
		}
	}

	@Override
	public ResultBean updateAssetTransaction(VmsAsset vmsAsset, VmsCamera vmsCamera, VmsDeviceLogin vmsDeviceLogin, VmsOrgAsset vmsOrgAsset) throws Exception {
		ResultBean resultBean = new ResultBean();
		resultBean.setFlag(true);
		try {
			vmsAsset.setPinyin(PinyinUtil.getPinYinHeadChar(vmsAsset.getName(), 0));//大写
			assetMapper.updateByPrimaryKeySelective(vmsAsset);
			//修改登录信息
			if (vmsAsset.getLoginSource() == null) {
				//增加新的登录设备
				//新填写信息 asset_deviceLogin camera
				vmsDeviceLogin.setDeviceloginId(vmsAsset.getAssetId());
				vmsDeviceLogin.setType(0);//TODO
				deviceLoginMapper.insertSelective(vmsDeviceLogin);
				//handle asset_camera
				vmsCamera.setCameraId(vmsAsset.getAssetId());//camera自带登录设备的id
				vmsCamera.setDeviceloginCameraId(vmsDeviceLogin.getDeviceloginId());
				cameraMapper.updateByPrimaryKeySelective(vmsCamera);//更改camera表
			} else {
				//切换已有的登录设备
				cameraMapper.updateByPrimaryKeySelective(vmsCamera);
				
			}
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setFeedBack("保存失败!");
			resultBean.setFlag(false);
			throw new PlatformException(e.getMessage());
		}
	}

	@Override
	public ResultBean updateLoginAssetTransaction(VmsAsset vmsAsset, VmsCamera vmsCamera, VmsDeviceLogin vmsDeviceLogin, VmsOrgAsset vmsOrgAsset) throws Exception {
		ResultBean resultBean = new ResultBean();
		resultBean.setFlag(true);
		try {
			vmsAsset.setPinyin(PinyinUtil.getPinYinHeadChar(vmsAsset.getName(), 0));//大写
			assetMapper.updateByPrimaryKeySelective(vmsAsset);
			//修改登录信息
			deviceLoginMapper.updateByPrimaryKeySelective(vmsDeviceLogin);
			//修改camera表
			cameraMapper.updateByPrimaryKeySelective(vmsCamera);
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setFeedBack("保存失败!");
			resultBean.setFlag(false);
			throw new PlatformException(e.getMessage());
		}
	}

	@Override
	public List<VmsAsset> selectListByIds(List<Integer> assetIds) throws Exception {
		return assetMapper.selectListByIds(assetIds);
	}

	@Override
	public ResultBean updateAssetCameraRelTransaction(Integer assetId, Integer[] cameraIdArray) throws Exception {
		ResultBean resultBean = new ResultBean();
		resultBean.setFlag(true);
		//整理逻辑 先删除，再添加
		try {
			VmsAssetCameraRelation acr = new VmsAssetCameraRelation();
			acr.setAssetId(assetId);
			acr.setType(AssetAssociationWayEnum.ASSET_ASSOCIATION);
			assetCameraRelationMapper.deleteByObj(acr);
			if (cameraIdArray!=null && cameraIdArray.length>0) {
				List<VmsAssetCameraRelation> list = new ArrayList<VmsAssetCameraRelation>();
				VmsAssetCameraRelation vacr = null;
				for (Integer cameraId : cameraIdArray) {
					vacr = new VmsAssetCameraRelation();
					vacr.setAssetId(assetId);
					vacr.setCameraId(cameraId);
					vacr.setType(AssetAssociationWayEnum.ASSET_ASSOCIATION);
					list.add(vacr);
				}
				assetCameraRelationMapper.insertBatch(list);
			}
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setFeedBack("保存失败!");
			resultBean.setFlag(false);
			throw new PlatformException(e.getMessage());
		}
	}

}
