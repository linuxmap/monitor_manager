package zst.core.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import zst.core.entity.SysLog;

/**
 * 系统日志操作记录
 * @author songxiang
 */
public interface ISysLogService {

	//新增日志操作
    int insertSelective(SysLog record);
    
    /**
     * 
     * @param request
     * @param logLevel
     * @param operateModule
     * @param operateType
     * @param operateAction
     * @return
     * @throws Exception
     */
    public int insertLog(HttpServletRequest request,Integer logLevel,String operateModule,Integer operateType,String operateAction) throws Exception;

    //条件查询操作日志对象
    SysLog selectByPrimaryKey(Integer id);

    //条件查询日志操作记录条数
  	public long selectCountByObj(SysLog sysLog) throws Exception;
  	
  	//条件查询日志操作集合
  	public List<SysLog> selectListByObj(SysLog sysLog) throws Exception;
  	
  	/**
  	 * 导出日志
  	 * @param logs
  	 * @return
  	 * @throws Exception
  	 */
  	public HSSFWorkbook createExcel(List<SysLog> logs) throws Exception;
}
