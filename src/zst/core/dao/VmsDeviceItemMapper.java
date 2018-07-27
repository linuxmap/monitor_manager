package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.VmsDeviceItem;

@Repository
public interface VmsDeviceItemMapper {
	
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(VmsDeviceItem record) throws Exception;

    int insertSelective(VmsDeviceItem record) throws Exception;

    VmsDeviceItem selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(VmsDeviceItem record) throws Exception;

    int updateByPrimaryKey(VmsDeviceItem record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsDeviceItem> selectListByObj(VmsDeviceItem record) throws Exception;
    
    /**
     * 查询个数
     * @param record
     * @return
     * @throws Exception
     */
    long selectCountByObj(VmsDeviceItem record) throws Exception;
    
    /**
     * 批量删除
     * @param list
     * @return
     * @throws Exception
     */
    int deleteBatchByIds(List<Integer> list) throws Exception;
}