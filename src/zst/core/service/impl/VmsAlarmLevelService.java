package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VmsAlarmLevelMapper;
import zst.core.entity.VmsAlarmLevel;
import zst.core.service.IVmsAlarmLevelService;

@Service
public class VmsAlarmLevelService implements IVmsAlarmLevelService {

	@Resource
	private VmsAlarmLevelMapper alarmLevelMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer levelId) throws Exception {
		return alarmLevelMapper.deleteByPrimaryKey(levelId);
	}

	@Override
	public int insert(VmsAlarmLevel record) throws Exception {
		return alarmLevelMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsAlarmLevel record) throws Exception {
		return alarmLevelMapper.insertSelective(record);
	}

	@Override
	public VmsAlarmLevel selectByPrimaryKey(Integer levelId) throws Exception {
		return alarmLevelMapper.selectByPrimaryKey(levelId);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsAlarmLevel record)
			throws Exception {
		return alarmLevelMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsAlarmLevel record) throws Exception {
		return alarmLevelMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsAlarmLevel> selectListByObj(VmsAlarmLevel record)
			throws Exception {
		return alarmLevelMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsAlarmLevel record) throws Exception {
		return alarmLevelMapper.selectCountByObj(record);
	}

}
