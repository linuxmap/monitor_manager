package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.VmsDeviceType;

@Repository
public interface VmsDeviceTypeMapper {
	
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(VmsDeviceType record) throws Exception;

    int insertSelective(VmsDeviceType record) throws Exception;

    VmsDeviceType selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(VmsDeviceType record) throws Exception;

    int updateByPrimaryKey(VmsDeviceType record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsDeviceType> selectListByObj(VmsDeviceType record) throws Exception;
    
    /**
     * 查询个数
     * @param record
     * @return
     * @throws Exception
     */
    long selectCountByObj(VmsDeviceType record) throws Exception;
}