package zst.core.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import zst.core.entity.VmsSms;

public interface IVmsSmsService {

	
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(VmsSms record) throws Exception;

    int insertSelective(VmsSms record) throws Exception;

    VmsSms selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(VmsSms record) throws Exception;

    int updateByPrimaryKey(VmsSms record) throws Exception;
    
    /**
     * 条件查询短信发送记录条数
     * @param sysLog
     * @return
     * @throws Exception
     */
  	public long selectCountByObj(VmsSms vmsSms) throws Exception;
  	
  	/**
  	 * 条件查询短息发送集合
  	 * @param sysLog
  	 * @return
  	 * @throws Exception
  	 */
  	public List<VmsSms> selectListByObj(VmsSms vmsSms) throws Exception;
  	
  	/**
  	 * 导出短信发送记录
  	 * @param logs
  	 * @return
  	 * @throws Exception
  	 */
  	public HSSFWorkbook createExcel(List<VmsSms> vmsList) throws Exception;
}
