package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.VmsSms;

@Repository
public interface VmsSmsMapper {
	
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
}