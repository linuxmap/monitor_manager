package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.VmsCamera;

@Repository
public interface VmsCameraMapper {
	
    int deleteByPrimaryKey(Integer cameraId) throws Exception;

    int insert(VmsCamera record) throws Exception;

    int insertSelective(VmsCamera record) throws Exception;

    VmsCamera selectByPrimaryKey(Integer cameraId) throws Exception;

    int updateByPrimaryKeySelective(VmsCamera record) throws Exception;

    int updateByPrimaryKey(VmsCamera record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsCamera> selectListByObj(VmsCamera record) throws Exception;
    
    
    long selectCountByObj(VmsCamera record) throws Exception;
}