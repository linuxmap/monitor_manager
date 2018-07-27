package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.SysLog;

@Repository
public interface SysLogMapper {
	
	/**
	 * 新增日志操作
	 * @param record
	 * @return
	 */
    int insertSelective(SysLog record);

    /**
     * 条件查询操作日志对象
     * @param id
     * @return
     */
    SysLog selectByPrimaryKey(Integer id);

    /**
     * 条件查询日志操作记录条数
     * @param sysLog
     * @return
     * @throws Exception
     */
  	public long selectCountByObj(SysLog sysLog)throws Exception;
  	
  	/**
  	 * 条件查询日志操作集合
  	 * @param sysLog
  	 * @return
  	 * @throws Exception
  	 */
  	public List<SysLog> selectListByObj(SysLog sysLog)throws Exception;
}