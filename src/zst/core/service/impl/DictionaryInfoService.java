package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.DictionaryInfoMapper;
import zst.core.entity.DictionaryInfo;
import zst.core.service.IDictionaryInfoService;
/**
 * 字典类型相关服务
 * @author songxiang
 */
@Service
public class DictionaryInfoService implements IDictionaryInfoService{
	private @Resource DictionaryInfoMapper dictionaryInfoMapper;
	
	/**
	 * 查询字典类型集合
	 * @author songxiang
	 * @throws Exception 
	 */
	@Override
	public List<DictionaryInfo> selectListByObj(DictionaryInfo dictionary) throws Exception {
		return dictionaryInfoMapper.selectListByObj(dictionary);
	}
	
	/**
	 * 查询字典类型总和
	 * @author songxiang
	 */
	@Override
	public long selectCountByObj(DictionaryInfo dictionary) throws Exception {
		return dictionaryInfoMapper.selectCountByObj(dictionary);
	}

	@Override
	public List<DictionaryInfo> selectAllByObj(DictionaryInfo record)
			throws Exception {
		return dictionaryInfoMapper.selectAllByObj(record);
	}
	

}
