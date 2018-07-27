package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.VmsDeviceProperty;

@Repository
public interface VmsDevicePropertyMapper {
	
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(VmsDeviceProperty record) throws Exception;

    int insertSelective(VmsDeviceProperty record) throws Exception;

    VmsDeviceProperty selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(VmsDeviceProperty record) throws Exception;

    int updateByPrimaryKey(VmsDeviceProperty record) throws Exception;
    
    /**
     * 单表批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsDeviceProperty> selectListByObj(VmsDeviceProperty record) throws Exception;
    
    /**
     * 多表批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsDeviceProperty> selectConbinedListByObj(VmsDeviceProperty record) throws Exception;
    
    /**
     * 单表查询个数
     * @param record
     * @return
     * @throws Exception
     */
    long selectCountByObj(VmsDeviceProperty record) throws Exception;
    
    /**
     * 联表查询个数
     * @param record
     * @return
     * @throws Exception
     */
    long selectCombinedCountByObj(VmsDeviceProperty record) throws Exception;
    
    /**
     * 批量添加
     * @param list
     * @return
     * @throws Exception
     */
    int addList(List<VmsDeviceProperty> list) throws Exception;
    
    /**
     * 批量修改
     * @param list
     * @return
     * @throws Exception
     */
    int updateList(List<VmsDeviceProperty> list) throws Exception;
    
    /**
     * 批量删除device_property
     * @param list
     * @return
     * @throws Exception
     */
    int deleteBatchByIds(List<Integer> list) throws Exception;
}