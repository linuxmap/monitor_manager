package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.VmsAlarmLevel;

@Repository
public interface VmsAlarmLevelMapper {
	
    int deleteByPrimaryKey(Integer levelId) throws Exception;

    int insert(VmsAlarmLevel record) throws Exception;

    int insertSelective(VmsAlarmLevel record) throws Exception;

    VmsAlarmLevel selectByPrimaryKey(Integer levelId) throws Exception;

    int updateByPrimaryKeySelective(VmsAlarmLevel record) throws Exception;

    int updateByPrimaryKey(VmsAlarmLevel record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsAlarmLevel> selectListByObj(VmsAlarmLevel record) throws Exception;
    
    /**
     * 查询个数
     * @param record
     * @return
     * @throws Exception
     */
    long selectCountByObj(VmsAlarmLevel record) throws Exception;
}